package com.sprtcoding.salita.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sprtcoding.salita.contract.IEasyContract

class EasyViewModel(application: Application) : AndroidViewModel(application) {
    private val _score = MutableLiveData<Result<Int>>()
    val score: LiveData<Result<Int>> get() = _score


}