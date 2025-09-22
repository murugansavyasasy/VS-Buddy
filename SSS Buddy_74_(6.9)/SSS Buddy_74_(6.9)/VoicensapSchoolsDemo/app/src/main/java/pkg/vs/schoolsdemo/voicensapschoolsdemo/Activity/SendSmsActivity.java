package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;

import static pkg.vs.schoolsdemo.voicensapschoolsdemo.util.Util_common.maxHomeWorkSMSCount;

public class SendSmsActivity extends AppCompatActivity {

    TextView genText_count, genText_msgcount;
    EditText general_content, genText_txtmessage;
    Button Send;
    String DemoId, txtMessage, content, LoginID;
    SharedPreferences shpRemember;

    private static final String SH_USERS = "userInfo";
    private static final String SH_USERID = "UserId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);
        genText_txtmessage = (EditText) findViewById(R.id.genText_txtmessage);
        general_content = (EditText) findViewById(R.id.general_content);


        genText_count = (TextView) findViewById(R.id.genText_count);
        genText_msgcount = (TextView) findViewById(R.id.genText_msgcount);

        Send = (Button) findViewById(R.id.Send);


        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
        LoginID = shpRemember.getString(SH_USERID, null);


        genText_txtmessage.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxHomeWorkSMSCount)});
        genText_txtmessage.addTextChangedListener(mTextEditorWatcher);

        String totcount = String.valueOf(maxHomeWorkSMSCount);
        genText_count.setText(totcount);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Log.d("ID", extras.toString());
            DemoId = extras.getString("ID");

        }


        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMessage = genText_txtmessage.getText().toString();
                content = general_content.getText().toString();

                if (txtMessage.length() != 0 && content.length() != 0) {
                    SendSmsApi(txtMessage, content, DemoId);
                }
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

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            genText_msgcount.setText("" + (maxHomeWorkSMSCount - (s.length())));
        }

        public void afterTextChanged(Editable s) {

        }
    };

    private void SendSmsApi(String txtMessage, String content, String demoId) {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("SMSContent", content);
        jsonObject.addProperty("DemoId", demoId);
        jsonObject.addProperty("LoginID", LoginID);
        jsonObject.addProperty("Description", txtMessage);

        Log.d("Listdemo:req", jsonObject.toString());

        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        Call<JsonArray> call = apiService.DemoSMS(jsonObject);

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

                        String status = data.getString("Status");
                        String Message = data.getString("Message");

                        if (status.equals("1")) {
                            alert(Message);
//                            showToast(Message);
//                            finish();
                        } else {
                            alert(Message);
//                            showToast(Message);
                        }
                    }
                    else {
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

//    private void showToast(String msg) {
//        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//    }
    private void alert(String reason) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SendSmsActivity.this);
        builder.setTitle(reason);
//        builder.setMessage(reason);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();


    }
    private void Alert(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SendSmsActivity.this);
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
