package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter.parentnoadapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.parentnoclass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;

public class EditCreateDemo extends AppCompatActivity {

    EditText etschoolname, etdemoprincipalnum, etemailId, etdemoparentnuum;
    Button btnAdd, demosubmitbtn, democlearbtn;
    String editvalue, editschoolname, editprincipalno, editparentno, editemail,DemoID;

    RequestQueue requestQueue;
    int Result;
    String Reason;
    String serverPath = "http://220.226.2.177:9000/StaffVoiceAnnouncerCloudSchool/Service.asmx/";
    //    String serverPath ="http://192.168.0.68:8096/SchoolDemoAppService/Service.asmx/";
//String serverPath = "http://192.168.0.77:8096/SchoolDemoAppService/Service.asmx/";
    private ProgressDialog pDialog;
    private List<EditText> editTextList = new ArrayList<>();

    private ArrayList<parentnoclass> datasList1 = new ArrayList<>();
    LinearLayout linearLayoutParent;// = new LinearLayout(this);
    String SchoolName;
    String PrincipalNumber;
    RecyclerView rvphonelist;
    String PrincipalEmailId, ParentNumber;
    LinearLayout listview;
    parentnoadapter parentadap;
    parentnoclass parentclass;
    List<String> mobNumbersArray = new ArrayList<>();
    String strDemoGroupId, demoId;



    String userId;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_create_demo);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

//        linearLayoutParent = (LinearLayout) findViewById(R.id.llParent);
//        linearLayoutParent.addView(editText(String.valueOf(count++)));

        etschoolname = (EditText) findViewById(R.id.demoschoolname);
        etdemoprincipalnum = (EditText) findViewById(R.id.demoprincipalno);
        etemailId = (EditText) findViewById(R.id.demoprincipalemail);
//        listview=(LinearLayout) findViewById(R.id.list);
        rvphonelist = (RecyclerView) findViewById(R.id.rvlist);
        rvphonelist.setNestedScrollingEnabled(false);

        parentadap = new parentnoadapter(datasList1, EditCreateDemo.this);

        userId= SharedPreference_class.getShSchlLoginid(EditCreateDemo.this);
//
//        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
//        userId = shpRemember.getString(SH_USERID, null);

//        Log.d("Test",demoId.toString());

        LinearLayoutManager LayoutManager = new LinearLayoutManager(EditCreateDemo.this);
        rvphonelist.setLayoutManager(LayoutManager);
        rvphonelist.setItemAnimator(new DefaultItemAnimator());
        rvphonelist.setAdapter(parentadap);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            DemoID = extras.getString("VALUE");

//            SchoolName = extras.getString("VALUE1");
//            PrincipalNumber = extras.getString("VALUE2");
//            ParentNumber = extras.getString("VALUE3");
//            PrincipalEmailId = extras.getString("VALUE4");

        }



//        Log.d("ParentNumber", ParentNumber);
//        String temp[] = ParentNumber.split(",");
//        for (int i = 0; i < temp.length; i++) {
//            parentnoclass temppno = new parentnoclass();
//            temppno.setStrDemoPhonenumber(temp[i]);
//            datasList1.add(temppno);
//        }
        //  parentadap.notifyDataSetChanged();


        EditDataList();



//        etschoolname.setText(SchoolName);
//        etdemoprincipalnum.setText(PrincipalNumber);
//        etemailId.setText(PrincipalEmailId);


        demosubmitbtn = (Button) findViewById(R.id.demosubmitedit);
        btnAdd = (Button) findViewById(R.id.demoadd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(EditCreateDemo.this);

                dlgAlert.setTitle("New Parent Number");

                dlgAlert.setMessage("Enter Parent Number");
                final EditText input = new EditText(EditCreateDemo.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
               int maxLength = 10;
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                dlgAlert.setView(input);


                dlgAlert.setPositiveButton("Submit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String activityNum, newPhoneNumber;
                                newPhoneNumber = input.getText().toString();
                                if (!newPhoneNumber.isEmpty()) {
                                    parentnoclass temppno = new parentnoclass();
                                    temppno.setStrDemoPhonenumber(newPhoneNumber);
                                    datasList1.add(temppno);
                                    parentadap.notifyDataSetChanged();
                                    dialog.cancel();
                                } else {
                                    Alert("Parent Number cannot be empty ");
//                                    Toast.makeText(EditCreateDemo.this, "Parent Number cannot be empty ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                dlgAlert.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });


                dlgAlert.setIcon(android.R.drawable.ic_dialog_info);
                dlgAlert.setCancelable(false);
                dlgAlert.create().show();


            }
        });
        demosubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editschoolname = (etschoolname.getText().toString());
                editprincipalno = (etdemoprincipalnum.getText().toString());
                editemail = (etemailId.getText().toString());

                if (editschoolname.isEmpty() || editprincipalno.isEmpty() || editprincipalno.length() != 10 || datasList1.isEmpty()) {

                    if (editschoolname.isEmpty()) {
                        etschoolname.setError("Enter Your School Name");
                    }

                    if (editprincipalno.isEmpty()) {
                        etdemoprincipalnum.setError("Enter Your Principal number");
                    }


                    if (editprincipalno.length() != 10) {
                        etdemoprincipalnum.setError("Enter Valid Principal number");
                    }


                    if (datasList1.isEmpty()) {
                        Alert("Add Atleast One Parent Number");
//                        Toast.makeText(EditCreateDemo.this, "Add Atleast One Parent Number", Toast.LENGTH_SHORT).show();
                    }

                } else {
//                    Editdemoretrofit();
//                    if (!isNetworkConnected()) {
                        Editdemoretrofit();
//                    } else {
//                        showToast("Check Your Internet Connection");
//                    }
                }
            }
        });


    }

    private void EditDataList() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();



        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        Call<JsonArray> call = apiService.GetDemoDetailsByDemoId(DemoID);

        call.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                try {
                    Log.d("Listdemo;Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();

                    JSONArray jsonArrayorgList = new JSONArray(strResponse);
                    if (jsonArrayorgList.length() > 0) {
                        JSONObject jsonObjectorgInfo;
                        datasList1.clear();
                        for (int i = 0; i < jsonArrayorgList.length(); i++) {
                            jsonObjectorgInfo = jsonArrayorgList.getJSONObject(i);

                            etschoolname.setText(jsonObjectorgInfo.getString("SchoolName"));
                            etdemoprincipalnum.setText(jsonObjectorgInfo.getString("PrincipalNumber"));
                            etemailId.setText(jsonObjectorgInfo.getString("PrincipalEmail"));


                            String phoneNumbers=jsonObjectorgInfo.getString("ParentNos");
                            Log.d("ParentNumber", phoneNumbers);
                            String temp[] = phoneNumbers.split(",");
                            for (int j = 0; j < temp.length; j++) {
                                parentnoclass temppno = new parentnoclass();
                                temppno.setStrDemoPhonenumber(temp[j]);
                                datasList1.add(temppno);
                            }


                            }
                        parentadap.notifyDataSetChanged();

                    } else {
                        Alert("No data Received. Try Again.");
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
//                showToast("Server Connection Failed");
            }
        });
    }

    private void clearTextDemo() {
        etschoolname.setText("");
        etdemoprincipalnum.setText("");
        etschoolname.requestFocus();
    }

//    private void showToast(String msg) {
//        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//    }

    private EditText editText(String hint) {
        EditText editText = new EditText(this);
        editText.setId(Integer.valueOf(hint));
//        editText.setHint("Mobile Number");
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        editTextList.add(editText);
        return editText;
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


    private void Editdemoretrofit() {
        editvalue = "";
        for (int j = 0; j < datasList1.size(); j++) {
            editvalue = editvalue + datasList1.get(j).getStrDemoPhonenumber().trim() + ",";
        }
        Log.d("test", editvalue);
        editvalue = editvalue.substring(0, editvalue.length() - 1);
        SchoolName = etschoolname.getText().toString();

        PrincipalNumber = etdemoprincipalnum.getText().toString();

        PrincipalEmailId = etemailId.getText().toString();
        etemailId.setText(PrincipalEmailId);

        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoginID", userId);
        jsonObject.addProperty("SchoolName", SchoolName);
        jsonObject.addProperty("MobileNo", PrincipalNumber);
        jsonObject.addProperty("Email", PrincipalEmailId);
        jsonObject.addProperty("ParentNos", editvalue);
        jsonObject.addProperty("RequestType","2");
        jsonObject.addProperty("Demoid",DemoID);
        Log.d("createdemo:req", jsonObject.toString());
        Call<JsonArray> call = apiService.CreateDemo(jsonObject);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                try {
                    Log.d("Response", response.toString());
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    {
                        Log.d("createdemo:code-res", response.code() + " - " + response.toString());
                        if (response.code() == 200 || response.code() == 201) {
                            Log.d("Response", response.body().toString());

                            JSONArray js = new JSONArray(response.body().toString());
                            if (js.length() > 0) {
                                JSONObject jsonObject = js.getJSONObject(0);
//                                strDemoGroupId = jsonObject.getString("GroupID");
                                if (Integer.parseInt(jsonObject.getString("Status")) == 1) {
                                    LayoutInflater layoutInflater = LayoutInflater.from(EditCreateDemo.this);
                                    View promptView = layoutInflater.inflate(R.layout.dailog_ok, null);
                                    final AlertDialog dlgAlert = new AlertDialog.Builder(EditCreateDemo.this).create();
                                    dlgAlert.setTitle("Alert");
                                    dlgAlert.setMessage(jsonObject.getString("Message"));
                                    dlgAlert.setIcon(android.R.drawable.ic_dialog_info);
                                    dlgAlert.setCancelable(false);
                                    final Button Okbtn = (Button) promptView.findViewById(R.id.okbtndemo);
                                    Okbtn.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            dlgAlert.dismiss();
                                            finish();
                                        }
                                    });
                                    dlgAlert.setView(promptView);
                                    dlgAlert.show();
                                } else {
                                    datasList1.clear();
                                    LayoutInflater layoutInflater = LayoutInflater.from(EditCreateDemo.this);
                                    View promptView = layoutInflater.inflate(R.layout.dailog_ok, null);
                                    final AlertDialog dlgAlert = new AlertDialog.Builder(EditCreateDemo.this).create();
                                    dlgAlert.setTitle("Alert");
                                    dlgAlert.setMessage(jsonObject.getString("Message"));
                                    dlgAlert.setIcon(android.R.drawable.ic_dialog_info);
                                    dlgAlert.setCancelable(false);
                                    final Button Okbtn = (Button) promptView.findViewById(R.id.okbtndemo);
                                    Okbtn.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            dlgAlert.dismiss();
                                            finish();
                                        }
                                    });
                                    dlgAlert.setView(promptView);
                                    dlgAlert.show();
                                }
                            }
                        } else {
                            Alert("No Data received..");
//                            showToast(Reason);
                        }
                    }
                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                mProgressDialog.dismiss();
                Alert("Server Connection Failed");
//                showToast("Server Connection Failed");
            }
        });
    }

    private void Alert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditCreateDemo.this);
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