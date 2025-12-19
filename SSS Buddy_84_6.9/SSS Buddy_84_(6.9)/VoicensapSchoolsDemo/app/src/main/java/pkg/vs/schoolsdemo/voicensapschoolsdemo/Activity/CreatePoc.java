package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import com.android.volley.RequestQueue;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.commons.validator.routines.EmailValidator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;

public class CreatePoc extends AppCompatActivity {
    EditText POCschoolname, POCcity, POCpincode, POCSchooladdr, POCphno, POCcontactperson,
            POCemailid, POCprinciname, POCmobno, POCcaption, POCfromdate,
            POCtodate, POCremarks, POCdatadate, POCcallcost, POCsmscost, POCsmscaption, POCwelcometext;
    RadioGroup POCmodule, POCdbrequried, POCdemo, POCscope;
    String etvalue;
    RequestQueue requestQueue;
    int Result;
    String Reason;
    String serverPath = "http://220.226.2.177:9000/StaffVoiceAnnouncerCloudSchool/Service.asmx/";
//    String serverPath = "http://192.168.0.77:8096/SchoolDemoAppService/Service.asmx/";
//    String serverPath = "https://creator.zoho.com/api/ganesh235/json/vims-2-0/form/Add_New_Customer/record/add/";

    ImageView imageFromDate, imageToDate, imgdatadate;

    private ProgressDialog pDialog;
//    private static final String SH_USERS = "userInfo";
//    private static final String SH_USERID = "UserId";
//    SharedPreferences shpRemember;
//    SharedPreferences.Editor ed;
    CheckBox ch_sms, ch_voice, ch_erp, ch_app, ch_unlimitedsms, ch_unlimitedcall;
    RadioGroup rg_purchasetype;
    RadioButton rb_poc, rb_live;
    String userId, SchoolID;
    TextView tv_fromdate, tv_todate, tv_pocmodule;
    String strSchoolname, strAddress, strphoneno, strEmailId, strPrincipalname, strMobilenumber, strcity, strpincode,
            strcontactperson, strcaption, strFromdate, strdatadate, strTodate, strRemarks, strunlimitedsms = "0", strpurchasetype = "1",
            strunlimitedcalls = "0", strsms = "0", strcall = "0", strcallcost = "0", strsmscost = "0", strmodule, strwelcometext;
    int intModule, intdbrequired;
    Boolean intdemo, intScope;
    private Calendar calendar;
    private int year, month, day, hour, minute;
    Button POCsubmit;
    LinearLayout linearsms, linearcall, linearLayoutParent;
    int dateType;
    EditText textIn;
    Button btnAdd;
    private List<EditText> editTextList = new ArrayList<>();
    CheckBox checkGroupOfschools,checkSingleSchools;
    int schooltype=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poc);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        textIn = (EditText) findViewById(R.id.textmobno);
        btnAdd = (Button) findViewById(R.id.addmobno);
        linearLayoutParent = (LinearLayout) findViewById(R.id.llParentmobno);

        checkGroupOfschools = (CheckBox) findViewById(R.id.checkGroupOfschools);
        checkSingleSchools = (CheckBox) findViewById(R.id.checkSingleSchools);

        userId= SharedPreference_class.getShSchlLoginid(CreatePoc.this);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String inputtxt;
                inputtxt = textIn.getText().toString();
                if (inputtxt.isEmpty()) {
                    textIn.setError("Enter the mobile number and add it");
                } else {
                    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.contactnumberlist, null);
                    EditText textOut = (EditText) addView.findViewById(R.id.textout);
                    ImageButton buttonRemove = (ImageButton) addView.findViewById(R.id.remove);
                    buttonRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((LinearLayout) addView.getParent()).removeView(addView);
                        }
                    });
                    linearLayoutParent.addView(addView);
                    editTextList.add(textOut);
                    textOut.requestFocus();
//                    textIn.setText("");
                }
            }
        });

//        btnNext = (Button) findViewById(R.id.voiceRec_btnNext);
//        btnNext.setOnClickListener(this);

//        rlVoicePreview.setVisibility(View.GONE);
//        btnNext.setEnabled(false);

//        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
//        userId = shpRemember.getString(SH_USERID, null);

        POCschoolname = (EditText) findViewById(R.id.pocschoolname);
        POCSchooladdr = (EditText) findViewById(R.id.pocschooladdr);
        POCphno = (EditText) findViewById(R.id.pocschoolphno);
        POCcity = (EditText) findViewById(R.id.poccity);
        POCcontactperson = (EditText) findViewById(R.id.poccontactperson);
        POCpincode = (EditText) findViewById(R.id.pocpincode);
        POCemailid = (EditText) findViewById(R.id.pocschoolmailid);
        POCfromdate = (EditText) findViewById(R.id.pocfromdate);
        POCtodate = (EditText) findViewById(R.id.poctodate);
//        POCremarks = (EditText) findViewById(R.id.pocremarks);
        POCcaption = (EditText) findViewById(R.id.pocschoolsmscaption);
        POCcallcost = (EditText) findViewById(R.id.callmodpoc);
        POCsmscost = (EditText) findViewById(R.id.smsmodpoc);
        POCwelcometext = (EditText) findViewById(R.id.pocwelcometext);
        rg_purchasetype = (RadioGroup) findViewById(R.id.poc_purchasetype);
        rb_live = (RadioButton) findViewById(R.id.radio_purchasetype_live);
        rb_poc = (RadioButton) findViewById(R.id.radio_purchasetype_poc);
        ch_app = (CheckBox) findViewById(R.id.ch_app);
        ch_erp = (CheckBox) findViewById(R.id.ch_erp);
        ch_sms = (CheckBox) findViewById(R.id.ch_sms);
        ch_voice = (CheckBox) findViewById(R.id.ch_voice);
        ch_unlimitedsms = (CheckBox) findViewById(R.id.chsmspoc);
        ch_unlimitedcall = (CheckBox) findViewById(R.id.chcallpoc);
        tv_fromdate = (TextView) findViewById(R.id.tv_pocfromdate);
        tv_todate = (TextView) findViewById(R.id.tv_poctodate);
        tv_pocmodule= (TextView) findViewById(R.id.tv_pocmod);
        ch_unlimitedsms.setChecked(false);
        ch_unlimitedcall.setChecked(false);
        strunlimitedsms = "0";
        strunlimitedcalls = "0";
        POCsmscost.setVisibility(View.VISIBLE);
        POCcallcost.setVisibility(View.VISIBLE);

        checkGroupOfschools.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkSingleSchools.setChecked(false);
                    schooltype=1;

                } else {

                }
            }
        });
        checkSingleSchools.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkGroupOfschools.setChecked(false);
                    schooltype=3;

                } else {

                }
            }
        });


        ch_unlimitedsms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strunlimitedsms = "1";
                    POCsmscost.setVisibility(View.GONE);
                } else {
                    strunlimitedsms = "0";
                    POCsmscost.setVisibility(View.VISIBLE);
                }
            }
        });


        rb_live.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strpurchasetype = "2";
                    tv_fromdate.setText(R.string.hint_livestartdate);
                    tv_todate.setText(R.string.hint_livetopdate);
                    ch_sms.setEnabled(true);
                    POCcaption.setEnabled(true);
                    ch_voice.setChecked(false);
                    POCcallcost.setText("");
                    POCcallcost.setEnabled(true);
                    linearcall.setVisibility(View.GONE);
                    ch_unlimitedcall.setEnabled(true);
                    tv_pocmodule.setText(R.string.hint_livemodule);
                    imageToDate.setEnabled(true);
//                    calendar.setTime(date);
//                    calendar.add(Calendar.DATE, 15); // add 10 days
                }
            }
        });
        rb_poc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strpurchasetype = "1";
                    tv_fromdate.setText(R.string.hint_Pocstartdate);
                    tv_todate.setText(R.string.hint_Pocstopdate);
                    ch_sms.setEnabled(false);
                    POCcaption.setEnabled(false);
                    ch_voice.setChecked(true);
                    POCcallcost.setEnabled(false);
                    POCcallcost.setText("20");
                    ch_unlimitedcall.setEnabled(false);
                    tv_pocmodule.setText(R.string.hint_pocmodule);
                    imageToDate.setEnabled(false);

                }

            }
        });


        ch_unlimitedcall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strunlimitedcalls = "1";
                    POCcallcost.setVisibility(View.GONE);
                } else {
                    strunlimitedcalls = "0";
                    POCcallcost.setVisibility(View.VISIBLE);
                }
            }
        });
        linearsms = (LinearLayout) findViewById(R.id.linearLayoutsmspoc);
        linearcall = (LinearLayout) findViewById(R.id.linearLayoutcallpoc);

        imageFromDate = (ImageView) findViewById(R.id.imagefromdate);
        imageToDate = (ImageView) findViewById(R.id.imagetodate);
        showDate(year, month, day);
        POCsubmit = (Button) findViewById(R.id.pocsubmit);
        ch_sms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearsms.setVisibility(View.VISIBLE);
                    strsms = "1";
                } else {
                    linearsms.setVisibility(View.GONE);
                    strsms = "0";
                }
            }
        });

        ch_voice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearcall.setVisibility(View.VISIBLE);
                    strcall = "1";
                } else {
                    linearcall.setVisibility(View.GONE);
                    strcall = "0";
                }
            }
        });
        POCsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Schoolname = (POCschoolname.getText().toString());
                String Schoolcity = (POCcity.getText().toString());
                String SchoolPhno = (POCphno.getText().toString());
                String EmailId = (POCemailid.getText().toString());
                String principalname = (POCcontactperson.getText().toString());
//                int selectedRbIndex = getModuleId(POCmodule.getCheckedRadioButtonId());
//                String mobileno = (POCmobno.getText().toString());
                String FromDate = (POCfromdate.getText().toString());
                String ToDate = (POCtodate.getText().toString());

                String strcallcost = (POCcallcost.getText().toString());
//                String strsmscost = (POCsmscost.getText().toString());
//                String DataDate = (POCdatadate.getText().toString());
//                String Remarks = (POCremarks.getText().toString());

                if (Schoolname.isEmpty() || Schoolcity.isEmpty() || SchoolPhno.length() != 10 || FromDate.isEmpty() || ToDate.isEmpty() || ((!myEmailValidation(EmailId)) && (!EmailId.isEmpty()))) {//|| (ch_sms.isChecked())
                    if (Schoolname.isEmpty()) {
                        POCschoolname.setError("Enter Your School Name");
                    }
                    if (Schoolcity.isEmpty()) {
                        POCcity.setError("Enter Your School city");
                    }
                    if (SchoolPhno.length() != 10) {
                        POCphno.setError("Enter valid Contact Number");
                    }
                    if ((!myEmailValidation(EmailId)) && (!EmailId.isEmpty())) {
                        POCemailid.setError("Enter Valid Mobile number");
                    }
                    if (FromDate.isEmpty()) {
                        POCfromdate.setError("Enter the POC from date");
                    }
                    if (ToDate.isEmpty()) {
                        POCtodate.setError("Enter the POC To date");
                        Log.d("log1", "log1");
                    }
//                    if((rb_poc.isChecked())||(rb_live.isChecked())){
//                        showToast("Check the purchase type..");
//                    }
                } else {
                    if ((!ch_voice.isChecked()) && (!ch_sms.isChecked())) {
                        alert("Please check voice or sms");
                    } else if ((ch_voice.isChecked()) && (ch_sms.isChecked())) {
                        if (smscheck() && voicecheck()) {
                            createpocretrofit();
                        }
                    } else if (ch_voice.isChecked()) {
                        if (voicecheck()) {
                            createpocretrofit();
                        }
                    } else if (ch_sms.isChecked()) {
                        if (smscheck()) {
                            createpocretrofit();
                        }

                    }

                }
            }


//                else {
//                    createpocretrofit();
//                }

        });

        imageFromDate.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                dateType = 1;
                showDialog(999);
            }
        });
        imageToDate.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                dateType = 2;
                showDialog(999);
            }
        });


        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        if(strpurchasetype.equals("1")){
            tv_fromdate.setText(R.string.hint_Pocstartdate);
            tv_todate.setText(R.string.hint_Pocstopdate);
            ch_sms.setEnabled(false);
            POCcaption.setEnabled(false);
            ch_voice.setChecked(true);
            POCcallcost.setText("20");
            POCcallcost.setEnabled(false);
            ch_unlimitedcall.setEnabled(false);
            linearcall.setVisibility(View.VISIBLE);
            tv_pocmodule.setText(R.string.hint_pocmodule);
            strcall="1";
            imageToDate.setEnabled(false);

        }
        else{
            tv_fromdate.setText(R.string.hint_livestartdate);
            tv_todate.setText(R.string.hint_livetopdate);
            ch_sms.setEnabled(true);
            POCcaption.setEnabled(true);
            ch_voice.setChecked(false);
            POCcallcost.setEnabled(true);
            POCcallcost.setText("");
            linearcall.setVisibility(View.GONE);
            ch_unlimitedcall.setEnabled(true);
            tv_pocmodule.setText(R.string.hint_livemodule);
            imageToDate.setEnabled(true);
        }
    }

    private boolean smscheck() {
        String strsmscost1 = (POCsmscost.getText().toString());
        String smscaption = (POCcaption.getText().toString());
        if (ch_unlimitedsms.isChecked()) {
            return true;
        } else {
            if ((strsmscost1.isEmpty()) && (smscaption.isEmpty())) {
                POCsmscaption.setError("Enter the sms caption");
                alert("Check Unlimited or Enter the number of SMS");
                return false;
            } else {
                return true;
            }
        }
    }


    private boolean voicecheck() {
        String strcallcost1 = (POCcallcost.getText().toString());

        if (ch_unlimitedcall.isChecked()) {
            return true;
        } else {
            if (strcallcost1.isEmpty()) {
                alert("Check Unlimited or Enter the number of CALLS");
                return false;
            } else {
                return true;
            }
        }
    }

    private boolean myEmailValidation(String strEmail) {
        return EmailValidator.getInstance().isValid(strEmail);
    }

    boolean getDemoId(int radioId) {
        int selectedRbIndex = POCdemo.indexOfChild(findViewById(radioId));
        if (selectedRbIndex == 1)
            return true;
        else {
            return false;
        }
    }

    int getDBrequiredId(int radioId) {
        int selectedRbIndex = POCdbrequried.indexOfChild(findViewById(radioId));
        return selectedRbIndex;
    }

    boolean getScopeId(int radioId) {
        int selectedRbIndex = POCscope.indexOfChild(findViewById(radioId));
        if (selectedRbIndex == 1)
            return true;
        else {
            return false;
        }
    }

    int getModuleId(int radioId) {
        int selectedRbIndex = POCmodule.indexOfChild(findViewById(radioId));
        return selectedRbIndex;
    }

    private void showDate(int year, int month, int day) {
        String strDateTime;
        this.year = year;
        this.month = month;
        this.day = day;
        Calendar cal2 = Calendar.getInstance();
        cal2.set(year, month, day, hour, minute);
        SimpleDateFormat df2 = new SimpleDateFormat("MMM dd, yyyy");//yyyyMMdd");//dd/mm/yyyy");//dd/mm/yyyyMM/dd/yy
        strDateTime = df2.format(cal2.getTime());
        if (dateType == 1) {
            POCfromdate.setText(strDateTime);
        } else if (dateType == 2) {
            cal2.add(Calendar.DATE, 15);
            POCtodate.setText(strDateTime);

        } else if (dateType == 3) {
            POCdatadate.setText(strDateTime);
        }
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            showDate(arg1, arg2, arg3);
            if (strpurchasetype.equals("1")) {
                Calendar cal2 = Calendar.getInstance();
                cal2.set(year, month, day, hour, minute);
                SimpleDateFormat df2 = new SimpleDateFormat("MMM dd, yyyy");//yyyyMMdd");//dd/mm/yyyy");//dd/mm/yyyyMM/dd/yy
//            strDateTime = df2.format(cal2.getTime());
                cal2.add(Calendar.DATE, 15);
                String end_date = df2.format(cal2.getTime());
                POCtodate.setText(end_date);
            }
        }
    };


    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(CreatePoc.this, myDateListener, year, month, day);
        }


        return null;
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

    private void clearPoc() {
        POCschoolname.setText("");
        POCSchooladdr.setText("");
        POCphno.setText("");

        POCemailid.setText("");
        POCprinciname.setText("");
//        POCmobno.setText("");

        POCfromdate.setText("");
        POCtodate.setText("");
        POCdatadate.setText("");
//        POCremarks.setText("");

//        POCmodule.clearCheck();
//        POCdbrequried.clearCheck();
//        POCdemo.clearCheck();
//        POCscope.clearCheck();
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


    private void createpocretrofit() {
        etvalue = textIn.getText().toString();
        String value = "";
        for (int j = 0; j < editTextList.size(); j++) {
            value = value + editTextList.get(j).getText().toString() + ",";
        }
        if (value.equals("")) {
            value = etvalue + ",";
        } else {
            value = etvalue + "," + value;
        }
        value = value.substring(0, value.length() - 1);
        Log.d("test", value);
        strSchoolname = POCschoolname.getText().toString();
        strAddress = POCSchooladdr.getText().toString();
        strMobilenumber = POCphno.getText().toString();
        strcity = POCcity.getText().toString();
        strpincode = POCpincode.getText().toString();
        strEmailId = POCemailid.getText().toString();
        strcontactperson = POCcontactperson.getText().toString();
        strcaption = POCcaption.getText().toString();
        strcallcost = POCcallcost.getText().toString();
        strwelcometext = POCwelcometext.getText().toString();
        strsmscost = POCsmscost.getText().toString();
        strFromdate = POCfromdate.getText().toString();
        strTodate = POCtodate.getText().toString();

        Log.d("SMSandCall", strcall + strsms);
        if ((strsms.equals("1")) && (strcall.equals("1"))) {
            strmodule = "1~2";
        } else if ((strsms.equals("1")) && (strcall.equals("0"))) {
            strmodule = "2";
        } else if ((strsms.equals("0")) && (strcall.equals("1"))) {
            strmodule = "1";
        }
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

//        {"LoginId":""
//            "schoolId":""
//            "InstitutionName":""
//            "Address":""
//            "pincode":""
//            "City":""
//            "ContactNumber1":""
//            "ContactPerson1":""
//            "Email":""
//            "caption":""
//            "peroidFrom":""
//            "peroidTo":""
//            "Modules":""
//            "processType":""
//            "isUnlimitedCalls":""
//            "isUnlimitedSMS":""
//            "smsCount":""
//            "callsCount":""
//            "WelcomefileText":""
//            "LoginName":""
//            "password":""
//            "MgmtNumbers":""}

        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoginId", userId);
        jsonObject.addProperty("schoolId", "0");
        jsonObject.addProperty("InstitutionName", strSchoolname);
        jsonObject.addProperty("Address", strAddress);
        jsonObject.addProperty("City", strcity);
        jsonObject.addProperty("pincode", strpincode);
        jsonObject.addProperty("ContactNumber1", strMobilenumber);
        jsonObject.addProperty("ContactPerson1", strcontactperson);
        jsonObject.addProperty("Email", strEmailId);
        jsonObject.addProperty("Modules", strmodule);
        jsonObject.addProperty("caption", strcaption);
        jsonObject.addProperty("peroidFrom", strFromdate);
        jsonObject.addProperty("peroidTo", strTodate);
        jsonObject.addProperty("processType", "add");
        jsonObject.addProperty("isUnlimitedCalls", strunlimitedcalls);
        jsonObject.addProperty("isUnlimitedSMS", strunlimitedsms);
        jsonObject.addProperty("callsCount", strcallcost);
        jsonObject.addProperty("smsCount", strsmscost);
        jsonObject.addProperty("WelcomefileText", strwelcometext);
        jsonObject.addProperty("PurchaseType", strpurchasetype);
        jsonObject.addProperty("LoginName", "");
        jsonObject.addProperty("password", "");
        jsonObject.addProperty("MgmtNumbers", value);

        jsonObject.addProperty("UniversalDID", false);
        jsonObject.addProperty("SchoolType", schooltype);

        Log.d("createpoc:req", jsonObject.toString());


        Call<JsonArray> call = apiService.CreatePOC(jsonObject);//, bodyFile);


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
                                if (jsonObject.getString("Status").equals("1")) {
                                    SchoolID = jsonObject.getString("schoolID");
//                                    clearPoc();
                                    LayoutInflater layoutInflater = LayoutInflater.from(CreatePoc.this);
                                    View promptView = layoutInflater.inflate(R.layout.dilogpoc, null);

                                    final AlertDialog dlgAlert = new AlertDialog.Builder(CreatePoc.this).create();
//                                    dlgAlert.setTitle("Alert");
//                                    dlgAlert.setMessage(jsonObject.getString("Message"));
//                                    dlgAlert.setIcon(android.R.drawable.ic_dialog_info);
                                    dlgAlert.setCancelable(false);
                                    final Button Okbtn = (Button) promptView.findViewById(R.id.okbtn);
                                    ImageView imgrecord = (ImageView) promptView.findViewById(R.id.imgrecord);
                                    TextView tvmessage = (TextView) promptView.findViewById(R.id.pocmessage);
                                    tvmessage.setText(jsonObject.getString("Message"));
                                    Okbtn.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            dlgAlert.dismiss();
                                            finish();
                                        }
                                    });

                                    imgrecord.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            Intent i = new Intent(CreatePoc.this, Recordwelcomefile.class);
                                            i.putExtra("SchoolID", SchoolID);
                                            startActivity(i);
                                        }
                                    });
                                    dlgAlert.setView(promptView);
                                    dlgAlert.show();
                                } else {
                                    String reason=jsonObject.getString("Message");
                                    alert(reason);
//                                    Toast.makeText(CreatePoc.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(CreatePoc.this);
        builder.setTitle(reason);
//            builder.setMessage(month);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();

    }
}
