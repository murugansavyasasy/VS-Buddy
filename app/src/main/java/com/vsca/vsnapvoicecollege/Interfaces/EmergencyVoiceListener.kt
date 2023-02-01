package com.vsca.vsnapvoicecollege.Interfaces

import com.vsca.vsnapvoicecollege.Adapters.DashboardChild
import com.vsca.vsnapvoicecollege.Model.DashboardSubItems


interface EmergencyVoiceListener {

    fun onImagePlaypauseClick(holder: DashboardChild.ViewHolder, item: DashboardSubItems)

}