package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.ChatScreenActivity;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.MemberListHeader;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.MemberListSub;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class ExpandableMemberListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<MemberListHeader> _listDataHeader; // header titles
    private HashMap<MemberListHeader, List<MemberListSub>> _listDataChild;

    public ExpandableMemberListAdapter(Context context, List<MemberListHeader> listDataHeader,
                                       HashMap<MemberListHeader, List<MemberListSub>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final MemberListSub exam = (MemberListSub) getChild(groupPosition, childPosition);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.member_sub_list, null);
        }

        LinearLayout lnrParent = (LinearLayout) convertView.findViewById(R.id.lnrParent);
        RelativeLayout rytParent = (RelativeLayout) convertView.findViewById(R.id.rytParent);


        TextView lblSchoolName = (TextView) convertView.findViewById(R.id.lblSchoolName);
        TextView lblPoNumber = (TextView) convertView.findViewById(R.id.lblPoNumber);
        TextView lblPoValidFrom = (TextView) convertView.findViewById(R.id.lblPoValidFrom);
        TextView lblPoValidTo = (TextView) convertView.findViewById(R.id.lblPoValidTo);
        TextView lblDayLeft = (TextView) convertView.findViewById(R.id.lblDayLeft);
        Button btnChat = (Button) convertView.findViewById(R.id.btnChat);


        lblSchoolName.setText(exam.getCustomerName());
        lblPoNumber.setText(": "+exam.getPoNumber());
        lblPoValidFrom.setText(": "+exam.getValidFrom());
        lblPoValidTo.setText(": "+exam.getValidTo());
        lblDayLeft.setText(": "+exam.getRemainingDays());

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatScreen=new Intent(_context,ChatScreenActivity.class);
                chatScreen.putExtra("CustomerName",exam.getCustomerName());
                chatScreen.putExtra("PO_number",exam.getPoNumber());
                chatScreen.putExtra("POID",exam.getPOID());
                _context.startActivity(chatScreen);
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        final MemberListHeader head = (MemberListHeader) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.members_header_list, null);
        }

        TextView lblName = (TextView) convertView.findViewById(R.id.lblName);
        ImageView image = (ImageView) convertView.findViewById(R.id.absExTitle_ivArrow);

        if (isExpanded) {
            image.setImageResource(R.drawable.down_arrow);
        } else {
            image.setImageResource(R.drawable.right_arrow);
        }

        lblName.setText(head.getName());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void updateList(List<MemberListHeader> temp) {
        _listDataHeader = temp;
        notifyDataSetChanged();
    }
}
