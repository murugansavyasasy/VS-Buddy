package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.Seniormgtnumberclass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

/**
 * Created by devi on 5/2/2018.
 */

public class Seniormgtnumberlist extends RecyclerView.Adapter<Seniormgtnumberlist.MyViewHolder> {
    private List<Seniormgtnumberclass> datasListschool;
    Context context;
    String schoolid,schoolname;

    public Seniormgtnumberlist(ArrayList<Seniormgtnumberclass> datasListschool,Context context) {
        this.datasListschool = datasListschool;
//        this.schoolid = schoolid;
//        this.schoolname = schoolname;
        this.context = context;
}

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.seniormgtnumbers, parent, false);
        return new Seniormgtnumberlist.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Seniormgtnumberclass seniorschool = datasListschool.get(position);

//        holder.bind(datasListschool.get(position), listener);
        holder.tvMemberName.setText(seniorschool.getMemberName());
        holder.tvAppPassword.setText(seniorschool.getAppPassword());
        holder.tvMemberType.setText(seniorschool.getMemberType());
        holder.tvDesignation.setText(seniorschool.getDesignation());
        holder.tvIVRPassword.setText(seniorschool.getIVRPassword());
        holder.tvmobileno.setText(seniorschool.getMobileno());
    }

    @Override
    public int getItemCount() {
        return datasListschool.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMemberName, tvAppPassword,tvMemberType,tvDesignation,tvIVRPassword,tvmobileno;
        public MyViewHolder(View view) {
            super(view);
            tvmobileno=(TextView)view.findViewById(R.id.snrmgt_mobno);
            tvMemberName=(TextView)view.findViewById(R.id.snrmgt_membername);
            tvAppPassword=(TextView)view.findViewById(R.id.snrmgt_apppassword);
            tvMemberType=(TextView)view.findViewById(R.id.snrmgt_membertype);
            tvDesignation=(TextView)view.findViewById(R.id.snrmgt_designation);
            tvIVRPassword  =(TextView)view.findViewById(R.id.snrmgt_ivrpassword);

        }
    }
}
