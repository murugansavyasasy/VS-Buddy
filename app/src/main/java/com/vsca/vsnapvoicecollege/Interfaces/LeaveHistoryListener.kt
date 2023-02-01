package com.vsca.vsnapvoicecollege.Interfaces

import com.vsca.vsnapvoicecollege.Adapters.LeaveHistoryAdapter
import com.vsca.vsnapvoicecollege.Model.LeaveHistoryData

interface LeaveHistoryListener {
    fun onLeaveClick(holder: LeaveHistoryAdapter.MyViewHolder, item: LeaveHistoryData)
}