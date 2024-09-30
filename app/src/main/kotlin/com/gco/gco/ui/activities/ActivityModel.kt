package com.gco.gco.ui.activities

data class ActivityModel(
    var title: String? = null,
    var category: String? = null,
    var status: String? = null,
    val attachment: Int? = null,
    var locationData: String? = null,
    var dateData: String? = null,
    var pageMoreToLoad:Boolean=false
)