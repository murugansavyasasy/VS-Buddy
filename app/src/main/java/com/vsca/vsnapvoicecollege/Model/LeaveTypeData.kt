package com.vsca.vsnapvoicecollege.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LeaveTypeData {

    @SerializedName("leavetypeid")
    @Expose
    var leavetypeid: String? = null

    @SerializedName("leavetypename")
    @Expose
    var leavetypename: String? = null
}