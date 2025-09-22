package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.Approvepocclass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;

public class Pocapprovesublist extends AppCompatActivity {

    TextView textPOCid, textPOCschoolname, textPOCSchooladdr, textPOCCity, textPOCcallcredit, textPOCsmscredit,
            textPOCphno, textPOCemailid, textPOCprinciname, textPOCmobno, textPOCfromdate, textPOCtodate, textPOCremarks,
            textPOCmodule, textPOCdbrequried, textPOCdemo, textPOCscope, tvsmscaption,tvpurchasetype;
    Button approve_btn, decline_btn;
    ImageView img_edit;
    private static final String SH_PASSWORD = "sPassword";
    private static final String SH_USERS = "userInfo";
    private static final String SH_USERID = "UserId";
    //    String serverPath = "http://192.168.0.77:8096/SchoolDemoAppService/Service.asmx/";
    String serverPath = "http://220.226.2.177:9000/StaffVoiceAnnouncerCloudSchool/Service.asmx/";
    private ProgressDialog pDialog;
    RequestQueue requestQueue;
    String Password, userId, message, tid, Result;
    SharedPreferences shpRemember;
    SharedPreferences.Editor ed;
    String strSchoolname, strAddress, strmodule, strMobilenumber, strschoolid, strcity, strEmailId,
            strcontactperson, strcaption, strcallcost, strsmscost, strFromdate, strTodate, strcallcredits,
            strsmscredits, strunlimitedcalls, strunlimitedsms, strsmscaption,strpurchasetype;
    int iRequestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocapprovesublist);

        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
        userId = shpRemember.getString(SH_USERID, null);
        Password = shpRemember.getString(SH_PASSWORD, null);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        final Approvepocclass POCdata = (Approvepocclass) getIntent().getSerializableExtra("Approvepocclass");
        iRequestCode= getIntent().getExtras().getInt("REQUEST_CODE", 0);
        textPOCid = (TextView) findViewById(R.id.POCid1);
        textPOCschoolname = (TextView) findViewById(R.id.POClistschoolname1);
        textPOCSchooladdr = (TextView) findViewById(R.id.POCSchooladdr1);
        textPOCCity = (TextView) findViewById(R.id.POCCity1);
        textPOCphno = (TextView) findViewById(R.id.POCphno1);
        textPOCemailid = (TextView) findViewById(R.id.POCemailid1);
        textPOCprinciname = (TextView) findViewById(R.id.POCprinciname1);
        textPOCmobno = (TextView) findViewById(R.id.POCmobno1);
        textPOCfromdate = (TextView) findViewById(R.id.POCfromdate1);
        textPOCtodate = (TextView) findViewById(R.id.POCtodate1);
        textPOCremarks = (TextView) findViewById(R.id.POCremarks1);
        textPOCmodule = (TextView) findViewById(R.id.POCmodule1);
        textPOCdbrequried = (TextView) findViewById(R.id.POCdbrequried1);
        textPOCdemo = (TextView) findViewById(R.id.POCdemo1);
        textPOCscope = (TextView) findViewById(R.id.POCscope1);
        tvsmscaption = (TextView) findViewById(R.id.POCsmscaption);
        tvpurchasetype=(TextView)findViewById(R.id.POCpurchasetype1);
        textPOCcallcredit = (TextView) findViewById(R.id.POCcallcredits1);
        textPOCsmscredit = (TextView) findViewById(R.id.POCsmscredits1);
        approve_btn = (Button) findViewById(R.id.btnapprove_poc);
        decline_btn = (Button) findViewById(R.id.btndecline_poc);
        img_edit = (ImageView) findViewById(R.id.img_editpoc);


        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pocapprovesublist.this, EditPOC.class);
                i.putExtra("POCid", POCdata.getStrPOCid());
                i.putExtra("POCschoolname", POCdata.getStrPOCschoolname());
                i.putExtra("POCcity", POCdata.getStrPOCcity());
                i.putExtra("POCaddress", POCdata.getStrPOCSchooladdr());
//                i.putExtra("POCphno", POCdata.getStrPOCphno());
                i.putExtra("POCemailid", POCdata.getStrPOCemailid());
                i.putExtra("POCprinciname", POCdata.getStrPOCprinciname());
                i.putExtra("POCmobno", POCdata.getStrPOCmobno());
                i.putExtra("POCfromdate", POCdata.getStrPOCfromdate());
                i.putExtra("POCtodate", POCdata.getStrPOCtodate());
                i.putExtra("POCCallcredits", POCdata.getCallcredits());
                i.putExtra("POCSmscredit", POCdata.getSmscredit());
                i.putExtra("POCmodule", POCdata.getStrPOCmodule());
                i.putExtra("POCcaption", POCdata.getStrPOCcaption());
                i.putExtra("POCpincode", POCdata.getStrPOCpincode());
                i.putExtra("POCpurchasetype",POCdata.getStrPOCpurchasetype());


//                i.putExtra("POCid",POCdata.getSmscredit());
                startActivityForResult(i, iRequestCode);

            }
        });
        textPOCid.setText(POCdata.getStrPOCid());
        textPOCschoolname.setText(POCdata.getStrPOCschoolname());
        textPOCSchooladdr.setText(POCdata.getStrPOCSchooladdr());
        textPOCphno.setText(POCdata.getStrPOCmobno());
        textPOCemailid.setText(POCdata.getStrPOCemailid());
        textPOCCity.setText(POCdata.getStrPOCcity());
        textPOCprinciname.setText(POCdata.getStrPOCprinciname());
        textPOCmobno.setText(POCdata.getStrPOCmobno());
        textPOCfromdate.setText(POCdata.getStrPOCfromdate());
        textPOCtodate.setText(POCdata.getStrPOCtodate());
        textPOCcallcredit.setText(POCdata.getCallcredits());
        textPOCsmscredit.setText(POCdata.getSmscredit());
        tvsmscaption.setText(POCdata.getStrPOCcaption());
        textPOCmodule.setText(POCdata.getStrPOCmodule());
        tvpurchasetype.setText(POCdata.getStrPOCpurchasetype());
//        textPOCdbrequried.setText(POCdata.getStrPOCdbrequried());
//        textPOCscope.setText(POCdata.getStrPOCscope());
//        textPOCdemo.setText(POCdata.getStrPOCdemo());
        strSchoolname = POCdata.getStrPOCschoolname();
        strschoolid = POCdata.getStrPOCid();
        strAddress = POCdata.getStrPOCSchooladdr();
        strMobilenumber = POCdata.getStrPOCmobno();
        strcity = POCdata.getStrPOCcity();
        strEmailId = POCdata.getStrPOCemailid();
        strcontactperson = POCdata.getStrPOCprinciname();
//        strcaption = POCcaption.getText().toString();
//        strcallcost = POCcallcost.getText().toString();
//        strsmscost = POCsmscost.getText().toString();
        strFromdate = POCdata.getStrPOCfromdate();
        strTodate = POCdata.getStrPOCtodate();
        strmodule = POCdata.getStrPOCmodule();
        strcallcredits = POCdata.getCallcredits();
        strsmscredits = POCdata.getSmscredit();
        strcaption = POCdata.getStrPOCcaption();

        if (strcallcredits.equals("Unlimited")) {
            strunlimitedcalls = "1";
        } else {
            strunlimitedcalls = "0";
        }

        if (strsmscredits.equals("Unlimited")) {
            strunlimitedsms = "1";
        } else {
            strunlimitedsms = "0";
        }

        approve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert1();


            }
        });
        decline_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert2();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tid = extras.getString("VALUE");

        }

    }
    private void backToResultActvity(String msg) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("MESSAGE", msg);
        setResult(iRequestCode, returnIntent);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == iRequestCode) {
            String message = data.getStringExtra("MESSAGE");
            if (message.equals("SENT")) {
                backToResultActvity(message);
            }
        }
    }
    private void alert1() {
        Log.d("password", Password);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Pocapprovesublist.this);

        alertDialog.setTitle("Alert");

        alertDialog.setMessage("Are you sure To Approve POC?");
//

        alertDialog.setIcon(android.R.drawable.ic_dialog_info);

        alertDialog.setPositiveButton("Approve",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        Result="1";
                        alertapprove();

                    }
                });

//        alertDialog.setNegativeButton("Decline",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
////                        dialog.cancel();
////                        Result="0";
//                        alertdecline();
//                    }
//                });
        alertDialog.show();
    }

    private void alert2() {
        Log.d("password", Password);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Pocapprovesublist.this);

        alertDialog.setTitle("Alert");

        alertDialog.setMessage("Are you sure To Decline POC?");
//

        alertDialog.setIcon(android.R.drawable.ic_dialog_info);

//        alertDialog.setPositiveButton("Approve",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
////                        Result="1";
//                        alertapprove();
//
//                    }
//                });

        alertDialog.setNegativeButton("Decline",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                        Result="0";
                        alertdecline();
                    }
                });
        alertDialog.show();
    }

    private void alertapprove() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Pocapprovesublist.this);

        alertDialog.setTitle("Password Alert");

        alertDialog.setMessage("Re-Enter Your Vims Password");
        final EditText input = new EditText(Pocapprovesublist.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        alertDialog.setView(input);

        alertDialog.setIcon(android.R.drawable.ic_dialog_info);

        alertDialog.setPositiveButton("Submit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("test", input.getText().toString() + Password + Result);
                        if ((input.getText().toString()).equals(Password)) {
//                            Log.d("testResult", Result);
//                         approvePOC();
//                            if (!isNetworkConnected()) {
                            approve();
//                            } else {
//                                showToast("Check Your Internet Connection");
//                            }
//                            approve();
                        }

//                        else if ((input.getText().toString()).equals(Password))
//                        {
//                            Log.d("testResult", Result);
////                            approvePOC();
//                            approve();
//
//                        }
                        else {
                            input.getText().clear();
                            alert("Password Incorrect Enter the Password again");

//                            Toast.makeText(getApplicationContext(), "Password Incorrect Enter the Password again", Toast.LENGTH_SHORT).show();
                            alertapprove();

                        }
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    private void alertdecline() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Pocapprovesublist.this);

        alertDialog.setTitle("Password Alert");

        alertDialog.setMessage("Re-Enter Your Password");
        final EditText input = new EditText(Pocapprovesublist.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        alertDialog.setView(input);

        alertDialog.setIcon(android.R.drawable.ic_dialog_info);

        alertDialog.setPositiveButton("Submit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("test", input.getText().toString() + Password + Result);
                        if ((input.getText().toString()).equals(Password)) {
//                            Log.d("testResult", Result);
//                         approvePOC();
//                            decline();

//                            if (!isNetworkConnected()) {
                            decline();
//                            } else {
//                                showToast("Check Your Internet Connection");
//                            }
//                            decline();
                        }

//                        else if ((input.getText().toString()).equals(Password))
//                        {
//                            Log.d("testResult", Result);
////                            approvePOC();
//                            decline();

//                        }
                        else {
                            input.getText().clear();
                            alert("Password Incorrect Enter the Password again");
//                            Toast.makeText(getApplicationContext(), "Password Incorrect Enter the Password again", Toast.LENGTH_SHORT).show();
                            alertdecline();

                        }
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    void approvePOC() {
        String strServerUrl = serverPath + "DemoApprovePOC?LoginID=" + userId + "&&TID=" + tid + "&Result=" + Result;


        Log.d("URL", strServerUrl);
        if (isNetworkConnected()) {
            requestQueue = Volley.newRequestQueue(Pocapprovesublist.this);
            StringRequest obreq = new StringRequest(Request.Method.GET, strServerUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        hidePDialog();
                        approve_btn.setEnabled(true);
                        JSONObject obj = new JSONObject(response);
                        Log.d("Server Response", obj.toString());
                        JSONArray arrrr = obj.getJSONArray("ApprovePOC");
                        JSONObject obj1 = arrrr.getJSONObject(0);
                        message = obj1.getString("Message");


                        if (Integer.parseInt(obj1.getString("Status")) == 1) {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Pocapprovesublist.this);

                            alertDialog.setTitle("Alert");

                            alertDialog.setMessage(message + " Press OK to exit.");

                            alertDialog.setIcon(android.R.drawable.ic_dialog_info);

                            alertDialog.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                            Toast.makeText(Pocapprovesublist.this, "" + message, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            alertDialog.show();
                        } else {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Pocapprovesublist.this);

                            alertDialog.setTitle("Alert");

                            alertDialog.setMessage(message);

                            alertDialog.setIcon(android.R.drawable.ic_dialog_info);

                            alertDialog.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                            Toast.makeText(Pocapprovesublist.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            alertDialog.show();
                        }


                    } catch (Exception e) {
                        hidePDialog();
                        approve_btn.setEnabled(true);
                        alert("Exception");
//                        Toast.makeText(Pocapprovesublist.this, "Exception", Toast.LENGTH_SHORT).show();
                        Log.d("Exception", "ChangePassword-changepassword()-Message:" + e.getMessage());
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hidePDialog();
                            approve_btn.setEnabled(true);
                            alert("Network Problem - Failure");
//                            Toast.makeText(Pocapprovesublist.this, "Network Problem - Failure", Toast.LENGTH_SHORT).show();
                            Log.d("OnErrorResponse", "ChangePassword-changepassword()-Message:" + error.getMessage());
                        }
                    });

            requestQueue.add(obreq);

            pDialog = new ProgressDialog(Pocapprovesublist.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        } else {
            approve_btn.setEnabled(true);
            alert("Check Your Internet connection");


//            Toast.makeText(this, "Check Your Internet connection", Toast.LENGTH_SHORT).show();
        }
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

    private void approve() {

        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();


        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoginId", userId);
        jsonObject.addProperty("schoolId", strschoolid);
        jsonObject.addProperty("InstitutionName", strSchoolname);
        jsonObject.addProperty("Address", strAddress);
        jsonObject.addProperty("City", strcity);
        jsonObject.addProperty("ContactNumber1", strMobilenumber);
        jsonObject.addProperty("ContactPerson1", strcontactperson);
        jsonObject.addProperty("Email", strEmailId);
        jsonObject.addProperty("Modules", strmodule);
        jsonObject.addProperty("caption", strcaption);
        jsonObject.addProperty("peroidFrom", strFromdate);
        jsonObject.addProperty("peroidTo", strTodate);
        jsonObject.addProperty("processType", "approve");
        jsonObject.addProperty("isUnlimitedCalls", strunlimitedcalls);
        jsonObject.addProperty("isUnlimitedSMS", strunlimitedsms);
        jsonObject.addProperty("callCost", strcallcost);
        jsonObject.addProperty("smsCost", strsmscost);
        jsonObject.addProperty("LoginName", "");
        jsonObject.addProperty("password", "");

        Log.d("createdemo:req", jsonObject.toString());
        Call<JsonArray> call = apiService.Approveordecline(jsonObject);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                try {
                    Log.d("Response", response.toString());
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    {
                        Log.d("createdemo:code-res", response.code() + " - " + response.toString());
                        if (response.code() == 200 || response.code() == 201) {
                            Log.d("Response", response.body().toString());
                            JSONArray js = new JSONArray(response.body().toString());
                            if (js.length() > 0) {
                                JSONObject jsonObject = js.getJSONObject(0);
                                message = jsonObject.getString("Message");
                                if (Integer.parseInt(jsonObject.getString("Status")) == 1) {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Pocapprovesublist.this);

                                    alertDialog.setTitle("Alert");

                                    alertDialog.setMessage(message + " Press OK to exit.");

                                    alertDialog.setIcon(android.R.drawable.ic_dialog_info);

                                    alertDialog.setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
//                                                    Toast.makeText(Pocapprovesublist.this, "" + message, Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                    alertDialog.show();
                                } else {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Pocapprovesublist.this);

                                    alertDialog.setTitle("Alert");

                                    alertDialog.setMessage(message);

                                    alertDialog.setIcon(android.R.drawable.ic_dialog_info);

                                    alertDialog.setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                    Toast.makeText(Pocapprovesublist.this, message, Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                    alertDialog.show();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                alert("Server Connection Failed");
            }
        });
    }


    private void decline() {

        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();


        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoginId", userId);
        jsonObject.addProperty("schoolId", strschoolid);
        jsonObject.addProperty("InstitutionName", strSchoolname);
        jsonObject.addProperty("Address", strAddress);
        jsonObject.addProperty("City", strcity);
        jsonObject.addProperty("ContactNumber1", strMobilenumber);
        jsonObject.addProperty("ContactPerson1", strcontactperson);
        jsonObject.addProperty("Email", strEmailId);
        jsonObject.addProperty("Modules", strmodule);
        jsonObject.addProperty("caption", strcaption);
        jsonObject.addProperty("peroidFrom", strFromdate);
        jsonObject.addProperty("peroidTo", strTodate);
        jsonObject.addProperty("processType", "decline");
        jsonObject.addProperty("isUnlimitedCalls", strunlimitedcalls);
        jsonObject.addProperty("isUnlimitedSMS", strunlimitedsms);
        jsonObject.addProperty("callCost", strcallcost);
        jsonObject.addProperty("smsCost", strsmscost);
        jsonObject.addProperty("LoginName", "");
        jsonObject.addProperty("password", "");

        Log.d("createdemo:req", jsonObject.toString());
        Call<JsonArray> call = apiService.Approveordecline(jsonObject);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                try {
                    Log.d("Response", response.toString());
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    {
                        Log.d("createdemo:code-res", response.code() + " - " + response.toString());
                        if (response.code() == 200 || response.code() == 201) {
                            Log.d("Response", response.body().toString());
                            JSONArray js = new JSONArray(response.body().toString());

                            if (js.length() > 0) {
                                JSONObject jsonObject = js.getJSONObject(0);

                                message = jsonObject.getString("Message");
                                if (Integer.parseInt(jsonObject.getString("Status")) == 1) {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Pocapprovesublist.this);

                                    alertDialog.setTitle("Alert");

                                    alertDialog.setMessage(message + " Press OK to exit.");

                                    alertDialog.setIcon(android.R.drawable.ic_dialog_info);

                                    alertDialog.setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                    Toast.makeText(Pocapprovesublist.this, "" + message, Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                    alertDialog.show();
                                } else {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Pocapprovesublist.this);

                                    alertDialog.setTitle("Alert");

                                    alertDialog.setMessage(message);

                                    alertDialog.setIcon(android.R.drawable.ic_dialog_info);

                                    alertDialog.setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                    Toast.makeText(Pocapprovesublist.this, message, Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                    alertDialog.show();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                alert("Server Connection Failed");
            }
        });
    }

//    private void showToast(String msg) {
//        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//    }

    private void alert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Pocapprovesublist.this);
        builder.setTitle(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                finish();
            }
        });
        builder.create().show();


    }
}
