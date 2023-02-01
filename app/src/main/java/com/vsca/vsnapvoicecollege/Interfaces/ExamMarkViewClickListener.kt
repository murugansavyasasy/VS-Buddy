package com.vsca.vsnapvoicecollege.Interfaces

import com.vsca.vsnapvoicecollege.Adapters.ExamListAdapter
import com.vsca.vsnapvoicecollege.Model.GetExamListDetails

interface ExamMarkViewClickListener {
    fun onExamClickListener(holder: ExamListAdapter.MyViewHolder, item: GetExamListDetails)
}