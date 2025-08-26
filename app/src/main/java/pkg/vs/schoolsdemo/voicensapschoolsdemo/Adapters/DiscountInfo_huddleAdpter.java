package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.Discountinfo_huddle;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;


/**
 * Created by devi on 5/21/2019.
 */

public class DiscountInfo_huddleAdpter extends RecyclerView.Adapter<DiscountInfo_huddleAdpter.ViewHolder> {
    private ArrayList<Discountinfo_huddle> CountryList;
    private static final String TAG =IndividualHuddlePO_Adapter .class.getSimpleName();
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView AboveValue,Discountpercent;

        public ViewHolder(View v) {
            super(v);
            AboveValue=(TextView)v.findViewById(R.id.txt_above);
            Discountpercent=(TextView)v.findViewById(R.id.txt_discount);
        }
    }
    public DiscountInfo_huddleAdpter(Context context, ArrayList<Discountinfo_huddle> discountlist) {
        this.CountryList = discountlist;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.huddle_abovediscount, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Discountinfo_huddle customer1 = CountryList.get(position);
        holder.AboveValue.setText(customer1.getAboveValue());
        holder.Discountpercent.setText(customer1.getDiscountPercentage());
    }
    @Override
    public int getItemCount() {
        return CountryList.size();

    }

}
