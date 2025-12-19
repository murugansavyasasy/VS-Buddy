package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter.ApprovepocAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.Approvepocclass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.OnPOCClickListener;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.util.Util_common;
import retrofit2.Call;
import retrofit2.Callback;

public class Approve_poc extends AppCompatActivity {
    RecyclerView rvPOCList;
    ApprovepocAdapter approvepocAdapter;
    private ArrayList<Approvepocclass> datasListpoc = new ArrayList<>();
    private ProgressDialog pDialog;
    //    String serverPath = "http://192.168.0.77:8096/SchoolDemoAppService/Service.asmx/";
    String serverPath = "http://220.226.2.177:9000/StaffVoiceAnnouncerCloudSchool/Service.asmx/";
//    private static final String SH_PASSWORD = "sPassword";
    ArrayList<Approvepocclass> arrayListpoc;
    String Password, userId;
    RequestQueue requestQueue;
//    SharedPreferences shpRemember;
//    SharedPreferences.Editor ed;
//    private static final String SH_USERS = "userInfo";
//    private static final String SH_USERID = "UserId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_poc);
        rvPOCList = (RecyclerView) findViewById(R.id.rvpocList);


        userId= SharedPreference_class.getShSchlLoginid(Approve_poc.this);
        Password= SharedPreference_class.getShPassword(Approve_poc.this);

//        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
//        userId = shpRemember.getString(SH_USERID, null);
//        Password = shpRemember.getString(SH_PASSWORD, null);
//        Log.d("Password", Password);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        approvepocAdapter = new ApprovepocAdapter(datasListpoc, Password, Approve_poc.this, new OnPOCClickListener() {
            @Override
            public void onPOCClick(Approvepocclass item) {


                Intent i = new Intent(Approve_poc.this, Pocapprovesublist.class);
                i.putExtra("Approvepocclass", item);
                i.putExtra("VALUE", item.getStrPOCid());
                i.putExtra("REQUEST_CODE", Util_common.VS_APPROVEPOC);
                startActivity(i);
            }

        });

        LinearLayoutManager LayoutManagerpoc = new LinearLayoutManager(Approve_poc.this);
        rvPOCList.setLayoutManager(LayoutManagerpoc);
        rvPOCList.setItemAnimator(new DefaultItemAnimator());
        rvPOCList.setAdapter(approvepocAdapter);
//        getData();

    }

    @Override
    protected void onResume() {
        hidePDialog();
        super.onResume();
//        getData1();
//        if (!isNetworkConnected()) {
            Approvepoclistretrofit();
//        } else {
//            showToast("Check Your Internet Connection");
//        }
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

    private void Approvepoclistretrofit() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoginID", userId);

        Log.d("Approvepoclist:req", jsonObject.toString());

        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        Call<JsonArray> call = apiService.ApprovePocList(jsonObject);

        call.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                try {

                    Log.d("Approvepoclist:Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();

                    JSONArray jsonArrayorgList = new JSONArray(strResponse);
                    if (jsonArrayorgList.length() > 0) {
                        JSONObject temp;
                        datasListpoc.clear();
                        for (int i = 0; i < jsonArrayorgList.length(); i++) {
                            temp = jsonArrayorgList.getJSONObject(i);

                            Approvepocclass temppocdata = new Approvepocclass();
                            temppocdata.setStrPOCid(temp.getString("SchoolID"));
                            temppocdata.setStrPOCby(temp.getString("Createdby"));
                            temppocdata.setStrPOCschoolname(temp.getString("SchoolName"));
                            temppocdata.setStrPOCSchooladdr(temp.getString("Address"));
                            temppocdata.setStrPOCcity(temp.getString("City"));
                            temppocdata.setStrPOCemailid(temp.getString("Email"));
                            temppocdata.setStrPOCpincode(temp.getString("Pincode"));
                            temppocdata.setStrPOCfromdate(temp.getString("PeriodFrom"));
                            temppocdata.setStrPOCtodate(temp.getString("PeriodTo"));
                            temppocdata.setStrPOCprinciname(temp.getString("ContactPerson1"));
                            temppocdata.setStrPOCmobno(temp.getString("ContactNumber1"));
                            temppocdata.setContactPerson2(temp.getString("ContactPerson2"));
                            temppocdata.setContactNumber2(temp.getString("ContactNumber2"));
                            temppocdata.setStrPOCmodule(temp.getString("Modules"));
                            temppocdata.setCallcredits(temp.getString("CallCredits"));
                            temppocdata.setSmscredit(temp.getString("SMSCredits"));
                            temppocdata.setStrPOCcaption(temp.getString("Caption"));
                            temppocdata.setStrPOCpurchasetype(temp.getString("PurchaseType"));
//                            temppocdata.setStrPOCcaption(temp.getString("Caption"));

//                            temppocdata.setStrPOCphno(temp.getString("ConfigureMobileMGT"));
//                            temppocdata.setStrPOCemailid(temp.getString("SchoolEmail"));
//                            temppocdata.setStrPOCremarks(temp.getString("Remarks"));
//                            temppocdata.setStrPOCdbrequried(temp.getString("DatabaseThrough"));
//                            temppocdata.setStrPOCdemo(temp.getString("HasDemoGiven"));
//                            temppocdata.setStrPOCscope(temp.getString("IsSchoolawareofServices"));


                            Log.d("Server Array", temp.toString());
                            datasListpoc.add(temppocdata);


//                            else{
//                                showToast("No Customers available");
//                            }

                        }
                        arrayListpoc = new ArrayList<>();
                        arrayListpoc.addAll(datasListpoc);

                        approvepocAdapter.notifyDataSetChanged();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Approve_poc.this);
        builder.setTitle(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();

//        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
