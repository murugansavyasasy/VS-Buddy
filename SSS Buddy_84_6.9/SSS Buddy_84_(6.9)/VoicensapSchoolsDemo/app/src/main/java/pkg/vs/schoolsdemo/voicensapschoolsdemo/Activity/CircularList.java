package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter.CircularListAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.CircularListClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;

public class CircularList extends AppCompatActivity {

    RecyclerView rvCircularList;
    CircularListAdapter circularlistadap;
    private ProgressDialog pDialog;
    //    private static final String SH_USERS = "userInfo";
//    private static final String SH_USERID = "UserId";
//    private static final String SH_LOGINTYPE = "slogintype";
//    private static final String SH_SCHOOLID = "sschoolid";
    String userId;
    String schoolid;
    //    SharedPreferences shpRemember;
//    SharedPreferences.Editor ed;
    RequestQueue requestQueue;
    int Result;
    int Status;
    String Logintype;
    private ArrayList<CircularListClass> circulardatalist = new ArrayList<>();
    ArrayList<CircularListClass> arrayListcircular;
    String serverPath = "http://220.226.2.177:9000/StaffVoiceAnnouncerCloudSchool/Service.asmx/";

    private SearchView mSearchView;
    private MenuItem searchMenuItem;

    private String strvoiceFile;
    ArrayList<CircularListClass> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular_list);
        rvCircularList = (RecyclerView) findViewById(R.id.rvCircularList);

        userId = SharedPreference_class.getShSchlLoginid(CircularList.this);
        Log.d("scholuserid", userId);
        Logintype = SharedPreference_class.getShSchlUsertype(CircularList.this);
        Log.d("logintype", Logintype);

//        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
//        userId = shpRemember.getString(SH_USERID, null);
//        Logintype=shpRemember.getString(SH_LOGINTYPE, null);
//        schoolid=shpRemember.getString(SH_SCHOOLID, null);
//        schoolid=shpRemember.getString(SH_SCHOOLID, null);

        Log.d("test", Logintype);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        circularlistadap = new CircularListAdapter(circulardatalist);


        LinearLayoutManager circularLayoutManager = new LinearLayoutManager(CircularList.this);
        rvCircularList.setLayoutManager(circularLayoutManager);
        rvCircularList.setItemAnimator(new DefaultItemAnimator());
        rvCircularList.setAdapter(circularlistadap);
//        getData();

//        if (!isNetworkConnected()) {
        Circularlistretrofit();
//        } else {
//            showToast("Check Your Internet Connection");
//        }

        circularlistadap.setOnItemClickListener(item -> {
            Intent intent = new Intent(CircularList.this, PlayAudioActivity.class);
            intent.putExtra("MessageId", item.getStrcircularid());
            Log.d("URL", "Sending MessageId: " + item.getStrcircularid());
            intent.putExtra("SchoolName", item.getStrcircularschoolname());
            Log.d("URL", "Sending SchoolName: " + item.getStrcircularschoolname());
            intent.putExtra("voiceFile", item.getStrVoiceFile());
            Log.d("URL", "Sending voiceFile: " + item.getStrVoiceFile());
            startActivity(intent);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_schoollist, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
//        MenuItem playItem = menu.findItem(R.id.action_play);

        // Access the LinearLayout inside the action_play item
//        LinearLayout playLayout = (LinearLayout) playItem.getActionView();
//        playLayout.setOnClickListener(view -> {
//            Intent intent = new Intent(CircularList.this, PlayAudioActivity.class);
//            startActivity(intent);
//        });

        mSearchView = (SearchView) searchMenuItem.getActionView();

        // Listen for SearchView expand/collapse to hide/show Play Audio
        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Hide play item when search is expanded
//                playItem.setVisible(false);
                return true; // allow expansion
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Show play item when search is collapsed
//                playItem.setVisible(true);
                return true; // allow collapse
            }
        });

        // Search query listener
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchFilter(newText);
                return false;
            }
        });

        return true;
    }


    private void searchFilter(String newText) {
        circularlistadap.clearAllData();

        if (newText.length() == 0) {
            circulardatalist.addAll(arrayListcircular);
            Log.d("testing", "hai");
        } else {
            for (int i = 0; i < arrayListcircular.size(); i++) {
                final String text = arrayListcircular.get(i).getStrcircularschoolname().toLowerCase() + arrayListcircular.get(i).getStrcircularschoolId() + arrayListcircular.get(i).getStrcircularid().toLowerCase();
                if (text.contains(newText)) {

                    circulardatalist.add(arrayListcircular.get(i));

                }
            }
        }
        circularlistadap.notifyDataSetChanged();

    }

    void getData() {
        String serverUrl = serverPath + "DemoGetCircularsInfoByMarketingPerson?LoginID=" + userId;
        Log.d("URL", serverUrl);
        if (isNetworkConnected()) {

            requestQueue = Volley.newRequestQueue(this);
            StringRequest strRequest = new StringRequest(Request.Method.GET, serverUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    hidePDialog();
                    try {
                        JSONObject obj = new JSONObject(response);
                        Log.d("Server Response", obj.toString());
                        JSONArray jsonArray = obj.getJSONArray("DemoGetCircularsInfoByMarketingPerson");
                        circulardatalist.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject temp = jsonArray.getJSONObject(i);
                            if (Integer.parseInt(temp.getString("Status")) == 1) {
                                CircularListClass tempData1 = new CircularListClass();
                                tempData1.setStrcircularschoolId(temp.getString("SchoolID"));
                                tempData1.setStrcircularschoolname(temp.getString("SchoolName"));
                                tempData1.setStrcircularid(temp.getString("CircularID"));
                                tempData1.setStrcircularattended(temp.getString("DeliveredCount"));
                                tempData1.setStrcircularcount(temp.getString("TargetCount"));
                                tempData1.setStrcircularmissed(temp.getString("MissedCount"));
                                tempData1.setStrserverresponcecount(temp.getString("ServerResponceCount"));
                                tempData1.setStrEODTiming(temp.getString("EODTiming"));
                                tempData1.setStrVoiceFile(temp.getString("voiceFile"));
                                Log.d("CircularListItem", "Set VoiceFile: " + tempData1.getStrVoiceFile());

                                Log.d("Server Array", temp.toString());
                                circulardatalist.add(tempData1);
//                                Toast.makeText(MyschoolList.this,temp.getString("reason"), Toast.LENGTH_SHORT).show();
                            } else {
                                String reason = temp.getString("reason");
                                Alert(reason);
//                                Toast.makeText(CircularList.this,temp.getString("reason"), Toast.LENGTH_SHORT).show();
                            }
                        }
                        arrayListcircular = new ArrayList<>();
                        arrayListcircular.addAll(circulardatalist);

                        circularlistadap.notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.d("Exception", e.toString());
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hidePDialog();
                            Log.d("MyError", error.toString());
                        }
                    });


            strRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });
            requestQueue.add(strRequest);

            pDialog = new ProgressDialog(CircularList.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        } else {
            Toast.makeText(this, "Check Your Internet connection", Toast.LENGTH_SHORT).show();
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

//            case R.id.action_play:
//                Intent intent = new Intent(CircularList.this, PlayAudioActivity.class);
//                startActivity(intent);
//                return true;

            // case R.id.menu_tohome:
            //     Intent intent2 = new Intent(CircularList.this, Addaccount.class);
            //     startActivity(intent2);
            //     return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void Circularlistretrofit() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoginID", userId);
        Log.d("CircularList API Request", jsonObject.toString());

        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        Call<JsonArray> call = apiService.CircularList(jsonObject);

        call.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                try {
                    Log.d("Circularlist:Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();

                    JSONArray jsonArrayorgList = new JSONArray(strResponse);
                    if (jsonArrayorgList.length() > 0) {
                        JSONObject temp;
                        circulardatalist.clear();
                        for (int i = 0; i < jsonArrayorgList.length(); i++) {
                            temp = jsonArrayorgList.getJSONObject(i);
                            CircularListClass tempData1 = new CircularListClass();
                            tempData1.setStrcircularschoolId(temp.getString("SchoolId"));
                            tempData1.setStrcircularschoolname(temp.getString("SchoolName"));
                            tempData1.setStrcircularid(temp.getString("MessageId"));
                            tempData1.setStrcircularattended(temp.getString("Connected"));
                            tempData1.setStrcircularcount(temp.getString("TotalCalls"));
                            tempData1.setStrcircularmissed(temp.getString("Missed"));
                            tempData1.setStrserverresponcecount(temp.getString("Requested"));
                            tempData1.setStrEODTiming(temp.getString("Time"));
                            tempData1.setStrVoiceFile(temp.getString("voiceFile"));
                            Log.d("CircularListItem", "Set VoiceFile: " + tempData1.getStrVoiceFile());

//                                tempData1.setStrSentByName(temp.getString("SentByName"));
//                                tempData1.setStrCircularType(temp.getString("CircularType"));
                            Log.d("Server Array", temp.toString());
                            circulardatalist.add(tempData1);
                        }
                        arrayListcircular = new ArrayList<>();
                        arrayListcircular.addAll(circulardatalist);

                        circularlistadap.notifyDataSetChanged();
                    } else {
                        String msg = "No data Received. Try Again..";
                        Alert(msg);

//                        showToast("No data Received. Try Again.");
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
        AlertDialog.Builder builder = new AlertDialog.Builder(CircularList.this);
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


