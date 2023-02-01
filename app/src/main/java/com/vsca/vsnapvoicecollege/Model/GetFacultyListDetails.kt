package com.vsca.vsnapvoicecollege.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetFacultyListDetails {

    @SerializedName("memberid")
    @Expose
    var memberid: String? = null

    @SerializedName("staffname")
    @Expose
    var staffname: String? = null

    @SerializedName("subjectname")
    @Expose
    var subjectname: String? = null

    @SerializedName("subjectcode")
    @Expose
    var subjectcode: String? = null

    @SerializedName("stafftype")
    @Expose
    var stafftype: String? = null

    @SerializedName("facultyphoto")
    @Expose
    var facultyphoto: String? = null
}