package com.vsca.vsnapvoicecollege.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Adapters.EventsAdapter
import com.vsca.vsnapvoicecollege.Adapters.FacultyAdapter
import com.vsca.vsnapvoicecollege.Interfaces.EventClickListener
import com.vsca.vsnapvoicecollege.Model.GetEventDetailsData
import com.vsca.vsnapvoicecollege.Model.GetFacultyListDetails
import com.vsca.vsnapvoicecollege.Model.GetFacultyListResponse
import com.vsca.vsnapvoicecollege.Model.SemesterSectionListDetails
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Repository.ApiRequestNames
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import com.vsca.vsnapvoicecollege.ViewModel.App

class Faculty : BaseActivity() {
    var facultyAdapter: FacultyAdapter? = null
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
    @BindView(R.id.lblNoRecordsFound)
    var lblNoRecordsFound: TextView? = null

    @JvmField
    @BindView(R.id.SpinnerSemester)
    var SpinnerSemester: Spinner? = null

    var semspin: Array<String?>? = null
    var semspinid: Array<String?>? = null
    var SemesterId = ""


    var GetSemesterSectionData: List<SemesterSectionListDetails> = ArrayList()
    var GetFacultyLiveData: List<GetFacultyListDetails> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
      CommonUtil.SetTheme(this)

        super.onCreate(savedInstanceState)
        appViewModel = ViewModelProvider(this).get(App::class.java)
        appViewModel!!.init()
        ButterKnife.bind(this)
        ActionBarMethod(this)
        MenuBottomType()

        CommonUtil.OnMenuClicks("Faculty")

        Glide.with(this)
            .load(CommonUtil.CommonAdvertisement)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgAdvertisement!!)
        Glide.with(this)
            .load(CommonUtil.CommonAdImageSmall)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgthumb!!)

        appViewModel!!.SemesterSectionLiveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message
                UserMenuRequest(this)
                if (status == 1) {
                    GetSemesterSectionData = response.data!!

                    if (GetSemesterSectionData.size > 0) {
                        lblNoRecordsFound!!.visibility = View.GONE
                        recyclerNoticeboard!!.visibility = View.VISIBLE

                        semspin = arrayOfNulls(GetSemesterSectionData.size)
                        semspinid = arrayOfNulls(GetSemesterSectionData.size)

                        for (j in GetSemesterSectionData.indices) {
                            semspinid!![j] = GetSemesterSectionData.get(j).clgsemesterid
                            semspin!![j] = GetSemesterSectionData.get(j).semestername!!
                        }

                        val adapter = ArrayAdapter(this, R.layout.spinner_textview, semspin!!)
                        adapter.setDropDownViewResource(R.layout.dopdown_spinner)
                        SpinnerSemester!!.setPrompt("Select Semester");
                        SpinnerSemester!!.adapter = adapter
                        SpinnerSemester!!.onItemSelectedListener = object :
                            AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>,
                                view: View,
                                position: Int,
                                id: Long
                            ) {
                                SemesterId = GetSemesterSectionData.get(position).clgsemesterid!!
                                Log.d("SemesterId", SemesterId)

                                FacultyRequest()

                            }
                            override fun onNothingSelected(parent: AdapterView<*>) {

                            }
                        }
                    } else {
                        lblNoRecordsFound!!.visibility = View.VISIBLE
                        recyclerNoticeboard!!.visibility = View.GONE
                    }
                } else {
                    UserMenuRequest(this)
                    lblNoRecordsFound!!.visibility = View.VISIBLE
                    recyclerNoticeboard!!.visibility = View.GONE
                }

            } else {
                lblNoRecordsFound!!.visibility = View.VISIBLE
                recyclerNoticeboard!!.visibility = View.GONE
            }

        }

        appViewModel!!.FacultyLiveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message
                UserMenuRequest(this)
                if (status == 1) {
                    lblNoRecordsFound!!.visibility = View.GONE
                    recyclerNoticeboard!!.visibility = View.VISIBLE
                    GetFacultyLiveData = response.data!!
                    facultyAdapter = FacultyAdapter(GetFacultyLiveData, this)
                    val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
                    recyclerNoticeboard!!.layoutManager = mLayoutManager
                    recyclerNoticeboard!!.itemAnimator = DefaultItemAnimator()
                    recyclerNoticeboard!!.adapter = facultyAdapter
                    recyclerNoticeboard!!.recycledViewPool.setMaxRecycledViews(0, 80)
                    facultyAdapter!!.notifyDataSetChanged()
                } else {
                    lblNoRecordsFound!!.visibility = View.VISIBLE
                    recyclerNoticeboard!!.visibility = View.GONE
                }
            } else {
                UserMenuRequest(this)
                lblNoRecordsFound!!.visibility = View.VISIBLE
                recyclerNoticeboard!!.visibility = View.GONE
            }
        }

    }

    private fun FacultyRequest() {
        val jsonObject = JsonObject()
        run {
            jsonObject.addProperty(ApiRequestNames.Req_userid, CommonUtil.MemberId)
            jsonObject.addProperty(ApiRequestNames.Req_appid, CommonUtil.ReceiverAppId)
            jsonObject.addProperty(ApiRequestNames.Req_priority, CommonUtil.Priority)
            jsonObject.addProperty(ApiRequestNames.Req_sectionid, CommonUtil.SectionId)
            jsonObject.addProperty(ApiRequestNames.Req_semesterid, SemesterId)
            appViewModel!!.getFaculty(jsonObject, this)
            Log.d("FacultyList:", jsonObject.toString())

        }
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_faculty_main

    private fun SemesterSectionRequest() {
        val jsonObject = JsonObject()
        run {
            jsonObject.addProperty(ApiRequestNames.Req_yearid, CommonUtil.YearId)
            appViewModel!!.getSemesterAndSection(jsonObject, this)
            Log.d("SemesterSection:", jsonObject.toString())
        }
    }


    override fun onResume() {
        SemesterSectionRequest()
        super.onResume()
    }

    override fun onBackPressed() {
        CommonUtil.OnBackSetBottomMenuClickTrue()
        super.onBackPressed()
    }
}