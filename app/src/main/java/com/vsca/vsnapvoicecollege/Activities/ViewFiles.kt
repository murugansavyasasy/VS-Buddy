package com.vsca.vsnapvoicecollege.Activities

import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import com.vsca.vsnapvoicecollege.Utils.DownloadImage
import java.io.File

class
ViewFiles : AppCompatActivity() {

    @JvmField
    @BindView(R.id.imgFile)
    var imgFile: ImageView? = null


    private var imagePathList = ArrayList<String>()
    var Filepath: String? = ""
    var storagepath: String? = null
    var PlayPath: String? = null
    var detailsid: String? = null

    private val VOICE_FOLDER: String? = "Gradit/Images/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_files)
        ButterKnife.bind(this)

        Filepath = intent.getStringExtra("images")!!

        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(this)
            .load(Filepath)
            .placeholder(circularProgressDrawable)
            .into(imgFile!!)


    }
    @OnClick(R.id.imgClose)
    fun imgcloseClick() {
        Log.d("testclick", "test")
        onBackPressed();
    }
    @OnClick(R.id.Fabdownload)
    fun donloadClick() {
        ImageDownloadlocalstorage()
    }

    private fun ImageDownloadlocalstorage() {

        val filepath: String
        filepath = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + File.separator + "Gradit/" + "Images/" + Filepath!!.substring(
                Filepath!!.lastIndexOf('/') + 1
            )
        } else {
            Environment.getExternalStorageDirectory()
                .toString() + File.separator + "Gradit/" + "Images/" + Filepath!!.substring(
                Filepath!!.lastIndexOf(
                    '/'
                ) + 1
            )
        }
        val fileName: String = Filepath!!.substring(Filepath!!.lastIndexOf('/') + 1, Filepath!!.length)
        val fileDir = File(filepath)
        if (!fileDir.exists()) {
            DownloadImage.downloadSampleFile(
                this,
                Filepath!!,
                VOICE_FOLDER!!,
                fileName
            )
        } else {
            CommonUtil.ApiAlertFinish(this, "File Already exists...!")
        }

    }
}