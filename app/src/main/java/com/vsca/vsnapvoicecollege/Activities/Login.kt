package com.vsca.vsnapvoicecollege.Activities


import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Model.DashboardSubItems
import com.vsca.vsnapvoicecollege.Model.LoginDetails
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Repository.ApiRequestNames
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import com.vsca.vsnapvoicecollege.Utils.CommonUtil.Priority
import com.vsca.vsnapvoicecollege.Utils.SharedPreference
import com.vsca.vsnapvoicecollege.ViewModel.Auth

class Login : AppCompatActivity() {
    @JvmField
    @BindView(R.id.lblforgotPassword)
    var lblforgotPassword: TextView? = null

    @JvmField
    @BindView(R.id.edMobilenumber)
    var txtMobilenumber: EditText? = null

    @JvmField
    @BindView(R.id.edPassword)
    var txtPassword: EditText? = null

    @JvmField
    @BindView(R.id.btnLogin)
    var btnlogin: Button? = null

    @JvmField
    @BindView(R.id.imgpasswordlock)
    var imgpasswordlock: ImageView? = null
    var MobileNumber: String? = null
    var Password: String? = null
    var authViewModel: Auth? = null
    var LoginData: List<LoginDetails> = ArrayList()
    private var passwordvisible = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)
        authViewModel = ViewModelProvider(this).get(Auth::class.java)
        authViewModel!!.init()

        authViewModel!!.loginResposneLiveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message
                if (status == 1) {
                    LoginData = response.data!!
                    if (LoginData.size != 0) {
                        CommonUtil.UserDataList = response.data
                        SharedPreference.putLoginDetails(this@Login, MobileNumber, Password)
                        SetLoginData(LoginData)

                        Log.d("LoginDataSize",LoginData.size.toString())
                        if (LoginData.size > 1) {
                            val i = Intent(this@Login, LoginRoles::class.java)
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(i)
                            finishAffinity()
                        } else {
                            val i = Intent(this@Login, DashBoard::class.java)
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(i)
                            finishAffinity()
                        }
                    } else {
                        CommonUtil.ApiAlert(this@Login, message)
                    }
                } else {
                    CommonUtil.ApiAlert(this@Login, message)
                }
            } else {
                CommonUtil.ApiAlert(this@Login, "Something went wrong")

            }
        }
    }

    @OnClick(R.id.btnLogin)
    fun LoginbtnClick(view: View?) {
        MobileNumber = txtMobilenumber!!.text.toString()
        Password = txtPassword!!.text.toString()

        Log.d("Mobilenumber", MobileNumber!!)
        if (MobileNumber != "" && Password != "") {
            CommonUtil.MobileNUmber = MobileNumber!!
            val jsonObject = JsonObject()
            jsonObject.addProperty(ApiRequestNames.Req_mobileNumber, MobileNumber)
            jsonObject.addProperty(ApiRequestNames.Req_password, Password)
            authViewModel!!.login(jsonObject, this@Login)
        } else {
            CommonUtil.ApiAlert(this@Login, getString(R.string.txt_login_details))
        }
    }

    @OnClick(R.id.imgpasswordlock)
    fun imgpasswordlockClick() {
        if (passwordvisible) {
            txtPassword!!.transformationMethod = PasswordTransformationMethod.getInstance()
            imgpasswordlock!!.setImageResource(R.drawable.ic_lock)
            passwordvisible = false
        } else {
            txtPassword!!.transformationMethod = null
            passwordvisible = true
            txtPassword!!.setSelection(txtPassword!!.text.length)
            imgpasswordlock!!.setImageResource(R.drawable.ic_lock_open)
        }
    }

    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()
    }

    private fun SetLoginData(data: List<LoginDetails>) {

        for (i in data.indices) {
            CommonUtil.Priority = data.get(i).priority!!
            CommonUtil.MemberId = data.get(i).memberid
            CommonUtil.MemberName = data.get(i).membername!!
            CommonUtil.MemberType = data.get(i).loginas!!
            CommonUtil.CollegeId = data.get(i).colgid
            CommonUtil.DivisionId = data.get(i).divisionId!!
            CommonUtil.Courseid = data.get(i).courseid!!
            CommonUtil.DepartmentId = data.get(i).deptid!!
            CommonUtil.YearId = data.get(i).yearid!!
            CommonUtil.SemesterId = data.get(i).semesterid!!
            CommonUtil.SectionId = data.get(i).sectionid!!
            CommonUtil.isParentEnable = data.get(i).is_parent_target_enabled!!
            CommonUtil.CollegeLogo = data.get(i).colglogo!!
        }
    }
}