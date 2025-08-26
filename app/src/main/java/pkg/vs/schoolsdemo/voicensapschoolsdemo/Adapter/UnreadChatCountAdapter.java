package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.ChatScreenActivity;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.parentnoclass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.UnreadChatModel;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class UnreadChatCountAdapter extends RecyclerView.Adapter<UnreadChatCountAdapter.MyViewHolder>  {

    private List<UnreadChatModel> datasList1;
    Context context;
    String text;


    public UnreadChatCountAdapter(ArrayList<UnreadChatModel> datasList1, Context context) {
        this.datasList1 = datasList1;
        this.context = context;
    }
    @Override
    public UnreadChatCountAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.unread_chat_list, parent, false);

        return new UnreadChatCountAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UnreadChatCountAdapter.MyViewHolder holder, final int position) {
        final UnreadChatModel datas1 = datasList1.get(position);

        holder.lblSchoolName.setText(datas1.getCustomerName());
        holder.lblPoNumber.setText(datas1.getPoNumber());
        holder.lblUnreadCount.setText(datas1.getUnReadCount());

        holder.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatScreen=new Intent(context,ChatScreenActivity.class);
                chatScreen.putExtra("POID",datas1.getPOID());
                context.startActivity(chatScreen);
            }
        });




    }

    @Override
    public int getItemCount() {
        return datasList1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView lblSchoolName,lblPoNumber,lblUnreadCount;
        public RelativeLayout rytParent;
        public Button btnChat;


        public MyViewHolder(View view) {
            super(view);
            lblSchoolName = (TextView) view.findViewById(R.id.lblSchoolName);
            lblPoNumber = (TextView) view.findViewById(R.id.lblPoNumber);
            lblUnreadCount = (TextView) view.findViewById(R.id.lblUnreadCount);
            rytParent = (RelativeLayout) view.findViewById(R.id.rytParent);
            btnChat = (Button) view.findViewById(R.id.btnChat);



        }

        public void bind(parentnoclass parentnoclass) {


        }
    }
}

