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

import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.Usagecountclass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class Usagecountadapter extends RecyclerView.Adapter<Usagecountadapter.MyViewHolder> {
    private List<Usagecountclass> datasListschool;
    String schoolid, schoolname;
    Context context;

    public Usagecountadapter(ArrayList<Usagecountclass> datasListschool, String schoolid, String schoolname, Context context) {
        this.datasListschool = datasListschool;
        this.schoolid = schoolid;
        this.schoolname = schoolname;
        this.context = context;

    }

    @Override
    public Usagecountadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.usagecount, parent, false);


        return new Usagecountadapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Usagecountadapter.MyViewHolder holder, int position) {
        Usagecountclass dataschool = datasListschool.get(position);

//        holder.bind(datasListschool.get(position), listener);
        holder.tvtotvoiceusagecount.setText(dataschool.getTotal_voiceusage());
        holder.lblVoiceAllocated.setText(dataschool.getVoiceAllocated());
        holder.tvtotsmsusagecount.setText(dataschool.getTotal_smsusage());
        holder.lblSMS_Allocated.setText(dataschool.getSMSAllocated());

        if (dataschool.getErp_module_usage() != null) {
            holder.erp_module_usage.setText(dataschool.getErp_module_usage());
        } else {
            holder.erp_module_usage.setText("");
        }

        if (!dataschool.getAppUsage().equals("")) {
            holder.lnrAppUsage.setVisibility(View.VISIBLE);
            holder.app_usage_count.setText(dataschool.getAppUsage());
        } else {
            holder.lnrAppUsage.setVisibility(View.GONE);
        }

        holder.tvschoolId.setText(schoolid);
        holder.tvschoolnamelist.setText(schoolname);
    }

    @Override
    public int getItemCount() {
        return datasListschool.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView erp_module_usage, tvschoolId, tvschoolnamelist, tvtotvoiceusagecount, tvtotsmsusagecount, app_usage_count, lblSMS_Allocated, lblVoiceAllocated;

        public LinearLayout lnrAppUsage, lnrSmsAllocated;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvschoolId = (TextView) itemView.findViewById(R.id.usage_schoolid);
            tvschoolnamelist = (TextView) itemView.findViewById(R.id.usage_listschoolname);
            tvtotvoiceusagecount = (TextView) itemView.findViewById(R.id.usage_totalvoicecount);
            tvtotsmsusagecount = (TextView) itemView.findViewById(R.id.usage_totalsmscount);
            lblSMS_Allocated = (TextView) itemView.findViewById(R.id.lblSMS_Allocated);
            lblVoiceAllocated = (TextView) itemView.findViewById(R.id.lblVoiceAllocated);
            app_usage_count = (TextView) itemView.findViewById(R.id.app_usage_count);
            lnrAppUsage = (LinearLayout) itemView.findViewById(R.id.lnrAppUsage);
            lnrSmsAllocated = (LinearLayout) itemView.findViewById(R.id.lnrSmsAllocated);
            erp_module_usage = (TextView) itemView.findViewById(R.id.erp_module_usage);

        }
    }
}
