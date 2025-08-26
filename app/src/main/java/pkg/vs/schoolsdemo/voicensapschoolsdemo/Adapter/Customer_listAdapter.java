package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.CustomerListClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;


/**
 * Created by devi on 4/23/2019.
 */
public class Customer_listAdapter extends RecyclerView.Adapter<Customer_listAdapter.ViewHolder> {
    private ArrayList<CustomerListClass> CustomerList;
    Context context;

    public void updateList(List<CustomerListClass> data) {
        CustomerList = (ArrayList<CustomerListClass>) data;
        this.notifyDataSetChanged();
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Title1,Title4, Title5, Title6, Title7,Title8;
        LinearLayout Layout;


        public ViewHolder(View v) {
            super(v);
            Layout=(LinearLayout) v.findViewById(R.id.layout_details);
            Title1 = (TextView) v.findViewById(R.id.recycle_customname);
//            Title2 = (TextView) v.findViewById(R.id.recycle_schoolid);
            Title4= (TextView) v.findViewById(R.id.recycle_billing);
            Title5 = (TextView) v.findViewById(R.id.recycle_tallyuser);
            Title6= (TextView) v.findViewById(R.id.recycle_contact_type);
            Title7 = (TextView) v.findViewById(R.id.recyle_salesperson);
            Title8 = (TextView) v.findViewById(R.id.recycle_customerid);
        }
    }

    public Customer_listAdapter(Context context, ArrayList<CustomerListClass> getList) {
        this.CustomerList = getList;
        this.context = context;
    }

    @Override
    public Customer_listAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customerlist, parent, false);
        return new ViewHolder(view);
    }




    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final CustomerListClass customer = CustomerList.get(position);
        holder.Title1.setText(customer.getCustomerName()+ " - " + "("+customer.getSchoolServerId()+")");
//        holder.Title2.setText(customer.getSchoolServerId());
        holder.Title4.setText(customer.getBillingCity());
        holder.Title5.setText(customer.getTallyCustomerId());
        holder.Title6.setText(customer.getContactPerson());
        holder.Title7.setText(customer.getSalesPersonName());
        holder.Title8.setText(customer.getIdCustomer());

//        TextView billingcity=holder.Title4;
//        Billing_city=customer.getBillingCity();
//
//
//
//        if (Billing_city.isEmpty()){
//            billingcity.setVisibility(View.GONE);
//
//        }else {
//            billingcity.setVisibility(View.VISIBLE);
//        }

        holder.Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                    Intent intent = new Intent(v.getContext(), IndividualCustomer_Info.class);
//                     intent.putExtra("customeridetails",customer);
//                    context.startActivity(intent);
            }
        });


    }




    @Override
    public int getItemCount() {
        return CustomerList.size();


    }

}





