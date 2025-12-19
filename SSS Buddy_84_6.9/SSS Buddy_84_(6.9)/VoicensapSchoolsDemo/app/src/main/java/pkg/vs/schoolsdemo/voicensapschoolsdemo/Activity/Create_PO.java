package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import com.android.volley.AuthFailureError;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;

public class Create_PO extends AppCompatActivity {
    EditText POschoolname, POSchooladdr, POphno, POemailid, POprinciname, POmobno, POfromdate, POtodate, PObilldate, POremarks, POdatadate, POsms, POcall;
    RadioGroup POmodule, POdbrequried, POdemo, POscope;
    CheckBox chunlimited;
    RequestQueue requestQueue;
    LinearLayout linearsmspoc,linearcallpoc;
    int Result;
    String Reason;
    String serverPath = "http://220.226.2.177:9000/StaffVoiceAnnouncerCloudSchool/Service.asmx/";
//    String serverPath = "http://192.168.0.77:8096/SchoolDemoAppService/Service.asmx/";

    ImageView imageFromDatepo, imageToDatepo, imgdatadatepo, imgbilldate;
    RadioButton radiosms, radiocalls, radioboth;

    LinearLayout linearsms, linearcall, linearboth;
    private ProgressDialog pDialog;
//    private static final String SH_USERS = "userInfo";
//    private static final String SH_USERID = "UserId";
//    SharedPreferences shpRemember;
//    SharedPreferences.Editor ed;

    String userId;

    String strSchoolname, strAddress, strphoneno, strEmailId, strPrincipalname, strMobilenumber, strFromdate, strdatadate, strTodate, strRemarks, strSms, strCall, strbilldate;
    int intModule, intdbrequired;
    Boolean intdemo, intScope;
    CheckBox chsms, chcall;
    private Calendar calendar;
    private int year, month, day, hour, minute;
    Button POsubmit;

    int dateType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createp);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

//        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
//        userId = shpRemember.getString(SH_USERID, null);


        userId= SharedPreference_class.getShSchlLoginid(Create_PO.this);

        POschoolname = (EditText) findViewById(R.id.poschoolname);
        POSchooladdr = (EditText) findViewById(R.id.poschooladdr);
        POphno = (EditText) findViewById(R.id.poschoolphno);
        POemailid = (EditText) findViewById(R.id.poschoolmailid);
        POprinciname = (EditText) findViewById(R.id.poprincipalname);
        POmobno = (EditText) findViewById(R.id.pomobileno);
        POfromdate = (EditText) findViewById(R.id.pofromdate);
        POdatadate = (EditText) findViewById(R.id.podatarevdate);
        POtodate = (EditText) findViewById(R.id.potodate);
        PObilldate = (EditText) findViewById(R.id.pobilldate);
        POremarks = (EditText) findViewById(R.id.poremarks);

        POmodule = (RadioGroup) findViewById(R.id.pomodule);
        POsms = (EditText) findViewById(R.id.smsmod);
        POcall = (EditText) findViewById(R.id.callmod);
        POdbrequried = (RadioGroup) findViewById(R.id.podbrequired);
        POdemo = (RadioGroup) findViewById(R.id.podemo);
        POscope = (RadioGroup) findViewById(R.id.poscope);
        radiosms = (RadioButton) findViewById(R.id.radiosmsmod);
        radiocalls = (RadioButton) findViewById(R.id.radiocallmod);
        radioboth = (RadioButton) findViewById(R.id.radiobothmod);
        linearsms = (LinearLayout) findViewById(R.id.linearLayoutsms);
        linearcall = (LinearLayout) findViewById(R.id.linearLayoutcall);

        imageFromDatepo = (ImageView) findViewById(R.id.imagepofromdate);
        imageToDatepo = (ImageView) findViewById(R.id.imagepotodate);
        imgdatadatepo = (ImageView) findViewById(R.id.imagedatarevdatepo);
        imgbilldate = (ImageView) findViewById(R.id.imagepobilldate);
        showDate(year, month, day);
        POsubmit = (Button) findViewById(R.id.posubmit);
        chsms = (CheckBox) findViewById(R.id.chsms);
        chcall = (CheckBox) findViewById(R.id.chcall);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        POmodule.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radiosms.isChecked()) {
                    linearsms.setVisibility(View.VISIBLE);
                    linearcall.setVisibility(View.GONE);
                } else if (radiocalls.isChecked()) {
                    linearsms.setVisibility(View.GONE);
                    linearcall.setVisibility(View.VISIBLE);
                } else if (radioboth.isChecked()) {
                    linearsms.setVisibility(View.VISIBLE);
                    linearcall.setVisibility(View.VISIBLE);

                }
            }
        });

        chsms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    POsms.setText("U");
//                    POsms.setEnabled(false);
POsms.setVisibility(View.GONE);
                } else {
//                    POsms.setEnabled(true);
//                    POsms.setText("");
                    POsms.setVisibility(View.VISIBLE);
                }

            }
        });


        chcall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    POcall.setText("U");
//                    POcall.setEnabled(false);
                    POcall.setVisibility(View.GONE);

                } else {
//                    POcall.setEnabled(true);
//                    POcall.setText("");
                    POcall.setVisibility(View.VISIBLE);
                }

            }
        });


        POsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Schoolname1 = (POschoolname.getText().toString());
                String Schooladdr1 = (POSchooladdr.getText().toString());
                String SchoolPhno1 = (POphno.getText().toString());
                String EmailId1 = (POemailid.getText().toString());
                String principalname1 = (POprinciname.getText().toString());
                int selectedRbIndex1 = getModuleId(POmodule.getCheckedRadioButtonId());
                String mobileno1 = (POmobno.getText().toString());
                String FromDate1 = (POfromdate.getText().toString());
                String ToDate1 = (POtodate.getText().toString());
                String DataDate1 = (POdatadate.getText().toString());
                String Billdate = (PObilldate.getText().toString());
                String SMS = (POsms.getText().toString());
                String Call = (POcall.getText().toString());
                String Remarks1 = (POremarks.getText().toString());

                if (Schoolname1.isEmpty() || Schooladdr1.isEmpty() || SchoolPhno1.length() != 10 || principalname1.isEmpty() || mobileno1.isEmpty() || mobileno1.length() != 10 || FromDate1.isEmpty() || ToDate1.isEmpty() || Billdate.isEmpty() || (POdbrequried.getCheckedRadioButtonId() == -1)) {
                    if (Schoolname1.isEmpty()) {
                        POschoolname.setError("Enter Your School Name");
                    }

                    if (Schooladdr1.isEmpty()) {
                        POSchooladdr.setError("Enter Your School Address");
                    }

                    if (SchoolPhno1.length() != 10) {
                        POphno.setError("Enter valid Phone Number");
                    }
                    if (principalname1.isEmpty()) {
                        POprinciname.setError("Enter Your Principal name");
                    }
                    if (mobileno1.isEmpty()) {
                        POmobno.setError("Enter Your Mobile number");
                    }
                    if (mobileno1.length() != 10) {
                        POmobno.setError("Enter Valid Mobile number");
                    }

                    if (FromDate1.isEmpty()) {
                        POfromdate.setError("Enter Your Start Date");
                    }

                    if (ToDate1.isEmpty()) {
                        POtodate.setError("Enter Your End Date");
                    }
                    if (Billdate.isEmpty()) {
                        PObilldate.setError("Enter Your Bill Date");
                    }
                    if (DataDate1.isEmpty()) {
                        POtodate.setError("Enter The Date to receive your data");
                    }
//                    if (SMS.isEmpty()) {
//                        POsms.setError("Enter number of SMS");
//                    }
//                    if (Call.isEmpty()) {
//                        POcall.setError("Enter The number of calls");
//                    }
                    else if (POdbrequried.getCheckedRadioButtonId() == -1) {
                        alert("Select the database for required class");

//                        Toast.makeText(Create_PO.this, "Select the database for required class", Toast.LENGTH_SHORT).show();
                    }
                }
//
                else {
                    insertValues();
//                    Toast.makeText(CreatePoc.this, "Successfully Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });


        imageFromDatepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateType = 1;
                showDialog(999);
            }
        });
        imageToDatepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateType = 2;
                showDialog(999);
            }
        });

        imgdatadatepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateType = 3;
                showDialog(999);
            }
        });
        imgbilldate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateType = 4;
                showDialog(999);
            }
        });
        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }

    int getModuleId(int radioId) {
        int selectedRbIndex = POmodule.indexOfChild(findViewById(radioId));
        return selectedRbIndex;
    }

    boolean getDemoId(int radioId) {
        int selectedRbIndex = POdemo.indexOfChild(findViewById(radioId));
        if (selectedRbIndex == 1)
            return true;
        else {
            return false;
        }
    }

    int getDBrequiredId(int radioId) {
        int selectedRbIndex = POdbrequried.indexOfChild(findViewById(radioId));
        return selectedRbIndex;
    }


    boolean getScopeId(int radioId) {
        int selectedRbIndex = POscope.indexOfChild(findViewById(radioId));
        if (selectedRbIndex == 1)
            return true;
        else {
            return false;
        }
    }

    private void showDate(int year, int month, int day) {
        String strDateTime;
        this.year = year;
        this.month = month;
        this.day = day;
        Calendar cal2 = Calendar.getInstance();
        cal2.set(year, month, day, hour, minute);
        SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yy");
        strDateTime = df2.format(cal2.getTime());
        if (dateType == 1) {
            POfromdate.setText(strDateTime);
        } else if (dateType == 2) {
            POtodate.setText(strDateTime);
        } else if (dateType == 3) {
            POdatadate.setText(strDateTime);
        } else if (dateType == 4) {
            PObilldate.setText(strDateTime);
        }
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            showDate(arg1, arg2, arg3);
        }
    };


    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(Create_PO.this, myDateListener, year, month, day);
        }


        return null;
    }


    private void insertValues() {
        strSchoolname = POschoolname.getText().toString();
        strAddress = POSchooladdr.getText().toString();
        strphoneno = POphno.getText().toString();
        strEmailId = POemailid.getText().toString();
        strPrincipalname = POprinciname.getText().toString();
        strMobilenumber = POmobno.getText().toString();
        strFromdate = POfromdate.getText().toString();
        strdatadate = POdatadate.getText().toString();
        strTodate = POtodate.getText().toString();
        intModule = getModuleId(POmodule.getCheckedRadioButtonId()) + 1;
        strSms = POsms.getText().toString();
        strCall = POcall.getText().toString();
        intdbrequired = getDBrequiredId(POdbrequried.getCheckedRadioButtonId()) + 1;
        strbilldate = PObilldate.getText().toString();
        strRemarks = POremarks.getText().toString();


        String serverUrl = serverPath + "CreatePOSchool";
        Log.d("URL", serverUrl);


        Log.d("Enter Value", "SchoolName=" + strSchoolname + " SchoolAddress=" + strAddress + " SchoolMobileNo=" + strphoneno +
                " SchoolEmail=" + strEmailId + " NameofPrincipal=" + strPrincipalname + " ConfigureMobileMGT=" + strMobilenumber +
                " POCFromdate=" + strFromdate + " POCTodate=" + strTodate + " POCModule=" + intModule + " DatabaseThrough=" +
                intdbrequired + " No od sms="+strSms+"No of call"+strCall + intdemo + " Remarks=" + strRemarks);


//        Log.d("JsonString", jsonParam);
        if (isNetworkConnected()) {


            requestQueue = Volley.newRequestQueue(this);

            StringRequest strRequest = new StringRequest(Request.Method.POST, serverUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Server Response", response);
                            try {
                                hidePDialog();
//                                POsubmit.setEnabled(true);
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("CreatePOSchool");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject temp = jsonArray.getJSONObject(i);
//
                                    if (Integer.parseInt(temp.getString("Status")) == 1) {

                                        clearPo();

                                        LayoutInflater layoutInflater = LayoutInflater.from(Create_PO.this);
                                        View promptView = layoutInflater.inflate(R.layout.dailog_ok, null);

                                        final AlertDialog dlgAlert = new AlertDialog.Builder(Create_PO.this).create();

                                        dlgAlert.setTitle("Alert");

                                        dlgAlert.setMessage(temp.getString("Message"));
//                                        dlgAlert.getWindow().setBackgroundDrawableResource(R.color.background);
                                        dlgAlert.setIcon(android.R.drawable.ic_dialog_info);
                                        dlgAlert.setCancelable(false);
//                                        dlgAlert.create().show();

                                        final Button Okbtn = (Button) promptView.findViewById(R.id.okbtn);


                                        Okbtn.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                dlgAlert.dismiss();
                                                finish();

                                            }
                                        });
                                        dlgAlert.setView(promptView);

                                        dlgAlert.show();
                                    } else {
                                        String message=temp.getString("Message");
                                        alert(message);

//                                        Toast.makeText(Create_PO.this, temp.getString("Message"), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (Exception e) {
                                hidePDialog();

                                alert("Exception");
//                                POsubmit.setEnabled(true);
//                                Toast.makeText(Create_PO.this, "Exception", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hidePDialog();
//                            POsubmit.setEnabled(true);
                            String msg="Values Not Inserted";
                            alert(msg);
//                            Toast.makeText(Create_PO.this, "Values Not Inserted", Toast.LENGTH_SHORT).show();
                            Log.d("Server Response", error.toString());
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("LoginID", userId);
                    params.put("SchoolName", strSchoolname);
                    params.put("SchoolAddress", strAddress);
                    params.put("SchoolMobileNo", strphoneno);
                    params.put("SchoolEmail", strEmailId);
                    params.put("NameofPrincipal", strPrincipalname);
                    params.put("ConfigureMobileMGT", strMobilenumber);
                    params.put("POFromdate", strFromdate);
                    params.put("POTodate", strTodate);
                    params.put("POModule", String.valueOf(intModule));
                    params.put("DatabaseThrough", String.valueOf(intdbrequired));
                    params.put("NoOfSMS", strSms);
                    params.put("NoOfCall", strCall);
                    params.put("DataDate", strdatadate);
                    params.put("BillingStartDate", strbilldate);
                    params.put("Remarks", strRemarks);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    return params;
                }
            };

            requestQueue.add(strRequest);

            pDialog = new ProgressDialog(Create_PO.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        } else {
            POsubmit.setEnabled(true);
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

    private void clearPo() {
//        POCschoolname,POCSchooladdr,POCphno,POCemailid,POCprinciname,POCmobno,POCfromdate,POCtodate,POCremarks;
//        POCmodule,POCdbrequried,POCdemo,POCscope;
        POschoolname.setText("");
        POSchooladdr.setText("");
        POphno.setText("");

        POemailid.setText("");
        POprinciname.setText("");
        POmobno.setText("");

        POfromdate.setText("");
        POtodate.setText("");
        POdatadate.setText("");
        POremarks.setText("");

        POmodule.clearCheck();
        POdbrequried.clearCheck();
        POdemo.clearCheck();
        POscope.clearCheck();
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


    private void createporetrofit() {

        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("SchoolName", strSchoolname);
        jsonObject.addProperty("SchoolAddress", strAddress);
        jsonObject.addProperty("SchoolMobileNo", strphoneno);
        jsonObject.addProperty("SchoolEmail", strEmailId);
        jsonObject.addProperty("NameofPrincipal", strPrincipalname);
        jsonObject.addProperty("ConfigureMobileMGT", strMobilenumber);
        jsonObject.addProperty("POFromdate", strFromdate);
        jsonObject.addProperty("POTodate", strTodate);
        jsonObject.addProperty("POModule", String.valueOf(intModule));
        jsonObject.addProperty("DatabaseThrough", String.valueOf(intdbrequired));
        jsonObject.addProperty("NoOfSMS", strSms);
        jsonObject.addProperty("NoOfCall", strCall);
        jsonObject.addProperty("DataDate", strdatadate);
        jsonObject.addProperty("BillingStartDate", strbilldate);
        jsonObject.addProperty("Remarks", strRemarks);

        Log.d("createpo:req", jsonObject.toString());
        Call<JsonArray> call = apiService.Credits(jsonObject);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                try {
                    Log.d("Response", response.toString());
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    {
                        Log.d("createpo:code-res", response.code() + " - " + response.toString());
                        if (response.code() == 200 || response.code() == 201) {
                            Log.d("Response", response.body().toString());
                            JSONArray js = new JSONArray(response.body().toString());
                            if (js.length() > 0) {
                                JSONObject jsonObject = js.getJSONObject(0);
                                if (Integer.parseInt(jsonObject.getString("Status")) == 1) {

                                    clearPo();

                                    LayoutInflater layoutInflater = LayoutInflater.from(Create_PO.this);
                                    View promptView = layoutInflater.inflate(R.layout.dailog_ok, null);

                                    final AlertDialog dlgAlert = new AlertDialog.Builder(Create_PO.this).create();

                                    dlgAlert.setTitle("Alert");

                                    dlgAlert.setMessage(jsonObject.getString("Message"));
//                                        dlgAlert.getWindow().setBackgroundDrawableResource(R.color.background);
                                    dlgAlert.setIcon(android.R.drawable.ic_dialog_info);
                                    dlgAlert.setCancelable(false);
//                                        dlgAlert.create().show();

                                    final Button Okbtn = (Button) promptView.findViewById(R.id.okbtn);


                                    Okbtn.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            dlgAlert.dismiss();
                                            finish();

                                        }
                                    });
                                    dlgAlert.setView(promptView);

                                    dlgAlert.show();
                                } else {
                                    String message=jsonObject.getString("Message");
                                    alert(message);

//                                    Toast.makeText(Create_PO.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
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

    private void alert(String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Create_PO.this);
        builder.setTitle(reason);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();

    }

}
