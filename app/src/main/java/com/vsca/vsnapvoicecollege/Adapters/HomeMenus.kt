package com.vsca.vsnapvoicecollege.Adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.vsca.vsnapvoicecollege.R
import android.widget.TextView
import android.widget.RelativeLayout
import butterknife.BindView
import butterknife.ButterKnife
import android.view.View
import com.vsca.vsnapvoicecollege.Interfaces.HomeMenuClickListener
import com.vsca.vsnapvoicecollege.Model.MenuDetailsResponse
import de.hdodenhof.circleimageview.CircleImageView
import java.util.ArrayList

class HomeMenus constructor(
    context: Context,
    data: List<MenuDetailsResponse>,
    val homeMenuClickListener: HomeMenuClickListener
) :
    RecyclerView.Adapter<HomeMenus.MyViewHolder>() {
    var menulist: List<MenuDetailsResponse> = ArrayList()
    var context: Context
    var Position: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.home_menu_list_design, parent, false)
        return MyViewHolder(itemView)
    }

    companion object {
        var menuListener: HomeMenuClickListener? = null
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data: MenuDetailsResponse = menulist.get(position)
        menuListener = homeMenuClickListener
        menuListener?.onMenuClick(holder, data)

        Position = holder.getAbsoluteAdapterPosition()
        if (data.id == 1) {
            holder.imgMenu!!.setImageResource(R.drawable.home)
            holder.lblMenuName!!.setText(data.name)
        }
        if (data.id == 11) {
            holder.imgMenu!!.setImageResource(R.drawable.chat)
            holder.lblMenuName!!.setText(data.name)
        }
        if (data.id == 2) {
            holder.imgMenu!!.setImageResource(R.drawable.communication)
            holder.lblMenuName!!.setText(data.name)
        }
        if (data.id == 3) {
            holder.imgMenu!!.setImageResource(R.drawable.exam)
            holder.lblMenuName!!.setText(data.name)
        }
        if (data.id == 4) {
            holder.imgMenu!!.setImageResource(R.drawable.attendance)
            holder.lblMenuName!!.setText(data.name)
        }
        if (data.id == 5) {
            holder.imgMenu!!.setImageResource(R.drawable.assignment)
            holder.lblMenuName!!.setText(data.name)
        }
        if (data.id == 6) {
            holder.imgMenu!!.setImageResource(R.drawable.circular)
            holder.lblMenuName!!.setText(context.getString(R.string.txt_img_pdf))
        }
        if (data.id == 7) {
            holder.imgMenu!!.setImageResource(R.drawable.noticeboard)
            holder.lblMenuName!!.setText(data.name)
        }
        if (data.id == 8) {
            holder.imgMenu!!.setImageResource(R.drawable.events)
            holder.lblMenuName!!.setText(data.name)
        }
        if (data.id == 9) {
            holder.imgMenu!!.setImageResource(R.drawable.faculty)
            holder.lblMenuName!!.setText(data.name)
        }
        if (data.id == 10) {
            holder.imgMenu!!.setImageResource(R.drawable.video)
            holder.lblMenuName!!.setText(data.name)
        }
        if (data.id == 12) {
            holder.imgMenu!!.setImageResource(R.drawable.bg_circle_course_details)
            holder.lblMenuName!!.setText(data.name)
        }
        if (data.id == 13) {
            holder.imgMenu!!.setImageResource(R.drawable.bg_circle_category_credit)
            holder.lblMenuName!!.setText(data.name)
        }
        if (data.id == 14) {
            holder.imgMenu!!.setImageResource(R.drawable. bg_circle_sem_credit)
            holder.lblMenuName!!.setText(data.name)
        }
        if (data.id == 15) {
            holder.imgMenu!!.setImageResource(R.drawable.bg_circle_exam_applications)
            holder.lblMenuName!!.setText(data.name)
        }


    }

     override fun getItemCount(): Int {
        return menulist.size
    }

    inner class MyViewHolder constructor(itemView: View?) : RecyclerView.ViewHolder(
        (itemView)!!
    ) {
        @JvmField
        @BindView(R.id.lblMenuName)
        var lblMenuName: TextView? = null

        @JvmField
        @BindView(R.id.imgMenu)
        var imgMenu: CircleImageView? = null

        @JvmField
        @BindView(R.id.LayoutHome)
        var LayoutHome: RelativeLayout? = null

        init {
            ButterKnife.bind(this, (itemView)!!)
        }
    }

    init {
        menulist = data
        this.context = context
    }
}