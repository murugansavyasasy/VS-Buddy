package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter.ChatAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.ChatDataModel;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;

public class ChatScreenActivity extends AppCompatActivity {


    String DemoId, txtMessage, content, LoginID;
    SharedPreferences shpRemember;

    private static final String SH_USERS = "userInfo";
    private static final String SH_USERID = "UserId";
    RecyclerView rvRecyle;

    ChatAdapter madapter;
    private ArrayList<ChatDataModel> chatList = new ArrayList<>();
    String UserId, PoId,CustomerName,PO_number;
    EditText typeMessage;
    ImageView imgSend;
    TextView Customername,PO_Number;
    String message, lastPurchaseOrderID = "";

    public static Handler handler = new Handler();
    public static Runnable yourRunnable;
    public static final int THIRTY_SECONDS = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_screen);

        rvRecyle = (RecyclerView) findViewById(R.id.rvChatList);
        imgSend = (ImageView) findViewById(R.id.imgSend);
        typeMessage = (EditText) findViewById(R.id.lblTextMessage);
        Customername = (TextView) findViewById(R.id.Customername);
        PO_Number = (TextView) findViewById(R.id.PO_Number);

        UserId = SharedPreference_class.getUserid(ChatScreenActivity.this);

        PoId = getIntent().getExtras().getString("POID", "");
        Log.d("Poid", PoId);

        CustomerName=getIntent().getExtras().getString("CustomerName", "");
        PO_number=getIntent().getExtras().getString("PO_number", "");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = typeMessage.getText().toString();

                if (!message.equals("")) {
                    postChat(message, lastPurchaseOrderID);
                }
            }
        });

        madapter = new ChatAdapter(chatList, ChatScreenActivity.this);
        LinearLayoutManager circularLayoutManager = new LinearLayoutManager(ChatScreenActivity.this);
        rvRecyle.setLayoutManager(circularLayoutManager);
        rvRecyle.setItemAnimator(new DefaultItemAnimator());
        rvRecyle.setAdapter(madapter);


        callChatListevery10Secods();
        //chatList();

        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
        LoginID = shpRemember.getString(SH_USERID, null);


        Customername.setText(CustomerName);
        PO_Number.setText(PO_number);

    }


    private void callChatListevery10Secods() {

        handler = new Handler();
        yourRunnable = new Runnable() {
            @Override
            public void run() {
                chatList("0");
                handler.postDelayed(this, THIRTY_SECONDS);
            }
        };
        handler.post(yourRunnable);

    }


    @Override
    protected void onResume() {
        super.onResume();
        startHandler();

    }

    private void startHandler() {
        handler.postDelayed(yourRunnable, THIRTY_SECONDS);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopHandler();

    }

    private void postChat(String msg, String purchaseID) {


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Message", msg);
        jsonObject.addProperty("PurchaseOrderID", PoId);
        jsonObject.addProperty("UserID", UserId);


        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);
        Call<JsonArray> call = apiService.postText(jsonObject);

        call.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {


                try {
                    Log.d("Listdemo;Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();

                    JSONArray jsonArrayorgList = new JSONArray(strResponse);

                    if (jsonArrayorgList.length() > 0) {
                        JSONObject data = jsonArrayorgList.getJSONObject(0);

                        String result = data.getString("result");
                        String Message = data.getString("resultMessage");

                        if (result.equals("1")) {
                            chatList("1");
                            typeMessage.setText("");

                        }


                    } else {
                        //Alert("Server Response Failed.");
                    }


                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                // Alert("Server Connection Failed");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopHandler();
        finish();
    }


    private void stopHandler() {
        handler.removeCallbacks(yourRunnable);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return (true);

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void chatList(final String postvalue) {
//        final ProgressDialog mProgressDialog = new ProgressDialog(this);
//        mProgressDialog.setIndeterminate(true);
//        mProgressDialog.setMessage("Loading...");
//        mProgressDialog.setCancelable(false);
//        mProgressDialog.show();


        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);
        Call<JsonArray> call = apiService.GetPurchaseOrderConversation(PoId, UserId);

        call.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
//                if (mProgressDialog.isShowing())
//                    mProgressDialog.dismiss();

                try {
                    Log.d("Listdemo;Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();

                    JSONArray jsonArrayorgList = new JSONArray(strResponse);

                    if (jsonArrayorgList.length() > 0) {

                        chatList.clear();
                        ChatDataModel data;
                        for (int i = 0; i < jsonArrayorgList.length(); i++) {
                            JSONObject temp = jsonArrayorgList.getJSONObject(i);
                            data = new ChatDataModel(temp.getString("idPurchaseOrderConversation"),
                                    temp.getString("PurchaseOrderID"), temp.getString("UserID"),
                                    temp.getString("Message"), temp.getString("createdOn"),
                                    temp.getString("userName"), temp.getString("IconText"),
                                    temp.getString("ChatSide"), temp.getString("isSeen"));

                            chatList.add(data);

                        }

                        if (postvalue.equals("1")) {
                            rvRecyle.scrollToPosition(madapter.getItemCount() - 1);
                        }
                        madapter.notifyDataSetChanged();

                        messageSeenApi();


                    } else {
                        // Alert("No Records Found..");
                    }


                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                //Alert("Server Connection Failed");
            }
        });
    }

    private void messageSeenApi() {


        JsonArray jsonArrayschoolstd = new JsonArray();

        for (int i = 0; i < chatList.size(); i++) {
            ChatDataModel data = chatList.get(i);
            if (data.getIsSeen().equals("0")) {
                JsonObject jsonObjectclass = new JsonObject();
                jsonObjectclass.addProperty("ChatId", data.getIdPurchaseOrderConversation());
                jsonObjectclass.addProperty("UserID", data.getUserID());
                jsonArrayschoolstd.add(jsonObjectclass);
            }

        }

        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);
        Call<JsonArray> call = apiService.InsertPOChatSeenBy(jsonArrayschoolstd);

        call.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {


                try {
                    Log.d("Listdemo;Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();

                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                // Alert("Server Connection Failed");
            }
        });
    }


    private void Alert(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ChatScreenActivity.this);
        builder.setTitle(msg);
//        builder.setMessage(reason);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();


    }

}

