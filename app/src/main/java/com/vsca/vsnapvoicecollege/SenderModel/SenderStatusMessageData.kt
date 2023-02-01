package com.vsca.vsnapvoicecollege.SenderModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SenderStatusMessageData {

    @SerializedName("Status")
    @Expose
    var Status: String? = null

    @SerializedName("Message")
    @Expose
    var Message: String? = null

    @SerializedName("data")
    @Expose
    var data: String? = null
}