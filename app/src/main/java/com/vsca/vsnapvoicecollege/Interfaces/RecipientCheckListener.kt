package com.vsca.vsnapvoicecollege.Interfaces

import com.vsca.vsnapvoicecollege.SenderModel.RecipientSelected


interface RecipientCheckListener {
    fun add(data: RecipientSelected?)
    fun remove(data: RecipientSelected?)
}