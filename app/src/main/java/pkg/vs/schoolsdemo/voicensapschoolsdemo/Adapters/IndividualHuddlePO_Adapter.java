package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.IndividualHuddle_PO;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

/**
 * Created by devi on 5/16/2019.
 */

public class IndividualHuddlePO_Adapter extends RecyclerView.Adapter<IndividualHuddlePO_Adapter.ViewHolder> {
    private ArrayList<IndividualHuddle_PO> CountryList;
    private static final String TAG =IndividualHuddlePO_Adapter .class.getSimpleName();
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Country1,Price1;

        public ViewHolder(View v) {
            super(v);
            Country1=(TextView)v.findViewById(R.id.txt_country);
            Price1=(TextView)v.findViewById(R.id.txt_price);
        }
    }
    public IndividualHuddlePO_Adapter(Context context, ArrayList<IndividualHuddle_PO> countryList) {
        this.CountryList = countryList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.huddle_popup, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final IndividualHuddle_PO customer1 = CountryList.get(position);
        holder.Country1.setText(customer1.getCountry());
        holder.Price1.setText(customer1.getRatePerMinuteInbound());

    }

    @Override
    public int getItemCount() {
        return CountryList.size();

    }

}
