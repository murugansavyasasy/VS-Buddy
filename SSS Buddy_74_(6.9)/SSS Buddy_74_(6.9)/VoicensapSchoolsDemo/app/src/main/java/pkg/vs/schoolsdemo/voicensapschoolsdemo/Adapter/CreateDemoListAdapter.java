package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter;

import static pkg.vs.schoolsdemo.voicensapschoolsdemo.util.Util_common.MENU_VOICE;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.Recordwelcomefile;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.SendSmsActivity;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.CreateDemoListClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.OnDemoClickListener;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

/**
 * Created by devi on 12/8/2016.
 */

public class CreateDemoListAdapter extends RecyclerView.Adapter<CreateDemoListAdapter.MyViewHolder>  {

    private List<CreateDemoListClass> datasList;
    Context context;
    private OnDemoClickListener listener;
    String text;


    public CreateDemoListAdapter(ArrayList<CreateDemoListClass> datasList, Context context, OnDemoClickListener listener) {
        this.datasList = datasList;
        this.context = context;
        this.listener = listener;
    }
    @Override
    public CreateDemoListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listcreatedemodetails, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CreateDemoListAdapter.MyViewHolder holder, int position) {

        final CreateDemoListClass datas = datasList.get(position);

        holder.bind(datasList.get(position), listener);

        holder.tvdemoid.setText(datas.getStrDemoId());
        holder.tvSchoolName.setText(datas.getStrDemoSchoolName());
        holder.tvPrincipalNumber.setText(datas.getStrDemoPrincipalNumber());
        holder.tvParentNumber.setText(datas.getStrDemoParentNumbercount());
//        holder.tvEmailId.setText(datas.getStrDemoEmailId());

        holder.SendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sms=new Intent(context,SendSmsActivity.class);
                sms.putExtra("ID",datas.getStrDemoId());
                Log.d("iddemo",datas.getStrDemoId());
                context.startActivity(sms);

            }
        });
        holder.sendvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent voice=new Intent(context, Recordwelcomefile.class);
                voice.putExtra("Requestcode",MENU_VOICE);
                voice.putExtra("demoid",datas.getStrDemoId());
                context.startActivity(voice);

            }
        });

    }




    @Override
    public int getItemCount() {
        return datasList.size();
    }

    public void updateList(List<CreateDemoListClass> temp) {
        datasList = temp;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSchoolName, tvPrincipalNumber, tvParentNumber, tvdemoid,tvEmailId, tvNoservices;
        public Button SendSms,sendvoice;

        public MyViewHolder(View view) {
            super(view);
            tvdemoid=(TextView)view.findViewById(R.id.demoid);
            tvSchoolName = (TextView) view.findViewById(R.id.schoolnamedemo);
            tvPrincipalNumber = (TextView) view.findViewById(R.id.principipalnodemo);
            tvParentNumber = (TextView) view.findViewById(R.id.parentnodemo);

            SendSms = (Button) view.findViewById(R.id.SendSms);
            sendvoice = (Button) view.findViewById(R.id.sendvoice);

//            tvParentNumbercount= (TextView) view.findViewById(R.id.parentnodemo);
//            tvEmailId=(TextView) view.findViewById(R.id.demoprincipalemail);

        }



        public void bind(final CreateDemoListClass item, final OnDemoClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDemoClick(item);

                }
            });
        }
    }
}
