package com.vsca.vsnapvoicecollege.Adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.vsca.vsnapvoicecollege.R

import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import butterknife.BindView
import butterknife.ButterKnife
import com.vsca.vsnapvoicecollege.Model.GetCircularListDetails
import android.content.Intent
import android.view.View
import android.widget.*
import com.vsca.vsnapvoicecollege.Activities.*

import java.util.ArrayList

class CircularAdapter constructor(data: List<GetCircularListDetails>, context: Context) :
    RecyclerView.Adapter<CircularAdapter.MyViewHolder>() {
    var circulardata: List<GetCircularListDetails> = ArrayList()
    var context: Context
    var Position: Int = 0
    private var mExpandedPosition: Int = -1
    var filepath: String? = null
    private var pathlist: List<String>? = null
    var hide: Boolean = true
    var Imagepath: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.noticeboard_list_design, parent, false)
        return MyViewHolder(itemView)
    }

     override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data: GetCircularListDetails = circulardata.get(position)
        Position = holder.getAbsoluteAdapterPosition()
        holder.lblNoticeboardTitle!!.setText(data.title)
        holder.lblNoticeboardDate!!.setText(data.createdondate)
        holder.lblNoticetime!!.setText(data.createdontime)
        val isExpanded: Boolean = position == mExpandedPosition
        holder.rytNotice!!.setVisibility(if (isExpanded) View.VISIBLE else View.GONE)
        holder.lnrNoticeboardd!!.setActivated(isExpanded)
        holder.lnrNoticeboardd!!.setVisibility(View.VISIBLE)
        val filename: String = data.userfilename!!
        val firstvalue: Array<String> = filename.split("/".toRegex()).toTypedArray()
        val Stringone: String = firstvalue.get(0)
        val StringTwo: String = firstvalue.get(1)
        filepath = firstvalue.get(2)

        if(data.isappread.equals("0")){
            holder.lblNewCircle!!.visibility =View.VISIBLE
        }else{
            holder.lblNewCircle!!.visibility =View.GONE
        }
        if (isExpanded) {
            CommonUtil.isExpandAdapter = true
            holder.imgArrowdown!!.setVisibility(View.GONE)
            holder.lnrAboveAttachment!!.setVisibility(View.GONE)
        } else {
            CommonUtil.isExpandAdapter = false
            holder.imgArrowdown!!.setVisibility(View.VISIBLE)
            holder.lnrAboveAttachment!!.setVisibility(View.VISIBLE)
            holder.lblAboveCircularPath!!.setText("View")
        }
        holder.lnrNoticeboardd!!.setOnClickListener(object : View.OnClickListener {
             override fun onClick(view: View) {
                hide = false
                holder.lnrAboveAttachment!!.setVisibility(View.GONE)
                holder.rytCircularFiles!!.setVisibility(View.VISIBLE)
                holder.imgArrowdown!!.setVisibility(View.GONE)
                holder.lblNoticeboardDescription!!.setText(data.description)
                holder.lblNoticePostedby!!.setText(data.sentbyname)
                holder.lblCircularPath!!.setText("View")
                pathlist = data.newfilepath

                val filecount: Int = pathlist!!.size
                if (filecount > 1) {
                    val totalcount: Int = filecount - 1
                    holder.lblFileCount!!.setVisibility(View.VISIBLE)
                    holder.lblFileCount!!.setText("+" + totalcount.toString())
                } else {
                    holder.lblFileCount!!.setVisibility(View.GONE)
                }

                Imagepath = data.file_path
                holder.lnrAttachment!!.setOnClickListener({
                    val i: Intent = Intent(context, ViewFiles::class.java)
                    i.putExtra("images", Imagepath)
                    context.startActivity(i)
                })
                mExpandedPosition = if (isExpanded) -1 else position
                notifyDataSetChanged()
            }
        })
    }

     override fun getItemCount(): Int {
        return circulardata.size
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
        @BindView(R.id.lblCircularPath)
        var lblCircularPath: TextView? = null

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
        @BindView(R.id.lblAboveCircularPath)
        var lblAboveCircularPath: TextView? = null

        @JvmField
        @BindView(R.id.lblFileCount)
        var lblFileCount: TextView? = null

        @JvmField
        @BindView(R.id.lnrAttachment)
        var lnrAttachment: LinearLayout? = null

        @JvmField
        @BindView(R.id.lnrAboveAttachment)
        var lnrAboveAttachment: LinearLayout? = null

        @JvmField
        @BindView(R.id.rytCircularFiles)
        var rytCircularFiles: RelativeLayout? = null

        @JvmField
        @BindView(R.id.lblNewCircle)
        var lblNewCircle: TextView? = null


        init {
            ButterKnife.bind(this, (itemView)!!)
        }
    }

    init {
        circulardata = data
        this.context = context
    }
}