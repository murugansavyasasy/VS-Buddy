package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.Edit_localexp_1;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.Edit_localexpense_2;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.Report_localExpenseClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;

public class Report_Edit extends AppCompatActivity {
    private static final String TAG = Report_Edit.class.getSimpleName();

//With Bill

    EditText Ed_ExpenseDescription, ed_BoardLodge, ed_PetrolFuel, ed_Telephone, ed_ConveyTravel, ed_PostageCourier, ed_MiscelExpense, ed_FoodBeverage, ed_PrintingStationary, Ed_Remarks;
    EditText Ed_BoardLodge2, Ed_PetrolFuel2, Ed_Telephone2, Ed_ConveyTravel2, Ed_PostageCourier2, Ed_MiscelExpense2, Ed_FoodBeverage2, Ed_PrintingStationary2, Ed_Remarks2;

    TextView Txt_TotalLocalExpenses;
    int currentMonth;
    Report_localExpenseClass info;
    String BoardLodge,PetrolFuel,Telephone_Datacharge,ConveyTravel,PostCourier,MiscelExpense,FoodBeverage,PrintingStationary;
    String BoardLodge2, PetrolFuel2, Telephone_Datacharge2, ConveyTravel2, PostCourier2, MiscelExpense2, FoodBeverage2, PrintingStationary2;

    String Board,Petrol,Telephone,Convey_Trav,Postage,Misc_exp,Food,Printing;
    String Board_2,Petrol_2,Telephone_2,Convey_Trav_2,Postage_2,Misc_exp_2,Food_2,Printing_2;
    Button Submit;
    Spinner MonthSpinner;
    String iduser;
    String Month_Claim;
    String ClaimMonth;
    int Monthid;

    public List<Edit_localexp_1> LocalExpensesItemlist = new ArrayList<>();
    public List<Edit_localexpense_2> LocalExpensesItemlist2 = new ArrayList<>();
    String idLocalExpense;
    String Description, Remarks, RemarksWithoutBill,tot_expense;
    String months;

    TextView lblWithBill,
    lblWithoutBill;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report__edit);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        MonthSpinner = (Spinner) findViewById(R.id.spin_Month);

        //withbill
        Ed_ExpenseDescription = (EditText) findViewById(R.id.Ed_ExpnseDescription);

        ed_BoardLodge = (EditText) findViewById(R.id.ed_BoradLodge1);
        ed_PetrolFuel = (EditText) findViewById(R.id.ed_petrolFuel1);
        ed_Telephone = (EditText) findViewById(R.id.ed_Telephone1);
        ed_PostageCourier = (EditText) findViewById(R.id.ed_postageCourier1);
        ed_ConveyTravel = (EditText) findViewById(R.id.ed_conveytravel1);
        ed_MiscelExpense = (EditText) findViewById(R.id.ed_MiscellExpense1);
        ed_FoodBeverage = (EditText) findViewById(R.id.ed_food_Beverages1);
        ed_PrintingStationary = (EditText) findViewById(R.id.ed_printSationary1);
        Ed_Remarks = (EditText) findViewById(R.id.ed_Remarks1);
        lblWithBill = (TextView) findViewById(R.id.lblWithBill);
        lblWithoutBill = (TextView) findViewById(R.id.lblWithoutBill);

        //withoutbill
        Ed_BoardLodge2 = (EditText) findViewById(R.id.ed_BoradLodge_2);
        Ed_PetrolFuel2 = (EditText) findViewById(R.id.ed_petrolFuel_2);
        Ed_Telephone2 = (EditText) findViewById(R.id.ed_Telephone_2);
        Ed_PostageCourier2 = (EditText) findViewById(R.id.ed_postageCourier_2);
        Ed_ConveyTravel2 = (EditText) findViewById(R.id.ed_conveytravel_2);
        Ed_MiscelExpense2 = (EditText) findViewById(R.id.ed_MiscellExpense_2);
        Ed_FoodBeverage2 = (EditText) findViewById(R.id.ed_food_Beverages_2);
        Ed_PrintingStationary2 = (EditText) findViewById(R.id.ed_printSationary_2);
        Ed_Remarks2 = (EditText) findViewById(R.id.ed_Remarks_2);

        Txt_TotalLocalExpenses = (TextView) findViewById(R.id.txt_TotalLocal_expense_2);

        Submit = (Button) findViewById(R.id.btn_Submit);

        iduser = SharedPreference_class.getUserid(Report_Edit.this);
        Log.d("iduser", String.valueOf(iduser));
        ed_BoardLodge.addTextChangedListener(generalTextWatcher);
        ed_PetrolFuel.addTextChangedListener(generalTextWatcher);
        ed_Telephone.addTextChangedListener(generalTextWatcher);
        ed_PostageCourier.addTextChangedListener(generalTextWatcher);
        ed_ConveyTravel.addTextChangedListener(generalTextWatcher);
        ed_MiscelExpense.addTextChangedListener(generalTextWatcher);
        ed_FoodBeverage.addTextChangedListener(generalTextWatcher);
        ed_PrintingStationary.addTextChangedListener(generalTextWatcher);


        Ed_BoardLodge2.addTextChangedListener(generalTextWatcher);
        Ed_PetrolFuel2.addTextChangedListener(generalTextWatcher);
        Ed_Telephone2.addTextChangedListener(generalTextWatcher);
        Ed_PostageCourier2.addTextChangedListener(generalTextWatcher);
        Ed_ConveyTravel2.addTextChangedListener(generalTextWatcher);
        Ed_MiscelExpense2.addTextChangedListener(generalTextWatcher);
        Ed_FoodBeverage2.addTextChangedListener(generalTextWatcher);
        Ed_PrintingStationary2.addTextChangedListener(generalTextWatcher);


        info = (Report_localExpenseClass) getIntent().getSerializableExtra("idlocal");
        idLocalExpense = info.getIdLocalExpense();
        Log.d("IDlocalexp", idLocalExpense);
        ClaimMonth = info.getMonthOfClaim();
        Log.d("classmonth", ClaimMonth);


        //Spinner
        final int[] MonthID = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        final String[] Monthlist = {"Select Month", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};


        final ArrayAdapter<String> spinadapter = new ArrayAdapter<String>(Report_Edit.this, android.R.layout.simple_spinner_item, Monthlist);
        spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MonthSpinner.setAdapter(spinadapter);

        MonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Calendar cal = Calendar.getInstance();
                currentMonth = cal.get(Calendar.MONTH) + 2;
                Log.d("currentmonth", String.valueOf(currentMonth));

                Month_Claim = MonthSpinner.getSelectedItem().toString();
                Log.d("totalmonths", Month_Claim);

                int index = MonthSpinner.getSelectedItemPosition();
                Monthid = MonthID[index];


                if (Monthid != 0) {
                    months = String.valueOf(Monthid);
                    Log.d("MONTHID", String.valueOf(Monthid));
                    Log.d("selected", months);
                }
                if (Monthid == 0) {
                    ((TextView) view).setText(ClaimMonth);
                }
                if (MonthID[index] > currentMonth) {

                    ((TextView) view).setVisibility(View.GONE);

                    String Title1 = "Select Current Month Or Past Months";
                    String Message = "To Add Local Expenses";
                    alert1(Title1, Message);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Description = Ed_ExpenseDescription.getText().toString();

                //Withbill
                BoardLodge = ed_BoardLodge.getText().toString();
                PetrolFuel = ed_PetrolFuel.getText().toString();
                Telephone_Datacharge = ed_Telephone.getText().toString();
                PostCourier = ed_PostageCourier.getText().toString();
                ConveyTravel = ed_ConveyTravel.getText().toString();
                MiscelExpense = ed_MiscelExpense.getText().toString();
                FoodBeverage = ed_FoodBeverage.getText().toString();
                PrintingStationary = ed_PrintingStationary.getText().toString();
                Remarks = Ed_Remarks.getText().toString();

                //WithoutBill
                BoardLodge2 = Ed_BoardLodge2.getText().toString();
                PetrolFuel2 = Ed_PetrolFuel2.getText().toString();
                Telephone_Datacharge2 = Ed_Telephone2.getText().toString();
                PostCourier2 = Ed_PostageCourier2.getText().toString();
                ConveyTravel2 = Ed_ConveyTravel2.getText().toString();
                MiscelExpense2 = Ed_MiscelExpense2.getText().toString();
                FoodBeverage2 = Ed_FoodBeverage2.getText().toString();
                PrintingStationary2 = Ed_PrintingStationary2.getText().toString();
                RemarksWithoutBill = Ed_Remarks2.getText().toString();
////

                tot_expense = Txt_TotalLocalExpenses.getText().toString();
                Month_Claim = MonthSpinner.getSelectedItem().toString();
                Log.d("Choose Month", Month_Claim);

                String Title = "Alert!";

//                Local_Expenses();
                    if (Month_Claim.equals(months)) {
                        Log.d("Monthselect", months);
                        String Months = "Select Your Month";
                        alert1(Title, Months);
                    } else if (Description.equals("")) {
                        String expense = "Enter Expense Description";
                        alert1(Title, expense);
                    }
                    else if (tot_expense.equals("0")|| tot_expense.equals("0.00")) {
                        String total = "Add some Local ExpensesItem";
                        alert1(Title, total);
                    }
                    else if (ed_MiscelExpense.getText().toString().length() > 0 && !ed_MiscelExpense.getText().toString().equals("0.00") && Remarks.isEmpty()) {
                        String withbill = "Enter Remarks for Miscellenous Expense With Bill";
                        alert1(Title, withbill);

                    } else if (Ed_MiscelExpense2.getText().toString().length() > 0 && !Ed_MiscelExpense2.getText().toString().equals("0.00")   && RemarksWithoutBill.isEmpty()) {

                        String withoutbill = "Enter Remarks for Miscellenous Expense Without Bill";
                        alert1(Title, withoutbill);

                    } else {
                        if (ed_BoardLodge.getText().toString().isEmpty()) {
                            ed_BoardLodge.setText("0");
                        }else {
                             BoardLodge= ed_BoardLodge.getText().toString();
                        }
                        if ( ed_PetrolFuel.getText().toString().isEmpty()) {
                            ed_PetrolFuel.setText("0");
                        }else {
                            PetrolFuel = ed_PetrolFuel.getText().toString();
                        }
                        if ( ed_Telephone.getText().toString().isEmpty()) {
                            ed_Telephone.setText("0");
                        }
                        else {
                            Telephone_Datacharge = ed_Telephone.getText().toString();
                        }
                        if(ed_PostageCourier.getText().toString().isEmpty()){
                            ed_PostageCourier.setText("0");
                        } else {
                            PostCourier = ed_PostageCourier.getText().toString();
                        }
                        if(ed_ConveyTravel.getText().toString().isEmpty()){
                            ed_ConveyTravel.setText("0");
                        }else {
                            ConveyTravel = ed_ConveyTravel.getText().toString();
                        }
                        if(ed_MiscelExpense.getText().toString().isEmpty()){
                            ed_MiscelExpense.setText("0");
                        }else {
                            MiscelExpense = ed_MiscelExpense.getText().toString();
                        }
                        if( ed_FoodBeverage.getText().toString().isEmpty()){
                            ed_FoodBeverage.setText("0");
                        }else {
                            FoodBeverage = ed_FoodBeverage.getText().toString();
                        }
                        if( ed_PrintingStationary.getText().toString().isEmpty()){
                            ed_PrintingStationary.setText("0");
                        }else {
                            PrintingStationary = ed_PrintingStationary.getText().toString();
                        }

                        //Withoutbill
                        if (Ed_BoardLodge2.getText().toString().isEmpty()) {
                            Ed_BoardLodge2.setText("0");
                        }else {
                            BoardLodge2 = Ed_BoardLodge2.getText().toString();
                        }
                        if ( Ed_PetrolFuel2.getText().toString().isEmpty()) {
                            Ed_PetrolFuel2.setText("0");
                        }else {
                            PetrolFuel2 = Ed_PetrolFuel2.getText().toString();
                        }
                        if ( Ed_Telephone2.getText().toString().isEmpty()) {
                            Ed_Telephone2.setText("0");
                        }else {
                            Telephone_Datacharge2 = Ed_Telephone2.getText().toString();
                        }
                        if(Ed_PostageCourier2.getText().toString().isEmpty()){
                            Ed_PostageCourier2.setText("0");
                        }else {
                            PostCourier2 = Ed_PostageCourier2.getText().toString();
                        }
                        if(Ed_ConveyTravel2.getText().toString().isEmpty()){
                            Ed_ConveyTravel2.setText("0");
                        }else {
                            ConveyTravel2 = Ed_ConveyTravel2.getText().toString();
                        }
                        if(Ed_MiscelExpense2.getText().toString().isEmpty()){
                            Ed_MiscelExpense2.setText("0");
                        }else {
                            MiscelExpense2 = Ed_MiscelExpense2.getText().toString();
                        }
                        if( Ed_FoodBeverage2.getText().toString().isEmpty()){
                            Ed_FoodBeverage2.setText("0");
                        }else {
                            FoodBeverage2 = Ed_FoodBeverage2.getText().toString();
                        }
                        if( Ed_PrintingStationary2.getText().toString().isEmpty()){
                            Ed_PrintingStationary2.setText("0");
                        }else {
                            PrintingStationary2 = Ed_PrintingStationary2.getText().toString();
                        }


                        Local_Expenses();
                    }


//                clearText();


            }


        });

        GetReport_Edit();
        GetDetails();


//        Edit_LocalExpenseTotal();

    }
    private TextWatcher generalTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.d("beforeTextChanged:", String.valueOf(s));
            totaltourexpenses(String.valueOf(s));
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d("onTextChanged:", String.valueOf(s));
            totaltourexpenses(String.valueOf(s));
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.d("afterTextChanged:", String.valueOf(s));
            totaltourexpenses(String.valueOf(s));
        }
    };
    private void totaltourexpenses(String s) {
        float board, buisness, conveyance, food, fuel, postage, printing, travel, misc;
        float board1, buisness1, conveyance1, food1, fuel1, postage1, printing1, travel1, misc1;
        //Withbill
        BoardLodge = ed_BoardLodge.getText().toString();
        PetrolFuel = ed_PetrolFuel.getText().toString();
        Telephone_Datacharge = ed_Telephone.getText().toString();
        PostCourier = ed_PostageCourier.getText().toString();
        ConveyTravel = ed_ConveyTravel.getText().toString();
        MiscelExpense = ed_MiscelExpense.getText().toString();
        FoodBeverage = ed_FoodBeverage.getText().toString();
        PrintingStationary = ed_PrintingStationary.getText().toString();
        Remarks = Ed_Remarks.getText().toString();

        //WithoutBill
        BoardLodge2 = Ed_BoardLodge2.getText().toString();
        PetrolFuel2 = Ed_PetrolFuel2.getText().toString();
        Telephone_Datacharge2 = Ed_Telephone2.getText().toString();
        PostCourier2 = Ed_PostageCourier2.getText().toString();
        ConveyTravel2 = Ed_ConveyTravel2.getText().toString();
        MiscelExpense2 = Ed_MiscelExpense2.getText().toString();
        FoodBeverage2 = Ed_FoodBeverage2.getText().toString();
        PrintingStationary2 = Ed_PrintingStationary2.getText().toString();
        RemarksWithoutBill = Ed_Remarks2.getText().toString();


        if (BoardLodge.length() > 0) {
            board = Float.parseFloat(BoardLodge);
        } else {
            board = 0;
        }

        if (PetrolFuel.length() > 0) {
            buisness = Float.parseFloat(PetrolFuel);
        } else {
            buisness = 0;
        }
        if (Telephone_Datacharge.length() > 0) {
            conveyance = Float.parseFloat(Telephone_Datacharge);
        } else {
            conveyance = 0;
        }
        if (PostCourier.length() > 0) {
            food = Float.parseFloat(PostCourier);
        } else {
            food = 0;
        }
        if (ConveyTravel.length() > 0) {
            fuel = Float.parseFloat(ConveyTravel);
        } else {
            fuel = 0;
        }
        if (MiscelExpense.length() > 0) {
            postage = Float.parseFloat(MiscelExpense);
        } else {
            postage = 0;
        }
        if (FoodBeverage.length() > 0) {
            printing = Float.parseFloat(FoodBeverage);
        } else {
            printing = 0;
        }
        if (PrintingStationary.length() > 0) {
            travel = Float.parseFloat(PrintingStationary);
        } else {
            travel = 0;
        }
        if (BoardLodge2.length() > 0) {
            misc = Float.parseFloat(BoardLodge2);
        } else {
            misc = 0;
        }
        if (PetrolFuel2.length() > 0) {
            board1 = Float.parseFloat(PetrolFuel2);
        } else {
            board1 = 0;
        }

        if (Telephone_Datacharge2.length() > 0) {
            buisness1 = Float.parseFloat(Telephone_Datacharge2);
        } else {
            buisness1 = 0;
        }
        if (PostCourier2.length() > 0) {
            conveyance1 = Float.parseFloat(PostCourier2);
        } else {
            conveyance1 = 0;
        }
        if (ConveyTravel2.length() > 0) {
            food1 = Float.parseFloat(ConveyTravel2);
        } else {
            food1 = 0;
        }
        if (MiscelExpense2.length() > 0) {
            fuel1 = Float.parseFloat(MiscelExpense2);
        } else {
            fuel1 = 0;
        }
        if (FoodBeverage2.length() > 0) {
            postage1 = Float.parseFloat(FoodBeverage2);
        } else {
            postage1 = 0;
        }
        if (PrintingStationary2.length() > 0) {
            printing1 = Float.parseFloat(PrintingStationary2);
        } else {
            printing1 = 0;
        }

        float totaltourexpenses = (board + buisness + conveyance + food + fuel + postage + printing + travel + misc +board1 + buisness1 + conveyance1 + food1 + fuel1 + postage1 + printing1 );
        float withBill = (board + buisness + conveyance + food + fuel + postage + printing + travel + misc );
        float withoutBill = (board1 + buisness1 + conveyance1 + food1 + fuel1 + postage1 + printing1 );
        Txt_TotalLocalExpenses.setText(String.valueOf(totaltourexpenses));

        lblWithBill.setText(String.valueOf(withBill));
        lblWithoutBill.setText(String.valueOf(withoutBill));
    }



    private void GetReport_Edit() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setTitle(" Loading");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);

        Call<JsonArray> call = apiService.GetReport_Edit(idLocalExpense);
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
                                    months=jsobj.getString("monthOfClaim");

                                    Description = jsobj.getString("Description");
                                    Ed_ExpenseDescription.setText(Description);

                                    Remarks = jsobj.getString("Remarks");
                                    Ed_Remarks.setText(Remarks);

                                    RemarksWithoutBill = jsobj.getString("RemarksWithoutBill");
                                    Ed_Remarks2.setText(RemarksWithoutBill);

                                    tot_expense = jsobj.getString("TotalLocalExpense");
//                                    Txt_TotalLocalExpenses.setText(tot_expense);

                                    Log.d("total_response",tot_expense);
                                }
                                Log.d("selected", months);
                            }

                        } else {
                            Toast.makeText(Report_Edit.this, "No Records has found", Toast.LENGTH_SHORT).show();
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



    public void GetDetails() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setTitle(" Loading");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        Log.d("parameter", idLocalExpense);
        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);


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
                                ed_BoardLodge = (EditText) findViewById(R.id.ed_BoradLodge1);
                                ed_PetrolFuel = (EditText) findViewById(R.id.ed_petrolFuel1);
                                ed_Telephone = (EditText) findViewById(R.id.ed_Telephone1);
                                ed_PostageCourier = (EditText) findViewById(R.id.ed_postageCourier1);
                                ed_ConveyTravel = (EditText) findViewById(R.id.ed_conveytravel1);
                                ed_MiscelExpense = (EditText) findViewById(R.id.ed_MiscellExpense1);
                                ed_FoodBeverage = (EditText) findViewById(R.id.ed_food_Beverages1);
                                ed_PrintingStationary = (EditText) findViewById(R.id.ed_printSationary1);
                                Ed_Remarks = (EditText) findViewById(R.id.ed_Remarks1);

                                //withoutbill
                                Ed_BoardLodge2 = (EditText) findViewById(R.id.ed_BoradLodge_2);
                                Ed_PetrolFuel2 = (EditText) findViewById(R.id.ed_petrolFuel_2);
                                Ed_Telephone2 = (EditText) findViewById(R.id.ed_Telephone_2);
                                Ed_PostageCourier2 = (EditText) findViewById(R.id.ed_postageCourier_2);
                                Ed_ConveyTravel2 = (EditText) findViewById(R.id.ed_conveytravel_2);
                                Ed_MiscelExpense2 = (EditText) findViewById(R.id.ed_MiscellExpense_2);
                                Ed_FoodBeverage2 = (EditText) findViewById(R.id.ed_food_Beverages_2);
                                Ed_PrintingStationary2 = (EditText) findViewById(R.id.ed_printSationary_2);
                                Ed_Remarks2 = (EditText) findViewById(R.id.ed_Remarks_2);


                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsobj = array.getJSONObject(i);

                                    Board = jsobj.getString("BoardLodge");
                                    ed_BoardLodge.setText(Board);

                                    Convey_Trav = jsobj.getString("ConvTravel");
                                    ed_ConveyTravel.setText(Convey_Trav);

                                    Food = jsobj.getString("Food");
                                    ed_FoodBeverage.setText(Food);

                                    Petrol = jsobj.getString("Fuel");
                                    ed_PetrolFuel.setText(Petrol);

                                    Postage = jsobj.getString("PostageCourier");
                                    ed_PostageCourier.setText(Postage);

                                    Printing = jsobj.getString("Printing");
                                    ed_PrintingStationary.setText(Printing);

                                    Telephone = jsobj.getString("Telephone");
                                    ed_Telephone.setText(Telephone);

                                    Misc_exp = jsobj.getString("Misc");
                                    ed_MiscelExpense.setText(Misc_exp);
                                    //withbill

                                    Board_2 = jsobj.getString("BoardLodgeWithoutBill");
                                    Ed_BoardLodge2.setText(Board_2);

                                    Convey_Trav_2 = jsobj.getString("ConvTravelWithoutBill");
                                    Ed_ConveyTravel2.setText(Convey_Trav_2);

                                    Food_2 = jsobj.getString("FoodWithoutBill");
                                    Ed_FoodBeverage2.setText(Food_2);

                                    Petrol_2 = jsobj.getString("FuelWithoutBill");
                                    Ed_PetrolFuel2.setText(Petrol_2);

                                    Postage_2 = jsobj.getString("PostageCourierWithoutBill");
                                    Ed_PostageCourier2.setText(Postage_2);

                                    Printing_2 = jsobj.getString("PrintingWithoutBill");
                                    Ed_PrintingStationary2.setText(Printing_2);

                                    Telephone_2= jsobj.getString("TelephoneWithoutBill");
                                    Ed_Telephone2.setText(Telephone_2);

                                    Misc_exp_2 = jsobj.getString("MiscWithoutBill");
                                    Ed_MiscelExpense2.setText(Misc_exp_2);

                                }
                            }

                        } else {
//                            full_layout.setVisibility(View.GONE);

                            Toast.makeText(Report_Edit.this, "No Records has found", Toast.LENGTH_SHORT).show();
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

    private void Local_Expenses() {


        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setTitle("Loading");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);

        final JsonObject object = new JsonObject();
        object.addProperty("idLocalExpense", idLocalExpense);
        Log.d("editidlocal", idLocalExpense);
        object.addProperty("idUser", iduser);

        object.addProperty("monthOfClaim", String.valueOf(months));
        Log.d("monthid", String.valueOf(months));

        object.addProperty("RefId", "");
        object.addProperty("TotalLocalExpense", tot_expense);
        Log.d("TotalExpense", String.valueOf(tot_expense));
        object.addProperty("Description", "Monthly Expense from App");
        object.addProperty("processBy", iduser);
        object.addProperty("processType", "editLocal");
        object.addProperty("IsApproved", 0);
        object.addProperty("IsPaid", 0);
        object.addProperty("Remarks", Remarks);
        object.addProperty("RemarksWithoutBill", RemarksWithoutBill);
//Withbill
        JsonArray LocalExpenseItems = new JsonArray();
        Edit_localexp_1 list = new Edit_localexp_1(BoardLodge, ConveyTravel, FoodBeverage, PetrolFuel, PostCourier, PrintingStationary, Telephone_Datacharge, MiscelExpense);
        LocalExpensesItemlist.add(list);
        Log.d("list_1_size", String.valueOf((LocalExpensesItemlist.size())));
        for (int i = 0; i < LocalExpensesItemlist.size(); i++) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("BoardLodge", LocalExpensesItemlist.get(i).getBoardLodge());
//            Log.d("boardlodge", String.valueOf(LocalExpensesItemlist.get(i).getBoardLodge()));
            jsonObject.addProperty("ConvTravel", LocalExpensesItemlist.get(i).getConvTravel());
            jsonObject.addProperty("Food", LocalExpensesItemlist.get(i).getFood());
            jsonObject.addProperty("Fuel", LocalExpensesItemlist.get(i).getFuel());
            jsonObject.addProperty("PostageCourier", LocalExpensesItemlist.get(i).getPostageCourier());
            jsonObject.addProperty("Printing", LocalExpensesItemlist.get(i).getPrinting());
            jsonObject.addProperty("Telephone", LocalExpensesItemlist.get(i).getTelephone());
            jsonObject.addProperty("Misc", LocalExpensesItemlist.get(i).getMisc());

            LocalExpenseItems.add(jsonObject);
            Log.d("list 1", jsonObject.toString());
        }

//WithoutBill
        JsonArray LocalExpenseItemsWithoutBill = new JsonArray();
        Edit_localexpense_2 list2 = new Edit_localexpense_2(BoardLodge2, ConveyTravel2, FoodBeverage2,  PetrolFuel2, PostCourier2, PrintingStationary2,  Telephone_Datacharge2,  MiscelExpense2);
        LocalExpensesItemlist2.add(list2);
        Log.d("list_2_size", String.valueOf((LocalExpensesItemlist2.size())));
        for (int j = 0; j < LocalExpensesItemlist2.size(); j++) {

            JsonObject jsonObject1 = new JsonObject();
            jsonObject1.addProperty("BoardLodge", LocalExpensesItemlist2.get(j).getBoardLodge());
            Log.d("boardlodge", String.valueOf(LocalExpensesItemlist.get(j).getBoardLodge()));

            jsonObject1.addProperty("ConvTravel", LocalExpensesItemlist2.get(j).getConvTravel());
            Log.d("conveytravel", String.valueOf(LocalExpensesItemlist.get(j).getConvTravel()));

            jsonObject1.addProperty("Food", LocalExpensesItemlist2.get(j).getFood());
            jsonObject1.addProperty("Fuel", LocalExpensesItemlist2.get(j).getFuel());
            jsonObject1.addProperty("PostageCourier", LocalExpensesItemlist2.get(j).getPostageCourier());
            jsonObject1.addProperty("Printing", LocalExpensesItemlist2.get(j).getPrinting());
            jsonObject1.addProperty("Telephone", LocalExpensesItemlist2.get(j).getTelephone());
            jsonObject1.addProperty("Misc", LocalExpensesItemlist2.get(j).getMisc());

            LocalExpenseItemsWithoutBill.add(jsonObject1);
            Log.d("Arraylist", jsonObject1.toString());
        }

        object.add("LocalExpenseItems", LocalExpenseItems);
        object.add("LocalExpenseItemsWithoutBill", LocalExpenseItemsWithoutBill);

        Log.d("REQUEST", String.valueOf(object));


        Call<JsonArray> call = apiService.Edit_Local_Expenses(object);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                try {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    Log.d("URL", String.valueOf(response.code()));
                    Log.d("customer:code-res", response.code() + " - " + response.toString());

                    if (response.code() == 200 || response.code() == 201) {
                        Log.d("response", response.body().toString());

                        LocalExpensesItemlist.clear();
                        LocalExpensesItemlist2.clear();
                        JSONArray array = new JSONArray(response.body().toString());
                        if (array.length() > 0) {

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object1 = array.getJSONObject(i);

                                String strMsg = object1.getString("resultMessage");
                                String MailMsg = object1.getString("MailMsg");
                                String WebUrl = object1.getString("WebUrl");
                                String result = object1.getString("result");


                                if (Integer.parseInt(result) == 1) {
                                    Result_alert(strMsg, WebUrl, result);
                                } else {
                                    Result_alert(strMsg, WebUrl, result);
                                }

                            }
                        } else {
                            alert("No Records has found");
//                            Toast.makeText(Report_Edit.this, "No Records has found", Toast.LENGTH_SHORT).show();
                        }
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




            }
        });

    }

    private void Result_alert(String strMsg, String WebUrl, String result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Report_Edit.this);
        builder.setTitle(strMsg);
        builder.setMessage(WebUrl);
        builder.setCancelable(false);


        if (Integer.parseInt(result) == 1) {
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   finish();
                }
            });

            builder.show();
        } else {
            builder.setTitle(strMsg);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }

    }


    private void alert1(String title, String month) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Report_Edit.this);

        builder.setTitle(title);
        builder.setMessage(month);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();

    }
    private void alert(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Report_Edit.this);
//        builder.setTitle(title);
        builder.setMessage(title);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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
