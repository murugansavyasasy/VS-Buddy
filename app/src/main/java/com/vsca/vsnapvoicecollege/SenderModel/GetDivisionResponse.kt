package com.vsca.vsnapvoicecollege.SenderModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetDivisionResponse {

    @SerializedName("Status")
    @Expose
    var status = 0

    @SerializedName("Message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    var data: ArrayList<GetDivisionData>? = null
}