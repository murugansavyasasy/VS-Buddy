package com.vsca.vsnapvoicecollege.Utils

import android.content.SharedPreferences
import com.vsca.vsnapvoicecollege.Utils.SharedPreference
import android.app.Activity
import android.webkit.WebViewClient
import com.vsca.vsnapvoicecollege.Utils.MyWebViewClient
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.WindowManager
import android.graphics.drawable.ColorDrawable
import android.util.Log
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Model.LoginDetails
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import com.vsca.vsnapvoicecollege.Utils.CustomGestureDetector
import java.lang.Exception

abstract class CustomGestureDetector(private val view: View) : SimpleOnGestureListener() {
    var isSelectionStart = false

    //FOR GESTURE
    override fun onFling(
        motionEventOne: MotionEvent,
        motionEventTwo: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return if (motionEventOne == null || motionEventTwo == null) {
            false
        } else if (motionEventOne.pointerCount > 1 || motionEventTwo.pointerCount > 1) {
            false
        } else {
            if (isSelectionStart) {
                Log.d(TAG, "ME 1 : X - " + motionEventOne.x)
                Log.d(TAG, "ME 1 : Y - " + motionEventOne.y)
                Log.d(TAG, "ME 2 : X - " + motionEventTwo.x)
                Log.d(TAG, "ME 2 : Y - " + motionEventTwo.y)
                Log.d(TAG, "Velocity Of X - $velocityX")
                Log.d(TAG, "Velocity Of Y - $velocityY")
            } else {
                try {
                    ///////////////////////////////////////////////////////////////////////////////
                    Log.d(TAG, "ME 1 : X - " + motionEventOne.x)
                    Log.d(TAG, "ME 1 : Y - " + motionEventOne.y)
                    Log.d(TAG, "ME 2 : X - " + motionEventTwo.x)
                    Log.d(TAG, "ME 2 : Y - " + motionEventTwo.y)
                    Log.d(TAG, "Velocity Of X - $velocityX")
                    Log.d(TAG, "Velocity Of Y - $velocityY")
                    val mRightToLeftCover = motionEventOne.x - motionEventTwo.x
                    val mTopToBottomCover = motionEventTwo.y - motionEventOne.y
                    Log.i(TAG, "mRightToLeftCover : $mRightToLeftCover")
                    Log.i(TAG, "mTopToBottomCover : $mTopToBottomCover")
                    Log.i(TAG, "mVelocityX : $velocityX")
                    Log.i(TAG, "mVelocityY : $velocityY")
                    if (mRightToLeftCover >= 0) {
                        if (mTopToBottomCover >= 0) {
                            if (mTopToBottomCover < 100) {
                                if (mRightToLeftCover > 100) {
                                    Log.d(TAG, "1. R =>> L")
                                    onRightToLeftSwap()
                                }
                            } else {
                                if (mRightToLeftCover < 100) {
                                    Log.d(TAG, "9. T ==>> B")
                                    onTopToBottomSwap()
                                } else {
                                    Log.d(TAG, "2. T ==>> B, R =>> L")
                                }
                            }
                        } else {
                            if (mTopToBottomCover > -100) {
                                if (mRightToLeftCover > 100) {
                                    Log.d(TAG, "3. R =>> L")
                                    onRightToLeftSwap()
                                }
                            } else {
                                if (mRightToLeftCover < 100) {
                                    Log.d(TAG, "10. B ==>> T")
                                    onBottomToTopSwap()
                                } else {
                                    Log.d(TAG, "4. B ==>> T, R =>> L")
                                }
                            }
                        }
                    } else if (mRightToLeftCover < 0) {
                        if (mTopToBottomCover >= 0) {
                            if (mTopToBottomCover < 100) {
                                if (mRightToLeftCover > -100) {
                                    Log.d(TAG, "5. L =>> R")
                                    onLeftToRightSwap()
                                }
                            } else {
                                if (mRightToLeftCover > -100) {
                                    Log.d(TAG, "11. T ==>> B")
                                    onTopToBottomSwap()
                                } else {
                                    Log.d(TAG, "6. T ==>> B, L =>> R")
                                }
                            }
                        } else {
                            if (mTopToBottomCover > -100) {
                                if (mRightToLeftCover < -100) {
                                    Log.d(TAG, "7. L =>> R")
                                    onLeftToRightSwap()
                                }
                            } else {
                                if (mRightToLeftCover < -100) {
                                    Log.d(TAG, "12. B ==>> T")
                                    onBottomToTopSwap()
                                } else {
                                    Log.d(TAG, "8. B ==>> T, L =>> R")
                                }
                            }
                        }
                    }
                    return true
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            //////////////////////////////////////////////////////////////////////////////
            false
        }
    }

    //EXPERIMENTAL PURPOSE
    abstract fun onLeftToRightSwap()
    abstract fun onRightToLeftSwap()
    abstract fun onTopToBottomSwap()
    abstract fun onBottomToTopSwap()
    abstract fun onLeftToRightTopToBottomDiagonalSwap()
    abstract fun onLeftToRightBottomToTopDiagonalSwap()
    abstract fun onRightToLeftTopToBottomDiagonalSwap()
    abstract fun onRightToLeftBottomToTopDiagonalSwap()

    //SINGLE AND DOUBLE TABS
    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        Log.d(TAG, "On Single Tap")
        Log.d(TAG, "Selection Start : " + isSelectionStart)
        Log.d(TAG, "ME 1 : X - " + e.x)
        Log.d(TAG, "ME 1 : Y - " + e.y)
        onSingleTap()
        return super.onSingleTapConfirmed(e)
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        Log.d(TAG, "On Double Tap")
        onDoubleTap()
        return super.onDoubleTap(e)
    }

    abstract fun onSingleTap()
    abstract fun onDoubleTap()
    override fun onLongPress(e: MotionEvent) {
        onLongPressPerformed(e)
        super.onLongPress(e)
    }

    abstract fun onLongPressPerformed(e: MotionEvent?)

    companion object {
        private val TAG = CustomGestureDetector::class.java.simpleName
    }
}