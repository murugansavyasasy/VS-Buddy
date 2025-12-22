package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter.Zero_Activity_Adapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.Zero_activity_DataClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;

public class Zero_Activity extends AppCompatActivity {

    RecyclerView rcy_schoollist;

    private String selectedStatus = ""; // "", "LIVE", "POC"

    Zero_Activity_Adapter Zero_Activity_Adapter;
    EditText txtSearch;
    String userId;

    RadioButton rbAll, rbInactive, rbActive;
    RadioGroup rbGroup;

    TextView txt_poccount, txt_livecount, txt_allcount, txt_nodatafound;

    ArrayList<Zero_activity_DataClass> data = new ArrayList();

    private ArrayList<Zero_activity_DataClass> datasList = new ArrayList<>();

    ArrayList<Zero_activity_DataClass> commonlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zero);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        rcy_schoollist = (RecyclerView) findViewById(R.id.rcy_schoollist);
        txtSearch = (EditText) findViewById(R.id.txtSearch);
        rbGroup = (RadioGroup) findViewById(R.id.rb_group);

        txt_poccount = findViewById(R.id.txt_poccount);
        txt_livecount = findViewById(R.id.txt_livecount);
        txt_allcount = findViewById(R.id.txt_allcount);
        txt_nodatafound = findViewById(R.id.txt_nodatafound);

        userId = SharedPreference_class.getShSchlLoginid(Zero_Activity.this);


        Zero_Activity_Adapter = new Zero_Activity_Adapter(datasList, Zero_Activity.this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(Zero_Activity.this);
        rcy_schoollist.setLayoutManager(mLayoutManager);
        rcy_schoollist.setItemAnimator(new DefaultItemAnimator());
        rcy_schoollist.setAdapter(Zero_Activity_Adapter);

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Zero_Activity_Adapter.filter(s.toString(), selectedStatus);
                handleNoData();
                updateCounts();
            }


            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        rbGroup.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == R.id.img_all) {
                selectedStatus = "";
                txt_allcount.setVisibility(View.VISIBLE);
                txt_livecount.setVisibility(View.GONE);
                txt_poccount.setVisibility(View.GONE);

            } else if (checkedId == R.id.imgInactive) {
                selectedStatus = "LIVE";
                txt_livecount.setVisibility(View.VISIBLE);
                txt_allcount.setVisibility(View.GONE);
                txt_poccount.setVisibility(View.GONE);

            } else if (checkedId == R.id.img_active) {
                selectedStatus = "POC";
                txt_poccount.setVisibility(View.VISIBLE);
                txt_allcount.setVisibility(View.GONE);
                txt_livecount.setVisibility(View.GONE);
            }

            Zero_Activity_Adapter.filter(
                    txtSearch.getText().toString(),
                    selectedStatus
            );
            handleNoData();
            updateCounts();

        });
    }

    private void updateCounts() {

        int count = Zero_Activity_Adapter.getFilteredCount();
        String text = "(" + count + ")";

        if (selectedStatus.equals("LIVE")) {
            txt_livecount.setText(text);
            txt_livecount.setVisibility(View.VISIBLE);
            txt_allcount.setVisibility(View.GONE);
            txt_poccount.setVisibility(View.GONE);

        } else if (selectedStatus.equals("POC")) {
            txt_poccount.setText(text);
            txt_poccount.setVisibility(View.VISIBLE);
            txt_allcount.setVisibility(View.GONE);
            txt_livecount.setVisibility(View.GONE);

        } else {
            txt_allcount.setText(text);
            txt_allcount.setVisibility(View.VISIBLE);
            txt_livecount.setVisibility(View.GONE);
            txt_poccount.setVisibility(View.GONE);
        }
    }


    private void handleNoData() {
        if (Zero_Activity_Adapter.getItemCount() == 0) {
            txt_nodatafound.setVisibility(View.VISIBLE);
        } else {
            txt_nodatafound.setVisibility(View.GONE);
        }
    }

    public void filter(String s) {
        Zero_Activity_Adapter.setData(datasList);
        data.clear();
        for (Zero_activity_DataClass d : datasList) {
            if (s.equals("")) {

                data.add(d);
                String allcount = String.valueOf(data.size());
                txt_allcount.setText("(" + allcount + ")");

                txt_allcount.setVisibility(View.VISIBLE);
                txt_livecount.setVisibility(View.GONE);
                txt_poccount.setVisibility(View.GONE);

            } else if (s.equals("LIVE") && d.getStatus().equals("LIVE")) {
                data.add(d);
                String livecount = String.valueOf(data.size());
                txt_livecount.setText("(" + livecount + ")");

                txt_livecount.setVisibility(View.VISIBLE);
                txt_allcount.setVisibility(View.GONE);
                txt_poccount.setVisibility(View.GONE);

            } else if (s.equals("POC") && d.getStatus().equals("POC")) {
                data.add(d);
                String poccount = String.valueOf(data.size());
                txt_poccount.setText("(" + poccount + ")");

                txt_poccount.setVisibility(View.VISIBLE);
                txt_allcount.setVisibility(View.GONE);
                txt_livecount.setVisibility(View.GONE);
            }
        }

        commonlist.addAll(data);
        Zero_Activity_Adapter.updateList(data);
        Log.d("updatelist", String.valueOf(data.size()));
        if (data.size() == 0) {
            txt_nodatafound.setVisibility(View.VISIBLE);
        }else {
            txt_nodatafound.setVisibility(View.GONE);
        }
    }


    private void UnusedSchoolList() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("User_id", userId);
        Log.d("Listdemo:req", jsonObject.toString());

        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        Call<JsonArray> call = apiService.UnusedSchoolList(jsonObject);

        call.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                if (mProgressDialog.isShowing()) mProgressDialog.dismiss();

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


                            Zero_activity_DataClass tempData = new Zero_activity_DataClass();
                            Log.d("Server Response", jsonObjectorgInfo.toString());

                            tempData.setSchoolId(jsonObjectorgInfo.getString("institute_id"));
                            tempData.setSchoolName(jsonObjectorgInfo.getString("institude_name"));
                            tempData.setSalesname(jsonObjectorgInfo.getString("sales_person"));
                            tempData.setStatus(jsonObjectorgInfo.getString("institute_status"));
                            tempData.setApplastuse(jsonObjectorgInfo.getString("last_app_login"));
                            tempData.setWeblastuse(jsonObjectorgInfo.getString("last_web_login"));


                            datasList.add(tempData);

                            String allcount = String.valueOf(datasList.size());
                            txt_allcount.setText("(" + allcount + ")");
                            txt_allcount.setVisibility(View.VISIBLE);

                        }
                        Zero_Activity_Adapter.setData(datasList);
                    } else {
                        Alert("No data Received. Try Again.");
                        txt_nodatafound.setVisibility(View.VISIBLE);

                    }


                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                        Alert("Server Connection Failed");
                    txt_nodatafound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                Alert("No data Received. Try Again.");
            }
        });
    }

    private void Alert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Zero_Activity.this);
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

    @Override
    protected void onResume() {

        UnusedSchoolList();
        super.onResume();
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