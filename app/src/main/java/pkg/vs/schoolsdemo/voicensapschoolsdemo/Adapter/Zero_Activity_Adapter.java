package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.CreateDemoListClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.Zero_activity_DataClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.OnDemoClickListener;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class Zero_Activity_Adapter extends RecyclerView.Adapter<Zero_Activity_Adapter.MyViewHolder> {

    private List<Zero_activity_DataClass> datasList;
    Context context;
    Date NewAppdate, Newwebdate;
    public Zero_Activity_Adapter(ArrayList<Zero_activity_DataClass> datasList, Context context) {
        this.datasList = datasList;
        this.context = context;
        this.notifyDataSetChanged();
        notifyDataSetChanged();
    }


    @Override
    public Zero_Activity_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.zero_activity_layout, parent, false);
        return new Zero_Activity_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Zero_Activity_Adapter.MyViewHolder holder, int position) {

        final Zero_activity_DataClass datas = datasList.get(position);

        holder.tvSchoolName.setText(datas.getSchoolName());
        holder.txtSchoolid.setText(datas.getSchoolId());
        holder.txtsatatus.setText(datas.getStatus());
        holder.txtsalesname.setText(datas.getSalesname());

        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        if (!datas.getWeblastuse().isEmpty() || !datas.getWeblastuse().equals("")) {
            holder.lnrWeb.setVisibility(View.VISIBLE);
            String Webdate = datas.getWeblastuse();
            try {
                Newwebdate = spf.parse(Webdate);
            } catch (Exception e) {
                Log.d("Exception", String.valueOf(e));
            }
            spf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            String WebDate = spf.format(Newwebdate);
            holder.txtwebusage.setText(WebDate);
        } else {
            holder.lnrWeb.setVisibility(View.GONE);
        }

        if (!datas.getApplastuse().isEmpty() || !datas.getApplastuse().equals("")) {
            holder.lnrApp.setVisibility(View.VISIBLE);
            String Appdate = datas.getApplastuse();
            SimpleDateFormat AppdateDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                NewAppdate = AppdateDF.parse(Appdate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            spf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            String AppDate = spf.format(NewAppdate);
            holder.txtappusage.setText(AppDate);
        } else {
            holder.lnrApp.setVisibility(View.GONE);
        }

        if ((!datas.getApplastuse().isEmpty() || !datas.getApplastuse().equals("")) ||
                (!datas.getWeblastuse().isEmpty() || !datas.getWeblastuse().equals(""))) {
            holder.lnrViewLbl.setVisibility(View.VISIBLE);
        } else {
            holder.lnrViewLbl.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return datasList.size();
    }

    public void updateList(List<Zero_activity_DataClass> temp) {
        datasList = temp;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSchoolName, txtSchoolid, txtsatatus, txtappusage, txtwebusage, txtsalesname;

        CardView card_Schoollist;
        LinearLayout lnrViewLbl, lnrWeb, lnrApp;


        public MyViewHolder(View view) {
            super(view);

            tvSchoolName = view.findViewById(R.id.schoolname);
            txtSchoolid = view.findViewById(R.id.schoolid);
            txtsatatus = view.findViewById(R.id.txt_status);
            txtappusage = view.findViewById(R.id.txt_apptiming);
            txtwebusage = view.findViewById(R.id.txt_webtime);
            txtsalesname = view.findViewById(R.id.salesname);
            card_Schoollist = view.findViewById(R.id.card_Schoollist);

            lnrViewLbl = view.findViewById(R.id.lnrViewLbl);
            lnrWeb = view.findViewById(R.id.lnrWebLbl);
            lnrApp = view.findViewById(R.id.lnrAppLbl);

        }


//        public void bind(final CreateDemoListClass item, final OnDemoClickListener listener) {
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.onDemoClick(item);
//
//                }
//            });
//        }
    }
}
