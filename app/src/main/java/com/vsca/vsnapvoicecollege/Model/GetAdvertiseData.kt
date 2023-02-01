package com.vsca.vsnapvoicecollege.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetAdvertiseData {

    @SerializedName("add_id")
    @Expose
    var add_id: String? = null

    @SerializedName("add_title")
    @Expose
    var add_title: String? = null

    @SerializedName("add_content")
    @Expose
    var add_content: String? = null

    @SerializedName("company")
    @Expose
    var company: String? = null

    @SerializedName("background_image")
    @Expose
    var background_image: String? = null

    @SerializedName("add_image")
    @Expose
    var add_image: String? = null


    @SerializedName("add_url")
    @Expose
    var add_url: String? = null

    @SerializedName("video_url")
    @Expose
    var video_url: String? = null

}