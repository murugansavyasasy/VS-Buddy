package com.vsca.vsnapvoicecollege.Adapters

import android.content.Context
import com.vsca.vsnapvoicecollege.Model.DashboardSubItems
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.vsca.vsnapvoicecollege.R
import com.bumptech.glide.Glide
import android.media.MediaPlayer.OnCompletionListener
import android.view.MotionEvent
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.constraintlayout.widget.ConstraintLayout
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.*
import android.widget.*
import com.vsca.vsnapvoicecollege.Activities.*
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import com.vsca.vsnapvoicecollege.Utils.DownloadVoice
import java.io.*
import java.lang.Exception
import java.util.ArrayList

class DashboardChild(
    private val newsModalArrayList: ArrayList<DashboardSubItems>,
    private val context: Context,
    private val type: String
) : RecyclerView.Adapter<DashboardChild.ViewHolder>() {

    private var mExpandedPosition = -1
    var msgcontent: String? = null
    var path: String? = null
    var detailsid: String? = null
    var PlayPath: String? = null
    var mediaPlayer: MediaPlayer? = MediaPlayer()
    var mediaPlayerTwo: MediaPlayer? = MediaPlayer()
    var mediaFileLengthInMilliseconds = 0
    var handler = Handler()
    var iMediaDuration = 0
    var Position: Int = 0

    var isemergencyExpanded: Boolean? = null
    private val VOICE_FOLDER: String? = "Gradit/Voice/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.dashboard_list_design, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modal = newsModalArrayList[position]
        Position = position

        if ((type == "Circular")) {
            holder.lnrImageView.visibility = View.GONE
            holder.LayoutAd.visibility = View.GONE
            holder.LayoutCicular.visibility = View.VISIBLE
            holder.lnrEmgVoice.visibility = View.GONE
            if (position % 2 == 0) {
                holder.LayoutCicular.setBackgroundResource(R.drawable.bg_dashboard_circular_grey)
            } else {
                holder.LayoutCicular.setBackgroundResource(R.drawable.bg_dashboard_cicular)
            }
            holder.lblCircularTitle.text = modal.menuTitle
            holder.lblCirculardescription.text = modal.menuDescription
            holder.lblCircularCreateTime.text = modal.createTime
            holder.lblCicularCreateDate.text = modal.createDate

            val Imagepath = modal.FilepathList.get(0)
            holder.lblPath.text = context.getString(R.string.txt_attachment)
            holder.lnrCircularAttachment!!.setOnClickListener({
                val i: Intent = Intent(context, ViewFiles::class.java)
                i.putExtra("images", Imagepath)
                context.startActivity(i)
            })

            var menuid = BaseActivity.CircularMenuID
            CommonUtil.MenuIDCircular = menuid
            holder.LayoutCicular.setOnClickListener({

                val i: Intent = Intent(context, Circular::class.java)
                context.startActivity(i)
            })
        } else if ((type == "Notice Board")) {
            holder.lnrImageView.visibility = VISIBLE
            holder.LayoutAd.visibility = GONE
            holder.LayoutCicular.visibility = GONE
            holder.lnrEmgVoice.visibility = GONE
            holder.lblNoiceboardTitle.text = modal.menuTitle
            holder.lblNoticeDescription.text = modal.menuDescription
            holder.lblCreateTime.text = modal.createTime
            holder.lblNotiCreateDate.text = modal.createDate
            if (position % 2 == 0) {
                holder.rytNoticeboard.setBackgroundResource(R.drawable.noticeboard_blue)
            } else {
                holder.rytNoticeboard.setBackgroundResource(R.drawable.noticeboard_yellow)
            }

            var menuid = BaseActivity.NoticeboardMenuID
            CommonUtil.MenuIDNoticeboard = menuid
            holder.lnrImageView.setOnClickListener({
                val i: Intent = Intent(context, Noticeboard::class.java)
                context.startActivity(i)
            })

        } else if ((type == "Ad")) {
            holder.lnrImageView.visibility = GONE
            holder.LayoutAd.visibility = VISIBLE
            holder.LayoutCicular.visibility = GONE
            holder.lnrEmgVoice.visibility = GONE
            Glide.with(context)
                .load(modal.adBaackgroundImage)
                .into(holder.imgAdvertisement)
            Glide.with(context)
                .load(modal.addImage)
                .into(holder.imgthumb)

            holder.LayoutAd.setOnClickListener({

                BaseActivity.LoadWebViewContext(context, CommonUtil.AdWebURl)

            })

        } else if ((type == "Attendance")) {
            holder.lnrImageView.visibility = View.GONE
            holder.LayoutAd.visibility = View.GONE
            holder.LayoutCicular.visibility = View.GONE
            holder.lnrEmgVoice.visibility = View.GONE
            holder.lnrRecentNotifications.visibility = View.GONE
            holder.lblNoDataFound.visibility = VISIBLE
            holder.lblNoDataFound.text = context.getString(R.string.txt_attanace_no_data)

//            if(newsModalArrayList.isNullOrEmpty()){
//                holder.lblNoDataFound.visibility= VISIBLE
//                holder.lblNoDataFound.text = context.getString(R.string.txt_attanace_no_data)
//            }

        } else if ((type == "Emergency Notification")) {
            holder.lnrImageView.visibility = GONE
            holder.LayoutAd.visibility = GONE
            holder.LayoutCicular.visibility = GONE
            holder.lnrEmgVoice.visibility = VISIBLE

            isemergencyExpanded = position == mExpandedPosition

            holder.lnrEmergencyVoice.visibility =
                if (isemergencyExpanded!!) View.VISIBLE else View.GONE
            holder.lnrEmgVoice.isActivated = isemergencyExpanded!!
            holder.lnrEmgVoice.visibility = VISIBLE

            val voiceduration = modal.duration
            val minutes = (voiceduration % 3600) / 60
            val seconds = voiceduration % 60
            val timeString = String.format("%02d:%02d", minutes, seconds)
            holder.lbltotalduration.text = timeString
            holder.lblVoicetitle.text = modal.menuTitle
            holder.lblPostedBy.text = modal.membername
            val value = modal.createdon
            val splitDate = value!!.split("\\s+".toRegex()).toTypedArray()
            val Date = splitDate[0]
            holder.lblVoiceDate.text = Date
            val time = splitDate[1]
            holder.lblVoiceTime.text = time

            holder.rytEmgVoice.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    mExpandedPosition = if (isemergencyExpanded!!) -1 else position
                    notifyDataSetChanged()

                    msgcontent = modal.voiceFilepath
                    path = modal.voiceFilepath
                    var filename: String = modal.MsgId!! + "_" + "Gradit.mp3"
                    path = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        context!!.applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.path
                    } else {
                        Environment.getExternalStorageDirectory().path
                    }
                    val dir = File(path, VOICE_FOLDER)
                    val file = File(dir, filename)
                    if (file.exists()) {
                        holder.rytSeekbarlayout.visibility = View.VISIBLE
                        CommonUtil.CommunicationisExpandAdapter = false
                        SetUpAudioPlayer(holder)
                        fetchPathUrl(modal)
                        mExpandedPosition = if (isemergencyExpanded!!) -1 else position
                        notifyDataSetChanged()

                        Log.d("FileExist", "exist")
                        mediaPlayer!!.setOnCompletionListener(OnCompletionListener {
                            holder.imgEmgplaypause.setImageResource(R.drawable.ic_play)

                            Log.d("SeekCompleteion","stop")
                            mediaPlayer!!.seekTo(0)
                        })
                        holder.emergencyseekbar.setOnSeekBarChangeListener(object :
                            OnSeekBarChangeListener {
                            override fun onStopTrackingTouch(seekBar: SeekBar) {}
                            override fun onStartTrackingTouch(seekBar: SeekBar) {}
                            override fun onProgressChanged(
                                seekBar: SeekBar,
                                progress: Int,
                                fromUser: Boolean
                            ) {
                                if (mediaPlayer != null && fromUser) {
                                    val playPositionInMillisecconds =
                                        (mediaFileLengthInMilliseconds / 100) * holder.emergencyseekbar.progress
                                    mediaPlayer!!.seekTo(playPositionInMillisecconds)
                                }
                            }
                        })

                        Log.d("FetchSong", "END***************************************")
                        holder.imgEmgplaypause.setOnClickListener(object : View.OnClickListener {
                            override fun onClick(view: View) {
                                mediaFileLengthInMilliseconds =
                                    mediaPlayer!!.duration // gets the song length in milliseconds from URL
                                if (!mediaPlayer!!.isPlaying) {
                                    mediaPlayer!!.start()
                                    holder.imgEmgplaypause.setImageResource(R.drawable.ic_pause)
                                } else {
                                    mediaPlayer!!.pause()
                                    holder.imgEmgplaypause.setImageResource(R.drawable.ic_play)
                                }
                                primarySeekBarProgressUpdater(mediaFileLengthInMilliseconds)
                            }

                            private fun primarySeekBarProgressUpdater(fileLength: Int) {
                                val iProgress =
                                    ((mediaPlayer!!.currentPosition.toFloat() / fileLength) * 100).toInt()
                                holder.emergencyseekbar.progress = iProgress
                                if (mediaPlayer!!.isPlaying) {
                                    val notification: Runnable = object : Runnable {
                                        override fun run() {
                                            holder.lblEmgfromduration.text = milliSecondsToTimer(
                                                mediaPlayer!!.currentPosition.toLong()
                                            )
                                            primarySeekBarProgressUpdater(fileLength)
                                        }
                                    }
                                    handler.postDelayed(notification, 1000)
                                }
                            }
                        })

                    } else {

                        DownloadVoice.downloadSampleFile(
                            context, modal.voiceFilepath!!,
                            VOICE_FOLDER,
                            filename, holder,true
                        )

                        Log.d("FetchSong", "END***************************************")
                        holder.imgEmgplaypause.setOnClickListener(object :
                            View.OnClickListener {
                            override fun onClick(view: View) {
                                SetUpAudioPlayer(holder)
                                fetchPathUrl(modal)
                                mediaPlayer!!.setOnCompletionListener(OnCompletionListener {
                                    holder.imgEmgplaypause.setImageResource(R.drawable.ic_play)
                                    Log.d("SeekCompleteion","stop")
                                    mediaPlayer!!.seekTo(0)
                                })
                                if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                                    mediaPlayer!!.stop()
                                    holder.imgRecentEmgplaypause.setImageResource(R.drawable.ic_play)
                                    mediaPlayer!!.seekTo(0)
                                }
                                mediaFileLengthInMilliseconds =
                                    mediaPlayer!!.duration // gets the song length in milliseconds from URL
                                if (!mediaPlayer!!.isPlaying) {
                                    mediaPlayer!!.start()
                                    holder.imgEmgplaypause.setImageResource(R.drawable.ic_pause)
                                } else {
                                    mediaPlayer!!.pause()
                                    holder.imgEmgplaypause.setImageResource(R.drawable.ic_play)
                                }
                                primarySeekBarProgressUpdater(mediaFileLengthInMilliseconds)
                            }

                            private fun primarySeekBarProgressUpdater(fileLength: Int) {
                                val iProgress =
                                    ((mediaPlayer!!.currentPosition.toFloat() / fileLength) * 100).toInt()
                                holder.emergencyseekbar.progress = iProgress
                                if (mediaPlayer!!.isPlaying) {
                                    val notification: Runnable = object : Runnable {
                                        override fun run() {
                                            holder.lblEmgfromduration.text = milliSecondsToTimer(mediaPlayer!!.currentPosition.toLong())
                                            primarySeekBarProgressUpdater(fileLength)
                                        }
                                    }
                                    handler.postDelayed(notification, 1000)
                                }
                            }
                        })
                    }
                    holder.emergencyseekbar.setOnSeekBarChangeListener(object :
                        OnSeekBarChangeListener {
                        override fun onStopTrackingTouch(seekBar: SeekBar) {}
                        override fun onStartTrackingTouch(seekBar: SeekBar) {}
                        override fun onProgressChanged(
                            seekBar: SeekBar,
                            progress: Int,
                            fromUser: Boolean
                        ) {
                            if (mediaPlayer != null && fromUser) {
                                val playPositionInMillisecconds =
                                    (mediaFileLengthInMilliseconds / 100) * holder.emergencyseekbar.progress
                                mediaPlayer!!.seekTo(playPositionInMillisecconds)
                            }
                        }
                    })


//                    }


                }
            })


        } else if ((type == "Recent Notifications")) {
            holder.lnrImageView.visibility = View.GONE
            holder.LayoutAd.visibility = View.GONE
            holder.LayoutCicular.visibility = View.GONE
            holder.lnrEmgVoice.visibility = View.GONE
            holder.lnrRecentNotifications.visibility = View.VISIBLE

            val isExpanded = position == mExpandedPosition
            holder.lnrRecentVoice.visibility = if (isExpanded) View.VISIBLE else View.GONE
            holder.lnrRecentNotifications.isActivated = isExpanded
            holder.lnrRecentNotifications.visibility = View.VISIBLE
            holder.lblRecenttitle.text = modal.menuDescription

            if (modal.RecentType!!.equals("Voice")) {
                holder.lnrplayvoice.visibility = VISIBLE
                holder.imgRecentType.setImageResource(R.drawable.dashboard_recent_voice)
                holder.imgRecentType.setAlpha(0.7f)
            }
            if (modal.RecentType!!.equals("Emergencyvoice Message")) {
                holder.lnrplayvoice.visibility = VISIBLE
                holder.imgRecentType.setImageResource(R.drawable.emergency_voice)
                holder.imgRecentType.setAlpha(0.7f)
            }
            if (modal.RecentType!!.equals("Text Message")) {
                holder.lnrplayvoice.visibility = GONE
                holder.imgRecentType.setImageResource(R.drawable.dashboard_text)
                holder.imgRecentType.setAlpha(0.7f)
            }
            val voiceduration = modal.duration
            val minutes = (voiceduration % 3600) / 60
            val seconds = voiceduration % 60
            val timeString = String.format("%02d:%02d", minutes, seconds)
            holder.lblRecentTotalDuration.text = timeString
            holder.lblRecentPostedby.text = modal.membername
            holder.lblRecentDate.text = modal.createdon
            holder.lblRecentTime.text = modal.createTime

            if (isExpanded) {
                holder.imgArrowdown!!.setImageResource(R.drawable.ic_arrow_up_blue)
                holder.lnrplayvoice.visibility = View.GONE
                holder.lblRecentDesciption.visibility = View.GONE

            } else {
                if (modal.RecentType!!.equals("Voice")) {
                    holder.lnrplayvoice.visibility = View.VISIBLE
                }
                if (modal.RecentType!!.equals("Emergencyvoice Message")) {
                    holder.lnrplayvoice.visibility = View.VISIBLE
                }
                if (modal.RecentType!!.equals("Text Message")) {
                    holder.lnrplayvoice.visibility = GONE
                }

                holder.imgArrowdown!!.setImageResource(R.drawable.ic_arrow_down_blue)
            }

            holder.rytRecentNotification.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    holder.lnrplayvoice.visibility = GONE
                    if (modal.RecentType!!.equals("Text")) {
                        holder.lblRecentDesciption.visibility = VISIBLE
                        holder.lblRecentDesciption.text = modal.Content
                        holder.recentSeekbarlayout.visibility = GONE
                        mExpandedPosition = if (isExpanded) -1 else position
                        notifyDataSetChanged()
                    } else {
                        mExpandedPosition = if (isExpanded) -1 else position
                        notifyDataSetChanged()

                        msgcontent = modal.Content
                        Log.d("msgcontent", msgcontent!!)

                        path = modal.Content

                        Log.d("Recentpath", path!!)

                        var filename: String = modal.MsgId!! + "_" + "Gradit.mp3"
                        path = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            context!!.applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.path
                        } else {
                            Environment.getExternalStorageDirectory().path
                        }
                        val dir = File(path, VOICE_FOLDER)
                        val file = File(dir, filename)
                        if (file.exists()) {

                            holder.recentSeekbarlayout.visibility = VISIBLE

                            CommonUtil.CommunicationisExpandAdapter = false
                            SetUpRecentAudioPlayer(holder)
                            fetchRecentPathUrl(modal)


                            Log.d("FileExist", "exist")
                            holder.recentseekbar.setOnSeekBarChangeListener(object :
                                OnSeekBarChangeListener {
                                override fun onStopTrackingTouch(seekBar: SeekBar) {}
                                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                                override fun onProgressChanged(
                                    seekBar: SeekBar,
                                    progress: Int,
                                    fromUser: Boolean
                                ) {
                                    if (mediaPlayer != null && fromUser) {
                                        val playPositionInMillisecconds =
                                            (mediaFileLengthInMilliseconds / 100) * holder.recentseekbar.progress
                                        mediaPlayer!!.seekTo(playPositionInMillisecconds)
                                    }
                                }
                            })

                            holder.imgRecentEmgplaypause.setOnClickListener(object :
                                View.OnClickListener {
                                override fun onClick(view: View) {
                                    mediaFileLengthInMilliseconds =
                                        mediaPlayer!!.duration // gets the song length in milliseconds from URL
                                    if (!mediaPlayer!!.isPlaying) {
                                        mediaPlayer!!.start()
                                        holder.imgRecentEmgplaypause.setImageResource(R.drawable.ic_pause)
                                    } else {
                                        mediaPlayer!!.pause()
                                        holder.imgRecentEmgplaypause.setImageResource(R.drawable.ic_play)
                                    }
                                    primarySeekBarProgressUpdater(mediaFileLengthInMilliseconds)
                                }

                                private fun primarySeekBarProgressUpdater(fileLength: Int) {
                                    val iProgress =
                                        ((mediaPlayer!!.currentPosition.toFloat() / fileLength) * 100).toInt()
                                    holder.recentseekbar.progress = iProgress
                                    if (mediaPlayer!!.isPlaying) {
                                        val notification: Runnable = object : Runnable {
                                            override fun run() {
                                                holder.lblEmgRecentduration.text =
                                                    milliSecondsToTimer(
                                                        mediaPlayer!!.currentPosition.toLong()
                                                    )
                                                primarySeekBarProgressUpdater(fileLength)
                                            }
                                        }
                                        handler.postDelayed(notification, 1000)
                                    }
                                }
                            })
                        } else {

                            DownloadVoice.downloadSampleFile(
                                context, modal.Content!!,
                                VOICE_FOLDER,
                                filename, holder,false
                            )

//                            if (CommonUtil.CommunicationisExpandAdapter) {
                                holder.recentseekbar.setOnSeekBarChangeListener(object :
                                    OnSeekBarChangeListener {
                                    override fun onStopTrackingTouch(seekBar: SeekBar) {}
                                    override fun onStartTrackingTouch(seekBar: SeekBar) {}
                                    override fun onProgressChanged(
                                        seekBar: SeekBar,
                                        progress: Int,
                                        fromUser: Boolean
                                    ) {
                                        if (mediaPlayer != null && fromUser) {
                                            val playPositionInMillisecconds =
                                                (mediaFileLengthInMilliseconds / 100) * holder.recentseekbar.progress
                                            mediaPlayer!!.seekTo(playPositionInMillisecconds)
                                        }
                                    }
                                })

                                holder.imgRecentEmgplaypause.setOnClickListener(object :
                                    View.OnClickListener {
                                    override fun onClick(view: View) {

                                        SetUpRecentAudioPlayer(holder)
                                        fetchRecentPathUrl(modal)
                                        mediaPlayer!!.setOnCompletionListener(OnCompletionListener {

                                            Log.d("Completeion","stop")
                                            holder.imgRecentEmgplaypause.setImageResource(R.drawable.ic_play)
                                            mediaPlayer!!.seekTo(0)
                                        })
                                        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                                            mediaPlayer!!.stop()
                                            holder.imgRecentEmgplaypause.setImageResource(R.drawable.ic_play)
                                            mediaPlayer!!.seekTo(0)
                                        }

                                        mediaFileLengthInMilliseconds =
                                            mediaPlayer!!.duration // gets the song length in milliseconds from URL
                                        if (!mediaPlayer!!.isPlaying) {
                                            mediaPlayer!!.start()
                                            holder.imgRecentEmgplaypause.setImageResource(R.drawable.ic_pause)
                                        } else {
                                            mediaPlayer!!.pause()
                                            holder.imgRecentEmgplaypause.setImageResource(R.drawable.ic_play)
                                        }
                                        primarySeekBarProgressUpdater(mediaFileLengthInMilliseconds)
                                    }

                                    private fun primarySeekBarProgressUpdater(fileLength: Int) {
                                        val iProgress =
                                            ((mediaPlayer!!.currentPosition.toFloat() / fileLength) * 100).toInt()
                                        holder.recentseekbar.progress = iProgress
                                        if (mediaPlayer!!.isPlaying) {
                                            val notification: Runnable = object : Runnable {
                                                override fun run() {
                                                    holder.lblEmgRecentduration.text =
                                                        milliSecondsToTimer(
                                                            mediaPlayer!!.currentPosition.toLong()
                                                        )
                                                    primarySeekBarProgressUpdater(fileLength)
                                                }
                                            }
                                            handler.postDelayed(notification, 1000)
                                        }
                                    }
                                })
//                            }



                        }


                    }
                }
            })

        } else {
            holder.lnrImageView.visibility = View.GONE
            holder.LayoutAd.visibility = View.GONE
            holder.LayoutCicular.visibility = View.GONE
            holder.lnrEmgVoice.visibility = View.GONE
        }
    }

    private fun SetUpRecentAudioPlayer(holder: ViewHolder) {
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
            holder.imgRecentEmgplaypause.setImageResource(R.drawable.ic_play)
            mediaPlayer!!.seekTo(0)
            Log.d("Completeion","stop")

        }
        mediaPlayer!!.setOnCompletionListener(OnCompletionListener {

            Log.d("Completeion","stop")
            holder.imgRecentEmgplaypause.setImageResource(R.drawable.ic_play)
            mediaPlayer!!.seekTo(0)
        })
        holder.recentseekbar.max = 99
        holder.recentseekbar.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (v.id == R.id.recentseekbar) {
                    run {
                        val sb: SeekBar = v as SeekBar
                        val playPositionInMillisecconds: Int =
                            (mediaFileLengthInMilliseconds / 100) * sb.getProgress()
                        mediaPlayer!!.seekTo(playPositionInMillisecconds)
                    }
                }
                return false
            }
        })
    }
    private fun fetchRecentPathUrl(modal: DashboardSubItems) {
        Log.d("PlayStart", "Start***************************************")
        try {
            var filename: String = modal.MsgId!! + "_" + "Gradit.mp3"
            val path: String
            path = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                context!!.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.getPath()
            } else {
                Environment.getExternalStorageDirectory().path
            }

            val dir = File(path, VOICE_FOLDER)
            val file = File(dir, filename)
            PlayPath = file.path
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(PlayPath)
            mediaPlayer!!.prepare()
            iMediaDuration = (mediaPlayer!!.duration / 1000.0).toInt()
        } catch (e: Exception) {
            Log.d("ExceptionWhilePlaying", e.toString())
        }
        Log.d("PlayEnd", "END***************************************")
    }
    private fun fetchPathUrl(modal: DashboardSubItems) {
        Log.d("PlayStart", "Start***************************************")
        try {
            var filename: String = modal.MsgId!! + "_" + "Gradit.mp3"
            val path: String
            path = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                context!!.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.getPath()
            } else {
                Environment.getExternalStorageDirectory().path
            }

            val dir = File(path, VOICE_FOLDER)
            val file = File(dir, filename)
            PlayPath = file.path
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(PlayPath)
            mediaPlayer!!.prepare()
            iMediaDuration = (mediaPlayer!!.duration / 1000.0).toInt()
        } catch (e: Exception) {
            Log.d("ExceptionWhilePlaying", e.toString())
        }
        Log.d("PlayEnd", "END***************************************")
    }

    private fun SetUpAudioPlayer(holder: ViewHolder) {
        mediaPlayer = MediaPlayer()

        Log.d("SetUpAudio","audio")
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
            holder.imgEmgplaypause.setImageResource(R.drawable.ic_play)
            Log.d("SetUpAudioPlayer","stop")

            mediaPlayer!!.seekTo(0)
        }
        mediaPlayer!!.setOnCompletionListener(OnCompletionListener {
            holder.imgEmgplaypause.setImageResource(R.drawable.ic_play)

            Log.d("SeekCompleteion","stop")
            mediaPlayer!!.seekTo(0)
        })
        holder.emergencyseekbar.max = 99
        holder.emergencyseekbar.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (v.id == R.id.emergencyseekbar) {
                    run {
                        val sb: SeekBar = v as SeekBar
                        val playPositionInMillisecconds: Int =
                            (mediaFileLengthInMilliseconds / 100) * sb.getProgress()
                        mediaPlayer!!.seekTo(playPositionInMillisecconds)
                    }
                }
                return false
            }
        })
    }

    override fun getItemCount(): Int {
        return newsModalArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lblNoiceboardTitle: TextView
        val lblNoticeDescription: TextView
        val lblCreateTime: TextView
        val lblNotiCreateDate: TextView
        val lblCircularTitle: TextView
        val lblCirculardescription: TextView
        val lblCicularCreateDate: TextView
        val lblCircularCreateTime: TextView
        val lblVoicetitle: TextView
        val lblPostedBy: TextView
        val lbltotalduration: TextView
        val lblVoiceTime: TextView
        val lblVoiceDate: TextView
        val lblEmgfromduration: TextView
        val lnrImageView: RelativeLayout
        val rytEmgVoice: RelativeLayout
        var imgAdvertisement: ImageView
        var imgthumb: ImageView
        var imgEmgplaypause: ImageView
        var lnrRecentVoice: LinearLayout
        var LayoutAd: ConstraintLayout
        var LayoutCicular: ConstraintLayout
        var rytNoticeboard: RelativeLayout
        var lnrEmgVoice: LinearLayout
        var lnrEmergencyVoice: LinearLayout
        var lblVoicedescription: TextView
        var lnrCircularAttachment: LinearLayout
        var emergencyseekbar: SeekBar
        var lblPath: TextView
        var lblRecentDesciption: TextView
        var lnrRecentNotifications: LinearLayout
        var recentSeekbarlayout: RelativeLayout
        var lblRecentDate: TextView
        var lblRecentTime: TextView
        var imgArrowdown: ImageView
        var imgRecentEmgplaypause: ImageView
        var recentseekbar: SeekBar
        var lblEmgRecentduration: TextView
        var lblRecentTotalDuration: TextView
        var lblRecentPostedby: TextView
        var lblRecenttitle: TextView
        var lblNoDataFound: TextView
        var lnrplayvoice: LinearLayout
        var rytRecentNotification: RelativeLayout
        var imgRecentType: ImageView
        var rytSeekbarlayout: RelativeLayout

        init {
            lblNoiceboardTitle = itemView.findViewById(R.id.lblNoiceboardTitle)
            rytEmgVoice = itemView.findViewById(R.id.rytEmgVoice)
            lblNoticeDescription = itemView.findViewById(R.id.lblNoticeDescription)
            lblCreateTime = itemView.findViewById(R.id.lblCreateTime)
            lblNotiCreateDate = itemView.findViewById(R.id.lblNotiCreateDate)
            lblVoicetitle = itemView.findViewById(R.id.lblVoicetitle)
            lblVoiceTime = itemView.findViewById(R.id.lblVoiceTime)
            lnrEmergencyVoice = itemView.findViewById(R.id.lnrEmergencyVoice)
            lblVoicedescription = itemView.findViewById(R.id.lblVoicedescription)
            imgEmgplaypause = itemView.findViewById(R.id.imgEmgplaypause)
            lblEmgfromduration = itemView.findViewById(R.id.lblEmgfromduration)
            lblPostedBy = itemView.findViewById(R.id.lblPostedBy)
            lblVoiceDate = itemView.findViewById(R.id.lblVoiceDate)
            lbltotalduration = itemView.findViewById(R.id.lblTotalDuration)
            emergencyseekbar = itemView.findViewById(R.id.emergencyseekbar)
            lnrEmgVoice = itemView.findViewById(R.id.lnrEmgVoice)
            lnrImageView = itemView.findViewById(R.id.lnrImageView)
            imgAdvertisement = itemView.findViewById(R.id.imgAdvertisement)
            imgthumb = itemView.findViewById(R.id.imgthumb)
            LayoutAd = itemView.findViewById(R.id.LayoutAdvertisement)
            lblCircularTitle = itemView.findViewById(R.id.lblCircularTitle)
            lblCirculardescription = itemView.findViewById(R.id.lblCirculardescription)
            lblCicularCreateDate = itemView.findViewById(R.id.lblCicularCreateDate)
            lblCircularCreateTime = itemView.findViewById(R.id.lblCircularCreateTime)
            LayoutCicular = itemView.findViewById(R.id.LayoutCicular)
            lnrCircularAttachment = itemView.findViewById(R.id.lnrCircularAttachment)
            rytNoticeboard = itemView.findViewById(R.id.rytNoticeboard)
            lblPath = itemView.findViewById(R.id.lblPath)
            lnrRecentNotifications = itemView.findViewById(R.id.lnrRecentNotifications)
            lnrRecentVoice = itemView.findViewById(R.id.lnrRecentVoice)
            lblRecentDesciption = itemView.findViewById(R.id.lblRecentDesciption)
            recentSeekbarlayout = itemView.findViewById(R.id.recentSeekbarlayout)
            lblRecentDate = itemView.findViewById(R.id.lblRecentDate)
            lblRecentTime = itemView.findViewById(R.id.lblRecentTime)
            imgArrowdown = itemView.findViewById(R.id.imgArrowdown)
            imgRecentEmgplaypause = itemView.findViewById(R.id.imgRecentEmgplaypause)
            recentseekbar = itemView.findViewById(R.id.recentseekbar)
            lblEmgRecentduration = itemView.findViewById(R.id.lblEmgRecentduration)
            lblRecentTotalDuration = itemView.findViewById(R.id.lblRecentTotalDuration)
            lblRecentPostedby = itemView.findViewById(R.id.lblRecentPostedby)
            lblRecenttitle = itemView.findViewById(R.id.lblRecenttitle)
            lnrplayvoice = itemView.findViewById(R.id.lnrplayvoice)
            rytRecentNotification = itemView.findViewById(R.id.rytRecentNotification)
            imgRecentType = itemView.findViewById(R.id.imgRecentType)
            lblNoDataFound = itemView.findViewById(R.id.lblNoDataFound)
            rytSeekbarlayout = itemView.findViewById(R.id.rytSeekbarlayout)
        }
    }

    companion object {
        fun milliSecondsToTimer(milliseconds: Long): String {
            var finalTimerString = ""
            var secondsString = ""
            var minutesString = ""

            // Convert total duration into time
            val hours = (milliseconds / (1000 * 60 * 60)).toInt()
            val minutes = (milliseconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
            val seconds = ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000).toInt()
            // Add hours if there
            if (hours > 0) {
                finalTimerString = "$hours:"
            }

            // Prepending 0 to Minutes if it is one digit
            if (minutes < 10) {
                minutesString = "0$minutes"
            } else {
                minutesString = "" + minutes
            }

            // Prepending 0 to seconds if it is one digit
            if (seconds < 10) {
                secondsString = "0$seconds"
            } else {
                secondsString = "" + seconds
            }
            finalTimerString = "$finalTimerString$minutesString:$secondsString"

            // return timer string
            return finalTimerString
        }
    }

}