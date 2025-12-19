package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter.CreateDemoListAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.CreateDemoListClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.OnDemoClickListener;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;


public class CreateDemoList extends AppCompatActivity {

    RecyclerView rvlist;
    CreateDemoListAdapter createdemolistadapter;
    private ArrayList<CreateDemoListClass> datasList = new ArrayList<>();
    RequestQueue requestQueue;

    //    private static final String SH_USERS = "userInfo";
//    private static final String SH_USERID = "UserId";
    //    SharedPreferences shpRemember;
//    SharedPreferences.Editor ed;
    int Result;
    String Reason, userId, demoId;
    String serverPath = "http://220.226.2.177:9000/StaffVoiceAnnouncerCloudSchool/Service.asmx/";
    private ProgressDialog pDialog;

    private SearchView mSearchView;
    private MenuItem searchMenuItem;
    //    int userid=0;
    EditText txtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_demo_list);

        rvlist = (RecyclerView) findViewById(R.id.rvDemoList);
        txtSearch = (EditText) findViewById(R.id.txtSearch);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
//        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
//        userId = shpRemember.getString(SH_USERID, null);

        userId = SharedPreference_class.getShSchlLoginid(CreateDemoList.this);


        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        createdemolistadapter = new CreateDemoListAdapter(datasList, CreateDemoList.this, new OnDemoClickListener() {
            @Override
            public void onDemoClick(CreateDemoListClass item) {


                Intent i = new Intent(CreateDemoList.this, EditCreateDemo.class);

                demoId = item.getStrDemoId();
                String phno = item.getStrDemoParentNumber();
//                i.putExtra("VALUE",demoIphnod);
                i.putExtra("VALUE", item.getStrDemoId());

//                i.putExtra("VALUE1",item.getStrDemoSchoolName());
//                i.putExtra("VALUE2",item.getStrDemoPrincipalNumber());
//                i.putExtra("VALUE3",item.getStrDemoParentNumber());
//                i.putExtra("VALUE4",item.getStrDemoEmailId());

                startActivity(i);


            }

        });

//        Float a=Float.valueOf(userId);
//        userid=(int)(Math.round(a));
//        Log.d("Integer", String.valueOf(userid));


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(CreateDemoList.this);
        rvlist.setLayoutManager(mLayoutManager);
        rvlist.setItemAnimator(new DefaultItemAnimator());
        rvlist.setAdapter(createdemolistadapter);


        if (txtSearch != null) {
            txtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (createdemolistadapter == null)
                        return;

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    // TODO Auto-generated method stub
                }

                @Override
                public void afterTextChanged(Editable s) {

                    filter(s.toString());
                    //you can use runnable postDelayed like 500 ms to delay search text
                }
            });
        }


//        getData1();
        Listdemoretrofit();
    }

    private void filter(String s) {
        List<CreateDemoListClass> temp = new ArrayList();
        for (CreateDemoListClass d : datasList) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            String value = d.getStrDemoSchoolName().toLowerCase() + d.getStrDemoId().toLowerCase();
            if (value.contains(s.toLowerCase())) {
                temp.add(d);
            }
        }
        //update recyclerview
        createdemolistadapter.updateList(temp);
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
        super.onBackPressed();
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return (true);

            case R.id.Refresh: {

                Listdemoretrofit();
                return (true);
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onResume() {
        hidePDialog();
        super.onResume();
//        getData1();

//        if (!isNetworkConnected()) {
        Listdemoretrofit();
//        } else {
//            showToast("Check Your Internet Connection");
//        }
    }


    private void Listdemoretrofit() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("LoginID", userId);
//        Log.d("Listdemo:req", jsonObject.toString());

        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        Call<JsonArray> call = apiService.GetDemosByLoginId(userId);

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
                        JSONObject jsonObjectorgInfo;
                        datasList.clear();
                        for (int i = 0; i < jsonArrayorgList.length(); i++) {
                            jsonObjectorgInfo = jsonArrayorgList.getJSONObject(i);


                            CreateDemoListClass tempData = new CreateDemoListClass();
                            Log.d("Server Response", jsonObjectorgInfo.toString());
                            tempData.setStrDemoId(jsonObjectorgInfo.getString("DemoId"));
                            tempData.setStrDemoSchoolName(jsonObjectorgInfo.getString("SchoolName"));
                            tempData.setStrDemoPrincipalNumber(jsonObjectorgInfo.getString("PrincipalNumber"));


//                            tempData.setStrDemoEmailId(jsonObjectorgInfo.getString("PrincipalEmail"));
//                            tempData.setStrDemoParentNumber(jsonObjectorgInfo.getString("ParentNos"));
//                            String s1 = jsonObjectorgInfo.getString("ParentNos");
//                            String count[] = s1.split(",");
//                            String words = String.valueOf(count.length);
//                            tempData.setStrDemoParentNumbercount(words);
//                            Log.d("Server Array", jsonObjectorgInfo.toString());

                            datasList.add(tempData);

//                            else{
//                                showToast("No Customers available");
//                            }

                        }
                        createdemolistadapter.notifyDataSetChanged();
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

    private void Alert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateDemoList.this);
        builder.setTitle(msg);
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
