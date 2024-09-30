package com.gco.gco.ui.announcements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AnnouncementViewModel : ViewModel() {
    val dummyDataSet = arrayListOf<AnnouncementModel>().apply {
        add(
            AnnouncementModel(
                title = "Lorem ipsum dolor sit amet consectetur. Sit elit at mauris pulvinar.",

                dateData = "5 May 2021, 1:00pm to 2:00pm"
            )
        )
        add(
            AnnouncementModel(
                title = "Lorem ipsum dolor sit amet consectetur. Sit elit at mauris pulvinar.",

                dateData = "5 May 2021, 1:00pm to 2:00pm"
            )
        )
        add(
            AnnouncementModel(
                title = "Lorem ipsum dolor sit amet consectetur. Sit elit at mauris pulvinar.",

                dateData = "5 May 2021, 1:00pm to 2:00pm"
            )
        )
        add(
            AnnouncementModel(
                title = "Lorem ipsum dolor sit amet consectetur. Sit elit at mauris pulvinar.",

                dateData = "5 May 2021, 1:00pm to 2:00pm"
            )
        )
        add(
            AnnouncementModel(
                title = "Lorem ipsum dolor sit amet consectetur. Sit elit at mauris pulvinar.",

                dateData = "5 May 2021, 1:00pm to 2:00pm"
            )
        )
        add(AnnouncementModel(pageMoreToLoad = true))
    }

    private var mutableLiveData: MutableLiveData<ArrayList<AnnouncementModel>> = MutableLiveData()
    val liveData: LiveData<ArrayList<AnnouncementModel>> get() = mutableLiveData
    fun setUpData() {
        val currentList = mutableLiveData.value ?: ArrayList()
        currentList.removeLastOrNull()
        currentList.addAll(dummyDataSet)
        mutableLiveData.value = currentList

    }

}