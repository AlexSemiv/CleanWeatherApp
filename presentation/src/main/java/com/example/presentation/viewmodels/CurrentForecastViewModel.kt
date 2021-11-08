package com.example.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.common.base.BaseViewModel
import com.example.common.other.Mapper
import com.example.domain.models.current.CurrentForecastDomainModel
import com.example.domain.usecases.GetCurrentForecastUseCase
import com.example.presentation.models.current.CurrentForecastUiModel
import javax.inject.Inject

class CurrentForecastViewModel @Inject constructor(
    private val currentForecastUseCase: GetCurrentForecastUseCase,
    private val mapper: Mapper<CurrentForecastDomainModel, CurrentForecastUiModel>
): ViewModel() {

}