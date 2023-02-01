package com.vsca.vsnapvoicecollege.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.vsca.vsnapvoicecollege.R
import android.widget.TextView
import com.vsca.vsnapvoicecollege.Model.DashboardOverall
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import androidx.cardview.widget.CardView
import com.vsca.vsnapvoicecollege.Activities.*
import com.vsca.vsnapvoicecollege.Utils.CommonUtil

import java.util.ArrayList

class DashboardParent constructor(
    private val categoriesModalArrayList: ArrayList<DashboardOverall>,
    private val context: Context
) : RecyclerView.Adapter<DashboardParent.ViewHolder>() {
    public override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.dashboard_parent_design, parent, false)
        )
    }

    public override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modal: DashboardOverall = categoriesModalArrayList.get(position)
        if ((modal.menuHeadings == "Ad")) {
            holder.category.setText(R.string.txt_advertisement)
            holder.lblViewAll.visibility=View.INVISIBLE
        } else if (modal.menuHeadings == "Circular") {
            holder.category.setText(context.getString(R.string.txt_img_pdf))
            holder.lblViewAll.visibility=View.VISIBLE
        }else{
            holder.category.setText(modal.menuHeadings)
            holder.lblViewAll.visibility=View.VISIBLE
        }
        if ((modal.menuHeadings == "Notice Board")) {
            val adapter: DashboardChild =
                DashboardChild(modal.menusubitemlist, context, "Notice Board")
            holder.recyclerDashboardTitle.setItemAnimator(DefaultItemAnimator())
            holder.recyclerDashboardTitle.setLayoutManager(
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            )
            holder.recyclerDashboardTitle.setAdapter(adapter)
            adapter.notifyDataSetChanged()
            var menuid = BaseActivity.NoticeboardMenuID
            Log.d("MenuID",menuid)
            CommonUtil.MenuIDNoticeboard = menuid
            holder.lblViewAll.setOnClickListener({
                val i: Intent = Intent(context, Noticeboard::class.java)
                context.startActivity(i)
            })

        } else if ((modal.menuHeadings == "Circular")) {
            val adapter: DashboardChild = DashboardChild(modal.menusubitemlist, context, "Circular")
            val linearLayoutManager: LinearLayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            holder.recyclerDashboardTitle.setLayoutManager(linearLayoutManager)
            holder.recyclerDashboardTitle.setAdapter(adapter)
            adapter.notifyDataSetChanged()

            var menuid = BaseActivity.CircularMenuID
            Log.d("CircularMenuID",menuid)
            CommonUtil.MenuIDCircular = menuid
            holder.lblViewAll.setOnClickListener({

                val i: Intent = Intent(context, Circular::class.java)
                context.startActivity(i)
            })
        } else if ((modal.menuHeadings == "Emergency Notification")) {
            val adapter: DashboardChild =
                DashboardChild(modal.menusubitemlist, context, "Emergency Notification")
            val linearLayoutManager: LinearLayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            holder.recyclerDashboardTitle.setLayoutManager(linearLayoutManager)
            holder.recyclerDashboardTitle.setAdapter(adapter)
            adapter.notifyDataSetChanged()

            holder.lblViewAll.setOnClickListener({

                var menuid = BaseActivity.CommunicationMenuID
                CommonUtil.MenuIDCommunication = menuid
                val i: Intent = Intent(context, Communication::class.java)
                context.startActivity(i)
            })

        } else if ((modal.menuHeadings == "Ad")) {
            val adapter: DashboardChild = DashboardChild(modal.menusubitemlist, context, "Ad")
            val linearLayoutManager: LinearLayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            holder.recyclerDashboardTitle.setLayoutManager(linearLayoutManager)
            holder.recyclerDashboardTitle.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        }
        else if ((modal.menuHeadings == "Recent Notifications")) {
            val adapter: DashboardChild =
                DashboardChild(modal.menusubitemlist, context, "Recent Notifications")
            val linearLayoutManager: LinearLayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            holder.recyclerDashboardTitle.setLayoutManager(linearLayoutManager)
            holder.recyclerDashboardTitle.setAdapter(adapter)
            adapter.notifyDataSetChanged()


            holder.lblViewAll.setOnClickListener({
                var menuid = BaseActivity.CommunicationMenuID
                CommonUtil.MenuIDCommunication = menuid
                val i: Intent = Intent(context, Communication::class.java)
                context.startActivity(i)
            })

        }
        else if ((modal.menuHeadings == "Attendance")) {
            if(modal.menusubitemlist.size == 0){
                holder.lblViewAll.visibility=View.INVISIBLE
                holder.recyclerDashboardTitle.visibility=View.GONE
                holder.idCVCategory.visibility=View.GONE
                holder.lblNoRecords.text = context.getString(R.string.txt_attanace_no_data)
            }else{
                val adapter: DashboardChild = DashboardChild(modal.menusubitemlist, context, "Attendance")
                val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                holder.recyclerDashboardTitle.setLayoutManager(linearLayoutManager)
                holder.recyclerDashboardTitle.setAdapter(adapter)
                adapter.notifyDataSetChanged()
            }
        }
    }

    public override fun getItemCount(): Int {
        return categoriesModalArrayList.size
    }

    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerDashboardTitle: RecyclerView
        val category: TextView
        val lblViewAll: TextView
        val lblNoRecords: TextView
        val idCVCategory: CardView

        init {
            category = itemView.findViewById(R.id.idTVCategory)
            lblViewAll = itemView.findViewById(R.id.lblViewAll)
            lblNoRecords = itemView.findViewById(R.id.lblNoRecords)
            idCVCategory = itemView.findViewById(R.id.idCVCategory)
            recyclerDashboardTitle = itemView.findViewById(R.id.recyclerDashboardTitle)
        }
    }
}