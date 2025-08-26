package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.Supportverifylistclass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.OnPOCSupportClickListener;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

/**
 * Created by devi on 12/13/2017.
 */

public class Supportverifylistadapter extends RecyclerView.Adapter<Supportverifylistadapter.MyViewHolder> {

private List<Supportverifylistclass> datasListpoc;
        Context context;
private OnPOCSupportClickListener listener;
        String password;


public Supportverifylistadapter(ArrayList<Supportverifylistclass> datasListpoc, String password, Context context, OnPOCSupportClickListener listener) {
        this.datasListpoc = datasListpoc;
        this.context = context;
        this.listener = listener;
        this.password = password;
        }

@Override
public Supportverifylistadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.supportlist, parent, false);
        return new Supportverifylistadapter.MyViewHolder(itemView);
        }

@Override
public void onBindViewHolder(Supportverifylistadapter.MyViewHolder holder, int position) {

        Supportverifylistclass dataschoolpoc = datasListpoc.get(position);
//    "SchoolID": "1000",
//            "SchoolName": "djsfddfhj",
//            "schoolAddress": "ghghg",
//            "City": "hjhj",
//            "PinCode": "102100",
//            "SchoolMobile": "7777777777",
//            "SchoolEmail": "ytyy",
//            "ContactPerson": "tyty",
//            "FromDate": "Dec 12, 2017",
//            "ToDate": "Dec 12, 2018",
//            "SMSCaption": "tytyty",
//            "POCCreatedOn": "Dec 13, 2017",
//            "isCallsUnlimited": "Unlimited",
//            "isSMSUnlimited": "",
//            "SMSCount": 500,
//            "CallCount": 0,
//            "POCCreatedBy": "kdp",
//            "WelcomefileText": "dfgg"

        holder.bind(datasListpoc.get(position), listener);
        holder.tvPOCid.setText(dataschoolpoc.getStrPOCsupportschoolid());
        holder.tvPOCby.setText(dataschoolpoc.getStrPOCsupportcreatedby());
        holder.tvPOCschoolname.setText(dataschoolpoc.getStrPOCsupportschoolname());
        holder.tvPOCSchooladdr.setText(dataschoolpoc.getStrPOCsupportSchooladdr());
        holder.tvPOCphno.setText(dataschoolpoc.getStrPOCsupportmobno());
        holder.tvPOCemailid.setText(dataschoolpoc.getStrPOCsupportemailid());
        holder.tvPOCprinciname.setText(dataschoolpoc.getStrsupportContactPerson());
//        holder.tvPOCmobno.setText(dataschoolpoc.getStrPOCmobno());
        holder.tvPOCfromdate.setText(dataschoolpoc.getStrPOCsupportfromdate());
        holder.tvPOCtodate.setText(dataschoolpoc.getStrPOCsupporttodate());
        holder.tvcallcredits.setText(dataschoolpoc.getStrPOCsupportisCallsUnlimited());
        holder.tvsmscredits.setText(dataschoolpoc.getStrPOCsupportisSMSUnlimited());
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
    TextView tvPOCid, tvPOCby, tvPOCschoolname, tvPOCSchooladdr, tvPOCphno, tvPOCemailid, tvPOCprinciname,
            tvPOCmobno, tvPOCfromdate, tvPOCtodate, tvPOCremarks, tvPOCmodule, tvPOCdbrequried, tvPOCdemo, tvPOCscope, tvsmscredits, tvcallcredits;

    public MyViewHolder(View view) {
        super(view);
        tvPOCid = (TextView) view.findViewById(R.id.POCid_support);
        tvPOCby = (TextView) view.findViewById(R.id.POCby_support);
        tvPOCschoolname = (TextView) view.findViewById(R.id.POClistschoolname_support);
        tvPOCSchooladdr = (TextView) view.findViewById(R.id.POCcity_support);
        tvPOCfromdate = (TextView) view.findViewById(R.id.POCfromdate_support);
        tvPOCtodate = (TextView) view.findViewById(R.id.POCtodate_support);
        tvPOCemailid = (TextView) view.findViewById(R.id.POCemailid_support);
        tvPOCmobno = (TextView) view.findViewById(R.id.POCmobno_support);
        tvPOCphno = (TextView) view.findViewById(R.id.POCphno_support);
        tvPOCprinciname = (TextView) view.findViewById(R.id.POCprinciname_support);
        tvsmscredits = (TextView) view.findViewById(R.id.POCsmscredits_support);
        tvcallcredits = (TextView) view.findViewById(R.id.POCcallcredits_support);
//            tvPOCremarks = (TextView) view.findViewById(R.id.POCremarks);
//            tvPOCmodule = (TextView) view.findViewById(R.id.POCmodule);
//            tvPOCdbrequried = (TextView) view.findViewById(R.id.POCdbrequried);
//            tvPOCdemo = (TextView) view.findViewById(R.id.POCdemo);
//            tvPOCscope = (TextView) view.findViewById(R.id.POCscope);

    }

    public void bind(final Supportverifylistclass item, final OnPOCSupportClickListener listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPOCsupportClick(item);

            }
        });
//
//
    }
}
}
