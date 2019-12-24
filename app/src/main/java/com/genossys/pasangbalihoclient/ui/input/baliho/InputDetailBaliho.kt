package com.genossys.pasangbalihoclient.ui.input.baliho

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.genossys.pasangbalihoclient.R
import com.genossys.pasangbalihoclient.ui.adapter.AdapterBaliho
import com.genossys.pasangbalihoclient.ui.baliho.BalihoViewModel
import com.genossys.pasangbalihoclient.ui.baliho.BalihoViewModelFactory
import com.genossys.pasangbalihoclient.utils.GenosDialog
import com.genossys.pasangbalihoclient.utils.SimpleListener
import com.google.android.material.button.MaterialButton
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class InputDetailBaliho : AppCompatActivity(), SimpleListener, KodeinAware, GenosDialog {

    override val kodein by kodein()
    private val factory: BalihoViewModelFactory by instance()
    lateinit var viewModel: BalihoViewModel
    private lateinit var balihoAdapter: AdapterBaliho

    lateinit var btnSimpan: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_detail_baliho)

        viewModel = ViewModelProviders.of(this, factory).get(BalihoViewModel::class.java)
        viewModel.listener = this

        initCompoent()
        initButton()
    }

    private fun initCompoent(){
        btnSimpan = findViewById(R.id.button_simpan)
    }

    private fun initButton(){
        btnSimpan.setOnClickListener {
            Intent(this, InputLokasiBaliho::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onStarted() {

    }

    override fun onSuccess() {
    }

    override fun onEmpty() {
    }

    override fun onTimeOut(message: String) {

    }

    override fun onFailure(message: String) {

    }

    override fun action() {

    }
}
