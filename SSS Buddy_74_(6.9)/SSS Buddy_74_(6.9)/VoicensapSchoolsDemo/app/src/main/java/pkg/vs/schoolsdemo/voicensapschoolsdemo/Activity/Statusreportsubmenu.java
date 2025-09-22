package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class Statusreportsubmenu extends AppCompatActivity {
Button btntovisit,btnoverallStatus,btnReport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statusreportsubmenu);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        btntovisit=(Button)findViewById(R.id.btntovisitreport);
        btnoverallStatus=(Button)findViewById(R.id.btnoverallstatusreport);
        btnReport = findViewById(R.id.btnreport);
        btntovisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentvisit=new Intent(Statusreportsubmenu.this,VisitReport.class);
                startActivity(intentvisit);
            }
        });
        btnoverallStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Statusreportsubmenu.this,OverallStatusReport.class);
                startActivity(i);
            }
        });
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReport = new Intent(Statusreportsubmenu.this, ReportActivity.class);
                startActivity(intentReport);
            }
        });
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



    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return (true);


            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
