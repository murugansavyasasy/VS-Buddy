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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters.DiscountInfo_huddleAdpter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters.IndividualHuddlePO_Adapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.Discountinfo_huddle;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.IndividualHuddle_PO;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.PopUp_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;


public class GetIndividualPO_Huddle extends AppCompatActivity {
    PopUp_class GetPO;

    PopupWindow popupWindow;
    ImageView popclose;
    LinearLayout layout1, layout2,layout3,layout4,layout5,layout6;



    String iduser;
    String idValue;
    private static final String TAG = GetIndividualPO_Huddle.class.getSimpleName();
    RecyclerView recyclerView;
    Button PoCopy,NDACopy;
    Button CountryPrice,DiscountInfo;
    ArrayList<IndividualHuddle_PO> countrylist = new ArrayList<>();
    ArrayList<Discountinfo_huddle> discountlist = new ArrayList<>();
    IndividualHuddlePO_Adapter Adapter;
    DiscountInfo_huddleAdpter adapter1;


    ImageView ScanCopy,NdaCopy;

    TextView Po_Type, CustomerName, PO_Number,Inbound,Inbound_RateMin,FixedBound,Moderators_Count,AmountPerModerator,Outbound,Outbound_RateMin,AboveValue,DiscountPercentage;
    String str_PoType, str_CustomerName, str_PO_Number, str_POcopy,str_NDACopy,str_Inbound,str_InboundRateMin,str_FixedBound,str_ModeratorCount,str_AmountPerModerator,str_outbound,str_OutboundRate,str_AboveValue,str_DiscountPercent,str_Country,str_rateperminInbound;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_individual_po_huddle);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);

        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        Po_Type=(TextView)findViewById(R.id.txt_POtype);
        CustomerName=(TextView)findViewById(R.id.txt_customerName);
        PO_Number=(TextView)findViewById(R.id.txt_po_number);

        PoCopy = (Button) findViewById(R.id.Btn_POcopy);
        NDACopy=(Button) findViewById(R.id.Btn_NDA);
        CountryPrice=(Button)findViewById(R.id.btn_CountryPrice);
       DiscountInfo=(Button)findViewById(R.id.btn_discountInfo) ;

        Inbound=(TextView) findViewById(R.id.txt_inbound);
        Inbound_RateMin=(TextView)findViewById(R.id.txt_InboundRateMIn) ;
        FixedBound=(TextView)findViewById(R.id.txt_fixedinbound) ;
        Moderators_Count=(TextView)findViewById(R.id.moderatorCount) ;
        AmountPerModerator=(TextView)findViewById(R.id.amtPerModerator) ;
        Outbound=(TextView)findViewById(R.id.txt_outbound) ;
        Outbound_RateMin=(TextView)findViewById(R.id.outboundratepermin) ;
//        AboveValue=(TextView)findViewById(R.id.txt_above_value);
//        DiscountPercentage=(TextView)findViewById(R.id.txt_discountPercentage);

        layout1=(LinearLayout)findViewById(R.id.layout_full) ;
        layout2=(LinearLayout)findViewById(R.id.layout_InboundRateMin) ;
        layout3=(LinearLayout)findViewById(R.id.layout_moderatorCount) ;
        layout4=(LinearLayout)findViewById(R.id.layout_amountpermoderator) ;
        layout5=(LinearLayout)findViewById(R.id.layout_outboundPerMin) ;


//        search=(EditText) findViewById(R.id.search);

        iduser = SharedPreference_class.getUserid(GetIndividualPO_Huddle.this);
        Log.d("iduser", iduser);
//IDVALUE
        GetPO = (PopUp_class) getIntent().getSerializableExtra("IDvalue");
        idValue = GetPO.getIdValue();
        Log.d("Idvalue",idValue);



        GetPO_Huddle();


        PoCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.scan_copy_popup, null, false);
                ScanCopy= (ImageView) layout.findViewById(R.id.PO_ScanCopy);
                Log.d("URI",str_POcopy);
                Glide.with(getApplicationContext())
                        .load(str_POcopy)
                        .into(ScanCopy);

                popupWindow = new PopupWindow(layout,MATCH_PARENT,MATCH_PARENT, false);
                popupWindow.showAtLocation(layout, Gravity.CENTER_HORIZONTAL, 0, 0);
                popupWindow.setContentView(layout);
                if (popupWindow.isShowing()) {

                    layout1.setVisibility(View.GONE);
                }

                popclose=(ImageView) layout.findViewById(R.id.pop_close1);
                popclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("close_btn","Button");
                        popupWindow.dismiss();
                        layout1.setVisibility(View.VISIBLE);

//

                    }
                });


            }
        });
        NDACopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.nda_copy_popup, null, false);
                NdaCopy= (ImageView) layout.findViewById(R.id.PO_NDACopy);
                Log.d("URI",str_NDACopy);
                Glide.with(getApplicationContext())
                        .load(str_NDACopy)
                        .into(NdaCopy);
                popupWindow = new PopupWindow(layout,MATCH_PARENT , MATCH_PARENT, false);
                popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
                popupWindow.setContentView(layout);
                if (popupWindow.isShowing()) {

                    layout1.setVisibility(View.GONE);
                }

                popclose=(ImageView) layout.findViewById(R.id.pop_close2);
                popclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("close_btn","Button");
                        popupWindow.dismiss();
                        layout1.setVisibility(View.VISIBLE);

//

                    }
                });


            }
        });


        DiscountInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.huddle_discount_popup, null, false);
                recyclerView = (RecyclerView) layout.findViewById(R.id.recycler_huddle1);

                popupWindow = new PopupWindow(layout,MATCH_PARENT, MATCH_PARENT, false);
                popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
                popupWindow.setContentView(layout);
                if (popupWindow.isShowing()) {

                    layout1.setVisibility(View.GONE);
                }


                popclose=(ImageView) layout.findViewById(R.id.pop_close);
                popclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("close_btn","Button");
                        popupWindow.dismiss();
                        layout1.setVisibility(View.VISIBLE);


                    }
                });

//                Log.d("search","searched");

//                String searched=search.getText().toString();



                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                adapter1 = new DiscountInfo_huddleAdpter(GetIndividualPO_Huddle.this, discountlist);
                recyclerView.setAdapter(adapter1);

                adapter1.notifyDataSetChanged();



            }
        });




        CountryPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.huddle_po_countryprice_list, null, false);
                recyclerView = (RecyclerView) layout.findViewById(R.id.recycler_huddle);

                popupWindow = new PopupWindow(layout,MATCH_PARENT, MATCH_PARENT, false);
                popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
                popupWindow.setContentView(layout);
                if (popupWindow.isShowing()) {

                    layout1.setVisibility(View.GONE);
                }


                popclose=(ImageView) layout.findViewById(R.id.pop_close);
                popclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("close_btn","Button");
                        popupWindow.dismiss();
                        layout1.setVisibility(View.VISIBLE);


                    }
                });

//                Log.d("search","searched");

//                String searched=search.getText().toString();



                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                Adapter = new IndividualHuddlePO_Adapter(GetIndividualPO_Huddle.this, countrylist);
                recyclerView.setAdapter(Adapter);
                Adapter.notifyDataSetChanged();



            }
        });


//        GetPO_Huddle();
    }

  private void GetPO_Huddle()  {
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

        Call<JsonArray> call = apiService.Individual_HuddlePO(object);
        call.enqueue(new Callback<JsonArray>() {


            @Override

            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                try {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    Log.d("customer:code-res", response.code() + " - " + response.toString());
                    if (response.code() == 200 || response.code() == 201) {
                        Log.d("Response", response.body().toString());

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

                                    str_CustomerName = SharedPreference_class.getCustomername(GetIndividualPO_Huddle.this);
                                    CustomerName.setText(str_CustomerName);
                                    Log.d("Customername", str_CustomerName);

                                    str_PO_Number = Object.getString("PONumber");
                                    PO_Number.setText(str_PO_Number);

                                    str_POcopy = Object.getString("ScanCopyFilePath");

                                    str_NDACopy = Object.getString("NDACopyFilePath");

                                    str_Inbound = Object.getString("isInbound");
                                    Inbound.setText(str_Inbound);

                                    str_InboundRateMin = Object.getString("InboundRatePerMin");
                                    Inbound_RateMin.setText(str_InboundRateMin);

                                    str_FixedBound = Object.getString("isFixedInbound");
                                    FixedBound.setText(str_FixedBound);

                                    str_ModeratorCount = Object.getString("ModeratorCount");
                                    Moderators_Count.setText(str_ModeratorCount);

                                    str_AmountPerModerator = Object.getString("FixedAmountPerModerator");
                                    AmountPerModerator.setText(str_AmountPerModerator);

                                    str_outbound = Object.getString("isOutbound");
                                    Outbound.setText(str_outbound);

                                    str_OutboundRate = Object.getString("OutboundRatePerMin");
                                    Outbound_RateMin.setText(str_OutboundRate);

                                     discountlist.clear();

                                    JSONArray jsonArray1 = Object.getJSONArray("DiscountModule");
                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        JSONObject object1 = jsonArray1.getJSONObject(j);
                                        str_AboveValue = object1.getString("AboveValue");
                                        str_DiscountPercent = object1.getString("DiscountPercentage");

                                        Discountinfo_huddle discount = new Discountinfo_huddle(str_AboveValue, str_DiscountPercent);
                                        discountlist.add(discount);

                                    }
                                    countrylist.clear();

                                    JSONArray jsonArray2 = Object.getJSONArray("HuddlePriceInfo");

                                    for (int k = 0; k < jsonArray2.length(); k++) {
                                        JSONObject object1 = jsonArray2.getJSONObject(k);

                                        str_Country = object1.getString("Country");

                                        str_rateperminInbound = object1.getString("RatePerMinuteInbound");
//                                    Price.setText(str_Price);

                                        IndividualHuddle_PO countryprice = new IndividualHuddle_PO(str_Country, str_rateperminInbound);
                                        countrylist.add(countryprice);

                                    }


                                    if (str_Inbound.equals("Yes")) {
                                        layout2.setVisibility(View.VISIBLE);
                                    } else {
                                        layout2.setVisibility(View.GONE);
                                    }
                                    if ((str_FixedBound.equals("Yes")) && (str_Inbound.equals("Yes"))) {
                                        layout3.setVisibility(View.VISIBLE);
                                        layout4.setVisibility(View.VISIBLE);
                                    } else {
                                        layout3.setVisibility(View.GONE);
                                        layout4.setVisibility(View.GONE);
                                    }
                                    if (str_outbound.equals("Yes")) {
                                        layout5.setVisibility(View.VISIBLE);
                                    } else {
                                        layout5.setVisibility(View.GONE);
                                    }

//                                }

                            }

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
            AlertDialog.Builder builder = new AlertDialog.Builder(GetIndividualPO_Huddle.this);
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
