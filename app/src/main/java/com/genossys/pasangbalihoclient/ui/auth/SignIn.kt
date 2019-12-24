package com.genossys.pasangbalihoclient.ui.auth

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import com.genossys.pasangbalihoclient.R
import com.genossys.pasangbalihoclient.data.db.entity.Client
import com.genossys.pasangbalihoclient.ui.link.Link
import com.genossys.pasangbalihoclient.utils.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.*
import kotlin.concurrent.schedule

class SignIn : AppCompatActivity(), AuthListener, KodeinAware, GenosDialog {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    lateinit var viewModel: AuthViewModel
    lateinit var rootLayout: ConstraintLayout
    lateinit var editEmail: TextInputEditText
    lateinit var editPassword: TextInputEditText
    lateinit var buttonSignIn: MaterialButton
    lateinit var buttonSignUp: MaterialButton
    lateinit var syaratDanKetentuan: TextView
    lateinit var pgb: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        viewModel.authListener = this

        initComponent()
        initButton()
    }

    private fun initComponent() {
        editEmail = findViewById(R.id.edit_email)
        editPassword = findViewById(R.id.edit_password)
        buttonSignIn = findViewById(R.id.button_sign_in)
        syaratDanKetentuan = findViewById(R.id.syaratdanketentuan)
//        buttonSignUp = findViewById(R.id.button_sign_up)
        rootLayout = findViewById(R.id.root_layout)
        pgb = findViewById(R.id.progressbar)

        Coroutines.main {
            pgb.visibility = View.GONE
        }
    }

    private fun initButton() {
        buttonSignIn.setOnClickListener {

            if (isValidEmail(editEmail.text.toString())) {
                if (editPassword.text.toString().isNotEmpty()) {
                    Coroutines.main {

                        Coroutines.main {
                            FirebaseInstanceId.getInstance().instanceId
                                .addOnCompleteListener(OnCompleteListener { task ->
                                    if (!task.isSuccessful) {
                                        Log.w(
                                            ContentValues.TAG,
                                            "getInstanceId failed",
                                            task.exception
                                        )
                                        return@OnCompleteListener
                                    }
                                    val token = task.result?.token
                                    Log.d(ContentValues.TAG, token!!)
                                    viewModel.loginClient(
                                        editEmail.text.toString(),
                                        editPassword.text.toString(),
                                        token
                                    )
                                })
                        }
                    }
                } else {
                    dialogHandler(
                        this,
                        "Error",
                        "Field password belum di isi",
                        R.drawable.ic_notif,
                        "pesan"
                    )
                }
            } else {
                dialogHandler(
                    this,
                    "Error",
                    "Email belum terisi dengan benar",
                    R.drawable.ic_notif,
                    "pesan"
                )
            }
        }

        syaratdanketentuan.setOnClickListener {
            Intent(this, Link::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                it.putExtra("tittle", "Syarat dan Ketentuan")
                it.putExtra("link", "https://www.pasangbaliho.com/syarat-dan-ketentuan")
                startActivity(it)
            }
        }
    }


    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }


    override fun onStarted() {
        Coroutines.main {
            pgb.visibility = View.VISIBLE
        }
    }

    override fun onSuccess(client: Client) {
        Coroutines.main {
            dialogHandler(
                this,
                "Login Berhasil",
                "Selamat datang ${client.nama}",
                R.drawable.ic_welcome,
                "sukses"
            )

            Timer("SettingUp", false).schedule(1000) {
                keSplashScreen()
            }
            pgb.visibility = View.GONE
        }


    }

    override fun onFailure(message: String) {
        Coroutines.main {
            pgb.visibility = View.GONE
            if (message == ERROR_INTERNET) {
                dialogHandler(
                    this,
                    "Internet Error",
                    ERROR_INTERNET,
                    R.drawable.ic_nointernet,
                    "pesan"
                )
            } else {
                dialogHandler(this, "Error", "Email dan password tidak terdaftar", R.drawable.ic_nointernet, "pesan")
            }
        }
    }

    override fun action() {

    }
}
