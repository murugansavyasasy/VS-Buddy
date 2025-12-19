package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import java.util.ArrayList;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SchoolModel;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.util.CommonUtil;

public class Recordcollectionmenu extends AppCompatActivity {
LinearLayout linear_school,linear_corporate,linear_huddle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recollectionmenu);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        linear_school = findViewById(R.id.gridHome_lay_school);
        linear_corporate = findViewById(R.id.gridHome_lay_corporate);
        linear_huddle = findViewById(R.id.gridHome_lay_huddle);

        findViewById(R.id.btnAddFaq).setOnClickListener(v -> {
            startActivity(new Intent(Recordcollectionmenu.this, SchoolDetailsActivity.class));
        });

        linear_school.setOnClickListener(v -> {
            Intent i = new Intent(Recordcollectionmenu.this, Recordcollection.class);
            startActivity(i);
        });

        ImageView btnEditFaq = findViewById(R.id.btnEditFaq);

        btnEditFaq.setOnClickListener(v -> {
            Intent intent = new Intent(Recordcollectionmenu.this, EditDetailsActivity.class);
            startActivity(intent);
        });


//        btnEditFaq.setOnClickListener(v -> {
//            if (CommonUtil.schoolList.size() > 0) {
//                Intent intent = new Intent(Recordcollectionmenu.this, EditDetailsActivity.class);
//                intent.putExtra("schoolArray", CommonUtil.schoolList);
//                startActivity(intent);
//            }
//        });


        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);
        linear_school=(LinearLayout)findViewById(R.id.gridHome_lay_school);
        linear_corporate=(LinearLayout)findViewById(R.id.gridHome_lay_corporate);
        linear_huddle=(LinearLayout)findViewById(R.id.gridHome_lay_huddle);
        linear_school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Recordcollectionmenu.this, Recordcollection.class);
                startActivity(i);
            }
        });
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
}
