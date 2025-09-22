package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter.Seniormgtnumberlist;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.Seniormgtnumberclass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;

public class Senior_mgt_number extends AppCompatActivity {
    Seniormgtnumberlist snrmgtadapter;
    RecyclerView rvsnrmgtlist;
    private ArrayList<Seniormgtnumberclass> datasListschool = new ArrayList<>();
    ArrayList<Seniormgtnumberclass> arrayList;
    String schoolid, schoolname;
    TextView tv_schoolid, tv_schoolname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senior_mgt_number);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            schoolid = extras.getString("SCHOOLID");
            schoolname = extras.getString("SCHOOLNAME");
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        tv_schoolid=(TextView)findViewById(R.id.snrmgt_schoolid);
        tv_schoolname=(TextView)findViewById(R.id.snrmgt_schoolname);
        rvsnrmgtlist = (RecyclerView) findViewById(R.id.rvSeniormgtno);
        tv_schoolid.setText(schoolid);
        tv_schoolname.setText(schoolname);
        snrmgtadapter = new Seniormgtnumberlist(datasListschool, Senior_mgt_number.this);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(Senior_mgt_number.this);
        rvsnrmgtlist.setLayoutManager(LayoutManager);
        rvsnrmgtlist.setItemAnimator(new DefaultItemAnimator());
        rvsnrmgtlist.setAdapter(snrmgtadapter);
        snemgtlistretrofit();

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);
    }


    private void snemgtlistretrofit() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("LoginID", userId);
//
//        Log.d("Listdemo:req", jsonObject.toString());

        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        Call<JsonArray> call = apiService.GetManagementNumbers(schoolid);

        call.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                try {
                    Log.d("Listdemo;Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();

                    JSONArray jsonArrayorgList = new JSONArray(strResponse);
                    if (jsonArrayorgList.length() > 0) {
                        JSONObject temp;
                        datasListschool.clear();
                        for (int i = 0; i < jsonArrayorgList.length(); i++) {
                            temp = jsonArrayorgList.getJSONObject(i);
                            Seniormgtnumberclass tempData1 = new Seniormgtnumberclass();

                            tempData1.setMemberName(temp.getString("MemberName"));
                            tempData1.setAppPassword(temp.getString("AppPassword"));
                            tempData1.setMemberType(temp.getString("MemberType"));
                            tempData1.setDesignation(temp.getString("Designation"));
                            tempData1.setIVRPassword(temp.getString("IVRPassword"));
                            tempData1.setMobileno(temp.getString("MobileNumber"));

                            Log.d("Server Array", temp.toString());
                            datasListschool.add(tempData1);
                        }
                        arrayList = new ArrayList<>();
                        arrayList.addAll(datasListschool);

                        snrmgtadapter.notifyDataSetChanged();
                    } else {
                        Alert("No data Received. Try Again.");
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

//    private void showToast(String msg) {
//        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//    }

    private void Alert(String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Senior_mgt_number.this);
        builder.setTitle(reason);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();


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
