package com.vsca.vsnapvoicecollege.Activities

import butterknife.BindView
import com.vsca.vsnapvoicecollege.R
import android.os.Bundle
import butterknife.ButterKnife
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Repository.ApiRequestNames
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DefaultItemAnimator
import com.vsca.vsnapvoicecollege.Adapters.NotificationAdapter
import com.vsca.vsnapvoicecollege.Model.GetNotificationDetails
import android.util.Log
import java.util.ArrayList

class Notification : BaseActivity() {
    var notificationadapter: NotificationAdapter? = null

    @JvmField
    @BindView(R.id.idRVCategories)
    var rvNotification: RecyclerView? = null
    var GetNotificationData: List<GetNotificationDetails> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        CommonUtil.SetTheme(this)
        super.onCreate(savedInstanceState)

        ButterKnife.bind(this)
        ActionBarMethod(this@Notification)
        MenuBottomType()
        NotificatonRequest()
        if (CommonUtil.HeaderMenuNotification) {
            imgNotification!!.isEnabled = false
            imgNotification!!.isClickable = false
        }
        dashboardViewModel!!.notificationData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message
                if (status == 1) {
                    UserMenuRequest(this@Notification)
                    GetNotificationData = response.data!!
                    notificationadapter =
                        NotificationAdapter(GetNotificationData, this@Notification)
                    val mLayoutManager: RecyclerView.LayoutManager =
                        LinearLayoutManager(this@Notification)
                    rvNotification!!.layoutManager = mLayoutManager
                    rvNotification!!.itemAnimator = DefaultItemAnimator()
                    rvNotification!!.adapter = notificationadapter
                    rvNotification!!.recycledViewPool.setMaxRecycledViews(0, 80)
                    notificationadapter!!.notifyDataSetChanged()
                } else {
                    CommonUtil.ApiAlert(this@Notification, message)
                }
            }
        }
    }

    override val layoutResourceId: Int
        protected get() = R.layout.bottom_menu_swipe

    private fun NotificatonRequest() {
        val jsonObject = JsonObject()
        jsonObject.addProperty(ApiRequestNames.Req_MemberID, CommonUtil.MemberId)
        jsonObject.addProperty(ApiRequestNames.Req_college_id, CommonUtil.CollegeId)
        dashboardViewModel!!.getNotifications(jsonObject, this@Notification)
        Log.d("NotificationRequest:", jsonObject.toString())
    }
}