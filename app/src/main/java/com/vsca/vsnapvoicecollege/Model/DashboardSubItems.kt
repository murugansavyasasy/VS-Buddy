package com.vsca.vsnapvoicecollege.Model

import java.util.ArrayList

class DashboardSubItems {
    var menuTitle: String? = null
    var menuDescription: String? = null
    var createDate: String? = null
    var createTime: String? = null
    var createBy: String? = null
    var addImage: String? = null
    var type: String? = null
    var adBaackgroundImage: String? = null
    var voiceFilepath: String? = null
    var membername: String? = null
    var duration = 0
    var createdon: String? = null
    var RecentType: String? = null
    var Content: String? = null
    var SubjectName: String? = null
    var AttendanceType: String? = null
    var AttendanceDate: String? = null
    var MsgId: String? = null
    var FilepathList = ArrayList<String>()

    constructor(addImage: String?, adBaackgroundImage: String?) {
        this.addImage = addImage
        this.adBaackgroundImage = adBaackgroundImage
    }

    constructor(
        menuTitle: String?,
        menuDescription: String?,
        createDate: String?,
        createTime: String?,
        filepath: ArrayList<String>
    ) {
        this.menuTitle = menuTitle
        this.menuDescription = menuDescription
        this.createDate = createDate
        this.createTime = createTime
        this.FilepathList = filepath
    }

    constructor(
        menuTitle: String?,
        addimage: String?,
        menuDescription: String?,
        createdate: String?,
        createtime: String?,
        createby: String?,
        type: String?
    ) {
        this.menuTitle = menuTitle
        this.menuDescription = menuDescription
        createDate = createdate
        createTime = createtime
        createBy = createby
        this.type = type
    }

    constructor(
        menuDescription: String?,
        membername: String?,
        typ: String?,
        createdon: String?,
        createTime: String?,
        content: String?,
        duartion: Int,
        type: String?,
        detailsID: String?
    ) {
        this.menuDescription = menuDescription
        this.membername = membername
        RecentType = typ
        this.createTime = createTime
        this.createdon = createdon
        Content = content
        duration = duartion
        this.type = type
        MsgId = detailsID
    }

    constructor(
        description: String?,
        voiceFilepath: String?,
        membername: String?,
        duration: Int,
        createdon: String?,
        type: String?,
        detailsid: String?
    ) {
        menuTitle = description
        this.voiceFilepath = voiceFilepath
        this.membername = membername
        this.duration = duration
        this.createdon = createdon
        this.type = type
        this.MsgId = detailsid
    }
    constructor(
       subjectName: String?,
        attendanceType: String?,
        attendancedate: String?,
    ) {
        this.SubjectName = subjectName
        this.AttendanceType = attendanceType
        this.AttendanceDate = attendancedate

    }


}