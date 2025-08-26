package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.parentnoclass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

/**
 * Created by devi on 12/20/2016.
 */

public class parentnoadapter  extends RecyclerView.Adapter<parentnoadapter.MyViewHolder>  {

    private List<parentnoclass> datasList1;
    Context context;
    String text;


    public parentnoadapter(ArrayList<parentnoclass> datasList1, Context context) {
        this.datasList1 = datasList1;
        this.context = context;
    }
    @Override
    public parentnoadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listparentnodemo, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final parentnoadapter.MyViewHolder holder, final int position) {
        parentnoclass datas1 = datasList1.get(position);

        holder.bind(datasList1.get(position));

        holder.tvPhonenu.setText(datas1.getStrDemoPhonenumber());


        holder.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvPhonenu.setText("");
                datasList1.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datasList1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvPhonenu,tvadd;
        public ImageButton delbtn;

        public MyViewHolder(View view) {
            super(view);
            tvPhonenu = (TextView) view.findViewById(R.id.phoneno);
            delbtn=(ImageButton) view.findViewById(R.id.delete);

        }

        public void bind(parentnoclass parentnoclass) {


        }
    }
}
