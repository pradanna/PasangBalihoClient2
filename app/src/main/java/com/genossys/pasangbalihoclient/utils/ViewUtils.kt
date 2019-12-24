package com.genossys.pasangbalihoclient.utils

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.genossys.pasangbalihoclient.ui.MainActivity
import com.genossys.pasangbalihoclient.ui.auth.SignIn
import com.genossys.pasangbalihoclient.ui.splashscreen.SplashScreen
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.iid.FirebaseInstanceId
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

const val ERROR_API = "Terjadi kesalahan, Coba lagi nanti ya..."
const val ERROR_INTERNET = "Internet kamu lagi bermasalah nih..."

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.INVISIBLE
}

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("OK") {
            snackbar.dismiss()
        }
    }.show()
}

fun View.snackbarError(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.view.setBackgroundColor(Color.parseColor("#E94340"))
        snackbar.setActionTextColor(Color.parseColor("#FFFFFF"))
        snackbar.setAction("OK") {
            snackbar.dismiss()
        }
    }.show()
}


fun View.snackbarSuccess(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.view.setBackgroundColor(Color.parseColor("#55AE59"))
        snackbar.setActionTextColor(Color.parseColor("#FFFFFF"))
        snackbar.setAction("OK") {
            snackbar.dismiss()
        }
    }.show()
}

fun tglSystemToView(tanggalSistem: String): String {

    val dateAwal =
        SimpleDateFormat("yyyy-MM-dd").parse(tanggalSistem)

    val myFormat = "dd MMM yyyy" // mention the format you need
    val sdf = SimpleDateFormat(myFormat, Locale.US)
    return sdf.format(dateAwal)
}

fun tglViewToSystem(tanggalSistem: String): String {

    val dateAwal =
        SimpleDateFormat("dd MMM yyyy").parse(tanggalSistem)

    val myFormat = "yyyy-MM-dd" // mention the format you need
    val sdf = SimpleDateFormat(myFormat, Locale.US)
    return sdf.format(dateAwal)
}

fun toDesimalText(harga: Int): String {
    val text: String
    val dec = DecimalFormat("#,###")
    text = dec.format(harga).toString()

    return text
}

fun Context.gotoWhatsapp(pesan: String, tambahan: String) {
    val url =
        "https://api.whatsapp.com/send?phone=$phoneNumber&text=$pesan $tambahan%0A"
    val i = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse(url)
    startActivity(i)
}

fun Context.gotoGmail(pesan: String, tambahan: String) {
    val emailIntent = Intent(
        Intent.ACTION_SENDTO, Uri.fromParts(
            "mailto", "pradanamahendra@gmail.com", null
        )
    )
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "$pesan $tambahan")
    startActivity(Intent.createChooser(emailIntent, null))
}

fun Context.gotoLoginFormBelumLogin() {
    Intent(this, SignIn::class.java).also {
        it.putExtra("login", 1)
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun changeStatusBar(activity: Activity, color: Int) {
    val window = activity.window

    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = ContextCompat.getColor(activity, color)
    }
}

fun Context.keMenuutama() {
    Intent(this, MainActivity::class.java).also {
        it.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}


fun Context.keSplashScreen() {
    Intent(this, SplashScreen::class.java).also {
        it.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}


fun Context.keLogin() {
    Intent(this, SignIn::class.java).also {
        it.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}
