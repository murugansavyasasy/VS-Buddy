package com.vsca.vsnapvoicecollege.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetVideoListDetails {

    @SerializedName("videoid")
    @Expose
    var videoid: String? = null

    @SerializedName("createdby")
    @Expose
    var createdby: String? = null

    @SerializedName("createdon")
    @Expose
    var createdon: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("vimeourl")
    @Expose
    var vimeourl: String? = null

    @SerializedName("vimeoid")
    @Expose
    var vimeoid: String? = null

    @SerializedName("detailid")
    @Expose
    var detailid: String? = null

    @SerializedName("iframe")
    @Expose
    var iframe: String? = null

    @SerializedName("isappviewed")
    @Expose
    var isappviewed: String? = null
}