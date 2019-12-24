package com.genossys.pasangbalihoclient.ui.account

import android.content.*
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.genossys.pasangbalihoclient.R
import com.genossys.pasangbalihoclient.ui.link.Link
import com.genossys.pasangbalihoclient.ui.splashscreen.SplashScreen
import com.genossys.pasangbalihoclient.ui.transaksi.menuTransaksi.MenuTransaksi
import com.genossys.pasangbalihoclient.utils.Coroutines
import com.genossys.pasangbalihoclient.utils.GenosDialog
import com.genossys.pasangbalihoclient.utils.customSnackBar.ChefSnackbar
import com.genossys.pasangbalihoclient.utils.firebaseServices.MyFirebaseMessagingService
import com.genossys.pasangbalihoclient.utils.keLogin
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.wang.avi.AVLoadingIndicatorView
import kotlinx.android.synthetic.main.fragment_account.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.*
import kotlin.concurrent.schedule

class AccountFragment : Fragment(), KodeinAware, AccountListener, GenosDialog {


    override val kodein by kodein()
    private val factory: AccountViewModelFactory by instance()
    private var idPref: SharedPreferences? = null

    private lateinit var progressBar: AVLoadingIndicatorView
    private lateinit var accountViewModel: AccountViewModel
    private lateinit var signoutLayout: ConstraintLayout
    private lateinit var rootLayout: ConstraintLayout
    private lateinit var layoutHistory: ConstraintLayout
    private lateinit var layoutLoginAccount: ConstraintLayout
    private lateinit var beriNilaiLayout: ConstraintLayout
    private lateinit var layoutSyaratDanKetentuan: ConstraintLayout
    private lateinit var textFullName: TextView
    private lateinit var textAlamat: TextView
    private lateinit var root: View


    private val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {

            val tittle: String? = intent?.getStringExtra("tittle")
            val body: String? = intent?.getStringExtra("body")

            ChefSnackbar.make(rootLayout, R.mipmap.logobaliho, tittle!!, body!!).show()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        accountViewModel =
            ViewModelProviders.of(this, factory).get(AccountViewModel::class.java)
        accountViewModel.accountListener = this

        root = inflater.inflate(R.layout.fragment_account, container, false)

        initComponent()
        initUser()
        initButton()
        return rootLayout
    }


    private fun initUser() {
        if (idPref?.getInt(SplashScreen.ID_CLIENT, 0) == 0) {
            layoutLoginAccount.visibility = View.VISIBLE
        } else {
            layoutLoginAccount.visibility = View.GONE
            textFullName.text = idPref?.getString(SplashScreen.NAMA_CLIENT, "")
            textAlamat.text = idPref?.getString(SplashScreen.ALAMAT_CLIENT, "")
        }
    }

    private fun initComponent() {
        idPref = androidx.preference.PreferenceManager
            .getDefaultSharedPreferences(activity?.applicationContext)

        rootLayout = root.findViewById(R.id.root_layout)
        textAlamat = root.findViewById(R.id.textview_email)
        textFullName = root.findViewById(R.id.textview_fullname)
        layoutLoginAccount = root.findViewById(R.id.layout_login_accuont)
        layoutSyaratDanKetentuan = root.findViewById(R.id.layout_privacy_policy)
        layoutHistory = root.findViewById(R.id.layout_history)
        signoutLayout = root.findViewById(R.id.layout_sign_out)
        beriNilaiLayout = root.findViewById(R.id.layout_beri_nilai)
        progressBar = root.findViewById(R.id.progressBar)

        progressBar.visibility = View.GONE
    }

    private fun initButton() {
        root.layout_sign_out.setOnClickListener {

            dialogHandler(
                activity!!,
                "SIGN OUT",
                "Apakah kamu yakin ingin melakukan sign out?",
                R.mipmap.konfirm,
                "warning"
            )

        }

        root.layout_sign_out.setOnClickListener {
            dialogHandler(
                activity!!,
                "SIGN OUT, ",
                "Apakah anda yakin?",
                R.drawable.ic_choose,
                "warning"
            )
        }

        layoutHistory.setOnClickListener {
            val i = Intent(activity, MenuTransaksi::class.java)
            startActivity(i)
        }

        layoutSyaratDanKetentuan.setOnClickListener {
            Intent(activity, Link::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                it.putExtra("tittle", "Kebijakan Privasi")
                it.putExtra("link", "https://www.pasangbaliho.com/kebijakan-privasi")
                startActivity(it)
            }
        }

        beriNilaiLayout.setOnClickListener {
            val uri: Uri = Uri.parse("market://details?id=" + context!!.packageName)
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            goToMarket.addFlags(
                Intent.FLAG_ACTIVITY_NO_HISTORY or
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            )
            try {
                startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + context!!.packageName)
                    )
                )
            }
        }
    }


    private fun signOut() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(ContentValues.TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result?.token
                Coroutines.main {
                    accountViewModel.signOut(token!!)
                }
            })
    }

    override fun onResume() {
        super.onResume()
        idPref = androidx.preference.PreferenceManager
            .getDefaultSharedPreferences(activity?.applicationContext)
        appContext = this.activity!!
        SplashScreen.STATE_ACTIVITY = "AccountFragment"

        LocalBroadcastManager.getInstance(activity?.applicationContext!!)
            .registerReceiver(
                broadCastReceiver,
                IntentFilter(MyFirebaseMessagingService.NOTIF_TRANSAKSI)
            )
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(activity?.applicationContext!!)
            .unregisterReceiver(broadCastReceiver)
    }


    companion object {

        var appContext: Context? = null

    }

    override fun onStartedSignOut() {
        Coroutines.main {
            progressBar.visibility = View.VISIBLE

        }
    }

    override fun onSuccessSignOut() {
        Coroutines.main {
            progressBar.visibility = View.GONE

            dialogHandler(
                activity!!,
                "Berhasil",
                "Sign out berhasil",
                R.drawable.ic_welcome,
                "pesan"
            )
            Timer("loading", false).schedule(2000) {
                activity?.keLogin()
            }
        }
    }

    override fun onFailureSignOut(message: String) {
        Coroutines.main {
            progressBar.visibility = View.GONE
            Timer("loading", false).schedule(1000) {
                Coroutines.main {
                    dialogHandler(
                        activity!!,
                        "Gagal Sign Out, ",
                        message,
                        R.drawable.ic_notif,
                        "pesan"
                    )
                }
            }
        }
    }

    override fun action() {
        signOut()
    }
}