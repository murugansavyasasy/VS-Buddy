package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities;

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

import java.util.ArrayList;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters.AlertNotificationAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.AlertClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;

public class AlertNotificationScreen extends AppCompatActivity {
    AlertNotificationAdapter alertadapter;
    RecyclerView recycler_view1;
    List<AlertClass.DataBean> listalert = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);
        recycler_view1 = (RecyclerView) findViewById(R.id.recycler_view1);
        LinearLayoutManager circularLayoutManager = new LinearLayoutManager(AlertNotificationScreen.this);
        recycler_view1.setLayoutManager(circularLayoutManager);
        recycler_view1.setItemAnimator(new DefaultItemAnimator());
        recycler_view1.setAdapter(alertadapter);

        AlertNotificationAPI();
    }

    private void AlertNotificationAPI() {
        Log.d("test", "log");
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        Call<AlertClass> call = apiService.alert_messages();

        call.enqueue(new Callback<AlertClass>() {
            @Override
            public void onResponse(Call<AlertClass> call, retrofit2.Response<AlertClass> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                Log.d("URL", String.valueOf(response.code()));
                Log.d("AlertResponse:", response.toString());
                try {

                    if (response.body() != null) {
                        listalert = response.body().getData();
                        Log.d("listsize", String.valueOf(listalert.size()));
                        int statusCode = response.code();
                        Log.d("Status Code - Response", statusCode + " - " + response.body().toString());
                        alertadapter = new AlertNotificationAdapter(AlertNotificationScreen.this, listalert);
                        recycler_view1.setAdapter(alertadapter);
                        alertadapter.notifyDataSetChanged();
                    } else {
                        alert("No Data Received");
                    }
                } catch (Exception e) {

                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<AlertClass> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                alert("Server Connection Failed");
            }
        });
    }

    private void alert(String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(reason);
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
    public void onBackPressed() {
        super.onBackPressed();
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
}
