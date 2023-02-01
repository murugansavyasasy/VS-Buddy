package com.vsca.vsnapvoicecollege.SenderModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetDivisionData {

    @SerializedName("division_code")
    @Expose
    var division_code: String? = null

    @SerializedName("division_name")
    @Expose
    var division_name: String? = null

    @SerializedName("division_id")
    @Expose
    var division_id: String? = null

}