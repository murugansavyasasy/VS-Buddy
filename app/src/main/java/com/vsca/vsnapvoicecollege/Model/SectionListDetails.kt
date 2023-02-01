package com.vsca.vsnapvoicecollege.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SectionListDetails {
    @SerializedName("sectionid")
    @Expose
    var sectionid: String? = null

    @SerializedName("sectionname")
    @Expose
    var sectionname: String? = null

}