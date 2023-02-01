package com.vsca.vsnapvoicecollege.SenderModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetCourseData {

    @SerializedName("course_code")
    @Expose
    var course_code: String? = null

    @SerializedName("course_name")
    @Expose
    var course_name: String? = null

    @SerializedName("course_id")
    @Expose
    var course_id: String? = null

}