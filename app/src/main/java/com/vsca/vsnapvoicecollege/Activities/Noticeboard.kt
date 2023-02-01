package com.vsca.vsnapvoicecollege.Activities


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
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
import com.vsca.vsnapvoicecollege.ActivitySender.AddTextNoticeboard
import com.vsca.vsnapvoicecollege.ActivitySender.ImageOrPdf
import com.vsca.vsnapvoicecollege.Adapters.NoticeBoard
import com.vsca.vsnapvoicecollege.Model.GetNoticeboardDetails
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Repository.ApiRequestNames
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import com.vsca.vsnapvoicecollege.ViewModel.App

class Noticeboard : BaseActivity() {
    var noticeboardAdapter: NoticeBoard? = null
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
    @BindView(R.id.lblDepartmentSize)
    var lblDepartmentSize: TextView? = null

    @JvmField
    @BindView(R.id.lblCollegeSize)
    var lblCollegeSize: TextView? = null

    @JvmField
    @BindView(R.id.lblNoRecordsFound)
    var lblNoRecordsFound: TextView? = null

    var NoticeboardType = true
    var GetNoticeboardData: ArrayList<GetNoticeboardDetails> = ArrayList()
    var GetCollegeNoticeBoardData: ArrayList<GetNoticeboardDetails> = ArrayList()
    var DepartmentCount: String? = null
    var CollegeCount: String? = null
    private lateinit var scrollListener: RecyclerView.OnScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        CommonUtil.SetTheme(this)

        super.onCreate(savedInstanceState)
        appViewModel = ViewModelProvider(this).get(App::class.java)
        appViewModel!!.init()
        ButterKnife.bind(this)
        ActionBarMethod(this@Noticeboard)

        TabDepartmentColor()
        MenuBottomType()
        OverAllMenuCountRequest(this, CommonUtil.MenuIDNoticeboard!!)


        CommonUtil.OnMenuClicks("Noticeboard")

        Glide.with(this@Noticeboard)
            .load(CommonUtil.CommonAdvertisement)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgAdvertisement!!)
        Glide.with(this@Noticeboard)
            .load(CommonUtil.CommonAdImageSmall)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgthumb!!)
        appViewModel!!.OverAllMenuResponseLiveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message
                if (status == 1) {
                    if (response.data.isNullOrEmpty()) {
                        NoticeboardRequest(NoticeboardType)
                        OverAllMenuCountData = emptyList()
                    } else {
                        OverAllMenuCountData = response.data!!
                        DepartmentCount = OverAllMenuCountData[0].departmentnotice
                        CollegeCount = OverAllMenuCountData[0].collegenotice
                        NoticeboardRequest(NoticeboardType)
                        CountValueSet()
                    }
                } else {
                    NoticeboardRequest(NoticeboardType)
                    OverAllMenuCountData = emptyList()
                }
            } else {
                NoticeboardRequest(NoticeboardType)
            }
        }


        appViewModel!!.noticeBoardResponseLiveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message
                UserMenuRequest(this@Noticeboard)
                if (status == 1) {
                    if (NoticeboardType) {
                        GetNoticeboardData = response.data!!
                        val size = GetNoticeboardData.size
                        GetCollegeNoticeBoardData.clear()
                        if (size > 0) {
                            lblNoRecordsFound!!.visibility = View.GONE
                            recyclerNoticeboard!!.visibility = View.VISIBLE

                            noticeboardAdapter = NoticeBoard(GetNoticeboardData, this@Noticeboard)
                            val mLayoutManager: RecyclerView.LayoutManager =
                                LinearLayoutManager(this@Noticeboard)
                            recyclerNoticeboard!!.layoutManager = mLayoutManager
                            recyclerNoticeboard!!.itemAnimator = DefaultItemAnimator()
                            recyclerNoticeboard!!.adapter = noticeboardAdapter
                            recyclerNoticeboard!!.recycledViewPool.setMaxRecycledViews(0, 80)
                            noticeboardAdapter!!.notifyDataSetChanged()

                        } else {
                            NoDataFound()
                        }
                    } else {
                        GetCollegeNoticeBoardData = response.data!!
                        val size = GetCollegeNoticeBoardData.size
                        if (size > 0) {
                            lblNoRecordsFound!!.visibility = View.GONE
                            recyclerNoticeboard!!.visibility = View.VISIBLE
                            noticeboardAdapter = NoticeBoard(GetCollegeNoticeBoardData, this@Noticeboard)
                            val mLayoutManager: RecyclerView.LayoutManager =
                                LinearLayoutManager(this@Noticeboard)
                            recyclerNoticeboard!!.layoutManager = mLayoutManager
                            recyclerNoticeboard!!.itemAnimator = DefaultItemAnimator()
                            recyclerNoticeboard!!.adapter = noticeboardAdapter
                            recyclerNoticeboard!!.recycledViewPool.setMaxRecycledViews(0, 80)
                            noticeboardAdapter!!.notifyDataSetChanged()
                        } else {
                            NoDataFound()
                        }
                    }

                } else {
                    NoDataFound()
                }
            } else {
                NoDataFound()
            }
        }
        imgRefresh!!.setOnClickListener(View.OnClickListener {
            if (NoticeboardType) {
                NoticeboardType = true
                NoticeboardRequest(NoticeboardType)
            } else {
                NoticeboardType = false
                NoticeboardRequest(NoticeboardType)
            }
        })

        recyclerNoticeboard!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)){
                    bottomsheetStateCollpased()
                }
            }
        })
    }

    private fun NoDataFound() {
        lblNoRecordsFound!!.visibility = View.VISIBLE
        recyclerNoticeboard!!.visibility = View.GONE
    }

    private fun CountValueSet() {

        if (!DepartmentCount.equals("0") && !DepartmentCount.equals("")) {
            lblDepartmentSize!!.visibility = View.VISIBLE
            lblDepartmentSize!!.text = DepartmentCount
        } else {
            lblDepartmentSize!!.visibility = View.GONE
            DepartmentCount = "0"
        }
        if (!CollegeCount.equals("0") && !CollegeCount.equals("")) {
            lblCollegeSize!!.visibility = View.VISIBLE
            lblCollegeSize!!.text = CollegeCount
        } else {
            lblCollegeSize!!.visibility = View.GONE
            CollegeCount = "0"
        }
        var intdepartment = Integer.parseInt(DepartmentCount!!)
        var intCollegecount = Integer.parseInt(CollegeCount!!)
        var TotalSizeCount = intdepartment + intCollegecount
        if (TotalSizeCount > 0) {
            lbltotalsize!!.visibility = View.VISIBLE
            lbltotalsize!!.text = TotalSizeCount.toString()
        } else {
            lbltotalsize!!.visibility = View.GONE
        }
    }

    override val layoutResourceId: Int
        protected get() = R.layout.activity_noticeboard

    private fun NoticeboardRequest(type: Boolean) {
        val jsonObject = JsonObject()
        run {
            jsonObject.addProperty(ApiRequestNames.Req_userid, CommonUtil.MemberId)
            if (CommonUtil.Priority == "p1" || CommonUtil.Priority == "p2" || CommonUtil.Priority == "p3") {
                jsonObject.addProperty(ApiRequestNames.Req_appid, CommonUtil.SenderAppId)
            } else {
                jsonObject.addProperty(ApiRequestNames.Req_appid, CommonUtil.ReceiverAppId)
            }
            jsonObject.addProperty(ApiRequestNames.Req_priority, CommonUtil.Priority)
            if (type) {
                jsonObject.addProperty(ApiRequestNames.Req_type, CommonUtil.DepartmentNotice)
            } else {
                jsonObject.addProperty(ApiRequestNames.Req_type, CommonUtil.CollegeNotice)
            }
            appViewModel!!.getNoticeboardList(jsonObject, this@Noticeboard)
            Log.d("NotiboardRequest:", jsonObject.toString())
        }
    }

    @OnClick(R.id.LayoutDepartment)
    fun departmentClick() {
        bottomsheetStateCollpased()
        TabDepartmentColor()

        NoticeboardType = true
        NoticeboardRequest(NoticeboardType)

    }

    @OnClick(R.id.LayoutCollege)
    fun collegeClick() {
        bottomsheetStateCollpased()
        NoticeboardType = false
        NoticeboardRequest(NoticeboardType)
        TabCollegeColor()

    }

    @OnClick(R.id.imgAddPlus)
    fun imgaddclick() {
        val i: Intent = Intent(this, AddTextNoticeboard::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.putExtra("screentype",true)
        startActivity(i)

    }
    override fun onBackPressed() {
        CommonUtil.OnBackSetBottomMenuClickTrue()
        super.onBackPressed()
    }

}