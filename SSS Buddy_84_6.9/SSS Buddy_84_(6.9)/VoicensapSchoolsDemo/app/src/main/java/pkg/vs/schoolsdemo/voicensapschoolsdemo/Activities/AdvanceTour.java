package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters.AdvanceTourAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.AdvanceTourClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by devi on 7/6/2019.
 */

public class AdvanceTour extends AppCompatActivity {
    private ArrayList<AdvanceTourClass> tourList = new ArrayList<>();
    ArrayList<AdvanceTourClass> arrayListTour;
    AdvanceTourAdapter advanceTourAdapter;
    RecyclerView rvtourList;
    EditText txtsearch;
    ImageView fab;
    String iduser ;
    TextView txtgone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_advtour);

        rvtourList = (RecyclerView) findViewById(R.id.recycler_report2);
        txtsearch=(EditText)findViewById(R.id.ed_Search);
        txtgone=(TextView) findViewById(R.id.txtgone);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        fab=(ImageView)findViewById(R.id.img_plus1);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i=new Intent(AdvanceTour.this,FabActivity.class);
                startActivity(i);
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }


        iduser= SharedPreference_class.getUserid(AdvanceTour.this );
        Log.d("userid",iduser);

        advanceTourAdapter = new AdvanceTourAdapter(AdvanceTour.this,tourList);
        LinearLayoutManager circularLayoutManager = new LinearLayoutManager(AdvanceTour.this);
        rvtourList.setLayoutManager(circularLayoutManager);
        rvtourList.setItemAnimator(new DefaultItemAnimator());
        rvtourList.setAdapter(advanceTourAdapter);

        if (txtsearch != null) {
            txtsearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (advanceTourAdapter == null)
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

        //tourretrofit();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void tourretrofit() {

        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("idUser", iduser);

        Log.d("AdvanceTour:req", iduser);
        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);

        Call<JsonArray> call = apiService.AdvanceTour(iduser);

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                try {
                    Log.d("AdvanceTour:Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();

                    JSONArray jsonArrayorgList = new JSONArray(strResponse);
                    if (jsonArrayorgList.length() > 0) {

                        JSONObject temp;

                        tourList.clear();
                        for (int i = 0; i < jsonArrayorgList.length(); i++) {
                            temp = jsonArrayorgList.getJSONObject(i);


                            AdvanceTourClass tempData1 = new AdvanceTourClass();

                            String Msg = temp.getString("resultMessage");

                            if (Integer.parseInt(temp.getString("result")) == 1) {


                                tempData1.setStrtourid(temp.getString("TourId"));
                                tempData1.setStridtourexpenses(temp.getString("idTourExpense"));
                                tempData1.setStremployee(temp.getString("EmpName"));
                                tempData1.setStrtourdate(temp.getString("Date"));
                                tempData1.setStrplace(temp.getString("TourPlace"));
                                tempData1.setStrpurpose(temp.getString("TourPurpose"));
                                tempData1.setStrtotal(temp.getString("TotalTourExpense"));
                                tempData1.setStrpaid(temp.getString("PaidAmount"));
                                tempData1.setStrbalance(temp.getString("BalanceAmount"));
                                tempData1.setStrapproved(temp.getString("isApproved"));
                                tempData1.setStrstatus(temp.getString("Status"));
                                tempData1.setStrclaim(temp.getString("isClaimed"));


                                tourList.add(tempData1);
                            } else{
                                Alert(Msg);
                            }
                        }
                        Log.d("Server Response", strResponse);
//                        arrayListTour = new ArrayList<>();
//                        arrayListTour.addAll(tourList);
                        advanceTourAdapter = new AdvanceTourAdapter(AdvanceTour.this, tourList);
                        rvtourList.setAdapter(advanceTourAdapter);
                        advanceTourAdapter.notifyDataSetChanged();
                    } else {
                        alert("No data Received. Try Again.");
                    }


                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                Log.e("Response Failure", t.getMessage());
                alert("Server Connection Failed");
            }


        });
    }
    private void filter(String s) {
        List<AdvanceTourClass> temp = new ArrayList();
        for (AdvanceTourClass d : tourList) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            String value=d.getStrtourid().toLowerCase()+d.getStremployee().toLowerCase()+d.getStrtourdate().toLowerCase()+
                    d.getStrplace().toLowerCase()+d.getStrpurpose().toLowerCase()+d.getStrtotal().toLowerCase()+
                    d.getStrpaid().toLowerCase()+d.getStrbalance().toLowerCase()+d.getStrstatus().toLowerCase();
            if (value.contains(s.toLowerCase())) {
                temp.add(d);
            }
            else if(!value.contains(s.toLowerCase())){
                txtgone.setVisibility(View.VISIBLE);
            }
        }

//        if(temp.size()==0){
//            Toast.makeText(AdvanceTour.this, "No Records found ", Toast.LENGTH_SHORT).show();
//        }

        //update recyclerview
        advanceTourAdapter.updateList(temp);
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
    @Override
    public void onResume(){
        super.onResume();
        tourretrofit();
    }

    private void alert(String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdvanceTour.this);
        builder.setTitle(reason);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();

    }
    private void Alert(String Msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AdvanceTour.this);
        builder.setTitle("Alert");
        builder.setMessage(Msg);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // finish();
            }
        });
        builder.create().show();

    }
}
