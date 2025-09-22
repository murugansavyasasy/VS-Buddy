package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import com.bumptech.glide.Glide;
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


public class GetIndividualPO_Corporate extends AppCompatActivity {
    PopUp_class GetPO;

    PopupWindow popupWindow;
    ImageView popclose;
    LinearLayout Individual,layout1,layout2,layout3,layout4,layout5,layout6,layout7,layout8,layout9,layout10,layout11,layout12,layout13,layout14,layout15,layout16,layout17;


    Button PoCopy;
    ImageView ScanCopy;

    TextView Po_Type, CustomerName, PO_Number, Scan_copy, Select_Solution, Solution_name, InvoiceType, Rate_Agent, Total_Agent, Miscell_Amt, Miscell_Discrption, PO_ValidTo, PO_Validfrom, Golive_date, SMS, Inbound, Fixed_Inbound, RatePer_MinInbound, Slab_Inbound, SlabPulse_Inbound, SlabCost_Inbound, SmsType, SmsCost, AllocatedSmS, DiscountAmt, Calls, Outbound, Fixed_Outbound, RatePerMinuteOutbound, Slab_Outbound, SlabPulse_Outbound, SlabCost_Outbound, PaymentCycle, Remarks;
    String str_PoType, str_CustomerName, str_PO_Number, str_Scancopy, str_SelectSolution, str_SolutionName, str_InvoiceType,isagentType, str_RateAgent, str_TotalAgent, str_MiscellAmt, str_MiscellDiscrption, str_POValidTo, str_POValidfrom, str_Golivedate, str_SMS, str_Inbound, str_FixedInbound, str_RatePerMinInbound, str_SlabInbound, str_SlabPulseInbound, str_SlabCostInbound, str_SmsType, str_SmsCost, str_AllocatedSmS, str_DiscountAmt, str_Calls, str_Outbound, str_Fixed_Outbound, str_RatePerMinuteOutbound, str_SlabOutbound, str_SlabPulseOutbound, str_SlabCost_Outbound, str_PaymentCycle, str_Remarks;

    private static final String TAG = GetIndividualPO_Corporate.class.getSimpleName();


    String iduser;
    String idValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_individual_po_corporate);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

//       layout1=(LinearLayout)findViewById(R.id.layout_Typeof_SMS);
        Individual=(LinearLayout)findViewById(R.id.layout_Individual);
        layout1=(LinearLayout)findViewById(R.id.layout_Typeof_SMS);
        layout2=(LinearLayout)findViewById(R.id.layout_allocatedSMS);
        layout3=(LinearLayout)findViewById(R.id.layout_Inbound);
        layout4=(LinearLayout)findViewById(R.id.layout_Outbound);
        layout5=(LinearLayout)findViewById(R.id.layout_fixedInbound);
        layout6=(LinearLayout)findViewById(R.id.layout_SlabInbound);
        layout7=(LinearLayout)findViewById(R.id.layout_RatePerMinInbound);
        layout8=(LinearLayout)findViewById(R.id.layout_SlabPulseInbound);
        layout9=(LinearLayout)findViewById(R.id.layout_slabCostInbond);
        layout10=(LinearLayout)findViewById(R.id.layout_fixedOutbound);
        layout11=(LinearLayout)findViewById(R.id.layout_SlabOutbound);
        layout12=(LinearLayout)findViewById(R.id.layout_ratePerMinOutbound);
        layout13=(LinearLayout)findViewById(R.id.layout_Slabpulseoutbound);
        layout14=(LinearLayout)findViewById(R.id.layout_slabcostoutbound);
        layout15=(LinearLayout)findViewById(R.id.layout_agenttype);
        layout16=(LinearLayout)findViewById(R.id.solutionName);






        Po_Type = (TextView) findViewById(R.id.txt_POtype);
        CustomerName = (TextView) findViewById(R.id.txt_customerName);
        PO_Number = (TextView) findViewById(R.id.txt_po_number);


        PoCopy = (Button) findViewById(R.id.Btn_chooseFile1);

        Select_Solution = (TextView) findViewById(R.id.txt_selectSolution);
        Solution_name = (TextView) findViewById(R.id.txt_slnName);
        InvoiceType = (TextView) findViewById(R.id.txt__invoicetype);
        Rate_Agent = (TextView) findViewById(R.id.txt_Rate_Month);
        Total_Agent = (TextView) findViewById(R.id.txt_totalAgent);
        Miscell_Amt = (TextView) findViewById(R.id.txt_Misslaneous_amount);
        Miscell_Discrption = (TextView) findViewById(R.id.txt_Misslaneous_description);
        PO_ValidTo = (TextView) findViewById(R.id.txt__povalidTo);
        PO_Validfrom = (TextView) findViewById(R.id.txt__povalidfrom);
        Golive_date = (TextView) findViewById(R.id.txt_golivedate);

        SMS = (TextView) findViewById(R.id.txt_SmS);
        Inbound = (TextView) findViewById(R.id.txt_Inbound);
        Fixed_Inbound = (TextView) findViewById(R.id.txt_fixedInbound);
        RatePer_MinInbound = (TextView) findViewById(R.id.txt_rateperMinInbound);
        Slab_Inbound = (TextView) findViewById(R.id.txt_slabInbound);
        SlabPulse_Inbound = (TextView) findViewById(R.id.txt_pulseSlab_inbound);
        SlabCost_Inbound = (TextView) findViewById(R.id.txt_costSlab_inbound);
        SmsType = (TextView) findViewById(R.id.txt_SmsType);
        SmsCost = (TextView) findViewById(R.id.txt_SmsCost);
        AllocatedSmS = (TextView) findViewById(R.id.txt_allocate_sms);
        DiscountAmt = (TextView) findViewById(R.id.txt_discount_amt);

        Calls = (TextView) findViewById(R.id.txt_Calls);
        Outbound = (TextView) findViewById(R.id.txt_Outbound);
        Fixed_Outbound = (TextView) findViewById(R.id.txt_fixedOutbound);
        RatePerMinuteOutbound = (TextView) findViewById(R.id.txt_rateMinuteOutbound);
        Slab_Outbound = (TextView) findViewById(R.id.txt_slabOutbound);
        SlabPulse_Outbound = (TextView) findViewById(R.id.txt_pulseSlab_outbound);
        SlabCost_Outbound = (TextView) findViewById(R.id.txt_costSlab_outbound);
        PaymentCycle = (TextView) findViewById(R.id.txt_paymentcycle);
        Remarks = (TextView) findViewById(R.id.txt_remarks);



        iduser = SharedPreference_class.getUserid(GetIndividualPO_Corporate.this);
        Log.d("iduser", iduser);
//IDVALUE
        GetPO = (PopUp_class) getIntent().getSerializableExtra("IDvalue");
        idValue = GetPO.getIdValue();
        Log.d("Idvalue",idValue);

        PoCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.scan_copy_popup, null, false);
                ScanCopy= (ImageView) layout.findViewById(R.id.PO_ScanCopy);
//                Log.d("URI",str_Scancopy);
                Glide.with(getApplicationContext())
                        .load(str_Scancopy)
                        .into(ScanCopy);



              final  PopupWindow popupWindow = new PopupWindow(layout, MATCH_PARENT, MATCH_PARENT,  true);
                popupWindow.showAtLocation(layout, Gravity.CENTER_HORIZONTAL, 0, 0);
                if(popupWindow.isShowing()){
                    Individual.setVisibility(View.GONE);
                }


                popupWindow.setContentView(layout);
                popclose=(ImageView) layout.findViewById(R.id.pop_close1);
                popclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("close_btn","Button");
                        popupWindow.dismiss();
                        Individual.setVisibility(View.VISIBLE);

//

                    }
                });




            }
        });

        GetPO_Corporate();

    }

    private  void GetPO_Corporate(){
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setTitle(" Loading");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);
        JsonObject object = new JsonObject();
        object.addProperty("idUser", iduser);
        Log.d("iduser",iduser);
        object.addProperty("PurchaseOrderID",idValue);
        Log.d("purchaseorderID",idValue);

        Call<JsonArray> call = apiService.Individual_CorporatePO(object);
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
                                String msg= Object.getString("resultMessage");

//                                if(Object.getString("result").equals("-2")){
//                                    alert(msg);
//                                }else {

                                    str_PoType = Object.getString("POType");
                                    Po_Type.setText(str_PoType);

                                    str_CustomerName = SharedPreference_class.getCustomername(GetIndividualPO_Corporate.this);
                                    CustomerName.setText(str_CustomerName);
                                    Log.d("Customername", str_CustomerName);

                                    str_PO_Number = Object.getString("PONumber");
                                    PO_Number.setText(str_PO_Number);

                                    str_Scancopy = Object.getString("ScanCopyFilePath");

                                    str_SelectSolution = Object.getString("Solution");
                                    Select_Solution.setText(str_SelectSolution);

                                    str_SolutionName = Object.getString("SolutionName");
                                    Solution_name.setText(str_SolutionName);

                                    str_InvoiceType = Object.getString("InvoicingType");
                                    InvoiceType.setText(str_InvoiceType);

                                    str_RateAgent = Object.getString("RatePerAgentPerMonth");
                                    Rate_Agent.setText(str_RateAgent);

                                    str_TotalAgent = Object.getString("TotalAgent");
                                    Total_Agent.setText(str_TotalAgent);

                                    str_MiscellAmt = Object.getString("MiscellaneousAmount");
                                    Miscell_Amt.setText(str_MiscellAmt);

                                    str_MiscellDiscrption = Object.getString("MiscellaneousDescription");
                                    Miscell_Discrption.setText(str_MiscellDiscrption);

                                    str_POValidTo = Object.getString("POValidTo");
                                    PO_ValidTo.setText(str_POValidTo);

                                    str_POValidfrom = Object.getString("POValidFrom");
                                    PO_Validfrom.setText(str_POValidfrom);


                                    str_Golivedate = Object.getString("GoLiveDate");
                                    Golive_date.setText(str_Golivedate);

                                    str_SMS = Object.getString("SMS");
                                    SMS.setText(str_SMS);


                                    str_Inbound = Object.getString("isInbound");
                                    Inbound.setText(str_Inbound);

                                    str_FixedInbound = Object.getString("isFixedInbound");
                                    Fixed_Inbound.setText(str_FixedInbound);

                                    str_RatePerMinInbound = Object.getString("RatePerMinuteInbound");
                                    RatePer_MinInbound.setText(str_RatePerMinInbound);

                                    str_SlabInbound = Object.getString("isSlabInbound");
                                    Slab_Inbound.setText(str_SlabInbound);

                                    str_SlabPulseInbound = Object.getString("SlabPulseInbound");
                                    SlabPulse_Inbound.setText(str_SlabPulseInbound);

                                    str_SlabCostInbound = Object.getString("SlabCostInbound");
                                    SlabCost_Inbound.setText(str_SlabCostInbound);


                                    str_SmsType = Object.getString("SMSType");
                                    SmsType.setText(str_SmsType);

                                    str_SmsCost = Object.getString("SMSCost");
                                    SmsCost.setText(str_SmsCost);

                                    str_AllocatedSmS = Object.getString("SMSCount");
                                    AllocatedSmS.setText(str_AllocatedSmS);

                                    str_DiscountAmt = Object.getString("DiscountAmount");
                                    DiscountAmt.setText(str_DiscountAmt);

                                    str_Calls = Object.getString("Calls");
                                    Calls.setText(str_Calls);

                                    str_Outbound = Object.getString("isOutbound");
                                    Outbound.setText(str_Outbound);

                                    str_Fixed_Outbound = Object.getString("isFixedOutbound");
                                    Fixed_Outbound.setText(str_Fixed_Outbound);

                                    str_RatePerMinuteOutbound = Object.getString("RatePerMinuteOutbound");
                                    RatePerMinuteOutbound.setText(str_RatePerMinuteOutbound);

                                    str_SlabOutbound = Object.getString("isSlabOutbound");

                                    str_SlabPulseOutbound = Object.getString("SlabPulseOutbound");
                                    SlabPulse_Outbound.setText(str_SlabPulseOutbound);

                                    str_SlabCost_Outbound = Object.getString("SlabCostOutbound");
                                    SlabCost_Outbound.setText(str_SlabCost_Outbound);

                                    str_PaymentCycle = Object.getString("PaymentCycle");
                                    PaymentCycle.setText(str_PaymentCycle);

                                    str_Remarks = Object.getString("Remarks");
                                    Remarks.setText(str_Remarks);


                                    if (str_SelectSolution.equals("Other")) {
                                        layout16.setVisibility(View.VISIBLE);
                                    } else {
                                        layout16.setVisibility(View.GONE);
                                    }

//                                if(isagentType.equals("1")){
//                                    layout15.setVisibility(View.VISIBLE);
//                                }

                                    if (str_SMS.equals("Yes")) {
                                        layout1.setVisibility(View.VISIBLE);
                                        if (str_SmsType.equals("Limited")) {
                                            layout2.setVisibility(View.VISIBLE);
                                        } else {
                                            layout2.setVisibility(View.GONE);
                                        }
                                    } else {
                                        layout1.setVisibility(View.GONE);
                                    }
                                    if (str_Calls.equals("Yes")) {
                                        layout3.setVisibility(View.VISIBLE);
                                        layout4.setVisibility(View.VISIBLE);

                                    } else {
                                        layout3.setVisibility(View.GONE);
                                        layout4.setVisibility(View.GONE);
                                    }
                                    if (str_Inbound.equals("Yes")) {
                                        layout5.setVisibility(View.VISIBLE);
                                        layout6.setVisibility(View.VISIBLE);

                                    } else {
                                        layout5.setVisibility(View.GONE);
                                        layout6.setVisibility(View.GONE);
                                    }
                                    if (str_FixedInbound.equals("Yes")) {
                                        layout7.setVisibility(View.VISIBLE);
                                    } else {
                                        layout7.setVisibility(View.GONE);
                                    }
                                    if (str_SlabInbound.equals("Yes")) {
                                        layout8.setVisibility(View.VISIBLE);
                                        layout9.setVisibility(View.VISIBLE);
                                    } else {
                                        layout8.setVisibility(View.GONE);
                                        layout9.setVisibility(View.GONE);
                                    }
                                    if (str_Outbound.equals("Yes")) {
                                        layout10.setVisibility(View.VISIBLE);
                                        layout11.setVisibility(View.VISIBLE);

                                    } else {
                                        layout10.setVisibility(View.GONE);
                                        layout11.setVisibility(View.GONE);
                                    }
                                    if (str_Fixed_Outbound.equals("Yes")) {
                                        layout12.setVisibility(View.VISIBLE);
                                    } else {
                                        layout12.setVisibility(View.GONE);
                                    }
                                    if (str_SlabOutbound.equals("Yes")) {
                                        layout13.setVisibility(View.VISIBLE);
                                        layout14.setVisibility(View.VISIBLE);
                                    } else {
                                        layout13.setVisibility(View.GONE);
                                        layout14.setVisibility(View.GONE);
                                    }
//                                }
                            }

                        }else{
                            Toast.makeText(GetIndividualPO_Corporate.this, "No Records has found", Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(GetIndividualPO_Corporate.this);
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
