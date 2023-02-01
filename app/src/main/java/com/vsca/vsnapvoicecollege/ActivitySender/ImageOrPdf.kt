package com.vsca.vsnapvoicecollege.ActivitySender

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.webkit.WebView
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.vsca.vsnapvoicecollege.AWS.S3Uploader
import com.vsca.vsnapvoicecollege.AWS.S3Utils
import com.vsca.vsnapvoicecollege.Model.AWSUploadedFiles
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import com.vsca.vsnapvoicecollege.Utils.CommonUtil.SelcetedFileList
import com.vsca.vsnapvoicecollege.Utils.CustomLoading
import com.vsca.vsnapvoicecollege.ViewModel.App
import com.vsca.vsnapvoicecollege.albumImage.AlbumSelectActivity
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ImageOrPdf : AppCompatActivity() {

    @JvmField
    @BindView(R.id.LayoutUploadImagePdf)
    var LayoutUploadImagePdf: ConstraintLayout? = null

    @JvmField
    @BindView(R.id.btnConfirm)
    var btnConfirm: Button? = null

    var FilePopup: PopupWindow? = null
    val REQUEST_Camera = 1
    val REQUEST_GAllery = 2
    val SELECT_PDF = 8778
    var imageFilePath: String? = null
    var PDFTempFileWrite: File? = null
    var photoTempFileWrite: File? = null
    var photoURI: Uri? = null
    var outputDir: File? = null

    var AWSUploadedFilesList = ArrayList<AWSUploadedFiles>()
    var fileNameDateTime: String? = null
    var urlFromS3: String? = null
    var uploadFilePath: String? = null
    var contentType: String? = null
    var pathIndex = 0
    var fileName: File? = null
    var filename: String? = null
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_or_pdf)
        CommonUtil.SetTheme(this)

        ButterKnife.bind(this)


    }

    @OnClick(R.id.LayoutUploadImagePdf)
    fun ChooseFile() {

        Log.d("popup", "test")
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialog: View = inflater.inflate(R.layout.popup_choose_file, null)
        FilePopup = PopupWindow(
            dialog,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            true
        )
        FilePopup?.showAtLocation(dialog, Gravity.BOTTOM, 0, 0)
        FilePopup?.setContentView(dialog)
        FilePopup?.setOutsideTouchable(true)
        FilePopup?.setFocusable(true)

        val LayoutGallery = dialog.findViewById<ConstraintLayout>(R.id.LayoutGallery)
        val LayoutCamera = dialog.findViewById<ConstraintLayout>(R.id.LayoutCamera)
        val LayoutDocuments = dialog.findViewById<ConstraintLayout>(R.id.LayoutDocuments)
        val popClose = dialog.findViewById<ImageView>(R.id.popClose)

        val container = FilePopup?.getContentView()?.getParent() as View
        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val p = container.layoutParams as WindowManager.LayoutParams
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        p.dimAmount = 0.7f
        wm.updateViewLayout(container, p)


        popClose.setOnClickListener {
            FilePopup?.dismiss()
        }
        LayoutGallery.setOnClickListener {
            val intent1 = Intent(this, AlbumSelectActivity::class.java)
            intent1.putExtra("Gallery", "Images")
            startActivityForResult(intent1, REQUEST_GAllery)
            FilePopup!!.dismiss()
        }
        LayoutCamera.setOnClickListener {

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                photoTempFileWrite = createImageFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (photoTempFileWrite != null) {
                photoURI = FileProvider.getUriForFile(
                    this,
                    "com.vsca.vsnapvoicecollege.provider",
                    photoTempFileWrite!!
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(intent, REQUEST_Camera)
                FilePopup?.dismiss()
            }

        }
        LayoutDocuments.setOnClickListener({
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(intent, SELECT_PDF)
            FilePopup!!.dismiss()

        })

    }

    @OnClick(R.id.imgImagePdfback)
    fun backClick() {
        onBackPressed()
    }

    @OnClick(R.id.btnConfirm)
    fun btnConfirmClick() {
        awsFileUpload(this, pathIndex)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == REQUEST_Camera) {
                SelcetedFileList.add(imageFilePath!!)

                Log.d("imageFilePath", imageFilePath.toString())

                SelcetedFileList.forEach {
                    var path = it

                }

            } else if (requestCode == REQUEST_GAllery) {
                if (data != null) {
                    SelcetedFileList = data.getStringArrayListExtra("images")!!
                    Log.d("SelectedFileListSize", SelcetedFileList.size.toString())
                    SelcetedFileList.forEach {
                        var path = it
                    }

                }
            } else if (requestCode == SELECT_PDF && resultCode == AppCompatActivity.RESULT_OK && data != null) {
                if (data!!.clipData != null) {
                    val mClipData = data.clipData
                    val mArrayUri = ArrayList<Uri>()

                    for (i in 0 until mClipData!!.itemCount) {
                        val item = mClipData!!.getItemAt(i)
                        val uri = item.uri
                        mArrayUri.add(uri)

                        Log.d("marrayuri", mArrayUri.size.toString())
                        outputDir = this!!.externalCacheDir!!
                        ReadAndWriteFile(uri, ".pdf")


                    }

                }

//                else if (data!!.data != null) {
//                    UtilConstants.Apifiletype = UtilConstants.filetypePdf
//                    val fileuri = data!!.data
//                    outputDir = activity!!.externalCacheDir
//
//                    ReadAndWriteFile(fileuri, ".pdf")
//                }
            }
        }

    }


    @Throws(IOException::class)
    fun createImageFile(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "IMG_" + timeStamp + "_"
        val storageDir: File? = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
        imageFilePath = image.absolutePath
        return image
    }

    fun ReadAndWriteFile(uri: Uri?, type: String) {
        try {
            uri?.let {
                this!!.contentResolver?.openInputStream(it).use { `in` ->
                    if (`in` == null) return
                    try {
                        PDFTempFileWrite = File.createTempFile("File_", type, outputDir)
                        var pdfPath: String = PDFTempFileWrite?.path!!
                        CommonUtil.extension =
                            pdfPath.substring(pdfPath.toString().lastIndexOf("."))
                        Log.d("extensionpdf", CommonUtil.extension!!)
                        Log.d("PDFTempFileWrite", PDFTempFileWrite.toString()!!)
                        CommonUtil.SelcetedFileList.add(pdfPath)
                        CommonUtil.SelcetedFileList.forEach {
                            var path = it
                            Log.d("test", path)
//                            CommonUtil.pathlist.add(SelectedFilesClass(path,
//                                UtilConstants.filetypePdf
//                            ))
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    this!!.contentResolver?.openOutputStream(Uri.fromFile(PDFTempFileWrite))
                        .use { out ->
                            if (out == null) return
                            val buf = ByteArray(1024)
                            var len = 0
                            while (true) {
                                try {
                                    if (`in`.read(buf).also({ len = it }) <= 0) break
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                }
                                try {
                                    out.write(buf, 0, len)
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                }
                            }
                        }
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun awsFileUpload(activity: Activity?, pathind: Int?) {

        Log.d("SelcetedFileList", SelcetedFileList.size.toString())
        val s3uploaderObj: S3Uploader
        s3uploaderObj = S3Uploader(activity)
        pathIndex = pathind!!

        for (index in pathIndex until SelcetedFileList.size) {
            uploadFilePath = SelcetedFileList.get(index)
            var extension = uploadFilePath!!.substring(uploadFilePath!!.lastIndexOf("."))
            if (extension.equals(".pdf")) {
                contentType = ".pdf"
            } else {
                contentType = ".jpg"
            }
            break

        }
        if (AWSUploadedFilesList.size < SelcetedFileList.size) {
            Log.d("test", uploadFilePath!!)
            if (uploadFilePath != null) {
                progressDialog = CustomLoading.createProgressDialog(this)

                progressDialog!!.show()
                fileNameDateTime = SimpleDateFormat("yyyyMMddHHmmss").format(
                    Calendar.getInstance().getTime()
                )
                fileNameDateTime = "File_" + fileNameDateTime
                s3uploaderObj.initUpload(
                    uploadFilePath,
                    contentType,
                    CommonUtil.CollegeId.toString(),
                    fileNameDateTime
                )
                s3uploaderObj!!.setOns3UploadDone(object : S3Uploader.S3UploadInterface {
                    override fun onUploadSuccess(response: String?) {
                        if (response!!.equals("Success")) {
                            urlFromS3 = S3Utils.generates3ShareUrl(
                                this@ImageOrPdf,
                                CommonUtil.CollegeId.toString(), uploadFilePath, fileNameDateTime
                            )
                            if (!TextUtils.isEmpty(urlFromS3)) {

                                fileName = File(uploadFilePath)
                                filename = fileName!!.name
                                AWSUploadedFilesList.add(
                                    AWSUploadedFiles(
                                        urlFromS3!!,
                                        filename,
                                        contentType
                                    )
                                )
                                awsFileUpload(activity, pathIndex + 1)

                                if (SelcetedFileList.size == AWSUploadedFilesList.size) {
                                    progressDialog!!.dismiss()

                                    Log.d("AwsFileList",AWSUploadedFilesList.get(0).filepath)

                                }
                            }
                        }
                    }
                    override fun onUploadError(response: String?) {
                        progressDialog!!.dismiss()
                        Log.d("error", "Error Uploading")
                    }
                })

            }
        }
    }


}