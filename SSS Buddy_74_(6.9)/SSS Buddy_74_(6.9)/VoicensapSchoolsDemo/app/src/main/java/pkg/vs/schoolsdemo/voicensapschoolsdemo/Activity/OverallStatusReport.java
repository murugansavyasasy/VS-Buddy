package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.Overallstatusclass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;

public class OverallStatusReport extends AppCompatActivity {
    String userId, Reason;
    int Status;
    Button btn_submit;
    SharedPreferences shpRemember;
    SharedPreferences.Editor ed;
    EditText et_revenuetarget, et_schoolachieved, et_pocschool, et_remarks;
    String revenuetarget, schoolachieved, pocschool, remarks;
    private static final String SH_USERS = "userInfo";
    private static final String SH_USERID = "UserId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_status_report);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        et_revenuetarget = (EditText) findViewById(R.id.overall_status_revenuetarget);
        et_schoolachieved = (EditText) findViewById(R.id.overall_status_schoolachieved);
        et_pocschool = (EditText) findViewById(R.id.overall_status_pocschool);
        et_remarks = (EditText) findViewById(R.id.overall_status_remarks);
        btn_submit = (Button) findViewById(R.id.btnoverallstatus_submit);
        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
        userId = shpRemember.getString(SH_USERID, null);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overallstatusretrofit();
            }
        });

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);



//        if (!isNetworkConnected()) {
            Listoverallstatusretrofit();
//        } else {
//            showToast("Check Your Internet Connection");
//        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void overallstatusretrofit() {
        revenuetarget = et_revenuetarget.getText().toString().trim();
        schoolachieved = et_schoolachieved.getText().toString().trim();
        pocschool = et_pocschool.getText().toString().trim();
        remarks = et_remarks.getText().toString().trim();
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
//        {"LoginID":"40740.0","RevenueTarget":"400000","SchoolsAchieved":"13","POCSchools":"5","Remarks":"rate bargaining"}
        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoginID", userId);
        jsonObject.addProperty("RevenueTarget", revenuetarget);
        jsonObject.addProperty("SchoolsAchieved", schoolachieved);
        jsonObject.addProperty("POCSchools", pocschool);
        jsonObject.addProperty("Remarks", remarks);
        Log.d("login:req", jsonObject.toString());


        Call<JsonArray> call = apiService.Overallstatus(jsonObject);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                try {

//                 {
                    Log.d("Response", response.toString());
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    {
                        Log.d("login:code-res", response.code() + " - " + response.toString());
                        if (response.code() == 200 || response.code() == 201) {
                            Log.d("Response", response.body().toString());

                            JSONArray js = new JSONArray(response.body().toString());
                            if (js.length() > 0) {
                                JSONObject jsonObject = js.getJSONObject(0);

                                Status = jsonObject.getInt("Status");
                                Reason = jsonObject.getString("Message");
//                                userId = jsonObject.getString("LoginID");

                                if (Status == 1) {
//                                    finish();
                                    alert(Reason);
//                                    showToast(Reason);
                                }
                                else{
                                    alertfinish(Reason);
                                }
                            }
                        } else {
                            alertfinish(Reason);
//                            showToast(Reason);
                        }
                    }
                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                alertfinish("Server Connection Failed");
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

//            case R.id.menu_tohome:
//                finish();
//                Intent intent2 = new Intent(Addservices.this, Addaccount.class);
//                startActivity(intent2);//to start the activity
//                return (true);


            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void Listoverallstatusretrofit() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoginID", userId);

        Log.d("Listdemo:req", jsonObject.toString());

        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        Call<JsonArray> call = apiService.Overallstatus(jsonObject);

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
                        JSONObject jsonObjectorgInfo;
                        for (int i = 0; i < jsonArrayorgList.length(); i++) {
                            jsonObjectorgInfo = jsonArrayorgList.getJSONObject(i);

                            Overallstatusclass tempData = new Overallstatusclass();
                            Log.d("Server Response", jsonObjectorgInfo.toString());


                            tempData.setStrtarget(jsonObjectorgInfo.getString("Target"));
                            tempData.setStrsalesperson(jsonObjectorgInfo.getString("salesPersonName"));
                            tempData.setStrliveschool(jsonObjectorgInfo.getString("Liveschools"));
                            tempData.setStrpocschools(jsonObjectorgInfo.getString("POCSchools"));
                            et_revenuetarget.setText(jsonObjectorgInfo.getString("Target"));
                            et_pocschool.setText(jsonObjectorgInfo.getString("POCSchools"));
                            et_schoolachieved.setText(jsonObjectorgInfo.getString("Liveschools"));


                            Log.d("Server Array", jsonObjectorgInfo.toString());


                        }
                    } else {
                        alertfinish("No data Received. Try Again.");
                    }


                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                alertfinish("Server Connection Failed");
            }
        });
    }


//    private void showToast(String msg) {
//        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//    }
    private void alert(String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(OverallStatusReport.this);
        builder.setTitle(reason);
//        builder.setMessage(reason);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();


    }
    private void alertfinish(String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(OverallStatusReport.this);
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

}
