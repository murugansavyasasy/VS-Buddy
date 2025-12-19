package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.parentnoclass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.ChatDataModel;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private List<ChatDataModel> datasList1;
    Context context;
    String text;


    public ChatAdapter(ArrayList<ChatDataModel> datasList1, Context context) {
        this.datasList1 = datasList1;
        this.context = context;
    }

    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_list_data, parent, false);

        return new ChatAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ChatAdapter.MyViewHolder holder, final int position) {
        ChatDataModel datas1 = datasList1.get(position);

        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/Arial-Regular.ttf");


        if (datas1.getChatSide().equals("right")) {
            holder.rytRight.setVisibility(View.VISIBLE);
            holder.rytLeft.setVisibility(View.GONE);
            holder.txtMessageRight.setText(datas1.getMessage());
            holder.MemberNameRight.setText(datas1.getName());
            holder.DateTimeRight.setText(datas1.getDateTime());
        } else {
            holder.rytRight.setVisibility(View.GONE);
            holder.rytLeft.setVisibility(View.VISIBLE);

            holder.txtMessage.setText(datas1.getMessage());
            holder.MemberName.setText(datas1.getName());
            holder.DateTime.setText(datas1.getDateTime());
        }

        holder.txtMessageRight.setTypeface(custom_font);
        holder.MemberNameRight.setTypeface(custom_font);
        holder.DateTimeRight.setTypeface(custom_font);


        holder.MemberName.setTypeface(custom_font);
        holder.txtMessage.setTypeface(custom_font);
        holder.DateTime.setTypeface(custom_font);


//        String initial= String.valueOf(datas1.getName().charAt(0));
//
//        if(initial.equalsIgnoreCase("a")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#E34545",holder);
//
//
//        }
//        else if(initial.equalsIgnoreCase("b")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#80E34545",holder);
//
//        }
//
//        if(initial.equalsIgnoreCase("c")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#ea921f",holder);
//
//        }
//        if(initial.equalsIgnoreCase("d")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#787887",holder);
//
//        }
//
//        if(initial.equalsIgnoreCase("e")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#b757d7",holder);
//
//        }
//
//        if(initial.equalsIgnoreCase("f")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#4949b1",holder);
//
//        }
//        if(initial.equalsIgnoreCase("g")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#ea1fa6",holder);
//
//        }
//        if(initial.equalsIgnoreCase("h")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#ea1f5f",holder);
//
//
//        }
//        if(initial.equalsIgnoreCase("i")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#4bea1f",holder);
//
//
//        }
//
//        if(initial.equalsIgnoreCase("j")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#ea951f",holder);
//
//
//        }
//
//        if(initial.equalsIgnoreCase("k")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#74749e",holder);
//
//
//        }
//
//        if(initial.equalsIgnoreCase("l")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#16161a",holder);
//
//
//        }
//        if(initial.equalsIgnoreCase("m")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#1feabe",holder);
//
//        }
//
//        if(initial.equalsIgnoreCase("n")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#df875e",holder);
//
//
//        }
//        if(initial.equalsIgnoreCase("o")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#1f4eea",holder);
//
//
//        }
//        if(initial.equalsIgnoreCase("p")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#1fea52",holder);
//
//
//        }
//        if(initial.equalsIgnoreCase("q")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#e95e6f",holder);
//
//
//        }
//        if(initial.equalsIgnoreCase("r")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#eab41f",holder);
//
//        }
//        if(initial.equalsIgnoreCase("s")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#1f1fea",holder);
//
//        }
//        if(initial.equalsIgnoreCase("t")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#69696b",holder);
//
//        }
//        if(initial.equalsIgnoreCase("u")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#101018",holder);
//
//        }
//
//        if(initial.equalsIgnoreCase("v")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#ea1f4e",holder);
//
//        }
//        if(initial.equalsIgnoreCase("w")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#4eea1f",holder);
//
//        }
//        if(initial.equalsIgnoreCase("x")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#95ea1f",holder);
//
//        }
//
//        if(initial.equalsIgnoreCase("y")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#363640",holder);
//
//        }
//
//        if(initial.equalsIgnoreCase("z")){
//            holder.lblInitial.setText(initial);
//            setbackGroundColor("#eae31f",holder);
//
//        }


    }

    private void setbackGroundColor(String s, MyViewHolder holder) {
        int color = Color.parseColor(s);
        ((GradientDrawable) holder.lnrInitial.getBackground()).setColor(color);
    }

    @Override
    public int getItemCount() {
        return datasList1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView lblInitial, MemberName, txtMessage, DateTime;
        public LinearLayout lnrInitial;
        public RelativeLayout rytLeft, rytRight;
        public TextView MemberNameRight, txtMessageRight, DateTimeRight;


        public MyViewHolder(View view) {
            super(view);

            txtMessage = (TextView) view.findViewById(R.id.txtMessage);
//            lblInitial = (TextView) view.findViewById(R.id.lblInitial);
            MemberName = (TextView) view.findViewById(R.id.MemberName);
            DateTime = (TextView) view.findViewById(R.id.DateTime);

            MemberNameRight = (TextView) view.findViewById(R.id.MemberNameRight);
            txtMessageRight = (TextView) view.findViewById(R.id.txtMessageRight);
            DateTimeRight = (TextView) view.findViewById(R.id.DateTimeRight);

           // lnrInitial = (LinearLayout) view.findViewById(R.id.lnrInitial);
            rytLeft = (RelativeLayout) view.findViewById(R.id.rytLeft);
            rytRight = (RelativeLayout) view.findViewById(R.id.rytRight);


        }

        public void bind(parentnoclass parentnoclass) {


        }
    }
}


