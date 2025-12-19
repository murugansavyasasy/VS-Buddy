package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.Supportverifylistclass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;

public class Supportverifysublist extends AppCompatActivity {
    TextView textPOCid, textPOCschoolname, textPOCSchooladdr, textPOCCity, textPOCcallcredit, textPOCsmscredit,
            textPOCphno, textPOCemailid, textPOCprinciname, textPOCmobno, textPOCfromdate, textPOCtodate, textPOCremarks,
            textPOCmodule, textPOCdbrequried, textPOCdemo, textPOCscope, tvsmscaption, tvcreatedon, tvwelcometext,
            tvsmscount, tvcallcount,tvpurchsetype;
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
            strsmscredits, strunlimitedcalls, strunlimitedsms, strsmscaption,strpurchsetype;
    int iRequestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supportverifysublist);
        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
        userId = shpRemember.getString(SH_USERID, null);
        Password = shpRemember.getString(SH_PASSWORD, null);
        iRequestCode = getIntent().getExtras().getInt("REQUEST_CODE", 0);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        final Supportverifylistclass POCdata = (Supportverifylistclass) getIntent().getSerializableExtra("Supportverifylistclass");
        textPOCid = (TextView) findViewById(R.id.POCsupportid1);
        textPOCschoolname = (TextView) findViewById(R.id.POCsupportlistschoolname1);
        textPOCSchooladdr = (TextView) findViewById(R.id.POCsupportSchooladdr1);
        textPOCCity = (TextView) findViewById(R.id.POCsupportCity1);
        textPOCphno = (TextView) findViewById(R.id.POCsupportphno1);
        textPOCemailid = (TextView) findViewById(R.id.POCsupportemailid1);
        textPOCprinciname = (TextView) findViewById(R.id.POCsupportprinciname1);
        textPOCmobno = (TextView) findViewById(R.id.POCsupportmobno1);
        textPOCfromdate = (TextView) findViewById(R.id.POCsupportfromdate1);
        textPOCtodate = (TextView) findViewById(R.id.POCsupporttodate1);
        textPOCremarks = (TextView) findViewById(R.id.POCsupportremarks1);
        textPOCmodule = (TextView) findViewById(R.id.POCsupportmodule1);
        textPOCdbrequried = (TextView) findViewById(R.id.POCsupportdbrequried1);
        textPOCdemo = (TextView) findViewById(R.id.POCsupportdemo1);
        textPOCscope = (TextView) findViewById(R.id.POCsupportscope1);
        tvsmscaption = (TextView) findViewById(R.id.POCsupportsmscaption);
        textPOCcallcredit = (TextView) findViewById(R.id.POCsupportcallcredits1);
        textPOCsmscredit = (TextView) findViewById(R.id.POCsupportsmscredits1);

        tvcallcount = (TextView) findViewById(R.id.POCsupportcallcount1);
        tvsmscount = (TextView) findViewById(R.id.POCsupportsmscount1);
        tvwelcometext = (TextView) findViewById(R.id.POCsupportwelcometext1);
        tvcreatedon = (TextView) findViewById(R.id.POCsupportcreatedon1);
        tvpurchsetype=(TextView) findViewById(R.id.POCsupportpurchasetype);

        approve_btn = (Button) findViewById(R.id.btnapprove_supportpoc);
        decline_btn = (Button) findViewById(R.id.btndecline_supportpoc);
        img_edit = (ImageView) findViewById(R.id.img_editpocsupport);
        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Supportverifysublist.this, SuportVerify.class);
                i.putExtra("REQUEST_CODE", iRequestCode);
                i.putExtra("POCid", POCdata.getStrPOCsupportschoolid());
                i.putExtra("POCschoolname", POCdata.getStrPOCsupportschoolname());
                i.putExtra("POCcity", POCdata.getStrPOCsupportcity());
                i.putExtra("POCaddress", POCdata.getStrPOCsupportSchooladdr());
                i.putExtra("POCwelcomefiletext", POCdata.getStrPOCsupportWelcomefileText());
                i.putExtra("POCemailid", POCdata.getStrPOCsupportemailid());
                i.putExtra("POCprinciname", POCdata.getStrsupportContactPerson());
                i.putExtra("POCmobno", POCdata.getStrPOCsupportmobno());
                i.putExtra("POCfromdate", POCdata.getStrPOCsupportfromdate());
                i.putExtra("POCtodate", POCdata.getStrPOCsupporttodate());
//                i.putExtra("POCCallcredits", POCdata.getStrPOCsupportisCallsUnlimited());
//                i.putExtra("POCSmscredit", POCdata.getStrPOCsupportisSMSUnlimited());
                i.putExtra("POCcallcost", POCdata.getStrPOCsupportCallCount());
                i.putExtra("POCsmscost", POCdata.getStrPOCsupportSMSCount());
                i.putExtra("POCmodule", POCdata.getStrPOCsupportmodules());
                i.putExtra("POCcaption", POCdata.getStrPOCsupportSMSCaption());
                i.putExtra("POCpincode", POCdata.getStrPOCsupportpincode());
                i.putExtra("POCpurchasetype",POCdata.getStrPOCsupportpurchasetype());


//                i.putExtra("POCid",POCdata.getSmscredit());
                startActivityForResult(i, iRequestCode);
            }
        });
        textPOCid.setText(POCdata.getStrPOCsupportschoolid());
        textPOCschoolname.setText(POCdata.getStrPOCsupportschoolname());
        textPOCSchooladdr.setText(POCdata.getStrPOCsupportSchooladdr());
        textPOCphno.setText(POCdata.getStrPOCsupportmobno());
        textPOCemailid.setText(POCdata.getStrPOCsupportemailid());
        textPOCCity.setText(POCdata.getStrPOCsupportcity());
        textPOCprinciname.setText(POCdata.getStrsupportContactPerson());
        textPOCmobno.setText(POCdata.getStrPOCsupportmobno());
        textPOCfromdate.setText(POCdata.getStrPOCsupportfromdate());
        textPOCtodate.setText(POCdata.getStrPOCsupporttodate());
        textPOCcallcredit.setText(POCdata.getStrPOCsupportisCallsUnlimited());
        textPOCsmscredit.setText(POCdata.getStrPOCsupportisSMSUnlimited());
        textPOCmodule.setText(POCdata.getStrPOCsupportmodules());
        tvsmscaption.setText(POCdata.getStrPOCsupportSMSCaption());
        tvcreatedon.setText(POCdata.getStrsupportPOCCreatedOn());
        tvwelcometext.setText(POCdata.getStrPOCsupportWelcomefileText());
        tvsmscount.setText(POCdata.getStrPOCsupportSMSCount());
        tvcallcount.setText(POCdata.getStrPOCsupportCallCount());
        tvpurchsetype.setText(POCdata.getStrPOCsupportpurchasetype());
        strSchoolname = POCdata.getStrPOCsupportschoolname();
        strschoolid = POCdata.getStrPOCsupportschoolid();
        strAddress = POCdata.getStrPOCsupportSchooladdr();
        strMobilenumber = POCdata.getStrPOCsupportmobno();
        strcity = POCdata.getStrPOCsupportcity();
        strEmailId = POCdata.getStrPOCsupportemailid();
        strcontactperson = POCdata.getStrsupportContactPerson();
//        strcaption = POCcaption.getText().toString();
//        strcallcost = POCcallcost.getText().toString();
//        strsmscost = POCsmscost.getText().toString();
        strFromdate = POCdata.getStrPOCsupportfromdate();
        strTodate = POCdata.getStrPOCsupporttodate();
        strmodule = POCdata.getStrPOCsupportmodules();
        strcallcredits = POCdata.getStrPOCsupportCallCount();
        strsmscredits = POCdata.getStrPOCsupportSMSCount();
        strcaption = POCdata.getStrPOCsupportSMSCaption();

//        if (strcallcost.equals("Unlimited")) {
//            strunlimitedcalls = "1";
//        } else {
//            strunlimitedcalls = "0";
//        }
//
//        if (strsmscost.equals("Unlimited")) {
//            strunlimitedsms = "1";
//        } else {
//            strunlimitedsms = "0";
//        }

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
//        returnIntent.putExtra("STD_SEC", stdSec);
        setResult(iRequestCode, returnIntent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void alert1() {
        Log.d("password", Password);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Supportverifysublist.this);
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Are you sure To Approve POC?");
        alertDialog.setIcon(android.R.drawable.ic_dialog_info);
        alertDialog.setPositiveButton("Approve",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertapprove();
                    }
                });
        alertDialog.show();
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

    private void alert2() {
        Log.d("password", Password);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Supportverifysublist.this);
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Are you sure To Decline POC?");
        alertDialog.setIcon(android.R.drawable.ic_dialog_info);

        alertDialog.setNegativeButton("Decline",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertdecline();
                    }
                });
        alertDialog.show();
    }


    private void alertapprove() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Supportverifysublist.this);

        alertDialog.setTitle("Password Alert");

        alertDialog.setMessage("Re-Enter Your Vims Password");
        final EditText input = new EditText(Supportverifysublist.this);
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
                            approve();
                        } else {
                            input.getText().clear();
                            Alert("Password Incorrect Enter the Password again");
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Supportverifysublist.this);

        alertDialog.setTitle("Password Alert");

        alertDialog.setMessage("Re-Enter Your Password");
        final EditText input = new EditText(Supportverifysublist.this);
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
                            decline();
                        } else {
                            input.getText().clear();
                            Alert("Password Incorrect Enter the Password again");
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

    private void approve() {

        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

//        {"LoginId":"33","idPOCSchool":"1021","Password":"1234","ProcessType":"approve"}
        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoginId", userId);
        jsonObject.addProperty("idPOCSchool", strschoolid);
//        jsonObject.addProperty("InstitutionName", strSchoolname);
//        jsonObject.addProperty("Address", strAddress);
//        jsonObject.addProperty("City", strcity);
//        jsonObject.addProperty("ContactNumber1", strMobilenumber);
//        jsonObject.addProperty("ContactPerson1", strcontactperson);
//        jsonObject.addProperty("Email", strEmailId);
//        jsonObject.addProperty("Modules", strmodule);
//        jsonObject.addProperty("caption", strcaption);
//        jsonObject.addProperty("peroidFrom", strFromdate);
//        jsonObject.addProperty("peroidTo", strTodate);
//        jsonObject.addProperty("processType", "approve");
//        jsonObject.addProperty("isUnlimitedCalls", strunlimitedcalls);
//        jsonObject.addProperty("isUnlimitedSMS", strunlimitedsms);
//        jsonObject.addProperty("callCost", strcallcost);
//        jsonObject.addProperty("smsCost", strsmscost);
//        jsonObject.addProperty("LoginName", "");
        jsonObject.addProperty("password", Password);
        jsonObject.addProperty("ProcessType", "approve");

        Log.d("createdemo:req", jsonObject.toString());
        Call<JsonArray> call = apiService.Approveordeclinesupport(jsonObject);
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
//                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Supportverifysublist.this);
//
//                                    alertDialog.setTitle("Alert");
//
//                                    alertDialog.setMessage(message + " Press OK to exit.");
//
//                                    alertDialog.setIcon(android.R.drawable.ic_dialog_info);
//
//                                    alertDialog.setPositiveButton("OK",
//                                            new DialogInterface.OnClickListener() {
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    finish();
//                                                    Toast.makeText(Supportverifysublist.this, "" + message, Toast.LENGTH_SHORT).show();
//                                                }
//                                            });
//
//                                    alertDialog.show();


                                    LayoutInflater layoutInflater = LayoutInflater.from(Supportverifysublist.this);
                                    View promptView = layoutInflater.inflate(R.layout.supportdailog, null);

                                    final AlertDialog dlgAlert = new AlertDialog.Builder(Supportverifysublist.this).create();
//                                    dlgAlert.setTitle("Alert");
//                                    dlgAlert.setMessage(jsonObject.getString("Message"));
//                                    dlgAlert.setIcon(android.R.drawable.ic_dialog_info);
                                    dlgAlert.setCancelable(false);
                                    final Button Okbtn = (Button) promptView.findViewById(R.id.okbtn);
                                    ImageView imgrecord = (ImageView) promptView.findViewById(R.id.imgrecord1);
                                    TextView tvmessage = (TextView) promptView.findViewById(R.id.pocmessage1);
                                    tvmessage.setText(jsonObject.getString("Message"));
                                    Okbtn.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            dlgAlert.dismiss();
                                            finish();
                                        }
                                    });

                                    imgrecord.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            Intent i = new Intent(Supportverifysublist.this, Recordwelcomefile.class);
                                            i.putExtra("SchoolID", strschoolid);
                                            startActivity(i);
                                        }
                                    });
                                    dlgAlert.setView(promptView);
                                    dlgAlert.show();
                                } else {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Supportverifysublist.this);
                                    alertDialog.setTitle("Alert");
                                    alertDialog.setMessage(message);
                                    alertDialog.setIcon(android.R.drawable.ic_dialog_info);
                                    alertDialog.setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();

                                                    Toast.makeText(Supportverifysublist.this, message, Toast.LENGTH_SHORT).show();
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
                Alert("Server Connection Failed");
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
        jsonObject.addProperty("idPOCSchool", strschoolid);
        jsonObject.addProperty("InstitutionName", strSchoolname);
//        jsonObject.addProperty("Address", strAddress);
//        jsonObject.addProperty("City", strcity);
//        jsonObject.addProperty("ContactNumber1", strMobilenumber);
//        jsonObject.addProperty("ContactPerson1", strcontactperson);
//        jsonObject.addProperty("Email", strEmailId);
//        jsonObject.addProperty("Modules", strmodule);
//        jsonObject.addProperty("caption", strcaption);
//        jsonObject.addProperty("peroidFrom", strFromdate);
//        jsonObject.addProperty("peroidTo", strTodate);
//        jsonObject.addProperty("processType", "decline");
//        jsonObject.addProperty("isUnlimitedCalls", strunlimitedcalls);
//        jsonObject.addProperty("isUnlimitedSMS", strunlimitedsms);
//        jsonObject.addProperty("callCost", strcallcost);
//        jsonObject.addProperty("smsCost", strsmscost);
//        jsonObject.addProperty("LoginName", "");
        jsonObject.addProperty("password", Password);
        jsonObject.addProperty("ProcessType", "decline");

        Log.d("createdemo:req", jsonObject.toString());
        Call<JsonArray> call = apiService.Approveordeclinesupport(jsonObject);
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
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Supportverifysublist.this);

                                    alertDialog.setTitle("Alert");

                                    alertDialog.setMessage(message + " Press OK to exit.");

                                    alertDialog.setIcon(android.R.drawable.ic_dialog_info);

                                    alertDialog.setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                    Toast.makeText(Supportverifysublist.this, "" + message, Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                    alertDialog.show();
                                } else {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Supportverifysublist.this);

                                    alertDialog.setTitle("Alert");

                                    alertDialog.setMessage(message);

                                    alertDialog.setIcon(android.R.drawable.ic_dialog_info);

                                    alertDialog.setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
//                                                    finish();
                                                    Toast.makeText(Supportverifysublist.this, message, Toast.LENGTH_SHORT).show();
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
                Alert("Server Connection Failed");
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
        backToResultActvity("BACK");
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
    private void Alert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Supportverifysublist.this);
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

//    private void showToast(String msg) {
//        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//    }

}
