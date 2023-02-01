package com.vsca.vsnapvoicecollege.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class StatusMessageResponse {
    @SerializedName("Status")
    @Expose
    var status = 0

    @SerializedName("Message")
    @Expose
    var message: String? = null
}