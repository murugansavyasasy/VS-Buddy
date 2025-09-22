package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;


import static pkg.vs.schoolsdemo.voicensapschoolsdemo.util.Util_common.MENU_VOICE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import com.android.volley.RequestQueue;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;

public class CreateDemo extends AppCompatActivity {

    EditText etschoolname, etdemoprincipalnum, etemailId, etdemoparentnuum;
    Button btnAdd, demosubmitbtn, democlearbtn, btnrmv;
    String value;
    String etvalue;
    RequestQueue requestQueue;
    int Result;
    String Reason;

    TextView txt_schoolnamecount;

    String serverPath = "http://220.226.2.177:9000/StaffVoiceAnnouncerCloudSchool/Service.asmx/";

    private ProgressDialog pDialog;
    private List<EditText> editTextList = new ArrayList<>();
    LinearLayout linearLayoutParent;// = new LinearLayout(this);
    String SchoolName;
    TextView textViewnumber;
    String PrincipalNumber;
    String PrincipalEmailId;
    EditText textIn;
    String strDemoGroupId, Message;
    Button button;
    String userId;
    int count = 0;
    int ViewCount = 0;
    String Vims_usertype, Schl_usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_demo);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        linearLayoutParent = (LinearLayout) findViewById(R.id.llParent);

        etschoolname = (EditText) findViewById(R.id.demoschoolname);
        etdemoprincipalnum = (EditText) findViewById(R.id.demoprincipalno);
        etemailId = (EditText) findViewById(R.id.demoprincipalemail);
        textViewnumber = (TextView) findViewById(R.id.tvnumber);
        textIn = (EditText) findViewById(R.id.textin);
        btnAdd = (Button) findViewById(R.id.add);
        txt_schoolnamecount = (TextView) findViewById(R.id.txt_schoolnamecount);

        userId = SharedPreference_class.getShSchlLoginid(CreateDemo.this);


        demosubmitbtn = (Button) findViewById(R.id.demosubmit);
        democlearbtn = (Button) findViewById(R.id.democlear);


        Vims_usertype = SharedPreference_class.getSh_v_Usertype(CreateDemo.this);
        Schl_usertype = SharedPreference_class.getShSchlUsertype(CreateDemo.this);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        final TextWatcher textwatcher1 = new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                Log.d("aftertext", String.valueOf(s));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("beforeTextChanged", String.valueOf(s));


            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("onTextChanged", String.valueOf(s));

                String schoolname = etschoolname.getText().toString();
                String princename = etdemoprincipalnum.getText().toString();
                String addin = textIn.getText().toString();


                if ((Vims_usertype.equals("19"))) {
                    btnAdd.setEnabled(true);

                } else {
                    if (schoolname.isEmpty()) {
                        btnAdd.setEnabled(false);
                    } else if (princename.length() != 10) {
                        btnAdd.setEnabled(false);
                    } else if (addin.length() != 10) {
                        Log.d("else_s", String.valueOf(s));
                        btnAdd.setEnabled(false);
                    } else {
                        btnAdd.setEnabled(true);
                    }
                }
            }
        };

        etschoolname.addTextChangedListener(textwatcher1);
        etdemoprincipalnum.addTextChangedListener(textwatcher1);
        textIn.addTextChangedListener(textwatcher1);


        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                HideKeyboard();
                for (int v = 0; v <= ViewCount; v++) {
                    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.contactnumberlist, null);
                    final EditText textOut = (EditText) addView.findViewById(R.id.textout);
                    ImageButton buttonRemove = (ImageButton) addView.findViewById(R.id.remove);
                    btnAdd.setEnabled(false);
                    buttonRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((LinearLayout) addView.getParent()).removeView(addView);
                            editTextList.remove(textOut);
                            if (textIn.length() != 10) {
                                btnAdd.setEnabled(false);
                            } else {
                                btnAdd.setEnabled(true);
                            }
                        }
                    });

                    textOut.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String number = textOut.getText().toString();

                            if (textOut.length() != 10) {
                                btnAdd.setEnabled(false);
                            } else if (number.isEmpty()) {
                                btnAdd.setEnabled(false);
                            } else {
                                btnAdd.setEnabled(true);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    linearLayoutParent.addView(addView);
                    editTextList.add(textOut);
                    Log.d("Count", String.valueOf(ViewCount));
                }
            }

        });


        demosubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("testclick", "test");

                String schoolname = (etschoolname.getText().toString());
                String principalno = (etdemoprincipalnum.getText().toString());
                String principalemail = (etemailId.getText().toString());
                String inputtxt1 = textIn.getText().toString();
                String parentNumberr = textIn.getText().toString();

                if (schoolname.isEmpty() ||
                        principalno.isEmpty()
                        || inputtxt1.isEmpty()
                ) {

                    Log.d("empty", "test");

                    if (schoolname.isEmpty()) {
                        etschoolname.setError("Enter Your School Name");
                    }

                    if (principalno.isEmpty()) {
                        etdemoprincipalnum.setError("Enter Your Principal number");
                    }


                    if (inputtxt1.isEmpty()) {
                        textIn.setError("Enter Your Parent number");
                    }
                    if (!(Vims_usertype.equals("19"))) {

                        if (inputtxt1.length() != 10) {
                            textIn.setError("Enter Valid Parent number");
                        }
                    }
                    if (!(Vims_usertype.equals("19"))) {
                        if (principalno.length() != 10) {
                            etdemoprincipalnum.setError("Enter Valid Principal number");
                        }
                    }
                } else {

                    Log.d("TestScuccesClick", "test");
                    StringBuilder stringBuilder = new StringBuilder();
                    for (EditText editText : editTextList) {
                        stringBuilder.append(editText.getText() + ",");
                    }
                    AlertApi("Are you sure do you want to create this demo?");
                }
            }
        });


        democlearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTextDemo();
            }
        });

        etschoolname.addTextChangedListener(mTextEditorWatcher);

    }

    private void HideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            txt_schoolnamecount.setText(s.length() + "/50");
        }

        public void afterTextChanged(Editable s) {
        }
    };

    private void clearTextDemo() {
        etschoolname.setText("");
        etdemoprincipalnum.setText("");
        etemailId.setText("");
        textIn.setText("");

        linearLayoutParent.removeAllViews();
        editTextList.clear();
        count = 0;
        etschoolname.requestFocus();
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createdemoretrofit() {

        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();

        if (view == null) {
            view = new View(getApplicationContext());
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        etvalue = textIn.getText().toString();
        value = "";
        for (int j = 0; j < editTextList.size(); j++) {
            value = value + editTextList.get(j).getText().toString() + ",";
        }
        if (value.equals("")) {
            value = etvalue + ",";
        } else {
            value = etvalue + "," + value;
        }
        value = value.substring(0, value.length() - 1);
        Log.d("test", value);
        SchoolName = etschoolname.getText().toString();
        PrincipalNumber = etdemoprincipalnum.getText().toString();
        PrincipalEmailId = etemailId.getText().toString();

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
        jsonObject.addProperty("ParentNos", value);
        jsonObject.addProperty("RequestType", "1");
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
                                strDemoGroupId = jsonObject.getString("DemoID");
                                Message = jsonObject.getString("Message");
                                if (Integer.parseInt(jsonObject.getString("Status")) == 1) {
                                    clearTextDemo();

                                    alertvoiceRecord(Message, strDemoGroupId);


                                } else {

                                    Alert(jsonObject.getString("Message"));

                                }
                            }
                        } else {
                            Alert("No Data Received");
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

    private void alertvoiceRecord(String Message, final String demoid) {
        LayoutInflater li = LayoutInflater.from(CreateDemo.this);
        final View promptsView = li.inflate(R.layout.dialogvoice_alert, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateDemo.this);
        alertDialogBuilder.setView(promptsView);
        Button btnOk = (Button) promptsView.findViewById(R.id.btnconfirm);
//        Button btnCancel = (Button) promptsView.findViewById(R.id.btncancel);
        TextView lblMessages = (TextView) promptsView.findViewById(R.id.lblMessages);
        TextView lblAlertTitle = (TextView) promptsView.findViewById(R.id.lblAlertTitle);
        ImageView imgClose = (ImageView) promptsView.findViewById(R.id.imgClose);
        lblAlertTitle.setText("Alert");

        lblMessages.setText(Message);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CreateDemo.this, Recordwelcomefile.class);
                i.putExtra("Requestcode", MENU_VOICE);
                i.putExtra("demoid", demoid);
                startActivity(i);
                finish();

            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                finish();
            }
        });

        alertDialog.show();
    }

    private void Alert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateDemo.this);
        builder.setTitle(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        builder.create().show();

    }

    private void AlertApi(String msg) {

        Log.d("Alertest", "test");
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateDemo.this);
        builder.setTitle(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                createdemoretrofit();

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        builder.create().show();

    }

}
