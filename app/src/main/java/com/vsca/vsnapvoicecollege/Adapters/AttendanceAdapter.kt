package com.vsca.vsnapvoicecollege.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.vsca.vsnapvoicecollege.Interfaces.EventClickListener
import com.vsca.vsnapvoicecollege.Model.AttendanceData
import com.vsca.vsnapvoicecollege.R

class AttendanceAdapter(
    var attendacelist: List<AttendanceData>,
    private val context: Context?,
) : RecyclerView.Adapter<AttendanceAdapter.MyViewHolder>() {

    var Position: Int = 0

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @JvmField
        @BindView(R.id.lblAttendanceType)
        var lblAttendanceType: TextView? = null

        @JvmField
        @BindView(R.id.lblAttendanceSubject)
        var lblAttendanceSubject: TextView? = null

        init {
            ButterKnife.bind(this, (itemView)!!)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.attendance_list_ui, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = attendacelist[position]
        if(data.attendancetype.equals("Present")){
            holder.lblAttendanceType!!.setText("P")
            holder.lblAttendanceType!!.setBackgroundResource(R.drawable.bg_circle_green)
        }else{
            holder.lblAttendanceType!!.setText("Ab")
            holder.lblAttendanceType!!.setBackgroundResource(R.drawable.bg_read_status)
        }
        holder.lblAttendanceSubject!!.setText(data.subjectname)
    }

    override fun getItemCount(): Int {
        return attendacelist.size

    }
}
