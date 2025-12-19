
package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.android.volley.RequestQueue;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.HomeScreen;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText etusername, etpassword;
    Button loginbtn, clearbtn;
    private ProgressDialog pDialog;
    private static final String SH_LOGIN = "userlogin";
    private static final String SH_USERNAME = "sUsername";
    private static final String SH_PASSWORD = "sPassword";
    private static final String SH_LOGINTYPE = "slogintype";
    private static final String SH_STATUS = "sstatus";
    CheckBox chRemember;
    SharedPreferences shpRemember;
    SharedPreferences.Editor ed;
    String username, password;
    RequestQueue requestQueue;
    int Result;
    //    String Status;
//    String Reason;
    String S_userType;
    String Update = "demoapp";
    String V_IdUser, V_UserName, V_UserTypeId, V_Number, Location, Region, result_login, resultMessage;
    //    String JsonURL = "http://192.168.0.77:8096/SchoolDemoAppService/Service.asmx/";
//    String JsonURL = "http://192.168.0.68:8096/SchoolDemoAppService/Service.asmx/";
    String JsonURL = "http://220.226.2.177:9000/StaffVoiceAnnouncerCloudSchool/Service.asmx/";
    private static final String SH_USERS = "userInfo";
    private static final String SH_USERID = "UserId";

    boolean doubleBackToExitPressedOnce = false;
    int PERMISSION_ALL = 1;
    String S_LoginId;
    String Status = "";
//    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etusername = (EditText) findViewById(R.id.login_username);
        etpassword = (EditText) findViewById(R.id.login_password);

        chRemember = (CheckBox) findViewById(R.id.login_chRememberMe);

        loginbtn = (Button) findViewById(R.id.login_btn);
        clearbtn = (Button) findViewById(R.id.clear_btn);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);


        String[] PERMISSIONS = {Manifest.permission.RECORD_AUDIO, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        if (!isNetworkConnected()) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Login.this);

            dlgAlert.setMessage("Not Connected to Network");
            dlgAlert.setTitle("Error Message");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();

            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {


                            finish();
                        }
                    });
            dlgAlert.show();
        } else {


            shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
            S_LoginId = shpRemember.getString(SH_USERID, "");
            Log.d("schl_loginid", S_LoginId);
            password = shpRemember.getString(SH_PASSWORD, "");
            S_userType = shpRemember.getString(SH_LOGINTYPE, "");
//            shpRemember = getSharedPreferences(SH_LOGIN, MODE_PRIVATE);
            // ed = shpRemember.edit();
            // ed.putBoolean("hasLoggedOut",true);
            //  ed.apply();
            loginbtn.setOnClickListener(this);
            clearbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    clearText();
                }
            });

            getSharedPrefRemember();

        }
    }

    public static boolean hasPermissions(Context context, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        username = (etusername.getText().toString());
        Log.d("sUsername:", username);
        password = (etpassword.getText().toString());
        Log.d("sPassword:", password);
        if (username.isEmpty() || password.isEmpty()) {

            if (username.isEmpty()) {
                etusername.setError("Enter Your EmployeeId");
            }
            if (password.isEmpty()) {
                etpassword.setError("Enter the Password");
            }
        } else if (v.getId() == R.id.login_btn) {

            username = etusername.getText().toString().trim();
            password = etpassword.getText().toString().trim();

            putSharedPrefRemember(username, password);

        }

        loginretrofit();

    }

    private void clearText() {
        etusername.setText("");
        etpassword.setText("");
        etusername.requestFocus();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void getSharedPrefRemember() {

        shpRemember = getSharedPreferences(SH_LOGIN, MODE_PRIVATE);
        username = shpRemember.getString(SH_USERNAME, "");
        Log.d("Username:", username);
        password = shpRemember.getString(SH_PASSWORD, "");
        Log.d("V_Password:", password);
        if (username == null || password == null) {
            Log.d("Remember Password", "No username and password in catch,,,");
        } else {
            etusername.setText(username);
            etpassword.setText(password);
        }
    }

    private void putSharedPrefRemember(String mo, String pass) {
        shpRemember = getSharedPreferences(SH_LOGIN, MODE_PRIVATE);
        ed = shpRemember.edit();

        // ed.putInt("hasLoggedOut", 1);

        if (chRemember.isChecked()) {
            ed.putString(SH_USERNAME, mo);
            ed.putString(SH_PASSWORD, pass);
        } else {
            ed.putString(SH_USERNAME, "");
            ed.putString(SH_PASSWORD, "");
        }
        // ed.putBoolean("hasLoggedIn", true);
        ed.apply();
    }


    @Override
    public void onBackPressed() {
        {
            moveTaskToBack(true);
            return;
        }

    }

    private void loginretrofit() {

        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);
        final JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("IMENumber", "3533");
        jsonObject.addProperty("EmployeeId", username);
        jsonObject.addProperty("Password", password);
        Log.d("login:req", jsonObject.toString());
        Call<JsonObject> call = apiService.Login(jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                try {

//                    if (mProgressDialog.isShowing())
//                        mProgressDialog.dismiss();
//                    {
                    Log.d("Response", response.toString());
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    {
                        Log.d("login:code-res", response.code() + " - " + response.toString());
                        if (response.code() == 200 || response.code() == 201) {
                            Log.d("Response", response.body().toString());

                            JSONObject jsonObject = new JSONObject(response.body().toString());
//                                    Status = jsonObject.getString("result");
//                                    userId = jsonObject.getString("LoginID");
//                                    Logintype = jsonObject.getString("LoginType");
                            V_IdUser = jsonObject.getString("VimsIdUser");
                            V_UserName = jsonObject.getString("VimsUserName");
                            username = jsonObject.getString("VimsEmployeeId");
                            V_UserTypeId = jsonObject.getString("VimsUserTypeId");

                            S_userType = jsonObject.getString("SchooluserType");
                            S_LoginId = jsonObject.getString("SchoolLoginId");

                            V_Number = jsonObject.getString("VimsNumber");
                            Location = jsonObject.getString("Location");
                            Region = jsonObject.getString("Region");
                            result_login = jsonObject.getString("result");


                            if (jsonObject.has("SchoolStatus")) {
                                Status = jsonObject.getString("SchoolStatus");
                            }

                            resultMessage = jsonObject.getString("resultMessage");

                            if (result_login.equals("1")) {

                                shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
                                ed = shpRemember.edit();
                                ed.putString(SH_USERID, S_LoginId);
                                ed.putString(SH_USERNAME, username);
                                ed.putString(SH_PASSWORD, password);
                                ed.putString(SH_LOGINTYPE, S_userType);
                                ed.putString(SH_STATUS, Status);
                                ed.commit();

                                SharedPreference_class.putLogin_detail(Login.this, username, password, V_IdUser, V_UserName, V_UserTypeId, S_userType, S_LoginId, V_Number, Location, Region);
                                SharedPreference_class.putUpdation(Login.this, Update);
                                Log.d("update", Update);


                                if (S_userType.equals("")) {
                                    Log.d("logintype", S_userType);
                                    Alert(resultMessage);
                                } else {
                                    Intent intent = new Intent(Login.this, HomeScreen.class);
                                    startActivity(intent);
//                                        finish();
                                }
                            } else {
                                Alert(resultMessage);
                            }


                        }
                    }
                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());

            }
        });
    }

    private void Alert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setIcon(R.drawable.alert);
        builder.setTitle("Alert!");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }
//    private void showToast(String msg) {
//        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//    }
}
