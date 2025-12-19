package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.WindowInsetsControllerCompat;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.AdvanceTour;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.CustomerDetails;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.Invoice_Verification;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.Pending_payments;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.Report;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class Menumain extends AppCompatActivity {
LinearLayout card1,card2,card3,card4,card5,card6,card7,card8,card9,card10,card11;
LinearLayout customer_details,local_convey,Advance_tour,pending_pay,tagetsale,tour_settlement,invoice_verify,Director_approval;
    CardView c1,c2;
    String userId, Logintype;
    SharedPreferences shpRemember;
    SharedPreferences.Editor ed;
    private static final String SH_LOGINTYPE = "slogintype";
    private static final String SH_USERS = "userInfo";
    private static final String SH_USERID = "UserId";
    int iRequestCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        card1=(LinearLayout)findViewById(R.id.gridHome_lay1);
        card2=(LinearLayout)findViewById(R.id.gridHome_lay2);
        card3=(LinearLayout)findViewById(R.id.gridHome_lay3);
        card4=(LinearLayout)findViewById(R.id.gridHome_lay4);
        card5=(LinearLayout)findViewById(R.id.gridHome_lay5);
        card11=(LinearLayout)findViewById(R.id.gridHome_lay10);


//        c1=(CardView)findViewById(R.id.c1);
//        c2=(CardView)findViewById(R.id.c2);
        card6=(LinearLayout)findViewById(R.id.gridHome_lay6);
        card7=(LinearLayout)findViewById(R.id.gridHome_lay7);
//        card8=(LinearLayout)findViewById(R.id.gridHome_lay8);
        card9=(LinearLayout)findViewById(R.id.gridHome_lay9);
        card10=(LinearLayout)findViewById(R.id.gridHome_lay_collect);
        customer_details=(LinearLayout)findViewById(R.id.gridHome_CustomerDetail);
        local_convey=(LinearLayout)findViewById(R.id.gridHome_Localconvey);
        Advance_tour =(LinearLayout)findViewById(R.id.gridHome_AdvanceTour);
        pending_pay=(LinearLayout)findViewById(R.id.gridHome_pending_pay);
        tagetsale=(LinearLayout)findViewById(R.id.gridHome_targetsale);
        tour_settlement=(LinearLayout)findViewById(R.id.gridHome_TourSettle);
        invoice_verify=(LinearLayout)findViewById(R.id.gridHome_invoice);
        Director_approval=(LinearLayout)findViewById(R.id.gridHome_Director_approve);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
        userId = shpRemember.getString(SH_USERID, null);
        Logintype = shpRemember.getString(SH_LOGINTYPE, null);
        if (Logintype.equals("Admin")) {
            card5.setVisibility(View.VISIBLE);
            card9.setVisibility(View.VISIBLE);
            }
       else if(Logintype.equals("Support")){
            card9.setVisibility(View.VISIBLE);
            card5.setVisibility(View.INVISIBLE);
        }
       else if(Logintype.equals("MyTeam")){
            card9.setVisibility(View.INVISIBLE);
            card5.setVisibility(View.INVISIBLE);
            }
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menumain.this, CreateDemo.class);
                startActivity(i);
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menumain.this, CreateDemoList.class);
                startActivity(i);
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menumain.this, CreatePoc.class);
                startActivity(i);
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menumain.this, MyschoolList.class);
                startActivity(i);
            }
        });
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menumain.this, Approve_poc.class);
                startActivity(i);
            }
        });
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menumain.this, CircularList.class);
                startActivity(i);
            }
        });
        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menumain.this, Statusreportsubmenu.class);
                startActivity(i);
            }
        });

        card9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menumain.this, SupportverifyList.class);
                startActivity(i);
            }
        });
        card10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menumain.this, Recordcollectionmenu.class);
                startActivity(i);
            }
        });



        card11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menumain.this, ContactsNumbersActivity.class);
                startActivity(i);
            }
        });
        customer_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(Menumain.this, CustomerDetails.class);
                startActivity(intent1);
            }
        });
        local_convey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Menumain.this, Report.class);
                startActivity(intent1);
            }
        });
        Advance_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Menumain.this, AdvanceTour.class);
                startActivity(intent1);
            }
        });

        invoice_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Menumain.this, Invoice_Verification.class);
                startActivity(intent1);
            }
        });
        pending_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Menumain.this, Pending_payments.class);
                startActivity(intent1);
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.changepassword: {
//
                Intent intent = new Intent(Menumain.this, ChangePassword.class);
                startActivity(intent);
                return (true);
            }


            case R.id.Logout: {
//
                Intent next = new Intent(Menumain.this, Login.class);
                startActivity(next);
                finish();
                return (true);
            }


            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();
//        if (doubleBackToExitPressedOnce) {
//            moveTaskToBack(true);
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);
    }
}
