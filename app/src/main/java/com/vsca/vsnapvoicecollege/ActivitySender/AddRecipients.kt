package com.vsca.vsnapvoicecollege.ActivitySender

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.Nullable
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Activities.ActionBarActivity
import com.vsca.vsnapvoicecollege.Activities.Communication
import com.vsca.vsnapvoicecollege.Adapters.SelectedRecipientAdapter
import com.vsca.vsnapvoicecollege.Interfaces.RecipientCheckListener
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Repository.ApiRequestNames
import com.vsca.vsnapvoicecollege.SenderModel.GetDepartmentData
import com.vsca.vsnapvoicecollege.SenderModel.GetDivisionData
import com.vsca.vsnapvoicecollege.SenderModel.RecipientSelected
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import com.vsca.vsnapvoicecollege.ViewModel.App

class AddRecipients : ActionBarActivity() {

    @JvmField
    @BindView(R.id.chboxEntire)
    var chboxEntire: CheckBox? = null

    @JvmField
    @Nullable
    @BindView(R.id.lblDivision)
    var lblDivision: TextView? = null

    @JvmField
    @BindView(R.id.lblDepartment)
    var lblDepartment: TextView? = null

    @JvmField
    @BindView(R.id.lblCourse)
    var lblCourse: TextView? = null

    @JvmField
    @BindView(R.id.lblYourClasses)
    var lblYourClasses: TextView? = null

    @JvmField
    @BindView(R.id.lblGroups)
    var lblGroups: TextView? = null

    @JvmField
    @BindView(R.id.recycleRecipients)
    var recycleRecipients: RecyclerView? = null

    @JvmField
    @BindView(R.id.lblSelectedRecipient)
    var lblSelectedRecipient: TextView? = null

    @JvmField
    @BindView(R.id.spinnerDropdown)
    var spinnerDropdown: Spinner? = null

    @JvmField
    @BindView(R.id.chboxParents)
    var chboxParents: CheckBox? = null

    @JvmField
    @BindView(R.id.chboxStaff)
    var chboxStaff: CheckBox? = null

    @JvmField
    @BindView(R.id.chboxStudent)
    var chboxStudent: CheckBox? = null

    var appViewModel: App? = null
    var SelecteRecipientType: String? = null
    var GetDivisionData: ArrayList<GetDivisionData>? = null
    var GetDepartmentData: ArrayList<GetDepartmentData>? = null
    var divisionadapter: SelectedRecipientAdapter? = null
    var SpinnerData = ArrayList<String>()
    var SelectedSpinnerID: String? = null
    var ScreenName: String? = null
    var isStaff: Boolean? = null
    var isStudent: Boolean? = null
    var isParent: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        CommonUtil.SetTheme(this)
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)
        ActionbarWithoutBottom(this)

        ScreenName = intent.getStringExtra("ScreenName")



        chboxParents!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                isParent=true
                isStudent=false
                isStaff = false
                chboxStudent!!.isChecked=false
                chboxStaff!!.isChecked=false
            }
        }
        chboxStaff?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                isStaff=true
                isParent=false
                isStudent = false
                chboxStudent!!.isChecked=false
                chboxParents!!.isChecked=false
            }
        }

        chboxStudent?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                isStudent=true
                isParent=false
                isStaff = false
                chboxStaff!!.isChecked=false
                chboxParents!!.isChecked=false
            }
        }
        appViewModel = ViewModelProvider(this).get(App::class.java)
        appViewModel!!.init()

        appViewModel!!.GetDivisionMutableLiveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message

                if (status == 1) {
                    GetDivisionData = response.data!!
                    if (GetDivisionData!!.size > 0) {
                        SelectedRecipientlist.clear()

                        GetDivisionData!!.forEach {
                            it.division_id
                            it.division_name

                            var divisions = RecipientSelected(it.division_id, it.division_name)
                            SelectedRecipientlist.add(divisions)
                        }
                        if (SelecteRecipientType.equals("Division")) {
                            divisionadapter = SelectedRecipientAdapter(
                                SelectedRecipientlist!!,
                                this,
                                object : RecipientCheckListener {
                                    override fun add(data: RecipientSelected?) {
                                        var divisionId = data!!.SelectedId
                                    }

                                    override fun remove(data: RecipientSelected?) {
                                        var divisionId = data!!.SelectedId
                                    }
                                })

                            val mLayoutManager: RecyclerView.LayoutManager =
                                LinearLayoutManager(this)
                            recycleRecipients!!.layoutManager = mLayoutManager
                            recycleRecipients!!.itemAnimator = DefaultItemAnimator()
                            recycleRecipients!!.adapter = divisionadapter
                            recycleRecipients!!.recycledViewPool.setMaxRecycledViews(0, 80)
                            divisionadapter!!.notifyDataSetChanged()


                        } else if (SelecteRecipientType.equals("Department")) {
                            Log.d("Department", SelecteRecipientType!!)

                            LoadDivisionSpinner()
                        }

                    } else {
                        CommonUtil.ApiAlert(this, "No Data Found")
                    }
                } else {
                    CommonUtil.ApiAlert(this, "No Data Found")
                }
            } else {
                CommonUtil.ApiAlert(this, "Something went wrong")
            }
        }


        appViewModel!!.GetDepartmentMutableLiveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message
                if (status == 1) {
                    GetDepartmentData = response.data!!
                    if (GetDepartmentData!!.size > 0) {
                        SelectedRecipientlist.clear()
                        GetDepartmentData!!.forEach {
                            it.department_id
                            it.department_name
                            var divisions = RecipientSelected(it.department_id, it.department_name)
                            SelectedRecipientlist.add(divisions)
                        }
                        if (SelecteRecipientType.equals("Department")) {
                            divisionadapter = SelectedRecipientAdapter(
                                SelectedRecipientlist!!,
                                this,
                                object : RecipientCheckListener {
                                    override fun add(data: RecipientSelected?) {
                                        var divisionId = data!!.SelectedId
                                    }

                                    override fun remove(data: RecipientSelected?) {
                                        var divisionId = data!!.SelectedId
                                    }
                                })

                            val mLayoutManager: RecyclerView.LayoutManager =
                                LinearLayoutManager(this)
                            recycleRecipients!!.layoutManager = mLayoutManager
                            recycleRecipients!!.itemAnimator = DefaultItemAnimator()
                            recycleRecipients!!.adapter = divisionadapter
                            recycleRecipients!!.recycledViewPool.setMaxRecycledViews(0, 80)
                            divisionadapter!!.notifyDataSetChanged()

                        } else if (SelecteRecipientType.equals("")) {

                        }

                    } else {
                        CommonUtil.ApiAlert(this, "No Data Found")
                    }
                } else {
                    CommonUtil.ApiAlert(this, "No Data Found")
                }
            } else {
                CommonUtil.ApiAlert(this, "Something went wrong")
            }
        }


        appViewModel!!.SendSMSToEntireCollegeLiveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.Status
                val message = response.Message
                if (status.equals("1")) {
                    val i: Intent = Intent(this, Communication::class.java)
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(i)
                } else {
                    CommonUtil.ApiAlert(this, message)
                }
            } else {
                CommonUtil.ApiAlert(this, "Something went wrong")
            }
        }
        chboxEntire!!.setOnClickListener({
            lblDivision!!.setBackgroundResource(R.drawable.bg_available_outline_red)
            lblDivision!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))
            lblDepartment!!.setBackgroundResource(R.drawable.bg_available_outline_red)
            lblDepartment!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))
            lblCourse!!.setBackgroundResource(R.drawable.bg_available_outline_red)
            lblCourse!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))
            lblYourClasses!!.setBackgroundResource(R.drawable.bg_available_outline_red)
            lblYourClasses!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))
            lblGroups!!.setBackgroundResource(R.drawable.bg_available_outline_red)
            lblGroups!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))

        })

    }

    override val layoutResourceId: Int
        get() = R.layout.activity_add_recipients



    @OnClick(R.id.btnRecipientCancel)
    fun cancelClick() {
        onBackPressed()

    }

    private fun GetDivisionRequest() {

        val jsonObject = JsonObject()
        jsonObject.addProperty(ApiRequestNames.Req_user_id, CommonUtil.MemberId)
        jsonObject.addProperty(ApiRequestNames.Req_college_id, CommonUtil.CollegeId)
        appViewModel!!.getDivision(jsonObject, this)
    }

    private fun GetDepartmentRequest() {

        val jsonObject = JsonObject()
        jsonObject.addProperty(ApiRequestNames.Req_user_id, CommonUtil.MemberId)
        jsonObject.addProperty(ApiRequestNames.Req_college_id, CommonUtil.CollegeId)
        jsonObject.addProperty(ApiRequestNames.Req_div_id, SelectedSpinnerID)
        appViewModel!!.getDepartment(jsonObject, this)
    }



    private fun LoadDivisionSpinner() {

        SpinnerData.clear()
        GetDivisionData!!.forEach {

            SpinnerData.add(it.division_name!!)
        }
        val adapter = ArrayAdapter(this, R.layout.spinner_textview, SpinnerData)
        adapter.setDropDownViewResource(R.layout.spinner_recipient_layout)
        spinnerDropdown!!.adapter = adapter
        spinnerDropdown!!.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                SelectedSpinnerID = GetDivisionData!!.get(position).division_id

                Log.d("spinnerselected", SelectedSpinnerID!!)
                GetDepartmentRequest()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

    }



    @OnClick(R.id.btnConfirm)
    fun SendButtonAPi() {
        if (ScreenName.equals("Text")) {

            if (chboxEntire!!.isChecked) {
                SmsToEntireCollegeRequest()
                lblSelectedRecipient!!.setText("")
                SelectedRecipientlist.clear()
            } else {
                SmsToParticularTypeRequest()
            }

        } else if (ScreenName.equals("Noticeboard")) {

        }


    }

    private fun SmsToEntireCollegeRequest() {

        val jsonObject = JsonObject()
        jsonObject.addProperty(ApiRequestNames.Req_collegeid, CommonUtil.CollegeId)
        jsonObject.addProperty(ApiRequestNames.Req_staffid, CommonUtil.MemberId)
        jsonObject.addProperty(ApiRequestNames.Req_Callertye, CommonUtil.Priority)
        jsonObject.addProperty(ApiRequestNames.Req_filetype, "1")
        jsonObject.addProperty(ApiRequestNames.Req_MessageContent, CommonUtil.MenuTitle)
        jsonObject.addProperty(ApiRequestNames.Req_isStudent, isStudent)
        jsonObject.addProperty(ApiRequestNames.Req_isStaff, isStaff)
        jsonObject.addProperty(ApiRequestNames.Req_isParent, isParent)
        jsonObject.addProperty(ApiRequestNames.Req_Description, CommonUtil.MenuDescription)

        appViewModel!!.SendSmsToEntireCollege(jsonObject, this)

        Log.d("SMSJsonObject", jsonObject.toString())

    }

    private fun SmsToParticularTypeRequest() {

    }

    @OnClick(R.id.lblDivision)
    fun divisionClick() {
        chboxEntire!!.isChecked = false

        spinnerDropdown!!.visibility = View.GONE

        lblDivision!!.setBackgroundResource(R.drawable.bg_available_selected_green)
        lblDivision!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_white)))

        lblDepartment!!.setBackgroundResource(R.drawable.bg_available_outline_red)
        lblDepartment!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))

        lblCourse!!.setBackgroundResource(R.drawable.bg_available_outline_red)
        lblCourse!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))

        lblYourClasses!!.setBackgroundResource(R.drawable.bg_available_outline_red)
        lblYourClasses!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))

        lblGroups!!.setBackgroundResource(R.drawable.bg_available_outline_red)
        lblGroups!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))

        SelecteRecipientType = lblDivision!!.text.toString()
        lblSelectedRecipient?.text = SelecteRecipientType!!
        GetDivisionRequest()


    }

    @OnClick(R.id.lblDepartment)
    fun departmentClick() {

        chboxEntire!!.isChecked = false

        spinnerDropdown!!.visibility = View.VISIBLE

        GetDivisionRequest()

        lblDivision!!.setBackgroundResource(R.drawable.bg_available_outline_red)
        lblDivision!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))

        lblDepartment!!.setBackgroundResource(R.drawable.bg_available_selected_green)
        lblDepartment!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_white)))

        lblCourse!!.setBackgroundResource(R.drawable.bg_available_outline_red)
        lblCourse!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))

        lblYourClasses!!.setBackgroundResource(R.drawable.bg_available_outline_red)
        lblYourClasses!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))

        lblGroups!!.setBackgroundResource(R.drawable.bg_available_outline_red)
        lblGroups!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))

        SelecteRecipientType = lblDepartment!!.text.toString()
        lblSelectedRecipient?.text = SelecteRecipientType!!


    }
    @OnClick(R.id.lblCourse)
    fun CourseClick() {
        chboxEntire!!.isChecked = false

        lblDivision!!.setBackgroundResource(R.drawable.bg_available_outline_red)
        lblDivision!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))

        lblDepartment!!.setBackgroundResource(R.drawable.bg_available_outline_red)
        lblDepartment!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))

        lblCourse!!.setBackgroundResource(R.drawable.bg_available_selected_green)
        lblCourse!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_white)))

        lblYourClasses!!.setBackgroundResource(R.drawable.bg_available_outline_red)
        lblYourClasses!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))

        lblGroups!!.setBackgroundResource(R.drawable.bg_available_outline_red)
        lblGroups!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))

        SelecteRecipientType = lblCourse!!.text.toString()
        lblSelectedRecipient?.text = SelecteRecipientType!!

    }


    @OnClick(R.id.lblYourClasses)
    fun YourClassesClick() {
        SelecteRecipientType = lblYourClasses!!.text.toString()
        lblSelectedRecipient?.text = SelecteRecipientType!!
        chboxEntire!!.isChecked = false

        lblDivision!!.setBackgroundResource(R.drawable.bg_available_outline_red)
        lblDivision!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))

        lblDepartment!!.setBackgroundResource(R.drawable.bg_available_outline_red)
        lblDepartment!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))

        lblCourse!!.setBackgroundResource(R.drawable.bg_available_outline_red)
        lblCourse!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))

        lblYourClasses!!.setBackgroundResource(R.drawable.bg_available_selected_green)
        lblYourClasses!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_white)))

        lblGroups!!.setBackgroundResource(R.drawable.bg_available_outline_red)
        lblGroups!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))

    }

    @OnClick(R.id.lblGroups)
    fun GroupsClick() {
        SelecteRecipientType = lblGroups!!.text.toString()
        lblSelectedRecipient?.text = SelecteRecipientType!!
        chboxEntire!!.isChecked = false
        spinnerDropdown!!.visibility = View.GONE

        lblDivision!!.setBackgroundResource(R.drawable.bg_available_outline_red)
        lblDivision!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))

        lblDepartment!!.setBackgroundResource(R.drawable.bg_available_outline_red)
        lblDepartment!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))

        lblCourse!!.setBackgroundResource(R.drawable.bg_available_outline_red)
        lblCourse!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))

        lblYourClasses!!.setBackgroundResource(R.drawable.bg_available_outline_red)
        lblYourClasses!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_red)))

        lblGroups!!.setBackgroundResource(R.drawable.bg_available_selected_green)
        lblGroups!!.setTextColor(Color.parseColor(getString(R.string.lbl_clr_white)))

    }
}