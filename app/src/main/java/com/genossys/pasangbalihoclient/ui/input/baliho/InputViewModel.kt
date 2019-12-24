package com.genossys.pasangbalihoclient.ui.input.baliho

import androidx.lifecycle.ViewModel
import com.genossys.pasangbalihoclient.data.db.entity.InputBaliho
import com.genossys.pasangbalihoclient.data.db.repository.BalihoRepository
import com.genossys.pasangbalihoclient.utils.SimpleListener
import kotlinx.coroutines.Job

class InputViewModel(
    private val repository: BalihoRepository
) : ViewModel() {

    var listener: SimpleListener? = null
    lateinit var job: Job

    fun insertDetailBaliho(inputBaliho: InputBaliho){
        job = Job()
        listener?.onStarted()
    }

}