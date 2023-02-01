package com.vsca.vsnapvoicecollege.Adapters

import android.content.Context
import com.vsca.vsnapvoicecollege.Model.DashboardSubItems
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.vsca.vsnapvoicecollege.R
import com.bumptech.glide.Glide
import android.media.MediaPlayer.OnCompletionListener
import android.view.View.OnTouchListener
import android.view.MotionEvent
import android.widget.SeekBar.OnSeekBarChangeListener
import com.vsca.vsnapvoicecollege.Adapters.DashboardChild
import androidx.constraintlayout.widget.ConstraintLayout
import com.vsca.vsnapvoicecollege.Model.GetAssignmentDetails
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import butterknife.BindView
import butterknife.ButterKnife
import android.view.View
import android.view.View.VISIBLE
import android.widget.*
import com.vsca.vsnapvoicecollege.Model.GetNoticeboardDetails
import java.util.ArrayList

class NoticeBoard constructor(data: List<GetNoticeboardDetails>, context: Context) :
    RecyclerView.Adapter<NoticeBoard.MyViewHolder>() {
    var noticeboarddata: List<GetNoticeboardDetails> = ArrayList()
    var context: Context
    var Position: Int = 0
    private var mExpandedPosition: Int = -1
    var msgcontent: String? = null
    var detailsid: String? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.noticeboard_list_design, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, positionindex: Int) {
        val data: GetNoticeboardDetails = noticeboarddata.get(positionindex)
        Position = holder.getAbsoluteAdapterPosition()
        holder.rytNoticeboard!!.visibility = VISIBLE
        holder.lnrNoticeboardd!!.setVisibility(View.GONE)

        holder.lblNoticeboardTitle!!.setText(data.topic)
        holder.lblNoticeboardDescription!!.setText(data.description)
        holder.lblNoticeboardDate!!.setText(data.createdondate)
        holder.lblNoticetime!!.setText(data.createdontime)
        if (data.isappread.equals("0")) {
            holder.lblNewCircle!!.visibility = View.VISIBLE
        } else {
            holder.lblNewCircle!!.visibility = View.GONE
        }
        holder.lblSentByNotice!!.setText(data.sentbyname)
    }

    public override fun getItemCount(): Int {
        return noticeboarddata.size
    }

    inner class MyViewHolder constructor(itemView: View?) : RecyclerView.ViewHolder(
        (itemView)!!
    ) {
        @JvmField
        @BindView(R.id.lblNoticeboardTitle1)
        var lblNoticeboardTitle: TextView? = null

        @JvmField
        @BindView(R.id.lblNoticeboardDescription1)
        var lblNoticeboardDescription: TextView? = null

        @JvmField
        @BindView(R.id.lblNoticeboardDate1)
        var lblNoticeboardDate: TextView? = null

        @JvmField
        @BindView(R.id.lblNoticetime1)
        var lblNoticetime: TextView? = null

        @JvmField
        @BindView(R.id.lblNoticePostedby)
        var lblNoticePostedby: TextView? = null

        @JvmField
        @BindView(R.id.rytNotice)
        var rytNotice: RelativeLayout? = null

        @JvmField
        @BindView(R.id.lnrNoticeboardd)
        var lnrNoticeboardd: LinearLayout? = null


        @JvmField
        @BindView(R.id.lblNewCircle)
        var lblNewCircle: TextView? = null

        @JvmField
        @BindView(R.id.rytNoticeboard)
        var rytNoticeboard: RelativeLayout? = null

        @JvmField
        @BindView(R.id.lblSentByNotice)
        var lblSentByNotice: TextView? = null

        init {
            ButterKnife.bind(this, (itemView)!!)
        }
    }

    init {
        noticeboarddata = data
        this.context = context
    }
}