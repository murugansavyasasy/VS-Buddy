package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.Report_Delete;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.Report_Details;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.Report_Edit;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.Report_localExpenseClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;


/**
 * Created by devi on 4/23/2019.
 */
public class ReportLocalAdapter extends RecyclerView.Adapter<ReportLocalAdapter.ViewHolder> {
    private ArrayList<Report_localExpenseClass> reportlist;
    Context context;
    String usertpye_id;

    ExpandableListView expandableListView;


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Title1, Title2, Title3, Title4, Title5, Title6, Title7;
        Button edit, delete, Details;
//        Button approve, decline ;


        public ViewHolder(View v) {
            super(v);
            Title1 = (TextView) v.findViewById(R.id.Refer_id);
            Title2 = (TextView) v.findViewById(R.id.Employee);
            Title3 = (TextView) v.findViewById(R.id.monthclaim);
            Title4 = (TextView) v.findViewById(R.id.Amount);
            Title5 = (TextView) v.findViewById(R.id.Description);
            Title6 = (TextView) v.findViewById(R.id.idlocalexpense);
            Title7 = (TextView) v.findViewById(R.id.Status);

            edit = (Button) v.findViewById(R.id.btn_Edit);
            delete = (Button) v.findViewById(R.id.btn_delete);
            Details = (Button) v.findViewById(R.id.btn_Details);
//            approve = (Button) v.findViewById(R.id.btn_Approve);
//            decline = (Button) v.findViewById(R.id.btn_decline);


        }
    }

    public ReportLocalAdapter(Context context, ArrayList<Report_localExpenseClass> getList) {
        this.reportlist = getList;
        this.context = context;
    }

    @Override
    public ReportLocalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Report_localExpenseClass details = reportlist.get(position);
        holder.Title1.setText(details.getRefId());
        holder.Title2.setText(details.getUsername());
        holder.Title3.setText(details.getMonthOfClaim());
        holder.Title4.setText(details.getTotalLocalExpense());
        holder.Title5.setText(details.getDescription());
        holder.Title6.setText(details.getIdLocalExpense());
        holder.Title7.setText(details.getStatus());


        usertpye_id = SharedPreference_class.getSh_v_Usertype(context);
        Log.d("usertype", usertpye_id);

        int approved = details.getIsApproved();

        if ((usertpye_id.equals("3") && (approved == 0))) {

//                holder.approve.setVisibility(View.VISIBLE);
//                holder.decline.setVisibility(View.VISIBLE);
            Log.d("Approve", String.valueOf(approved));
            holder.Details.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.GONE);
            holder.edit.setVisibility(View.GONE);
        } else if ((!usertpye_id.equals("3") && (approved == 0) || (approved == 2))) {

            holder.delete.setVisibility(View.VISIBLE);
            holder.edit.setVisibility(View.VISIBLE);
            holder.Details.setVisibility(View.VISIBLE);
        } else {
//                holder.Details.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.GONE);
            holder.edit.setVisibility(View.GONE);
//                holder.approve.setVisibility(View.GONE);
//                holder.decline.setVisibility(View.GONE);
        }

        holder.Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Report_Details.class);
                intent.putExtra("idlocal", details);
               // intent.putExtra("Billfilepath", details.getFilepath());
                context.startActivity(intent);


            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Report_Edit.class);
                intent.putExtra("idlocal", details);
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.alert_2);
                builder.setTitle("Warning!");
                builder.setMessage("Are You Sure,You Want to Delete the Expense!");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(v.getContext(), Report_Delete.class);
                        i.putExtra("idlocal", details);
                        context.startActivity(i);
                    }
                });
                builder.setNegativeButton("No!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();

            }
        });


    }

    public void updateList(List<Report_localExpenseClass> data) {
        reportlist = (ArrayList<Report_localExpenseClass>) data;
//       this.notifyDataSetChanged();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return reportlist.size();

    }

}





