package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter.MySchoolListAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.MyschoolListclass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class SchoolSubList extends AppCompatActivity {

    RecyclerView rvschoolsublist;
    MyschoolListclass myschoollistclass;
    MySchoolListAdapter myschoollistadapter;
    private static final String SH_USERS = "userInfo";
    private static final String SH_LOGINTYPE = "slogintype";
    private static final String SH_USERID = "UserId";
    String Logintype;
    SharedPreferences shpRemember;
    SharedPreferences.Editor ed;
    private ProgressDialog pDialog;
    RequestQueue requestQueue;
    //    String serverPath = "http://192.168.0.77:8096/SchoolDemoAppService/Service.asmx/";
    String serverPath = "http://220.226.2.177:9000/StaffVoiceAnnouncerCloudSchool/Service.asmx/";
    String userId, schoolid, mobno, name, designation, message, seniormanagement, schoolname;
    TextView tvhiddentext, tvhiddentext1;
    TextView textschoolid, textschoolname, textschooladdr, textschoolcity, textschooldid, textschoolusername,
            textschoolpassword, textschoolstatus,
            textschoolsrmgt, textstudentcount,
            textstaffcount, tvfromdate, tvtodate,
            lblConatctPerson1, lblContactPerson2, lblCntctNumber1, lblCntctNumber2, lblCntctEmail;
    Button message1, seniormgnt, Credit, Feature, seniormgt_no, usagecount;
    private ArrayList<MyschoolListclass> datasListschool = new ArrayList<>();
    LinearLayout linearLayoutbtn;

    LinearLayout lnrCalls, lnrSms;


    TextView txtSmsCount, txtCallsCount, lblWebuser_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_sub_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        lblConatctPerson1 = (TextView) findViewById(R.id.lblContactPrs_1);
        lblCntctNumber1 = (TextView) findViewById(R.id.lblCntctNum_1);
        lblContactPerson2 = (TextView) findViewById(R.id.lblCntctPerson_2);
        lblWebuser_name = (TextView) findViewById(R.id.lblWebuser_name);

        lblCntctNumber2 = (TextView) findViewById(R.id.lblCntctNum_2);
        lblCntctEmail = (TextView) findViewById(R.id.lblContactEmail);

        textschoolid = (TextView) findViewById(R.id.schoolid1);
        textschoolname = (TextView) findViewById(R.id.listschoolname1);
        textschooladdr = (TextView) findViewById(R.id.schooladdr1);
        textschoolcity = (TextView) findViewById(R.id.shoolcity1);
        textschooldid = (TextView) findViewById(R.id.school_did1);
        textschoolid = (TextView) findViewById(R.id.schoolid1);
        textschoolusername = (TextView) findViewById(R.id.school_username1);
        textschoolpassword = (TextView) findViewById(R.id.school_Password1);
        textschoolstatus = (TextView) findViewById(R.id.school_status1);
        textschoolsrmgt = (TextView) findViewById(R.id.seniormgt1);
        tvfromdate = (TextView) findViewById(R.id.fromdate);
        tvtodate = (TextView) findViewById(R.id.todate);
        textstudentcount = (TextView) findViewById(R.id.studentcount1);
        textstaffcount = (TextView) findViewById(R.id.schoolstaff1);
        message1 = (Button) findViewById(R.id.schoolmsg);
        usagecount = (Button) findViewById(R.id.School_usage_count);
//        tvhiddentext = (TextView) findViewById(R.id.hiddenTextView1);
//        tvhiddentext1 = (TextView) findViewById(R.id.hiddenTextView2);
        seniormgnt = (Button) findViewById(R.id.btnmgnt);
        Credit = (Button) findViewById(R.id.btncredit);
        Feature = (Button) findViewById(R.id.btnfeature);
        seniormgt_no = (Button) findViewById(R.id.School_seniormgt_number);
        linearLayoutbtn = (LinearLayout) findViewById(R.id.linearbtn);
        txtSmsCount = (TextView) findViewById(R.id.txtSmsCount);
        txtCallsCount = (TextView) findViewById(R.id.txtCallsCount);
        lnrCalls = (LinearLayout) findViewById(R.id.lnrCalls);
        lnrSms = (LinearLayout) findViewById(R.id.lnrSms);

        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
        userId = shpRemember.getString(SH_USERID, null);
        Logintype = shpRemember.getString(SH_LOGINTYPE, null);
        Log.d("testsublist", Logintype);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);


//        if(schoolid.equals("0000"))
//        {
//            linearLayoutbtn.setVisibility(View.GONE);
//        }
//        else
//        {
//            linearLayoutbtn.setVisibility(View.VISIBLE);
//        }

        final MyschoolListclass Schooldata = (MyschoolListclass) getIntent().getSerializableExtra("MyschoolListclass");
        message1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SchoolSubList.this);

                alertDialog.setTitle("Alert");

                alertDialog.setMessage("Are you sure To send the Message?");

                alertDialog.setIcon(android.R.drawable.ic_dialog_info);

                alertDialog.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(SchoolSubList.this, Message_student.class);
                                i.putExtra("VALUE", Schooldata.getStrSchoolId());
                                i.putExtra("SCHOOLNAME", Schooldata.getStrSchoolNameList());
                                startActivity(i);

                            }
                        });


                alertDialog.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog.show();
            }
        });


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            seniormanagement = extras.getString("VALUEMOBNO");

        }

        seniormgnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SchoolSubList.this, Senior_mgmt.class);
                i.putExtra("VALUE", schoolid);
                startActivity(i);

            }
        });

        Credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SchoolSubList.this, Credits.class);
                i.putExtra("VALUE", schoolid);
                startActivity(i);

            }
        });

        Feature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SchoolSubList.this, Features.class);
                i.putExtra("VALUE", schoolid);
                startActivity(i);

            }
        });
        schoolid = Schooldata.getStrSchoolId();
        schoolname = Schooldata.getStrSchoolNameList();
//        seniormanagement=Schooldata.getStrSeniormgt();

        lblConatctPerson1.setText(Schooldata.getContactPerson1());
        lblCntctNumber1.setText(Schooldata.getContactNumber1());
        lblContactPerson2.setText(Schooldata.getContactPerson2());
        lblCntctNumber2.setText(Schooldata.getContactNumber2());
        lblCntctEmail.setText(Schooldata.getContactEmail());
        lblWebuser_name.setText(Schooldata.getWebUsername());

        textschoolid.setText(Schooldata.getStrSchoolId());
        textschoolname.setText(Schooldata.getStrSchoolNameList());
        textschooladdr.setText(Schooldata.getStrSchooladdr());
        textschoolcity.setText(Schooldata.getStrSchoolcity());

        textschooldid.setText(Schooldata.getStrDidschool());
        textschoolusername.setText(Schooldata.getStrUserName());
        textschoolpassword.setText(Schooldata.getStrPassword());
        tvfromdate.setText(Schooldata.getStrFromdate());
        tvtodate.setText(Schooldata.getStrTodate());
        textschoolstatus.setText(Schooldata.getStrschoolStatus());
        textschoolsrmgt.setText(Schooldata.getStrSeniormgt());
        textstudentcount.setText(Schooldata.getStrstudentcount());
        textstaffcount.setText(Schooldata.getStrstaffcount());

        if (Schooldata.getCallsCount() != null) {
            lnrCalls.setVisibility(View.VISIBLE);
            txtCallsCount.setText(Schooldata.getCallsCount());
        }

        if (Schooldata.getSmsCount() != null) {
            lnrSms.setVisibility(View.VISIBLE);
            txtSmsCount.setText(Schooldata.getSmsCount());
        }


        Log.d("schoolid", schoolid);
        if (schoolid.equals("0000")) {
            linearLayoutbtn.setVisibility(View.GONE);
        } else {
            linearLayoutbtn.setVisibility(View.GONE);
        }
        if (Logintype.equals("Admin")) {
            linearLayoutbtn.setVisibility(View.GONE);
        } else {
            linearLayoutbtn.setVisibility(View.GONE);
        }
        seniormgt_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SchoolSubList.this, Senior_mgt_number.class);
                i.putExtra("SCHOOLID", schoolid);
                i.putExtra("SCHOOLNAME", schoolname);
                startActivity(i);
            }
        });
        usagecount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SchoolSubList.this, Usagecount.class);
                i.putExtra("SCHOOLIDusage", schoolid);
                i.putExtra("SCHOOLNAMEusage", schoolname);
                startActivity(i);
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


    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


}
