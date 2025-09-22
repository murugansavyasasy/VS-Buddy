package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter.MySchoolListAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.MyschoolListclass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.OnSchoolClickListener;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;

public class MyschoolList extends AppCompatActivity {

    RecyclerView rvschoollist;
    Button message;
    MyschoolListclass myschoollistclass = new MyschoolListclass();
    MySchoolListAdapter myschoollistadapter;
    private ProgressDialog pDialog;
    private static final String SH_USERS = "userInfo";
    private static final String SH_USERID = "UserId";
    private static final String SH_LOGINTYPE = "slogintype";
    private static final String SH_SCHOOLID = "sschoolid";
    private static final String SH_STATUS = "sstatus";
    String userId;
    SharedPreferences shpRemember;
    SharedPreferences.Editor ed;
    RequestQueue requestQueue;
    int Result;
    String Status;
    RelativeLayout layout_active, layout_stopped;
    RelativeLayout layout_inactive, layout_all;
    RadioButton rbAll, rbInactive, rbActive, rbStop, img_Poc, imgPocInactive;
    RadioGroup rbGroup, rb_groupPoc;
    String Logintype, schoolid;
    TextView txt_gone, txt_all, txt_active, txt_Inactive, txt_stoped, txtPoc_allcount, txtpoc_inactive;
    private ArrayList<MyschoolListclass> datasListschool = new ArrayList<>();
    ArrayList<MyschoolListclass> arrayList;
    ArrayList<MyschoolListclass> commonlist = new ArrayList<>();
    List<MyschoolListclass> data = new ArrayList();
    String str_status;
    String value;
    String newText = "";
    private SearchView mSearchView;
    private MenuItem searchMenuItem;
    private int index;

    //    String serverPath =  "http://192.168.0.77:8096/SchoolDemoAppService/Service.asmx/";
    String serverPath = "http://220.226.2.177:9000/StaffVoiceAnnouncerCloudSchool/Service.asmx/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myschool_list);
        rvschoollist = (RecyclerView) findViewById(R.id.rvSchoolList);

        rbAll = (RadioButton) findViewById(R.id.img_all);
        rbInactive = (RadioButton) findViewById(R.id.imgInactive);
        rbActive = (RadioButton) findViewById(R.id.img_active);
        rbStop = (RadioButton) findViewById(R.id.img_stopped);
        img_Poc = (RadioButton) findViewById(R.id.img_Poc);
        imgPocInactive = (RadioButton) findViewById(R.id.imgPocInactive);

        rbGroup = (RadioGroup) findViewById(R.id.rb_group);
        rb_groupPoc = (RadioGroup) findViewById(R.id.rb_groupPoc);
        txt_gone = (TextView) findViewById(R.id.txt_gone);

        txt_all = findViewById(R.id.txt_allcount);
        txt_Inactive = findViewById(R.id.txt_inactive);
        txt_active = findViewById(R.id.txt_Active);
        txt_stoped = findViewById(R.id.txt_Stoped);
        txtPoc_allcount = findViewById(R.id.txtPoc_allcount);
        txtpoc_inactive = findViewById(R.id.txtpoc_inactive);

        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
        userId = shpRemember.getString(SH_USERID, null);
        Logintype = shpRemember.getString(SH_LOGINTYPE, "");
        schoolid = shpRemember.getString(SH_SCHOOLID, "");
        Status = shpRemember.getString(SH_STATUS, "");

        Log.d("test", Logintype);
        Log.d("status", Status);
        Log.d("schoolid", schoolid);
        Log.d("Statusc", Status);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);
//        Log.d("Mylistschoolid",schoolid);
        myschoollistadapter = new MySchoolListAdapter(datasListschool, Status, MyschoolList.this, new OnSchoolClickListener() {
            @Override
            public void onSchoolClick(MyschoolListclass item) {
                Intent i = new Intent(MyschoolList.this, SchoolSubList.class);
                i.putExtra("SCHOOLVALUE", item.getStrSchoolId());
                schoolid = item.getStrSchoolId();
                Log.d("Mylistyschoolid", schoolid);
                i.putExtra("MyschoolListclass", item);
                startActivity(i);

            }

        }, Logintype);


        LinearLayoutManager LayoutManager = new LinearLayoutManager(MyschoolList.this);
        rvschoollist.setLayoutManager(LayoutManager);
        rvschoollist.setItemAnimator(new DefaultItemAnimator());
        rvschoollist.setAdapter(myschoollistadapter);

        final RadioGroup.OnCheckedChangeListener[] groupListener2 = new RadioGroup.OnCheckedChangeListener[1];

        RadioGroup.OnCheckedChangeListener groupListener1 = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    rb_groupPoc.setOnCheckedChangeListener(null);
                    rb_groupPoc.clearCheck();
                    rb_groupPoc.setOnCheckedChangeListener(groupListener2[0]); // <-- use the array reference
                }

                switch (checkedId) {
                    case R.id.img_all:
                        if (rbAll.isChecked()) {
                            commonlist.clear();
                            filter("All");
                            searchFilter(newText);
                        }
                        break;
                    case R.id.imgInactive:
                        if (rbInactive.isChecked()) {
                            commonlist.clear();
                            filter("live_inactive");
                            searchFilter(newText);
                        }
                        break;
                    case R.id.img_active:
                        if (rbActive.isChecked()) {
                            commonlist.clear();
                            filter("live_active");
                            searchFilter(newText);
                        }
                        break;
                }
            }
        };

        groupListener2[0] = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    rbGroup.setOnCheckedChangeListener(null);
                    rbGroup.clearCheck();
                    rbGroup.setOnCheckedChangeListener(groupListener1);
                }

                switch (checkedId) {
                    case R.id.img_Poc:
                        if (img_Poc.isChecked()) {
                            commonlist.clear();
                            filter("poc_active");
                            searchFilter(newText);
                        }
                        break;
                    case R.id.imgPocInactive:
                        if (imgPocInactive.isChecked()) {
                            commonlist.clear();
                            filter("poc_inactive");
                            searchFilter(newText);
                        }
                        break;
                    case R.id.img_stopped:
                        if (rbStop.isChecked()) {
                            commonlist.clear();
                            filter("STOPPED");
                            searchFilter(newText);
                        }
                        break;
                }
            }
        };

// Attach listeners
        rbGroup.setOnCheckedChangeListener(groupListener1);
        rb_groupPoc.setOnCheckedChangeListener(groupListener2[0]);
        Myschoollistretrofit();
    }


    public void filter(String s) {

        myschoollistadapter.notifyDataSetChanged();
        data.clear();
        for (MyschoolListclass d : datasListschool) {

            Log.d("dataListCount", String.valueOf(datasListschool.size()));

            if (s.equals("All")) {

                String Active = String.valueOf(datasListschool.size());
                txt_all.setText("(" + Active + ")");
                txt_stoped.setVisibility(View.GONE);
                txt_active.setVisibility(View.GONE);
                txt_all.setVisibility(View.VISIBLE);
                txt_Inactive.setVisibility(View.GONE);
                txtPoc_allcount.setVisibility(View.GONE);
                txtpoc_inactive.setVisibility(View.GONE);
                data.add(d);

            }

            if (s.equals("live_inactive") && d.getStrschoolStatus().equals("LIVE") && d.getStrstatus().equals("0")) {

                data.add(d);
                String Inactive = String.valueOf(data.size());
                txt_Inactive.setText("(" + Inactive + ")");
                txt_Inactive.setVisibility(View.VISIBLE);
                txt_stoped.setVisibility(View.GONE);
                txt_active.setVisibility(View.GONE);
                txt_all.setVisibility(View.GONE);
                txtPoc_allcount.setVisibility(View.GONE);
                txtpoc_inactive.setVisibility(View.GONE);

            } else if (d.getStrschoolStatus().equals("LIVE") && d.getStrstatus().equals("1") && s.equals("live_active")) {
                data.add(d);
                String Active = String.valueOf(data.size());
                txt_active.setText("(" + Active + ")");
                txt_active.setVisibility(View.VISIBLE);
                txt_stoped.setVisibility(View.GONE);
                txt_Inactive.setVisibility(View.GONE);
                txt_all.setVisibility(View.GONE);
                txtPoc_allcount.setVisibility(View.GONE);
                txtpoc_inactive.setVisibility(View.GONE);

            }


            if (s.equals("poc_active") && d.getStrschoolStatus().equals("POC") && d.getStrstatus().equals("1")) {

                data.add(d);
                String Inactive = String.valueOf(data.size());
                txtPoc_allcount.setText("(" + Inactive + ")");
                txt_stoped.setVisibility(View.GONE);
                txt_active.setVisibility(View.GONE);
                txt_Inactive.setVisibility(View.GONE);
                txtPoc_allcount.setVisibility(View.VISIBLE);
                txt_all.setVisibility(View.GONE);
                txtpoc_inactive.setVisibility(View.GONE);

            } else if (d.getStrschoolStatus().equals("POC") && d.getStrstatus().equals("0") && s.equals("poc_inactive")) {
                data.add(d);
                String Active = String.valueOf(data.size());
                txtpoc_inactive.setText("(" + Active + ")");
                txt_active.setVisibility(View.GONE);
                txtpoc_inactive.setVisibility(View.VISIBLE);
                txt_stoped.setVisibility(View.GONE);
                txt_Inactive.setVisibility(View.GONE);
                txtPoc_allcount.setVisibility(View.GONE);
                txt_all.setVisibility(View.GONE);

            }

            if (s.equals("STOPPED") && d.getStrschoolStatus().equals("STOPPED")) {

                data.add(d);
                String stopcount = String.valueOf(data.size());
                txt_stoped.setText("(" + stopcount + ")");
                txt_all.setVisibility(View.GONE);
                txt_active.setVisibility(View.GONE);
                txt_Inactive.setVisibility(View.GONE);
                txtpoc_inactive.setVisibility(View.GONE);
                txtPoc_allcount.setVisibility(View.GONE);
                txt_stoped.setVisibility(View.VISIBLE);

            }
        }

        commonlist.addAll(data);
        myschoollistadapter.updateList(data);
        Log.d("updatelist", String.valueOf(data.size()));

    }


    private void searchFilter(String charText) {
        newText = charText;

        if (charText == null) {
            newText = "";
        } else {
            newText = charText;
        }
        Log.d("search", "search");
        myschoollistadapter.notifyDataSetChanged();
        arrayList.clear();

        if (charText.length() == 0) {

            arrayList.addAll(commonlist);
            Log.d("sizeofarray", String.valueOf(arrayList.size()));
            txt_gone.setVisibility(View.GONE);
            rvschoollist.setVisibility(View.VISIBLE);

        } else {
            for (int i = 0; i < commonlist.size(); i++) {
                final String text = commonlist.get(i).getStrSchoolNameList().toLowerCase() + commonlist.get(i).getStrSchoolId() + commonlist.get(i).getStrstatus();
                if (text.contains(charText)) {
                    arrayList.add(commonlist.get(i));
                }

            }
            if (arrayList.size() > 0) {
                myschoollistadapter.updateList(arrayList);
                txt_gone.setVisibility(View.GONE);
                rvschoollist.setVisibility(View.VISIBLE);
            } else {
                txt_gone.setVisibility(View.VISIBLE);
                rvschoollist.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_schoollist, menu);
        searchMenuItem = menu.findItem(R.id.action_search);

        mSearchView = (SearchView) searchMenuItem.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String charText) {
                searchFilter(charText);
                return false;
            }
        });
        return true;
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


    private void Myschoollistretrofit() {

        myschoollistadapter.notifyDataSetChanged();
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoginID", userId);

        Log.d("Listdemo:req", jsonObject.toString());

        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        Call<JsonArray> call = apiService.Myschoollist(jsonObject);

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
                            MyschoolListclass tempData1 = new MyschoolListclass();
                            tempData1.setStrSchoolId(temp.getString("SchoolID"));
                            tempData1.setStrSchoolNameList(temp.getString("SchoolName"));
                            tempData1.setStrSchooladdr(temp.getString("Address"));
                            tempData1.setStrSchoolcity(temp.getString("City"));
                            tempData1.setStrDidschool(temp.getString("SchoolDID"));
                            tempData1.setStrUserName(temp.getString("UserName"));
                            tempData1.setStrPassword(temp.getString("Password"));
                            tempData1.setStrFromdate(temp.getString("PeriodFrom"));
                            tempData1.setStrTodate(temp.getString("PeriodTo"));
                            tempData1.setStrschoolStatus(temp.getString("Status"));
                            tempData1.setSalespersionname(temp.getString("sales_person"));
                            tempData1.setContactPerson1(temp.getString("ContactPerson1"));
                            tempData1.setContactNumber1(temp.getString("ContactNumber1"));
                            tempData1.setContactPerson2(temp.getString("ContactPerson2"));
                            tempData1.setContactNumber2(temp.getString("ContactNumber2"));
                            tempData1.setContactEmail(temp.getString("ContactEmail"));
                            tempData1.setWebUsername(temp.getString("UserName"));

                            tempData1.setStrstudentcount(temp.getString("Students"));

                            tempData1.setStrstaffcount(temp.getString("Staff"));

                            str_status = temp.getString("isActive");
                            Log.d("log_status", str_status);
                            tempData1.setStrstatus(temp.getString("isActive"));


                            if (temp.has("CallCount")) {
                                tempData1.setCallsCount(temp.getString("CallCount"));
                            }

                            if (temp.has("SMSCount")) {
                                tempData1.setSmsCount(temp.getString("CallCount"));
                            }


                            Log.d("Server Array", temp.toString());
                            datasListschool.add(tempData1);
                        }

                        commonlist.addAll(datasListschool);
                        Log.d("datasListschool", String.valueOf(datasListschool.size()));

                        String Allcount = String.valueOf(datasListschool.size());
                        txt_all.setText("(" + Allcount + ")");
                        txt_all.setVisibility(View.VISIBLE);


                        arrayList = new ArrayList<>();
                        arrayList.addAll(datasListschool);

                        myschoollistadapter.notifyDataSetChanged();
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


    private void Alert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyschoolList.this);
        builder.setTitle(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();

    }

//    private void showToast(String msg) {
//        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//    }
}

