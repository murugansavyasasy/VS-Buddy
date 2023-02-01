package com.vsca.vsnapvoicecollege.Activities

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vsca.vsnapvoicecollege.Adapters.EventsPhotoAdapter
import com.vsca.vsnapvoicecollege.Model.DashboardSubItems
import com.vsca.vsnapvoicecollege.Model.GetEventDetailsData
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import java.util.ArrayList

class EventsViewDetails : ActionBarActivity() {
    @JvmField
    @BindView(R.id.imgAdvertisement)
    var imgAdvertisement: ImageView? = null

    @JvmField
    @BindView(R.id.imgthumb)
    var imgthumb: ImageView? = null

    @JvmField
    @BindView(R.id.lblEventDate)
    var lblEventDate: TextView? = null

    @JvmField
    @BindView(R.id.lblEventDescription)
    var lblEventDescription: TextView? = null

    @JvmField
    @BindView(R.id.lblEventTopic)
    var lblEventTopic: TextView? = null

    @JvmField
    @BindView(R.id.lblEventVenue)
    var lblEventVenue: TextView? = null

    @JvmField
    @BindView(R.id.lblEventTime)
    var lblEventTime: TextView? = null

    @JvmField
    @BindView(R.id.recyleEventPhoto)
    var recyleEventPhoto: RecyclerView? = null

    @JvmField
    @BindView(R.id.lblNoPhotoFound)
    var lblNoPhotoFound: TextView? = null

    var eventsdata: GetEventDetailsData? = null
    var photoAdapter: EventsPhotoAdapter? = null
    private var eventPhotosList: List<String>? = null
    var eventsDetaildID: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        CommonUtil.SetTheme(this)
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)
        ActionbarWithoutBottom(this@EventsViewDetails)

        Glide.with(this)
            .load(CommonUtil.CommonAdvertisement)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgAdvertisement!!)
        Glide.with(this)
            .load(CommonUtil.CommonAdImageSmall)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgthumb!!)

        eventsdata = intent.getSerializableExtra("EventsData") as? GetEventDetailsData
        lblEventTime!!.text = eventsdata!!.event_time
        lblEventVenue!!.text = eventsdata!!.venue
        lblEventDate!!.text = eventsdata!!.event_date
        lblEventTopic!!.text = eventsdata!!.topic
        lblEventDescription!!.text = eventsdata!!.body

        eventsDetaildID = eventsdata!!.eventdetailsid

        if (eventsdata!!.isappread.equals("0")) {
            AppReadStatusActionbar(this, "event", eventsdata!!.eventdetailsid!!)
        }

        try {
            eventPhotosList = eventsdata!!.newfilepath
            if (eventPhotosList.isNullOrEmpty()) {
                recyleEventPhoto!!.visibility = View.GONE
                lblNoPhotoFound!!.visibility = View.VISIBLE
            } else {
                eventPhotosList = eventsdata!!.newfilepath
                if (eventPhotosList!!.size > 0) {
                    recyleEventPhoto!!.visibility = View.VISIBLE
                    lblNoPhotoFound!!.visibility = View.GONE

                    photoAdapter = EventsPhotoAdapter(eventPhotosList!!, this)
                    val mLayoutManager: RecyclerView.LayoutManager =
                        GridLayoutManager(applicationContext, 3)
                    recyleEventPhoto!!.layoutManager = mLayoutManager
                    recyleEventPhoto!!.isNestedScrollingEnabled = false
                    recyleEventPhoto!!.addItemDecoration(GridSpacingItemDecoration(4, false))
                    recyleEventPhoto!!.itemAnimator = DefaultItemAnimator()
                    recyleEventPhoto!!.adapter = photoAdapter

                } else {
                    recyleEventPhoto!!.visibility = View.GONE
                    lblNoPhotoFound!!.visibility = View.VISIBLE
                }
            }
        } catch (NpE: NullPointerException) {
            recyleEventPhoto!!.visibility = View.GONE
            lblNoPhotoFound!!.visibility = View.VISIBLE
            NpE.printStackTrace()
            Log.d("Catch", "catch")
        }

    }


    override val layoutResourceId: Int
        protected get() = R.layout.activity_events

    @OnClick(R.id.imgEventback)
    fun eventclickback() {
        onBackPressed()

    }

    override fun onBackPressed() {

        super.onBackPressed()
    }

    class GridSpacingItemDecoration(private val spanCount: Int, includeEdge: Boolean) :
        RecyclerView.ItemDecoration() {
        private var spacing = 4
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


}