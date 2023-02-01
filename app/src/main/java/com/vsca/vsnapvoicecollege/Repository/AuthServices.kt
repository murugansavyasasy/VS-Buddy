package com.vsca.vsnapvoicecollege.Repository


import androidx.lifecycle.MutableLiveData
import com.vsca.vsnapvoicecollege.Model.CountryDetailsResponse
import com.vsca.vsnapvoicecollege.Model.VersionCheckResposne
import com.vsca.vsnapvoicecollege.Model.LoginResponse
import android.app.Activity
import android.app.ProgressDialog
import android.util.Log
import com.vsca.vsnapvoicecollege.Utils.CustomLoading
import android.widget.Toast
import org.json.JSONObject
import org.json.JSONException
import androidx.lifecycle.LiveData
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.String

class AuthServices {
    var progressDialog: ProgressDialog? = null

    var client_auth: RestClient
    var countryDetailsMutableLiveData: MutableLiveData<CountryDetailsResponse?>
    var versionCheckResposneMutableLiveData: MutableLiveData<VersionCheckResposne?>
    var LoginResposneMutableLiveData: MutableLiveData<LoginResponse?>
    fun GetCountryList(appid: Int, activity: Activity?) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.Companion.apiInterfaces.Getcountrylist(appid)
            ?.enqueue(object : Callback<CountryDetailsResponse?> {
                override fun onResponse(
                    call: Call<CountryDetailsResponse?>,
                    response: Response<CountryDetailsResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "GetCountryList",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            val status = response.body()!!.status
                            if (status == 1) {
                                countryDetailsMutableLiveData.postValue(response.body())
                            } else {
                                Log.d("Error", String.valueOf(response.body()!!.status))
                                Toast.makeText(
                                    activity,
                                    response.body()!!.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    } else if (response.code() == 400) {
                        progressDialog!!.dismiss()
                        try {
                            val jsonObject = JSONObject(response.errorBody()!!.string())
                            val message = jsonObject.getString("message")
                            Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<CountryDetailsResponse?>, t: Throwable) {
                    progressDialog!!.dismiss()
                    countryDetailsMutableLiveData.postValue(null)
                    t.printStackTrace()
                }
            })
    }

    val countryLiveData: LiveData<CountryDetailsResponse?>
        get() = countryDetailsMutableLiveData

    fun GetVersionCheck(versionid: Int, activity: Activity?) {
        Log.d("VersionCheckApi", "test")
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.Companion.apiInterfaces.VersionCheck(versionid)
            ?.enqueue(object : Callback<VersionCheckResposne?> {
                override fun onResponse(
                    call: Call<VersionCheckResposne?>,
                    response: Response<VersionCheckResposne?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "GetVersionCheck_Res",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            val status = response.body()!!.status
                            if (status == 1) {
                                versionCheckResposneMutableLiveData.postValue(response.body())
                            } else {
                                Log.d("Error", String.valueOf(response.body()!!.status))
                                Toast.makeText(
                                    activity,
                                    response.body()!!.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    } else if (response.code() == 400) {
                        progressDialog!!.dismiss()
                        try {
                            val jsonObject = JSONObject(response.errorBody()!!.string())
                            val message = jsonObject.getString("message")
                            Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<VersionCheckResposne?>, t: Throwable) {
                    progressDialog!!.dismiss()
                    versionCheckResposneMutableLiveData.postValue(null)
                    t.printStackTrace()
                }
            })
    }

    val versionCheckLiveData: LiveData<VersionCheckResposne?>
        get() = versionCheckResposneMutableLiveData

    fun Login(jsonObject: JsonObject?, activity: Activity?) {
        Log.d("LoginApi", "test")
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.Companion.apiInterfaces.Login(jsonObject)
            ?.enqueue(object : Callback<LoginResponse?> {
                override fun onResponse(
                    call: Call<LoginResponse?>,
                    response: Response<LoginResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d("GetLogin_Res", response.code().toString() + " - " + response.toString())
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            val status = response.body()!!.status
//                            if (status == 1) {
                                LoginResposneMutableLiveData.postValue(response.body())
//                            } else {
//                                CommonUtil.ApiAlert(activity,response.body()!!.message)
//
////
////                                Toast.makeText(
////                                    activity,
////                                    response.body()!!.message,
////                                    Toast.LENGTH_LONG
////                                ).show();
//                            }
                        }
                    } else if (response.code() == 400) {
                        progressDialog!!.dismiss()
                        try {
                            val jsonObject = JSONObject(response.errorBody()!!.string())
                            val message = jsonObject.getString("message")
                            Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                    progressDialog!!.dismiss()
                    LoginResposneMutableLiveData.postValue(null)
                    t.printStackTrace()
                }
            })
    }

    val loginResponseLiveData: LiveData<LoginResponse?>
        get() = LoginResposneMutableLiveData

    init {
        client_auth = RestClient()
        countryDetailsMutableLiveData = MutableLiveData()
        versionCheckResposneMutableLiveData = MutableLiveData()
        LoginResposneMutableLiveData = MutableLiveData()
    }
}