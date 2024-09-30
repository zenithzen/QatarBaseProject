package com.gco.gco.ui.archives

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gco.gco.ui.activities.ActivityModel

class ArchiveViewModel : ViewModel() {
    val dummyDataSet = arrayListOf<ActivityModel>().apply {
        add(
            ActivityModel(
                title = "Cybersecurity drills and training of the human aspect",
                category = "Announcement",
                status = "Accepted",
                3,
                locationData = "Ministry of Transport and Communications",
                dateData = "5 May 2021, 1:00pm to 2:00pm"
            )
        )
        add(
            ActivityModel(
                title = "Cybersecurity drills and training of the human aspect",
                category = "Announcement",
                status = "Accepted",
                3,
                locationData = "Ministry of Transport and Communications",
                dateData = "5 May 2021, 1:00pm to 2:00pm"
            )
        )
        add(
            ActivityModel(
                title = "Cybersecurity drills and training of the human aspect",
                category = "Announcement",
                status = "Accepted",
                3,
                locationData = "Ministry of Transport and Communications",
                dateData = "5 May 2021, 1:00pm to 2:00pm"
            )
        )
        add(
            ActivityModel(
                title = "Cybersecurity drills and training of the human aspect",
                category = "Announcement",
                status = "Accepted",
                3,
                locationData = "Ministry of Transport and Communications",
                dateData = "5 May 2021, 1:00pm to 2:00pm"
            )
        )
        add(
            ActivityModel(
                title = "Cybersecurity drills and training of the human aspect",
                category = "Announcement",
                status = "Accepted",
                3,
                locationData = "Ministry of Transport and Communications",
                dateData = "5 May 2021, 1:00pm to 2:00pm"
            )
        )
        add(ActivityModel(pageMoreToLoad = true))
    }

    private var mutableLiveData: MutableLiveData<ArrayList<ActivityModel>> = MutableLiveData()
    val liveData: LiveData<ArrayList<ActivityModel>> get() = mutableLiveData
    fun setUpData() {
        val currentList = mutableLiveData.value ?: ArrayList()
        currentList.removeLastOrNull()
        currentList.addAll(dummyDataSet)
        mutableLiveData.value = currentList

    }

}