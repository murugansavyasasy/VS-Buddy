package com.vsca.vsnapvoicecollege.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class CountryDetailsResponse {
    @SerializedName("Status")
    @Expose
    var status = 0

    @SerializedName("Message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    var data: List<CountryDetails>? = null
}