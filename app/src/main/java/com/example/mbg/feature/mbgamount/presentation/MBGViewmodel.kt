package com.example.mbg.feature.mbg.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.feature.mbg.data.MBGRepository
import com.example.mbg.feature.mbg.domain.model.MBGNeedModel
import kotlinx.coroutines.launch

class MBGViewModel : ViewModel() {

    private val repository = MBGRepository()

    fun insertMBGNeed(
        school: String,
        total: Int
    ) {

        viewModelScope.launch {

            repository.insertMBGNeed(

                MBGNeedModel(
                    schoolName = school,
                    totalStudent = total
                )
            )
        }
    }
}