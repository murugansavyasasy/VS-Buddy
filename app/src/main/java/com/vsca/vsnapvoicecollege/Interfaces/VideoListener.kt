package com.vsca.vsnapvoicecollege.Interfaces

import com.vsca.vsnapvoicecollege.Adapters.VideoAdapter
import com.vsca.vsnapvoicecollege.Model.GetVideoListDetails

interface VideoListener {

    fun onVideoClick(holder: VideoAdapter.MyViewHolder, data: GetVideoListDetails)

}