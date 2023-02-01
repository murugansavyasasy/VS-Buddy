package com.vsca.vsnapvoicecollege.ActivitySender

import android.app.ActionBar
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.os.SystemClock
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Activities.BaseActivity
import com.vsca.vsnapvoicecollege.Activities.ChatCommunication
import com.vsca.vsnapvoicecollege.Activities.ChatParent
import com.vsca.vsnapvoicecollege.Adapters.ChatStaffAdapter
import com.vsca.vsnapvoicecollege.Adapters.VideoAdapter
import com.vsca.vsnapvoicecollege.Adapters.VideoContentAdapter
import com.vsca.vsnapvoicecollege.Interfaces.ApiInterfaces
import com.vsca.vsnapvoicecollege.Interfaces.ChatListener
import com.vsca.vsnapvoicecollege.Model.GetStaffDetailsData
import com.vsca.vsnapvoicecollege.Model.VideoRestrictionData
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import com.vsca.vsnapvoicecollege.VideoAlbum.AlbumVideoSelectVideoActivity
import com.vsca.vsnapvoicecollege.VideoAlbum.Video
import com.vsca.vsnapvoicecollege.ViewModel.App
import com.vsca.vsnapvoicecollege.ViewModel.Dashboards
import com.vsca.vsnapvoicecollege.albumImage.AlbumSelectActivity
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*
import java.util.concurrent.TimeUnit


class AddVideo : AppCompatActivity() {
    @JvmField
    @BindView(R.id.LayoutUploadVideo)
    var LayoutUploadVideo: ConstraintLayout? = null

    @JvmField
    @BindView(R.id.txtTitle)
    var txtTitle: EditText? = null

    @JvmField
    @BindView(R.id.txtDescription)
    var txtDescription: EditText? = null

    private val REQUEST = 1
    private val SELECT_VIDEO = 2

    var appViewmodel: App? = null
    var GetContentData: ArrayList<VideoRestrictionData>? = null
    var videoAdapter: VideoContentAdapter? = null
    var videoContentlist = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        CommonUtil.SetTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_video)
        ButterKnife.bind(this)

        appViewmodel = ViewModelProvider(this).get(App::class.java)
        appViewmodel!!.init()

        appViewmodel!!.VideoRestrictContentMutableLiveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message
                BaseActivity.UserMenuRequest(this)
                if (status == 1) {
                    GetContentData = response.data!!
                    for (i in GetContentData!!.indices) {
                        var content = GetContentData!!.get(i).content
                        videoContentlist.add(content!!)
                    }
                    ShowVideoRestrictionPopUp()
                } else {
                    CommonUtil.ApiAlert(this,message)
                }
            } else {
                CommonUtil.ApiAlert(this,"Something Went Wrong")
            }
        }
    }

    private fun ShowVideoRestrictionPopUp() {

        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout: View = inflater.inflate(R.layout.popup_video_content_ui, null)
        val popupWindow = PopupWindow(
            layout,
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.MATCH_PARENT,
            true
        )
        popupWindow.setContentView(layout)
        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0)
        val ryccontent = layout.findViewById<View>(R.id.rycContent) as RecyclerView
        val btnAgree = layout.findViewById<View>(R.id.btnAgree) as Button
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        ryccontent.layoutManager = layoutManager
        ryccontent.itemAnimator = DefaultItemAnimator()
        videoAdapter = VideoContentAdapter(videoContentlist, this)
        ryccontent.adapter = videoAdapter
        //        lblContent.setText(content);
        btnAgree.setOnClickListener {
            popupWindow.dismiss()

            val intent1 = Intent(this, AlbumVideoSelectVideoActivity::class.java)
            intent1.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(intent1, SELECT_VIDEO)        }
    }

    @OnClick(R.id.LayoutUploadVideo)
    fun OpenGallery() {
        appViewmodel!!.VideoRestrictionContent(this)


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_CANCELED) {
            if (requestCode == SELECT_VIDEO) {
                if (data != null) {
                    var SelcetedFileList = data.getStringArrayListExtra("images")!!
                    var VideoFilePath = SelcetedFileList.get(0)
                    Log.d("VideoFilePath", VideoFilePath)

                }
            }
        }
    }


    fun VimeoVideoUpload(activity: Activity, file: File) {
        val strsize = file.length()
        Log.d("size", strsize.toString())
        val clientinterceptor = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        clientinterceptor.interceptors().add(interceptor)
        val client1: OkHttpClient
        client1 = OkHttpClient.Builder()
            .connectTimeout(300, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .build()
        val retrofit = Retrofit.Builder()
            .client(client1)
            .baseUrl("https://api.vimeo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: ApiInterfaces =
            retrofit.create(ApiInterfaces::class.java)
        val mProgressDialog = ProgressDialog(activity)
        mProgressDialog.isIndeterminate = true
        mProgressDialog.setMessage("Connecting...")
        mProgressDialog.setCancelable(false)
        mProgressDialog.show()
        val `object` = JsonObject()
        val jsonObjectclasssec = JsonObject()
        jsonObjectclasssec.addProperty("approach", "post")
        jsonObjectclasssec.addProperty("size", strsize.toString())
        val jsonprivacy = JsonObject()
        jsonprivacy.addProperty("view", "unlisted")
        val jsonshare = JsonObject()
        jsonshare.addProperty("share", "false")
        val jsonembed = JsonObject()
        jsonembed.add("buttons", jsonshare)


        `object`.add("upload", jsonObjectclasssec)
        `object`.addProperty("name", txtTitle!!.text.toString())
        `object`.addProperty("description", txtDescription!!.text.toString())
        `object`.add("privacy", jsonprivacy)
        `object`.add("embed", jsonembed)
        val head =
            "Bearer " + "8d74d8bf6b5742d39971cc7d3ffbb51a"
        Log.d("header", head)
        val call: Call<JsonObject> = service.VideoUpload(`object`, head)
        Log.d("jsonOBJECT", `object`.toString())
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                if (mProgressDialog.isShowing) mProgressDialog.dismiss()
                val res = response.code()
                Log.d("RESPONSE", res.toString())
                if (response.isSuccessful) {
                    try {
                        Log.d("try", "testtry")
                        val object1 = JSONObject(response.body().toString())
                        Log.d("Response sucess", "response entered success")
                        val obj = object1.getJSONObject("upload")
                        val obj1 = object1.getJSONObject("embed")
                        val upload_link = obj.getString("upload_link")
                        val link = object1.getString("link")
                        val iframe = obj1.getString("html")
                        Log.d("c", upload_link)
                        Log.d("iframe", iframe)
//
//                        UtilConstants.VimeoIframe = iframe
//                        UtilConstants.VimeoVideoUrl = link
                        try {
                            VIDEOUPLOAD(upload_link, file, activity)
                        } catch (e: Exception) {
                            Log.e("VIMEO Exception", e.message!!)
                            CommonUtil.Toast(activity, "Video sending failed.Retry")
                        }
                    } catch (e: Exception) {
                        Log.e("VIMEO Exception", e.message!!)
                        CommonUtil.Toast(activity, e.message)
                    }
                } else {
                    Log.d("Response fail", "fail")
                    CommonUtil.Toast(activity, "Video sending failed.Retry")
                }
            }

            override fun onFailure(
                call: Call<JsonObject>,
                t: Throwable
            ) {
                if (mProgressDialog.isShowing) mProgressDialog.dismiss()
                Log.e("Response Failure", t.message!!)
                CommonUtil.Toast(activity, "Video sending failed.Retry")
            }
        })
    }


    private fun VIDEOUPLOAD(
        upload_link: String,
        file: File,
        activity: Activity
    ) {
        val separated =
            upload_link.split("?").toTypedArray()

        val name = separated[0] //"/"
        val FileName = separated[1]
        val upload = name.replace("upload", "")
        val id = FileName.split("&").toTypedArray()
        val ticket_id = id[0]
        val video_file_id = id[1]
        val signature = id[2]
        val v6 = id[3]
        val redirect_url = id[4]
        val seperate1: Array<String> = ticket_id.split("=").toTypedArray()
        val ticket = seperate1[0] //"/"
        val ticket2 = seperate1[1]
        val seperate2: Array<String> = video_file_id.split("=").toTypedArray()
        val ticket1 = seperate2[0] //"/"
        val ticket3 = seperate2[1]
        val seper: Array<String> = signature.split("=").toTypedArray()
        val ticke = seper[0] //"/"
        val tick = seper[1]
        val sepera: Array<String> = v6.split("=").toTypedArray()
        val str = sepera[0] //"/"
        val str1 = sepera[1]
        val sucess: Array<String> = redirect_url.split("=").toTypedArray()
        val urlRIDERCT = sucess[0] //"/"
        val redirect_url123 = sucess[1]
        val client1: OkHttpClient
        client1 = OkHttpClient.Builder()
            .connectTimeout(600, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.MINUTES)
            .writeTimeout(40, TimeUnit.MINUTES)
            .build()
        val retrofit = Retrofit.Builder()
            .client(client1)
            .baseUrl(upload)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val mProgressDialog = ProgressDialog(activity)
        mProgressDialog.isIndeterminate = true
        mProgressDialog.setMessage("Uploading...")
        mProgressDialog.setCancelable(false)
        mProgressDialog.show()
        val service: ApiInterfaces =
            retrofit.create(ApiInterfaces::class.java)
        var requestFile: RequestBody? = null
        try {
            val filepath = file.toString()
            Log.d("filepath", filepath)
            val `in`: InputStream = FileInputStream(filepath)
            val buf: ByteArray
            buf = ByteArray(`in`.available())
            while (`in`.read(buf) != -1);
            requestFile = RequestBody.create(
                "application/offset+octet-stream".toMediaTypeOrNull(),
                buf
            )
        } catch (e: IOException) {
            e.printStackTrace()
            CommonUtil.Toast(activity, e.message)
        }
        val call: Call<ResponseBody> = service.patchVimeoVideoMetaData(
            ticket2,
            ticket3,
            tick,
            str1,
            redirect_url123 + "www.voicesnapforschools.com",
            requestFile
        )
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                if (mProgressDialog.isShowing) mProgressDialog.dismiss()
                try {
                    if (response.isSuccessful) {


                    } else {
                        CommonUtil.Toast(activity, "Video sending failed.Retry")
                    }
                } catch (e: Exception) {
                    CommonUtil.Toast(activity, e.message)
                }
            }

            override fun onFailure(
                call: Call<ResponseBody?>,
                t: Throwable
            ) {
                if (mProgressDialog.isShowing) mProgressDialog.dismiss()
                CommonUtil.Toast(activity, "Video sending failed.Retry")
            }
        })
    }

}