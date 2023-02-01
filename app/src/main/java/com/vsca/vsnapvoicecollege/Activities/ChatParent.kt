package com.vsca.vsnapvoicecollege.Activities

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Adapters.ChatStaffAdapter
import com.vsca.vsnapvoicecollege.Interfaces.ChatListener
import com.vsca.vsnapvoicecollege.Model.GetStaffDetailsData
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Repository.ApiRequestNames
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import com.vsca.vsnapvoicecollege.ViewModel.App
import java.util.ArrayList

class ChatParent : BaseActivity() {
    var chatAdapter: ChatStaffAdapter? = null
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

    var GetStaffListData: List<GetStaffDetailsData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        CommonUtil.SetTheme(this)


        super.onCreate(savedInstanceState)
        appViewModel = ViewModelProvider(this).get(App::class.java)
        appViewModel!!.init()
        ButterKnife.bind(this)
        ActionBarMethod(this)

        MenuBottomType()
        layoutTab!!.visibility = View.GONE
        CommonUtil.OnMenuClicks("Chat")
        lblMenuTitle!!.setText("Chat")

        Glide.with(this)
            .load(CommonUtil.CommonAdvertisement)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgAdvertisement!!)
        Glide.with(this)
            .load(CommonUtil.CommonAdImageSmall)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgthumb!!)

        appViewModel!!.GetStaffDetailsLiveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message
                UserMenuRequest(this)

                if (status == 1) {
                    GetStaffListData = response.data!!
                    Log.d("test", GetStaffListData.size.toString())
                    chatAdapter = ChatStaffAdapter(GetStaffListData, this,
                        object : ChatListener {
                            override fun onChatStaffCLick(
                                holder: ChatStaffAdapter.MyViewHolder,
                                data: GetStaffDetailsData
                            ) {
                                holder.LayoutStaff!!.setOnClickListener(object :
                                    View.OnClickListener {
                                    override fun onClick(view: View) {
                                        val i =
                                            Intent(this@ChatParent, ChatCommunication::class.java)
                                        i.putExtra("ChatStaff", data)
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        startActivity(i)
                                    }
                                })
                            }
                        })
                    val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(
                        applicationContext, 2
                    )
                    recyclerNoticeboard!!.layoutManager = mLayoutManager
                    recyclerNoticeboard!!.isNestedScrollingEnabled = false
                    recyclerNoticeboard!!.addItemDecoration(GridSpacingItemDecoration(2, false))
                    recyclerNoticeboard!!.itemAnimator = DefaultItemAnimator()
                    recyclerNoticeboard!!.adapter = chatAdapter
                } else {

                    lblNoRecordsFound!!.visibility = View.VISIBLE
                    recyclerNoticeboard!!.visibility = View.GONE

                    //                    CommonUtil.ApiAlertContext(applicationContext, message)
                }
            } else {

                UserMenuRequest(this)
                lblNoRecordsFound!!.visibility = View.VISIBLE
                recyclerNoticeboard!!.visibility = View.GONE

            }
        }


        imgRefresh!!.setOnClickListener(View.OnClickListener {

            VideoRequest()

        })
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_noticeboard

    private fun VideoRequest() {
        val jsonObject = JsonObject()
        run {

            jsonObject.addProperty(ApiRequestNames.Req_student_id, CommonUtil.MemberId)
            jsonObject.addProperty(ApiRequestNames.Req_college_id, CommonUtil.CollegeId)
            appViewModel!!.getStaffDetailsForApp(jsonObject, this)
            Log.d("ChatStaff:", jsonObject.toString())
        }
    }

    class GridSpacingItemDecoration(private val spanCount: Int, includeEdge: Boolean) :
        RecyclerView.ItemDecoration() {
        private var spacing = 2
        private val includeEdge: Boolean
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount
            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount
                outRect.right = (column - 1) * spacing / spanCount
                if (position < spanCount) {
                    outRect.top = spacing
                }
                outRect.bottom = spacing
            } else {
                outRect.left = column * spacing / spanCount
                outRect.right = spacing - (column + 1) * spacing / spanCount
                if (position >= spanCount) {
                    outRect.top = spacing
                }
            }
        }

        init {
            spacing = spacing
            this.includeEdge = includeEdge
        }
    }

    override fun onResume() {
        VideoRequest()
        super.onResume()
    }

    override fun onBackPressed() {
        CommonUtil.OnBackSetBottomMenuClickTrue()

        super.onBackPressed()
    }
}