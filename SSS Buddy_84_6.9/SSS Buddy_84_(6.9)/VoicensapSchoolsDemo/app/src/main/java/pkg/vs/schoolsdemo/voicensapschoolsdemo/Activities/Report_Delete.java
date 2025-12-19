package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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


public class Report_Delete extends AppCompatActivity {

    private static final String TAG = Report_Delete.class.getSimpleName();

    LinearLayout overall;
    EditText Reason;
    ImageView Close;
    Button submit;
    Report_localExpenseClass info;
    String idLocalExpense, iduser;
    String desccription;
    ArrayList<Report_localExpenseClass> reportlist = new ArrayList<>();
    ReportLocalAdapter Radapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report__delete);

//        overall = (LinearLayout) findViewById(R.id.overall_layout);
        Reason = (EditText) findViewById(R.id.reason);
        Close = (ImageView) findViewById(R.id.img_pop_close);
        submit = (Button) findViewById(R.id.btn_SUBMIT);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        info = (Report_localExpenseClass) getIntent().getSerializableExtra("idlocal");
        Log.d("idlocalexp", String.valueOf(info));
        idLocalExpense = info.getIdLocalExpense();
        Log.d("idlocalexp", idLocalExpense);

        iduser = SharedPreference_class.getUserid(Report_Delete.this);
        Log.d("iduser", iduser);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desccription = Reason.getText().toString();

                if (desccription.isEmpty()) {
                    alert("Please Enter Reason");
                } else {
                    Delete_report();
                }


            }
        });

        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void Delete_report() {
        {
            final ProgressDialog mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setTitle(" Loading");
            mProgressDialog.setMessage("please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

            VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);

            JsonObject object = new JsonObject();
            object.addProperty("DeclineId", idLocalExpense);
            Log.d("decline", idLocalExpense);
            object.addProperty("cmd", "Local");
            object.addProperty("processType", "Cancel");
            object.addProperty("processby", iduser);
            object.addProperty("Reason", desccription);
            Log.d("iduser", iduser);

            Call<JsonArray> call = apiService.Delete_localExpense(object);
            call.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                    try {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();

                        Log.d("customer:code-res", response.code() + " - " + response.toString());
//                            if (response.code() == 200 || response.code() == 201) {
//                                Log.d("Response", response.body().toString());
//                            Log.d("URL", String.valueOf(response.code()));
                        JSONArray js = new JSONArray(response.body().toString());
                        if (js.length() > 0) {

                            for (int i = 0; i < js.length(); i++) {
                                JSONObject object1 = js.getJSONObject(i);

                                String Msg = object1.getString("resultMessage");
                                String result = object1.getString("result");

                                if (result.equals("1")) {
                                    Result_alert(Msg, result);

                                } else {
                                    Result_alert(Msg, result);
                                }
                            }

                        } else {
                            alert("No records has found");
                        }
//                            Toast.makeText(Report_Delete.this, "No Records has found", Toast.LENGTH_SHORT).show();
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

                    Log.e(TAG, t.toString());
                }
            });


        }
    }

    private void Result_alert(String Msg, String result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Report_Delete.this);

        builder.setTitle(Msg);
//        builder.setMessage(Msg);
        builder.setCancelable(false);
        if (Integer.parseInt(result) == 1) {
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent report=new Intent(Report_Delete.this,Report.class);
                    startActivity(report);
                    finish();
                }
            });

            builder.show();
        } else {
            builder.setTitle(Msg);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        }
    }

    private void alert(String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Report_Delete.this);
        builder.setTitle(reason);
//        builder.setMessage(reason);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                finish();
            }
        });
        builder.create().show();

    }
}
