package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.PopUp_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;


public class Get_IndividualPO_School extends AppCompatActivity {
    PopUp_class GetPO;
    PopupWindow popupWindow;
    ImageView popclose;
    LinearLayout layout1, layout2, overall;

    Button PoCopy;
    ImageView ScanCopy;
    TextView Po_Type, CustomerName, PO_Number, Scan_copy, Module, Calls_type, Allocated_calls, SMS_type, Allocated_SMS, StaffComponent, PO_Date, PO_validfrom, PO_ValidTo, GoliveBilling, DataRecieved_date, CallCost, SMSCost, StudentCount, StudRate_as_PO, StudentRateMonth, TaxComponent, TaxRate, Miscel_Amount, Miscel_Discription, POvalue_withTax, POvalue_withoutTax, PaymnetType, Adv_amount, Payment_cycle, Business_promotion, Next_invoicedate, Remarks;
    String str_PoType, str_CustomerName, str_PO_Number, str_Scancopy, str_Module, str_Calls_type, str_Allocatedcalls, str_SMStype, str_AllocatedSMS, str_StaffComponent, str_PODate, str_POvalidfrom, str_POValidTo, str_GoliveBilling, str_DataRecieveddate, str_CallCost, str_SMSCost, str_StudentCount, str_StudRateasPO, str_StudentRateMonth, str_TaxComponent, str_TaxRate, str_MiscelAmount, str_MiscelDiscription, str_POvaluewithTax, str_POvaluewithoutTax, str_PaymnetType, str_Advamount, str_Paymentcycle, str_Businesspromotion, str_Next_invoicedate, str_Remarks;

    private static final String TAG = Get_IndividualPO_School.class.getSimpleName();

    String iduser;
    String idValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_individual_po_school);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);


        Po_Type = (TextView) findViewById(R.id.txt_POtype);
        CustomerName = (TextView) findViewById(R.id.txt_customerName);
        PO_Number = (TextView) findViewById(R.id.txt_po_number);


        PoCopy = (Button) findViewById(R.id.Btn_chooseFile);

        Module = (TextView) findViewById(R.id.txt_module);
        Calls_type = (TextView) findViewById(R.id.txt_calltype);
        Allocated_calls = (TextView) findViewById(R.id.txt_alocate_call);
        SMS_type = (TextView) findViewById(R.id.txt_smstype);
        Allocated_SMS = (TextView) findViewById(R.id.txt_allocate_sms);
        StaffComponent = (TextView) findViewById(R.id.txt_staffcomponent);
        PO_Date = (TextView) findViewById(R.id.txt__POdate);
        PO_validfrom = (TextView) findViewById(R.id.txt__povalidfrom);
        PO_ValidTo = (TextView) findViewById(R.id.txt__povalidTo);
        GoliveBilling = (TextView) findViewById(R.id.txt_golivedate);
        CallCost = (TextView) findViewById(R.id.txt_Callcost);
        SMSCost = (TextView) findViewById(R.id.txt_SmsCost);
        DataRecieved_date = (TextView) findViewById(R.id.txt_DateRecieved);
        StudentCount = (TextView) findViewById(R.id.txt_Student_Count);
        StudRate_as_PO = (TextView) findViewById(R.id.txt_rate_as_per_po);
        StudentRateMonth = (TextView) findViewById(R.id.txt_Rate_Month);
        TaxComponent = (TextView) findViewById(R.id.txt_taxcomponent);
        TaxRate = (TextView) findViewById(R.id.txt_taxrate);
        Miscel_Amount = (TextView) findViewById(R.id.txt_Misslaneous_amount);
        Miscel_Discription = (TextView) findViewById(R.id.txt_Misslaneous_description);
        POvalue_withTax = (TextView) findViewById(R.id.txt_PO_withTax);
        POvalue_withoutTax = (TextView) findViewById(R.id.txt_po_withouttax);
        PaymnetType = (TextView) findViewById(R.id.txt_payment_type);
        Adv_amount = (TextView) findViewById(R.id.txt_advance_amount);
        Payment_cycle = (TextView) findViewById(R.id.txt_paymentcycle);
        Business_promotion = (TextView) findViewById(R.id.Textview_business);
        Next_invoicedate = (TextView) findViewById(R.id.txt_nextInvoicedate);
        Remarks = (TextView) findViewById(R.id.txt_remarks);

        //layout_id
        layout1 = (LinearLayout) findViewById(R.id.layout_module);
        layout2 = (LinearLayout) findViewById(R.id.layout_Business);
        overall = (LinearLayout) findViewById(R.id.layout_Overall);

        iduser = SharedPreference_class.getUserid(Get_IndividualPO_School.this);
        Log.d("iduser", iduser);
//IDVALUE
        GetPO = (PopUp_class) getIntent().getSerializableExtra("IDvalue");
        idValue = GetPO.getIdValue();
        Log.d("Idvalue", idValue);

        PoCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("URI", str_Scancopy);


                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(str_Scancopy));
                startActivity(browserIntent);
//                Glide.with(getApplicationContext())
//                        .load(str_Scancopy)
//                        .into(ScanCopy);


            }
        });

        GetPOSchool();

    }

    private void GetPOSchool() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setTitle(" Loading");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);
        JsonObject object = new JsonObject();
        object.addProperty("idUser", iduser);
        Log.d("iduser", iduser);
        object.addProperty("PurchaseOrderID", idValue);
        Log.d("purchaseorderID", idValue);

        Call<JsonArray> call = apiService.Individual_SchoolPO(object);
        call.enqueue(new Callback<JsonArray>() {


            @Override

            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                try {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    Log.d("customer:code-res", response.code() + " - " + response.toString());
                    if (response.code() == 200 || response.code() == 201) {
                        Log.d("Response", response.body().toString());


//                    Log.d("URL", String.valueOf(response.code()));
//                    Log.d("response", response.body().toString());
                        JSONArray Array = new JSONArray(response.body().toString());
                        if (Array.length() > 0) {
                            for (int i = 0; i < Array.length(); i++) {

                                JSONObject Object = Array.getJSONObject(i);
                                String msg = Object.getString("resultMessage");

//                            if(Object.getString("result").equals("-2")){
//                                alert(msg);
//                            }else{

                                str_PoType = Object.getString("POType");
                                Po_Type.setText(str_PoType);

                                str_CustomerName = SharedPreference_class.getCustomername(Get_IndividualPO_School.this);
                                CustomerName.setText(str_CustomerName);
                                Log.d("Customername", str_CustomerName);

                                str_PO_Number = Object.getString("PONumber");
                                PO_Number.setText(str_PO_Number);

                                str_Scancopy = Object.getString("ScanCopyFilePath");

                                str_Module = Object.getString("Module");
                                Module.setText(str_Module);

                                str_Calls_type = Object.getString("CallsType");
                                Calls_type.setText(str_Calls_type);

                                str_Allocatedcalls = Object.getString("CallsCount");
                                Allocated_calls.setText(str_Allocatedcalls);

                                str_SMStype = Object.getString("SmsType");
                                SMS_type.setText(str_SMStype);


                                str_AllocatedSMS = Object.getString("SmsCount");
                                Allocated_SMS.setText(str_AllocatedSMS);

                                str_StaffComponent = Object.getString("StaffComponent");
                                StaffComponent.setText(str_StaffComponent);

                                str_PODate = Object.getString("PODate");
                                PO_Date.setText(str_PODate);

                                str_POvalidfrom = Object.getString("POValidFrom");
                                PO_validfrom.setText(str_POvalidfrom);

                                str_POValidTo = Object.getString("POValidTo");
                                PO_ValidTo.setText(str_POValidTo);

                                str_GoliveBilling = Object.getString("GoLiveDate");
                                GoliveBilling.setText(str_GoliveBilling);

                                str_DataRecieveddate = Object.getString("DataReceivedDate");
                                DataRecieved_date.setText(str_DataRecieveddate);

                                str_CallCost = Object.getString("CallsCost");
                                CallCost.setText(str_CallCost);

                                str_SMSCost = Object.getString("SMSCost");
                                SMSCost.setText(str_SMSCost);

                                str_StudentCount = Object.getString("StudentCount");
                                StudentCount.setText(str_StudentCount);

                                str_StudRateasPO = Object.getString("StudentRate");
                                StudRate_as_PO.setText(str_StudRateasPO);

                                str_StudentRateMonth = Object.getString("StudentRatePerMonth");
                                StudentRateMonth.setText(str_StudentRateMonth);

                                str_TaxComponent = Object.getString("TaxComponent");
                                TaxComponent.setText(str_TaxComponent);


                                str_TaxRate = Object.getString("TaxRate");
                                TaxRate.setText(str_TaxRate);

                                str_MiscelAmount = Object.getString("MiscellaneousAmount");
                                Miscel_Amount.setText(str_MiscelAmount);

                                str_MiscelDiscription = Object.getString("MiscellaneousDescription");
                                Miscel_Discription.setText(str_MiscelDiscription);

                                str_POvaluewithTax = Object.getString("POAmountWithTax");
                                POvalue_withTax.setText(str_POvaluewithTax);

                                str_POvaluewithoutTax = Object.getString("POAmountWithoutTax");
                                POvalue_withoutTax.setText(str_POvaluewithoutTax);

                                str_PaymnetType = Object.getString("PaymentType");
                                PaymnetType.setText(str_PaymnetType);

                                str_Advamount = Object.getString("AdvanceAmount");
                                Adv_amount.setText(str_Advamount);

                                str_Paymentcycle = Object.getString("PaymentCycle");
                                Payment_cycle.setText(str_Paymentcycle);

                                str_Businesspromotion = Object.getString("NotToBill");

                                str_Next_invoicedate = Object.getString("NextInvoiceDate");
                                Next_invoicedate.setText(str_Next_invoicedate);

                                str_Remarks = Object.getString("Remarks");
                                Remarks.setText(str_Remarks);


                                if (str_Module.equals("POSTPAID")) {
                                    Calls_type.setVisibility(View.VISIBLE);
                                    SMS_type.setVisibility(View.VISIBLE);
                                    Allocated_calls.setVisibility(View.VISIBLE);
                                    Allocated_calls.setVisibility(View.VISIBLE);
                                    StaffComponent.setVisibility(View.VISIBLE);
                                    StudentCount.setVisibility(View.VISIBLE);
                                    StudentRateMonth.setVisibility(View.VISIBLE);
                                    TaxComponent.setVisibility(View.VISIBLE);
                                    TaxRate.setVisibility(View.VISIBLE);
                                    Miscel_Amount.setVisibility(View.VISIBLE);
                                    Miscel_Discription.setVisibility(View.VISIBLE);
                                    POvalue_withTax.setVisibility(View.VISIBLE);
                                    POvalue_withoutTax.setVisibility(View.VISIBLE);


                                } else if (str_Module.equals("PREPAID")) {
                                    StaffComponent.setVisibility(View.VISIBLE);
                                    CallCost.setVisibility(View.VISIBLE);
                                    SMSCost.setVisibility(View.VISIBLE);
                                    Calls_type.setVisibility(View.GONE);
                                    SMS_type.setVisibility(View.GONE);
                                    Allocated_calls.setVisibility(View.GONE);
                                    Allocated_calls.setVisibility(View.GONE);


                                } else if (str_Module.equals("FIXED AMOUNT")) {
                                    layout1.setVisibility(View.VISIBLE);
                                    Calls_type.setVisibility(View.GONE);
                                    SMS_type.setVisibility(View.GONE);
                                    Allocated_calls.setVisibility(View.GONE);
                                    Allocated_calls.setVisibility(View.GONE);
                                    StaffComponent.setVisibility(View.GONE);

                                }

                                if (str_Businesspromotion.equals("true")) {
                                    layout2.setVisibility(View.VISIBLE);
                                } else if (str_Businesspromotion.equals("false")) {
                                    layout2.setVisibility(View.GONE);
                                }


                            }

                        } else {
                            alert("No Records has found");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e(TAG, t.toString());


            }


        });


    }

    private void alert(String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Get_IndividualPO_School.this);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        finish();
        return true;
    }


}
