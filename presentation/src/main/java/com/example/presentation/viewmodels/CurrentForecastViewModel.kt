package com.example.presentation.viewmodels

import com.example.common.other.Mapper
import com.example.domain.models.current.CurrentForecastDomainModel
import com.example.domain.usecases.CurrentForecastUseCaseArgument
import com.example.domain.usecases.base.BaseUseCase
import com.example.presentation.models.current.CurrentForecastUiModel
import javax.inject.Inject

class CurrentForecastViewModel @Inject constructor(
    private val currentForecastUseCase: BaseUseCase<CurrentForecastDomainModel, CurrentForecastUseCaseArgument>,
    private val mapper: Mapper<CurrentForecastDomainModel, CurrentForecastUiModel>
){

}