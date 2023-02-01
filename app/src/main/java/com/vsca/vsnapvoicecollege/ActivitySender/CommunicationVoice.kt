package com.vsca.vsnapvoicecollege.ActivitySender

import android.graphics.Color
import android.graphics.PorterDuff
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import java.io.File
import java.io.IOException

class CommunicationVoice : AppCompatActivity() {
    var mediaPlayer: MediaPlayer? = null
    var bIsRecording = false
    private val iRequestCode = 0
    var mediaFileLengthInMilliseconds = 0
    var handler = Handler()
    var recTime = 0
    var recorder: MediaRecorder? = null
    var recTimerHandler = Handler()
    var iMediaDuration = 0
    var futureStudioIconFile: File? = null
    var iMaxRecDur = 0
    val VOICE_FOLDER_NAME = "Gradit"
    val VOICE_FILE_NAME = ".mp3"
    var VoiceFilePath: String? = null
    var VoiceDuration: String? = null

    @JvmField
    @BindView(R.id.imgrecord)
    var imgrecord: ImageView? = null

    @JvmField
    @BindView(R.id.imgPlayPasue)
    var imgPlayPasue: ImageView? = null

    @JvmField
    @BindView(R.id.lblRecordTime)
    var lblRecordTime: TextView? = null

    @JvmField
    @BindView(R.id.seekbarplayvoice)
    var seekbarplayvoice: SeekBar? = null

    @JvmField
    @BindView(R.id.lbltotalduration)
    var lbltotalduration: TextView? = null

    @JvmField
    @BindView(R.id.rytLayoutSeekPlay)
    var rytLayoutSeekPlay: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CommonUtil.SetTheme(this)

//        setContentView(R.layout.activity_communication_voice)

        ButterKnife.bind(this)
        setupAudioPlayer()

    }
    val layoutResourceId: Int
        get() = R.layout.activity_communication_voice


    @OnClick(R.id.imgrecord)
    fun imgvoicerecordClick() {
        if (bIsRecording) {
            if (lblRecordTime!!.getText().toString() == "00:00") {
                imgrecord!!.setClickable(false)
                imgrecord!!.setEnabled(false)
            } else {
                stop_RECORD()
            }
        } else {
            start_RECORD()
        }

    }

    @OnClick(R.id.imgPlayPasue)
    fun playvoiceatseekbar() {
        recplaypause()
    }

    fun start_RECORD() {
        if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
        }

        imgrecord!!.setImageResource(R.drawable.voice_stop)
        imgrecord!!.setClickable(true)
        rytLayoutSeekPlay!!.setVisibility(View.GONE)

        try {
            recorder = MediaRecorder()
            recorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            recorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            recorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            recorder!!.setAudioEncodingBitRate(16)
            recorder!!.setAudioSamplingRate(44100)
            recorder!!.setOutputFile(getRecFilename())
            recorder!!.prepare()
            recorder!!.start()
            recTimeUpdation()
            bIsRecording = true
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stop_RECORD() {
        recorder!!.stop()
        recTimerHandler.removeCallbacks(runson)
        bIsRecording = false
        imgrecord!!.setImageResource(R.drawable.voice_stop)
        rytLayoutSeekPlay!!.setVisibility(View.VISIBLE)

        fetchSong()

    }

    fun setupAudioPlayer() {
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setOnCompletionListener {
            imgPlayPasue!!.setImageResource(R.drawable.ic_pause)
            mediaPlayer!!.seekTo(0)
        }
    }

    fun recplaypause() {
        mediaFileLengthInMilliseconds = mediaPlayer!!.duration

        if (!mediaPlayer!!.isPlaying) {
            mediaPlayer!!.start()
            imgPlayPasue!!.setImageResource(R.drawable.ic_play)

        } else {
            mediaPlayer!!.pause()
            imgPlayPasue!!.setClickable(true)
            imgPlayPasue!!.setImageResource(R.drawable.ic_pause)
        }
        primarySeekBarProgressUpdater(mediaFileLengthInMilliseconds)
    }

    private fun primarySeekBarProgressUpdater(fileLength: Int) {
        val iProgress =
            ((mediaPlayer!!.currentPosition.toFloat() / fileLength) * 100).toInt()
        seekbarplayvoice!!.progress = iProgress
        if (mediaPlayer!!.isPlaying) {
            val notification: Runnable = object : Runnable {
                override fun run() {
                    lbltotalduration!!.text = milliSecondsToTimer(
                        mediaPlayer!!.currentPosition.toLong()
                    )
                    primarySeekBarProgressUpdater(fileLength)
                }
            }
            handler.postDelayed(notification, 1000)
        }
    }

    fun getRecFilename(): String? {
        var filepath: String
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            filepath = this!!.getApplicationContext()
                .getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.getPath()

        } else {
            filepath = Environment.getExternalStorageDirectory().getPath()

        }

        val fileDir = File(filepath, VOICE_FOLDER_NAME)
        if (!fileDir.exists()) {
            fileDir.mkdirs()
        }
        val fileNamePath = File(fileDir, VOICE_FILE_NAME)
        return fileNamePath.path


    }

    fun recTimeUpdation() {
        recTime = 1
        recTimerHandler.postDelayed(runson, 1000)
    }

    val runson: Runnable = object : Runnable {
        override fun run() {
            lblRecordTime!!.setText(milliSecondsToTimer(recTime * 1000.toLong()))
            val timing: String = lblRecordTime!!.getText().toString()
            if (lblRecordTime!!.getText().toString() != "00:00") {
                imgrecord!!.setEnabled(true)
            }
            recTime = recTime + 1
            if (recTime != iMaxRecDur) {
                recTimerHandler.postDelayed(this, 1000)
            } else {
                stop_RECORD()
            }
        }
    }

    fun milliSecondsToTimer(milliseconds: Long): String? {
        var finalTimerString = ""
        var secondsString = ""
        var minutesString = ""

        val hours = (milliseconds / (1000 * 60 * 60)).toInt()
        val minutes = (milliseconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
        val seconds = (milliseconds % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()
        if (hours > 0) {
            finalTimerString = "$hours:"
        }

        minutesString = if (minutes < 10) {
            "0$minutes"
        } else {
            "" + minutes
        }
        secondsString = if (seconds < 10) {
            "0$seconds"
        } else {
            "" + seconds
        }
        finalTimerString = "$finalTimerString$minutesString:$secondsString"
        return finalTimerString
    }

    fun fetchSong() {
        Log.d("FetchSong", "Start***************************************")
        try {
            val filepath: String
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                filepath = this!!.getApplicationContext()
                    .getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.getPath()
                Log.d("File 11", filepath!!)

            } else {

                filepath = Environment.getExternalStorageDirectory().getPath()
                Log.d("File 10", filepath!!)

            }

            val fileDir = File(filepath, VOICE_FOLDER_NAME)

            if (!fileDir.exists()) {
                fileDir.mkdirs()
            }

            futureStudioIconFile = File(fileDir, VOICE_FILE_NAME)
            VoiceFilePath = futureStudioIconFile?.path
            println("FILE_PATH:" + futureStudioIconFile!!.path)
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(futureStudioIconFile!!.path)
            mediaPlayer!!.prepare()
            iMediaDuration = (mediaPlayer!!.duration / 1000.0).toInt()
            VoiceDuration = iMediaDuration.toString()

        } catch (e: Exception) {
            Log.d("in Fetch Song", e.toString())
        }
        Log.d("FetchSong", "END***************************************")
    }

    override fun onResume() {
        super.onResume()
        this!!.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onPause() {
        super.onPause()
        if (mediaPlayer!!.isPlaying) {
            imgrecord!!.setClickable(false)
            mediaPlayer!!.pause()
            imgPlayPasue!!.setImageResource(R.drawable.ic_play)
        }
        if (bIsRecording) imgrecord!!.setClickable(true)
        this!!.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

}