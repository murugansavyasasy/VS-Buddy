package com.vsca.vsnapvoicecollege.Interfaces

import com.vsca.vsnapvoicecollege.Adapters.HomeMenus
import com.vsca.vsnapvoicecollege.Model.MenuDetailsResponse

interface HomeMenuClickListener {
    fun onMenuClick(holder: HomeMenus.MyViewHolder, data: MenuDetailsResponse)

}