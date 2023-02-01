package com.vsca.vsnapvoicecollege.Adapters

import android.content.Context
import com.vsca.vsnapvoicecollege.Model.DashboardSubItems
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.vsca.vsnapvoicecollege.R
import com.bumptech.glide.Glide
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vsca.vsnapvoicecollege.Model.LoginDetails
import android.view.View
import android.widget.*
import com.vsca.vsnapvoicecollege.Interfaces.LoginRolesListener
import java.util.ArrayList

class LoginChooseRoles constructor(data: List<LoginDetails>, context: Context,val rolesListener: LoginRolesListener
) :
    RecyclerView.Adapter<LoginChooseRoles.MyViewHolder>() {
    var loginlist: List<LoginDetails> = ArrayList()
    var context: Context
    var Position: Int = 0
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.role_list_design, parent, false)
        return MyViewHolder(itemView)
    }

    companion object {
        var chooserolelistener: LoginRolesListener? = null
    }

     override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data: LoginDetails = loginlist.get(position)
        Position = holder.getAbsoluteAdapterPosition()
         chooserolelistener = rolesListener
         chooserolelistener?.onroleClick(holder, data)

         holder.lblMemberName!!.setText(data.membername)
        holder.lblCollegeName!!.setText(data.colgname)
        if ((data.priority == "p1") || (data.priority == "p2") || (data.priority == "p3")) {
            if (!(data.yearname == "") || !(data.semestername == "")) {
                holder.lnrYearSem!!.setVisibility(View.VISIBLE)
                holder.lblYearName!!.setText(data.yearname)
                holder.lblSemesterName!!.setText(data.semestername)
            } else {
                holder.lnrYearSem!!.setVisibility(View.GONE)
            }
            holder.lblRoleType!!.setText(data.loginas)
        } else if ((data.priority == "p4")) {
            holder.lnrYearSem!!.setVisibility(View.VISIBLE)
            holder.lblYearName!!.setText(data.yearname)
            holder.lblSemesterName!!.setText(data.semestername)
            holder.lblRoleType!!.setText(R.string.txt_student)
        } else if ((data.priority == "p5")) {
            holder.lnrYearSem!!.setVisibility(View.VISIBLE)
            holder.lblYearName!!.setText(data.yearname)
            holder.lblSemesterName!!.setText(data.semestername)
            holder.lblRoleType!!.setText(R.string.txt_parent)
        } else {
            holder.lnrYearSem!!.setVisibility(View.VISIBLE)
            holder.lblYearName!!.setText(data.yearname)
            holder.lblSemesterName!!.setText(data.semestername)
            holder.lblRoleType!!.setText(data.loginas)
        }
        if (data.colglogo == null || data.colglogo!!.isEmpty()) {
            Glide.with(context)
                .load(R.drawable.ic_user_grey)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into((holder.imgCollegeLogo)!!)
        } else {
            Glide.with(context)
                .load(data.colglogo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into((holder.imgCollegeLogo)!!)
        }

    }

    public override fun getItemCount(): Int {
        return loginlist.size
    }

    inner class MyViewHolder constructor(itemView: View?) : RecyclerView.ViewHolder(
        (itemView)!!
    ) {
        @JvmField
        @BindView(R.id.lblMemberName)
        var lblMemberName: TextView? = null

        @JvmField
        @BindView(R.id.lblCollegeName)
        var lblCollegeName: TextView? = null

        @JvmField
        @BindView(R.id.lblRoleType)
        var lblRoleType: TextView? = null

        @JvmField
        @BindView(R.id.lblYearName)
        var lblYearName: TextView? = null

        @JvmField
        @BindView(R.id.lblSemesterName)
        var lblSemesterName: TextView? = null

        @JvmField
        @BindView(R.id.imgCollegeLogo)
        var imgCollegeLogo: ImageView? = null

        @JvmField
        @BindView(R.id.lnrYearSem)
        var lnrYearSem: LinearLayout? = null

        @JvmField
        @BindView(R.id.rytOverAll)
        var rytOverAll: RelativeLayout? = null

        init {
            ButterKnife.bind(this, (itemView)!!)
        }
    }

    init {
        loginlist = data
        this.context = context
    }
}