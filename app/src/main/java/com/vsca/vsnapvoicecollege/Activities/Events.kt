package com.vsca.vsnapvoicecollege.Activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.ActivitySender.AddEvents
import com.vsca.vsnapvoicecollege.ActivitySender.AddTextNoticeboard
import com.vsca.vsnapvoicecollege.Adapters.EventsAdapter
import com.vsca.vsnapvoicecollege.Interfaces.EventClickListener
import com.vsca.vsnapvoicecollege.Model.GetEventDetailsData
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Repository.ApiRequestNames
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import com.vsca.vsnapvoicecollege.ViewModel.App
import java.util.ArrayList

class Events : BaseActivity() {
    var eventsAdapter: EventsAdapter? = null
    override var appViewModel: App? = null

    @JvmField
    @BindView(R.id.recyclerCommon)
    var recyclerNoticeboard: RecyclerView? = null

    @JvmField
    @BindView(R.id.imgAdvertisement)
    var imgAdvertisement: ImageView? = null

    @JvmField
    @BindView(R.id.imgthumb)
    var imgthumb: ImageView? = null

    @JvmField
    @BindView(R.id.lbltotalsize)
    var lbltotalsize: TextView? = null

    @JvmField
    @BindView(R.id.lblMenuTitle)
    var lblMenuTitle: TextView? = null

    @JvmField
    @BindView(R.id.lblDepartment)
    var lblDepartment: TextView? = null

    @JvmField
    @BindView(R.id.lblCollege)
    var lblCollege: TextView? = null

    @JvmField
    @BindView(R.id.lblDepartmentSize)
    var lblDepartmentSize: TextView? = null

    @JvmField
    @BindView(R.id.lblCollegeSize)
    var lblCollegeSize: TextView? = null

    @JvmField
    @BindView(R.id.lblNoRecordsFound)
    var lblNoRecordsFound: TextView? = null
    var EventType = true
    var GetEvetnsData: List<GetEventDetailsData> = ArrayList()
    var CountUpcoming: String? = null
    var Countpast: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        CommonUtil.SetTheme(this)

        super.onCreate(savedInstanceState)
        appViewModel = ViewModelProvider(this).get(App::class.java)
        appViewModel!!.init()
        ButterKnife.bind(this)
        ActionBarMethod(this)
        MenuBottomType()
        OverAllMenuCountRequest(this, CommonUtil.MenuIDEvents!!)

        CommonUtil.OnMenuClicks("Events")
        TabDepartmentColor()

        lblMenuTitle!!.setText(R.string.txt_events)
        lblDepartment!!.setText(R.string.txt_upcoming)
        lblCollege!!.setText(R.string.txt_past)

        Glide.with(this)
            .load(CommonUtil.CommonAdvertisement)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgAdvertisement!!)
        Glide.with(this)
            .load(CommonUtil.CommonAdImageSmall)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgthumb!!)

        appViewModel!!.OverAllMenuResponseLiveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message
                if (status == 1) {
                    if (response.data.isNullOrEmpty()) {
                        EventRequest(EventType)
                        OverAllMenuCountData = emptyList()
                    } else {
                        OverAllMenuCountData = response.data!!
                        CountUpcoming = OverAllMenuCountData[0].upcomingevents
                        Countpast = OverAllMenuCountData[0].pastevents
                        EventRequest(EventType)
                        CountValueSet()
                    }
                } else {
                    EventRequest(EventType)
                    OverAllMenuCountData = emptyList()
                }
            } else {
                EventRequest(EventType)
            }
        }

        appViewModel!!.eventListbyTypeliveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message
                UserMenuRequest(this)
                if (status == 1) {
                    if (EventType) {
                        GetEvetnsData = response.data!!
                        val size = GetEvetnsData.size
                        if (size > 0) {
                            lblNoRecordsFound!!.visibility = View.GONE
                            recyclerNoticeboard!!.visibility = View.VISIBLE
                            eventsAdapter = EventsAdapter(GetEvetnsData, this, object :
                                EventClickListener {
                                override fun oneventClick(
                                    holder: EventsAdapter.MyViewHolder,
                                    item: GetEventDetailsData
                                ) {
                                    holder.lnrNoticeboardd?.setOnClickListener(View.OnClickListener {
                                        val i = Intent(this@Events, EventsViewDetails::class.java)
                                        i.putExtra("EventsData", item)
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        startActivity(i)

                                    })
                                }
                            })

                            val mLayoutManager: RecyclerView.LayoutManager =
                                LinearLayoutManager(this)
                            recyclerNoticeboard!!.layoutManager = mLayoutManager
                            recyclerNoticeboard!!.itemAnimator = DefaultItemAnimator()
                            recyclerNoticeboard!!.adapter = eventsAdapter
                            recyclerNoticeboard!!.recycledViewPool.setMaxRecycledViews(0, 80)
                            eventsAdapter!!.notifyDataSetChanged()
                        } else {
                            NoDataFound()
                        }
                    } else {
                        GetEvetnsData = response.data!!
                        val size = GetEvetnsData.size
                        if (size > 0) {
                            lblNoRecordsFound!!.visibility = View.GONE
                            recyclerNoticeboard!!.visibility = View.VISIBLE
                            eventsAdapter = EventsAdapter(GetEvetnsData, this, object :
                                EventClickListener {
                                override fun oneventClick(
                                    holder: EventsAdapter.MyViewHolder,
                                    item: GetEventDetailsData
                                ) {

                                    holder.lnrNoticeboardd!!.setOnClickListener(View.OnClickListener {

                                        val i = Intent(this@Events, EventsViewDetails::class.java)
                                        i.putExtra("EventsData", item)
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        startActivity(i)
                                    })


                                }
                            })
                            val mLayoutManager: RecyclerView.LayoutManager =
                                LinearLayoutManager(this)
                            recyclerNoticeboard!!.layoutManager = mLayoutManager
                            recyclerNoticeboard!!.itemAnimator = DefaultItemAnimator()
                            recyclerNoticeboard!!.adapter = eventsAdapter
                            recyclerNoticeboard!!.recycledViewPool.setMaxRecycledViews(0, 80)
                            eventsAdapter!!.notifyDataSetChanged()
                        } else {
                            NoDataFound()
                        }
                    }
                } else {

                    if (EventType) {
                        NoDataFound()
                    } else {
                        NoDataFound()
                    }
                }
            } else {
                UserMenuRequest(this)
                NoDataFound()
            }
        }

        imgRefresh!!.setOnClickListener(View.OnClickListener {
            if (EventType) {
                EventType = true
                EventRequest(EventType)
            } else {
                EventType = false
                EventRequest(EventType)
            }
        })
    }

    private fun NoDataFound() {
        lblNoRecordsFound!!.visibility = View.VISIBLE
        recyclerNoticeboard!!.visibility = View.GONE
    }

    private fun CountValueSet() {

        if (!CountUpcoming.equals("0") && !CountUpcoming.equals("")) {
            lblDepartmentSize!!.visibility = View.VISIBLE
            lblDepartmentSize!!.text = CountUpcoming
        } else {
            lblDepartmentSize!!.visibility = View.GONE
            CountUpcoming = "0"
        }
        if (!Countpast.equals("0") && !Countpast.equals("")) {
            lblCollegeSize!!.visibility = View.VISIBLE
            lblCollegeSize!!.text = Countpast
        } else {
            lblCollegeSize!!.visibility = View.GONE
            Countpast = "0"
        }
        var intdepartment = Integer.parseInt(CountUpcoming!!)
        var intCollegecount = Integer.parseInt(Countpast!!)
        var TotalSizeCount = intdepartment + intCollegecount
        if (TotalSizeCount > 0) {
            lbltotalsize!!.visibility = View.VISIBLE
            lbltotalsize!!.text = TotalSizeCount.toString()
        } else {
            lbltotalsize!!.visibility = View.GONE
        }
    }


    override val layoutResourceId: Int
        get() = R.layout.activity_noticeboard

    private fun EventRequest(type: Boolean) {
        val jsonObject = JsonObject()
        run {

            jsonObject.addProperty(ApiRequestNames.Req_userid, CommonUtil.MemberId)
            if (CommonUtil.Priority == "p1" || CommonUtil.Priority == "p2" || CommonUtil.Priority == "p3") {
                jsonObject.addProperty(ApiRequestNames.Req_sectionid, 0)
                jsonObject.addProperty(ApiRequestNames.Req_appid, CommonUtil.SenderAppId)
            } else {
                jsonObject.addProperty(ApiRequestNames.Req_sectionid, CommonUtil.SectionId)
                jsonObject.addProperty(ApiRequestNames.Req_appid, CommonUtil.ReceiverAppId)
            }
            jsonObject.addProperty(ApiRequestNames.Req_priority, CommonUtil.Priority)
            if (type) {
                jsonObject.addProperty(ApiRequestNames.Req_type, CommonUtil.UpcomingEvents)
            } else {
                jsonObject.addProperty(ApiRequestNames.Req_type, CommonUtil.PastEvents)
            }
            appViewModel!!.getEventListbyType(jsonObject, this)
            Log.d("EventListRequest:", jsonObject.toString())
        }
    }

    @OnClick(R.id.LayoutDepartment)
    fun departmentClick() {
        bottomsheetStateCollpased()
        TabDepartmentColor()
        EventType = true
        EventRequest(EventType)
        bottomsheetStateCollpased()
    }

    @OnClick(R.id.LayoutCollege)
    fun collegeClick() {
        bottomsheetStateCollpased()
        EventType = false
        EventRequest(EventType)
        TabCollegeColor()

    }

    override fun onBackPressed() {
        CommonUtil.OnBackSetBottomMenuClickTrue()
        super.onBackPressed()
    }

    override fun onResume() {
//        EventRequest(EventType)
        super.onResume()
        Log.d("lifecycle", "onResume")
    }

    override fun onRestart() {
        super.onRestart()

        if (EventType) {
            EventRequest(EventType)

        }
    }
    @OnClick(R.id.imgAddPlus)
    fun plusClick() {

//        Log.d()

        val i: Intent = Intent(this, AddEvents::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)

    }

}