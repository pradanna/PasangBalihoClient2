package com.genossys.pasangbalihoclient.ui.splashscreen

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.genossys.pasangbalihoclient.R
import com.genossys.pasangbalihoclient.ui.MainActivity
import com.genossys.pasangbalihoclient.ui.auth.SignIn
import com.genossys.pasangbalihoclient.utils.Coroutines
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SplashScreen : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: SplashViewModelFactory by instance()
    lateinit var viewModel: SplashViewModel
    private var sharedPref1: SharedPreferences? = null
    private val waktuLoading = 1200
    private val waktuFadeOut = 1000
    private var idLogin: Int = 0


//    private val sharedPref2: SharedPreferences =
//        getSharedPreferences(EMAIL_ADVERTISER, PRIVATE_MODE)
//    private val sharedPref3: SharedPreferences = getSharedPreferences(NAMA_ADVERTISER, PRIVATE_MODE)
//    private val sharedPref4: SharedPreferences = getSharedPreferences(API_TOKEN, PRIVATE_MODE)
//    private val sharedPref5: SharedPreferences = getSharedPreferences(TELP_ADVERTUSER, PRIVATE_MODE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        viewModel = ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)
        sharedPref1 = androidx.preference.PreferenceManager
            .getDefaultSharedPreferences(this)

        setSharedPref()

    }

    private fun setSharedPref() {
        Coroutines.main {
            viewModel.getClient().observe(this, Observer {
                try {
                    sharedPref1!!.edit().putInt(ID_CLIENT, it.idClient!!).apply()
                    sharedPref1!!.edit().putString(API_TOKEN, it.apiToken!!).apply()
                    sharedPref1!!.edit().putString(NAMA_CLIENT, it.nama).apply()
                    sharedPref1!!.edit().putString(ALAMAT_CLIENT, it.alamat).apply()
                    idLogin = it.idClient

                    Coroutines.main {
                        animation()
                    }
                } catch (e: NullPointerException) {
                    sharedPref1!!.edit().putInt(ID_CLIENT, 0).apply()
                    sharedPref1!!.edit().putString(API_TOKEN, "").apply()
                    sharedPref1!!.edit().putString(NAMA_CLIENT, "").apply()
                    sharedPref1!!.edit().putString(ALAMAT_CLIENT, "").apply()
                    idLogin = 0

                    Coroutines.main {
                        animation()
                    }
                }
            })
        }
    }

    private fun animation() {
        val animFadeIn = AnimationUtils.loadAnimation(
            this,
            R.anim.fade_in
        )
        img_splash_screen.startAnimation(animFadeIn)

        Handler().postDelayed({
            val animFadeOut = AnimationUtils.loadAnimation(
                this,
                R.anim.fade_out
            )
            img_splash_screen.startAnimation(animFadeOut)
        }, waktuFadeOut.toLong())

        Handler().postDelayed({
            //setelah loading maka akan langsung berpindah ke home activity
            if (idLogin != 0) {
                val home = Intent(this, MainActivity::class.java)
                startActivity(home)
                finish()
            } else {
                val home = Intent(this, SignIn::class.java)
                startActivity(home)
                finish()
            }

        }, waktuLoading.toLong())
    }

    override fun onResume() {
        super.onResume()
        STATE_ACTIVITY = "splashScreen"
    }

    companion object {
        const val ID_CLIENT = "idClientPasBal"
        const val NAMA_CLIENT = "namaClientPasBal"
        const val ALAMAT_CLIENT = "alamatClientPasBal"
        var STATE_ACTIVITY: String = "splashScreenPasBal"
        const val API_TOKEN = "apiTokenPasBal"
    }
}

