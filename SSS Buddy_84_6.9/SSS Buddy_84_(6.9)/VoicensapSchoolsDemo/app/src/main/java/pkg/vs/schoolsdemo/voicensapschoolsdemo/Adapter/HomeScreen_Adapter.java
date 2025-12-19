package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.AdvanceTour;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.AlertNotificationScreen;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.CustomerDetails;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.Report;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.TourSettlement;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.Zero_Activity;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.Approve_poc;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.ChatHomeScreen;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.CircularList;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.ContactsNumbersActivity;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.CreateDemo;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.CreateDemoList;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.CreatePoc;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.MyschoolList;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.Recordcollectionmenu;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.RequirementSendScreen;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.RequirementsListScreen;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.SchoolDocumentsActivity;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.Statusreportsubmenu;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.SupportverifyList;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.VideoListActivity;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.HomeScreen_Class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

/*
 * Created by devi on 7/9/2019.
 */

public class HomeScreen_Adapter extends RecyclerView.Adapter<HomeScreen_Adapter.MyViewHolder> {

    private Context mContext;
    private List<HomeScreen_Class> menuList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;
        public TextView menu_id;
        public LinearLayout layout_overall;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.text_heading);
            image = (ImageView) view.findViewById(R.id.img1);
            menu_id = (TextView) view.findViewById(R.id.menu_id);
            layout_overall = (LinearLayout) view.findViewById(R.id.home_layout);

        }
    }

    public HomeScreen_Adapter(Context mContext, List<HomeScreen_Class> menuList) {
        this.mContext = mContext;
        this.menuList = menuList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_screen, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final HomeScreen_Class menu = menuList.get(position);

        Log.d("position", String.valueOf(position));
        holder.title.setText(menu.getName());
        holder.menu_id.setText(menu.getId());

        final String text = menu.getName();


//            holder.image.setImageResource(menuList.get(position).getImage());

        Glide.with(mContext)
                .load(menu.getImage())
                .into(holder.image);

        holder.layout_overall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("icon_position", String.valueOf(position));

                if (text.equals("Create Demo")) {
                    Intent i1 = new Intent(v.getContext(), CreateDemo.class);
                    v.getContext().startActivity(i1);
                } else if (text.equals("Demo List")) {
                    Intent i2 = new Intent(v.getContext(), CreateDemoList.class);
                    v.getContext().startActivity(i2);
                } else if (text.equals("Create POC/Live")) {
                    Intent i3 = new Intent(v.getContext(), CreatePoc.class);
                    v.getContext().startActivity(i3);
                } else if (text.equals("School List")) {
                    Intent i4 = new Intent(v.getContext(), MyschoolList.class);
                    v.getContext().startActivity(i4);
                } else if (text.equals("Circular List")) {
                    Intent i5 = new Intent(v.getContext(), CircularList.class);
                    v.getContext().startActivity(i5);
                } else if (text.equals("Status Report")) {
                    Intent i6 = new Intent(v.getContext(), Statusreportsubmenu.class);
                    v.getContext().startActivity(i6);
                } else if (text.equals("Record Collection")) {
                    Intent i7 = new Intent(v.getContext(), Recordcollectionmenu.class);
                    v.getContext().startActivity(i7);
                } else if (text.equals("Important Info")) {
                    Intent i8 = new Intent(v.getContext(), ContactsNumbersActivity.class);
                    v.getContext().startActivity(i8);
                } else if (text.equals("Support Verify")) {
                    Intent i9 = new Intent(v.getContext(), SupportverifyList.class);
                    v.getContext().startActivity(i9);
                } else if (text.equals("Approve POC")) {
                    Intent i10 = new Intent(v.getContext(), Approve_poc.class);
                    v.getContext().startActivity(i10);
                } else if (text.equals("Customer Details")) {
                    Intent i11 = new Intent(v.getContext(), CustomerDetails.class);
                    v.getContext().startActivity(i11);
                } else if (text.equals("Local Conveyence")) {
                    Intent i12 = new Intent(v.getContext(), Report.class);
                    v.getContext().startActivity(i12);
                } else if (text.equals("Advance TourExpense")) {
                    Intent i13 = new Intent(v.getContext(), AdvanceTour.class);
                    v.getContext().startActivity(i13);
                } else if (text.equals("Tour Settlement")) {
                    Intent i14 = new Intent(v.getContext(), TourSettlement.class);
                    v.getContext().startActivity(i14);
                } else if (text.equals("FeedBack")) {

                    String Schl_usertype = SharedPreference_class.getSchoolType(mContext);
                    Log.d("type", Schl_usertype);

                    if (Schl_usertype.equals("Admin")) {
                        Intent i15 = new Intent(v.getContext(), RequirementsListScreen.class);
                        v.getContext().startActivity(i15);
                    } else {
                        Intent i16 = new Intent(v.getContext(), RequirementSendScreen.class);
                        v.getContext().startActivity(i16);
                    }
                } else if (text.equals("Chat")) {
                    Intent i17 = new Intent(v.getContext(), ChatHomeScreen.class);
                    v.getContext().startActivity(i17);

                } else if (text.equals("Alert")) {
                    Intent i18 = new Intent(v.getContext(), AlertNotificationScreen.class);
                    v.getContext().startActivity(i18);

                } else if (text.equals("School Documents")) {
                    Intent i21 = new Intent(v.getContext(), SchoolDocumentsActivity.class);
                    v.getContext().startActivity(i21);
                } else if (text.equals("Management Videos")) {
                    Intent i22 = new Intent(v.getContext(), VideoListActivity.class);
                    v.getContext().startActivity(i22);
                } else if (text.equals("Zero Activity")) {
                    Intent i23 = new Intent(v.getContext(), Zero_Activity.class);
                    v.getContext().startActivity(i23);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
}




