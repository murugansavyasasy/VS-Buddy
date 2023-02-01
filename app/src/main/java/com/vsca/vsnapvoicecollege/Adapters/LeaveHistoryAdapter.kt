package com.vsca.vsnapvoicecollege.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.vsca.vsnapvoicecollege.Interfaces.LeaveHistoryListener
import com.vsca.vsnapvoicecollege.Model.LeaveHistoryData
import com.vsca.vsnapvoicecollege.R

class LeaveHistoryAdapter(
    var leavelist: List<LeaveHistoryData>,
    private val context: Context?,
    val leaveListener: LeaveHistoryListener
) : RecyclerView.Adapter<LeaveHistoryAdapter.MyViewHolder>() {

    companion object {
        var leavelistenerClick: LeaveHistoryListener? = null
    }

    var Position: Int = 0

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @JvmField
        @BindView(R.id.lblLeaveCreatedDate)
        var lblLeaveCreatedDate: TextView? = null

        @JvmField
        @BindView(R.id.lblleaveStatus)
        var lblleaveStatus: TextView? = null

        @JvmField
        @BindView(R.id.lblLeaveType)
        var lblLeaveType: TextView? = null

        @JvmField
        @BindView(R.id.lblLeaveNoOfDays)
        var lblLeaveNoOfDays: TextView? = null

        @JvmField
        @BindView(R.id.lblFromDate)
        var lblFromDate: TextView? = null

        @JvmField
        @BindView(R.id.lblToDate)
        var lblToDate: TextView? = null

        @JvmField
        @BindView(R.id.rytLeaveDescription)
        var rytLeaveDescription: RelativeLayout? = null

        @JvmField
        @BindView(R.id.lblLeaveReason)
        var lblLeaveReason: TextView? = null

        @JvmField
        @BindView(R.id.lblEditleave)
        var lblEditleave: TextView? = null

        @JvmField
        @BindView(R.id.lblDelete)
        var lblDelete: TextView? = null


        init {
            ButterKnife.bind(this, (itemView)!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.leave_history_design, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = leavelist[position]
        leavelistenerClick = leaveListener
        leavelistenerClick?.onLeaveClick(holder, data)

        try {

            holder.lblLeaveReason!!.setText(data.leavereason)
            holder.lblLeaveType!!.setText(data.leaveapplicationtype)
            holder.lblLeaveCreatedDate!!.setText(data.createdon)
            holder.lblFromDate!!.setText(data.leavefromdate)
            holder.lblToDate!!.setText(data.leavetodate)
            holder.lblLeaveNoOfDays!!.setText(data.numofdays)

            if(data.leavestatus.equals("4")){
                holder.lblleaveStatus!!.text=data.leavestatus
            }else{
                holder.lblleaveStatus!!.text=data.leavestatus
            }

        } catch (e: NullPointerException) {
            e.printStackTrace()
        }



    }

    override fun getItemCount(): Int {
        return leavelist.size

    }
}