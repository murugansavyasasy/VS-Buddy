package com.vsca.vsnapvoicecollege.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class MenuDetailsResponse {
    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("order")
    @Expose
    var order = 0

    constructor(ID: Int?, Name: String?) {
        this.id = ID!!
        this.name = Name
    }
}