package com.vsca.vsnapvoicecollege.Adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.vsca.vsnapvoicecollege.R
import com.bumptech.glide.Glide
import androidx.constraintlayout.widget.ConstraintLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.vsca.vsnapvoicecollege.Model.GetCourseDetailsData
import com.vsca.vsnapvoicecollege.Model.GetExamApplicationDetails
import com.vsca.vsnapvoicecollege.Model.GetProfileDetails
import com.vsca.vsnapvoicecollege.Activities.CourseDetails
import com.bumptech.glide.load.engine.DiskCacheStrategy
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.*
import java.util.ArrayList

class CourseDetailsAdapter : RecyclerView.Adapter<CourseDetailsAdapter.MyViewHolder> {
    var coursedetails: List<GetCourseDetailsData> = ArrayList()
    var studentmenuExamdetails: List<GetExamApplicationDetails> = ArrayList()
    var profileData: List<GetProfileDetails> = ArrayList()
    var context: Context
    var Position: Int = 0
    var Type: Int

    constructor(data: List<GetExamApplicationDetails>, context: Context, type: Int) {
        studentmenuExamdetails = data
        this.context = context
        Type = type
    }

    constructor(
        getCourseDetailsData: List<GetCourseDetailsData>,
        context: CourseDetails,
        type: Int
    ) {
        coursedetails = getCourseDetailsData
        this.context = context
        Type = type
    }

    constructor(context: Context, getProfileData: List<GetProfileDetails>, type: Int) {
        this.context = context
        profileData = getProfileData
        Type = type
    }

    public override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.activity_coursedetails_list_design, parent, false)
        return MyViewHolder(itemView)
    }

    public override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Position = holder.getAbsoluteAdapterPosition()

        if (Type == 0) {
            if (position % 2 == 0) {
                holder.ViewSide!!.setBackgroundColor(Color.parseColor("#f3ca3a"))
            } else {
                holder.ViewSide!!.setBackgroundColor(Color.parseColor("#5daf98"))
            }
            val data: GetCourseDetailsData = coursedetails.get(position)
            holder.lblSubjectName!!.setText(data.subjectname)
            holder.lblSubjectType!!.setText(data.subjectType)
            holder.lblSubjectCategory!!.setText(data.subjectCategory)
            holder.lblSubjectCredits!!.setText(data.subjectCredits)
            holder.lblSubjectCode!!.setText(data.subjectcode)
            holder.lblSubjectRequirement!!.setText(data.subjectrequirement)
            holder.LayoutProfileOverall!!.setVisibility(View.GONE)
            holder.LayoutCourseAndExam!!.setVisibility(View.VISIBLE)
        } else if (Type == 1) {
            if (position % 2 == 0) {
                holder.ViewSide!!.setBackgroundColor(Color.parseColor("#f3ca3a"))
            } else {
                holder.ViewSide!!.setBackgroundColor(Color.parseColor("#5daf98"))
            }
            val data: GetExamApplicationDetails = studentmenuExamdetails.get(position)
            holder.lblSubjectName!!.setText(data.subjectName)
            holder.lblSubjectCode!!.setText(data.subjectcode)
            holder.LayoutFeeeAmount!!.setVisibility(View.VISIBLE)
            holder.LayoutSemNumber!!.setVisibility(View.VISIBLE)
            holder.lblSemesterName!!.setText(data.semNumber)
            holder.lblFeeAmount!!.setText(data.amount)
            holder.LayoutCategory!!.setVisibility(View.GONE)
            holder.LayoutSubjectType!!.setVisibility(View.GONE)
            holder.LayoutSubjectRequirement!!.setVisibility(View.GONE)
            holder.LayoutSubjectCredits!!.setVisibility(View.GONE)
            holder.LayoutProfileOverall!!.setVisibility(View.GONE)
            holder.LayoutCourseAndExam!!.setVisibility(View.VISIBLE)
        } else if (Type == 2) {
            val data: GetProfileDetails = profileData.get(position)
            holder.LayoutCourseAndExam!!.setVisibility(View.GONE)
            if ((data.type == "text")) {
                if(data.key.isNullOrEmpty()|| data.value.isNullOrEmpty()){
                    holder.LayoutProfileOverall!!.setVisibility(View.GONE)
                    holder.lblProfileKey!!.visibility= View.GONE
                    holder.lblProfileValue!!.visibility= View.GONE
                    holder.imgUser!!.setVisibility(View.GONE)
                    holder.layoutOverAll!!.setVisibility(View.GONE)
                }else{
                    if(data.key.equals("College Name")){
                        holder.LayoutProfileOverall!!.setVisibility(View.GONE)
                        holder.layoutOverAll!!.setVisibility(View.GONE)
                        holder.imgUser!!.setVisibility(View.GONE)
                    }else{
                        holder.layoutOverAll!!.setVisibility(View.VISIBLE)
                        holder.lblProfileKey!!.setText(data.key)
                        holder.lblProfileValue!!.setText(data.value)
                        holder.imgUser!!.setVisibility(View.GONE)
                        holder.LayoutProfileOverall!!.setVisibility(View.VISIBLE)
                    }
                }
            }
            if ((data.type == "img")) {
                if (data.value == null || data.value!!.isEmpty()) {
                    holder.LayoutProfileOverall!!.setVisibility(View.GONE)
                } else {
                    if(data.key.equals("College Logo")){
                        holder.lblProfileKey!!.setText("")
                        holder.lblcolon0!!.setText("")
                        holder.LayoutProfileOverall!!.setVisibility(View.VISIBLE)
                        holder.imgUser!!.setVisibility(View.VISIBLE)
                        Glide.with(context)
                            .load(data.value)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into((holder.imgUser)!!)
                    }else{
                        holder.LayoutProfileOverall!!.setVisibility(View.VISIBLE)
                        holder.imgUser!!.setVisibility(View.VISIBLE)
                        holder.lblProfileKey!!.setText(data.key)
                        Glide.with(context)
                            .load(data.value)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into((holder.imgUser)!!)
                    }

                }
            }
        }
    }

    public override fun getItemCount(): Int {
        if (Type == 0) {
            return coursedetails.size
        } else if (Type == 1) {
            return studentmenuExamdetails.size
        } else if (Type == 2) {
            return profileData.size
        }
        return 0
    }

    inner class MyViewHolder constructor(itemView: View?) : RecyclerView.ViewHolder(
        (itemView)!!
    ) {
        @JvmField
        @BindView(R.id.lblSubjectName)
        var lblSubjectName: TextView? = null

        @JvmField
        @BindView(R.id.lblSemesterName)
        var lblSemesterName: TextView? = null

        @JvmField
        @BindView(R.id.lblFeeAmount)
        var lblFeeAmount: TextView? = null

        @JvmField
        @BindView(R.id.lblSubjectType)
        var lblSubjectType: TextView? = null

        @JvmField
        @BindView(R.id.lblSubjectCategory)
        var lblSubjectCategory: TextView? = null

        @JvmField
        @BindView(R.id.lblSubjectCredits)
        var lblSubjectCredits: TextView? = null

        @JvmField
        @BindView(R.id.lblSubjectCode)
        var lblSubjectCode: TextView? = null

        @JvmField
        @BindView(R.id.lblSubjectRequirement)
        var lblSubjectRequirement: TextView? = null

        @JvmField
        @BindView(R.id.layoutFeeeAmount)
        var LayoutFeeeAmount: ConstraintLayout? = null

        @JvmField
        @BindView(R.id.layoutSemNumber)
        var LayoutSemNumber: ConstraintLayout? = null

        @JvmField
        @BindView(R.id.ViewSide)
        var ViewSide: View? = null

        @JvmField
        @BindView(R.id.layoutCategory)
        var LayoutCategory: ConstraintLayout? = null

        @JvmField
        @BindView(R.id.layoutSubjectType)
        var LayoutSubjectType: ConstraintLayout? = null

        @JvmField
        @BindView(R.id.LayoutSubjectRequirement)
        var LayoutSubjectRequirement: ConstraintLayout? = null

        @JvmField
        @BindView(R.id.layoutSubjectCredits)
        var LayoutSubjectCredits: ConstraintLayout? = null

        @JvmField
        @BindView(R.id.imgUser)
        var imgUser: ImageView? = null

        @JvmField
        @BindView(R.id.LayoutProfileOverall)
        var LayoutProfileOverall: ConstraintLayout? = null

        @JvmField
        @BindView(R.id.LayoutCourseAndExam)
        var LayoutCourseAndExam: ConstraintLayout? = null

        @JvmField
        @BindView(R.id.lblProfileKey)
        var lblProfileKey: TextView? = null

        @JvmField
        @BindView(R.id.lblProfileValue)
        var lblProfileValue: TextView? = null

        @JvmField
        @BindView(R.id.lblcolon0)
        var lblcolon0: TextView? = null

        @JvmField
        @BindView(R.id.layoutOverAll)
        var layoutOverAll: ConstraintLayout? = null

        init {
            ButterKnife.bind(this, (itemView)!!)
        }
    }
}