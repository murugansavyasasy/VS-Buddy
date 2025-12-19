package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.parentnoclass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.RequirementModel;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class RequirementsAdapter extends RecyclerView.Adapter<RequirementsAdapter.MyViewHolder>  {

    private List<RequirementModel> datasList1;
    Context context;
    String text;


    public RequirementsAdapter(ArrayList<RequirementModel> datasList1, Context context) {
        this.datasList1 = datasList1;
        this.context = context;
    }
    @Override
    public RequirementsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.require_list, parent, false);

        return new RequirementsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RequirementsAdapter.MyViewHolder holder, final int position) {
        RequirementModel datas1 = datasList1.get(position);

        holder.txtMessage.setText(datas1.getMessage());
        holder.txtTitle.setText(datas1.getTitle());
        holder.txtDateTime.setText(datas1.getDateTime());
        holder.txtName.setText(datas1.getSendBy());



    }

    @Override
    public int getItemCount() {
        return datasList1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtMessage,txtDateTime,txtTitle,txtName;


        public MyViewHolder(View view) {
            super(view);
            txtMessage = (TextView) view.findViewById(R.id.txtMessage);
            txtDateTime = (TextView) view.findViewById(R.id.txtDateTime);
            txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            txtName = (TextView) view.findViewById(R.id.txtName);


        }

        public void bind(parentnoclass parentnoclass) {


        }
    }
}

