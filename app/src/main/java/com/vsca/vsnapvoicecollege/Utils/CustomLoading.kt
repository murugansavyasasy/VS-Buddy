package com.vsca.vsnapvoicecollege.Utils

import android.content.SharedPreferences
import com.vsca.vsnapvoicecollege.Utils.SharedPreference
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.webkit.WebViewClient
import com.vsca.vsnapvoicecollege.Utils.MyWebViewClient
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.WindowManager
import android.graphics.drawable.ColorDrawable
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Model.LoginDetails
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import com.vsca.vsnapvoicecollege.Utils.CustomGestureDetector

object CustomLoading {
    fun createProgressDialog(context: Context?): ProgressDialog {
        val dialog = ProgressDialog(context)
        try {
            dialog.show()
        } catch (e: WindowManager.BadTokenException) {
        }
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_loading)
        // dialog.setMessage(Message);
        return dialog
    }
}