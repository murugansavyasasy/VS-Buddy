package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
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

import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.Featureclass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;

public class Features extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener
{
    SwitchCompat smstoschool,smsfeature, smsreport, templateblock, studupload, needsmsapi, smsmarks, needstudapi, uploadvoice, enablemobapps, adminmissedcall, scapptoivrmgtcalls, scapptoivreodcalls, sendusage;
    CheckBox checksms, checkvoice;
    Button Feature;
    private ProgressDialog pDialog;
    RequestQueue requestQueue;
//    SharedPreferences shpRemember;
//    SharedPreferences.Editor ed;
//    private static final String SH_USERS = "userInfo";
//    private static final String SH_LOGINTYPE = "slogintype";
//    private static final String SH_USERID = "UserId";
//    String serverPath = "http://192.168.0.77:8096/SchoolDemoAppService/Service.asmx/";
    String serverPath="http://220.226.2.177:9000/StaffVoiceAnnouncerCloudSchool/Service.asmx/";
    String userId, schoolid, message, strsmsfeature, strsmstoschool, strsmsreport, strtemplateblock, strstudupload, strneedsmsapi,
            strsmsmarks, strneedstudapi, strenablesmsapps, struploadvoice, strenablemobapps, stradminmissedcall, strscapptoivrmgtcalls,
            strscapptoivreodcalls, strsendusage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);

        userId= SharedPreference_class.getShSchlLoginid(Features.this);

//        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
//        userId = shpRemember.getString(SH_USERID, null);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            schoolid = extras.getString("VALUE");

        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

       strsmsfeature = strsmstoschool = strsmsreport = strtemplateblock = strstudupload = strneedsmsapi =
                strsmsmarks = strneedstudapi = struploadvoice = strenablemobapps = stradminmissedcall = strscapptoivrmgtcalls =
                        strscapptoivreodcalls = strsendusage = "false";

        smsfeature = (SwitchCompat) findViewById(R.id.Switch1);
        smsfeature.setOnCheckedChangeListener(this);
        smstoschool = (SwitchCompat) findViewById(R.id.Switch2);
        smstoschool.setOnCheckedChangeListener(this);
        smsreport = (SwitchCompat) findViewById(R.id.Switch3);
        smsreport.setOnCheckedChangeListener(this);
        templateblock = (SwitchCompat) findViewById(R.id.Switch4);
        templateblock.setOnCheckedChangeListener(this);
        studupload = (SwitchCompat) findViewById(R.id.Switch5);
        studupload.setOnCheckedChangeListener(this);
        needsmsapi = (SwitchCompat) findViewById(R.id.Switch6);
        needsmsapi.setOnCheckedChangeListener(this);
        smsmarks = (SwitchCompat) findViewById(R.id.Switch7);
        smsmarks.setOnCheckedChangeListener(this);
        needstudapi = (SwitchCompat) findViewById(R.id.Switch8);
        needstudapi.setOnCheckedChangeListener(this);
        uploadvoice = (SwitchCompat) findViewById(R.id.Switch9);
        uploadvoice.setOnCheckedChangeListener(this);
        enablemobapps = (SwitchCompat) findViewById(R.id.Switch10);
        enablemobapps.setOnCheckedChangeListener(this);
        adminmissedcall = (SwitchCompat) findViewById(R.id.Switch11);
        adminmissedcall.setOnCheckedChangeListener(this);
        scapptoivrmgtcalls = (SwitchCompat) findViewById(R.id.Switch12);
        scapptoivrmgtcalls.setOnCheckedChangeListener(this);
        scapptoivreodcalls = (SwitchCompat) findViewById(R.id.Switch13);
        scapptoivreodcalls.setOnCheckedChangeListener(this);
        sendusage = (SwitchCompat) findViewById(R.id.Switch14);
        sendusage.setOnCheckedChangeListener(this);

        checksms = (CheckBox) findViewById(R.id.featureS);
        checkvoice = (CheckBox) findViewById(R.id.featureA);


        Feature = (Button) findViewById(R.id.featurebtn);


        getfeatureData();

        Feature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checksms.isChecked() && checkvoice.isChecked()) {
                    strenablesmsapps = "SA";
                    enablemobapps.setChecked(true);
                }

                else if (checksms.isChecked()) {
                    strenablesmsapps = "S";
//                    enablemobapps.setChecked(false);
                }
                else if(checkvoice.isChecked()) {
                    strenablesmsapps = "A";
                    enablemobapps.setChecked(true);
                }
                else
                {
//                    Toast.makeText(Features.this,"", Toast.LENGTH_SHORT).show();
                    strenablesmsapps = "";
                }

                Enaablefeatures();

            }
        });


    }


    void Enaablefeatures() {
        String strServerUrl = serverPath + "EnableFeaturesByApp?LoginID=" + userId + "&&SchoolID=" + schoolid + "&&EnableApps=" + strenablemobapps + "&&EnableSMSApps=" + strenablesmsapps + "&&SMSReports=" + strsmsreport + "&&SMStoScl=" + strsmstoschool +
                "&&SMSFeature=" + strsmsfeature + "&&UploadMarks=" + strsmsmarks + "&&TemplateBlock=" + strtemplateblock + "&&StudUpload=" + strstudupload + "&&NeedSmsAPI=" + strneedsmsapi +
                "&&NeedStudAPI=" + strneedstudapi + "&&UploadVoice=" + struploadvoice + "&&AdminMissedCall=" + stradminmissedcall + "&&ScApptoIVRMgtCalls=" + strscapptoivrmgtcalls + "&&ScApptoIVREodCalls=" + strscapptoivreodcalls + "&&SensdUsage=" + strsendusage;


        Log.d("URL", strServerUrl);
        if (isNetworkConnected()) {
            requestQueue = Volley.newRequestQueue(Features.this);
            StringRequest obreq = new StringRequest(Request.Method.GET, strServerUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        hidePDialog();
                        Feature.setEnabled(true);
                        JSONObject obj = new JSONObject(response);
                        Log.d("Server Response", obj.toString());
                        JSONArray arrrr = obj.getJSONArray("EnableFeaturesByApp");
                        JSONObject obj1 = arrrr.getJSONObject(0);
//                        Status = obj1.getInt("Status");
                        message = obj1.getString("Message");

                        if (Integer.parseInt(obj1.getString("Status")) == 1) {
//                            Toast.makeText(Features.this, message, Toast.LENGTH_SHORT).show();

                            {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Features.this);

                                alertDialog.setTitle("Alert");

                                alertDialog.setMessage(message+" Press OK to exit.");

                                alertDialog.setIcon(android.R.drawable.ic_dialog_info);

                                alertDialog.setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
//                                                Toast.makeText(SchoolSubList.this, "" + "POC Approved", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                alertDialog.show();
                            }


                        } else {
                            String title="Alert";
                            alert(title,message);

//                            Toast.makeText(Features.this, message, Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e) {
                        hidePDialog();
                        Feature.setEnabled(true);
                        Alert("Exception");

//                        Toast.makeText(Features.this, "Exception", Toast.LENGTH_SHORT).show();
                        Log.d("Exception", "Feature-Message:Exception" + e.getMessage());
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hidePDialog();
                            Feature.setEnabled(true);
                            Alert("Network Problem - Failure");
//                            Toast.makeText(Features.this, "Network Problem - Failure", Toast.LENGTH_SHORT).show();
                            Log.d("OnErrorResponse", "Feature-Message:error" + error.getMessage());
                        }
                    });

            requestQueue.add(obreq);

            pDialog = new ProgressDialog(Features.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        } else {
            Feature.setEnabled(true);
            Alert("Check Your Internet connection");
//            Toast.makeText(this, "Check Your Internet connection", Toast.LENGTH_SHORT).show();
        }
    }


    void getfeatureData() {
        String serverUrl = serverPath + "GetExistingFeaturesbyApp?LoginID=" + userId + "&&SchoolID=" + schoolid;
        Log.d("URL", serverUrl);
        if (isNetworkConnected()) {

            requestQueue = Volley.newRequestQueue(this);
            StringRequest strRequest = new StringRequest(Request.Method.GET, serverUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    hidePDialog();
                    try {
                        JSONObject obj = new JSONObject(response);
                        Log.d("Server Response", obj.toString());
                        JSONArray jsonArray = obj.getJSONArray("GetExistingFeaturesbyApp");


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject temp = jsonArray.getJSONObject(i);

                            Featureclass featuredata = new Featureclass();
                            featuredata.setStrSmsfeature(temp.getString("SMSFeature"));
                            featuredata.setStrSmstoschool(temp.getString("SMSToSchool"));
                            featuredata.setStrSmsreports(temp.getString("SMSReports"));
                            featuredata.setStrTemplateblocks(temp.getString("TemplateBlock"));
                            featuredata.setStrStudupload(temp.getString("StudUpload"));
                            featuredata.setStrNeedsmsapi(temp.getString("NeedSmsAPI"));
                            featuredata.setStrSmsMarks(temp.getString("SMSMarks"));
                            featuredata.setStrNeedstudapi(temp.getString("NeedStudAPI"));
                            featuredata.setStrUploadVoice(temp.getString("UploadVoice"));
                            featuredata.setStrEnablemobapps(temp.getString("EnableMobApps"));
                            featuredata.setStrEnablesmsonapps(temp.getString("EnableSmsOnApps"));
                            featuredata.setStrAdminmissedcall(temp.getString("AdminMissedCall"));
                            featuredata.setStrScapptoivrmgtcalls(temp.getString("ScApptoIVRMgtCalls"));
                            featuredata.setStrScapptoivreodcallss(temp.getString("ScApptoIVREodCalls"));
                            featuredata.setStrSendUsage(temp.getString("SensdUsage"));
                            Log.d("Server Array", temp.toString());

                            getexistingdata(featuredata);

                        }

                    } catch (Exception e) {
                        Log.d("Exception", e.toString());
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hidePDialog();
                            Log.d("MyError", error.toString());
                        }
                    });
            requestQueue.add(strRequest);

            pDialog = new ProgressDialog(Features.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        } else {
            Alert("Check Your Internet connection");
//            Toast.makeText(this, "Check Your Internet connection", Toast.LENGTH_SHORT).show();
        }
    }


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

    public void getexistingdata(Featureclass feat) {


        String strsmsfeature1 = (feat.getStrSmsfeature());
        String strsmstoschool1 = (feat.getStrSmstoschool());
        String strsmsreport1 = (feat.getStrSmsreports());
        String strtemplateblock1 = (feat.getStrTemplateblocks());
        String strstudupload1 = (feat.getStrStudupload());
        String strneedsmsapi1 = (feat.getStrNeedsmsapi());
        String strsmsmarks1 = (feat.getStrSmsMarks());
        String strneedstudapi1 = (feat.getStrNeedstudapi());
        String struploadvoice1 = (feat.getStrUploadVoice());
        String strenablemobapps1 = (feat.getStrEnablemobapps());
        String stradminmissedcall1 = (feat.getStrAdminmissedcall());
        String strscapptoivrmgtcalls1 = (feat.getStrScapptoivrmgtcalls());
        String strscapptoivreodcalls1 = (feat.getStrScapptoivreodcalls());
        String strsendusage1 = (feat.getStrSendUsage());
        String strenablesmsapps1 = (feat.getStrEnablesmsonapps());
        Log.d("test", strsmsfeature1);

        smsfeature.setChecked(Boolean.parseBoolean(strsmsfeature1));
        smstoschool.setChecked(Boolean.parseBoolean(strsmstoschool1));
        smsreport.setChecked(Boolean.parseBoolean(strsmsreport1));
        templateblock.setChecked(Boolean.parseBoolean(strtemplateblock1));
        studupload.setChecked(Boolean.parseBoolean(strstudupload1));
        needsmsapi.setChecked(Boolean.parseBoolean(strneedsmsapi1));
        smsmarks.setChecked(Boolean.parseBoolean(strsmsmarks1));
        needstudapi.setChecked(Boolean.parseBoolean(strneedstudapi1));
        studupload.setChecked(Boolean.parseBoolean(strstudupload1));
        enablemobapps.setChecked(Boolean.parseBoolean(strenablemobapps1));
        uploadvoice.setChecked(Boolean.parseBoolean(struploadvoice1));
        adminmissedcall.setChecked(Boolean.parseBoolean(stradminmissedcall1));
        scapptoivrmgtcalls.setChecked(Boolean.parseBoolean(strscapptoivrmgtcalls1));
        scapptoivreodcalls.setChecked(Boolean.parseBoolean(strscapptoivreodcalls1));
        sendusage.setChecked(Boolean.parseBoolean(strsendusage1));

        if(strenablesmsapps1.equals("A")||strenablesmsapps1.equals("SA"))
        {
            enablemobapps.setChecked(true);
        }
        else{
//            Toast.makeText(Features.this, "Select the Enablemobapps", Toast.LENGTH_SHORT).show();
        }

        if (strenablesmsapps1.equals("S")) {
            checksms.setChecked(true);
            checkvoice.setChecked(false);
        }
        else if(strenablesmsapps1.equals("A")){
            checksms.setChecked(false);
            checkvoice.setChecked(true);
        }
        else if(strenablesmsapps1.equals("SA")) {
            checksms.setChecked(true);
            checkvoice.setChecked(true);
        }
        else {
            checksms.setChecked(false);
            checkvoice.setChecked(false);
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int bID = buttonView.getId();
        String strIsCheckedStatus = String.valueOf(isChecked);

        switch (bID)
        {
            case R.id.Switch1:
                strsmsfeature = strIsCheckedStatus;
                break;
            case R.id.Switch2:
                strsmstoschool = strIsCheckedStatus;
                break;
            case R.id.Switch3:
                strsmsreport = strIsCheckedStatus;
                break;
            case R.id.Switch4:
                strtemplateblock = strIsCheckedStatus;
                break;
            case R.id.Switch5:
                strstudupload = strIsCheckedStatus;
                break;
            case R.id.Switch6:
                strneedsmsapi = strIsCheckedStatus;
                break;
            case R.id.Switch7:
                strsmsmarks = strIsCheckedStatus;
                break;
            case R.id.Switch8:
                strneedstudapi = strIsCheckedStatus;
                break;
            case R.id.Switch9:
                struploadvoice = strIsCheckedStatus;
                break;
            case R.id.Switch10:
                strenablemobapps = strIsCheckedStatus;
                break;
            case R.id.Switch11:
                stradminmissedcall = strIsCheckedStatus;
                break;
            case R.id.Switch12:
                strscapptoivrmgtcalls = strIsCheckedStatus;
                break;
            case R.id.Switch13:
                strscapptoivreodcalls = strIsCheckedStatus;
                break;
            case R.id.Switch14:
                strsendusage = strIsCheckedStatus;
                break;

        }
    }

    private void Enablefeaturesretrofit() {

        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoginID",userId);
        jsonObject.addProperty("SchoolID",schoolid);
        jsonObject.addProperty("EnableApps", strenablemobapps);
        jsonObject.addProperty("EnableSMSApps", strenablesmsapps);
        jsonObject.addProperty("SMSReports",strsmsreport);
        jsonObject.addProperty("SMStoScl", strsmstoschool);
        jsonObject.addProperty("SMSFeature", strsmsfeature);
        jsonObject.addProperty("UploadMarks",strsmsmarks);
        jsonObject.addProperty("TemplateBlock", strtemplateblock);
        jsonObject.addProperty("StudUpload",strstudupload);
        jsonObject.addProperty("NeedSmsAPI",strneedsmsapi);
        jsonObject.addProperty("NeedStudAPI", strneedstudapi);
        jsonObject.addProperty("UploadVoice", struploadvoice);
        jsonObject.addProperty("AdminMissedCall",stradminmissedcall);
        jsonObject.addProperty("ScApptoIVRMgtCalls", strscapptoivrmgtcalls);
        jsonObject.addProperty("ScApptoIVREodCalls",strscapptoivreodcalls);
        jsonObject.addProperty("SensdUsage", strsendusage);
        Log.d("Feature:req", jsonObject.toString());
        Call<JsonArray> call = apiService.Features(jsonObject);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                try {
                    Log.d("Response", response.toString());
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    {
                        Log.d("Feature:code-res", response.code() + " - " + response.toString());
                        if (response.code() == 200 || response.code() == 201) {
                            Log.d("Response", response.body().toString());
                            JSONArray js = new JSONArray(response.body().toString());
                            if (js.length() > 0) {
                                JSONObject jsonObject = js.getJSONObject(0);
                                message = jsonObject.getString("Message");
                                if (Integer.parseInt(jsonObject.getString("Status")) == 1) {
                                    {
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Features.this);
                                        alertDialog.setTitle("Alert");
                                        alertDialog.setMessage(message + " Press OK to exit.");
                                        alertDialog.setIcon(android.R.drawable.ic_dialog_info);
                                        alertDialog.setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        finish();
                                                    }
                                                });
                                        alertDialog.show();
                                    }
                                } else {

                                    String title="Alert";
                                    alert(title,message);
//                                    Toast.makeText(Features.this, message, Toast.LENGTH_SHORT).show();
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
                Alert("Server Connection Failed");
            }
        });
    }
    private void featurelisttrofit() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoginID",userId);
        jsonObject.addProperty("SchoolID",schoolid);

        Log.d("Featurelist:req", jsonObject.toString());

        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        Call<JsonArray> call = apiService.Featurelist(jsonObject);

        call.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                try {
                    Log.d("Featurelist;Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();

                    JSONArray jsonArrayorgList = new JSONArray(strResponse);
                    if (jsonArrayorgList.length() > 0) {
                        JSONObject temp;

                        for (int i = 0; i < jsonArrayorgList.length(); i++) {
                            temp = jsonArrayorgList.getJSONObject(i);

                            Featureclass featuredata = new Featureclass();
                            featuredata.setStrSmsfeature(temp.getString("SMSFeature"));
                            featuredata.setStrSmstoschool(temp.getString("SMSToSchool"));
                            featuredata.setStrSmsreports(temp.getString("SMSReports"));
                            featuredata.setStrTemplateblocks(temp.getString("TemplateBlock"));
                            featuredata.setStrStudupload(temp.getString("StudUpload"));
                            featuredata.setStrNeedsmsapi(temp.getString("NeedSmsAPI"));
                            featuredata.setStrSmsMarks(temp.getString("SMSMarks"));
                            featuredata.setStrNeedstudapi(temp.getString("NeedStudAPI"));
                            featuredata.setStrUploadVoice(temp.getString("UploadVoice"));
                            featuredata.setStrEnablemobapps(temp.getString("EnableMobApps"));
                            featuredata.setStrEnablesmsonapps(temp.getString("EnableSmsOnApps"));
                            featuredata.setStrAdminmissedcall(temp.getString("AdminMissedCall"));
                            featuredata.setStrScapptoivrmgtcalls(temp.getString("ScApptoIVRMgtCalls"));
                            featuredata.setStrScapptoivreodcallss(temp.getString("ScApptoIVREodCalls"));
                            featuredata.setStrSendUsage(temp.getString("SensdUsage"));
                            Log.d("Server Array", temp.toString());

                            getexistingdata(featuredata);
                        }
                    } else {
                        Alert("No data Received. Try Again.");
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

    private void alert(String title,String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Features.this);
        builder.setTitle(title);
        builder.setMessage(reason);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();

    }
    private void Alert(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Features.this);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();

    }

}

