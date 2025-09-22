package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;

public class Senior_mgmt extends AppCompatActivity {

    EditText Seniorname, SeniorMobno, Seniordesig;
    Button Seniorsubmit;
    private ProgressDialog pDialog;
    RequestQueue requestQueue;
    SharedPreferences shpRemember;
    SharedPreferences.Editor ed;
    private static final String SH_USERS = "userInfo";
    private static final String SH_LOGINTYPE = "slogintype";
    private static final String SH_USERID = "UserId";
    //    String serverPath = "http://192.168.0.77:8096/SchoolDemoAppService/Service.asmx/";
    String serverPath = "http://220.226.2.177:9000/StaffVoiceAnnouncerCloudSchool/Service.asmx/";
    String userId, schoolid, mobno, message, designation, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senior_mgmt);
        Seniorname = (EditText) findViewById(R.id.seniorname);
        SeniorMobno = (EditText) findViewById(R.id.seniormobno);
        Seniordesig = (EditText) findViewById(R.id.seniordesig);
        Seniorsubmit = (Button) findViewById(R.id.seniorbtn);

        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
        userId = shpRemember.getString(SH_USERID, null);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            schoolid = extras.getString("VALUE");

        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        Seniorsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = Seniorname.getText().toString();
                String Mobno = SeniorMobno.getText().toString();
                String Designation = Seniordesig.getText().toString();

                if ((Name.isEmpty()) || (Mobno.isEmpty())) {
                    if (Name.isEmpty()) {
                        Seniorname.setError("Enter the Name");
                    } else if (Mobno.isEmpty()) {
                        SeniorMobno.setError("Enter the Mobile Number");
                    }

                } else {
                    name = Seniorname.getText().toString().trim();
                    mobno = SeniorMobno.getText().toString().trim();
                    designation = Seniordesig.getText().toString().trim();
                    seniormgnt();
                }

            }
        });
    }

    public void seniormgnt() {
        String strServerUrl = serverPath + "DemoAddSeniorManagement?LoginID=" + userId + "&&SchoolID=" + schoolid + "&&Name=" + name + "&&Mobile=" + mobno + "&&Designation=" + designation;
        Log.d("URL", strServerUrl);
        Log.d("Testing", "LoginID=" + userId + "SchoolID=" + schoolid + "Name=" + name + "Mobile=" + mobno + "Designation=" + designation);
        if (isNetworkConnected()) {
            requestQueue = Volley.newRequestQueue(this);
            StringRequest obreq = new StringRequest(Request.Method.GET, strServerUrl, new Response.Listener<String>() {
                @Override

                public void onResponse(String response) {
                    try {
//                            hidePDialog();
                        pDialog.dismiss();
                        JSONObject obj = new JSONObject(response);
                        Log.d("Test Log", obj.toString());


                        JSONArray arrrr = obj.getJSONArray("AddSeniorManagement");
                        JSONObject obj1 = arrrr.getJSONObject(0);
//                        mobno = obj1.getString("Mobile");
//                        designation = obj1.getString("Designation");
//                        name = obj1.getString("Name");
                        message = obj1.getString("Message");
//                        schoolid=obj1.getString("SchoolID");
//                        Log.d("logintype", Logintype);

                        if (Integer.parseInt(obj1.getString("Status")) == 1) {
//                            if (Logintype.equals("admin"))
                            {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Senior_mgmt.this);

                                alertDialog.setTitle("Alert");

                                alertDialog.setMessage(message + " Press OK to exit.");

                                alertDialog.setIcon(android.R.drawable.ic_dialog_info);

                                alertDialog.setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
//                                                finish();
//                                                Toast.makeText(SchoolSubList.this, "" + "POC Approved", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(Senior_mgmt.this, MyschoolList.class);
//                                                i.putExtra("VALUEMOBNO",mobno );
                                                startActivity(i);
                                            }
                                        });

                                alertDialog.show();
                            }
                        } else {
                            String message="Successfully Logged";
                            alert(message);
//                            Toast.makeText(Senior_mgmt.this, "Successfully Logged", Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e) {
                        hidePDialog();
                        Seniorsubmit.setEnabled(true);
                        Alert("Exception");

//                        Toast.makeText(Senior_mgmt.this, "Exception", Toast.LENGTH_SHORT).show();
                        Log.d("Exception", "Senior Management-Message:" + e.getMessage());
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hidePDialog();
                            Seniorsubmit.setEnabled(true);
                            Alert("Network Problem - Failure Try again");
//                            Toast.makeText(Senior_mgmt.this, "Network Problem - Failure Try again", Toast.LENGTH_SHORT).show();
                            Log.d("OnErrorResponse", "Senior Management-Message:" + error.getMessage());
                        }
                    });

            requestQueue.add(obreq);

            pDialog = new ProgressDialog(Senior_mgmt.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        } else {
            Seniorsubmit.setEnabled(true);
            Alert("Check Your Internet connection");

//            Toast.makeText(this, "Check Your Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
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


    private void Seniormgtretrofit() {

        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
//        LoginID=" + userId + "&&SchoolID=" + schoolid + "&&Name=" + name + "&&Mobile=" + mobno + "&&Designation=" + designation;
        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoginID", userId);
        jsonObject.addProperty("SchoolID", schoolid);
        jsonObject.addProperty("Name", name);
        jsonObject.addProperty("Mobile", mobno);
        jsonObject.addProperty("Designation", designation);

        Log.d("login:req", jsonObject.toString());
        Call<JsonArray> call = apiService.Seniormanagement(jsonObject);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                try {
                    Log.d("Response", response.toString());
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    {
                        Log.d("login:code-res", response.code() + " - " + response.toString());
                        if (response.code() == 200 || response.code() == 201) {
                            Log.d("Response", response.body().toString());

                            JSONArray js = new JSONArray(response.body().toString());
                            if (js.length() > 0) {
                                JSONObject jsonObject = js.getJSONObject(0);
                                message = jsonObject.getString("Message");
                                if (Integer.parseInt(jsonObject.getString("Status")) == 1) {
                                    {
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Senior_mgmt.this);

                                        alertDialog.setTitle("Alert");

                                        alertDialog.setMessage(message + " Press OK to exit.");

                                        alertDialog.setIcon(android.R.drawable.ic_dialog_info);

                                        alertDialog.setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
//                                                finish();
//                                                Toast.makeText(SchoolSubList.this, "" + "POC Approved", Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(Senior_mgmt.this, MyschoolList.class);
//                                                i.putExtra("VALUEMOBNO",mobno );
                                                        startActivity(i);
                                                    }
                                                });

                                        alertDialog.show();
                                    }
                                } else {
                                    String message1="Successfully Logged";
                                    alert(message1);
//                                    Toast.makeText(Senior_mgmt.this, "Successfully Logged", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
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

//    private void showToast(String msg) {
//        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//    }

    private void alert(String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Senior_mgmt.this);
        builder.setTitle(reason);
//        builder.setMessage(reason);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();


    }
    private void Alert(String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Senior_mgmt.this);
        builder.setTitle(reason);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();


    }

}
