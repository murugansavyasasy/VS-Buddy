package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import com.android.volley.AuthFailureError;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.MultiSelectionSpinner;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.MultiSelectionSpinner1;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;

public class Message_student extends AppCompatActivity// implements MultiSelectionSpinner.OnMultipleItemsSelectedListener, MultiSelectionSpinner1.OnMultipleItemsSelectedListener {
{
    LinearLayout linearMain;
    CheckBox checkBox;
    Button sendmsg;
    RadioButton Radiostd, Radiogrp;
    LinearLayout linearLayoutstd, linearLayoutgrp, linearLayoutcheck, linearstdgrp;
    private ProgressDialog pDialog;
    RequestQueue requestQueue;

    private ArrayList<String> dataListstd = new ArrayList<>();
    //    String serverPath = "http://192.168.0.68:8096/SchoolDemoAppService/Service.asmx/";
    String serverPath = "http://220.226.2.177:9000/StaffVoiceAnnouncerCloudSchool/Service.asmx/";
    //    String serverPath = "http://192.168.0.77:8096/SchoolDemoAppService/Service.asmx/";
    String schoolid, FieldId, status, Reason;

    RadioGroup radiotype;
    SharedPreferences shpRemember;
    SharedPreferences.Editor ed;
    EditText etmessage;
    TextView textview;
    String Imeno = "9789", Phoneno = "9840547017", Staffid, count, Textmsg, password, schoolname;

    //    private static final String SH_USERS = "userInfo";
//    private static final String SH_USERID = "UserId";
//    private static final String SH_PASSWORD = "sPassword";
    String userId;

    String[] itemArray, itemId, itemArraygrp, itemIdgrp;
    MultiSelectionSpinner multiSelectionSpinnerstd, multiSelectionSpinnergrp;
    MultiSelectionSpinner1 multiSelectionSpinnergrp1;

    String[] itemArraystd = {"LKG", "UKG", "1-5", "6-10", "11-12"};
    String[] itemIdstd = {"1", "2", "3", "4", "5"};

    String[] itemArraygrp1 = {"grp1", "grp2", "grp3", "grp4", "grp5"};
    String[] itemIdgrp1 = {"1", "2", "3", "4", "5"};
    String selectedstdID, selectedgrpID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_student);
//        btnSTD=(Button)findViewById(R.id.btnstd);
        Radiostd = (RadioButton) findViewById(R.id.radiostd);
        Radiogrp = (RadioButton) findViewById(R.id.radiogrp);
        checkBox = (CheckBox) findViewById(R.id.checkBoxcheck1);
//        radiotype = (RadioGroup) findViewById(R.id.radioGrouptype1);
        textview = (TextView) findViewById(R.id.messageschool1);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        etmessage = (EditText) findViewById(R.id.edittextmsg);
        sendmsg = (Button) findViewById(R.id.btnsend);
//
//        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
//        userId = shpRemember.getString(SH_USERID, null);
//        password = shpRemember.getString(SH_PASSWORD, null);

        userId = SharedPreference_class.getShSchlLoginid(Message_student.this);
        password = SharedPreference_class.getShPassword(Message_student.this);


        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Textmsg = etmessage.getText().toString().trim();
                if (Textmsg.isEmpty()) {
                    Alert("Please enter the Message to be sent");
//                    Toast.makeText(getApplicationContext(), "Please enter the Message to be sent", Toast.LENGTH_SHORT).show();
                } else {
                    dialogalert();
                }
            }
        });

        linearLayoutstd = (LinearLayout) findViewById(R.id.linearstd1);
        linearLayoutgrp = (LinearLayout) findViewById(R.id.lineargrp1);
        linearLayoutcheck = (LinearLayout) findViewById(R.id.linear21);
        linearstdgrp = (LinearLayout) findViewById(R.id.llstdgrp);
        multiSelectionSpinnerstd = (MultiSelectionSpinner) findViewById(R.id.mySpinnerstd);
        multiSelectionSpinnergrp1 = (MultiSelectionSpinner1) findViewById(R.id.mySpinnergrp);
//        multiSelectionSpinnerstd = (MultiSelectionSpinner) findViewById(R.id.mySpinnerstd);
//        multiSelectionSpinnerstd.setItems(itemArraystd, itemIdstd);
////        multiSelectionSpinnerstd.setListener(Message_student.this);
//        multiSelectionSpinnerstd.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
//            @Override
//            public void selectedIndices(List<Integer> indices) {
//                for (int i = 0; i < indices.size(); i++) {
//                    String selectedstdID = itemIdstd[indices.get(i)];
//                    Log.d("selectedstdID",selectedstdID);
//                }
//            }
//
//            @Override
//            public void selectedStrings(List<String> strings) {
//
//            }
//        });


//        multiSelectionSpinnergrp1 = (MultiSelectionSpinner1) findViewById(R.id.mySpinnergrp);
//        multiSelectionSpinnergrp1.setItems(itemArraygrp1, itemIdgrp1);
////        multiSelectionSpinnergrp.setListener(Message_student.this);
//        multiSelectionSpinnergrp1.setListener(new MultiSelectionSpinner1.OnMultipleItemsSelectedListener() {
//            @Override
//            public void selectedIndices(List<Integer> indices)
//            {
//                for (int i = 0; i < indices.size(); i++) {
//                    String selectedgrpID = itemIdgrp1[indices.get(i)];
//                    Log.d("selectedgrpID",selectedgrpID);
//                }
//            }
//
//            @Override
//            public void selectedStrings(List<String> strings) {
//
//            }
//        });
//

        userId = SharedPreference_class.getShSchlLoginid(Message_student.this);
//        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
//        userId = shpRemember.getString(SH_USERID, null);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            schoolid = extras.getString("VALUE");
            schoolname = extras.getString("SCHOOLNAME");
        }
        textview.setText(schoolname);
//        getstandard1();
//        multiSelectionSpinnerstd.setEnabled(false);
//        multiSelectionSpinnergrp1.setEnabled(false);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        multiSelectionSpinnerstd.setEnabled(false);
                                                        multiSelectionSpinnergrp1.setEnabled(false);
//              disableEnableControls(!isChecked, linearstdgrp);
//                 linearLayoutstd.setVisibility(View.GONE);
//                  linearLayoutgrp.setVisibility(View.GONE);
                                                    } else {
                                                        multiSelectionSpinnerstd.setEnabled(true);
                                                        multiSelectionSpinnergrp1.setEnabled(true);
//              linearLayoutcheck.setVisibility(View.VISIBLE);
//              linearLayoutstd.setVisibility(View.VISIBLE);
//              linearLayoutgrp.setVisibility(View.VISIBLE);
//              Radiostd.setChecked(true);
                                                    }
                                                }
                                            }
        );

        checkBox.setChecked(true);
        multiSelectionSpinnerstd.setEnabled(false);
        multiSelectionSpinnergrp1.setEnabled(false);

//        getstandard1();
//        getgroup1();


//        if (!isNetworkConnected()) {
        getstandard1();
        getgroup1();
//        } else {
//            showToast("Check Your Internet Connection");
//        }
//        Radiostd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    linearLayoutstd.setVisibility(View.VISIBLE);
//                    linearLayoutgrp.setVisibility(View.GONE);
//
//                }
//            }
//        });
//
//
//        Radiogrp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    linearLayoutstd.setVisibility(View.GONE);
//                    linearLayoutgrp.setVisibility(View.VISIBLE);
//
//
//                }
//
//            }
//        });

//        getgroup1();
    }

    int getTypeId(int radioId) {
        int selectedRbIndex = radiotype.indexOfChild(findViewById(radioId));
        return selectedRbIndex;
    }


    private void sendMessage() {

        String Targetcode = MultiSelectionSpinner.strSelectedValues;
//        if (Textmsg.isEmpty()) {
//            Toast.makeText(getApplicationContext(), "Please enter the Message to be sent", Toast.LENGTH_SHORT).show();
//        } else {
        if (checkBox.isChecked()) {

//            if (!isNetworkConnected()) {
            SendmessageforEntireSchool1();
//            } else {
//                showToast("Check Your Internet Connection");
//            }
//            SendmessageforEntireSchool1();
        } else {

//            if (!isNetworkConnected()) {
            sendsmstogroupsandstandard();
//            } else {
//                showToast("Check Your Internet Connection");
//            }
//            sendsmstogroupsandstandard();
//            if (!Targetcode.isEmpty()) {
//                if (Radiostd.isChecked()) {
//                    sendsmstogroupsandstandard();
//                } else if (Radiogrp.isChecked()) {
//                    sendsmstogroupsandstandard();
//                }
        }
//            else {
//                Toast.makeText(getApplicationContext(), "Please select atleast one value from the standard or group", Toast.LENGTH_SHORT).show();
//            }
//        }

//        }
    }

    private void disableEnableControls(boolean enable, ViewGroup vg) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup) {
                disableEnableControls(enable, (ViewGroup) child);
            }
        }
    }

    private void getstandard1() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("schoolID", schoolid);
        Log.d("standardres", jsonObject.toString());
        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        Call<JsonArray> call = apiService.Getstandards(jsonObject);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                try {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    Log.d("login:code-res", response.code() + " - " + response.toString());
                    if (response.code() == 200 || response.code() == 201) {
                        Log.d("Response", response.body().toString());

                        JSONArray jsstd = new JSONArray(response.body().toString());
                        itemArray = new String[jsstd.length()];
                        itemId = new String[jsstd.length()];

                        for (int i = 0; i < jsstd.length(); i++) {
                            JSONObject tempstd = jsstd.getJSONObject(i);
                            Log.d("Testing", tempstd.toString());
//                            itemArray[0] = "Select";
//                            itemId[0] ="0";
                            itemArray[i] = tempstd.getString("std_name");
                            itemId[i] = tempstd.getString("std_list_Id");
                        }


                        multiSelectionSpinnerstd.setItems(itemArray, itemId);
//                        multiSelectionSpinnerstd.setListener(Message_student.this);
                        MultiSelectionSpinner.strSelectedValues = itemId[0] + ",";
//                        dataListstd.add(MultiSelectionSpinner.strSelectedValues);

                        multiSelectionSpinnerstd.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
                            @Override
                            public void selectedIndices(List<Integer> indices) {
                                for (int i = 0; i < indices.size(); i++) {
                                    selectedstdID = itemId[indices.get(i)];
                                    Log.d("selectedgrpID", selectedstdID);
                                }
                            }

                            @Override
                            public void selectedStrings(List<String> strings) {

                            }
                        });
                    }
//                else {
//                    showToast("Server Response Failed");
//                }
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

    private void getgroup1() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("schoolID", schoolid);
        Log.d("standardres", jsonObject.toString());
        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        Call<JsonArray> call = apiService.Getgroups(jsonObject);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                try {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    Log.d("login:code-res", response.code() + " - " + response.toString());
                    if (response.code() == 200 || response.code() == 201) {
                        Log.d("Response", response.body().toString());

                        JSONArray jsgrp = new JSONArray(response.body().toString());
                        itemArraygrp = new String[jsgrp.length()];
                        itemIdgrp = new String[jsgrp.length()];

                        for (int i = 0; i < jsgrp.length(); i++) {
                            JSONObject tempgrp = jsgrp.getJSONObject(i);
                            Log.d("Testing", tempgrp.toString());

                            itemArraygrp[i] = tempgrp.getString("groupname");
                            itemIdgrp[i] = tempgrp.getString("groupcode");
                        }

//                        Log.d("Item Array", itemArraygrp.toString());
//                        Log.d("Item Id", itemIdgrp.toString());


                        multiSelectionSpinnergrp1.setItems(itemArraygrp, itemIdgrp);
//                        multiSelectionSpinnergrp1.setListener(Message_student.this);
                        MultiSelectionSpinner1.strSelectedValues = itemIdgrp[0] + ",";

                        multiSelectionSpinnergrp1.setListener(new MultiSelectionSpinner1.OnMultipleItemsSelectedListener() {
                            @Override
                            public void selectedIndices(List<Integer> indices) {
                                for (int i = 0; i < indices.size(); i++) {
                                    selectedgrpID = itemIdgrp[indices.get(i)];
                                    Log.d("selectedgrpID", selectedgrpID);
                                }
                            }

                            @Override
                            public void selectedStrings(List<String> strings) {

                            }
                        });


                    }
//                else {
//                    showToast("Server Response Failed");
//                }
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


    private void SendmessageforEntireSchool() {


        String serverUrl = serverPath + "sendTextCircularForEntireSchool";
        Log.d("URL", serverUrl);
        Log.d("Testing", "IMENumber=" + Imeno + "&SchoolID=" + Phoneno + "&StaffID=" + schoolid + "&Count=" + count + "&TextMessage=" + Textmsg);

        final ProgressDialog ValDialog = new ProgressDialog(Message_student.this);
        ValDialog.setMessage("Loading...");
        ValDialog.setCancelable(false);
        ValDialog.show();

        if (isNetworkConnected()) {
            requestQueue = Volley.newRequestQueue(this);

            StringRequest strRequest = new StringRequest(Request.Method.POST, serverUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            ValDialog.dismiss();
                            try {

                                JSONObject obj = new JSONObject(response);
                                Log.d("Test Log", obj.toString());
                                JSONArray jsonArray = obj.getJSONArray("ManagementTextCircular");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj1 = jsonArray.getJSONObject(i);
                                    FieldId = obj1.getString("FileID");
                                    status = obj1.getString("Status");
                                    Reason = obj1.getString("Reason");
                                    if (status.equals("Uploaded")) {

                                        String msg1 = "Message sent Successfully";
                                        alert1(msg1);
//                                        Toast.makeText(getApplicationContext(), "Message sent Successfully", Toast.LENGTH_SHORT).show();

                                    } else {
                                        String msg = "Message not sent";
                                        alert1(msg);

//                                        Log.d("Server Error", "Error");
//                                        Toast.makeText(getApplicationContext(), "Message not sent", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                                Log.d("Exception", e.getMessage());
                                Alert("Exception");
//                                Toast.makeText(getApplicationContext(), "Exception", Toast.LENGTH_SHORT).show();
//                        Log.d("Exception", getApplicationContext().getPackageCodePath() + "-getData()-Message:" + e.getMessage());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            ValDialog.dismiss();
                            Log.d("MyError", error.toString());
//                            Toast.makeText(getApplicationContext(), "Network Problem - Failure", Toast.LENGTH_SHORT).show();

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("IMENumber", Imeno);//?IMENumber="+Imeno+"&PhoneNumber="+Phoneno+"&SchoolID="+schoolid+"&StaffID="+userId+"&TextMessage="+Textmsg;
                    params.put("PhoneNumber", Phoneno);
                    params.put("SchoolID", schoolid);
                    params.put("StaffID", userId);
                    params.put("TextMessage", Textmsg);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    return params;
                }
            };

            requestQueue.add(strRequest);
        } else {
            Alert("Check Your Internet connection");
//            Toast.makeText(this, "Check Your Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void SendmessageforEntireSchool1() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("SchoolID", schoolid);
        jsonObject.addProperty("LoginID", userId);
        jsonObject.addProperty("Message", Textmsg);
        Log.d("Listdemo:req", jsonObject.toString());

        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        Call<JsonArray> call = apiService.Sendsmstoentireschool(jsonObject);

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

                    JSONArray jsonArrayList = new JSONArray(strResponse);
                    if (jsonArrayList.length() > 0) {
                        JSONObject obj1 = jsonArrayList.getJSONObject(0);
//                    FieldId = obj1.getString("FileID");
                        status = obj1.getString("Status");
                        Reason = obj1.getString("Message");
                        if (status.equals("y")) {
//                            finish();
                            String msg2 = "Message sent Successfully";
                            alert1(msg2);
//                            Toast.makeText(getApplicationContext(), "Message sent Successfully", Toast.LENGTH_SHORT).show();

                        } else {
                            String msg3 = "Message sent Successfully";
                            alert1(msg3);
//                        Log.d("Server Error", "Error");
//                            Toast.makeText(getApplicationContext(), "Message not sent", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Alert("No data Received. Try Again.");
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

    private void dialogalert() {
        LayoutInflater layoutInflater = LayoutInflater.from(Message_student.this);
        View promptView = layoutInflater.inflate(R.layout.dialog_send, null);

        final AlertDialog dlgAlert = new AlertDialog.Builder(Message_student.this).create();
        dlgAlert.setIcon(android.R.drawable.ic_dialog_info);
        dlgAlert.setCancelable(false);
//                                        dlgAlert.create().show();

        final Button snd_btn = (Button) promptView.findViewById(R.id.sndbtn);
        final Button exit_btn = (Button) promptView.findViewById(R.id.exitbtn);


        snd_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlgAlert.dismiss();
//                                        finish();
//                                        Toast.makeText(getApplicationContext(), "Message sent Successfully", Toast.LENGTH_SHORT).show();
                alert();
            }
        });


        exit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlgAlert.dismiss();


            }
        });


        dlgAlert.setView(promptView);

        dlgAlert.show();

    }

    private void alert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Message_student.this);

        alertDialog.setTitle("Password Alert");

        alertDialog.setMessage("Re-Enter Your Vims Password");
        final EditText input = new EditText(Message_student.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        alertDialog.setView(input);

        alertDialog.setIcon(android.R.drawable.ic_dialog_info);

        alertDialog.setPositiveButton("Submit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("test", input.getText().toString() + password);
                        if ((input.getText().toString()).equals(password)) {
//                            Toast.makeText(getApplicationContext(), "Message sent Successfully", Toast.LENGTH_SHORT).show();
                            confirmalert();
                        } else {
                            input.getText().clear();
                            Alert("Password Incorrect Enter the Password again");
//                            Toast.makeText(getApplicationContext(), "Password Incorrect Enter the Password again", Toast.LENGTH_SHORT).show();
                            alert();

                        }


                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }


    private void alert1(String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Message_student.this);

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

    private void confirmalert() {
        LayoutInflater layoutInflater = LayoutInflater.from(Message_student.this);
        View promptView = layoutInflater.inflate(R.layout.dialog_confirm, null);

        final AlertDialog dlgAlert = new AlertDialog.Builder(Message_student.this).create();

//       dlgAlert.setTitle("Alert");
//
//   dlgAlert.setMessage("We are going to send Message on behalf of School.Press yes to confirm");
//    dlgAlert.getWindow().setBackgroundDrawableResource(R.color.background);


        dlgAlert.setIcon(android.R.drawable.ic_dialog_info);
        dlgAlert.setCancelable(false);
//                                        dlgAlert.create().show();

        final Button snd_btn = (Button) promptView.findViewById(R.id.sndbtn);
        final Button exit_btn = (Button) promptView.findViewById(R.id.exitbtn);


        snd_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendMessage();
                dlgAlert.dismiss();


            }
        });


        exit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlgAlert.dismiss();


            }
        });


        dlgAlert.setView(promptView);

        dlgAlert.show();
    }


    private void SendmessageforEntireStandardAndStaff() {
        String text = MultiSelectionSpinner.strSelectedValues;
        String count[] = text.split(",");
        final String words = String.valueOf(count.length);

        String serverUrl = serverPath + "sendTextCircularForEntireStandardAndStaff";//?IMENumber="+Imeno+"&PhoneNumber="+Phoneno+"&SchoolID="+schoolid+"&StaffID="+userId+"&TargetCode="+MultiSelectionSpinner.strSelectedValues+"&Count="+words+"&TextMessage="+Textmsg;
        Log.d("URL", serverUrl);
        Log.d("Testing", "IMENumber=" + Imeno + "&PhoneNumber=" + Phoneno + "&StaffID=" + schoolid + "&TargetCode=" + MultiSelectionSpinner.strSelectedValues + "&Count=" + words + "&TextMessage=" + Textmsg);

        final ProgressDialog pInValDialog = new ProgressDialog(Message_student.this);
        pInValDialog.setMessage("Loading...");
        pInValDialog.setCancelable(false);
        pInValDialog.show();

        if (isNetworkConnected()) {
            StringRequest strRequest = new StringRequest(Request.Method.POST, serverUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pInValDialog.dismiss();
                            try {

                                JSONObject obj = new JSONObject(response);
                                Log.d("Test Log", obj.toString());
                                JSONArray jsonArray = obj.getJSONArray("ManagementTextCircular");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj1 = jsonArray.getJSONObject(i);
                                    FieldId = obj1.getString("FileID");
                                    status = obj1.getString("Status");
                                    Reason = obj1.getString("Reason");
                                    if (status.equals("Uploaded")) {
//                                        dialogalert();
//                                        finish();
                                        String message = "Message sent Successfully";
                                        alert1(message);
//                                        Toast.makeText(getApplicationContext(), "Message sent Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        String messages = "Message not sent";
                                        alert1(messages);
                                        Log.d("Server Error", "Error");
//                                        Toast.makeText(getApplicationContext(), "Message not sent", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                                Log.d("Exception", e.getMessage());
                                Alert("Exception");
//                                Toast.makeText(getApplicationContext(), "Exception", Toast.LENGTH_SHORT).show();
//                        Log.d("Exception", getApplicationContext().getPackageCodePath() + "-getData()-Message:" + e.getMessage());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pInValDialog.dismiss();
                            Log.d("MyError", error.toString());
//                            Toast.makeText(getApplicationContext(), "Network Problem - Failure", Toast.LENGTH_SHORT).show();

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("IMENumber", Imeno);//?IMENumber="+Imeno+"&PhoneNumber="+Phoneno+"&SchoolID="+schoolid+"&StaffID="+userId+"&TargetCode="+MultiSelectionSpinner.strSelectedValues+"&Count="+words+"&TextMessage="+Textmsg;
                    params.put("PhoneNumber", Phoneno);
                    params.put("SchoolID", schoolid);
                    params.put("StaffID", userId);
                    params.put("TargetCode", MultiSelectionSpinner.strSelectedValues);
                    params.put("Count", words);
                    params.put("TextMessage", Textmsg);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    return params;
                }
            };


            requestQueue.add(strRequest);
        } else {
            Alert("Check Your Internet connection");
//            Toast.makeText(this, "Check Your Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendsmstogroupsandstandard() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("SchoolID", schoolid);
//        jsonObject.addProperty("LoginID", userId);
//        jsonObject.addProperty("Message", Textmsg);
//        Log.d("Listdemo:req", jsonObject.toString());

        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);

        JsonObject jsonReqobject = constructJsonArrayMgtSchools();
        Call<JsonArray> call = apiService.sendsmstogroupsandstandards(jsonReqobject);
        Log.d("grp&stdarray", jsonReqobject.toString());
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

                    JSONArray jsonArrayList = new JSONArray(strResponse);
                    if (jsonArrayList.length() > 0) {
                        JSONObject obj1 = jsonArrayList.getJSONObject(0);
//                    FieldId = obj1.getString("FileID");
                        status = obj1.getString("Status");
                        Reason = obj1.getString("Message");
                        if (status.equals("y")) {
                            Log.d("status",status);
//                            String message1 = "Message sent Successfully";
                            alert1(Reason);
//                            finish();

//                            Toast.makeText(getApplicationContext(), "Message sent Successfully", Toast.LENGTH_SHORT).show();

                        } else {
                            Log.d("status",status);

//                            String message2 = "Message not sent";
                            alert1(Reason);
//                        Log.d("Server Error", "Error");
//                            Toast.makeText(getApplicationContext(), "Message not sent", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Alert("No data Received. Try Again.");
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


    private JsonObject constructJsonArrayMgtSchools() {
        JsonObject jsonObjectSchool = new JsonObject();
        try {
            jsonObjectSchool.addProperty("SchoolID", schoolid);
            jsonObjectSchool.addProperty("LoginId", userId);
            jsonObjectSchool.addProperty("Message", Textmsg);

            JsonArray jsonArrayStandards = new JsonArray();
            String str = MultiSelectionSpinner.strSelectedValues;
            Log.d("standardid", str);
//                String strArray[] = str.split(",");

            List<Integer> listInt = MultiSelectionSpinner.getSelectedIndices();
            Log.d("TT-Count", "" + listInt.size());
//                for (int i = 0; i < listInt.size(); i++) {
//                    Log.d("TT", i + " - " + itemId[listInt.get(i)]);
//                }
            for (int i = 0; i < listInt.size(); i++) {
                JsonObject jsonObjectStandards = new JsonObject();
                jsonObjectStandards.addProperty("TargetCode", itemId[listInt.get(i)]);//);
                Log.d("TT", i + " - " + itemId[listInt.get(i)]);
                jsonArrayStandards.add(jsonObjectStandards);
            }
            JsonArray jsonArrayGroups = new JsonArray();
            Log.d("TT-Countgroup", "" + listInt.size());
//                for (int i = 0; i < listInt.size(); i++) {
//                    Log.d("TTgroup", i + " - " + itemIdgrp[listInt.get(i)]);
//                }

            List<Integer> listInt1 = MultiSelectionSpinner1.getSelectedIndices();
            for (int j = 0; j < listInt1.size(); j++) {
                JsonObject jsonObjectGrops = new JsonObject();
                jsonObjectGrops.addProperty("TargetCode", itemIdgrp[listInt1.get(j)]);// ]);
                Log.d("TTgroup", j + " - " + itemIdgrp[listInt1.get(j)]);
                jsonArrayGroups.add(jsonObjectGrops);
            }
            jsonObjectSchool.add("Seccode", jsonArrayStandards);
            jsonObjectSchool.add("GrpCode", jsonArrayGroups);
            Log.d("Final_Array", jsonObjectSchool.toString());

        } catch (Exception e) {
            Log.d("ASDF", e.toString());
        }

        return jsonObjectSchool;
    }


    private void SendmessageforEntireGroup() {
        String textgrp = MultiSelectionSpinner.strSelectedValues;
        String countgrp[] = textgrp.split(",");
        final String targetcountgrp = String.valueOf(countgrp.length);

        String serverUrl = serverPath + "sendTextCircularForEntireGroup";//?IMENumber="+Imeno+"&PhoneNumber="+Phoneno+"&SchoolID="+schoolid+"&StaffID="+userId+"&TargetCode="+MultiSelectionSpinner.strSelectedValues+"&Count="+targetcountgrp+"&TextMessage="+Textmsg;
        Log.d("URL", serverUrl);
        Log.d("Testing", "IMENumber=" + Imeno + "&PhoneNumber=" + Phoneno + "&StaffID=" + userId + "&SchoolID=" + schoolid + "&TargetCode=" + MultiSelectionSpinner.strSelectedValues + "&Count=" + targetcountgrp + "&TextMessage=" + Textmsg);


        final ProgressDialog pInValDialog = new ProgressDialog(Message_student.this);
        pInValDialog.setMessage("Loading...");
        pInValDialog.setCancelable(false);
        pInValDialog.show();

        if (isNetworkConnected()) {
            StringRequest strRequest = new StringRequest(Request.Method.POST, serverUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pInValDialog.dismiss();
                            try {

                                JSONObject obj = new JSONObject(response);
                                Log.d("Test Log", obj.toString());
                                JSONArray jsonArray = obj.getJSONArray("ManagementTextCircular");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj1 = jsonArray.getJSONObject(i);
                                    FieldId = obj1.getString("FileID");
                                    status = obj1.getString("Status");
                                    Reason = obj1.getString("Reason");
                                    if (status.equals("Uploaded")) {
//                                        finish();
                                        String message4 = "Message sent Successfully";
                                        alert1(message4);
//                                        Toast.makeText(getApplicationContext(), "Message sent Successfully", Toast.LENGTH_SHORT).show();


//                                        dialogalert();;

                                    } else {
                                        String message5 = "Message not sent";
                                        alert1(message5);
                                        Log.d("Server Error", "Error");
//                                        Toast.makeText(getApplicationContext(), "Message not sent", Toast.LENGTH_SHORT).show();

                                    }
                                }

                            } catch (Exception e) {
                                Log.d("Exception", e.getMessage());
                                Toast.makeText(getApplicationContext(), "Exception", Toast.LENGTH_SHORT).show();
//                        Log.d("Exception", getApplicationContext().getPackageCodePath() + "-getData()-Message:" + e.getMessage());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pInValDialog.dismiss();
                            Log.d("MyError", error.toString());
//                            Toast.makeText(getApplicationContext(), "Network Problem - Failure", Toast.LENGTH_SHORT).show();

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("IMENumber", Imeno);//?IMENumber="+Imeno+"&PhoneNumber="+Phoneno+"&SchoolID="+schoolid+"&StaffID="+userId+"&TargetCode="+MultiSelectionSpinner.strSelectedValues+"&Count="+words+"&TextMessage="+Textmsg;
                    params.put("PhoneNumber", Phoneno);
                    params.put("SchoolID", schoolid);
                    params.put("StaffID", userId);
                    params.put("TargetCode", MultiSelectionSpinner.strSelectedValues);
                    params.put("Count", targetcountgrp);
                    params.put("TextMessage", Textmsg);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    return params;
                }
            };

            requestQueue.add(strRequest);
        } else {
            Alert("Check Your Internet connection");
            Toast.makeText(this, "Check Your Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Log.d("Is Network", "tetsing");
        return cm.getActiveNetworkInfo() != null;
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


//    @Override
//    public void selectedIndices(List<Integer> indices) {
//
//    }
//
//    @Override
//    public void selectedStrings(List<String> strings) {
//
//    }

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

    private void Alert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Message_student.this);
        builder.setTitle(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();

    }


//    @Override
//    public void selectedIndices(List<Integer> indices) {
//
//    }
//
//    @Override
//    public void selectedStrings(List<String> strings) {
//
//    }
}
