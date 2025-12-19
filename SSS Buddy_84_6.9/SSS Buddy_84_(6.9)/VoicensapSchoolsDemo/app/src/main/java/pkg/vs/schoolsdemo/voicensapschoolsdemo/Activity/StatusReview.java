package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class StatusReview extends AppCompatActivity {

    EditText et_RevenueTarget, et_SchoolAchieved, et_RevenuesAchieved, et_POCschools, et_POCstudentscount, et_Pendingpayment, et_Remarks, et_Complaints, et_Schoolname, et_SchoolRemarks;
    String RevenueTarget, SchoolAchieved, RevenuesAchieved, POCschools, POCstudentscount, Pendingpayment, Remarks, Complaints, Schoolname, SchoolRemarks;
    Button btn_statussubmit;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_review);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        et_RevenueTarget = (EditText) findViewById(R.id.status_revenuetarget);
        et_SchoolAchieved = (EditText) findViewById(R.id.status_schoolachieved);
        et_RevenuesAchieved = (EditText) findViewById(R.id.status_revenueacheive);
        et_POCschools = (EditText) findViewById(R.id.status_pocschool);
        et_POCstudentscount = (EditText) findViewById(R.id.status_pocstudentcount);
        et_Pendingpayment = (EditText) findViewById(R.id.status_paymentpending);
        et_Remarks = (EditText) findViewById(R.id.status_remarks);
        et_Complaints = (EditText) findViewById(R.id.status_complaints);
        et_Schoolname = (EditText) findViewById(R.id.status_schoolname);
        et_SchoolRemarks = (EditText) findViewById(R.id.status_dailyremarks);
        btn_statussubmit = (Button) findViewById(R.id.btnstatus_submit);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        btn_statussubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Checkinputvalidation();
            }
        });

    }

    private void Checkinputvalidation() {

        RevenueTarget = et_RevenueTarget.getText().toString();
        SchoolAchieved = et_SchoolAchieved.getText().toString();
        RevenuesAchieved = et_RevenuesAchieved.getText().toString();
        POCschools = et_POCschools.getText().toString();
        POCstudentscount = et_POCstudentscount.getText().toString();
        Pendingpayment = et_Pendingpayment.getText().toString();
        Remarks = et_Remarks.getText().toString();
        Complaints = et_Complaints.getText().toString();
        Schoolname = et_Schoolname.getText().toString();
        SchoolRemarks = et_SchoolRemarks.getText().toString();
        if (RevenueTarget.isEmpty()) {
            et_RevenueTarget.setError("Enter Revenue Target");
        } else if (SchoolAchieved.isEmpty()) {
            et_SchoolAchieved.setError("Enter School Achieved");
        } else if (RevenuesAchieved.isEmpty()) {
            et_RevenuesAchieved.setError("Enter Revenues Achieved");
        } else if (POCschools.isEmpty()) {
            et_POCschools.setError("Enter POCschools");
        } else if (POCstudentscount.isEmpty()) {
            et_POCstudentscount.setError("Enter POCstudents count");
        } else if (Pendingpayment.isEmpty()) {
            et_Pendingpayment.setError("Enter Pending payment");
        } else if (Schoolname.isEmpty()) {
            et_Schoolname.setError("Enter Schoolname");
        }
        else{
            Alert1("Status added Successfully");
//            Toast.makeText(StatusReview.this, "Status added Successfully", Toast.LENGTH_SHORT).show();
//            finish();
        }

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
    private void Alert1(String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(StatusReview.this);
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

}
