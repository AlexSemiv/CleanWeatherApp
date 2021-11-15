package com.example.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.presentation.livedata.InternetConnectionLiveData
import javax.inject.Inject

class InternetConnectionViewModel @Inject constructor(
    val internetConnectionLiveData: InternetConnectionLiveData
) : ViewModel()