package com.vsca.vsnapvoicecollege.Activities

import android.content.Intent
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
import com.vsca.vsnapvoicecollege.ActivitySender.AddVideo
import com.vsca.vsnapvoicecollege.ActivitySender.ImageOrPdf
import com.vsca.vsnapvoicecollege.Adapters.VideoAdapter
import com.vsca.vsnapvoicecollege.Interfaces.VideoListener
import com.vsca.vsnapvoicecollege.Model.GetVideoListDetails
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Repository.ApiRequestNames
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import com.vsca.vsnapvoicecollege.ViewModel.App
import java.util.ArrayList

class Video : BaseActivity() {
    var videoAdapter: VideoAdapter? = null
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
    @BindView(R.id.layoutTab)
    var layoutTab: ConstraintLayout? = null

    @JvmField
    @BindView(R.id.lblNoRecordsFound)
    var lblNoRecordsFound: TextView? = null

    var GetVideoListData: List<GetVideoListDetails> = ArrayList()
    var CountVideo="0"

    override fun onCreate(savedInstanceState: Bundle?) {
        CommonUtil.SetTheme(this)
        super.onCreate(savedInstanceState)

        appViewModel = ViewModelProvider(this).get(App::class.java)
        appViewModel!!.init()

        ButterKnife.bind(this)
        ActionBarMethod(this)
        MenuBottomType()
        OverAllMenuCountRequest(this, CommonUtil.MenuIDVideo!!)

        layoutTab!!.visibility = View.GONE
        lblMenuTitle!!.setText(R.string.txt_Video)
        CommonUtil.OnMenuClicks("Video")

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
                        VideoRequest()
                        OverAllMenuCountData = emptyList()
                    } else {
                        OverAllMenuCountData = response.data!!
                        CountVideo = OverAllMenuCountData[0].video!!
                        VideoRequest()
                        CountValueSet()
                    }
                } else {
                    VideoRequest()
                    OverAllMenuCountData = emptyList()
                }
            } else {
                VideoRequest()
            }
        }

        appViewModel!!.VideoListLiveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message
                UserMenuRequest(this)

                if (status == 1) {
                    GetVideoListData = response.data!!
                    CountValueSet()
                    if (GetVideoListData.size > 0) {
                        lblNoRecordsFound!!.visibility = View.GONE
                        recyclerNoticeboard!!.visibility = View.VISIBLE

                        videoAdapter =
                            VideoAdapter(GetVideoListData, this, object :
                                VideoListener {
                                override fun onVideoClick(
                                    holder: VideoAdapter.MyViewHolder,
                                    data: GetVideoListDetails
                                ) {
                                    holder.lnrplayvideo.setOnClickListener({
                                        val i: Intent = Intent(this@Video, VideoPlay::class.java)
                                        i.putExtra("iframe", data.iframe)
                                        i.putExtra("title", data.title)
                                        i.putExtra("description", data.description)
                                        i.putExtra("appread", data.isappviewed)
                                        i.putExtra("detailid", data.detailid)

                                        startActivity(i)
                                    })
                                }
                            })

                        val mLayoutManager: RecyclerView.LayoutManager =
                            LinearLayoutManager(this)
                        recyclerNoticeboard!!.layoutManager = mLayoutManager
                        recyclerNoticeboard!!.itemAnimator = DefaultItemAnimator()
                        recyclerNoticeboard!!.adapter = videoAdapter
                        recyclerNoticeboard!!.recycledViewPool.setMaxRecycledViews(0, 80)
                        videoAdapter!!.notifyDataSetChanged()

                    } else {
                        lblNoRecordsFound!!.visibility = View.VISIBLE
                        recyclerNoticeboard!!.visibility = View.GONE
                    }
                } else {
                    CountValueSet()
                    BaseActivity.UserMenuRequest(this)
                    lblNoRecordsFound!!.visibility = View.VISIBLE
                    recyclerNoticeboard!!.visibility = View.GONE
                }

            } else {
                CountValueSet()
                lblNoRecordsFound!!.visibility = View.VISIBLE
                recyclerNoticeboard!!.visibility = View.GONE
            }

        }


        imgRefresh!!.setOnClickListener(View.OnClickListener {

            VideoRequest()

        })
    }

    private fun CountValueSet() {
        var intdepartment=0
        if(CountVideo.equals("")|| CountVideo.equals("")){
            CountVideo="0"
        }else{
             intdepartment = Integer.parseInt(CountVideo)

        }

        var TotalSizeCount = intdepartment
        if (TotalSizeCount > 0) {
            lbltotalsize!!.visibility = View.VISIBLE
            lbltotalsize!!.text = TotalSizeCount.toString()
        } else {
            lbltotalsize!!.visibility = View.GONE
        }
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_noticeboard

    private fun VideoRequest() {
        val jsonObject = JsonObject()
        run {

            jsonObject.addProperty(ApiRequestNames.Req_userid, CommonUtil.MemberId)
            jsonObject.addProperty(ApiRequestNames.Req_collegeid, CommonUtil.CollegeId)
            jsonObject.addProperty(ApiRequestNames.Req_priority, CommonUtil.Priority)
            appViewModel!!.getVideoList(jsonObject, this)
            Log.d("VideoRequest:", jsonObject.toString())
        }
    }

    @OnClick(R.id.imgAddPlus)

    fun addVideo(){
        val i: Intent = Intent(this, AddVideo::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
    }

//    override fun onResume() {
//        VideoRequest()
//        super.onResume()
//    }

    override fun onBackPressed() {

        super.onBackPressed()
    }
}