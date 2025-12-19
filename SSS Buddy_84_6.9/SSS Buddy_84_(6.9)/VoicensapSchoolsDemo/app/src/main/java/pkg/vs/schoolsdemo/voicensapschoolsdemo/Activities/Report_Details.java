package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.Report_localExpenseClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;


public class Report_Details extends AppCompatActivity {

    private static final String TAG = Report_Details.class.getSimpleName();
    TextView BoardLodge1, PetrolFuel1, Telephonedata1, ConveyTravel1, PostageCourier1, MiscelExpense1, FoodBeverage1, PrintingStationary1;

    TextView BoardLodge2, PetrolFuel2, Telephonedata2, ConveyTravel2, PostageCourier2, MiscelExpense2, FoodBeverage2, PrintingStationary2;

    LinearLayout full_layout;
    String idLocalExpense;
    Report_localExpenseClass info;

    Button btn_viewbill;

    String BoardLodge, ConvTravel, Food, Fuel, PostageCourier, Printing, Telephone, Misc, BoardLodgeWithoutBill, ConvTravelWithoutBill, FoodWithoutBill, FuelWithoutBill, PostageCourierWithoutBill, PrintingWithoutBill, TelephoneWithoutBill, MiscWithoutBill, filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);
        BoardLodge1 = (TextView) findViewById(R.id.board);
        ConveyTravel1 = (TextView) findViewById(R.id.convey);
        FoodBeverage1 = (TextView) findViewById(R.id.food_Beverages);
        PetrolFuel1 = (TextView) findViewById(R.id.petrolFuel);
        PostageCourier1 = (TextView) findViewById(R.id.postageCourier);
        PrintingStationary1 = (TextView) findViewById(R.id.printSationary);
        Telephonedata1 = (TextView) findViewById(R.id.Telephonedata);
        MiscelExpense1 = (TextView) findViewById(R.id.MiscellExpense);

        BoardLodge2 = (TextView) findViewById(R.id.ed_BoradLodge2);
        ConveyTravel2 = (TextView) findViewById(R.id.conveytravel2);
        FoodBeverage2 = (TextView) findViewById(R.id.food_Beverages2);
        PetrolFuel2 = (TextView) findViewById(R.id.petrolFuel2);
        PostageCourier2 = (TextView) findViewById(R.id.postageCourier2);
        PrintingStationary2 = (TextView) findViewById(R.id.printSationary2);
        Telephonedata2 = (TextView) findViewById(R.id.Telephonedata2);
        MiscelExpense2 = (TextView) findViewById(R.id.MiscellExpense2);
        btn_viewbill = (Button) findViewById(R.id.btn_viewbill);
        full_layout = (LinearLayout) findViewById(R.id.full_layout);

        info = (Report_localExpenseClass) getIntent().getSerializableExtra("idlocal");
        idLocalExpense = info.getIdLocalExpense();




        GetReport_Details();

        filepath= info.getFilepath();
        Log.d("filepahimaghe",filepath);


        if (!filepath.isEmpty()) {

            btn_viewbill.setVisibility(View.VISIBLE);
            btn_viewbill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (filepath.contains("pdf")){

                        Intent intent = new Intent(getApplicationContext(), PdfView_Activity.class);
                        intent.putExtra("Pdffilepath", filepath);
                        startActivity(intent);

                    }else {

                        Intent intent = new Intent(getApplicationContext(), ImageViewActivity.class);
                        intent.putExtra("Imageview", filepath);
                        startActivity(intent);

                    }

                }
            });

        } else {
            btn_viewbill.setVisibility(View.GONE);
        }
    }

    public void GetReport_Details() {


        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setTitle(" Loading");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
//        idLocalExp = SharedPreference_class.getReport_idlocalExpense(Report_Details.this);
//        Log.d("getidlocal", idLocalExp);

        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);
//        Log.d("RequestInputDetails",idLocalExp);


        Call<JsonArray> call = apiService.GetReport_Details(idLocalExpense);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                try {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    {
                        Log.d("customer:code-res", response.code() + " - " + response.toString());
                        if (response.code() == 200 || response.code() == 201) {
                            Log.d("Response", response.body().toString());
//                            Log.d("URL", String.valueOf(response.code()));
                            JSONArray array = new JSONArray(response.body().toString());
                            if (array.length() > 0) {

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsobj = array.getJSONObject(i);

                                    BoardLodge = jsobj.getString("BoardLodge");
                                    BoardLodge1.setText(BoardLodge);

                                    ConvTravel = jsobj.getString("ConvTravel");
                                    ConveyTravel1.setText(ConvTravel);

                                    Food = jsobj.getString("Food");
                                    FoodBeverage1.setText(Food);

                                    Fuel = jsobj.getString("Fuel");
                                    PetrolFuel1.setText(Fuel);

                                    PostageCourier = jsobj.getString("PostageCourier");
                                    PostageCourier1.setText(PostageCourier);

                                    Printing = jsobj.getString("Printing");
                                    PrintingStationary1.setText(Printing);

                                    Telephone = jsobj.getString("Telephone");
                                    Telephonedata1.setText(Telephone);

                                    Misc = jsobj.getString("Misc");
                                    MiscelExpense1.setText(Misc);

                                    BoardLodgeWithoutBill = jsobj.getString("BoardLodgeWithoutBill");
                                    BoardLodge2.setText(BoardLodgeWithoutBill);

                                    ConvTravelWithoutBill = jsobj.getString("ConvTravelWithoutBill");
                                    ConveyTravel2.setText(ConvTravelWithoutBill);

                                    FoodWithoutBill = jsobj.getString("FoodWithoutBill");
                                    FoodBeverage2.setText(FoodWithoutBill);

                                    FuelWithoutBill = jsobj.getString("FuelWithoutBill");
                                    PetrolFuel2.setText(FuelWithoutBill);

                                    PostageCourierWithoutBill = jsobj.getString("PostageCourierWithoutBill");
                                    PostageCourier2.setText(PostageCourierWithoutBill);

                                    PrintingWithoutBill = jsobj.getString("PrintingWithoutBill");
                                    PrintingStationary2.setText(PrintingWithoutBill);

                                    TelephoneWithoutBill = jsobj.getString("TelephoneWithoutBill");
                                    Telephonedata2.setText(TelephoneWithoutBill);

                                    MiscWithoutBill = jsobj.getString("MiscWithoutBill");
                                    MiscelExpense2.setText(MiscWithoutBill);

                                }
                            }

                        } else {

                            alert("No Records has found");
                        }

//                            Toast.makeText(Report_Details.this, "No Records has found", Toast.LENGTH_SHORT).show();

                    }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private void alert(String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Report_Details.this);
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