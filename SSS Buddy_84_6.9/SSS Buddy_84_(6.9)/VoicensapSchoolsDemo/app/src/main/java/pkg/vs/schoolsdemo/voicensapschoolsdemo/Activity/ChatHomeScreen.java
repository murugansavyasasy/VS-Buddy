package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter.ChatAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.RequirementModel;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.UnreadChatModel;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;

public class ChatHomeScreen extends AppCompatActivity {
    String DemoId, txtMessage, content, UserId = "";
    SharedPreferences shpRemember;

    private static final String SH_USERS = "userInfo";
    private static final String SH_USERID = "UserId";
    RecyclerView rvRecyle;

    ChatAdapter madapter;
    private ArrayList<RequirementModel> chatList = new ArrayList<>();
    TextView lblExpiredDays,
            lblTenDays,
            lblThirtyDays,
            lblSixtyDays;
    TextView lblExpiredDaysToview,
            lblTenDaysToview,
            lblThirtyDaysToview,
            lblSixtyDaysToview;

    String sixty = "", thirty = "", ten = "", expired = "";
    String vimsUserTypeId = "";

    Call<JsonArray> call;
    LinearLayout lnrSixtyDays,
            lnrThirtyDays,
            lnrTenDays,
            lnrExpiredDays;

    TextView lblSixtyChatCount,
            lblThirtyChatCount,
            lblTenChatCount,
            lblExpiredChatCount;

    int sixtyDaysCount = 0, tenDaysCount = 0, expiredCount = 0, thirtyDaysCount = 0;

    private ArrayList<UnreadChatModel> SixtyDaysUnreadChatList = new ArrayList<>();
    private ArrayList<UnreadChatModel> TenDaysUnreadChatList = new ArrayList<>();
    private ArrayList<UnreadChatModel> ThirtyDaysUnreadChatList = new ArrayList<>();
    private ArrayList<UnreadChatModel> ExpiredUnreadChatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_home_screen);

        lblExpiredDays = (TextView) findViewById(R.id.lblExpiredDays);
        lblTenDays = (TextView) findViewById(R.id.lblTenDays);
        lblThirtyDays = (TextView) findViewById(R.id.lblThirtyDays);
        lblSixtyDays = (TextView) findViewById(R.id.lblSixtyDays);

        lblExpiredDaysToview = (TextView) findViewById(R.id.lblExpiredDaysToview);
        lblTenDaysToview = (TextView) findViewById(R.id.lblTenDaysToview);
        lblThirtyDaysToview = (TextView) findViewById(R.id.lblThirtyDaysToview);
        lblSixtyDaysToview = (TextView) findViewById(R.id.lblSixtyDaysToview);

        lnrSixtyDays = (LinearLayout) findViewById(R.id.lnrSixtyDays);
        lnrThirtyDays = (LinearLayout) findViewById(R.id.lnrThirtyDays);
        lnrTenDays = (LinearLayout) findViewById(R.id.lnrTenDays);
        lnrExpiredDays = (LinearLayout) findViewById(R.id.lnrExpiredDays);


        lblSixtyChatCount = (TextView) findViewById(R.id.lblSixtyChatCount);
        lblThirtyChatCount = (TextView) findViewById(R.id.lblThirtyChatCount);
        lblTenChatCount = (TextView) findViewById(R.id.lblTenChatCount);
        lblExpiredChatCount = (TextView) findViewById(R.id.lblExpiredChatCount);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);
        vimsUserTypeId = SharedPreference_class.getSh_v_Usertype(ChatHomeScreen.this);
        UserId = SharedPreference_class.getUserid(ChatHomeScreen.this);


        getCountForAdminAndSalepersionalso();

        lnrSixtyDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SixtyDaysUnreadChatList.size() > 0) {
                    openUnreadChatScreen(SixtyDaysUnreadChatList);
                }

            }
        });

        lnrThirtyDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThirtyDaysUnreadChatList.size() > 0) {
                    openUnreadChatScreen(ThirtyDaysUnreadChatList);
                }
            }
        });
        lnrTenDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TenDaysUnreadChatList.size() > 0) {
                    openUnreadChatScreen(TenDaysUnreadChatList);
                }
            }
        });
        lnrExpiredDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ExpiredUnreadChatList.size() > 0) {
                    openUnreadChatScreen(ExpiredUnreadChatList);
                }
            }
        });


        lblExpiredDaysToview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openMemberScreen("0");


            }
        });
        lblTenDaysToview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMemberScreen("10");

            }
        });
        lblThirtyDaysToview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMemberScreen("30");

            }
        });
        lblSixtyDaysToview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMemberScreen("60");

            }
        });


    }


    private void openUnreadChatScreen(ArrayList<UnreadChatModel> UnreadChatList) {
        Intent i = new Intent(ChatHomeScreen.this, UnreadCountChatScreen.class);
        i.putExtra("unreadList", UnreadChatList);
        startActivity(i);
    }

    private void openMemberScreen(String count) {
        Intent membersScreen = new Intent(ChatHomeScreen.this, MemberListActivity.class);
        membersScreen.putExtra("Count", count);
        startActivity(membersScreen);
    }


    private void GetPObyValidityForAdminAndSalesPersionAlso(String count) {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("ValidityPeriod", count);
        jsonObject.addProperty("IdUser", UserId);


        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);
        if (vimsUserTypeId.equals("13") || vimsUserTypeId.equals("14")) {
            call = apiService.GetPObyValidity(jsonObject);
        } else {
            call = apiService.GetPObyValidityforAdmin(jsonObject);
        }


        call.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                try {
                    Log.d("Listdemo;Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();

                    JSONArray jsonArrayorgList = new JSONArray(strResponse);

                    if (jsonArrayorgList.length() > 0) {
                        JSONObject data = jsonArrayorgList.getJSONObject(0);

                        sixty = data.getString("SixtyDays");
                        thirty = data.getString("ThirtyDays");
                        ten = data.getString("TenDays");
                        expired = data.getString("Expired");


                    } else {
                        Alert("Server Response Failed.");
                    }


                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                Alert("Server Connection Failed");
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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


    private void getCountForAdminAndSalepersionalso() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);

        if (vimsUserTypeId.equals("13") || vimsUserTypeId.equals("14")) {
            call = apiService.GetPOByValidityCountforSalesPerson(UserId);
        } else {
            call = apiService.GetGetPOByValidityCountforAdmin(UserId);
        }

        call.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                try {
                    Log.d("Listdemo;Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();

                    JSONArray jsonArrayorgList = new JSONArray(strResponse);

                    if (jsonArrayorgList.length() > 0) {
                        JSONObject data = jsonArrayorgList.getJSONObject(0);

                        sixty = data.getString("SixtyDays");
                        thirty = data.getString("ThirtyDays");
                        ten = data.getString("TenDays");
                        expired = data.getString("Expired");

                        lblExpiredDays.setText("Expired " + expired + " Schools");
                        lblTenDays.setText("10 Days to go " + ten + " Schools");
                        lblThirtyDays.setText("30 Days to go " + thirty + " Schools");
                        lblSixtyDays.setText("60 Days to go " + sixty + " Schools");

                        unReadChatsCount();


                    } else {
                        Alert("No Records");
                    }


                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                Alert("Server Connection Failed");
            }
        });
    }

    private void unReadChatsCount() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);


        if (vimsUserTypeId.equals("13") || vimsUserTypeId.equals("14")) {
            call = apiService.GetUnreadPOChatCountforSalesPerson(UserId);
        } else {
            call = apiService.GetUnreadPOChatCountforAdmin(UserId);
        }

        call.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                try {
                    Log.d("Circularlist:Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();

                    JSONArray jsonArrayorgList = new JSONArray(strResponse);

                    if (jsonArrayorgList.length() > 0) {
                        JSONObject temp;
                        SixtyDaysUnreadChatList.clear();
                        TenDaysUnreadChatList.clear();
                        ThirtyDaysUnreadChatList.clear();
                        ExpiredUnreadChatList.clear();

                        for (int i = 0; i < jsonArrayorgList.length(); i++) {
                            temp = jsonArrayorgList.getJSONObject(i);


                            String unreadCount = temp.getString("UnreadCount");
                            String validRemaining = temp.getString("ValidRemaining");

                            if (validRemaining.equals("10")) {
                                UnreadChatModel data = new UnreadChatModel(temp.getString("POId"),
                                        temp.getString("UnreadCount"), temp.getString("CustomerName"),
                                        temp.getString("salesPersonId"), temp.getString("PONumber"),
                                        temp.getString("ValidRemaining"));
                                tenDaysCount = tenDaysCount + Integer.parseInt(unreadCount);
                                TenDaysUnreadChatList.add(data);
                            } else if (validRemaining.equals("30")) {
                                UnreadChatModel data = new UnreadChatModel(temp.getString("POId"),
                                        temp.getString("UnreadCount"), temp.getString("CustomerName"),
                                        temp.getString("salesPersonId"), temp.getString("PONumber"),
                                        temp.getString("ValidRemaining"));
                                thirtyDaysCount = thirtyDaysCount + Integer.parseInt(unreadCount);
                                ThirtyDaysUnreadChatList.add(data);
                            } else if (validRemaining.equals("60")) {
                                UnreadChatModel data = new UnreadChatModel(temp.getString("POId"),
                                        temp.getString("UnreadCount"), temp.getString("CustomerName"),
                                        temp.getString("salesPersonId"), temp.getString("PONumber"),
                                        temp.getString("ValidRemaining"));
                                sixtyDaysCount = sixtyDaysCount + Integer.parseInt(unreadCount);
                                SixtyDaysUnreadChatList.add(data);
                            } else {
                                UnreadChatModel data = new UnreadChatModel(temp.getString("POId"),
                                        temp.getString("UnreadCount"), temp.getString("CustomerName"),
                                        temp.getString("salesPersonId"), temp.getString("PONumber"),
                                        temp.getString("ValidRemaining"));
                                expiredCount = expiredCount + Integer.parseInt(unreadCount);
                                ExpiredUnreadChatList.add(data);
                            }


                        }

                        lblSixtyChatCount.setText(String.valueOf(sixtyDaysCount));
                        lblThirtyChatCount.setText(String.valueOf(thirtyDaysCount));
                        lblTenChatCount.setText(String.valueOf(tenDaysCount));
                        lblExpiredChatCount.setText(String.valueOf(expiredCount));

                    } else {

                    }

                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                // alert("Server Connection Failed");
            }
        });
    }


    private void Alert(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ChatHomeScreen.this);
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


