package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.Message_student;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.MyschoolListclass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.OnSchoolClickListener;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

/**
 * Created by devi on 12/30/2016.
 */

public class MySchoolListAdapter extends RecyclerView.Adapter<MySchoolListAdapter.MyViewHolder> {

    private ArrayList<MyschoolListclass> datasListschool;
    Context context;
    private OnSchoolClickListener listener;
    String Logintype;
    public String Schoolid;
    private static final String SH_USERS = "userInfo";
    private static final String SH_USERID = "UserId";
    SharedPreferences shpRemember;
    SharedPreferences.Editor ed;
    private static final String SH_LOGINTYPE = "slogintype";
    String status;

    //    LinearLayout layout_inactive,layout_active,layout_stopped;
    public void updateList(List<MyschoolListclass> data) {
        datasListschool = (ArrayList<MyschoolListclass>) data;
        this.notifyDataSetChanged();
        notifyDataSetChanged();

    }

    public MySchoolListAdapter(ArrayList<MyschoolListclass> datasListschool, String status, Context context, OnSchoolClickListener listener, String userType) {
        this.datasListschool = datasListschool;
        this.status = status;
        this.context = context;
        this.listener = listener;
        this.Logintype = userType;
    }

    @Override
    public MySchoolListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schoollist, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MySchoolListAdapter.MyViewHolder holder, int position) {

        MyschoolListclass dataschool = datasListschool.get(position);
        holder.bind(datasListschool.get(position), listener);

        holder.tvschoolId.setText(dataschool.getStrSchoolId());
        holder.tvschoolnamelist.setText(dataschool.getStrSchoolNameList());
        holder.Salespersionname.setText(dataschool.getSalespersionname());
//        holder.tvschoolcity.setText(dataschool.getStrSchoolcity());
        holder.tvusername.setText(dataschool.getContactNumber1());
//        holder.tvpassword.setText(dataschool.getStrPassword());
//        holder.tvfromdate.setText(dataschool.getStrFromdate());
//        holder.tvtodate.setText(dataschool.getStrTodate());
        holder.tvschooldid.setText(dataschool.getStrPassword());
        holder.tvschoolstatus.setText(dataschool.getStrschoolStatus());

        Schoolid = dataschool.getStrSchoolId();
        if (Schoolid.equals("0000")) {
            holder.message.setVisibility(View.GONE);
        } else {
            holder.message.setVisibility(View.VISIBLE);
        }

        if (status.equals("1")) {
//            holder.message.setVisibility(View.VISIBLE);
            holder.message.setVisibility(View.GONE);
        } else if (status.equals("-1")) {
            holder.message.setVisibility(View.GONE);
        }

        if (dataschool.getStrstatus().equals("1")) {
            holder.linear.setBackgroundColor(Color.parseColor("#a7f2c1"));
        } else if (!dataschool.getStrschoolStatus().equals("STOPPED") && dataschool.getStrstatus().equals("0")) {
            holder.linear.setBackgroundColor(Color.parseColor("#efb1b1"));
        }

        if (dataschool.getStrschoolStatus().equals("STOPPED")) {
            holder.linear.setBackgroundColor(Color.parseColor("#abdbe2"));
        }
    }

    @Override
    public int getItemCount() {
        return datasListschool.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView Salespersionname, tvschoolId, tvschoolnamelist, tvschoolcity, tvfromdate, tvtodate, tvusername, tvpassword, tvschooldid, tvschoolstatus, tvseniormrt, tvstudentcount, tvstaffcount, tvHidden;
        public Button message;
        public LinearLayout linear;

        public MyViewHolder(View view) {

            super(view);
            tvschoolId = (TextView) view.findViewById(R.id.schoolid);
            tvschoolnamelist = (TextView) view.findViewById(R.id.listschoolname);
            tvusername = (TextView) view.findViewById(R.id.school_username);
            tvschooldid = (TextView) view.findViewById(R.id.school_did);
            tvschoolstatus = (TextView) view.findViewById(R.id.school_status);
            message = (Button) view.findViewById(R.id.schoolmsg1);
            linear = (LinearLayout) view.findViewById(R.id.schoollist_linearlayout);
            Salespersionname = (TextView) view.findViewById(R.id.Salespersionname);

            Log.d("Logintypeadap", Logintype);

        }

        public void bind(final MyschoolListclass item, final OnSchoolClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSchoolClick(item);
                }
            });
            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                    alertDialog.setTitle("Alert");

                    alertDialog.setMessage("Are you sure To send the Message?");

                    alertDialog.setIcon(android.R.drawable.ic_dialog_info);

                    alertDialog.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(context, Message_student.class);
                                    i.putExtra("VALUE", item.getStrSchoolId());
                                    i.putExtra("SCHOOLNAME", item.getStrSchoolNameList());
                                    context.startActivity(i);

                                }
                            });

                    alertDialog.setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    alertDialog.show();
                }
            });


        }


    }

    public void clearAllData() {
        int size = this.datasListschool.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.datasListschool.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }
}
