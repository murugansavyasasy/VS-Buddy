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

    private List<Zero_activity_DataClass> displayList;
    private List<Zero_activity_DataClass> originalList;
    Context context;

    public Zero_Activity_Adapter(List<Zero_activity_DataClass> datasList, Context context) {
        this.displayList = new ArrayList<>(datasList);
        this.originalList = new ArrayList<>(datasList);
        this.context = context;
    }

    public void setData(List<Zero_activity_DataClass> newList) {
        originalList.clear();
        displayList.clear();

        originalList.addAll(newList);
        displayList.addAll(newList);

        notifyDataSetChanged();
    }




    public void updateList(List<Zero_activity_DataClass> temp) {
        originalList.clear();
        displayList.clear();

        originalList.addAll(temp);
        displayList.addAll(temp);

        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.zero_activity_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Zero_activity_DataClass datas = displayList.get(position);

        holder.tvSchoolName.setText(datas.getSchoolName());
        holder.txtSchoolid.setText(datas.getSchoolId());
        holder.txtsatatus.setText(datas.getStatus());
        holder.txtsalesname.setText(datas.getSalesname());

        // WEB
        if (datas.getWeblastuse() != null && !datas.getWeblastuse().isEmpty()) {
            holder.lnrWeb.setVisibility(View.VISIBLE);
            holder.txtwebusage.setText(datas.getWeblastuse());
        } else {
            holder.lnrWeb.setVisibility(View.GONE);
        }

        // APP
        if (datas.getApplastuse() != null && !datas.getApplastuse().isEmpty()) {
            holder.lnrApp.setVisibility(View.VISIBLE);
            holder.txtappusage.setText(datas.getApplastuse());
        } else {
            holder.lnrApp.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }


    public void filter(String searchText, String statusFilter) {

        displayList.clear();

        String search = searchText == null ? "" : searchText.trim().toLowerCase();
        String status = statusFilter == null ? "" : statusFilter.trim().toLowerCase();

        for (Zero_activity_DataClass d : originalList) {

            // ✅ STATUS FILTER
            if (!status.isEmpty()) {
                if (d.getStatus() == null ||
                        !d.getStatus().trim().toLowerCase().equals(status)) {
                    continue; // skip this item
                }
            }

            // ✅ SEARCH FILTER
            String combined =
                    safe(d.getSchoolId()) +
                            safe(d.getSchoolName()) +
                            safe(d.getSalesname()) +
                            safe(d.getStatus()) +
                            safe(d.getApplastuse()) +
                            safe(d.getWeblastuse());

            if (search.isEmpty() || combined.contains(search)) {
                displayList.add(d);
            }
        }

        notifyDataSetChanged();
    }

    public int getFilteredCount() {
        return displayList.size();
    }

    private String safe(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvSchoolName, txtSchoolid, txtsatatus, txtappusage, txtwebusage, txtsalesname;
        LinearLayout lnrWeb, lnrApp;

        MyViewHolder(View view) {
            super(view);
            tvSchoolName = view.findViewById(R.id.schoolname);
            txtSchoolid = view.findViewById(R.id.schoolid);
            txtsatatus = view.findViewById(R.id.txt_status);
            txtappusage = view.findViewById(R.id.txt_apptiming);
            txtwebusage = view.findViewById(R.id.txt_webtime);
            txtsalesname = view.findViewById(R.id.salesname);
            lnrWeb = view.findViewById(R.id.lnrWebLbl);
            lnrApp = view.findViewById(R.id.lnrAppLbl);
        }
    }
}
