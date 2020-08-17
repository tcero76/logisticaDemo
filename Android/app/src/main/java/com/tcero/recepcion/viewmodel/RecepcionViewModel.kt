package com.tcero.recepcion.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecepcionViewModel: ViewModel() {

    val cantidad: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }
}