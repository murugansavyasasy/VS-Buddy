package com.vsca.vsnapvoicecollege.Activities

import butterknife.BindView
import com.vsca.vsnapvoicecollege.R

import androidx.recyclerview.widget.RecyclerView
import com.vsca.vsnapvoicecollege.ViewModel.App
import android.os.Bundle
import butterknife.ButterKnife
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.DefaultItemAnimator
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import butterknife.OnClick
import android.widget.TextView
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Repository.ApiRequestNames
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.vsca.vsnapvoicecollege.Adapters.CourseDetailsAdapter
import com.vsca.vsnapvoicecollege.Model.GetCourseDetailsData
import com.vsca.vsnapvoicecollege.Model.GetExamApplicationDetails
import com.vsca.vsnapvoicecollege.Model.GetProfileDetails

import java.util.ArrayList

class CourseDetails : BaseActivity() {
    @JvmField
    @BindView(R.id.idRVCategories)
    var ryclerCourse: RecyclerView? = null

    @JvmField
    @BindView(R.id.imgheaderBack)
    var imgheaderBack: ImageView? = null

    @JvmField
    @BindView(R.id.lblNoRecordsFound)
    var lblNoRecordsFound: TextView? = null

    @JvmField
    @BindView(R.id.lblMenuHeaderName)
    var lblMenuHeaderName: TextView? = null

    override var appViewModel: App? = null
    var courseadapter: CourseDetailsAdapter? = null
    var GetCourseDetailsData: List<GetCourseDetailsData> = ArrayList()
    var GetExamAppiccationData: List<GetExamApplicationDetails> = ArrayList()
    var GetProfileData: List<GetProfileDetails> = ArrayList()
    var examlistSize = 0
    var CourseListSize = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)
        MenuBottomType()

        appViewModel = ViewModelProvider(this).get(App::class.java)
        appViewModel!!.init()
        if (CommonUtil.parentMenuCourseExam == 1) {
            //ExamAppDetails
            ryclerCourse!!.setBackgroundColor(Color.parseColor("#f2f2f2"))
            lblMenuHeaderName!!.setText(R.string.txt_exam_app_details)
            CommonUtil.OnMenuClicks("ExamApp")
            ExamApplicationDetails()

            appViewModel!!.examApplicationResponseLiveData?.observe(this) { response ->
                if (response != null) {
                    val status = response.status
                    val message = response.message
                    if (status == 1) {
                        BaseActivity.Companion.UserMenuRequest(this@CourseDetails)
                        GetExamAppiccationData = response.data!!

                        examlistSize = GetExamAppiccationData.size

                        Log.d("testExamSize",examlistSize.toString())
                        Log.d("GetExamAppiccationData",GetExamAppiccationData.size.toString())
                        if (examlistSize > 0) {
                            lblNoRecordsFound!!.visibility = View.GONE
                            ryclerCourse!!.visibility = View.VISIBLE

                            courseadapter =
                                CourseDetailsAdapter(
                                    GetExamAppiccationData,
                                    this@CourseDetails,
                                    1
                                )
                            val mLayoutManager: RecyclerView.LayoutManager =
                                LinearLayoutManager(this@CourseDetails)
                            ryclerCourse!!.layoutManager = mLayoutManager
                            ryclerCourse!!.itemAnimator = DefaultItemAnimator()
                            ryclerCourse!!.adapter = courseadapter
                            ryclerCourse!!.recycledViewPool.setMaxRecycledViews(0, 80)
                            courseadapter!!.notifyDataSetChanged()
                        } else {
                            lblNoRecordsFound!!.visibility = View.VISIBLE
                            ryclerCourse!!.visibility = View.GONE

                        }
                    } else {
                        Log.d("testNoreocrds123", "test")
                        lblNoRecordsFound!!.visibility = View.VISIBLE
                        ryclerCourse!!.visibility = View.GONE
                    }
                } else {
                    Log.d("testNoreocrds12", "test12")
                    lblNoRecordsFound!!.visibility = View.VISIBLE
                    ryclerCourse!!.visibility = View.GONE
                }
            }
        }
        if (CommonUtil.parentMenuCourseExam == 0) {
            //CourseDetails
            ryclerCourse!!.setBackgroundColor(Color.parseColor("#f2f2f2"))

            lblMenuHeaderName!!.setText(R.string.txt_course_details)
            CommonUtil.OnMenuClicks("CourseDetails")

            CourseDetailsRequest()
            appViewModel!!.courseDetailsResposneLiveData!!.observe(this) { response ->
                if (response != null) {
                    val status = response.status
                    val message = response.message
                    if (status == 1) {
                        BaseActivity.Companion.UserMenuRequest(this@CourseDetails)
                        GetCourseDetailsData = response.data!!
                        CourseListSize = GetCourseDetailsData.size

                        if (CourseListSize > 0) {
                            lblNoRecordsFound!!.visibility = View.GONE
                            ryclerCourse!!.visibility = View.VISIBLE
                            courseadapter =
                                CourseDetailsAdapter(GetCourseDetailsData, this@CourseDetails, 0)
                            val mLayoutManager: RecyclerView.LayoutManager =
                                LinearLayoutManager(this@CourseDetails)
                            ryclerCourse!!.layoutManager = mLayoutManager
                            ryclerCourse!!.itemAnimator = DefaultItemAnimator()
                            ryclerCourse!!.adapter = courseadapter
                            ryclerCourse!!.recycledViewPool.setMaxRecycledViews(0, 80)
                            courseadapter!!.notifyDataSetChanged()
                        } else {
                            lblNoRecordsFound!!.visibility = View.VISIBLE
                            ryclerCourse!!.visibility = View.GONE
                        }
                    } else {
                        lblNoRecordsFound!!.visibility = View.VISIBLE
                        ryclerCourse!!.visibility = View.GONE
                    }
                } else {
                    Log.d("testNoreocrds", "test")
                    lblNoRecordsFound!!.visibility = View.VISIBLE
                    ryclerCourse!!.visibility = View.GONE
                }
            }
        }
        if (CommonUtil.parentMenuCourseExam == 2) {
            ryclerCourse!!.setBackgroundResource(R.drawable.bg_rectangle_white)

            lblMenuHeaderName!!.setText(R.string.txt_profile)
            appViewModel!!.getStudentProfile(CommonUtil.MemberId, this@CourseDetails)
            appViewModel!!.getprofileResponseLiveData()?.observe(this) { response ->
                if (response != null) {
                    val status = response.status
                    val message = response.message
                    if (status == 1) {
                        UserMenuRequest(this@CourseDetails)
                        GetProfileData = response.data!!
                        if (GetProfileData.size > 0) {
                            lblNoRecordsFound!!.visibility = View.GONE
                            ryclerCourse!!.visibility = View.VISIBLE
                            courseadapter =
                                CourseDetailsAdapter(this@CourseDetails, GetProfileData, 2)
                            val mLayoutManager: RecyclerView.LayoutManager =
                                LinearLayoutManager(this@CourseDetails)
                            ryclerCourse!!.layoutManager = mLayoutManager
                            ryclerCourse!!.itemAnimator = DefaultItemAnimator()
                            ryclerCourse!!.adapter = courseadapter
                            ryclerCourse!!.recycledViewPool.setMaxRecycledViews(0, 80)
                            courseadapter!!.notifyDataSetChanged()
                        } else {
                            lblNoRecordsFound!!.visibility = View.VISIBLE
                            ryclerCourse!!.visibility = View.GONE
                        }

                    } else {
                        lblNoRecordsFound!!.visibility = View.VISIBLE
                        ryclerCourse!!.visibility = View.GONE
                    }
                } else {
                    Log.d("testNoreocrds", "test")
                    lblNoRecordsFound!!.visibility = View.VISIBLE
                    ryclerCourse!!.visibility = View.GONE
                }
            }
        }
    }

    @OnClick(R.id.imgheaderBack)
    fun setImgheaderBackclick() {
        onBackPressed()
    }

    override fun onBackPressed() {
        CommonUtil.OnBackSetBottomMenuClickTrue()

        super.onBackPressed()
    }

    override val layoutResourceId: Int
        protected get() = R.layout.common_recyclerview_bottomsheet

    private fun CourseDetailsRequest() {
        val jsonObject = JsonObject()
        jsonObject.addProperty(ApiRequestNames.Req_user_id, CommonUtil.MemberId)
        jsonObject.addProperty(ApiRequestNames.Req_college_id, CommonUtil.CollegeId)
        jsonObject.addProperty(ApiRequestNames.Req_dept_id, CommonUtil.DepartmentId)
        jsonObject.addProperty(ApiRequestNames.Req_sem_id, CommonUtil.SemesterId)
        jsonObject.addProperty(ApiRequestNames.Req_section_id, CommonUtil.SectionId)
        appViewModel!!.getCourseDetails(jsonObject, this@CourseDetails)
        Log.d("GetCourseDetailRequest:", jsonObject.toString())
    }

    private fun ExamApplicationDetails() {
        val jsonObject = JsonObject()
        jsonObject.addProperty(ApiRequestNames.Req_i_course_id, CommonUtil.Courseid)
        jsonObject.addProperty(ApiRequestNames.Req_colgid, CommonUtil.CollegeId)
        jsonObject.addProperty(ApiRequestNames.Req_i_sem_id, CommonUtil.SemesterId)
        jsonObject.addProperty(ApiRequestNames.Req_i_student_id, CommonUtil.MemberId)
        appViewModel!!.getExamApplication(jsonObject, this@CourseDetails)
        Log.d("GetExamAppDetRequest:", jsonObject.toString())
    }
}