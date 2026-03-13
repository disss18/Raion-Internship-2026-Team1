package com.example.mbg.feature.allergy.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.feature.allergy.data.AllergyRepository
import com.example.mbg.feature.allergy.domain.model.AllergyModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AllergyViewModel : ViewModel() {

    private val repository = AllergyRepository()

    private val _allergyList =
        MutableStateFlow<List<AllergyModel>>(emptyList())

    val allergyList: StateFlow<List<AllergyModel>>
        get() = _allergyList


    fun insertAllergy(
        school: String,
        allergyName: String,
        total: Int
    ) {

        viewModelScope.launch {

            repository.insertAllergy(

                AllergyModel(
                    schoolName = school,
                    allergyName = allergyName,
                    totalStudent = total
                )
            )

            loadAllergy()
        }
    }

    fun loadAllergy() {

        viewModelScope.launch {

            _allergyList.value =
                repository.getAllergySummary()
        }
    }
}