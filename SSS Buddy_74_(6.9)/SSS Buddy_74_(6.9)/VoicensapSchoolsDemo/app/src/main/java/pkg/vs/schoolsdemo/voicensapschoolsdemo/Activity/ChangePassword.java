package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import com.android.volley.RequestQueue;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;

public class ChangePassword extends AppCompatActivity {

    EditText password, newpassword, confirmpassword;
    Button submit;
//    private static final String SH_LOGIN = "userlogin";
//    private static final String SH_USERID = "UserId";
//    private static final String SH_USERS = "userInfo";
//    private static final String SH_PASSWORD = "sPassword";
//    private static final String SH_LOGINTYPE = "slogintype";
//    private static final String SH_USERNAME = "sUsername";
//    SharedPreferences shpRemember;
//    SharedPreferences.Editor ed;
//    int Status;
    String userId, Result;
//    String JsonURL = "http://192.168.0.68:8096/SchoolDemoAppService/Service.asmx/";
//    String JsonURL =   "http://220.226.2.177:9000/StaffVoiceAnnouncerCloudSchool/Service.asmx/";
    RequestQueue requestQueue;
    private ProgressDialog pDialog;
    String stroldpassword,strnewpassword,strconfirmpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        password = (EditText) findViewById(R.id.schoololdpassword);
        newpassword = (EditText) findViewById(R.id.schoolnewpassword);
        confirmpassword = (EditText) findViewById(R.id.schoolconfirmpassword);
        submit = (Button) findViewById(R.id.demosubmit1);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        userId= SharedPreference_class.getUserid(ChangePassword.this);
        Log.d("iduder",userId);
//        Logintype= SharedPreference_class.getShSchlUsertype(ChangePassword.this);
//        username=SharedPreference_class.getShV_Username(ChangePassword.this);

//        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
//        userId = shpRemember.getString(SH_USERID, null);
//        Logintype=shpRemember.getString(SH_LOGINTYPE, null);
//        username=shpRemember.getString(SH_USERNAME, null);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("btn_submit", String.valueOf(submit));
                stroldpassword = password.getText().toString();
                Log.d("oldpassword",stroldpassword);
                strnewpassword = newpassword.getText().toString();
                Log.d("newpassword",strnewpassword);
                strconfirmpassword = confirmpassword.getText().toString();
                Log.d("confirmpassword",strconfirmpassword);


//                PasswordValidator validator = new PasswordValidator();


                if (stroldpassword.isEmpty()|| strnewpassword.isEmpty() || strconfirmpassword.isEmpty()) {
                    if (stroldpassword.isEmpty()) {
                        password.setError("Enter Your old password");
                    }
                    if (strnewpassword.isEmpty()) {
                        newpassword.setError("Enter Your new password");
                    }
                    if (strconfirmpassword.isEmpty()) {
                        confirmpassword.setError("Enter Password again");
                    }


                } else if (strnewpassword.equals(strconfirmpassword)) {
                        Log.d("api","apicall");
                        Changepasswordretrofit();

                    } else {
                        confirmpassword.setError("passwords doesnot match");
                    }


//                        Toast.makeText(ChangePassword.this, "Password Changed", Toast.LENGTH_SHORT).show();
//                        finish();
//                        if (v.getId() == R.id.demosubmit1) {
//
//                            Log.d("sucess", String.valueOf(submit));
//                            strnewpassword = newpassword.getText().toString().trim();
//                            stroldpassword = password.getText().toString().trim();
////                            submit.setEnabled(false);
//
////                            changePassword();
////                            Changepasswordretrofit();
////                            if (!isNetworkConnected()) {
//                                Changepasswordretrofit();
////                            } else {
////                                showToast("Check Your Internet Connection");
////                            }
//                        }






//                }
            }
        });
    }

//    void changePassword() {
//        String strServerUrl = JsonURL + "DemoChangeNewPassword?LoginID=" + userId + "&&OldPwd=" + OldPassword + "&&NewPwd=" + NewPassword;
//
//
//        Log.d("URL", strServerUrl);
//        if (isNetworkConnected()) {
//            requestQueue = Volley.newRequestQueue(ChangePassword.this);
//            StringRequest obreq = new StringRequest(Request.Method.GET, strServerUrl, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    try {
//                        hidePDialog();
//                        submit.setEnabled(true);
//                        JSONObject obj = new JSONObject(response);
//                        Log.d("Server Response", obj.toString());
//                        JSONArray arrrr = obj.getJSONArray("DemoChangeNewPassword");
//                        JSONObject obj1 = arrrr.getJSONObject(0);
//                        Status = obj1.getInt("Status");
//                        Reason = obj1.getString("Message");
//
//                        if (Integer.parseInt(obj1.getString("Status")) == 1)
//                        {
//                            Toast.makeText(ChangePassword.this, Reason, Toast.LENGTH_SHORT).show();
//
////                            shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
////                            ed = shpRemember.edit();
////                            ed.clear();
////                            ed.commit();
////                            shpRemember = getSharedPreferences(SH_LOGIN, MODE_PRIVATE);
////                            ed = shpRemember.edit();
////                            ed.putString(SH_PASSWORD, "");
////                            ed.commit();
////                        Intent i = new Intent(ChangePassword.this, Mainmenu.class);
////                        startActivity(i);
//                            finish();
//                            Intent i = new Intent(ChangePassword.this, Login.class);
//                           startActivity(i);
//                        }
//                        else
//                        {
//                            showToast(Reason);
////                            Toast.makeText(ChangePassword.this, Reason, Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    } catch (Exception e) {
//                        hidePDialog();
//                        submit.setEnabled(true);
//
//                        Toast.makeText(ChangePassword.this, "Exception", Toast.LENGTH_SHORT).show();
//                        Log.d("Exception", "ChangePassword-changepassword()-Message:" + e.getMessage());
//                    }
//                }
//            },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            hidePDialog();
//                            submit.setEnabled(true);
//                             String msg="Network Problem - Failure";
//                                showToast(msg);
////                            Toast.makeText(ChangePassword.this, "Network Problem - Failure", Toast.LENGTH_SHORT).show();
//                            Log.d("OnErrorResponse", "ChangePassword-changepassword()-Message:" + error.getMessage());
//                        }
//                    });
//
//            requestQueue.add(obreq);
//
//            pDialog = new ProgressDialog(ChangePassword.this);
//            pDialog.setMessage("Loading...");
//            pDialog.setCancelable(false);
//            pDialog.show();
//        } else {
//            submit.setEnabled(true);
//            Toast.makeText(this, "Check Your Internet connection", Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
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
    private void Changepasswordretrofit() {

        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);

//        Log.d("Changepassword:req", jsonObject.toString());

        Call<JsonArray> call = apiService.ChangePassword(userId,stroldpassword,strnewpassword);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                try {

                    Log.d("Changepassword:Response", response.toString());
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    {
                        Log.d("Changepassword:code-res", response.code() + " - " + response.toString());
                        if (response.code() == 200 || response.code() == 201) {
                            Log.d("Response", response.body().toString());

                            JSONArray js = new JSONArray(response.body().toString());
                            if (js.length() > 0) {
                                JSONObject jsonObject = js.getJSONObject(0);

                                String result=jsonObject.getString("result");
                                 String resultMessage=jsonObject.getString("resultMessage");

                                if (Integer.parseInt(result) == 1) {
                                    alert(result,resultMessage);
                                }
                                else {
                                    alert(result,resultMessage);

                                }
                            }
                        } else {
                            Alert("No data Received");
                        }
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

    private void alert (String result,String msg) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
            builder.setTitle(msg);
//            builder.setMessage(month);
            builder.setCancelable(false);

        if(Integer.parseInt(result)==1){
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent i = new Intent(ChangePassword.this, Login.class);
                    startActivity(i);
                }
            });

            builder.create().show();

        }else{

            builder.setTitle(msg);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();

        }

        }

    private void Alert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
        builder.setTitle(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();


//        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }



    }


