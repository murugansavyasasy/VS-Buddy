package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.LocalExpenseItems;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.LocalExpenseItems_2;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;

public class LocalConveyance extends AppCompatActivity {
    private static final String TAG = LocalConveyance.class.getSimpleName();
    EditText Ed_ExpenseDescription, Ed_BoardLodge, Ed_PetrolFuel, ed_telephone1, Ed_ConveyTravel, Ed_PostageCourier, Ed_MiscelExpense, Ed_FoodBeverage, Ed_PrintingStationary, Ed_Remarks;
    EditText Ed_BoardLodge2, Ed_PetrolFuel2, ed_telephone2, Ed_ConveyTravel2, Ed_PostageCourier2, Ed_MiscelExpense2, Ed_FoodBeverage2, Ed_PrintingStationary2, Ed_Remarks2;
    TextView lblWithBill,
            lblWithoutBill;
    TextView Txt_TotalLocalExpenses;
//    String idlocalExpense;

    String str_ExpenseDescript = "", str_BoardLodge, str_PetrolFuel, str_Telephone_Datacharge, str_ConveyTravel, str_PostCourier, str_MiscelExpense, str_FoodBeverage, str_PrintingStationary, str_Remarks, str_TotalLocalExpenses;
    String str_BoardLodge2, str_PetrolFuel2, str_Telephone_Datacharge2, str_ConveyTravel2, str_PostCourier2, str_MiscelExpense2, str_FoodBeverage2, str_PrintingStationary2, str_Remarks2;
    Button Submit;

    long idLocalExpense = 0;
    Spinner MonthSpinner;
    String iduser;
    int currentMonth;
    String ClaimMonth;
    int Monthid;
    public List<LocalExpenseItems> LocalExpensesItemlist = new ArrayList<>();
    public List<LocalExpenseItems_2> LocalExpensesItemlist2 = new ArrayList<>();

    Float currentExpence = 0f;
    Float withBillTotal = 0f;
    Float withotBillTotal = 0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_conveyance);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);
//withbill
        Ed_ExpenseDescription = (EditText) findViewById(R.id.ed_ExpnseDescription);
        Ed_BoardLodge = (EditText) findViewById(R.id.ed_BoradLodge);
        Ed_PetrolFuel = (EditText) findViewById(R.id.ed_petrolFuel);
        ed_telephone1 = (EditText) findViewById(R.id.ed_telephone1);
        Ed_PostageCourier = (EditText) findViewById(R.id.ed_postageCourier);
        Ed_ConveyTravel = (EditText) findViewById(R.id.ed_conveytravel);
        Ed_MiscelExpense = (EditText) findViewById(R.id.ed_MiscellExpense);
        Ed_FoodBeverage = (EditText) findViewById(R.id.ed_food_Beverages);
        Ed_PrintingStationary = (EditText) findViewById(R.id.ed_printSationary);
        Ed_Remarks = (EditText) findViewById(R.id.ed_Remarks);
        lblWithBill = (TextView) findViewById(R.id.lblWithBill);
        lblWithoutBill = (TextView) findViewById(R.id.lblWithoutBill);

//withoutbill
        Ed_BoardLodge2 = (EditText) findViewById(R.id.ed_BoradLodge2);
        Ed_PetrolFuel2 = (EditText) findViewById(R.id.ed_petrolFuel2);
        ed_telephone2 = (EditText) findViewById(R.id.ed_telephone2);
        Ed_PostageCourier2 = (EditText) findViewById(R.id.ed_postageCourier2);
        Ed_ConveyTravel2 = (EditText) findViewById(R.id.ed_conveytravel2);
        Ed_MiscelExpense2 = (EditText) findViewById(R.id.ed_MiscellExpense2);
        Ed_FoodBeverage2 = (EditText) findViewById(R.id.ed_food_Beverages2);
        Ed_PrintingStationary2 = (EditText) findViewById(R.id.ed_printSationary2);
        Ed_Remarks2 = (EditText) findViewById(R.id.ed_Remarks2);

        Txt_TotalLocalExpenses = (TextView) findViewById(R.id.txt_TotalLocal_expense);

        Submit = (Button) findViewById(R.id.btn_Submit);

        iduser = SharedPreference_class.getUserid(LocalConveyance.this);
        Log.d("userid", iduser);

        MonthSpinner = (Spinner) findViewById(R.id.spin_Month);
//        iduser = Integer.parseInt(SharedPreference_class.getUserid(LocalConveyance.this));
//        Log.d("iduser", String.valueOf(iduser));

        final int[] MonthID = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        final String[] Month = {"Select Month", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        ArrayAdapter<String> adapterfinancialyear = new ArrayAdapter<String>(LocalConveyance.this, android.R.layout.simple_spinner_item, Month);
        adapterfinancialyear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MonthSpinner.setAdapter(adapterfinancialyear);
        MonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Calendar cal = Calendar.getInstance();
                currentMonth = cal.get(Calendar.MONTH) + 2;
                Log.d("currentmonth", String.valueOf(currentMonth));

                int index = MonthSpinner.getSelectedItemPosition();
                Monthid = MonthID[index];
                ClaimMonth = MonthSpinner.getSelectedItem().toString();


                if (MonthID[index] > currentMonth) {
                    ((TextView) view).setVisibility(View.GONE);
                    String Title1 = "Select Current Month Or Past Months";
                    String Message = "To Add Local Expenses";

                    alert1(Title1, Message);

                } else {

                    ClaimMonth = MonthSpinner.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_ExpenseDescript = Ed_ExpenseDescription.getText().toString();

                //Withbill
                str_BoardLodge = Ed_BoardLodge.getText().toString();
                str_PetrolFuel = Ed_PetrolFuel.getText().toString();
                str_Telephone_Datacharge = ed_telephone1.getText().toString();
                str_PostCourier = Ed_PostageCourier.getText().toString();
                str_ConveyTravel = Ed_ConveyTravel.getText().toString();
                str_MiscelExpense = Ed_MiscelExpense.getText().toString();
                str_FoodBeverage = Ed_FoodBeverage.getText().toString();
                str_PrintingStationary = Ed_PrintingStationary.getText().toString();
                str_Remarks = Ed_Remarks.getText().toString();

                //WithoutBill
                str_BoardLodge2 = Ed_BoardLodge2.getText().toString();
                str_PetrolFuel2 = Ed_PetrolFuel2.getText().toString();
                str_Telephone_Datacharge2 = ed_telephone2.getText().toString();
                str_PostCourier2 = Ed_PostageCourier2.getText().toString();
                str_ConveyTravel2 = Ed_ConveyTravel2.getText().toString();
                str_MiscelExpense2 = Ed_MiscelExpense2.getText().toString();
                str_FoodBeverage2 = Ed_FoodBeverage2.getText().toString();
                str_PrintingStationary2 = Ed_PrintingStationary2.getText().toString();
                str_Remarks2 = Ed_Remarks2.getText().toString();


                str_TotalLocalExpenses = Txt_TotalLocalExpenses.getText().toString();
                ClaimMonth = MonthSpinner.getSelectedItem().toString();
                Log.d("Choose Month", ClaimMonth);

                String Title = "Alert!";

//                if (ClaimMonth.equals("Select Month") || str_ExpenseDescript.equals("") || str_TotalLocalExpenses.equals("0") || !str_MiscelExpense.equals("") || !str_MiscelExpense2.equals("")) {
                if (ClaimMonth.equals("Select Month")) {
                    Log.d("Monthselect", ClaimMonth);
                    String Months = "Select Your Month";
                    alert1(Title, Months);
                }

//                else if (str_ExpenseDescript.equals("")) {
//
//                    String expense = "Enter Expense Description";
//                    alert1(Title, expense);
//
//                }

                else if (str_TotalLocalExpenses.equals("0")) {

                    String total = "Add some Local ExpensesItem";
                    alert1(Title, total);

                } else if (str_MiscelExpense.length() > 0 && str_Remarks.equals("")) {

                    String withbill = "Enter Remarks for Miscellaneous Expense With Bill";
                    alert1(Title, withbill);

                } else if (str_MiscelExpense2.length() > 0 && str_Remarks2.equals("")) {

                    String withoutbill = "Enter Remarks for Miscellaneous Expense Without Bill";
                    alert1(Title, withoutbill);


                } else {
                    if (Ed_BoardLodge.getText().toString().isEmpty()) {
                        Ed_BoardLodge.setText("0");
                    } else {
                        str_BoardLodge = Ed_BoardLodge.getText().toString();
                    }
                    if (Ed_PetrolFuel.getText().toString().isEmpty()) {
                        Ed_PetrolFuel.setText("0");
                    } else {
                        str_PetrolFuel = Ed_PetrolFuel.getText().toString();
                    }
                    if (ed_telephone1.getText().toString().isEmpty()) {
                        ed_telephone1.setText("0");
                    } else {
                        str_Telephone_Datacharge = ed_telephone1.getText().toString();
                    }
                    if (Ed_PostageCourier.getText().toString().isEmpty()) {
                        Ed_PostageCourier.setText("0");
                    } else {
                        str_PostCourier = Ed_PostageCourier.getText().toString();
                    }
                    if (Ed_ConveyTravel.getText().toString().isEmpty()) {
                        Ed_ConveyTravel.setText("0");
                    } else {
                        str_ConveyTravel = Ed_ConveyTravel.getText().toString();
                    }
                    if (Ed_MiscelExpense.getText().toString().isEmpty()) {
                        Ed_MiscelExpense.setText("0");
                    } else {
                        str_MiscelExpense = Ed_MiscelExpense.getText().toString();
                    }
                    if (Ed_FoodBeverage.getText().toString().isEmpty()) {
                        Ed_FoodBeverage.setText("0");
                    } else {
                        str_FoodBeverage = Ed_FoodBeverage.getText().toString();
                    }
                    if (Ed_PrintingStationary.getText().toString().isEmpty()) {
                        Ed_PrintingStationary.setText("0");
                    } else {
                        str_PrintingStationary = Ed_PrintingStationary.getText().toString();
                    }

                    //Withoutbill
                    if (Ed_BoardLodge2.getText().toString().isEmpty()) {
                        Ed_BoardLodge2.setText("0");
                    } else {
                        str_BoardLodge2 = Ed_BoardLodge2.getText().toString();
                    }
                    if (Ed_PetrolFuel2.getText().toString().isEmpty()) {
                        Ed_PetrolFuel2.setText("0");
                    } else {
                        str_PetrolFuel2 = Ed_PetrolFuel2.getText().toString();
                    }
                    if (ed_telephone2.getText().toString().isEmpty()) {
                        ed_telephone2.setText("0");
                    } else {
                        str_Telephone_Datacharge2 = ed_telephone2.getText().toString();
                    }
                    if (Ed_PostageCourier2.getText().toString().isEmpty()) {
                        Ed_PostageCourier2.setText("0");
                    } else {
                        str_PostCourier2 = Ed_PostageCourier2.getText().toString();
                    }
                    if (Ed_ConveyTravel2.getText().toString().isEmpty()) {
                        Ed_ConveyTravel2.setText("0");
                    } else {
                        str_ConveyTravel2 = Ed_ConveyTravel2.getText().toString();
                    }
                    if (Ed_MiscelExpense2.getText().toString().isEmpty()) {
                        Ed_MiscelExpense2.setText("0");
                    } else {
                        str_MiscelExpense2 = Ed_MiscelExpense2.getText().toString();
                    }
                    if (Ed_FoodBeverage2.getText().toString().isEmpty()) {
                        Ed_FoodBeverage2.setText("0");
                    } else {
                        str_FoodBeverage2 = Ed_FoodBeverage2.getText().toString();
                    }
                    if (Ed_PrintingStationary2.getText().toString().isEmpty()) {
                        Ed_PrintingStationary2.setText("0");
                    } else {
                        str_PrintingStationary2 = Ed_PrintingStationary2.getText().toString();
                    }


                    Local_Expenses();
                }

            }
        });
        ListLocalItes();
    }


    //    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Log.d("onback","onbackpress");
//        finish();
//    }
    private void cleartext() {

//WithBill

        MonthSpinner.setSelection(0);

        Ed_ExpenseDescription.setText("");
        Ed_BoardLodge.setText("");
        Ed_PetrolFuel.setText("");
        ed_telephone1.setText("");
        Ed_ConveyTravel.setText("");
        Ed_PostageCourier.setText("");
        Ed_MiscelExpense.setText("");
        Ed_FoodBeverage.setText("");
        Ed_PrintingStationary.setText("");
        Ed_Remarks.setText("");
        //WithoutBill
        Ed_BoardLodge2.setText("");
        Ed_PetrolFuel2.setText("");
        ed_telephone2.setText("");
        Ed_ConveyTravel2.setText("");
        Ed_PostageCourier2.setText("");
        Ed_PostageCourier2.setText("");
        Ed_MiscelExpense2.setText("");
        Ed_FoodBeverage2.setText("");
        Ed_PrintingStationary2.setText("");
        Ed_Remarks2.setText("");

    }
//With Bill

    public void ListLocalItes() {

        Ed_BoardLodge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.d("before", String.valueOf(s));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d("ontext", String.valueOf(s));
                totalAddNumbers(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
//                Log.d("aftertext", String.valueOf(s));
                totalAddNumbers(String.valueOf(s));


            }
        });

        Ed_ConveyTravel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d("ontext1", String.valueOf(s));
                totalAddNumbers(String.valueOf(s));
            }

            @Override

            public void afterTextChanged(Editable s) {
                totalAddNumbers(String.valueOf(s));


            }
        });

        Ed_FoodBeverage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("before1", String.valueOf(after));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d("ontext1", String.valueOf(before));
                totalAddNumbers(String.valueOf(s));

            }

            @Override
            public void afterTextChanged(Editable s) {
                totalAddNumbers(String.valueOf(s));
            }
        });

        Ed_PetrolFuel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                totalAddNumbers(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
                totalAddNumbers(String.valueOf(s));

            }
        });
        Ed_PostageCourier.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                totalAddNumbers(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
                totalAddNumbers(String.valueOf(s));

            }
        });
        Ed_PrintingStationary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Txt_TotalLocalExpenses.setText(new String(s.toString()));

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                totalAddNumbers(String.valueOf(s));

            }

            @Override
            public void afterTextChanged(Editable s) {
                totalAddNumbers(String.valueOf(s));

            }
        });
        ed_telephone1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                totalAddNumbers(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
                totalAddNumbers(String.valueOf(s));

            }
        });


        Ed_MiscelExpense.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                totalAddNumbers(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

//Without Bill
        Ed_BoardLodge2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("before", String.valueOf(s));

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.d("ontext", String.valueOf(s));
                totalAddNumbers(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
                totalAddNumbers(String.valueOf(s));


            }
        });

        Ed_ConveyTravel2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("ontext1", String.valueOf(s));

                totalAddNumbers(String.valueOf(s));
            }

            @Override

            public void afterTextChanged(Editable s) {
                totalAddNumbers(String.valueOf(s));


            }
        });

        Ed_FoodBeverage2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.d("before1", String.valueOf(after));

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d("ontext1", String.valueOf(before));
                totalAddNumbers(String.valueOf(s));

            }

            @Override
            public void afterTextChanged(Editable s) {
                totalAddNumbers(String.valueOf(s));

            }
        });

        Ed_PetrolFuel2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                totalAddNumbers(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
                totalAddNumbers(String.valueOf(s));


            }
        });
        Ed_PostageCourier2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                totalAddNumbers(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

                totalAddNumbers(String.valueOf(s));

            }
        });
        Ed_PrintingStationary2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Txt_TotalLocalExpenses.setText(new String(s.toString()));

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                totalAddNumbers(String.valueOf(s));

            }

            @Override
            public void afterTextChanged(Editable s) {

                totalAddNumbers(String.valueOf(s));

            }
        });
        ed_telephone2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                totalAddNumbers(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        Ed_MiscelExpense2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                totalAddNumbers(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }


    private void totalAddNumbers(String s) {
//With Bill
        str_BoardLodge = Ed_BoardLodge.getText().toString();
//        Log.d("text local1",str_BoardLodge);
        str_PetrolFuel = Ed_PetrolFuel.getText().toString();
//        Log.d("text 2",str_PetrolFuel);

        str_Telephone_Datacharge = ed_telephone1.getText().toString();
        str_PostCourier = Ed_PostageCourier.getText().toString();
        str_ConveyTravel = Ed_ConveyTravel.getText().toString();
        str_MiscelExpense = Ed_MiscelExpense.getText().toString();
        str_FoodBeverage = Ed_FoodBeverage.getText().toString();
        str_PrintingStationary = Ed_PrintingStationary.getText().toString();
//Without Bill
        str_BoardLodge2 = Ed_BoardLodge2.getText().toString();
        str_PetrolFuel2 = Ed_PetrolFuel2.getText().toString();
        str_Telephone_Datacharge2 = ed_telephone2.getText().toString();
        str_PostCourier2 = Ed_PostageCourier2.getText().toString();
        str_ConveyTravel2 = Ed_ConveyTravel2.getText().toString();
        str_MiscelExpense2 = Ed_MiscelExpense2.getText().toString();
        str_FoodBeverage2 = Ed_FoodBeverage2.getText().toString();
        str_PrintingStationary2 = Ed_PrintingStationary2.getText().toString();

        if (!str_BoardLodge.equals("")) {
            str_BoardLodge = Ed_BoardLodge.getText().toString();
//            Log.d("conditon board",str_BoardLodge);
        } else {
            str_BoardLodge = "0";
//            Log.d("else 1",str_BoardLodge);
        }
        if (!str_PetrolFuel.equals("")) {
            str_PetrolFuel = Ed_PetrolFuel.getText().toString();
            Log.d("conditon petrol", str_PetrolFuel);

        } else {
            str_PetrolFuel = "0";
        }
        if (!str_Telephone_Datacharge.equals("")) {
            str_Telephone_Datacharge = ed_telephone1.getText().toString();
        } else {
            str_Telephone_Datacharge = "0";
        }
        if (!str_PostCourier.equals("")) {
            str_PostCourier = Ed_PostageCourier.getText().toString();
        } else {
            str_PostCourier = "0";
        }

        if (!str_ConveyTravel.equals("")) {
            str_ConveyTravel = Ed_ConveyTravel.getText().toString();
        } else {
            str_ConveyTravel = "0";
        }
        if (!str_MiscelExpense.equals("")) {
            str_MiscelExpense = Ed_MiscelExpense.getText().toString();
        } else {
            str_MiscelExpense = "0";
        }

        if (!str_FoodBeverage.equals("")) {
            str_FoodBeverage = Ed_FoodBeverage.getText().toString();
        } else {
            str_FoodBeverage = "0";
        }

        if (!str_PrintingStationary.equals("")) {
            str_PrintingStationary = Ed_PrintingStationary.getText().toString();
        } else {
            str_PrintingStationary = "0";
        }
//WithoutBill
        if (!str_BoardLodge2.equals("")) {
            str_BoardLodge2 = Ed_BoardLodge2.getText().toString();
        } else {
            str_BoardLodge2 = "0";
        }
        if (!str_PetrolFuel2.equals("")) {
            str_PetrolFuel2 = Ed_PetrolFuel2.getText().toString();
        } else {
            str_PetrolFuel2 = "0";
        }
        if (!str_Telephone_Datacharge2.equals("")) {
            str_Telephone_Datacharge2 = ed_telephone2.getText().toString();
        } else {
            str_Telephone_Datacharge2 = "0";
        }

        if (!str_PostCourier2.equals("")) {
            str_PostCourier2 = Ed_PostageCourier2.getText().toString();
        } else {
            str_PostCourier2 = "0";
        }

        if (!str_ConveyTravel2.equals("")) {
            str_ConveyTravel2 = Ed_ConveyTravel2.getText().toString();
        } else {
            str_ConveyTravel2 = "0";
        }
        if (!str_MiscelExpense2.equals("")) {
            str_MiscelExpense2 = Ed_MiscelExpense2.getText().toString();
        } else {
            str_MiscelExpense2 = "0";
        }

        if (!str_FoodBeverage2.equals("")) {
            str_FoodBeverage2 = Ed_FoodBeverage2.getText().toString();
        } else {
            str_FoodBeverage2 = "0";
        }

        if (!str_PrintingStationary2.equals("")) {
            str_PrintingStationary2 = Ed_PrintingStationary2.getText().toString();
        } else {
            str_PrintingStationary2 = "0";
        }
        currentExpence = Float.parseFloat(String.valueOf(str_BoardLodge)) + Float.parseFloat(String.valueOf(str_BoardLodge2)) +
                Float.parseFloat(String.valueOf(str_PetrolFuel)) + Float.parseFloat(String.valueOf(str_PetrolFuel2)) +
                Float.parseFloat(String.valueOf(str_Telephone_Datacharge)) + Float.parseFloat(String.valueOf(str_Telephone_Datacharge2)) +
                Float.parseFloat(String.valueOf(str_PostCourier)) + Float.parseFloat(String.valueOf(str_PostCourier2)) +
                Float.parseFloat(String.valueOf(str_ConveyTravel)) + Float.parseFloat(String.valueOf(str_ConveyTravel2)) +
                Float.parseFloat(String.valueOf(str_MiscelExpense)) + Float.parseFloat(String.valueOf(str_MiscelExpense2)) +
                Float.parseFloat(String.valueOf(str_FoodBeverage)) + Float.parseFloat(String.valueOf(str_FoodBeverage2)) +
                Float.parseFloat(String.valueOf(str_PrintingStationary)) + Float.parseFloat(String.valueOf(str_PrintingStationary2));


        withBillTotal = Float.parseFloat(String.valueOf(str_BoardLodge)) +
                Float.parseFloat(String.valueOf(str_PetrolFuel)) +
                Float.parseFloat(String.valueOf(str_Telephone_Datacharge)) +
                Float.parseFloat(String.valueOf(str_PostCourier)) +
                Float.parseFloat(String.valueOf(str_ConveyTravel)) +
                Float.parseFloat(String.valueOf(str_MiscelExpense)) +
                Float.parseFloat(String.valueOf(str_FoodBeverage)) +
                Float.parseFloat(String.valueOf(str_PrintingStationary));

        withotBillTotal = Float.parseFloat(String.valueOf(str_BoardLodge2)) +
                Float.parseFloat(String.valueOf(str_PetrolFuel2)) +
                Float.parseFloat(String.valueOf(str_Telephone_Datacharge2)) +
                Float.parseFloat(String.valueOf(str_PostCourier2)) +
                Float.parseFloat(String.valueOf(str_ConveyTravel2)) +
                Float.parseFloat(String.valueOf(str_MiscelExpense2)) +
                Float.parseFloat(String.valueOf(str_FoodBeverage2)) +
                Float.parseFloat(String.valueOf(str_PrintingStationary2));


        Txt_TotalLocalExpenses.setText(String.valueOf(currentExpence));
        lblWithBill.setText(String.valueOf(withBillTotal));
        lblWithoutBill.setText(String.valueOf(withotBillTotal));
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
        object.addProperty("idUser", iduser);
        object.addProperty("monthOfClaim", String.valueOf(Monthid));
        object.addProperty("RefId", "");
        object.addProperty("TotalLocalExpense", currentExpence);
        Log.d("TotalExpense", String.valueOf(currentExpence));
        object.addProperty("Description", "Monthly Expense from App");
        object.addProperty("processBy", iduser);
        object.addProperty("processType", "add");
        object.addProperty("IsApproved", 0);
        object.addProperty("IsPaid", 0);
        object.addProperty("Remarks", str_Remarks);
        object.addProperty("RemarksWithoutBill", str_Remarks2);
//Withbill
        JsonArray LocalExpenseItems = new JsonArray();
        LocalExpenseItems list = new LocalExpenseItems(str_BoardLodge, str_ConveyTravel, str_FoodBeverage, str_PetrolFuel, str_PostCourier, str_PrintingStationary, str_Telephone_Datacharge, str_MiscelExpense);

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

        LocalExpenseItems_2 list2 = new LocalExpenseItems_2(str_BoardLodge2, str_ConveyTravel2, str_FoodBeverage2, str_PetrolFuel2, str_PostCourier2, str_PrintingStationary2, str_Telephone_Datacharge2, str_MiscelExpense2);
        LocalExpensesItemlist2.add(list2);
        Log.d("list_2_size", String.valueOf((LocalExpensesItemlist2.size())));
        for (int j = 0; j < LocalExpensesItemlist2.size(); j++) {


            JsonObject jsonObject1 = new JsonObject();
            jsonObject1.addProperty("BoardLodge", LocalExpensesItemlist2.get(j).getBoardLodge());
            Log.d("boardlodge", String.valueOf(LocalExpensesItemlist2.get(j).getBoardLodge()));

            jsonObject1.addProperty("ConvTravel", LocalExpensesItemlist2.get(j).getConvTravel());
            Log.d("conveytravel", String.valueOf(LocalExpensesItemlist2.get(j).getConvTravel()));

            jsonObject1.addProperty("Food", LocalExpensesItemlist2.get(j).getFood());
            jsonObject1.addProperty("Fuel", LocalExpensesItemlist2.get(j).getFuel());
            jsonObject1.addProperty("PostageCourier", LocalExpensesItemlist2.get(j).getPostageCourier());
            jsonObject1.addProperty("Printing", LocalExpensesItemlist2.get(j).getPrinting());
            jsonObject1.addProperty("Telephone", LocalExpensesItemlist2.get(j).getTelephone());
            jsonObject1.addProperty("Misc", LocalExpensesItemlist2.get(j).getMisc());

            LocalExpenseItemsWithoutBill.add(jsonObject1);
            Log.d("list2", jsonObject1.toString());
        }

        object.add("LocalExpenseItems", LocalExpenseItems);
        object.add("LocalExpenseItemsWithoutBill", LocalExpenseItemsWithoutBill);
        Log.d("REQUEST", String.valueOf(object));


        Call<JsonArray> call = apiService.Local_Expenses(object);
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

                                String Expenseid = object1.getString("idLocalExpense");

                                SharedPreference_class.putidlocalExpense(LocalConveyance.this, Expenseid);
                                Log.d("fileupload_local", Expenseid);

                                if (Integer.parseInt(result) == 1) {
                                    Result_alert(strMsg, WebUrl, result);
                                } else {
                                    Result_alert(strMsg, WebUrl, result);
                                }

                            }
                        } else {
                            Toast.makeText(LocalConveyance.this, "No Records has found", Toast.LENGTH_SHORT).show();
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

    private void Result_alert(String strMsg, String WebUrl, String result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LocalConveyance.this);
        builder.setTitle(strMsg);
        builder.setMessage(WebUrl);
        builder.setCancelable(false);


        if (Integer.parseInt(result) == 1) {
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent i = new Intent(LocalConveyance.this, FileUpload_localConvey.class);
                    cleartext();
                    startActivity(i);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    Intent intent1 = new Intent(LocalConveyance.this, Report.class);
                    cleartext();
                    startActivity(intent1);

                }
            });
            builder.show();
        }
        else {
            builder.setTitle(strMsg);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }

    }

    private void alert1(String title, String month) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LocalConveyance.this);

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        finish();
        return true;
    }
}
