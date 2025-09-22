package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class Mainmenu extends AppCompatActivity {

    Button create_demo, create_poc,create_po, create_list, myschool_list, approve_poc,circular_list,status_review;
    boolean doubleBackToExitPressedOnce = false;

    private static final String SH_LOGIN = "userlogin";
    private static final String SH_USERNAME = "sUsername";
    private static final String SH_PASSWORD = "sPassword";
    private static final String SH_LOGINTYPE = "slogintype";
    private static final String SH_USERS = "userInfo";
    private static final String SH_USERID = "UserId";
    SharedPreferences shpRemember;
    SharedPreferences.Editor ed;
    String userId, Logintype;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowTitleEnabled(true);
//        }

        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
        userId = shpRemember.getString(SH_USERID, null);
        Logintype = shpRemember.getString(SH_LOGINTYPE, null);


        create_demo = (Button) findViewById(R.id.btnCreateDemo);
        create_poc = (Button) findViewById(R.id.btnCreatePOC);
        create_po = (Button) findViewById(R.id.btnCreatePO);
        create_list = (Button) findViewById(R.id.btnCreatelist);
        myschool_list = (Button) findViewById(R.id.btnschoollist);
        approve_poc = (Button) findViewById(R.id.btnapprovepoc);
        circular_list = (Button) findViewById(R.id.btncircularlist);
        status_review= (Button) findViewById(R.id.btnstatus);
        if (Logintype.equals("Admin")) {
            approve_poc.setVisibility(View.VISIBLE);
        } else {
            approve_poc.setVisibility(View.GONE);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);


        create_demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Mainmenu.this, CreateDemo.class);
                startActivity(i);

            }
        });

        create_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Mainmenu.this, CreateDemoList.class);
                startActivity(i);

            }
        });
        create_poc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Mainmenu.this, CreatePoc.class);
                startActivity(i);

            }
        });
        create_po.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Mainmenu.this, Create_PO.class);
                startActivity(i);

            }
        });
        myschool_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Mainmenu.this, MyschoolList.class);
                startActivity(i);

            }
        });

        approve_poc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Mainmenu.this, Approve_poc.class);
                startActivity(i);

            }
        });


        circular_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Mainmenu.this, CircularList.class);
                startActivity(i);

            }
        });
        status_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Mainmenu.this, Statusreportsubmenu.class);
                startActivity(i);

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

                Intent intent = new Intent(Mainmenu.this, ChangePassword.class);
                startActivity(intent);
                return (true);
            }


            case R.id.Logout: {

                Intent next = new Intent(Mainmenu.this, Login.class);
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
        moveTaskToBack(true);
//        finish();
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
