package com.vsca.vsnapvoicecollege.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.vsca.vsnapvoicecollege.Interfaces.ExamMarkViewClickListener
import com.vsca.vsnapvoicecollege.Model.GetExamListDetails
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import java.util.ArrayList

class ExamListAdapter constructor(
    data: List<GetExamListDetails>, context: Context, type: Boolean,
    val markListener: ExamMarkViewClickListener
) :
    RecyclerView.Adapter<ExamListAdapter.MyViewHolder>() {
    var noticeboarddata: List<GetExamListDetails> = ArrayList()
    var context: Context
    var Position: Int = 0
    var Type: Boolean = true
    private var mExpandedPosition: Int = -1
    var msgcontent: String? = null
    var detailsid: String? = null

    companion object {
        var viewMarksClick: ExamMarkViewClickListener? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.noticeboard_list_design, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, positionindex: Int) {
        val data: GetExamListDetails = noticeboarddata.get(positionindex)
        Position = holder.getAbsoluteAdapterPosition()
        holder.lblNoticeboardTitle!!.setText(data.examname)

        viewMarksClick = markListener
        viewMarksClick?.onExamClickListener(holder, data)


        if(CommonUtil.Priority.equals("p4")||CommonUtil.Priority.equals("p5")||CommonUtil.Priority.equals("p6")){
            var examDate = data.date
            val splitDate = examDate!!.split("\\s+".toRegex()).toTypedArray()
            val Date = splitDate[0]
            val Month = splitDate[1]
            val Year = splitDate[2]
            val Date_2 = splitDate[3]
            val Month_2 = splitDate[4]
            val Year_2 = splitDate[5]
            holder.lblNoticeboardDate!!.setText(Date + " " + Month + " " + Year)
            holder.lblNoticetime!!.setText(Date_2 + " " + Month_2 + " " + Year_2)
        }
        val isExpanded: Boolean = positionindex == mExpandedPosition
        holder.rytNotice!!.setVisibility(if (isExpanded) View.VISIBLE else View.GONE)
        holder.lnrNoticeboardd!!.setActivated(isExpanded)
        holder.lnrNoticeboardd!!.setVisibility(View.VISIBLE)
        holder.imgArrowdown!!.setVisibility(View.VISIBLE)

        if (isExpanded) {
            holder.imgArrowdown!!.setVisibility(View.GONE)
            if (Type) {
                holder.lnrEventsView!!.setVisibility(View.GONE)
            }else{
                holder.lnrEventsView!!.setVisibility(View.VISIBLE)
            }
            holder.LayoutVenue!!.setVisibility(View.VISIBLE)
            holder.layoutSyllabus!!.setVisibility(View.VISIBLE)

        } else {
            holder.imgArrowdown!!.setVisibility(View.VISIBLE)
            holder.lnrEventsView!!.setVisibility(View.GONE)
            holder.LayoutVenue!!.setVisibility(View.GONE)
            holder.layoutSyllabus!!.setVisibility(View.GONE)

        }
        holder.lnrNoticeboardd!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                holder.imgArrowdown!!.setVisibility(View.GONE)
                holder.lblNoticeboardDescription!!.visibility = View.GONE
                holder.lblNoticePostedby!!.setText(data.createdbyname)
                if (Type) {
                    holder.lnrEventsView!!.setVisibility(View.GONE)
                }else{
                    holder.lnrEventsView!!.setVisibility(View.VISIBLE)
                }
                holder.LayoutVenue!!.setVisibility(View.VISIBLE)
                holder.layoutSyllabus!!.setVisibility(View.VISIBLE)

                holder.lblVenue!!.text = data.examvenue
                holder.lblSyllabus!!.text = data.syllabus

                mExpandedPosition = if (isExpanded) -1 else positionindex
                notifyDataSetChanged()
            }
        })
    }

    public override fun getItemCount(): Int {
        return noticeboarddata.size
    }

    inner class MyViewHolder constructor(itemView: View?) : RecyclerView.ViewHolder(
        (itemView)!!
    ) {
        @JvmField
        @BindView(R.id.lblNoticeboardTitle)
        var lblNoticeboardTitle: TextView? = null

        @JvmField
        @BindView(R.id.lblNoticeboardDescription)
        var lblNoticeboardDescription: TextView? = null

        @JvmField
        @BindView(R.id.lblNoticeboardDate)
        var lblNoticeboardDate: TextView? = null

        @JvmField
        @BindView(R.id.lblNoticetime)
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
        @BindView(R.id.imgArrowdown)
        var imgArrowdown: ImageView? = null

        @JvmField
        @BindView(R.id.imgArrowUp)
        var imgArrowUp: ImageView? = null

        @JvmField
        @BindView(R.id.LayoutVenue)
        var LayoutVenue: ConstraintLayout? = null

        @JvmField
        @BindView(R.id.layoutSyllabus)
        var layoutSyllabus: ConstraintLayout? = null

        @JvmField
        @BindView(R.id.lblSyllabus)
        var lblSyllabus: TextView? = null

        @JvmField
        @BindView(R.id.lblVenue)
        var lblVenue: TextView? = null

        @JvmField
        @BindView(R.id.lnrEventsView)
        var lnrEventsView: LinearLayout? = null

        init {
            ButterKnife.bind(this, (itemView)!!)
        }
    }

    init {
        noticeboarddata = data
        this.context = context
        Type = type
    }
}