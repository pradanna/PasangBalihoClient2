package com.genossys.pasangbalihoclient.ui.input.baliho

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.genossys.pasangbalihoclient.R
import com.google.android.material.button.MaterialButton

class InputLokasiBaliho : AppCompatActivity() {

    lateinit var btnSimpan: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_lokasi_baliho)
        initCompoent()
        initButton()
    }

    private fun initCompoent() {
        btnSimpan = findViewById(R.id.button_simpan)
    }

    private fun initButton() {
        btnSimpan.setOnClickListener {
            Intent(this, InputFotoBaliho::class.java).also {
                startActivity(it)
            }
        }
    }
}
