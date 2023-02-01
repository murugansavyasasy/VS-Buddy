package com.vsca.vsnapvoicecollege.Adapters

import android.content.Context
import com.vsca.vsnapvoicecollege.Model.DashboardSubItems
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.vsca.vsnapvoicecollege.R
import com.bumptech.glide.Glide
import android.media.MediaPlayer.OnCompletionListener
import android.util.Log
import android.view.View.OnTouchListener
import android.view.MotionEvent
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.vsca.vsnapvoicecollege.Adapters.DashboardChild
import android.widget.TextView
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.LinearLayout
import com.vsca.vsnapvoicecollege.Model.GetAssignmentDetails
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import butterknife.BindView
import butterknife.ButterKnife
import android.view.View
import com.vsca.vsnapvoicecollege.Model.GetNotificationDetails
import java.util.ArrayList

class NotificationAdapter constructor(data: List<GetNotificationDetails>, context: Context) :
    RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {
    var notificationList: List<GetNotificationDetails> = ArrayList()
    var context: Context
    var Position: Int = 0
    public override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.activity_notification, parent, false)
        return MyViewHolder(itemView)
    }

    public override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data: GetNotificationDetails = notificationList.get(position)
        Position = holder.getAbsoluteAdapterPosition()
        holder.lblMemberName!!.setText(data.title)
        holder.lblNotificationContent!!.setText(data.notification_content)
        val date: String = data.sentOn!!
        val splitDate: Array<String> = date.split("\\s+".toRegex()).toTypedArray()
        val Date: String = splitDate.get(0)
        holder.lblDate!!.setText(Date)
        val time: String = splitDate.get(1)

        val notificationTime: Array<String> = time.split("\\.".toRegex()).toTypedArray()
        val time1: String = notificationTime.get(0)
        Log.d("time1",time1)
        val time2: String = notificationTime.get(1)
        Log.d("time2",time2)


        holder.lblNoticeficationTime!!.setText(time1)
    }

    public override fun getItemCount(): Int {
        return notificationList.size
    }

    inner class MyViewHolder constructor(itemView: View?) : RecyclerView.ViewHolder(
        (itemView)!!
    ) {
        @JvmField
        @BindView(R.id.lblMemberName)
        var lblMemberName: TextView? = null

        @JvmField
        @BindView(R.id.lblNotificationContent)
        var lblNotificationContent: TextView? = null

        @JvmField
        @BindView(R.id.lblNoticeficationTime)
        var lblNoticeficationTime: TextView? = null

        @JvmField
        @BindView(R.id.lblDate)
        var lblDate: TextView? = null

        init {
            ButterKnife.bind(this, (itemView)!!)
        }
    }

    init {
        notificationList = data
        this.context = context
    }
}