package com.vsca.vsnapvoicecollege.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AttendanceData {

    @SerializedName("subjectname")
    @Expose
    var subjectname: String? = null

    @SerializedName("attendancedate")
    @Expose
    var attendancedate: String? = null

    @SerializedName("attendancetype")
    @Expose
    var attendancetype: String? = null

}