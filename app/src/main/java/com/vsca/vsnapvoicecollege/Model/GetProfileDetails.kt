package com.vsca.vsnapvoicecollege.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose


class GetProfileDetails {
    @SerializedName("key")
    @Expose
    var key: String? = null

    @SerializedName("value")
    @Expose
    var value: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

}