package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter.RequirementsAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.RequirementModel;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;

public class RequirementsListScreen extends AppCompatActivity {
    RecyclerView requirementList;
    RequirementsAdapter madapter;
    private ArrayList<RequirementModel> dataList = new ArrayList<>();
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requirement_list);
        requirementList = (RecyclerView) findViewById(R.id.requirementList);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        userID = SharedPreference_class.getUserid(RequirementsListScreen.this);
        madapter = new RequirementsAdapter(dataList, RequirementsListScreen.this);
        LinearLayoutManager circularLayoutManager = new LinearLayoutManager(RequirementsListScreen.this);
        requirementList.setLayoutManager(circularLayoutManager);
        requirementList.setItemAnimator(new DefaultItemAnimator());
        requirementList.setAdapter(madapter);

        requirementsList();

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

    }


    @Override
    public void onBackPressed() {
        finish();
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

    private void requirementsList() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();


        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);
        Call<JsonArray> call = apiService.GetFeedbackRequirements(userID);

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
                        RequirementModel data;
                        dataList.clear();
                        for (int i = 0; i < jsonArrayorgList.length(); i++) {
                            temp = jsonArrayorgList.getJSONObject(i);
                            data = new RequirementModel(temp.getString("Title"), temp.getString("Description"),
                                    temp.getString("SentOn"), temp.getString("SentBy"), temp.getString("FeedBackID"));

                            dataList.add(data);
                        }

                        madapter.notifyDataSetChanged();
                    } else {
                        String msg = "No Data Received...";
                        Alert(msg, "");


                    }


                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                Alert("Server Connection Failed", "");
            }
        });
    }

    private void Alert(String resultMessage, final String status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RequirementsListScreen.this);
        builder.setTitle(resultMessage);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (status.equals("1")) {
                    dialog.cancel();
                    finish();
                } else {
                    dialog.cancel();
                }


            }
        });

        builder.create().show();


    }


}
