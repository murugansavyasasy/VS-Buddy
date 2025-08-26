package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters;

import static android.view.Gravity.CENTER;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.AlertClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;


public class AlertNotificationAdapter extends RecyclerView.Adapter<AlertNotificationAdapter.MyViewHolder> {
    private List<AlertClass.DataBean>listalert;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView lblReferID,lblAlertType,lblAlertTitle,lblAlertContent,lblCreatedOn,lblCreatedFrom,lblExtractInfo;
        Button btnExtractInfo1,btnExtractInfo2;
        public MyViewHolder(View v) {
            super(v);

            lblReferID = (TextView) v.findViewById(R.id.lblReferID);
            lblAlertType = (TextView) v.findViewById(R.id.lblAlertType);
            lblAlertTitle = (TextView) v.findViewById(R.id.lblAlertTitle);
            lblAlertContent = (TextView) v.findViewById(R.id.lblAlertContent);
            lblCreatedOn = (TextView) v.findViewById(R.id.lblCreatedOn);
            lblCreatedFrom = (TextView) v.findViewById(R.id.lblCreatedFrom);
            btnExtractInfo1 = (Button) v.findViewById(R.id.btnExtractInfo1);
            btnExtractInfo2 = (Button) v.findViewById(R.id.btnExtractInfo2);

        }
    }
    public AlertNotificationAdapter(Context context,  List<AlertClass.DataBean> listalert) {
        this.listalert = listalert;
        this.context = context;
    }
    @Override
    public AlertNotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_alert_notification_screen, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final AlertClass.DataBean  details = listalert.get(position);
//
        Log.d("ReferID",details.getAlert_content());
        holder.lblReferID.setText(String.valueOf(details.getId()));
        holder.lblAlertType.setText(details.getAlert_type());
        holder.lblAlertTitle.setText(details.getAlert_title());
        holder.lblAlertContent.setText(details.getAlert_content());
        holder.lblCreatedOn.setText(details.getCreated_on());
        holder.lblCreatedFrom.setText(details.getCreated_from());

        holder.btnExtractInfo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(details.getExtrainfo1()!=null){
                    showpopup1(v,details.getExtrainfo1());
                }else{
                    showpopup1(v,"No Data Received , null");
                }


            }
        });

        holder.btnExtractInfo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(details.getExtrainfo2()!= null){
                    showpopup1(v,details.getExtrainfo2());
                }else{
                    showpopup1(v,"No Data Received , null");
                }

            }
        });

    }


    private void showpopup1(final View v,String ExtraInformation) {

        View popupView = LayoutInflater.from(v.getContext()).inflate(R.layout.popup_alert_notification, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, MATCH_PARENT, MATCH_PARENT, true);
        popupWindow.showAtLocation(v, CENTER, 0, 0);

        TextView ExtractInfo = (TextView) popupView.findViewById(R.id.lblExtractInfo);
        ImageView imgClose = (ImageView) popupView.findViewById(R.id.imgClose);

        ExtractInfo.setText(ExtraInformation);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


    }


    @Override
    public int getItemCount() {
        return listalert.size();

    }

}





