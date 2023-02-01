package com.vsca.vsnapvoicecollege.Activities

import android.content.Intent
import butterknife.BindView
import com.vsca.vsnapvoicecollege.R
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsca.vsnapvoicecollege.ViewModel.App
import android.os.Bundle
import butterknife.ButterKnife
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import butterknife.OnClick
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Repository.ApiRequestNames
import com.vsca.vsnapvoicecollege.Adapters.CircularAdapter
import com.vsca.vsnapvoicecollege.Model.GetCircularListDetails
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsca.vsnapvoicecollege.ActivitySender.ImageOrPdf

import java.util.ArrayList

class Circular : BaseActivity() {
    var circularadapter: CircularAdapter? = null
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
    @BindView(R.id.lblDepartmentSize)
    var lblDepartmentSize: TextView? = null

    @JvmField
    @BindView(R.id.lblCollegeSize)
    var lblCollegeSize: TextView? = null

    @JvmField
    @BindView(R.id.lblNoRecordsFound)
    var lblNoRecordsFound: TextView? = null


    var CircularType = true
    var GetCircularData: List<GetCircularListDetails> = ArrayList()
    var departmentSize = 0
    var TotalSize = ""
    var DepartmentCount: String? = null
    var CollegeCount: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        CommonUtil.SetTheme(this)

        super.onCreate(savedInstanceState)
        appViewModel = ViewModelProvider(this).get(App::class.java)
        appViewModel!!.init()
        ButterKnife.bind(this)
        ActionBarMethod(this)
        MenuBottomType()

        OverAllMenuCountRequest(this, CommonUtil.MenuIDCircular!!)
        CommonUtil.OnMenuClicks("Circular")

        TabDepartmentColor()

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
                        CircularRequest(CircularType)
                        OverAllMenuCountData = emptyList()
                    } else {
                        OverAllMenuCountData = response.data!!
                        DepartmentCount = OverAllMenuCountData[0].departmentcircular
                        CollegeCount = OverAllMenuCountData[0].collegecircular
                        CircularRequest(CircularType)
                        CountValueSet()
                    }
                } else {
                    CircularRequest(CircularType)
                    OverAllMenuCountData = emptyList()
                }
            } else {
                CircularRequest(CircularType)
            }
        }

        appViewModel!!.circularResponseLiveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message
                UserMenuRequest(this)
                if (status == 1) {
                    if (CircularType) {
                        GetCircularData = response.data!!
                        val size = GetCircularData.size
                        if (size > 0) {
                            lblNoRecordsFound!!.visibility = View.GONE
                            recyclerNoticeboard!!.visibility = View.VISIBLE
                            circularadapter = CircularAdapter(GetCircularData, this)
                            val mLayoutManager: RecyclerView.LayoutManager =
                                LinearLayoutManager(this)
                            recyclerNoticeboard!!.layoutManager = mLayoutManager
                            recyclerNoticeboard!!.itemAnimator = DefaultItemAnimator()
                            recyclerNoticeboard!!.adapter = circularadapter
                            recyclerNoticeboard!!.recycledViewPool.setMaxRecycledViews(0, 80)
                            circularadapter!!.notifyDataSetChanged()
                        } else {
                            NoDataFound()
                        }
                    } else {
                        GetCircularData = response.data!!
                        val size = GetCircularData.size
                        if (size > 0) {
                            lblNoRecordsFound!!.visibility = View.GONE
                            recyclerNoticeboard!!.visibility = View.VISIBLE

                            circularadapter = CircularAdapter(GetCircularData, this@Circular)
                            val mLayoutManager: RecyclerView.LayoutManager =
                                LinearLayoutManager(this@Circular)
                            recyclerNoticeboard!!.layoutManager = mLayoutManager
                            recyclerNoticeboard!!.itemAnimator = DefaultItemAnimator()
                            recyclerNoticeboard!!.adapter = circularadapter
                            recyclerNoticeboard!!.recycledViewPool.setMaxRecycledViews(0, 80)
                            circularadapter!!.notifyDataSetChanged()
                        } else {
                            NoDataFound()
                        }
                    }
                } else {
                    if (CircularType) {
                        NoDataFound()
                    } else {
                        NoDataFound()
                    }
                }
            } else {
                BaseActivity.Companion.UserMenuRequest(this)
                NoDataFound()
            }
        }

        imgRefresh!!.setOnClickListener(View.OnClickListener {
            if (CircularType) {
                CircularType = true
                CircularRequest(CircularType)
            } else {
                CircularType = false
                CircularRequest(CircularType)
            }
        })

        lblMenuTitle!!.setText(R.string.txt_img_pdf)
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

//    override fun onResume() {
//        CircularRequest(CircularType)
//        super.onResume()
//    }

    @OnClick(R.id.LayoutAdvertisement)
    fun adclick() {
        LoadWebViewContext(this, CommonUtil.AdWebURl)

    }

    override val layoutResourceId: Int
        protected get() = R.layout.activity_noticeboard

    fun CircularRequest(type: Boolean) {
        val jsonObject = JsonObject()
        run {
            jsonObject.addProperty(ApiRequestNames.Req_userid, CommonUtil.MemberId)
            if (CommonUtil.Priority == "p1" || CommonUtil.Priority == "p2" || CommonUtil.Priority == "p3") {
                jsonObject.addProperty(ApiRequestNames.Req_appid, 0)
            } else {
                jsonObject.addProperty(ApiRequestNames.Req_appid, CommonUtil.ReceiverAppId)
            }
            jsonObject.addProperty(ApiRequestNames.Req_priority, CommonUtil.Priority)
            if (type) {
                jsonObject.addProperty(ApiRequestNames.Req_type, CommonUtil.DepartmentCircular)
            } else {
                jsonObject.addProperty(ApiRequestNames.Req_type, CommonUtil.CollegeCircular)
            }
            appViewModel!!.getCircularList(jsonObject, this)
            Log.d("CircularRequest:", jsonObject.toString())
        }
    }

    @OnClick(R.id.LayoutDepartment)
    fun departmentClick() {
        bottomsheetStateCollpased()
        CircularType = true
        CircularRequest(CircularType)

        TabDepartmentColor()
    }

    @OnClick(R.id.LayoutCollege)
    fun collegeClick() {
        bottomsheetStateCollpased()
        CircularType = false
        CircularRequest(CircularType)

        TabCollegeColor()

    }

    @OnClick(R.id.imgAddPlus)
    fun imgaddclick() {

        val i: Intent = Intent(this, ImageOrPdf::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)

    }

    override fun onBackPressed() {
        CommonUtil.OnBackSetBottomMenuClickTrue()
        super.onBackPressed()
    }
}