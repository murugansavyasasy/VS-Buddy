package com.vsca.vsnapvoicecollege.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetSemesterWiseDetails {
    @SerializedName("semester_id")
    @Expose
    var semester_id: String? = null

    @SerializedName("semseter_name")
    @Expose
    var semseter_name: String? = null

    @SerializedName("semester_no")
    @Expose
    var semester_no: String? = null

}