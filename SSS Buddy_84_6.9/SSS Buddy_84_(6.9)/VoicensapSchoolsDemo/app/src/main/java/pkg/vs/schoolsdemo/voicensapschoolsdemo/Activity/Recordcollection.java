
package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.view.WindowInsetsControllerCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;

public class Recordcollection extends AppCompatActivity implements CalendarDatePickerDialogFragment.OnDateSetListener {
    Spinner spin_schoollist, spin_invoices, Spin_financialyear, spin_modeofpayment, spin_depositbank, spin_cash_deposit_bank;
    String[] itemschoolname, itemsinvoiceId, iteminvoicename;
    String[] itemschoolId;
    LinearLayout linear_invoice, linear_cheque, linear_cash, linear_neft, linear_pdc;
    String strSchoolId = "0", strinvoiceId = "0", strfinancialyearId, strmodeofpayment, strdepositbankId, stramount, stramountdate, strchequenumber,
            strchequedate, strchequebank, strcashdepositedon, strcashdepositedbank, strtransactionno, strpdcno, strpdcdate,
            strpdcbank, strpdcbankbranch, strchequerdepositeddate, strcashdepositbankId = "0", strchequebankbranch, strDateTime, strcurrentdate, strcashdepositbranch, strcashrecieveddate;
    RequestQueue requestQueue;
    EditText et_search, et_chequeno, et_chequedate, et_cheque_bank, et_cashdepositedon, et_cashdepositedbank, etamount, et_cashrecevieddate,
            et_transactionno, et_pdcno, et_pdcdate, et_pdcbank, et_pdcbankbranch, et_chequedepositeddate, et_cash_branch, et_cheque_branch, et_amountdate;
    String Schoolname, strDateTime1, invoices = "0", financialyear, modeofpayment, depositbank = "0", userId, cashdepositbank = "0";
    int dateType;
    TextView lblClick;
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
    String DepositedBankname = "",
            Branchname = "",
            cashdepositeddate = "",
            chequenumber = "",
            TransactionId = "",
            chequedate = "",
            Depositeddate = "",
            cashreceiveddate = "";
    String invoice_number;
    AlertDialog.Builder pictureDialog;
    private static final int REQUEST = 1;
    private static final int SELECT_IMAGE = 2;
    private static final int PICKFILE_REQUEST_CODE = 8778;

    private static final int FILE_SELECT_CODE = 0;

    ArrayList<String> imagePathList = new ArrayList<>();
    private final String TEMP_FILE_NAME = ".ImgTmp";
    File tempFile,outputFile1,filenew,myFile;
    private static final int PICK_PDF_FILE = 2;


    String imgpath="", contentType, imageFilePath;
    File photoFile;
    Uri photoURI;
    String pth = "";
    File file;
    com.toptoche.searchablespinnerlibrary.SearchableSpinner spin_school;
    SharedPreferences shpRemember;
    SharedPreferences.Editor ed;
    private static final String SH_USERS = "userInfo";
    private static final String SH_USERID = "UserId";
    String serverPath = "http://103.154.253.101:8088/api/GetDetails/";
    // String serverPath = "http://122.165.12.41:8094/api/GetDetails/";
    private static final String LOG_TAG_EXTERNAL_STORAGE = "EXTERNAL_STORAGE";
    private static final String ContentURI = "Content_Uri";

    ImageView ivimage;
    Button changebtn, btn_submit;
    String strSelectedImgFilePath = "";
    public static final String IMAGE_FOLDER_NAME = "DEMOAPP/image";
    public static final String IMAGE_FILE_NAME = "DEMOAPP";
    private Calendar calendar;
    private int year, month, day, hour, minute;
    public static int IMAGE_UPLOAD_STATUS = 123;

    ImageView img_chequedate, img_cashdatedeposit, img_pdcdate, img_chequedepositon, img_cashreciveddate;

    Uri picUri;
    // Compress...cardImage_ivImage
    int selDay, selMonth, selYear;
    private File actualImage;
    private File compressedImage;
    String strCompressedImagePath, CurrentFinancialYear;


    String[] itemfinancialyear;
    String[] itemfinancialId;


    //    vims.voicesnapforschools.com/api/GetDetails/DemoGetCustomerList
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordcollection);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
        userId = shpRemember.getString(SH_USERID, null);
        Log.d("schluserid", userId);
        ivimage = (ImageView) findViewById(R.id.cardImage_ivImage);
        changebtn = (Button) findViewById(R.id.cardImage_tvchangeimg);
        btn_submit = (Button) findViewById(R.id.collection_submit);
        linear_invoice = (LinearLayout) findViewById(R.id.linear_invoice);
        linear_cheque = (LinearLayout) findViewById(R.id.linear_cheque);
        linear_cash = (LinearLayout) findViewById(R.id.linear_cash);
        linear_neft = (LinearLayout) findViewById(R.id.linear_neft);
        linear_pdc = (LinearLayout) findViewById(R.id.linear_pdc);
        lblClick = (TextView) findViewById(R.id.lblClick);


//        et_search = (EditText) findViewById(R.id.et_search_schoolname);

//        Amount
        etamount = (EditText) findViewById(R.id.et_amount);
        et_amountdate = (EditText) findViewById(R.id.et_amount_date);

//        pdc
        et_pdcbank = (EditText) findViewById(R.id.et_pdc_bank);
        et_pdcno = (EditText) findViewById(R.id.et_pdc_number);
        et_pdcdate = (EditText) findViewById(R.id.et_pdc_date);
        et_pdcbankbranch = (EditText) findViewById(R.id.et_pdc_bank_branch);


//      cheque
        et_chequedepositeddate = (EditText) findViewById(R.id.et_cheque_depositedbankdate);
        et_cheque_bank = (EditText) findViewById(R.id.et_cheque_bank);
        et_chequedate = (EditText) findViewById(R.id.et_cheque_date);
        et_chequeno = (EditText) findViewById(R.id.et_cheque_number);
        et_cheque_branch = (EditText) findViewById(R.id.et_cheque_depositeddatebankbranch);

//        NEFT
        et_transactionno = (EditText) findViewById(R.id.et_neft_transaction);

//        cash
        et_cashrecevieddate = (EditText) findViewById(R.id.et_cash_recivedon);
        et_cashdepositedon = (EditText) findViewById(R.id.et_cash_depositedon);
        et_cashdepositedbank = (EditText) findViewById(R.id.et_cash_depositedbank);
        et_cash_branch = (EditText) findViewById(R.id.et_cash_depositedbankbranch);

        img_chequedate = (ImageView) findViewById(R.id.imagedate_cheque);
        img_cashdatedeposit = (ImageView) findViewById(R.id.imagedate_cash);
        img_pdcdate = (ImageView) findViewById(R.id.imagedate_pdc);
        img_chequedepositon = (ImageView) findViewById(R.id.imagedate_cheque_depositon);
        img_cashreciveddate = (ImageView) findViewById(R.id.imagedate_recievedcash);


        spin_cash_deposit_bank = (Spinner) findViewById(R.id.spin_cash_deposit_bank);
        spin_depositbank = (Spinner) findViewById(R.id.spin_deposit_bank);
        spin_school = (com.toptoche.searchablespinnerlibrary.SearchableSpinner) findViewById(R.id.spinner_school);
        spin_invoices = (Spinner) findViewById(R.id.spin_invoice);
        Spin_financialyear = (Spinner) findViewById(R.id.spin_financialyear);
        spin_modeofpayment = (Spinner) findViewById(R.id.spin_mode);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 10);

        selDay = cal.get(Calendar.DAY_OF_MONTH);
        selMonth = cal.get(Calendar.MONTH);// - 1;
        selYear = cal.get(Calendar.YEAR);
        getSchoolname();
        spin_school.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = spin_school.getSelectedItemPosition();
                strSchoolId = itemschoolId[index];
                getFinancialYearApi();
                Schoolname = spin_school.getSelectedItem().toString();
                Log.d("School name", Schoolname);


                if (strSchoolId.equals("0")) {
                    Alert("Select the School name");
                } else {
                    if (strfinancialyearId.equals("3")) {
                        linear_invoice.setVisibility(View.VISIBLE);
                        getinvoices();
                    } else {
                        linear_invoice.setVisibility(View.GONE);
                        getinvoices();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        getFinancialYearApi();



        Spin_financialyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = Spin_financialyear.getSelectedItemPosition();

                strfinancialyearId = itemfinancialId[index];
                financialyear = Spin_financialyear.getSelectedItem().toString();
                Log.d("financialyear", financialyear);
                Log.d("strfinancialyearId", strfinancialyearId);


                if (!strfinancialyearId.equals("0")) {
                    if ((strSchoolId.equals("0")) || (strSchoolId.equals("")) || (strSchoolId.equals("null"))) {
                        Alert("Select the school name");
                        linear_invoice.setVisibility(View.GONE);

                        btn_submit.setEnabled(false);
                        getinvoices();
                    } else {
                        linear_invoice.setVisibility(View.VISIBLE);
                        btn_submit.setEnabled(true);

                        getinvoices();
                    }
                } else {
                    linear_invoice.setVisibility(View.GONE);
                    btn_submit.setEnabled(false);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

//                ((TextView) view).setVisibility(View.GONE);


            }
        });


        String[] itemmodepayment = {"Select mode of payment", "Cash", "Cheque", "NEFT", "PDC"};
        final String[] itemmodepaymentId = {"0", "1", "2", "3", "4"};

        ArrayAdapter<String> adaptermode = new ArrayAdapter<String>(Recordcollection.this, android.R.layout.simple_spinner_item, itemmodepayment);
        adaptermode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_modeofpayment.setAdapter(adaptermode);
        spin_modeofpayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = spin_modeofpayment.getSelectedItemPosition();
                strmodeofpayment = itemmodepaymentId[index];
                modeofpayment = spin_modeofpayment.getSelectedItem().toString();
                Log.d("modeofpayment", modeofpayment);
                if (strmodeofpayment.equals("1")) {
                    linear_cheque.setVisibility(View.GONE);
                    linear_cash.setVisibility(View.VISIBLE);
                    linear_neft.setVisibility(View.GONE);
                    linear_pdc.setVisibility(View.GONE);
                } else if (strmodeofpayment.equals("2")) {
                    linear_cheque.setVisibility(View.VISIBLE);
                    linear_cash.setVisibility(View.GONE);
                    linear_neft.setVisibility(View.GONE);
                    linear_pdc.setVisibility(View.GONE);
                } else if (strmodeofpayment.equals("3")) {
                    linear_cheque.setVisibility(View.GONE);
                    linear_cash.setVisibility(View.GONE);
                    linear_neft.setVisibility(View.VISIBLE);
                    linear_pdc.setVisibility(View.GONE);
                } else if (strmodeofpayment.equals("4")) {
                    linear_cash.setVisibility(View.GONE);
                    linear_neft.setVisibility(View.GONE);
                    linear_cheque.setVisibility(View.GONE);
                    linear_pdc.setVisibility(View.VISIBLE);
                } else {
                    linear_cheque.setVisibility(View.GONE);
                    linear_cash.setVisibility(View.GONE);
                    linear_neft.setVisibility(View.GONE);
                    linear_pdc.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_invoices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = spin_invoices.getSelectedItemPosition();
                strinvoiceId = itemschoolId[index];
                invoices = spin_invoices.getSelectedItem().toString();
                Log.d("invoices_pendingAmt", invoices);


                String[] separated = invoices.split(" - ");
                invoice_number = separated[0];
                Log.d("sepearte_invoice", invoice_number);

                String pending_amt = separated[1];
                Log.d("pending_amount", pending_amt);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        String[] itemdepositbank = {"Select bank", "Karur vysya bank", "Kotak Mahindra bank","ICICI Bank"};
        final String[] itemdepositbankId = {"0", "1", "2", "3"};

        ArrayAdapter<String> adapterdepositbank = new ArrayAdapter<String>(Recordcollection.this, android.R.layout.simple_spinner_item, itemdepositbank);
        adapterdepositbank.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_depositbank.setAdapter(adapterdepositbank);

        spin_depositbank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = spin_depositbank.getSelectedItemPosition();
                strdepositbankId = itemdepositbankId[index];
                depositbank = spin_depositbank.getSelectedItem().toString();
                Log.d("invoices", depositbank);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        String[] itemcashdepositbank = {"Select bank", "Karur vysya bank", "Kotak Mahindra bank","ICICI Bank"};
        final String[] itemcashdepositbankId = {"0", "1", "2", "3"};


        ArrayAdapter<String> adaptercashdepositbank = new ArrayAdapter<String>(Recordcollection.this, android.R.layout.simple_spinner_item, itemcashdepositbank);
        adapterdepositbank.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_cash_deposit_bank.setAdapter(adaptercashdepositbank);

        spin_cash_deposit_bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = spin_cash_deposit_bank.getSelectedItemPosition();
                strcashdepositbankId = itemcashdepositbankId[index];
                cashdepositbank = spin_cash_deposit_bank.getSelectedItem().toString();
                Log.d("cashdepositbank", cashdepositbank);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ivimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogbox();

            }
        });

        changebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogbox();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                stramount = etamount.getText().toString();
                strchequenumber = et_chequeno.getText().toString();
                strchequebank = et_cheque_bank.getText().toString();
                strchequedate = et_chequedate.getText().toString();
                strchequerdepositeddate = et_chequedepositeddate.getText().toString();
                strchequebankbranch = et_cheque_branch.getText().toString();

                strcashdepositedon = et_cashdepositedon.getText().toString();
                strcashdepositedbank = et_cashdepositedbank.getText().toString();
                strcashdepositbranch = et_cash_branch.getText().toString();
                strcashrecieveddate = et_cashrecevieddate.getText().toString();


                strtransactionno = et_transactionno.getText().toString();

                strpdcbank = et_pdcbank.getText().toString();
                strpdcdate = et_pdcbank.getText().toString();
                strpdcno = et_pdcno.getText().toString();
                strpdcbankbranch = et_pdcbankbranch.getText().toString();

                Log.d("Amount", stramount + " " + strcashrecieveddate + strcashdepositedon + cashdepositbank + strcashdepositbranch);

                if (strSchoolId.equals("0") || financialyear.equals("Select financial year") || stramount.equals("") || (strmodeofpayment.equals("0") ||
                        (strmodeofpayment.equals("1")) || (strmodeofpayment.equals("2")) || (strmodeofpayment.equals("3")) || (strmodeofpayment.equals("4")))) {
                    if (strSchoolId.equals("0")) {
                        Alert("Select Your school name");
                    } else if (financialyear.equals("Select financial year")) {
                        Alert("Select financial year");
                    } else if (stramount.equals("")) {
                        Alert("Enter the received amount");
                    } else if (strmodeofpayment.equals("0")) {
                        Alert("Select Mode of Payment");
                    } else if (strmodeofpayment.equals("1")) {

                        if (strcashrecieveddate.equals("")) {
                            Alert("Enter the received date");
//                        et_cashrecevieddate.setError("Enter the received date");
                        } else if (strcashdepositedon.equals("")) {
                            Alert("Enter the Deposited date");
//                        et_cashdepositedon.setError("Enter the Deposited date");
                        } else if (strcashdepositbankId.equals("0")) {
                            Alert("Select bank");
                        } else if (strcashdepositbranch.equals("")) {
                            Alert("Enter the cash Deposited branch");
//                        et_cash_branch.setError("Enter the cash Deposited branch");
                        } else {
                            Log.d("cashValuesentered", "cashSubmitted");


                            try {
                                Log.d("strcashdepositedon", strcashdepositedon);
                                String fdate = strcashdepositedon;
                                SimpleDateFormat print = new SimpleDateFormat("dd/MM/yyyy");
                                Date fromDate = print.parse(fdate);
                                Log.d("fromDate", String.valueOf(fromDate));
                                Log.d("strcashrecieveddate", strcashrecieveddate);
                                String tdate = strcashrecieveddate;
                                SimpleDateFormat print1 = new SimpleDateFormat("dd/MM/yyyy");
                                Date toDate = print1.parse(tdate);
                                Log.d("toDate", String.valueOf(toDate));
                                if ((fromDate.after(toDate)) || (fromDate.equals(toDate))) {
                                    sendImageCircularRetroFit();
                                } else {
                                    Alert("Enter the deposited date after received date");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                        }
                    } else if (strmodeofpayment.equals("2")) {

                        if (strchequenumber.equals("")) {
                            et_chequeno.setError("Enter the cheque number");
                        } else if (strchequedate.equals("")) {
                            et_chequedate.setError("Enter the cheque date");
                        } else if (strchequebank.equals("")) {
                            et_cheque_bank.setError("Enter the cheque date");
                        } else if (depositbank.equals("Select bank")) {
                            Alert("Select Bank");
                        } else if (strchequebankbranch.equals("")) {
                            et_cheque_branch.setError("Enter the cash Deposited branch");
                        } else {
                            Log.d("chequeValuesentered", "chequeSubmitted");
                            try {
                                Log.d("strcashdepositedon", strchequerdepositeddate);
                                String fdate = strchequerdepositeddate;
                                SimpleDateFormat print = new SimpleDateFormat("dd/MM/yyyy");
                                Date fromDate = print.parse(fdate);
                                Log.d("fromDate", String.valueOf(fromDate));
                                Log.d("strcashrecieveddate", strchequedate);
                                String tdate = strchequedate;
                                SimpleDateFormat print1 = new SimpleDateFormat("dd/MM/yyyy");
                                Date toDate = print1.parse(tdate);
                                Log.d("toDate", String.valueOf(toDate));
                                if ((fromDate.after(toDate)) || (fromDate.equals(toDate))) {
                                    sendImageCircularRetroFit();
                                } else {
                                    Alert("Enter the deposited date after received date");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (strmodeofpayment.equals("3")) {

                        sendImageCircularRetroFit();

                    } else if (strmodeofpayment.equals("4")) {
                        if (strpdcno.equals("")) {
                            et_pdcno.setError("Enter the Cheque number");
                        } else if (strpdcdate.equals("")) {
                            et_pdcdate.setError("Enter the Cheque date");
                        } else if (strpdcbank.equals("")) {
                            et_pdcbank.setError("Enter the cheque bank");
                        } else if (strpdcbankbranch.equals("")) {
                            et_pdcbankbranch.setError("Enter the cheque bank branch");
                        } else {
                            Log.d("pdcValuesentered", "pdcSubmitted");
                            sendImageCircularRetroFit();
                        }
                    }
                }

            }
        });

        img_chequedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMinDateTime();
                dateType = 1;
                datePicker1();

            }
        });

        img_cashdatedeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMinDateTime();
//                setMinDateTimeforcashdeposit(strcashrecieveddate);
                dateType = 2;
                datePicker1();

            }
        });

        img_pdcdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMinDateTime();
                dateType = 3;
                datePicker();

            }
        });
        img_chequedepositon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMinDateTime();
//                setMinDateTimeforcashdeposit(strcashrecieveddate);
                dateType = 4;
                datePicker1();

            }
        });


        img_cashreciveddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMinDateTime();
                dateType = 5;
                datePicker1();

            }
        });
        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        showDate(year, month, day);

    }

    private void getFinancialYearApi() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        Voicesnapdemointerface apiService = VimsClient.getClient().create(Voicesnapdemointerface.class);
        Call<JsonArray> call = apiService.getFinancialYearForApp();

        call.enqueue(new Callback<JsonArray>() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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

                        itemfinancialyear = new String[jsonArrayorgList.length() + 1];
                        itemfinancialId = new String[jsonArrayorgList.length() + 1];
                        itemfinancialyear[0] = "Select financial year";
                        itemfinancialId[0] = "0";

                        for (int i = 0; i < jsonArrayorgList.length(); i++) {
                            jsonObjectorgInfo = jsonArrayorgList.getJSONObject(i);


                            itemfinancialyear[i + 1] = jsonObjectorgInfo.getString("nameValue");
                            itemfinancialId[i + 1] = jsonObjectorgInfo.getString("idValue");
                            CurrentFinancialYear = jsonObjectorgInfo.getString("idValue");

                        }
                        List<String> financial_year = new ArrayList<String>(Arrays.asList(itemfinancialyear));
                        financial_year.remove("2017 - 2018");
                        itemfinancialyear = financial_year.toArray(new String[0]);


                        List<String> financialid = new ArrayList<String>(Arrays.asList(itemfinancialId));
                        financialid.remove("2017");
                        itemfinancialId = financialid.toArray(new String[0]);


                        Log.d("year", String.valueOf(itemfinancialyear.length));
                        Log.d("CurrentFinancialYear", CurrentFinancialYear);

                        ArrayAdapter<String> adapterfinancialyear = new ArrayAdapter<String>(Recordcollection.this, android.R.layout.simple_spinner_item, itemfinancialyear);
                        adapterfinancialyear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Spin_financialyear.setAdapter(adapterfinancialyear);

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

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return (true);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void getSchoolname() {

//        http://vims.voicesnap.com/api/GetDetails/DemoGetCustomerList?CustomerType=1&&LoginId=41
        String jsonApplication = serverPath + "DemoGetCustomerList?CustomerType=" + "1" + "&&" + "LoginId=" + userId;
        Log.d("URL", jsonApplication);
        final ProgressDialog providerDialog = new ProgressDialog(Recordcollection.this);
        providerDialog.setMessage("Loading...");
        providerDialog.setCancelable(false);
        providerDialog.show();

        if (isNetworkConnected()) {
            requestQueue = Volley.newRequestQueue(this);
            StringRequest obreq = new StringRequest(Request.Method.GET, jsonApplication, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                    hidePDialog();
                    providerDialog.dismiss();
                    try {
                        JSONArray js = new JSONArray(response);
                        itemschoolname = new String[js.length()];
                        itemschoolId = new String[js.length()];
                        if (js.length() > 0) {
                            for (int i = 0; i < js.length(); i++) {

                                JSONObject jsonObject = js.getJSONObject(i);
                                Log.d("Testing", jsonObject.toString());

                                itemschoolname[i] = jsonObject.getString("CustomerName");
                                itemschoolId[i] = jsonObject.getString("CustomerID");
                                ArrayAdapter<String> adaptercustomeropinion = new ArrayAdapter<String>(Recordcollection.this, android.R.layout.simple_spinner_item, itemschoolname);
                                adaptercustomeropinion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin_school.setAdapter(adaptercustomeropinion);


                            }
                        } else {

                            itemschoolname[0] = "No Schools found";
                            itemschoolId[0] = "0";
                            ArrayAdapter<String> adaptercustomeropinion = new ArrayAdapter<String>(Recordcollection.this, android.R.layout.simple_spinner_item, itemschoolname);
                            adaptercustomeropinion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin_school.setAdapter(adaptercustomeropinion);
                        }


                    } catch (Exception e) {
//                    Toast.makeText(NewCustomer.t-his, "Exception", Toast.LENGTH_SHORT).show();
                        Log.d("Exception", e.toString());
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(NewCustomer.this, "MyError2", Toast.LENGTH_SHORT).show();
                            Log.d("MyError", error.toString());
//                            hidePDialog();
                            providerDialog.dismiss();
                        }
                    });


            obreq.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });
            requestQueue.add(obreq);

        } else {
            Alert("Check Your Internet connection");
//            Toast.makeText(this, "Check Your Internet connection", Toast.LENGTH_SHORT).show();
        }

    }


    private void getinvoices() {
        String jsonApplication = serverPath + "DemoGetInvoiceByCustomerID?customerId=" + strSchoolId;
        Log.d("URL", jsonApplication);
        final ProgressDialog providerDialog = new ProgressDialog(Recordcollection.this);
        providerDialog.setMessage("Loading...");
        providerDialog.setCancelable(false);
        providerDialog.show();

        if (isNetworkConnected()) {
            requestQueue = Volley.newRequestQueue(this);
            StringRequest obreq = new StringRequest(Request.Method.GET, jsonApplication, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                    hidePDialog();
                    providerDialog.dismiss();
                    try {
                        JSONArray js = new JSONArray(response);
                        Log.d("invoiceresponse", response.toString());

                        if (js.length() > 0) {
                            iteminvoicename = new String[js.length()];
                            itemsinvoiceId = new String[js.length()];
                            for (int i = 0; i < js.length(); i++) {
                                JSONObject jsonObject = js.getJSONObject(i);
                                Log.d("Testing", jsonObject.toString());
                                //{"InvoiceId":2,"InvoiceNumber":"0003\/E\/02\/2017-18"}
                                iteminvoicename[i] = jsonObject.getString("InvoiceNumber") + " - Pending " + jsonObject.getString("PendingAmount");
                                itemsinvoiceId[i] = jsonObject.getString("InvoiceId");


                                if (jsonObject.getString("InvoiceId").equals("0.0") || jsonObject.getString("InvoiceId").equals("0")) {
                                    btn_submit.setEnabled(false);
                                } else {
                                    btn_submit.setEnabled(true);
                                }
                                ArrayAdapter<String> adaptercustomeropinion = new ArrayAdapter<String>(Recordcollection.this, android.R.layout.simple_spinner_item, iteminvoicename);
                                adaptercustomeropinion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin_invoices.setAdapter(adaptercustomeropinion);
                            }

                        } else {
                            btn_submit.setEnabled(false);
                            iteminvoicename[0] = "No invoices found";
                            iteminvoicename[0] = "0";
                            ArrayAdapter<String> adaptercustomeropinion = new ArrayAdapter<String>(Recordcollection.this, android.R.layout.simple_spinner_item, iteminvoicename);
                            adaptercustomeropinion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin_invoices.setAdapter(adaptercustomeropinion);
                        }

                    } catch (Exception e) {
//                    Toast.makeText(NewCustomer.this, "Exception", Toast.LENGTH_SHORT).show();
                        Log.d("Exception", e.toString());
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(NewCustomer.this, "MyError2", Toast.LENGTH_SHORT).show();
                            Log.d("MyError", error.toString());
//                            hidePDialog();
                            providerDialog.dismiss();
                        }
                    });


            obreq.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });
            requestQueue.add(obreq);

        } else {
            Alert("Check Your Internet connection");
//            Toast.makeText(this, "Check Your Internet connection", Toast.LENGTH_SHORT).show();
        }

    }


    private void showDate(int year, int month, int day) {

        this.year = year;
        this.month = month;
        this.day = day;
        Calendar cal2 = Calendar.getInstance();

        cal2.set(year, month, day, hour, minute);
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");//yyyyMMdd");//dd/mm/yyyy");//dd/mm/yyyyMM/dd/yy

        strDateTime1 = df2.format(cal2.getTime());
//        strcurrentdate = strDateTime;

    }



    public String makeEmptyImg() {
        try {
            Log.d("makeEmptyImg","test0");
            String filepath = null;
            File file;
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                filepath =this.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath()+File.separator+"0000000000.jpg";

               // filepath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+File.separator+"0000000000.jpg";
                Log.d("testpath_11",filepath);
            }
            else{

                filepath = Environment.getExternalStorageDirectory() + File.separator + "0000000000.jpg";
                Log.d("path_10",filepath);

            }

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            File f = new File(filepath);
            f.createNewFile();

            Log.d("createNewFile", String.valueOf(f));

            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
            return f.getPath();
        } catch (IOException e) {
            Log.d("catchfile","test");
            e.printStackTrace();
            return "";
        }

    }


    private void sendImageCircularRetroFit() {

        Voicesnapdemointerface apiService = VimsClient.getClient().create(Voicesnapdemointerface.class);

        if ((imgpath.equals(""))) {
            String fff = makeEmptyImg();
            Log.d("fff", fff);
            pth = fff;
        } else {
            pth = imgpath;
        }

        file = new File(pth);
        Log.d("FILE_Path", pth + "   ");
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part bodyFile = MultipartBody.Part.createFormData("ChequeUpload", file.getName(), requestFile);
        Log.d("testImagePath",imgpath);
        Log.d("file.getName()",file.getName());
        Log.d("bodyFile", String.valueOf(bodyFile));
        JsonArray jsonReqArray = constructJsonArrayAdminSchools();

        RequestBody requestBody = RequestBody.create(MultipartBody.FORM, jsonReqArray.toString());
        Log.d("outfilepathApi",imgpath);


        final ProgressDialog mProgressDialog = new ProgressDialog(Recordcollection.this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Uploading...");
        mProgressDialog.setCancelable(false);

        if (!this.isFinishing())
            mProgressDialog.show();

        Call<JsonArray> call = apiService.DemoInsertPayment(requestBody, bodyFile);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call,
                                   retrofit2.Response<JsonArray> response) {

                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                Log.d("Upload-Code:Response", response.code() + "-" + response);
                if (response.code() == 200 || response.code() == 201) {
                    Log.d("Upload:Body", "" + response.body().toString());

                    try {
                        JSONArray js = new JSONArray(response.body().toString());
                        if (js.length() > 0) {
                            JSONObject jsonObject = js.getJSONObject(0);
//                            [{"result":0,"resultMessage":"Payment Successfully Updated"}]
                            String strStatus = jsonObject.getString("result");
                            String strMsg = jsonObject.getString("resultMessage");
                            if ((strStatus).equals("0")) {
//                                finish();
                                Alert1(strMsg);
                            }
                        } else {
                            Alert1("Payment not found");
                        }


                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
               // Alert("Check your Internet connectivity");
                Log.e("Response Failure", t.getMessage());
                Log.d("Upload error:", t.getMessage() + "\n" + t.toString());
              //  Alert(t.toString());
            }
        });
    }


    private JsonArray constructJsonArrayAdminSchools() {

        stramountdate = et_amountdate.getText().toString();
        strchequenumber = et_chequeno.getText().toString();
        strchequebank = et_cheque_bank.getText().toString();
        strchequedate = et_chequedate.getText().toString();
        strchequerdepositeddate = et_chequedepositeddate.getText().toString();
        strchequebankbranch = et_cheque_branch.getText().toString();
        strcashdepositedon = et_cashdepositedon.getText().toString();
        strcashdepositedbank = et_cashdepositedbank.getText().toString();
        strcashdepositbranch = et_cash_branch.getText().toString();
        strcashrecieveddate = et_cashrecevieddate.getText().toString();
        stramount = etamount.getText().toString();
        stramountdate = et_amountdate.getText().toString();
        strtransactionno = et_transactionno.getText().toString();
        Log.d("TransactionId1", strtransactionno);
        strpdcbank = et_pdcbank.getText().toString();
        strpdcdate = et_pdcbank.getText().toString();
        strpdcno = et_pdcno.getText().toString();
        strpdcbankbranch = et_pdcbankbranch.getText().toString();
        financialyear = Spin_financialyear.getSelectedItem().toString();
        depositbank = spin_depositbank.getSelectedItem().toString();
        cashdepositbank = spin_cash_deposit_bank.getSelectedItem().toString();


        if (strmodeofpayment.equals("1")) {
            chequenumber = "";
            chequedate = "";
            cashreceiveddate = strcashrecieveddate;
            Depositeddate = strcashdepositedon;
            Branchname = strcashdepositbranch;
            DepositedBankname = cashdepositbank;
            TransactionId = "";
        } else if (strmodeofpayment.equals("2")) {
            chequenumber = strchequenumber;
            chequedate = strchequedate;
            cashdepositeddate = "";
            cashreceiveddate = "";
            Depositeddate = strchequerdepositeddate;
            Branchname = strchequebankbranch;
            DepositedBankname = depositbank;
            TransactionId = "";
        } else if (strmodeofpayment.equals("3")) {
            chequenumber = "";
            chequedate = "";
            cashreceiveddate = "";
            cashdepositeddate = "";
            Depositeddate = "";
            Branchname = "";
            DepositedBankname = "";
            TransactionId = strtransactionno;
            Log.d("TransactionId", TransactionId);
        } else if (strmodeofpayment.equals("4")) {
            chequenumber = strpdcno;
            chequedate = "";
            cashreceiveddate = "";
            cashdepositeddate = "";
            Depositeddate = strpdcdate;
            Branchname = strpdcbankbranch;
            DepositedBankname = strpdcbank;
            TransactionId = "";
        }


        JsonArray jsonArraySchool = new JsonArray();

        try {
            JsonObject jsonObjectSchool = new JsonObject();
            if (invoices.equals("No Invoices Found")) {
                strinvoiceId = "0";
            }

            jsonObjectSchool.addProperty("InvoiceID", strinvoiceId);
            jsonObjectSchool.addProperty("CustomerId", strSchoolId);
            jsonObjectSchool.addProperty("FinancialYear", financialyear);
            jsonObjectSchool.addProperty("InvoiceNumber", invoice_number);
            jsonObjectSchool.addProperty("Received", stramount);
            jsonObjectSchool.addProperty("ReceivedDate", strDateTime1);
            jsonObjectSchool.addProperty("PaymentMode", strmodeofpayment);
            jsonObjectSchool.addProperty("CreatedBy", userId);
            jsonObjectSchool.addProperty("CashRecdDate", cashreceiveddate);
            jsonObjectSchool.addProperty("ChequeDate", chequedate);
            jsonObjectSchool.addProperty("ChequeNumber", chequenumber);
            jsonObjectSchool.addProperty("NEFTDetails", TransactionId);
            jsonObjectSchool.addProperty("DepositedBank", DepositedBankname);
            jsonObjectSchool.addProperty("DepositedBranch", Branchname);
            jsonObjectSchool.addProperty("DepositedBy", userId);
            jsonObjectSchool.addProperty("DepositedDate", Depositeddate);//"CreatedBy":"41

            jsonArraySchool.add(jsonObjectSchool);
            Log.d("Final_Array", jsonArraySchool.toString());

        } catch (Exception e) {
            Log.d("ASDF", e.toString());
        }

        return jsonArraySchool;
    }




    private void datePicker() {
        Calendar today = Calendar.getInstance();
        MonthAdapter.CalendarDay minDate = new MonthAdapter.CalendarDay(today);
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setThemeCustom(R.style.MyBetterPickersRadialTimePickerDialog)
                .setOnDateSetListener(Recordcollection.this)
                .setFirstDayOfWeek(Calendar.SUNDAY)
//                .setPreselectedDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH) - 1, today.get(Calendar.DAY_OF_MONTH))
                .setPreselectedDate(selYear, selMonth, selDay)
                .setDateRange(minDate, null)
                .setDoneText("OK")

                .setCancelText("Cancel");
//                .setThemeDark(true);
        cdp.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
        setMinDateTime();
    }


    private void datePicker1() {
        Calendar today = Calendar.getInstance();
        MonthAdapter.CalendarDay maxdate = new MonthAdapter.CalendarDay(today);
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setThemeCustom(R.style.MyBetterPickersRadialTimePickerDialog)
                .setOnDateSetListener(Recordcollection.this)
                .setFirstDayOfWeek(Calendar.SUNDAY)
//                .setPreselectedDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH) - 1, today.get(Calendar.DAY_OF_MONTH))
                .setPreselectedDate(selYear, selMonth, selDay)
                .setDateRange(null, maxdate)
                .setDoneText("OK")
                .setCancelText("Cancel");
//                .setThemeDark(true);
        cdp.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
    }

    private String dateFormater(long dateInMillis, String formatString) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatString);
        String dateString = formatter.format(new Date(dateInMillis));
        Log.d("dateString", dateString);
        return dateString;
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        selDay = dayOfMonth;
        selMonth = monthOfYear;
        selYear = year;
        strDateTime = dateFormater(dialog.getSelectedDay().getDateInMillis(), "dd/MM/yyyy");

        if (dateType == 1) {
            et_chequedate.setText(strDateTime);
        } else if (dateType == 2) {


            et_cashdepositedon.setText(strDateTime);
        } else if (dateType == 3) {
            et_pdcdate.setText(strDateTime);
        } else if (dateType == 4) {
            et_chequedepositeddate.setText(strDateTime);
        } else if (dateType == 5) {
            et_cashrecevieddate.setText(strDateTime);
        } else {
            strcurrentdate = strDateTime;
        }
    }

    private void setMinDateTime() {
        strDateTime = dateFormater(System.currentTimeMillis(), "dd/MM/yyyy");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 10);

        selDay = cal.get(Calendar.DAY_OF_MONTH);
        selMonth = cal.get(Calendar.MONTH);// - 1;
        selYear = cal.get(Calendar.YEAR);

    }

    private void dialogbox() {
        pictureDialog = new AlertDialog.Builder(Recordcollection.this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Take Photo from Camera",
                "Select Image from Gallery",


        };
        pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:

                        contentType = "image/png";
                        takePhotoFromCamera();
                        break;

                    case 1:

                        contentType = "image/png";
                        choosePhotoFromGallary();
//                        showFileChooser
                        pictureDialog.setCancelable(true);
                        break;
                }
            }
        });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        imagePathList.clear();

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_IMAGE);


    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (photoFile != null) {
            photoURI = FileProvider.getUriForFile(Recordcollection.this, "pkg.vs.schoolsdemo.voicensapschoolsdemo.provider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
            startActivityForResult(intent, REQUEST);


        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (pictureDialog != null) {
            pictureDialog.setCancelable(true);
            pictureDialog = null;
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg",storageDir );

        imageFilePath = image.getAbsolutePath();

        return image;
    }

    private void Alert(String Msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Recordcollection.this);
        builder.setIcon(R.drawable.alert);
        builder.setTitle(Msg);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();


    }

    private void Alert1(String Msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Recordcollection.this);
        builder.setIcon(R.drawable.inserted);
        builder.setTitle(Msg);
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
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode != Activity.RESULT_CANCELED) {

            if (requestCode == REQUEST && resultCode == RESULT_OK) {
                lblClick.setVisibility(View.GONE);
                changebtn.setEnabled(true);

                String filetype=".jpg";
                writeData(photoURI,filetype);
                Log.d("photoURicamera", String.valueOf(photoURI));
                Glide.with(getApplicationContext())
                        .load(imgpath)
                        .into(ivimage);
                Log.d("glidepat12h",imgpath);
            } else if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK && null != data) {

                lblClick.setVisibility(View.GONE);
                changebtn.setEnabled(true);
                if (data.getData() != null) {
                    Uri fileuri = data.getData();
                    Log.d("fileuri", String.valueOf(fileuri));
                    String filetype=".jpg";
                    writeData(fileuri,filetype);


                    Glide.with(getApplicationContext())
                            .load(imgpath)
                            .into(ivimage);
                    Log.d("glidepath",imgpath);

                }
            }

        }
    }

    private void writeData(Uri myFile, String data) {
        try {
            InputStream fis = getContentResolver().openInputStream(myFile);
            File outputDir = getApplicationContext().getExternalCacheDir();
            try {
                outputFile1 = File.createTempFile(IMAGE_FILE_NAME, data, outputDir);
                Log.d("outputFile1", String.valueOf(outputFile1));

                imgpath=outputFile1.getPath();
                Log.d("writeoutfilepath",outputFile1.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileOutputStream fos =new FileOutputStream(outputFile1);
            byte[] buffer =new byte[1024];
            int length=0;
            while ((length=fis.read(buffer)) >0) {
                fos.write(buffer);
            }
            fis.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
