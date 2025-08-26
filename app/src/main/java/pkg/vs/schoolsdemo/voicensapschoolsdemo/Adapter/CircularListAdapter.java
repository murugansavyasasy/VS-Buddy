
/**
 * Created by devi on 7/8/2019.
 */


package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.CircularListClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

/**
 * Created by devi on 4/1/2017.
 */

public class CircularListAdapter extends RecyclerView.Adapter<CircularListAdapter.MyViewHolder> {
    private List<CircularListClass> circulardatalist;

    public CircularListAdapter(ArrayList<CircularListClass> circulardatalist) {
        this.circulardatalist = circulardatalist;

    }

    @Override
    public CircularListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.circular_list, parent, false);
        return new CircularListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CircularListAdapter.MyViewHolder holder, int position) {

        CircularListClass circularschool = circulardatalist.get(position);

//        holder.bind(circularschool.get(position), listener);
        holder.tvcircularschoolname.setText(circularschool.getStrcircularschoolname());
        holder.tvcircularschoolid.setText(circularschool.getStrcircularschoolId());
        holder.tvcircularid.setText(circularschool.getStrcircularid() + " " + "@" + circularschool.getStrEODTiming());
        holder.tvcircularcount.setText(circularschool.getStrcircularcount());

        holder.tvcircularattended.setText(circularschool.getStrcircularattended());
        holder.tvcircularmissed.setText(circularschool.getStrcircularmissed());
        holder.tvcircularserverResponsecount.setText(circularschool.getStrserverresponcecount());
//        holder.tvcirularEODTiming.setText(circularschool.getStrEODTiming());
    }

    @Override
    public int getItemCount() {
        return circulardatalist.size();
    }

    public void clearAllData() {
        int size = this.circulardatalist.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.circulardatalist.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvcircularschoolname, tvcircularschoolid, tvcircularid, tvcircularcount, tvcircularattended, tvcircularmissed, tvcircularserverResponsecount,
                tvcirularEODTiming;

        public MyViewHolder(View view) {
            super(view);
            tvcircularschoolname = (TextView) view.findViewById(R.id.circulartlistschoolname);
            tvcircularschoolid = (TextView) view.findViewById(R.id.circulartlistschoolid);
            tvcircularid = (TextView) view.findViewById(R.id.circularid);
            tvcircularcount = (TextView) view.findViewById(R.id.circularcount);
            tvcircularattended = (TextView) view.findViewById(R.id.circularAttended);
            tvcircularmissed = (TextView) view.findViewById(R.id.circularMissed);
            tvcircularserverResponsecount = (TextView) view.findViewById(R.id.circularServerResponceCount);
//            tvcirularEODTiming=(TextView) view.findViewById(R.id.circularEODTiming);
        }
    }
}


