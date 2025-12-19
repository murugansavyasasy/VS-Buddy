package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.RelativeLayout;
import android.widget.SeekBar;
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

import java.io.File;
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

public class EditPOC extends AppCompatActivity {
    EditText POCschoolname, POCcity, POCpincode, POCSchooladdr, POCphno, POCcontactperson,
            POCemailid, POCprinciname, POCmobno, POCcaption, POCfromdate,
            POCtodate, POCremarks, POCdatadate, POCcallcost, POCsmscost;
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
    String userId;
    String strSchoolname, strAddress, strphoneno, strEmailId, strPrincipalname, strMobilenumber, strcity, strpincode,
            strcontactperson, strcaption, strFromdate, strdatadate, strTodate, strRemarks, strunlimitedsms = "0",
            strunlimitedcalls = "0", strsms = "0", strcall = "0", strcallcost, strsmscost, strmodule, strschoolId,strpurchasetype="1";
    int intModule, intdbrequired;
    Boolean intdemo, intScope;
    private Calendar calendar;
    private int year, month, day, hour, minute;
    Button POCsubmit;
    LinearLayout linearsms, linearcall;
    int dateType;
    EditText textIn;
    Button btnAdd;
    private List<EditText> editTextList = new ArrayList<>();

    //    Media Player
    Button btnNext;
    RelativeLayout rlVoicePreview;
    ImageView ivRecord;
    ImageButton imgBtnPlayPause;
    SeekBar seekBar;
    TextView tvPlayDuration, tvRecordDuration, tvRecordTitle;
    LinearLayout linearLayoutParent;
    private MediaPlayer mediaPlayer;
    int mediaFileLengthInMilliseconds = 0;
    Handler handler = new Handler();
    int recTime;
    RadioGroup rg_purchasetype;
    RadioButton rb_poc,rb_live;
    MediaRecorder recorder;
    Handler recTimerHandler = new Handler();
    boolean bIsRecording = false;
    int iMediaDuration = 0;
    File fileRecordedFilePath;
    public static final String VOICE_FOLDER_NAME = "School Demo/Voice";
    public static final String VOICE_FILE_NAME = "demoVoice.mp3";
    int iRequestCode;
    CheckBox checkDID;
    boolean checkDIDNumber =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_poc);
        iRequestCode= getIntent().getExtras().getInt("REQUEST_CODE", 0);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

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
            strcallcost = extras.getString("POCCallcredits");
            strsmscost = extras.getString("POCSmscredit");
            strmodule = extras.getString("POCmodule");
            strcaption = extras.getString("POCcaption");
            strpincode = extras.getString("POCpincode");
            strpurchasetype=extras.getString("POCpurchasetype");
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);


        Log.d("sms&call", strsmscost + strcallcost);

        textIn = (EditText) findViewById(R.id.textmobno);
        btnAdd = (Button) findViewById(R.id.addmobno);
        linearLayoutParent = (LinearLayout) findViewById(R.id.llParentmobno);


        userId= SharedPreference_class.getShSchlLoginid(EditPOC.this);

//        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
//        userId = shpRemember.getString(SH_USERID, null);

        POCschoolname = (EditText) findViewById(R.id.edit_pocschoolname);
        POCSchooladdr = (EditText) findViewById(R.id.edit_pocschooladdr);
        POCphno = (EditText) findViewById(R.id.edit_pocschoolphno);
        POCcity = (EditText) findViewById(R.id.edit_poccity);
        POCcontactperson = (EditText) findViewById(R.id.edit_poccontactperson);
        POCpincode = (EditText) findViewById(R.id.edit_pocpincode);
        POCemailid = (EditText) findViewById(R.id.edit_pocschoolmailid);
        POCfromdate = (EditText) findViewById(R.id.edit_pocfromdate);
        POCtodate = (EditText) findViewById(R.id.edit_poctodate);
        POCremarks = (EditText) findViewById(R.id.edit_pocremarks);
        POCcaption = (EditText) findViewById(R.id.edit_pocschoolsmscaption);
        POCcallcost = (EditText) findViewById(R.id.edit_callmodpoc);
        POCsmscost = (EditText) findViewById(R.id.edit_smsmodpoc);
        ch_app = (CheckBox) findViewById(R.id.edit_ch_app);
        ch_erp = (CheckBox) findViewById(R.id.edit_ch_erp);
        ch_sms = (CheckBox) findViewById(R.id.edit_ch_sms);
        ch_voice = (CheckBox) findViewById(R.id.edit_ch_voice);
        ch_unlimitedsms = (CheckBox) findViewById(R.id.edit_chsmspoc);
        ch_unlimitedcall = (CheckBox) findViewById(R.id.edit_chcallpoc);
        imageFromDate = (ImageView) findViewById(R.id.edit_imagefromdate);
        imageToDate = (ImageView) findViewById(R.id.edit_imagetodate);

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

        rg_purchasetype= (RadioGroup) findViewById(R.id.poc_purchasetype_edit);
        rb_live = (RadioButton) findViewById(R.id.radio_purchasetype_live_edit);
        rb_poc = (RadioButton) findViewById(R.id.radio_purchasetype_poc_edit);

        linearsms = (LinearLayout) findViewById(R.id.edit_linearLayoutsmspoc);
        linearcall = (LinearLayout) findViewById(R.id.edit_linearLayoutcallpoc);
        POCsubmit = (Button) findViewById(R.id.edit_pocsubmit);
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


        showDate(year, month, day);
        Log.d("module", strmodule);
        if (strmodule.equals("1,2")) {
            ch_sms.setChecked(true);
            ch_voice.setChecked(true);
            linearcall.setVisibility(View.VISIBLE);
            linearsms.setVisibility(View.VISIBLE);
            strsms = "1";
            strcall = "1";
        }  if (strmodule.equals("1")) {
            ch_sms.setChecked(false);
            ch_voice.setChecked(true);
            linearcall.setVisibility(View.VISIBLE);
            linearsms.setVisibility(View.GONE);
            strsms = "0";
            strcall = "1";
        } if (strmodule.equals("2")) {
            ch_sms.setChecked(true);
            ch_voice.setChecked(false);
            linearcall.setVisibility(View.GONE);
            linearsms.setVisibility(View.VISIBLE);
            strsms = "1";
            strcall = "0";
        }
        if(strpurchasetype.equals("1")){
            rb_poc.setChecked(true);

        }
        else if(strpurchasetype.equals("2")){
            rb_live.setChecked(true);
        }
//        Log.d("sms&call", strcallcost + strsmscost);
//        if (strsmscost.equals("Unlimited")) {
//            ch_unlimitedsms.setChecked(true);
//            POCsmscost.setVisibility(View.GONE);
//        }
//        else {
//            POCsmscost.setText(strsmscost);
//            ch_unlimitedsms.setChecked(false);
//            POCsmscost.setVisibility(View.VISIBLE);
//        }
//        if (strcallcost.equals("Unlimited")) {
//            ch_voice.setChecked(true);
//            ch_unlimitedcall.setChecked(true);
//            POCcallcost.setVisibility(View.GONE);
//        } else if ((strcallcost.equals("")) || ((strcallcost.equals("0")))) {
//            ch_sms.setChecked(false);
//        } else {
//            ch_voice.setChecked(true);
//            POCcallcost.setText(strsmscost);
//            ch_unlimitedcall.setChecked(false);
//            POCcallcost.setVisibility(View.VISIBLE);
//        }

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


        Log.d("sms&call",strcallcost+strsmscost);
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
        ch_sms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strsms = "1";
                    linearsms.setVisibility(View.VISIBLE);
                } else {
                    strsms = "0";
                    linearsms.setVisibility(View.GONE);
                }
            }
        });
        ch_voice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strcall = "1";
                    linearcall.setVisibility(View.VISIBLE);
                } else {
                    strcall = "0";
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


        POCsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Schoolname = (POCschoolname.getText().toString());
                String Schoolcity = (POCcity.getText().toString());
                String SchoolPhno = (POCphno.getText().toString());
                String EmailId = (POCemailid.getText().toString());
                String principalname = (POCcontactperson.getText().toString());
                String FromDate = (POCfromdate.getText().toString());
                String ToDate = (POCtodate.getText().toString());
                String smscaption = (POCcaption.getText().toString());

                if (Schoolname.isEmpty() || Schoolcity.isEmpty() || SchoolPhno.length() != 10 || FromDate.isEmpty() || ToDate.isEmpty() || ((!myEmailValidation(EmailId)) && (!EmailId.isEmpty()))) {
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
                        POCemailid.setError("Enter Valid email-Id");
                    }
                    if (FromDate.isEmpty()) {
                        POCfromdate.setError("Enter the POC from date");
                    }
                    if (ToDate.isEmpty()) {
                        POCtodate.setError("Enter the POC To date");
                    }
                } else {

                    if ((!ch_voice.isChecked()) && (!ch_sms.isChecked())) {
                        Alert("Please check voice or sms");
                    } else if ((ch_voice.isChecked()) && (ch_sms.isChecked())) {
                        if (smscheck() && voicecheck()) {
                            editpocretrofit();
                        }
                    } else if (ch_voice.isChecked()) {
                        if (voicecheck()) {
                            editpocretrofit();
                        }
                    } else if (ch_sms.isChecked()) {
                        if (smscheck()) {
                            editpocretrofit();
                        }

                    }

//                    insertValues();
//                    editpocretrofit();
//                    if (!isNetworkConnected()) {
//                    editpocretrofit();
//                    } else {
//                        showToast("Check Your Internet Connection");
//                    }
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
//        setupAudioPlayer();
//        fetchSong();

    }

    private void backToResultActvity(String msg) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("MESSAGE", msg);
//        returnIntent.putExtra("STD_SEC", stdSec);
        setResult(iRequestCode, returnIntent);
        finish();
    }

    private void showDate(int year, int month, int day) {
        String strDateTime;
        this.year = year;
        this.month = month;
        this.day = day;
        Calendar cal2 = Calendar.getInstance();
        cal2.set(year, month, day, hour, minute);
        SimpleDateFormat df2 = new SimpleDateFormat("MMM dd, yyyy");//dd/mm/yyyyMM/dd/yy dd/mm/yyyy

//        SimpleDateFormat originalFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
//        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyyMMdd");
//        Date date = originalFormat.parse("August 21, 2012");
//        String formattedDate = targetFormat.format(date);
        strDateTime = df2.format(cal2.getTime());
        if (dateType == 1) {
            POCfromdate.setText(strDateTime);
        } else if (dateType == 2) {
            POCtodate.setText(strDateTime);
        } else if (dateType == 3) {
            POCdatadate.setText(strDateTime);
        }
    }

    private boolean myEmailValidation(String strEmail) {
        return EmailValidator.getInstance().isValid(strEmail);
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
            return new DatePickerDialog(EditPOC.this, myDateListener, year, month, day);
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


    private void editpocretrofit() {

//        etvalue = textIn.getText().toString();
//        String value = "";
//        for (int j = 0; j < editTextList.size(); j++) {
//            value = value + editTextList.get(j).getText().toString() + ",";
//        }

//        value = value.substring(0, value.length() - 1);
//        Log.d("test", value);


        strSchoolname = POCschoolname.getText().toString();
        strAddress = POCSchooladdr.getText().toString();
        strMobilenumber = POCphno.getText().toString();
        strcity = POCcity.getText().toString();
        strEmailId = POCemailid.getText().toString();
        strpincode = POCpincode.getText().toString();
        strcontactperson = POCcontactperson.getText().toString();
        strFromdate = POCfromdate.getText().toString();
        strTodate = POCtodate.getText().toString();
        strcaption = POCcaption.getText().toString();

        if (ch_sms.isChecked()) {
            if (ch_unlimitedsms.isChecked()) {
                strunlimitedsms = "1";
                strsmscost = "";
//                strcaption = POCcaption.getText().toString();
            } else {
                strunlimitedsms = "0";
                strsmscost = POCsmscost.getText().toString();
//                strcaption = POCcaption.getText().toString();
            }
        } else {
            strunlimitedsms = "";
            strsmscost = "";
//            strcaption = "";
        }
        if (ch_voice.isChecked()) {
            if (ch_unlimitedcall.isChecked()) {
                strunlimitedcalls = "1";
                strcallcost = "";
//                strcaption = "";
            } else {
                strunlimitedcalls = "0";
                strcallcost = POCcallcost.getText().toString();
//                strcaption = "";
            }
        } else {
            strunlimitedcalls = "";
            strcallcost = "";
//            strcaption = "";
        }
//if((ch_sms.isChecked())&&(ch_voice.isChecked())){
//    if(ch_unlimitedcall.isChecked()){
//
//    }
//}
//        if (ch_sms.isChecked()) {
//            strmodule = "2";
//        } else if (ch_voice.isChecked()) {
//            strmodule = "1";
//        } else if ((ch_sms.isChecked()) && (ch_voice.isChecked())) {
//            strmodule ="1,2";
//        }
        Log.d("sms1&call1", strsms + strcall);
        if ((strsms.equals("1")) && (strcall.equals("1"))) {
            strmodule = "1,2";
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
        jsonObject.addProperty("LoginId", userId);
        jsonObject.addProperty("schoolId", strschoolId);
        jsonObject.addProperty("InstitutionName", strSchoolname);
        jsonObject.addProperty("Address", strAddress);
        jsonObject.addProperty("City", strcity);
        jsonObject.addProperty("ContactNumber1", strMobilenumber);
        jsonObject.addProperty("ContactPerson1", strcontactperson);
        jsonObject.addProperty("Email", strEmailId);
        jsonObject.addProperty("pincode", strpincode);
        jsonObject.addProperty("Modules", strmodule);
        jsonObject.addProperty("caption", strcaption);
        jsonObject.addProperty("peroidFrom", strFromdate);
        jsonObject.addProperty("peroidTo", strTodate);
        jsonObject.addProperty("processType", "edit");
        jsonObject.addProperty("isUnlimitedCalls", strunlimitedcalls);
        jsonObject.addProperty("isUnlimitedSMS", strunlimitedsms);
        jsonObject.addProperty("callsCount", strcallcost);
        jsonObject.addProperty("PurchaseType", strpurchasetype);
        jsonObject.addProperty("smsCount", strsmscost);
        jsonObject.addProperty("LoginName", "");
        jsonObject.addProperty("password", "");
        jsonObject.addProperty("UniversalDID", checkDIDNumber);
//        jsonObject.addProperty("MgmtNumbers", "");

        Log.d("createpoc:req", jsonObject.toString());


//        File file = new File(fileRecordedFilePath.getPath());
//        Log.d("FILE_Path", fileRecordedFilePath.getPath());
////        File file = FileUtils.getFile(this, Uri.parse(recFilePath));
//
//        // create RequestBody instance from file
//        RequestBody requestFile =
//                RequestBody.create(
//                        MediaType.parse("multipart/form-data"), file);
//
//        MultipartBody.Part bodyFile =
//                MultipartBody.Part.createFormData("voice", file.getName(), requestFile);
//
//        RequestBody requestBody =
//                RequestBody.create(
//                        MultipartBody.FORM, jsonObject.toString());

        Call<JsonArray> call = apiService.Approveordecline(jsonObject);//requestBody, bodyFile);


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

                                    LayoutInflater layoutInflater = LayoutInflater.from(EditPOC.this);
                                    View promptView = layoutInflater.inflate(R.layout.dailog_ok, null);
                                    final AlertDialog dlgAlert = new AlertDialog.Builder(EditPOC.this).create();
                                    dlgAlert.setTitle("Alert");
                                    dlgAlert.setMessage(jsonObject.getString("Message"));
                                    dlgAlert.setIcon(android.R.drawable.ic_dialog_info);
                                    dlgAlert.setCancelable(false);
                                    final Button Okbtn = (Button) promptView.findViewById(R.id.okbtndemo);
                                    Okbtn.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
//                                            Intent i = new Intent(EditPOC.this, Approve_poc.class);
//                                            startActivity(i);
//                                            dlgAlert.dismiss();
//                                            finish();
                                            backToResultActvity("SENT");
                                        }
                                    });
                                    dlgAlert.setView(promptView);
                                    dlgAlert.show();
                                } else {
                                    String title="Alert";
                                    String Message=jsonObject.getString("Message");

                                    alert(title,Message);
//                                    Toast.makeText(EditPOC.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
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
//                showToast("Server Connection Failed");
            }
        });
    }

//    private void showToast(String msg) {
//        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//    }

    private void alert(String title,String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditPOC.this);
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

    private void Alert(String msg ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditPOC.this);
        builder.setTitle(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();


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
}
