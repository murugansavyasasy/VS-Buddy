package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters.Customer_listAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.CustomerListClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;


public class CustomerDetails extends AppCompatActivity {
    private static final String TAG = CustomerDetails.class.getSimpleName();
    Spinner customer_spin;
    com.toptoche.searchablespinnerlibrary.SearchableSpinner sales_spin;
    TextView txtgone;
    private SearchView mSearchView;
    private MenuItem searchMenuItem;
    LinearLayout layout_salescustomer;
    ArrayList<CustomerListClass> DetailList = new ArrayList<>();
    List<CustomerListClass> data = new ArrayList();
    Customer_listAdapter listAdapter;
    RecyclerView recyclerView;
    String iduser;
    String customertype[];
    TextView txt_count;

    String[] salesid, salesname;
    Button btn_custom;
    String value;

    String selectedItemText = "";
    String SalePersionID = "0";
    String SelectedCustomerType = "";
    int count;
    int visibleAndGone = 0;
    String Schl_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);


        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        sales_spin = (com.toptoche.searchablespinnerlibrary.SearchableSpinner) findViewById(R.id.spin_salesperson);
        customer_spin = (Spinner) findViewById(R.id.spin_customertype);

        btn_custom = (Button) findViewById(R.id.btn_search_custom);
        txt_count = (TextView) findViewById(R.id.txt_count);
        layout_salescustomer = (LinearLayout) findViewById(R.id.layout_salescustomer);
        recyclerView = (RecyclerView) findViewById(R.id.custom_recycle);

        iduser = SharedPreference_class.getUserid(CustomerDetails.this);
        Log.d("userid", iduser);


        Schl_type = SharedPreference_class.getShSchlUsertype(CustomerDetails.this);
        Log.d("schooltype", Schl_type);

        setAdpter();
        if (!Schl_type.equals("MyTeam")) {
            Salesperson();
        }
        sales_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemText = (String) parent.getItemAtPosition(position);
                Log.d("selectedItemText", selectedItemText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("button", " button");
                for (int i = 0; i < salesname.length; i++) {
                    String name = salesname[i];
//                    String customer=customertype[i];
                    if (selectedItemText.equals(name)) {
                        Log.d("selectname", selectedItemText);
                        SalePersionID = salesid[i];
                        Log.d("ID", SalePersionID);

                        setAdpter();
                        //  CustomerInfo("search");
                    }
                }
                if (selectedItemText.equals("")) {
                    alert("SalesPerson not Selected");
                }
            }

        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.customertype, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customer_spin.setAdapter(adapter);
        customertype = CustomerDetails.this.getResources().getStringArray(R.array.customertype);
        customer_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedCustomerType = (String) parent.getItemAtPosition(position);
                Log.d("selected customertype", SelectedCustomerType);


                if (SelectedCustomerType.equals("Education")) {

                    filter("Education");

                } else if (SelectedCustomerType.equals("Corporate")) {

                    filter("Corporate");

                } else if (SelectedCustomerType.equals("Huddle")) {

                    filter("Huddle");
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setAdpter() {
        listAdapter = new Customer_listAdapter(CustomerDetails.this, DetailList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(listAdapter);
        CustomerInfo("");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return (true);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search....");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchFilter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void searchFilter(String newText) {
        List<CustomerListClass> data = new ArrayList();
        listAdapter.notifyDataSetChanged();
        for (CustomerListClass d : DetailList) {

            value = d.getIdCustomer().toLowerCase() + d.getBillingCity().toLowerCase() + d.getSchoolServerId().toLowerCase() +
                    d.getTallyCustomerId().toLowerCase() + d.getContactPerson().toLowerCase() + d.getCustomerName().toLowerCase() + d.getCustomerTypeName().toLowerCase() + d.getSalesPersonName().toLowerCase();
            if (value.contains(newText.toLowerCase())) {
                data.add(d);
            }

        }
        listAdapter.updateList(data);
    }

    private boolean filter(String s) {
        Log.d("filter", s);


        listAdapter.notifyDataSetChanged();
        data.clear();
        recyclerView.setVisibility(View.VISIBLE);
        txt_count.setVisibility(View.VISIBLE);

        for (CustomerListClass d : DetailList) {

            if (d.getCustomerTypeName().equals("Education") && s.equals("Education")) {
                data.add(d);

            } else if (d.getCustomerTypeName().equals("Corporate") && s.equals("Corporate")) {
                data.add(d);

            } else if (d.getCustomerTypeName().equals("Huddle") && s.equals("Huddle")) {
                data.add(d);

            }
        }
        Log.d("coutsize", String.valueOf(data.size()));
        txt_count.setText(String.valueOf(data.size()));


        listAdapter.updateList(data);
        return false;
    }


    public void CustomerInfo(final String search) {

        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setTitle(" Loading");
        mProgressDialog.setMessage(" Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        if (isNetworkConnected()) {

            VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);

            JsonObject object = new JsonObject();
            object.addProperty("idUser", iduser);
            Log.d("loginid_in_log", iduser);
            object.addProperty("customerId", "0");
            object.addProperty("selectedUser", SalePersionID);
            Log.d("request", object.toString());

            Call<JsonArray> call = apiService.CustomerDetails(object);
            call.enqueue(new Callback<JsonArray>() {

                @Override
                public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                    try {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();

                        Log.d("URL", String.valueOf(response.code()));
                        Log.d("response", response.body().toString());

                        JSONArray array1 = new JSONArray(response.body().toString());

                        count = array1.length();
                        DetailList.clear();
                        data.clear();

                        if (array1.length() > 0) {

                            Log.d("list_number", String.valueOf(count));

                            for (int i = 0; i < array1.length(); i++) {

                                txt_count.setText(String.valueOf(count));
                                JSONObject object1 = array1.getJSONObject(i);
                                String strMsg = object1.getString("resultMessage");


                                if (object1.getString("result").equals("1")) {
                                    String Tally_customerid = object1.getString("tallyCustomerId");
                                    String customer_Name = object1.getString("customerName");
                                    String customertype = object1.getString("customerTypeName");
                                    String contactPerson = object1.getString("contactPerson");
                                    String customerid = object1.getString("idCustomer");
                                    String salesPerson = object1.getString("salesPersonName");
                                    String billing = object1.getString("billingCity");
                                    String schoolid = object1.getString("SchoolServerId");

                                    CustomerListClass list = new CustomerListClass(Tally_customerid, customerid, customer_Name, customertype, contactPerson, salesPerson, billing, schoolid);
                                    DetailList.add(list);
                                    data.add(list);

                                } else {
                                    txt_count.setText("0");
//                                    recyclerView.setVisibility(View.GONE);
                                    alert(strMsg);
                                }
                            }
                            if (Schl_type.equals("MyTeam")) {
                                recyclerView.setVisibility(View.VISIBLE);
                                layout_salescustomer.setVisibility(View.GONE);

                            } else {
                                layout_salescustomer.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                            if (SelectedCustomerType.equals("Select customer")) {
                                recyclerView.setVisibility(View.VISIBLE);
                                txt_count.setVisibility(View.VISIBLE);
                            } else {
                                data.clear();
                                for (CustomerListClass d : DetailList) {
                                    if (d.getCustomerTypeName().equals(SelectedCustomerType)) {
                                        visibleAndGone = 1;
                                        CustomerListClass list = new CustomerListClass(d.getTallyCustomerId(), d.getIdCustomer(), d.getCustomerName(), d.getCustomerTypeName(), d.getContactPerson(), d.getSalesPersonName(), d.getBillingCity(), d.getSchoolServerId());
                                        data.add(list);
                                    }
                                }
                                if (visibleAndGone == 1) {
                                    recyclerView.setVisibility(View.VISIBLE);
                                    txt_count.setVisibility(View.VISIBLE);
                                    txt_count.setText(String.valueOf(data.size()));
                                } else {
                                    recyclerView.setVisibility(View.VISIBLE);
                                    txt_count.setVisibility(View.GONE);
                                }
                            }

                            if (search.equals("search")) {
                                listAdapter.updateList(data);
                            } else {

                                listAdapter.notifyDataSetChanged();
                            }

                        } else {
                            DetailList.clear();
                            listAdapter.notifyDataSetChanged();
                            alert("No Records has found");
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    Log.e("Response Failure", t.getMessage());
//                    mProgressDialog.dismiss();
                    alert("Server Connection Failed");
                    Log.e(TAG, t.toString());


                }


            });

        } else {
            alert("Check Internet Conncection");


        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void Salesperson() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setTitle(" Loading");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);

        Call<JsonArray> call = apiService.getsalespersonlist(iduser);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                try {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

                    Log.d("URL", String.valueOf(response.code()));
                    Log.d("response", response.body().toString());

                    JSONArray array = new JSONArray(response.body().toString());

                    salesid = new String[array.length()];
                    salesname = new String[array.length()];

                    if (array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object1 = array.getJSONObject(i);
                            salesid[i] = object1.getString("idValue");
                            salesname[i] = object1.getString("nameValue");
                            ArrayAdapter<String> adaptersales = new ArrayAdapter<String>(CustomerDetails.this, android.R.layout.simple_spinner_item, salesname);
                            adaptersales.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sales_spin.setAdapter(adaptersales);

                        }
                    } else {
                        alert("No Records has found");
//                        Toast.makeText(CustomerDetails.this, "No Records has found", Toast.LENGTH_SHORT).show();
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(CustomerDetails.this);
        builder.setTitle(reason);
//        builder.setMessage(reason);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
//                finish();
            }
        });
        builder.create().show();


    }


}

