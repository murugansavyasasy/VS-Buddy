package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities;

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters.ReportLocalAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.Report_localExpenseClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;


public class Report extends AppCompatActivity {
    private static final String TAG = Report.class.getSimpleName();
    String Referenceid, Employee, Month, Amount, Description, Status;
    String iduser;
    EditText search;
    int isApproved;
    String idLocalExp,filepath;
    TextView txtgone;

    ImageView imagePlus;
    private ArrayList<Report_localExpenseClass> reportlist = new ArrayList<>();
    ReportLocalAdapter Radapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_report);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        imagePlus = (ImageView) findViewById(R.id.img_plus);
        search = (EditText) findViewById(R.id.ed_Search);
        txtgone = (TextView) findViewById(R.id.txtgone);

        iduser = SharedPreference_class.getUserid(Report.this);
        Log.d("userid", iduser);
        imagePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Report.this, LocalConveyance.class);
                startActivity(i);
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recycler_report);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        Radapter = new ReportLocalAdapter(Report.this, reportlist);


        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(Radapter);


        if (search != null) {

            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (Radapter == null) {
                        return;
                    }

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    // TODO Auto-generated method stub
                }

                @Override
                public void afterTextChanged(Editable s) {

                    filter(s.toString());
                }
            });
        }

        // GetReport();

    }

    private void filter(String s) {
        ArrayList<Report_localExpenseClass> data = new ArrayList();
//        Radapter.notifyDataSetChanged();
        for (Report_localExpenseClass d : reportlist) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches

            String value = d.getUsername().toLowerCase() + d.getRefId().toLowerCase() + d.getMonthOfClaim().toLowerCase() +
                    d.getTotalLocalExpense().toLowerCase() + d.getDescription().toLowerCase() + d.getStatus().toLowerCase();
            if (value.contains(s.toLowerCase())) {
                data.add(d);
            } else if (!value.contains(s.toLowerCase())) {
                txtgone.setVisibility(View.VISIBLE);
            }
        }


        Radapter.updateList(data);

    }


    private void GetReport() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setTitle(" Loading");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);
//        Log.d("InputRequest",iduser);

        Call<JsonArray> call = apiService.GetReport(iduser);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                try {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    {
                        Log.d("customer:code-res", response.code() + " - " + response.toString());
                        if (response.code() == 200 || response.code() == 201) {
                            Log.d("Response", response.body().toString());
//                            Log.d("URL", String.valueOf(response.code()));
                            JSONArray array = new JSONArray(response.body().toString());
                            if (array.length() > 0) {
                                reportlist.clear();
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object1 = array.getJSONObject(i);

                                    String Msg = object1.getString("ResultMessage");

                                    if (Integer.parseInt(object1.getString("Result")) == 1) {

                                        idLocalExp = object1.getString("idLocalExpense");
                                        SharedPreference_class.putReport_Expenseidlocal(Report.this, idLocalExp);
                                        Referenceid = object1.getString("RefId");
                                        Employee = object1.getString("Username");
                                        Month = object1.getString("monthOfClaim");
                                        Amount = object1.getString("TotalLocalExpense");
                                        Status = object1.getString("Status");
                                        Description = object1.getString("Description");
                                        isApproved = object1.getInt("IsApproved");
                                        filepath=object1.getString("FilePath");

                                        Report_localExpenseClass report = new Report_localExpenseClass(Referenceid, Employee, Month, Amount, Description, isApproved, idLocalExp, Status,filepath);
                                        reportlist.add(report);
//                                        Log.d("listsize", String.valueOf(reportlist.size()));

                                        Radapter = new ReportLocalAdapter(Report.this, reportlist);
                                        recyclerView.setAdapter(Radapter);
                                        Radapter.notifyDataSetChanged();

                                    } else {
                                        Alert(Msg);
                                    }
                                }

                            } else {
                                alert("No Data Received");
                            }

                        } else {
                            alert("Server Conncection Failed");
//                            Toast.makeText(Report.this, "No Records has found", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (JSONException e) {


                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                Log.e("Response Failure", t.getMessage());
                alert("Server Connection Failed");
                Log.e(TAG, t.toString());


            }
        });
    }

    private void Alert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Report.this);
        builder.setTitle(msg);
//        builder.setMessage();
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });
        builder.create().show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        finish();
        return true;
    }

    @Override

    protected void onResume() {
        super.onResume();
        GetReport();

    }

    private void alert(String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Report.this);

        builder.setTitle(reason);
//        builder.setMessage(reason);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();


    }

}
