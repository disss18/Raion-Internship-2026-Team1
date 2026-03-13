package com.example.mbg.feature.feedback.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.feature.feedback.data.AllergyRepository
import com.example.mbg.feature.feedback.data.MBGRepository
import com.example.mbg.feature.feedback.domain.model.AllergyModel
import com.example.mbg.feature.feedback.domain.model.MBGNeedModel
import kotlinx.coroutines.launch

class SchoolInputViewModel : ViewModel() {

    private val allergyRepository = AllergyRepository()
    private val mbgRepository = MBGRepository()

    fun insertAllergy(
        school: String,
        allergy: String,
        total: Int
    ) {

        viewModelScope.launch {

            allergyRepository.insertAllergy(

                AllergyModel(
                    schoolName = school,
                    allergyName = allergy,
                    totalStudent = total
                )
            )
        }
    }

    fun insertMBGNeed(
        school: String,
        total: Int
    ) {

        viewModelScope.launch {

            mbgRepository.insertMBGNeed(

                MBGNeedModel(
                    schoolName = school,
                    totalStudent = total
                )
            )
        }
    }
}