package com.vsca.vsnapvoicecollege.ViewModel


import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.JsonObject
import android.app.Activity
import android.app.Application
import com.vsca.vsnapvoicecollege.Repository.AuthServices
import com.vsca.vsnapvoicecollege.Model.CountryDetailsResponse
import com.vsca.vsnapvoicecollege.Model.VersionCheckResposne
import com.vsca.vsnapvoicecollege.Model.LoginResponse
import com.vsca.vsnapvoicecollege.Utils.CommonUtil.Appid
import com.vsca.vsnapvoicecollege.Utils.CommonUtil.VersionId

class Auth(application: Application) : AndroidViewModel(application) {
    private var apiRepositories: AuthServices? = null
    var countryDetailsResponseLiveData: LiveData<CountryDetailsResponse?>? = null
        private set
    var versionCheckLiveData: LiveData<VersionCheckResposne?>? = null
        private set
    var loginResposneLiveData: LiveData<LoginResponse?>? = null
        private set

    fun init() {
        apiRepositories = AuthServices()
        countryDetailsResponseLiveData = apiRepositories!!.countryLiveData
        versionCheckLiveData = apiRepositories!!.versionCheckLiveData
        loginResposneLiveData = apiRepositories!!.loginResponseLiveData
    }
    fun getcountryList(activity: Activity?) {
        apiRepositories!!.GetCountryList(Appid, activity)
    }

    fun getVersionCheck(activity: Activity?) {

        apiRepositories!!.GetVersionCheck(VersionId, activity)
    }
    fun login(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.Login(jsonObject, activity)
    }
}