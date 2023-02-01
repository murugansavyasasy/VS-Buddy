package com.vsca.vsnapvoicecollege.Activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vsca.vsnapvoicecollege.Model.LeaveTypeData
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import com.vsca.vsnapvoicecollege.Utils.CommonUtil.SetTheme
import com.vsca.vsnapvoicecollege.ViewModel.App

class ApplyLeave : ActionBarActivity() {

    var appViewModel: App? = null

    @JvmField
    @BindView(R.id.imgAdvertisement)
    var imgAdvertisement: ImageView? = null

    @JvmField
    @BindView(R.id.imgthumb)
    var imgthumb: ImageView? = null

    @JvmField
    @BindView(R.id.SpinnerLeaveType)
    var SpinnerLeaveType: Spinner? = null

    var GetLeaveTypeData: List<LeaveTypeData> = ArrayList()
    var bookArrayList = ArrayList<String>()
    var LeaveName: String? = null
    var LeaveType: String? = null
    var LeaveID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        SetTheme(this)
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)
        ActionbarWithoutBottom(this)

        Glide.with(this)
            .load(CommonUtil.CommonAdvertisement)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgAdvertisement!!)
        Glide.with(this)
            .load(CommonUtil.CommonAdImageSmall)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgthumb!!)

        appViewModel!!.LeaveTypeLiveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message
                BaseActivity.UserMenuRequest(this)
                bookArrayList.clear()
                if (status == 1) {
                    GetLeaveTypeData = response.data!!
                    if (GetLeaveTypeData.size > 0) {
                        bookArrayList.add("Select Leave Type")
                        for (i in GetLeaveTypeData.indices) {
                            LeaveName = GetLeaveTypeData.get(i).leavetypename!!
                            bookArrayList.add(LeaveName!!)
                        }
                        val adapter: ArrayAdapter<String>
                        adapter = ArrayAdapter(
                            this,
                            android.R.layout.simple_spinner_item,
                            bookArrayList
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        SpinnerLeaveType!!.setAdapter(adapter)
                        SpinnerLeaveType!!.setOnItemSelectedListener(object :
                            AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                adapterView: AdapterView<*>?,
                                view: View,
                                i: Int,
                                l: Long
                            ) {
                                val index: Int = SpinnerLeaveType!!.getSelectedItemPosition()
                                LeaveID = index
                                LeaveType = SpinnerLeaveType!!.getSelectedItem().toString()
                            }

                            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
                        })
                    } else {

                    }
                } else {

                }

            } else {

            }

        }

    }


    override val layoutResourceId: Int
        protected get() = R.layout.activity_apply_leave

    @OnClick(R.id.imgLeaveback)
    fun LeaveApplyback() {
        onBackPressed()

    }

    override fun onBackPressed() {

        super.onBackPressed()
    }


}



