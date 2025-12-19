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

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters.SchoolDocumentsAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SchoolDocumentsClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;

public class SchoolDocumentsActivity extends AppCompatActivity {

    RecyclerView rcyDocuments;
    SchoolDocumentsAdapter schoolDocumentsAdapter;
    ArrayList<SchoolDocumentsClass> doclist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_documents);
        rcyDocuments = findViewById(R.id.rcyDocuments);
        documentsApi();
        schoolDocumentsAdapter = new SchoolDocumentsAdapter(this, doclist);

        LinearLayoutManager circularLayoutManager = new LinearLayoutManager(SchoolDocumentsActivity.this);
        rcyDocuments.setLayoutManager(circularLayoutManager);
        rcyDocuments.setItemAnimator(new DefaultItemAnimator());
        rcyDocuments.setAdapter(schoolDocumentsAdapter);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);
    }

    private void documentsApi() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        String userid = SharedPreference_class.getUserid(this);
        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);
        Log.d("userid", userid.toString());

        Call<JsonArray> call = apiService.GetSchoolDocuments(userid);

        call.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                try {
                    Log.d("DocumentsList:Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();
                    doclist.clear();
                    JSONArray jsonArrayorgList = new JSONArray(strResponse);

                    if (jsonArrayorgList.length() > 0) {
                        for (int i = 0; i < jsonArrayorgList.length(); i++) {
                            JSONObject temp;
                            temp = jsonArrayorgList.getJSONObject(i);
                            String id = temp.getString("id");
                            String DocumentName = temp.getString("DocumentName");
                            String DocumentDescription = temp.getString("DocumentDescription");
                            String DocumentURL = temp.getString("DocumentURL");
                            String DocumentType = temp.getString("DocumentType");
                            SchoolDocumentsClass schoolDocumentsClass = new SchoolDocumentsClass(id, DocumentName, DocumentDescription, DocumentURL, DocumentType);
                            doclist.add(schoolDocumentsClass);
                            schoolDocumentsAdapter.notifyDataSetChanged();
                        }

                    } else {
                        alertDialog("No Records Found");
                    }

                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                alertDialog(t.getMessage());
            }
        });
    }


    private void alertDialog(String msg) {
        final AlertDialog.Builder dlg = new AlertDialog.Builder(SchoolDocumentsActivity.this);
        dlg.setTitle("Alert");
        dlg.setMessage(msg);
        dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dlg.setCancelable(false);
        dlg.create();
        dlg.show();
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