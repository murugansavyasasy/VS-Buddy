package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;

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
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;

public class SuportVerify extends AppCompatActivity {
    EditText POCschoolname, POCcity, POCpincode, POCSchooladdr, POCphno, POCcontactperson,
            POCemailid, POCprinciname, POCmobno, POCcaption, POCfromdate,
            POCtodate, POCremarks, POCdatadate, POCcallcost, POCsmscost, POCsmscaption, POCusername, POCpassword, POCwelcometext;
    RadioGroup POCmodule, POCdbrequried, POCdemo, POCscope;
    String etvalue;
    RequestQueue requestQueue;
    int Result;
    String Reason;
    RadioGroup rg_purchasetype;
    RadioButton rb_poc, rb_live;
    String serverPath = "http://220.226.2.177:9000/StaffVoiceAnnouncerCloudSchool/Service.asmx/";
//    String serverPath = "http://192.168.0.77:8096/SchoolDemoAppService/Service.asmx/";
//    String serverPath = "https://creator.zoho.com/api/ganesh235/json/vims-2-0/form/Add_New_Customer/record/add/";

    ImageView imageFromDate, imageToDate, imgdatadate;

    private ProgressDialog pDialog;
    private static final String SH_USERS = "userInfo";
    private static final String SH_USERID = "UserId";
    SharedPreferences shpRemember;
    SharedPreferences.Editor ed;
    CheckBox ch_sms, ch_voice, ch_erp, ch_app, ch_unlimitedsms, ch_unlimitedcall;
    String userId, SchoolID;
    String strSchoolname, strwelcometext, strAddress, strschoolId, strphoneno, strEmailId, strPrincipalname, strMobilenumber, strcity, strpincode,
            strcontactperson, strcaption, strFromdate, strdatadate, strTodate, strRemarks, strunlimitedsms = "0",
            strunlimitedcalls = "0", strsms = "0", strcall = "0", strcallcost, strsmscost, strmodule, strusername, strpassword, strpurchasetype = "1";
    int intModule, intdbrequired;
    Boolean intdemo, intScope;
    private Calendar calendar;
    private int year, month, day, hour, minute;
    Button POCsubmit;
    LinearLayout linearsms, linearcall, linearLayoutParent;
    int dateType;
    EditText textIn;
    Button btnAdd;
    int iRequestCode;
    private List<EditText> editTextList = new ArrayList<>();
    CheckBox checkDID;
    boolean checkDIDNumber = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suport_verify);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        iRequestCode = getIntent().getExtras().getInt("REQUEST_CODE", 0);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            strschoolId = extras.getString("POCid");
            strSchoolname = extras.getString("POCschoolname");
            strAddress = extras.getString("POCaddress");
            strcity = extras.getString("POCcity");
            strMobilenumber = extras.getString("POCmobno");
            strEmailId = extras.getString("POCemailid");
            strPrincipalname = extras.getString("POCprinciname");
            strFromdate = extras.getString("POCfromdate");
            strTodate = extras.getString("POCtodate");
            strmodule = extras.getString("POCmodule");
            strcallcost = extras.getString("POCcallcost");
            strsmscost = extras.getString("POCsmscost");
            strcaption = extras.getString("POCcaption");
            strpincode = extras.getString("POCpincode");
            strwelcometext = extras.getString("POCwelcomefiletext");
            strpurchasetype = extras.getString("POCpurchasetype");
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

//        textIn = (EditText) findViewById(R.id.textmobno);
//        btnAdd = (Button) findViewById(R.id.addmobno);
//        linearLayoutParent = (LinearLayout) findViewById(R.id.llParentmobno);
//

        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
        userId = shpRemember.getString(SH_USERID, null);
        POCschoolname = (EditText) findViewById(R.id.pocschoolname_SuportVerify);
        POCSchooladdr = (EditText) findViewById(R.id.pocschooladdr_SuportVerify);
        POCphno = (EditText) findViewById(R.id.pocschoolphno_SuportVerify);
        POCcity = (EditText) findViewById(R.id.poccity_SuportVerify);
        POCcontactperson = (EditText) findViewById(R.id.poccontactperson_SuportVerify);
        POCpincode = (EditText) findViewById(R.id.pocpincode_SuportVerify);
        POCemailid = (EditText) findViewById(R.id.pocschoolmailid_SuportVerify);
        POCfromdate = (EditText) findViewById(R.id.pocfromdate_SuportVerify);
        POCtodate = (EditText) findViewById(R.id.poctodate_SuportVerify);
        POCremarks = (EditText) findViewById(R.id.pocremarks_SuportVerify);
        POCcaption = (EditText) findViewById(R.id.pocschoolsmscaption_SuportVerify);
        POCcallcost = (EditText) findViewById(R.id.callmodpoc_SuportVerify);
        POCsmscost = (EditText) findViewById(R.id.smsmodpoc_SuportVerify);
        POCwelcometext = (EditText) findViewById(R.id.pocwelcometext_SuportVerify);
        POCusername = (EditText) findViewById(R.id.pocusername_SuportVerify);
        POCpassword = (EditText) findViewById(R.id.pocpassword_SuportVerify);
        ch_app = (CheckBox) findViewById(R.id.ch_app_SuportVerify);
        ch_erp = (CheckBox) findViewById(R.id.ch_erp_SuportVerify);
        ch_sms = (CheckBox) findViewById(R.id.ch_sms_SuportVerify);
        ch_voice = (CheckBox) findViewById(R.id.ch_voice_SuportVerify);
        ch_unlimitedsms = (CheckBox) findViewById(R.id.chsmspoc_SuportVerify);
        ch_unlimitedcall = (CheckBox) findViewById(R.id.chcallpoc_SuportVerify);
        linearsms = (LinearLayout) findViewById(R.id.linearLayoutsmspoc_SuportVerify);
        linearcall = (LinearLayout) findViewById(R.id.linearLayoutcallpoc_SuportVerify);

        checkDID = (CheckBox) findViewById(R.id.checkDID);
        checkDID.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkDIDNumber = true;
                } else {
                    checkDIDNumber = false;
                }
            }
        });

        rg_purchasetype = (RadioGroup) findViewById(R.id.poc_purchasetype_support);
        rb_live = (RadioButton) findViewById(R.id.radio_purchasetype_live_support);
        rb_poc = (RadioButton) findViewById(R.id.radio_purchasetype_poc_support);
        ch_unlimitedsms.setChecked(false);
        ch_unlimitedcall.setChecked(false);
        strunlimitedsms = "0";
        strunlimitedcalls = "0";
        POCsmscost.setVisibility(View.VISIBLE);
        POCcallcost.setVisibility(View.VISIBLE);
//        ch_unlimitedsms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    strunlimitedsms = "1";
//                    POCsmscost.setVisibility(View.GONE);
//                } else {
//                    strunlimitedsms = "0";
//                    POCsmscost.setVisibility(View.VISIBLE);
//                }
//            }
//        });

        POCschoolname.setText(strSchoolname);
        POCSchooladdr.setText(strAddress);
        POCphno.setText(strMobilenumber);
        POCcity.setText(strcity);
        POCcontactperson.setText(strPrincipalname);
        POCpincode.setText(strpincode);
        POCemailid.setText(strEmailId);
        POCfromdate.setText(strFromdate);
        POCtodate.setText(strTodate);
        POCcaption.setText(strcaption);
        POCwelcometext.setText(strwelcometext);
        if (strmodule.equals("1~2")) {
            ch_sms.setChecked(true);
            ch_voice.setChecked(true);
            linearcall.setVisibility(View.VISIBLE);
            linearsms.setVisibility(View.VISIBLE);
            strsms = "1";
            strcall = "1";
        } else if (strmodule.equals("1")) {
            ch_sms.setChecked(false);
            ch_voice.setChecked(true);
            linearcall.setVisibility(View.VISIBLE);
            linearsms.setVisibility(View.GONE);
            strsms = "0";
            strcall = "1";
        } else if (strmodule.equals("2")) {
            ch_sms.setChecked(true);
            ch_voice.setChecked(false);
            linearcall.setVisibility(View.GONE);
            linearsms.setVisibility(View.VISIBLE);
            strsms = "1";
            strcall = "0";
        }

        Log.d("sms&call", strcallcost + strsmscost);
        if (strsmscost.equals("Unlimited")) {
            ch_unlimitedsms.setChecked(true);
            POCsmscost.setVisibility(View.GONE);
//            showToast("gone");
        } else {
            POCsmscost.setText(strsmscost);
            ch_unlimitedsms.setChecked(false);
            POCsmscost.setVisibility(View.VISIBLE);
//            showToast("Visible");
        }
        if (strcallcost.equals("Unlimited")) {
            ch_unlimitedcall.setChecked(true);
            POCcallcost.setVisibility(View.GONE);

        } else {
            POCcallcost.setText(strcallcost);
            ch_unlimitedcall.setChecked(false);
            POCcallcost.setVisibility(View.VISIBLE);

        }

        if (strpurchasetype.equals("1")) {
            rb_poc.setChecked(true);

        } else if (strpurchasetype.equals("2")) {
            rb_live.setChecked(true);
        }
        ch_sms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearsms.setVisibility(View.VISIBLE);
                } else {
                    linearsms.setVisibility(View.GONE);
                }
            }
        });
        ch_voice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearcall.setVisibility(View.VISIBLE);
                } else {
                    linearcall.setVisibility(View.GONE);
                }
            }
        });
        ch_unlimitedcall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    POCcallcost.setVisibility(View.GONE);
                } else {
                    POCcallcost.setVisibility(View.VISIBLE);
                }
            }
        });


        ch_unlimitedsms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    POCsmscost.setVisibility(View.GONE);
                } else {
                    POCsmscost.setVisibility(View.VISIBLE);
                }
            }
        });

        rb_live.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strpurchasetype = "2";
                }
            }
        });
        rb_poc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strpurchasetype = "1";
                }

            }
        });
        imageFromDate = (ImageView) findViewById(R.id.imagefromdate_SuportVerify);
        imageToDate = (ImageView) findViewById(R.id.imagetodate_SuportVerify);
        showDate(year, month, day);
        POCsubmit = (Button) findViewById(R.id.pocsubmit_SuportVerify);
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
                String smscaption = (POCcaption.getText().toString());
                String username = (POCusername.getText().toString());
                String password = (POCpassword.getText().toString());

                if (Schoolname.isEmpty() || Schoolcity.isEmpty() || SchoolPhno.length() != 10 || FromDate.isEmpty() || ToDate.isEmpty() || ((!myEmailValidation(EmailId)) && (!EmailId.isEmpty())) ||
                        username.isEmpty() || password.isEmpty()) {
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
                        POCemailid.setError("Enter Valid Email-Id");
                    }
                    if (FromDate.isEmpty()) {
                        POCfromdate.setError("Enter the POC from date");
                    }
                    if (ToDate.isEmpty()) {
                        POCtodate.setError("Enter the POC To date");
                    }
                    if (username.isEmpty()) {
                        POCusername.setError("Enter the username");
                        Alert("Enter the username");
                    }
                    if (password.isEmpty()) {
                        POCpassword.setError("Enter the Password");
                        Alert("Enter the Password");
                    }
                }
//
                else {

                    if ((!ch_voice.isChecked()) && (!ch_sms.isChecked())) {
                        Alert("Please check voice or sms");
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
//                    insertValues();

//                    if (!isNetworkConnected()) {
//                        createpocretrofit();
//                    } else {
//                        showToast("Check Your Internet Connection");
//                    }
                    }
                }
            }
        });

        imageFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateType = 1;
                showDialog(999);
            }
        });
        imageToDate.setOnClickListener(new View.OnClickListener() {
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


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == iRequestCode) {
            String message = data.getStringExtra("MESSAGE");
            if (message.equals("SENT")) {
                finish();
            }
        }
    }

    private boolean myEmailValidation(String strEmail) {
        return EmailValidator.getInstance().isValid(strEmail);
    }

    private boolean smscheck() {
        String strsmscost1 = (POCsmscost.getText().toString());
        String smscaption = (POCcaption.getText().toString());
        if (ch_unlimitedsms.isChecked()) {
            if (smscaption.isEmpty()) {
                POCcaption.setError("Enter the sms caption");
                return false;
            } else {
                return true;
            }
        } else {
            if ((strsmscost1.isEmpty())) {
                Alert("Check Unlimited or Enter the number of SMS");
                if ((smscaption.isEmpty())) {
                    POCcaption.setError("Enter the sms caption");
                }
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
                Alert("Check Unlimited or Enter the number of CALLS");
                return false;
            } else {
                return true;
            }
        }
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
        SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");//yyyyMMdd");//dd/mm/yyyy");//dd/mm/yyyyMM/dd/yy
        strDateTime = df2.format(cal2.getTime());
        if (dateType == 1) {
            POCfromdate.setText(strDateTime);
        } else if (dateType == 2) {
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
        }
    };


    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(SuportVerify.this, myDateListener, year, month, day);
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

    private void backToResultActvity(String msg) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("MESSAGE", msg);
//        returnIntent.putExtra("STD_SEC", stdSec);
        setResult(iRequestCode, returnIntent);
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


    private void createpocretrofit() {

        if (ch_unlimitedcall.isChecked()) {
            strunlimitedcalls = "1";
        } else {
            strunlimitedcalls = "0";
        }

        if (ch_unlimitedsms.isChecked()) {
            strunlimitedsms = "1";
        } else {
            strunlimitedsms = "0";
        }
//        etvalue = textIn.getText().toString();
//        String value = "";
//        for (int j = 0; j < editTextList.size(); j++) {
//            value = etvalue + "," + value + editTextList.get(j).getText().toString() + ",";
//        }
//        if (value.equals("")) {
//            value = etvalue + ",";
//        } else {
//            value = etvalue + "," + value;
//        }
//        value = value.substring(0, value.length() - 1);
//        Log.d("test", value);


        strSchoolname = POCschoolname.getText().toString();
        strAddress = POCSchooladdr.getText().toString();
        strMobilenumber = POCphno.getText().toString();
        strcity = POCcity.getText().toString();
        strEmailId = POCemailid.getText().toString();
        strcontactperson = POCcontactperson.getText().toString();
        strFromdate = POCfromdate.getText().toString();
        strTodate = POCtodate.getText().toString();
        strwelcometext = POCwelcometext.getText().toString();
        strusername = POCusername.getText().toString();
        strpassword = POCpassword.getText().toString();
        strcaption = POCcaption.getText().toString();

        if (ch_sms.isChecked()) {
            if (ch_unlimitedsms.isChecked()) {
                strsmscost = "Unlimited";
                strunlimitedsms = "1";
            } else {
                strsmscost = POCsmscost.getText().toString();
                strunlimitedsms = "0";
            }
        } else {
            strunlimitedsms = "0";
            strsmscost = "0";
        }

        if (ch_voice.isChecked()) {
            if (ch_unlimitedcall.isChecked()) {
                strunlimitedcalls = "1";
                strcallcost = "Unlimited";
//                strcaption = "";
            } else {
                strunlimitedcalls = "0";
                strcallcost = POCcallcost.getText().toString();
//                strcaption = "";
            }
        } else {
            strunlimitedcalls = "0";
            strcallcost = "0";
        }
//        if (ch_sms.isChecked()) {
//            strmodule = "2";
//        } else if (ch_voice.isChecked()) {
//            strmodule = "1";
//        } else if ((ch_sms.isChecked()) && (ch_voice.isChecked())) {
//            strmodule ="1,2";
//        }


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


        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        JsonObject jsonObject = new JsonObject();

//        jsonObject.addProperty("LoginId", userId);
//        jsonObject.addProperty("schoolId", "0");
//        jsonObject.addProperty("InstitutionName", strSchoolname);
//        jsonObject.addProperty("Address", strAddress);
//        jsonObject.addProperty("City", strcity);
//        jsonObject.addProperty("pincode", strpincode);
//        jsonObject.addProperty("ContactNumber1", strMobilenumber);
//        jsonObject.addProperty("ContactPerson1", strcontactperson);
//        jsonObject.addProperty("Email", strEmailId);
//        jsonObject.addProperty("Modules", strmodule);
//        jsonObject.addProperty("caption", strcaption);
//        jsonObject.addProperty("peroidFrom", strFromdate);
//        jsonObject.addProperty("peroidTo", strTodate);
//        jsonObject.addProperty("processType", "add");
//        jsonObject.addProperty("isUnlimitedCalls", strunlimitedcalls);
//        jsonObject.addProperty("isUnlimitedSMS", strunlimitedsms);
//        jsonObject.addProperty("callsCount", strcallcost);
//        jsonObject.addProperty("smsCount", strsmscost);
//        jsonObject.addProperty("WelcomefileText", strwelcometext);
//        jsonObject.addProperty("LoginName", "");
//        jsonObject.addProperty("password", "");
//        jsonObject.addProperty("MgmtNumbers", value);


        int schoolType=0;

        jsonObject.addProperty("LoginId", userId);
        jsonObject.addProperty("schoolId", strschoolId);
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
        jsonObject.addProperty("processType", "edit");
        jsonObject.addProperty("isUnlimitedCalls", strunlimitedcalls);
        jsonObject.addProperty("isUnlimitedSMS", strunlimitedsms);
        jsonObject.addProperty("callsCount", strcallcost);
        jsonObject.addProperty("smsCount", strsmscost);
        jsonObject.addProperty("WelcomefileText", strwelcometext);
        jsonObject.addProperty("LoginName", strusername);
        jsonObject.addProperty("PurchaseType", strpurchasetype);
        jsonObject.addProperty("password", strpassword);
        jsonObject.addProperty("UniversalDID", checkDIDNumber);
        jsonObject.addProperty("SchoolType ", schoolType);

//        jsonObject.addProperty("MgmtNumbers", "");

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
                                    LayoutInflater layoutInflater = LayoutInflater.from(SuportVerify.this);
                                    View promptView = layoutInflater.inflate(R.layout.dilogpoc, null);

                                    final AlertDialog dlgAlert = new AlertDialog.Builder(SuportVerify.this).create();
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
//                                            Intent i = new Intent(SuportVerify.this, SupportverifyList.class);
//                                            startActivity(i);
//                                            finish();
                                            backToResultActvity("SENT");

                                        }
                                    });

                                    imgrecord.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            Intent i = new Intent(SuportVerify.this, Recordwelcomefile.class);
                                            i.putExtra("SchoolID", SchoolID);
                                            startActivity(i);
                                        }
                                    });
                                    dlgAlert.setView(promptView);
                                    dlgAlert.show();
                                } else {
                                    String message = jsonObject.getString("Message");
                                    alert(message);

//                                    Toast.makeText(SuportVerify.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
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

    //    private void showToast(String msg) {
//        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//    }
    private void alert(String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SuportVerify.this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(SuportVerify.this);
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
