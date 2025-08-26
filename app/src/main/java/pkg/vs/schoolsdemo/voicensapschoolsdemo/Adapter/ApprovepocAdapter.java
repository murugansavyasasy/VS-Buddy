package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.Approvepocclass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.OnPOCClickListener;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

/**
 * Created by devi on 1/27/2017.
 */

public class ApprovepocAdapter extends RecyclerView.Adapter<ApprovepocAdapter.MyViewHolder> {

    private List<Approvepocclass> datasListpoc;
    Context context;
    private OnPOCClickListener listener;
    String password;


    public ApprovepocAdapter(ArrayList<Approvepocclass> datasListpoc, String password, Context context, OnPOCClickListener listener) {
        this.datasListpoc = datasListpoc;
        this.context = context;
        this.listener = listener;
        this.password = password;
    }

    @Override
    public ApprovepocAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_approvepoc, parent, false);
        return new ApprovepocAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ApprovepocAdapter.MyViewHolder holder, int position) {

        Approvepocclass dataschoolpoc = datasListpoc.get(position);

        holder.bind(datasListpoc.get(position), listener);
        holder.tvPOCid.setText(dataschoolpoc.getStrPOCid());
        holder.tvPOCby.setText(dataschoolpoc.getStrPOCby());
        holder.tvPOCschoolname.setText(dataschoolpoc.getStrPOCschoolname());
        holder.tvPOCSchooladdr.setText(dataschoolpoc.getStrPOCSchooladdr());
        holder.tvPOCphno.setText(dataschoolpoc.getStrPOCphno());
        holder.tvPOCemailid.setText(dataschoolpoc.getStrPOCemailid());
        holder.tvPOCprinciname.setText(dataschoolpoc.getStrPOCprinciname());
        holder.tvPOCmobno.setText(dataschoolpoc.getStrPOCmobno());
        holder.tvPOCfromdate.setText(dataschoolpoc.getStrPOCfromdate());
        holder.tvPOCtodate.setText(dataschoolpoc.getStrPOCtodate());
        holder.tvcallcredits.setText(dataschoolpoc.getCallcredits());
        holder.tvsmscredits.setText(dataschoolpoc.getSmscredit());
//        holder.tvPOCremarks.setText(dataschoolpoc.getStrPOCremarks());
//        holder.tvPOCmodule.setText(dataschoolpoc.getStrPOCmodule());
//        holder.tvPOCdbrequried.setText(dataschoolpoc.getStrPOCdbrequried());
//        holder.tvPOCdemo.setText(dataschoolpoc.getStrPOCdemo());
//        holder.tvPOCscope.setText(dataschoolpoc.getStrPOCscope());

    }

    @Override
    public int getItemCount() {
        return datasListpoc.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvPOCid,tvPOCby, tvPOCschoolname, tvPOCSchooladdr, tvPOCphno, tvPOCemailid, tvPOCprinciname,
                tvPOCmobno, tvPOCfromdate, tvPOCtodate, tvPOCremarks, tvPOCmodule, tvPOCdbrequried, tvPOCdemo, tvPOCscope,tvsmscredits,tvcallcredits;

        public MyViewHolder(View view) {
            super(view);
            tvPOCid = (TextView) view.findViewById(R.id.POCid);
            tvPOCby = (TextView) view.findViewById(R.id.POCby);
            tvPOCschoolname = (TextView) view.findViewById(R.id.POClistschoolname);
            tvPOCSchooladdr = (TextView) view.findViewById(R.id.POCcity);
            tvPOCfromdate = (TextView) view.findViewById(R.id.POCfromdate);
            tvPOCtodate = (TextView) view.findViewById(R.id.POCtodate);
            tvPOCemailid = (TextView) view.findViewById(R.id.POCemailid);
            tvPOCmobno= (TextView) view.findViewById(R.id.POCmobno);
            tvPOCphno = (TextView) view.findViewById(R.id.POCphno);
            tvPOCprinciname = (TextView) view.findViewById(R.id.POCprinciname);
            tvsmscredits = (TextView) view.findViewById(R.id.POCsmscredits);
            tvcallcredits = (TextView) view.findViewById(R.id.POCcallcredits);
//            tvPOCremarks = (TextView) view.findViewById(R.id.POCremarks);
//            tvPOCmodule = (TextView) view.findViewById(R.id.POCmodule);
//            tvPOCdbrequried = (TextView) view.findViewById(R.id.POCdbrequried);
//            tvPOCdemo = (TextView) view.findViewById(R.id.POCdemo);
//            tvPOCscope = (TextView) view.findViewById(R.id.POCscope);

        }

        public void bind(final Approvepocclass item, final OnPOCClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPOCClick(item);

                }
            });


        }
    }


}
