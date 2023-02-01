package com.vsca.vsnapvoicecollege.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SemesterSectionListDetails {

    @SerializedName("clgsemesterid")
    @Expose
    var clgsemesterid: String? = null

    @SerializedName("semestername")
    @Expose
    var semestername: String? = null

    @SerializedName("sectiondetails")
    var data: List<SectionListDetails>? = null
}