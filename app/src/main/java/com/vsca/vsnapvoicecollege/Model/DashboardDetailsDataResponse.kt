package com.vsca.vsnapvoicecollege.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose


class DashboardDetailsDataResponse {
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

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("idfiledetails")
    @Expose
    var idfiledetails: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("voicefilepath")
    @Expose
    var voicefilepath: String? = null

    @SerializedName("detailsid")
    @Expose
    var detailsid: String? = null

    @SerializedName("membername")
    @Expose
    var membername: String? = null

    @SerializedName("createdon")
    @Expose
    var createdon: String? = null

    @SerializedName("filepaths")
    @Expose
    var filepaths: List<String>? = null


    @SerializedName("createddate")
    @Expose
    var createddate: String? = null

    @SerializedName("createdtime")
    @Expose
    var createdtime: String? = null

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("typ")
    @Expose
    var typ: String? = null

    @SerializedName("sentbyname")
    @Expose
    var sentbyname: String? = null

    @SerializedName("createdondate")
    @Expose
    var createdondate: String? = null

    @SerializedName("createdontime")
    @Expose
    var createdontime: String? = null

    @SerializedName("content")
    @Expose
    var content: String? = null

    @SerializedName("duration")
    @Expose
    var duration = 0

    @SerializedName("idnoticeboarddetails")
    @Expose
    var idnoticeboarddetails: String? = null

    @SerializedName("topicheading")
    @Expose
    var topicheading: String? = null

    @SerializedName("topicbody")
    @Expose
    var topicbody: String? = null

    @SerializedName("subjectname")
    @Expose
    var subjectname: String? = null

    @SerializedName("attendancetype")
    @Expose
    var attendancetype: String? = null

    @SerializedName("attendancedate")
    @Expose
    var attendancedate: String? = null

}