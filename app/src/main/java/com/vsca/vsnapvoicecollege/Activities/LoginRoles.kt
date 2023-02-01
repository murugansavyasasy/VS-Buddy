package com.vsca.vsnapvoicecollege.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import com.vsca.vsnapvoicecollege.R
import android.os.Bundle
import android.util.Log
import butterknife.ButterKnife
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import butterknife.OnClick
import androidx.recyclerview.widget.RecyclerView
import com.vsca.vsnapvoicecollege.Adapters.LoginChooseRoles
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DefaultItemAnimator

import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Interfaces.LoginRolesListener
import com.vsca.vsnapvoicecollege.Model.LoginDetails
import com.vsca.vsnapvoicecollege.Repository.ApiRequestNames
import com.vsca.vsnapvoicecollege.ViewModel.App


class LoginRoles : AppCompatActivity() {
    @JvmField
    @BindView(R.id.ryclerview)
    var ryclerview: RecyclerView? = null
    var rolesadapter: LoginChooseRoles? = null
    var appviewModel: App? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_roles)
        ButterKnife.bind(this)

        appviewModel = ViewModelProvider(this).get(
            App::class.java
        )
        appviewModel!!.init()

        rolesadapter = CommonUtil.UserDataList?.let {
            LoginChooseRoles(it, this@LoginRoles, object : LoginRolesListener {
                override fun onroleClick(
                    holder: LoginChooseRoles.MyViewHolder,
                    data: LoginDetails
                ) {
                    holder.rytOverAll!!.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(view: View) {
                            SetLoginData(data)
                            val i: Intent = Intent(this@LoginRoles, DashBoard::class.java)
                            startActivity(i)
                        }
                    })
                }
            })
        }
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@LoginRoles)
        ryclerview!!.layoutManager = mLayoutManager
        ryclerview!!.itemAnimator = DefaultItemAnimator()
        ryclerview!!.adapter = rolesadapter
        ryclerview!!.recycledViewPool.setMaxRecycledViews(0, 80)
        rolesadapter!!.notifyDataSetChanged()
    }


    private fun SetLoginData(data: LoginDetails) {
        CommonUtil.Priority = data.priority!!
        CommonUtil.MemberId = data.memberid
        CommonUtil.MemberName = data.membername!!
        CommonUtil.MemberType = data.loginas!!
        CommonUtil.CollegeId = data.colgid
        CommonUtil.DivisionId = data.divisionId!!
        CommonUtil.Courseid = data.courseid!!
        CommonUtil.DepartmentId = data.deptid!!
        CommonUtil.YearId = data.yearid!!
        CommonUtil.SemesterId = data.semesterid!!
        CommonUtil.SectionId = data.sectionid!!
        CommonUtil.isParentEnable = data.is_parent_target_enabled!!
        CommonUtil.CollegeLogo = data.colglogo!!
    }

    @OnClick(R.id.btnLogout)
    fun logoutClick() {
        BaseActivity.LogoutAlert(getString(R.string.txt_logout_alert), 0, this@LoginRoles)
    }
}