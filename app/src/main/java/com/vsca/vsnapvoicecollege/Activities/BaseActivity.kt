package com.vsca.vsnapvoicecollege.Activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.*
import androidx.annotation.Nullable
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Optional
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Adapters.HomeMenus
import com.vsca.vsnapvoicecollege.Interfaces.HomeMenuClickListener
import com.vsca.vsnapvoicecollege.Model.GetOverAllCountDetails
import com.vsca.vsnapvoicecollege.Model.MenuDetailsResponse
import com.vsca.vsnapvoicecollege.Model.StatusMessageResponse
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Repository.ApiRequestNames
import com.vsca.vsnapvoicecollege.Utils.*
import com.vsca.vsnapvoicecollege.ViewModel.App
import com.vsca.vsnapvoicecollege.ViewModel.Dashboards
import java.io.File

abstract class BaseActivity : AppCompatActivity() {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    @JvmField
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_swipe)
    var img_swipe: ImageView? = null

    @JvmField
    @BindView(R.id.btnContinue)
    var btnContinue: Button? = null

    @JvmField
    @Nullable
    @BindView(R.id.layoutbottomCurve)
    var layoutbottomCurve: ConstraintLayout? = null

    @JvmField
    @Nullable
    @BindView(R.id.recyclermenusbottom)
    var recyclermenusbottom: RecyclerView? = null

    @JvmField
    @Nullable
    @BindView(R.id.swipeUpMenus)
    var llBottomSheet: LinearLayout? = null

    @JvmField
    @Nullable
    @BindView(R.id.LayoutDepartment)
    var LayoutDepartment: ConstraintLayout? = null

    @JvmField
    @Nullable
    @BindView(R.id.LayoutCollege)
    var LayoutCollege: ConstraintLayout? = null

    @JvmField
    @Nullable
    @BindView(R.id.imgAddPlus)
    var imgAddPlus: ImageView? = null

    open var appViewModel: App? = null
    open var menuadapter: HomeMenus? = null
    var UserMenuData: ArrayList<MenuDetailsResponse> = ArrayList()
    var MenuList: ArrayList<MenuDetailsResponse> = ArrayList()
    var OverAllMenuCountData: List<GetOverAllCountDetails> = ArrayList()
    var appreadstatusData: List<StatusMessageResponse> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResourceId)
        ButterKnife.bind(this)

        appviewModelbase = ViewModelProvider(this).get(
            App::class.java
        )
        appviewModelbase!!.init()

        layoutbottomCurve!!.visibility = View.INVISIBLE
        dashboardViewModel = ViewModelProvider(this).get(
            Dashboards::class.java
        )
        dashboardViewModel!!.init()
        dashboardViewModel!!.userMenuLiveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message
                MenuList.clear()
                if (status == 1) {
                    UserMenuData = response.data!!
                    UserMenuData.removeAt(10)

                    for (j in UserMenuData.indices) {
                        val id = UserMenuData[j].id
                        val name = UserMenuData[j].name
                        MenuList.add(MenuDetailsResponse(id, name))
                    }

                    layoutbottomCurve!!.visibility = View.VISIBLE
                    MenuList.add(1, MenuDetailsResponse(11, "Chat"))

                    for (k in MenuList.indices) {

                        if (MenuList.get(k).name.equals("Home")) {
                            DashboardHomeMenuID = MenuList.get(k).id.toString()
                            Log.d("DashboardHomeMenuIDbase", DashboardHomeMenuID)
                        }
                        if (MenuList.get(k).name.equals("Chat")) {
                            ChatMenuID = MenuList.get(k).id.toString()
                            Log.d("Chat", ChatMenuID)
                        }
                        if (MenuList.get(k).name.equals("Communication")) {
                            CommunicationMenuID = MenuList.get(k).id.toString()
                            Log.d("communicationmenu", CommunicationMenuID)
                        }

                        if (MenuList.get(k).name.equals("Examination")) {
                            ExamMenuID = MenuList.get(k).id.toString()
                            Log.d("ExamMenuID", ExamMenuID)
                        }

                        if (MenuList.get(k).name.equals("Attendance")) {
                            AttendanceMeuID = MenuList.get(k).id.toString()
                            Log.d("AttendanceMeuID", AttendanceMeuID)
                        }
                        if (MenuList.get(k).name.equals("Assignment")) {
                            AssignmentMenuID = MenuList.get(k).id.toString()
                        }
                        if (MenuList.get(k).name.equals("Circular")) {
                            CircularMenuID = MenuList.get(k).id.toString()
                        }
                        if (MenuList.get(k).name.equals("NoticeBoard")) {
                            NoticeboardMenuID = MenuList.get(k).id.toString()
                        }
                        if (MenuList.get(k).name.equals("Events")) {
                            EventsMenuID = MenuList.get(k).id.toString()
                        }

                        if (MenuList.get(k).name.equals("Faculty")) {
                            FacultyMenuID = MenuList.get(k).id.toString()
                        }
                        if (MenuList.get(k).name.equals("Video")) {
                            VideoMenuID = MenuList.get(k).id.toString()
                        }

                        if (MenuList.get(k).name.equals("Course Details")) {
                            CourseDetailsMenuID = MenuList.get(k).id.toString()
                        }
                        if (MenuList.get(k).name.equals("Category Credit Points")) {
                            CategoryDetailsMenuID = MenuList.get(k).id.toString()
                        }

                        if (MenuList.get(k).name.equals("Sem Credit Points")) {
                            SemesterCreditMenuID = MenuList.get(k).id.toString()
                        }

                        if (MenuList.get(k).name.equals("Exam Application Details")) {
                            ExamApplicationMenuID = MenuList.get(k).id.toString()
                        }

                    }
                    menuadapter =
                        HomeMenus(applicationContext, MenuList, object : HomeMenuClickListener {
                            override fun onMenuClick(
                                holder: HomeMenus.MyViewHolder,
                                data: MenuDetailsResponse
                            ) {
                                holder.LayoutHome!!.setOnClickListener(object :
                                    View.OnClickListener {
                                    override fun onClick(view: View) {
                                        ParticularMenuClick(data)
                                    }
                                })
                            }
                        })
                    val mLayoutManager: RecyclerView.LayoutManager =
                        GridLayoutManager(applicationContext, 4)
                    recyclermenusbottom!!.layoutManager = mLayoutManager
                    recyclermenusbottom!!.isNestedScrollingEnabled = false
                    recyclermenusbottom!!.addItemDecoration(GridSpacingItemDecoration(4, false))
                    recyclermenusbottom!!.itemAnimator = DefaultItemAnimator()
                    recyclermenusbottom!!.adapter = menuadapter
                } else {
                    CommonUtil.ApiAlertContext(applicationContext, message)
                }
            }
        }
        appviewModelbase!!.appreadstatusresponseLiveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message
                if (status == 1) {
                    Log.d("AppReadMessage", message!!)
                } else {

                }
            }
        }

        appviewModelbase!!.ChangePasswordLiveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message
                if (status == 1) {
                    ApiAlertOk(this, message, true)
                } else {
                    ApiAlertOk(this, message, false)
                }
            } else {
                ApiAlertOk(this, "Something went wrong", false)

            }
        }

    }

    fun ApiAlertOk(activity: Activity?, msg: String?, value: Boolean) {
        if (activity != null) {
            val dlg = androidx.appcompat.app.AlertDialog.Builder(activity)
            dlg.setTitle("Info")
            dlg.setMessage(msg)
            dlg.setPositiveButton("OK") { dialog, which ->

                if (value) {
                    changePassword!!.dismiss()
                    val i = Intent(activity, Login::class.java)
                    startActivity(i)
                    finishAffinity()
                } else {
//                    changePassword!!.dismiss()

                }

            }
            dlg.setCancelable(false)
            dlg.create()
            dlg.show()
        }
    }

    private fun ParticularMenuClick(data: MenuDetailsResponse) {
        if (data.id == 1) {
            if (CommonUtil.MenuDashboardHome) {
                val i: Intent = Intent(this@BaseActivity, DashBoard::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }
        }
        if (data.id == 2) {
            CommonUtil.MenuIDCommunication = data.id.toString()
            if (CommonUtil.MenuCommunication) {
                val i: Intent = Intent(this@BaseActivity, Communication::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }
        }
        if (data.id == 3) {
            CommonUtil.MenuIDExamination = data.id.toString()

            if (CommonUtil.MenuExamination) {
                val i: Intent = Intent(this@BaseActivity, ExamList::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }
        }

        if (data.id == 4) {
            if (CommonUtil.MenuAttendance) {
                val i: Intent = Intent(this@BaseActivity, Attendance::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }
        }
        if (data.id == 5) {
            CommonUtil.MenuIDAssignment = data.id.toString()
            if (CommonUtil.MenuAssignment) {
                val i: Intent = Intent(this@BaseActivity, Assignment::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }
        }
        if (data.id == 6) {
            CommonUtil.MenuIDCircular = data.id.toString()
            if (CommonUtil.MenuCircular) {
                val i: Intent = Intent(this@BaseActivity, Circular::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }
        }
        if (data.id == 7) {
            CommonUtil.MenuIDNoticeboard = data.id.toString()
            if (CommonUtil.MenuNoticeBoard) {
                val i: Intent = Intent(this@BaseActivity, Noticeboard::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }
        }
        if (data.id == 8) {
            CommonUtil.MenuIDEvents = data.id.toString()
            if (CommonUtil.MenuEvents) {
                val i: Intent = Intent(this@BaseActivity, Events::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }
        }
        if (data.id == 9) {
            if (CommonUtil.MenuFaculty) {
                val i: Intent = Intent(this@BaseActivity, Faculty::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }
        }
        if (data.id == 10) {
            CommonUtil.MenuIDVideo = data.id.toString()
            if (CommonUtil.MenuVideo) {
                val i: Intent = Intent(this@BaseActivity, Video::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }
        }

        if (data.id == 11) {
            if (CommonUtil.MenuChat) {
                val i: Intent = Intent(this@BaseActivity, ChatParent::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }
        }
        if (data.id == 12) {
            if (CommonUtil.MenuCourseDetails) {
                CommonUtil.parentMenuCourseExam = 0
                val i: Intent = Intent(this@BaseActivity, CourseDetails::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }
        }
        if (data.id == 13) {

            if (CommonUtil.MenuCategoryCredit) {
                val i: Intent = Intent(this@BaseActivity, CategoryCreditWise::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }
        }

        if (data.id == 14) {
            if (CommonUtil.MenuSemCredit) {
                val i: Intent = Intent(this@BaseActivity, SemesterCreditCategoryWise::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }
        }
        if (data.id == 15) {
            if (CommonUtil.MenuExamDetails) {
                CommonUtil.parentMenuCourseExam = 1

                val i: Intent = Intent(this@BaseActivity, CourseDetails::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }
        }


    }

    @Optional
    @OnClick(R.id.btnContinue)
    fun check() {
        val intents = Intent(this@BaseActivity, DashBoard::class.java)
        startActivity(intents)
    }


    protected abstract val layoutResourceId: Int

    fun ActionBarMethod(activity: Activity) {
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar_layout)
        val view = supportActionBar!!.customView
        val lblMemberName = view.findViewById<View>(R.id.lblMemberName) as TextView
        val layoutUserDetails = view.findViewById<View>(R.id.layoutUserDetails) as ConstraintLayout
        val lblRole = view.findViewById<View>(R.id.lblRole) as TextView
        val imgMan = view.findViewById<View>(R.id.imgProfile) as ImageView
        imgNotification = view.findViewById<View>(R.id.imgNotification) as ImageView
        imgRefresh = view.findViewById<View>(R.id.imgRefresh) as ImageView
        val imgCollegeLogo = view.findViewById<View>(R.id.imgCollegeLogo) as ImageView
        val constAction = view.findViewById<View>(R.id.constAction) as ConstraintLayout
        if (CommonUtil.CollegeLogo == null || CommonUtil.CollegeLogo.isEmpty()) {
            Glide.with(activity)
                .load(R.drawable.dummy_college_icon)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgCollegeLogo)
        } else {
            Glide.with(activity)
                .load(CommonUtil.CollegeLogo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgCollegeLogo)
        }
        imgNotification!!.setOnClickListener {
            CommonUtil.HeaderMenuNotification = true
            val i = Intent(activity, Notification::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            activity.startActivity(i)
        }
        imgMan.setOnClickListener { ProfilePopUp(activity) }
        imgCollegeLogo.setOnClickListener { IntentToChangeRole(activity) }
        layoutUserDetails.setOnClickListener { IntentToChangeRole(activity) }
        if (CommonUtil.Priority == "p1") {
            constAction.setBackgroundColor(Color.parseColor(getString(R.string.txt_color_white)))
            lblRole.setTextColor(Color.parseColor(getString(R.string.txt_color_prinicpal)))
            lblMemberName.text = CommonUtil.MemberName
            lblRole.text = CommonUtil.MemberType
        }
        if (CommonUtil.Priority == "p2" || CommonUtil.Priority == "p3") {
            constAction.setBackgroundColor(Color.parseColor(getString(R.string.txt_color_white)))
            lblRole.setTextColor(Color.parseColor(getString(R.string.txt_clr_staff)))
            lblMemberName.text = CommonUtil.MemberName
            lblRole.text = CommonUtil.MemberType
        }
        if (CommonUtil.Priority == "p4") {
            constAction.setBackgroundColor(Color.parseColor(getString(R.string.txt_color_white)))
            lblRole.setTextColor(Color.parseColor(getString(R.string.txt_color_receiver)))
            lblMemberName.text = CommonUtil.MemberName
            lblRole.setText(R.string.txt_student)
        }
        if (CommonUtil.Priority == "p5") {
            constAction.setBackgroundColor(Color.parseColor(getString(R.string.txt_color_white)))
            lblRole.setTextColor(Color.parseColor(getString(R.string.txt_color_parent)))
            lblMemberName.text = CommonUtil.MemberName
            lblRole.setText(R.string.txt_parent)
        }
        if (CommonUtil.Priority == "p6") {
            constAction.setBackgroundColor(Color.parseColor(getString(R.string.txt_color_white)))
            lblRole.setTextColor(Color.parseColor(getString(R.string.txt_color_parent)))
            lblMemberName.text = CommonUtil.MemberName
            lblRole.setText(R.string.txt_parent)
        }
    }

    private fun ProfilePopUp(activity: Activity) {
        val layoutInflater = activity.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.popup_profile_menus, null)
        val logout = view.findViewById<View>(R.id.layoutLogout) as ConstraintLayout
        val layoutChangeRoles = view.findViewById<View>(R.id.layoutChangeRole) as ConstraintLayout
        val layoutProfile = view.findViewById<View>(R.id.layoutProfile) as ConstraintLayout
        val layoutFaq = view.findViewById<View>(R.id.layoutFAQ) as ConstraintLayout
        val layoutHelp = view.findViewById<View>(R.id.layoutHelp) as ConstraintLayout
        val layoutPrivacypolicy =
            view.findViewById<View>(R.id.layoutPrivacypolicy) as ConstraintLayout
        val layoutTermsAndCondition =
            view.findViewById<View>(R.id.layoutTermsCondition) as ConstraintLayout
        val layoutChangepassword =
            view.findViewById<View>(R.id.layoutChangePassword) as ConstraintLayout
        val layoutClearCache = view.findViewById<View>(R.id.layoutClearCache) as ConstraintLayout
        if (CommonUtil.Priority == "p4" || CommonUtil.Priority == "p5") {
            layoutProfile.visibility = View.VISIBLE
        } else {
            layoutProfile.visibility = View.GONE
        }
        layoutProfile.setOnClickListener {
            profilePopup!!.dismiss()
            CommonUtil.parentMenuCourseExam = 2
            val i = Intent(activity, CourseDetails::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            activity.startActivity(i)
        }
        layoutChangepassword.setOnClickListener({
            ChangepasswordPopup(activity)
        })

        layoutFaq.setOnClickListener {
            val faq = SharedPreference.getSH_Faq(activity)
            LoadWebView(activity, faq, 0)
        }
        layoutHelp.setOnClickListener {
            val help = SharedPreference.getSH_Help(activity)
            LoadWebView(activity, help, 1)
        }
        layoutPrivacypolicy.setOnClickListener {
            val privacypolicy = SharedPreference.getSH_Privacypolicy(activity)
            LoadWebView(activity, privacypolicy, 2)
        }
        layoutTermsAndCondition.setOnClickListener {
            val termsCondition = SharedPreference.getSH_TermsNcondition(activity)
            LoadWebView(activity, termsCondition, 3)
        }
        logout.setOnClickListener { LogoutAlert(getString(R.string.txt_alert_logout), 1, activity) }
        layoutChangeRoles.setOnClickListener { IntentToChangeRole(activity) }
        layoutClearCache.setOnClickListener { ClearCache(activity) }
        profilePopup =
            PopupWindow(view, ListPopupWindow.WRAP_CONTENT, ListPopupWindow.WRAP_CONTENT, true)
        profilePopup!!.contentView = view
        profilePopup!!.isOutsideTouchable = false
        profilePopup!!.showAtLocation(view, Gravity.RIGHT or Gravity.TOP, 0, 225)
    }

    private fun ChangepasswordPopup(activity: Activity) {
        val layoutInflater = activity.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.change_password_popup, null)
        val txtOldPassword = view.findViewById<View>(R.id.txtOldPassword) as EditText
        val txtNewPassword = view.findViewById<View>(R.id.txtNewPassword) as EditText
        val txtConfirmPassword = view.findViewById<View>(R.id.txtConfirmPassword) as EditText
        val imgOldPassword = view.findViewById<View>(R.id.imgOldPassword) as ImageView
        val imgNewPassword = view.findViewById<View>(R.id.imgNewPassword) as ImageView
        val imgconfirmpassword = view.findViewById<View>(R.id.imgconfirmpassword) as ImageView
        val btnSubmit = view.findViewById<View>(R.id.btnSubmit) as Button
        val imgClose = view.findViewById<View>(R.id.imgClose) as ImageView
        imgClose.setOnClickListener({
            changePassword!!.dismiss()
        })

        imgconfirmpassword.setOnClickListener({
            passwordHideandShow(txtConfirmPassword, imgconfirmpassword)
        })
        imgNewPassword.setOnClickListener({
            passwordHideandShow(txtNewPassword, imgNewPassword)
        })
        imgOldPassword.setOnClickListener({
            passwordHideandShow(txtOldPassword, imgOldPassword)
        })
        btnSubmit.setOnClickListener(View.OnClickListener {
            OldPassword = txtOldPassword.text.toString()
            NewPassword = txtNewPassword.text.toString()
            var confirmpassword = txtConfirmPassword.text.toString()
            if (OldPassword!!.isNullOrEmpty()) {
                CommonUtil.ApiAlert(this, getString(R.string.lbl_enter_oldpassword))
            } else if (NewPassword!!.isNullOrEmpty()) {
                CommonUtil.ApiAlert(this, getString(R.string.lbl_enter_new_password))
            } else if (confirmpassword.isEmpty()) {
                CommonUtil.ApiAlert(this, getString(R.string.lbl_confim_password))
            } else if (OldPassword == NewPassword) {
                CommonUtil.ApiAlert(this, getString(R.string.lbl_similar_password))
            } else if (NewPassword == confirmpassword) {

                ChangePasswordRequest(activity)
            } else {
                CommonUtil.ApiAlert(this, getString(R.string.lbl_pswrd_not_match))
            }


        })

        changePassword =
            PopupWindow(view, ListPopupWindow.MATCH_PARENT, ListPopupWindow.MATCH_PARENT, true)
        changePassword!!.contentView = view
        changePassword!!.isOutsideTouchable = false
        changePassword!!.showAtLocation(view, Gravity.CENTER or Gravity.TOP, 0, 0)
    }

    private fun passwordHideandShow(txtPassword: EditText?, imgEye: ImageView?) {
        if (passwordvisible == true) {
            txtPassword?.transformationMethod = HideReturnsTransformationMethod.getInstance()
            txtPassword?.setSelection(txtPassword?.text!!.length)
            passwordvisible = false
            imgEye?.setImageResource(R.drawable.ic_lock)
        } else {
            txtPassword?.transformationMethod = PasswordTransformationMethod.getInstance()
            txtPassword?.setSelection(txtPassword?.text!!.length)
            passwordvisible = true
            imgEye?.setImageResource(R.drawable.ic_lock_open)

        }
    }

    fun GetMenuCountRespone() {

    }

    override fun onBackPressed() {
        CommonUtil.OnBackSetBottomMenuClickTrue()
        super.onBackPressed()
    }

    private fun IntentToChangeRole(activity: Activity) {
        val i = Intent(activity, LoginRoles::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        activity.startActivity(i)
    }

    fun MenuBottomType() {
        Log.d("testMenubottom", "log")
        if (CommonUtil.Priority == "p1") {
            layoutbottomCurve!!.setBackgroundResource(R.drawable.img_prinicipal_bottom_card)
        } else if (CommonUtil.Priority == "p2" || CommonUtil.Priority == "p3") {
            layoutbottomCurve!!.setBackgroundResource(R.drawable.img_staff_bottom_card)
        } else if (CommonUtil.Priority == "p4") {
            layoutbottomCurve!!.setBackgroundResource(R.drawable.img_student_bottom_card)
        } else if (CommonUtil.Priority == "p5") {
            layoutbottomCurve!!.setBackgroundResource(R.drawable.img_parent_bottom_card)
        } else if (CommonUtil.Priority == "p6") {
            layoutbottomCurve!!.setBackgroundResource(R.drawable.img_student_bottom_card)
        }
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet!!)
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == 3) {
                    img_swipe!!.setImageResource(R.drawable.ic_arrowdown_white)

                } else if (newState == 4) {
                    img_swipe!!.setImageResource(R.drawable.ic_arrowup_white)
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

    }

    fun bottomsheetStateCollpased() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            Log.d("expanded", "expanded");
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

    }

    fun bottomsheetStateHidden() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            Log.d("hidden", "hidden");
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }

    }

    fun ClearCache(activity: Activity) {
        try {
            val dir = activity.cacheDir
            if (deleteDir(dir)) {
                AlertOk(activity, "Cache cleared", true)
            } else {
                AlertOk(activity, "Cache not cleared", true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun AlertOk(activity: Activity, Msg: String, value: Boolean) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Clear Cache")
        builder.setMessage(Msg)
        builder.setCancelable(false)
        builder.setPositiveButton("OK") { dialog, which ->
            if (value) {
                profilePopup!!.dismiss()
            }
        }
        builder.create().show()
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun LoadWebView(activity: Activity?, url: String?, Type: Int) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(R.layout.activity_terms_condition, null)
        popupWebview = PopupWindow(
            layout,
            android.app.ActionBar.LayoutParams.MATCH_PARENT,
            android.app.ActionBar.LayoutParams.MATCH_PARENT,
            true
        )
        popupWebview!!.contentView = layout
        popupWebview!!.showAtLocation(layout, Gravity.CENTER, 0, 0)
        val webview = layout.findViewById<WebView>(R.id.webview)
        val lblMenuHeaderName = layout.findViewById<TextView>(R.id.lblMenuHeaderName)
        val imgBack = layout.findViewById<ImageView>(R.id.imgBack)
        val btnTerms = layout.findViewById<Button>(R.id.btnTermsAndCondition)
        val LayoutHeader = layout.findViewById<ConstraintLayout>(R.id.LayoutHeader)
        val viewLine = layout.findViewById<View>(R.id.viewline)
        btnTerms.visibility = View.GONE
        LayoutHeader.visibility = View.VISIBLE
        viewLine.visibility = View.VISIBLE
        if (Type == 0) {
            lblMenuHeaderName.setText(R.string.txt_faq)
        } else if (Type == 1) {
            lblMenuHeaderName.setText(R.string.txt_help)
        } else if (Type == 2) {
            lblMenuHeaderName.setText(R.string.txt_privacy_policy)
        } else if (Type == 3) {
            lblMenuHeaderName.setText(R.string.txt_terms_amp_condition)
        }
        imgBack.setOnClickListener { popupWebview!!.dismiss() }
        val progressDialog = CustomLoading.createProgressDialog(activity)
        webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                progressDialog.show()
                setProgress(progress * 100)
                if (progress == 100) {
                    progressDialog.dismiss()
                }
            }
        }
        webview.webViewClient = MyWebViewClient(activity!!)
        webview.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        val webSettings = webview.settings
        webSettings.loadsImagesAutomatically = true
        webSettings.builtInZoomControls = true
        webSettings.javaScriptEnabled = true
        webview.loadUrl(url!!)
        progressDialog.dismiss()
    }


    class GridSpacingItemDecoration(private val spanCount: Int, includeEdge: Boolean) :
        ItemDecoration() {
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

    override fun onResume() {
        bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
        super.onResume()
    }

    companion object {
        var profilePopup: PopupWindow? = null
        var popupWebview: PopupWindow? = null
        var changePassword: PopupWindow? = null
        var dashboardViewModel: Dashboards? = null
        var appviewModelbase: App? = null
        var imgRefresh: ImageView? = null
        var imgNotification: ImageView? = null
        var OldPassword: String? = null
        var NewPassword: String? = null


        var NoticeboardMenuID = "0"
        var CircularMenuID = "0"
        var CommunicationMenuID = "0"
        var EventsMenuID = "0"
        var AssignmentMenuID = "0"
        var DashboardHomeMenuID = "0"
        var ExamMenuID = "0"
        var AttendanceMeuID = "0"
        var FacultyMenuID = "0"
        var VideoMenuID = "0"
        var ChatMenuID = "0"
        var CourseDetailsMenuID = "0"
        var CategoryDetailsMenuID = "0"
        var SemesterCreditMenuID = "0"
        var ExamApplicationMenuID = "0"


        private var passwordvisible = true


        //outside touch to close bottomsheet
        //    @Override
        //    public boolean dispatchTouchEvent(MotionEvent event) {
        //        if (event.getAction() == MotionEvent.ACTION_DOWN) {
        //            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
        //                Log.d("expanded","expanded");
        //
        //                Rect outRect = new Rect();
        ////                linearLayout.getGlobalVisibleRect(outRect);
        //                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY()))
        //                    Log.d("testcolapse","collapse");
        //                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        //            }
        //        }
        //
        //        return super.dispatchTouchEvent(event);
        //    }
        fun LogoutAlert(title: String?, value: Int, activity: Activity) {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(title)
            builder.setCancelable(false)
            builder.setPositiveButton("Yes") { dialog, which ->
                if (value == 1) {
                    profilePopup!!.dismiss()
                }
                SharedPreference.clearShLogin(activity)
                CommonUtil.Priority = ""
                CommonUtil.MemberId = 0
                CommonUtil.MemberName = ""
                CommonUtil.MemberType = ""
                CommonUtil.CollegeLogo = ""
                CommonUtil.CollegeId = 0
                CommonUtil.DivisionId = ""
                CommonUtil.Courseid = ""
                CommonUtil.DepartmentId = ""
                CommonUtil.YearId = ""
                CommonUtil.SemesterId = ""
                CommonUtil.SectionId = ""
                CommonUtil.MobileNUmber = ""
                CommonUtil.isParentEnable = ""
                val i = Intent(activity, Login::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                activity.startActivity(i)
                activity.finish()
            }
            builder.setNegativeButton("No") { dialog, which -> builder.setCancelable(false) }
            builder.create().show()
        }

        fun LoadWebViewContext(activity: Context?, url: String?) {
            val inflater = activity!!.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater.inflate(R.layout.activity_terms_condition, null)
            popupWebview = PopupWindow(
                layout,
                android.app.ActionBar.LayoutParams.MATCH_PARENT,
                android.app.ActionBar.LayoutParams.MATCH_PARENT,
                true
            )
            popupWebview!!.contentView = layout
            popupWebview!!.showAtLocation(layout, Gravity.CENTER, 0, 0)
            val webview = layout.findViewById<WebView>(R.id.webview)
            val lblMenuHeaderName = layout.findViewById<TextView>(R.id.lblMenuHeaderName)
            val imgBack = layout.findViewById<ImageView>(R.id.imgBack)
            val btnTerms = layout.findViewById<Button>(R.id.btnTermsAndCondition)
            val LayoutHeader = layout.findViewById<ConstraintLayout>(R.id.LayoutHeader)
            val viewLine = layout.findViewById<View>(R.id.viewline)
            btnTerms.visibility = View.GONE
            LayoutHeader.visibility = View.VISIBLE
            viewLine.visibility = View.VISIBLE
            lblMenuHeaderName.setText(R.string.txt_ad)

            imgBack.setOnClickListener { popupWebview!!.dismiss() }
            val progressDialog = CustomLoading.createProgressDialog(activity)

            webview.webViewClient = MyWebViewClientContext(activity!!)
            webview.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            val webSettings = webview.settings
            webSettings.loadsImagesAutomatically = true
            webSettings.builtInZoomControls = true
            webSettings.javaScriptEnabled = true
            webview.loadUrl(url!!)
            progressDialog.dismiss()
        }


        fun deleteDir(dir: File?): Boolean {
            return if (dir != null && dir.isDirectory) {
                val children = dir.list()
                for (i in children.indices) {
                    val success = deleteDir(File(dir, children[i]))
                    if (!success) {
                        return false
                    }
                }
                dir.delete()
            } else if (dir != null && dir.isFile) {
                dir.delete()
            } else {
                false
            }
        }

        fun ChangePasswordRequest(activity: Activity?) {
            var mobilenumber = SharedPreference.getSH_MobileNumber(activity!!)
            val jsonObject = JsonObject()
            jsonObject.addProperty(ApiRequestNames.Req_mobileNumber, mobilenumber)
            jsonObject.addProperty(ApiRequestNames.Req_oldpassword, OldPassword)
            jsonObject.addProperty(ApiRequestNames.Req_newpassword, NewPassword)
            appviewModelbase!!.getChangePassword(jsonObject, activity)
            Log.d("ChangePasswordRequest", jsonObject.toString())
        }

        fun UserMenuRequest(activity: Activity?) {
            val countryid = SharedPreference.getCountryId(activity!!)
            val jsonObject = JsonObject()
            jsonObject.addProperty(ApiRequestNames.Req_collegeid, CommonUtil.CollegeId)
            jsonObject.addProperty(ApiRequestNames.Req_countryid, countryid)
            jsonObject.addProperty(ApiRequestNames.Req_langid, "1")
            jsonObject.addProperty(ApiRequestNames.Req_userid, CommonUtil.MemberId)
            dashboardViewModel!!.getUsermenus(jsonObject, activity)
            Log.d("UserMenus_Request", jsonObject.toString())
        }

        fun AppReadStatus(activity: Activity?, msgtype: String, detailsId: String) {
            val jsonObject = JsonObject()
            jsonObject.addProperty(ApiRequestNames.Req_userid, CommonUtil.MemberId)
            jsonObject.addProperty(ApiRequestNames.Req_msgtype, msgtype)
            jsonObject.addProperty(ApiRequestNames.Req_detailsid, detailsId)
            jsonObject.addProperty(ApiRequestNames.Req_priority, CommonUtil.Priority)
            appviewModelbase!!.getAppreadStatus(jsonObject, activity)
            Log.d("AppReadStatus", jsonObject.toString())
        }

        fun AppReadStatusContext(activity: Context?, msgtype: String, detailsId: String) {
            val jsonObject = JsonObject()
            jsonObject.addProperty(ApiRequestNames.Req_userid, CommonUtil.MemberId)
            jsonObject.addProperty(ApiRequestNames.Req_msgtype, msgtype)
            jsonObject.addProperty(ApiRequestNames.Req_detailsid, detailsId)
            jsonObject.addProperty(ApiRequestNames.Req_priority, CommonUtil.Priority)
            appviewModelbase!!.getAppreadStatusContext(jsonObject, activity)
            Log.d("AppReadStatus", jsonObject.toString())
        }

        fun OverAllMenuCountRequest(activity: Activity?, menuid: String) {

            val jsonObject = JsonObject()
            jsonObject.addProperty(ApiRequestNames.Req_userid, CommonUtil.MemberId)
            jsonObject.addProperty(ApiRequestNames.Req_menuid, menuid)
            jsonObject.addProperty(ApiRequestNames.Req_collegeid, CommonUtil.CollegeId)
            if (CommonUtil.Priority == "p1") {
                jsonObject.addProperty(ApiRequestNames.Req_departmentid, 0)
                jsonObject.addProperty(ApiRequestNames.Req_sectionid, 0)
            } else {
                jsonObject.addProperty(ApiRequestNames.Req_departmentid, CommonUtil.DepartmentId)
                jsonObject.addProperty(ApiRequestNames.Req_sectionid, CommonUtil.SectionId)
            }
            if (CommonUtil.Priority == "p1" || CommonUtil.Priority == "p2" || CommonUtil.Priority == "p3") {
                jsonObject.addProperty(ApiRequestNames.Req_appid, CommonUtil.SenderAppId)
            } else {
                jsonObject.addProperty(ApiRequestNames.Req_appid, CommonUtil.ReceiverAppId)

            }
            jsonObject.addProperty(ApiRequestNames.Req_priority, CommonUtil.Priority)
            appviewModelbase!!.getOverAllMenuCount(jsonObject, activity)
            Log.d("OverAllMenuCount_Req:", jsonObject.toString())

        }

    }


    fun TabCollegeColor() {
        if (CommonUtil.Priority == "p1") {
            LayoutCollege!!.setBackgroundColor(Color.parseColor(resources.getString(R.string.clr_prinicpal_selected)))
            LayoutDepartment!!.setBackgroundColor(Color.parseColor(resources.getString(R.string.clr_principal_unselected)))
            imgAddPlus!!.visibility = View.VISIBLE
        } else if ((CommonUtil.Priority == "p2") || CommonUtil.Priority.equals("p3")) {
            LayoutCollege!!.setBackgroundColor(Color.parseColor(resources.getString(R.string.clr_teachingstaff_selected)))
            LayoutDepartment!!.setBackgroundColor(Color.parseColor(resources.getString(R.string.clr_teachingstaff_unselected)))
            imgAddPlus!!.visibility = View.GONE
        } else if ((CommonUtil.Priority == "p4")) {
            LayoutCollege!!.setBackgroundColor(Color.parseColor(resources.getString(R.string.clr_student_selectedTab)))
            LayoutDepartment!!.setBackgroundColor(Color.parseColor(resources.getString(R.string.clr_student_unselectedTab)))
            imgAddPlus!!.visibility = View.GONE
        } else if ((CommonUtil.Priority == "p5")||(CommonUtil.Priority.equals("p6"))) {
            LayoutCollege!!.setBackgroundColor(Color.parseColor(resources.getString(R.string.clr_parent_selected)))
            LayoutDepartment!!.setBackgroundColor(Color.parseColor(resources.getString(R.string.clr_parent_unselected)))
            imgAddPlus!!.visibility = View.GONE

        }
    }

    fun TabDepartmentColor() {
        if (CommonUtil.Priority == "p1") {
            LayoutDepartment!!.setBackgroundColor(Color.parseColor((resources.getString(R.string.clr_prinicpal_selected))))
            LayoutCollege!!.setBackgroundColor(Color.parseColor((resources.getString(R.string.clr_principal_unselected))))
            imgAddPlus!!.visibility = View.VISIBLE
        } else if ((CommonUtil.Priority == "p2") || CommonUtil.Priority.equals("p3")) {
            LayoutDepartment!!.setBackgroundColor(Color.parseColor((resources.getString(R.string.clr_teachingstaff_selected))))
            LayoutCollege!!.setBackgroundColor(Color.parseColor((resources.getString(R.string.clr_teachingstaff_unselected))))
            imgAddPlus!!.visibility = View.VISIBLE
        } else if ((CommonUtil.Priority == "p4")) {
            LayoutDepartment!!.setBackgroundColor(Color.parseColor(resources.getString(R.string.clr_student_selectedTab)))
            LayoutCollege!!.setBackgroundColor(Color.parseColor(resources.getString(R.string.clr_student_unselectedTab)))
            imgAddPlus!!.visibility = View.GONE
        } else if ((CommonUtil.Priority.equals("p5")) || (CommonUtil.Priority.equals("p6"))) {
            LayoutDepartment!!.setBackgroundColor(Color.parseColor(resources.getString(R.string.clr_parent_selected)))
            LayoutCollege!!.setBackgroundColor(Color.parseColor(resources.getString(R.string.clr_parent_unselected)))
            imgAddPlus!!.visibility = View.GONE
        }
    }
}