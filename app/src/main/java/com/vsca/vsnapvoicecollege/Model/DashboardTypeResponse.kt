package com.vsca.vsnapvoicecollege.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose


class DashboardTypeResponse {
    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("order")
    @Expose
    var order = 0

    @SerializedName("data")
    var data: List<DashboardDetailsDataResponse>? = null
}