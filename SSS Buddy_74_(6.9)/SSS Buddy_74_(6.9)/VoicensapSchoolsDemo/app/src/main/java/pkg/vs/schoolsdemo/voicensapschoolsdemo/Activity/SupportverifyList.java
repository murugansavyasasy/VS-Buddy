package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

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

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter.Supportverifylistadapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.Supportverifylistclass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.OnPOCSupportClickListener;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.util.Util_common;
import retrofit2.Call;
import retrofit2.Callback;

public class SupportverifyList extends AppCompatActivity {
    Supportverifylistadapter supportadapter;
    RecyclerView rvPOCList;
    private ArrayList<Supportverifylistclass> datasListpoc = new ArrayList<>();
    private ProgressDialog pDialog;
    //    String serverPath = "http://192.168.0.77:8096/SchoolDemoAppService/Service.asmx/";
    String serverPath = "http://220.226.2.177:9000/StaffVoiceAnnouncerCloudSchool/Service.asmx/";
    private static final String SH_PASSWORD = "sPassword";
    ArrayList<Supportverifylistclass> arrayListpoc;
    String Password1, userId;
    RequestQueue requestQueue;
    SharedPreferences shpRemember;
    SharedPreferences.Editor ed;
    private static final String SH_USERS = "userInfo";
    private static final String SH_USERID = "UserId";


    ImageView img_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supportverify_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        rvPOCList = (RecyclerView) findViewById(R.id.rvsupportlist);
        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
        userId = shpRemember.getString(SH_USERID, null);
        Password1= shpRemember.getString(SH_PASSWORD, null);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

//        Log.d("Password", Password);

        supportadapter = new Supportverifylistadapter(datasListpoc, Password1, SupportverifyList.this, new OnPOCSupportClickListener() {
            @Override
            public void onPOCsupportClick(Supportverifylistclass item) {
                Intent i = new Intent(SupportverifyList.this, Supportverifysublist.class);
                i.putExtra("Supportverifylistclass", item);
                i.putExtra("REQUEST_CODE", Util_common.VS_SUPPORTVERFY);
                startActivity(i);
            }

        });

        LinearLayoutManager LayoutManagerpoc = new LinearLayoutManager(SupportverifyList.this);
        rvPOCList.setLayoutManager(LayoutManagerpoc);
        rvPOCList.setItemAnimator(new DefaultItemAnimator());
        rvPOCList.setAdapter(supportadapter);

    }



    private void supportpoclistretrofit() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoginID", userId);

        Log.d("Approvepoclist:req", jsonObject.toString());

        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        Call<JsonArray> call = apiService.ListAllPOCForSupportVerify(jsonObject);

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
//                            {"SchoolID":"1000","SchoolName":"djsfddfhj","schoolAddress":"ghghg",
//                                    "City":"hjhj","PinCode":"102100","SchoolMobile":"7777777777",
//                                    "SchoolEmail":"ytyy","ContactPerson":"tyty","FromDate":"Dec 12, 2017",
//                                    "ToDate":"Dec 12, 2018","SMSCaption":"tytyty","POCCreatedOn":"Dec 13, 2017",
//                                    "ToDate":"Dec 12, 2018","SMSCaption":"tytyty","POCCreatedOn":"Dec 13, 2017",
//                                    "isCallsUnlimited":"Unlimited","isSMSUnlimited":"","SMSCount":500,"CallCount":0,
//                                    "POCCreatedBy":"kdp","WelcomefileText":"dfgg"}
                            Supportverifylistclass temppocdata = new Supportverifylistclass();
                            temppocdata.setStrPOCsupportschoolid(temp.getString("SchoolID"));
                            temppocdata.setStrPOCsupportcreatedby(temp.getString("POCCreatedBy"));
                            temppocdata.setStrPOCsupportschoolname(temp.getString("SchoolName"));
                            temppocdata.setStrPOCsupportSchooladdr(temp.getString("schoolAddress"));
                            temppocdata.setStrPOCsupportcity(temp.getString("City"));
                            temppocdata.setStrPOCsupportemailid(temp.getString("SchoolEmail"));
                            temppocdata.setStrPOCsupportpincode(temp.getString("PinCode"));
                            temppocdata.setStrPOCsupportfromdate(temp.getString("FromDate"));
                            temppocdata.setStrPOCsupporttodate(temp.getString("ToDate"));
                            temppocdata.setStrsupportContactPerson(temp.getString("ContactPerson"));
                            temppocdata.setStrPOCsupportmobno(temp.getString("SchoolMobile"));
//                            temppocdata.setContactPerson2(temp.getString("ContactPerson2"));
//                            temppocdata.setContactNumber2(temp.getString("ContactNumber2"));
                            temppocdata.setStrPOCsupportmodules(temp.getString("Modules"));
//                            temppocdata.setStrPOCsupportisCallsUnlimited(temp.getString("isCallsUnlimited"));
//                            temppocdata.setStrPOCsupportisSMSUnlimited(temp.getString("SMSCount"));
                            temppocdata.setStrPOCsupportSMSCaption(temp.getString("SMSCaption"));
                            temppocdata.setStrPOCsupportWelcomefileText(temp.getString("WelcomefileText"));
                            temppocdata.setStrPOCsupportSMSCount(temp.getString("SMSCredits"));
                            temppocdata.setStrPOCsupportCallCount(temp.getString("CallCredits"));
                            temppocdata.setStrsupportPOCCreatedOn(temp.getString("POCCreatedOn"));

                            temppocdata.setStrPOCsupportpurchasetype(temp.getString("PurchaseType"));
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

                        supportadapter.notifyDataSetChanged();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        supportpoclistretrofit();
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
    private void Alert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SupportverifyList.this);
        builder.setTitle(msg);
//        builder.setMessage(reason);
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
