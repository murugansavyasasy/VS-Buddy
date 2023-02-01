package com.vsca.vsnapvoicecollege.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.vsca.vsnapvoicecollege.Activities.DashBoard
import com.vsca.vsnapvoicecollege.Activities.ViewFiles
import com.vsca.vsnapvoicecollege.Activities.ViewFiles_ViewBinding
import com.vsca.vsnapvoicecollege.Model.GetAssignmentDetails
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import com.vsca.vsnapvoicecollege.Utils.CommonUtil.isExpandAdapter

class AssignmentAdapter constructor(data: List<GetAssignmentDetails>, context: Context) :
    RecyclerView.Adapter<AssignmentAdapter.MyViewHolder>() {
    var assignmentdata: List<GetAssignmentDetails> = ArrayList()
    var context: Context
    var Position: Int = 0
    private var mExpandedPosition: Int = -1
    private var pathlist: List<String>? = null
    var filepath: String? = null
    var Imagepath: String? = null
    public override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.assignment_list_design, parent, false)
        return MyViewHolder(itemView)
    }

    public override fun onBindViewHolder(holder: MyViewHolder, positionindex: Int) {
        val data: GetAssignmentDetails = assignmentdata.get(positionindex)
        Position = holder.getAbsoluteAdapterPosition()
        holder.lblNoticeboardTitle!!.setText(data.topic)
        try {
            val createdDateTime: String = data.createdon.toString()
            val firstvalue: Array<String> = createdDateTime.split("-".toRegex()).toTypedArray()
            val createddate: String = firstvalue.get(0)
            holder.lblNoticeboardDate!!.setText(createddate)
            val createdTime: String = firstvalue.get(1)
            holder.lblNoticetime!!.setText(createdTime)
            val isExpanded: Boolean = positionindex == mExpandedPosition
            holder.rytNotice!!.setVisibility(if (isExpanded) View.VISIBLE else View.GONE)
            holder.lnrNoticeboardd!!.setActivated(isExpanded)
            holder.lnrNoticeboardd!!.setVisibility(View.VISIBLE)

            if(data.isappread.equals("0")){
                holder.lblNewCircle!!.visibility =View.VISIBLE
            }else{
                holder.lblNewCircle!!.visibility =View.GONE
            }

            if (isExpanded) {
                isExpandAdapter = true
                holder.imgArrowdown!!.setImageResource(R.drawable.ic_arrow_up_blue)
            } else {
                isExpandAdapter = false
                holder.imgArrowdown!!.setImageResource(R.drawable.ic_arrow_down_blue)
            }
            if (!(data.userfilename == "")) {
                val filename: String = data.userfilename!!
                Log.d("filename", filename)
                val file: Array<String> = filename.split("/".toRegex()).toTypedArray()
                val Stringone: String = file.get(0)
                val StringTwo: String = file.get(1)
                filepath = file.get(2)
            }
            holder.lnrNoticeboardd!!.setOnClickListener(object : View.OnClickListener {
                public override fun onClick(view: View) {
                    holder.lblNoticeboardDescription!!.setText(data.description)
                    holder.lblNoticePostedby!!.setText(data.sentbyname)
                    holder.lblSubmitedOn!!.setText(data.submissiondate)
                    if ((CommonUtil.Priority == "p4") || (CommonUtil.Priority == "p5") || (CommonUtil.Priority == "p6")) {
                        holder.LayoutSubmissions!!.setVisibility(View.VISIBLE)
                    }
                    if ((CommonUtil.Priority == "p1") || (CommonUtil.Priority == "p2") || (CommonUtil.Priority == "p3")) {
                        val Createdby: String = data.createdby!!
                        if (CommonUtil.MemberId == Createdby.toInt()) {
                            holder.lnrForward!!.setVisibility(View.VISIBLE)
                        }
                    }
                    if ((data.assignmenttype == "image")) {
                        Imagepath = data.file_path
                        holder.rytAssignmentFiles!!.setVisibility(View.VISIBLE)
//                        holder.lblAssignmentPath!!.setText(filepath)
                        pathlist = data.newfilepath
                        val filecount: Int = pathlist!!.size
                        if (filecount > 1) {
                            val totalcount: Int = filecount - 1
                            holder.lblFileCount!!.setVisibility(View.VISIBLE)
                            holder.lblFileCount!!.setText("+" + totalcount.toString())
                        } else {
                            holder.lblFileCount!!.setVisibility(View.GONE)
                        }
                    }
                    holder.lnrAttachment!!.setOnClickListener({
                        val i: Intent = Intent(context, ViewFiles::class.java)
                        i.putExtra("images", Imagepath)
                        context.startActivity(i)
                    })
                    mExpandedPosition = if (isExpanded) -1 else positionindex
                    notifyDataSetChanged()
                }
            })
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    public override fun getItemCount(): Int {
        return assignmentdata.size
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
        @BindView(R.id.lblSubmitedOn)
        var lblSubmitedOn: TextView? = null

        @JvmField
        @BindView(R.id.rytNotice)
        var rytNotice: RelativeLayout? = null

        @JvmField
        @BindView(R.id.lnrNoticeboardd)
        var lnrNoticeboardd: LinearLayout? = null

        @JvmField
        @BindView(R.id.LayoutSubmissions)
        var LayoutSubmissions: LinearLayout? = null

        @JvmField
        @BindView(R.id.imgArrowdown)
        var imgArrowdown: ImageView? = null

        @JvmField
        @BindView(R.id.rytAssignmentFiles)
        var rytAssignmentFiles: RelativeLayout? = null

        @JvmField
        @BindView(R.id.lblFileCount)
        var lblFileCount: TextView? = null

        @JvmField
        @BindView(R.id.lnrForward)
        var lnrForward: LinearLayout? = null

        @JvmField
        @BindView(R.id.lnrAttachment)
        var lnrAttachment: LinearLayout? = null

        @JvmField
        @BindView(R.id.lblAssignmentPath)
        var lblAssignmentPath: TextView? = null

        @JvmField
        @BindView(R.id.lblNewCircle)
        var lblNewCircle: TextView? = null

        init {
            ButterKnife.bind(this, (itemView)!!)
        }
    }

    init {
        assignmentdata = data
        this.context = context
    }
}