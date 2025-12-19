package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;

public class Credits extends AppCompatActivity {

    RadioGroup radioSV;
    RadioButton Radiosms, Radiovoice;
    EditText amountcredit;
    Button creditbtn;
    private ProgressDialog pDialog;
    RequestQueue requestQueue;
//    SharedPreferences shpRemember;
//    SharedPreferences.Editor ed;
//    private static final String SH_USERS = "userInfo";
//    private static final String SH_LOGINTYPE = "slogintype";
//    private static final String SH_USERID = "UserId";
    //    String serverPath = "http://192.168.0.77:8096/SchoolDemoAppService/Service.asmx/";
    String serverPath = "http://220.226.2.177:9000/StaffVoiceAnnouncerCloudSchool/Service.asmx/";
    String userId, schoolid, Creditamt, message, creditType;
    int selectedRbIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        amountcredit = (EditText) findViewById(R.id.Creditamount);
        creditbtn = (Button) findViewById(R.id.creditbtn);
        Radiosms = (RadioButton) findViewById(R.id.radiosms);
        Radiovoice = (RadioButton) findViewById(R.id.radiovoice);
        radioSV = (RadioGroup) findViewById(R.id.radioGroupcredits);

        userId= SharedPreference_class.getShSchlLoginid(Credits.this);

//        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
//        userId = shpRemember.getString(SH_USERID, null);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            schoolid = extras.getString("VALUE");

        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        creditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Creditamt = (amountcredit.getText().toString());
                if (Creditamt.isEmpty()) {
                    amountcredit.setError("Enter Your Amount");
                } else {

                    Creditamt = amountcredit.getText().toString().trim();
//                    {
//
//                        if (selectedRbIndex == 1) {
//                            creditType = "S";
//                        } else if (selectedRbIndex == 2) {
//                            creditType = "V";
//
//                        }
//                    }

                    addcredits();

                }


            }
        });
    }

    int getCatoryId(int radioId) {
        selectedRbIndex = radioSV.indexOfChild(findViewById(radioId));
        return selectedRbIndex;
    }


    public void addcredits() {

        String strAmount = amountcredit.getText().toString();
        int intselecteditem = getCatoryId(radioSV.getCheckedRadioButtonId()) + 1;

        if (intselecteditem == 1) {
            creditType = "S";
        } else {
            creditType = "V";
        }

        String strServerUrl = serverPath + "DemoAddCallORSMSCredits?LoginID=" + userId + "&&SchoolID=" + schoolid + "&&CreditType=" + creditType + "&&credits=" + strAmount;
        Log.d("URL", strServerUrl);
        Log.d("Testing", "LoginID=" + userId + "&&SchoolID=" + schoolid + "&&CreditType=" + intselecteditem + "&&credits=" + strAmount);
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


                        JSONArray arrrr = obj.getJSONArray("AddCallorSMSCredits");
                        JSONObject obj1 = arrrr.getJSONObject(0);
                        message = obj1.getString("Message");
                        if (Integer.parseInt(obj1.getString("Status")) == 1) {
                            {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Credits.this);

                                alertDialog.setTitle("Alert");

                                alertDialog.setMessage(message + " Press OK to exit.");

                                alertDialog.setIcon(android.R.drawable.ic_dialog_info);

                                alertDialog.setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        });

                                alertDialog.show();
                            }
                        } else {
                            String titte = "Alert";
                            alert(titte,message);


//                            Toast.makeText(Credits.this,message, Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e) {
                        hidePDialog();
                        creditbtn.setEnabled(true);
                        Alert("Exception");
//                        Toast.makeText(Credits.this, "Exception", Toast.LENGTH_SHORT).show();
                        Log.d("Exception", "Senior Management-Message:" + e.getMessage());
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hidePDialog();
                            creditbtn.setEnabled(true);
                            Alert("Network Problem - Failure");
//                            Toast.makeText(Credits.this, "Network Problem - Failure", Toast.LENGTH_SHORT).show();
                            Log.d("OnErrorResponse", "Senior Management-Message:" + error.getMessage());
                        }
                    });

            requestQueue.add(obreq);

            pDialog = new ProgressDialog(Credits.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        } else {
            creditbtn.setEnabled(true);
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


    private void Creditsretrofit() {
        String strAmount = amountcredit.getText().toString();
        int intselecteditem = getCatoryId(radioSV.getCheckedRadioButtonId()) + 1;

        if (intselecteditem == 1) {
            creditType = "S";
        } else {
            creditType = "V";
        }
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoginID", userId);
        jsonObject.addProperty("SchoolID", schoolid);
        jsonObject.addProperty("CreditType", creditType);
        jsonObject.addProperty("credits", strAmount);

        Log.d("Credits:req", jsonObject.toString());
        Call<JsonArray> call = apiService.Credits(jsonObject);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                try {

//                    if (mProgressDialog.isShowing())
//                        mProgressDialog.dismiss();
//                    {
                    Log.d("CreditsResponse", response.toString());
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    {
                        Log.d("Credits:code-res", response.code() + " - " + response.toString());
                        if (response.code() == 200 || response.code() == 201) {
                            Log.d("Response", response.body().toString());

                            JSONArray js = new JSONArray(response.body().toString());
                            if (js.length() > 0) {
                                JSONObject jsonObject = js.getJSONObject(0);
                                message = jsonObject.getString("Message");
                                if (Integer.parseInt(jsonObject.getString("Status")) == 1) {
                                    {
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Credits.this);
                                        alertDialog.setTitle("Alert");
                                        alertDialog.setMessage(message + " Press OK to exit.");
                                        alertDialog.setIcon(android.R.drawable.ic_dialog_info);
                                        alertDialog.setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        finish();
                                                    }
                                                });
                                        alertDialog.show();
                                    }
                                } else {
                                    String titte = "Alert";
                                    alert(titte,message);
//                                    Toast.makeText(Credits.this, message, Toast.LENGTH_SHORT).show();
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

    private void alert(String title,String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Credits.this);
        builder.setTitle(title);
            builder.setMessage(reason);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();


    }
    private void Alert(String msg ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Credits.this);
        builder.setTitle(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();


    }

}