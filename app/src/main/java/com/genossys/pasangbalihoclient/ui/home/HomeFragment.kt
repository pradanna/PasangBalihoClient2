package com.genossys.pasangbalihoclient.ui.home

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.genossys.pasangbalihoclient.R
import com.genossys.pasangbalihoclient.data.db.entity.Transaksi
import com.genossys.pasangbalihoclient.ui.adapter.AdapterTransaksi
import com.genossys.pasangbalihoclient.ui.baliho.BalihoActivity
import com.genossys.pasangbalihoclient.ui.splashscreen.SplashScreen
import com.genossys.pasangbalihoclient.ui.splashscreen.SplashScreen.Companion.ALAMAT_CLIENT
import com.genossys.pasangbalihoclient.ui.splashscreen.SplashScreen.Companion.ID_CLIENT
import com.genossys.pasangbalihoclient.ui.splashscreen.SplashScreen.Companion.NAMA_CLIENT
import com.genossys.pasangbalihoclient.ui.transaksi.menuTransaksi.MenuTransaksi
import com.genossys.pasangbalihoclient.utils.*
import com.wang.avi.AVLoadingIndicatorView
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class HomeFragment : HomeListener, KodeinAware, Fragment(), GenosDialog {

    private lateinit var homeViewModel: HomeViewModel
    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private lateinit var root: View

    private lateinit var adapterTransaksi: AdapterTransaksi
    var listTransaksi: MutableList<Transaksi> = mutableListOf()
    private var page: Int = 1
    private var totalPage: Int = 1

    lateinit var cardLoading: CardView
    lateinit var namaText: TextView
    lateinit var badgeBaliho: TextView
    lateinit var badgeTransaksi: TextView
    lateinit var alamatText: TextView
    lateinit var btnBaliho: CardView
    lateinit var btnTransaksi: CardView
    lateinit var btnChat: CardView
    lateinit var loadingMid: AVLoadingIndicatorView
    lateinit var btnReload: ImageView
//    lateinit var toolbar: Toolbar

    var idPref: SharedPreferences? = null

    private lateinit var recycleHistory: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        homeViewModel.homeListener = this

        root = inflater.inflate(R.layout.fragment_home, container, false)

//        statusBar(android.R.color.transparent)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            val w: Window = getWindow()
//            w.setFlags(
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//            )
//        }

        initComponent()
        initButton()
        loadDataHistory()
        return root
    }

    private fun loadDataHistory() {

        Coroutines.main {
            homeViewModel.allDataTransaksiClient(idPref?.getInt(ID_CLIENT, 0)!!, 1)
                .observe(this, Observer { pageTransaksi ->
                    listTransaksi.clear()
                    initRecycleView()

                    for (i in pageTransaksi.transaksi) {
                        listTransaksi.add(i!!)
                    }

                    adapterTransaksi.sumitList(listTransaksi)
                    page = pageTransaksi.currentPage!!
                    totalPage = pageTransaksi.lastPage!!

                    Coroutines.main {
                        homeViewModel.countNewTransaksiClient(idPref?.getInt(ID_CLIENT, 0)!!)
                            .observe(this, Observer {

                                if (it.count != 0) {
                                    badgeTransaksi.visibility = View.VISIBLE
                                    badgeTransaksi.text = it.count.toString()
                                }

                            })
                    }
                })

        }

    }

    private fun initComponent() {
        idPref = androidx.preference.PreferenceManager
            .getDefaultSharedPreferences(activity?.applicationContext)

        setPref()

        recycleHistory = root.findViewById(R.id.recycler_history_transksi)
        cardLoading = root.findViewById(R.id.card_loading)
        loadingMid = root.findViewById(R.id.progress_loading_mid)
        btnReload = root.findViewById(R.id.reload)
        btnBaliho = root.findViewById(R.id.btn_baliho)
        btnTransaksi = root.findViewById(R.id.btn_transaksi)
        btnChat = root.findViewById(R.id.btn_chat)
        namaText = root.findViewById(R.id.text_nama_client)
        alamatText = root.findViewById(R.id.text_alamat_client)
        badgeBaliho = root.findViewById(R.id.badge_baliho)
        badgeTransaksi = root.findViewById(R.id.badge_transaksi)

        namaText.text = idPref?.getString(NAMA_CLIENT, "")!!
        alamatText.text = idPref?.getString(ALAMAT_CLIENT, "")!!
        badgeBaliho.visibility = View.GONE
        badgeTransaksi.visibility = View.GONE
        cardLoading.visibility = View.GONE
    }

    private fun initButton() {
        btnBaliho.setOnClickListener { v ->
            Intent(activity, BalihoActivity::class.java).also {
                startActivity(it)
            }
        }

        btnChat.setOnClickListener {
            activity?.gotoWhatsapp("", "")
        }

        btnTransaksi.setOnClickListener {
            Intent(activity, MenuTransaksi::class.java).also {
                startActivity(it)
            }
        }

        btnReload.setOnClickListener {
            loadDataHistory()
        }
    }

    private fun initRecycleView() {
        recycleHistory.apply {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapterTransaksi = AdapterTransaksi()
            adapter = adapterTransaksi
            isNestedScrollingEnabled = false
        }
    }


    private fun setPref() {
        Coroutines.main {
            homeViewModel.getLoggedInAdvertiser().observe(this, Observer {
                try {
                    idPref!!.edit().putInt(ID_CLIENT, it.idClient!!).apply()
                    idPref!!.edit().putString(SplashScreen.API_TOKEN, it.apiToken!!).apply()
                    idPref!!.edit().putString(NAMA_CLIENT, it.nama).apply()
                    idPref!!.edit().putString(ALAMAT_CLIENT, it.alamat).apply()


                } catch (e: NullPointerException) {
                    idPref!!.edit().putInt(ID_CLIENT, 0).apply()
                    idPref!!.edit().putString(SplashScreen.API_TOKEN, "").apply()
                    idPref!!.edit().putString(NAMA_CLIENT, "").apply()
                    idPref!!.edit().putString(ALAMAT_CLIENT, "").apply()

                }
            })
        }
    }

    override fun onStarted() {
        Coroutines.main {
            cardLoading.visibility = View.VISIBLE
            btnReload.visibility = View.GONE
            loadingMid.visibility = View.VISIBLE
        }
    }


    override fun onLoadMore() {
        Coroutines.main {
            cardLoading.visibility = View.VISIBLE
            btnReload.visibility = View.GONE
            loadingMid.visibility = View.VISIBLE
        }
    }

    override fun onRekomendasiLoaded() {
    }

    override fun onSuccess() {
        Coroutines.main {
            cardLoading.visibility = View.GONE
            btnReload.visibility = View.GONE
            loadingMid.visibility = View.GONE
        }
    }


    override fun onFailure(message: String) {
        Coroutines.main {
            cardLoading.visibility = View.VISIBLE
            btnReload.visibility = View.VISIBLE
            loadingMid.visibility = View.GONE

            if (message == ERROR_INTERNET) {
                dialogHandler(
                    activity!!,
                    "Internet Error",
                    ERROR_INTERNET,
                    R.drawable.ic_nointernet,
                    "pesan"
                )
            } else {
                dialogHandler(activity!!, "Error", ERROR_API, R.drawable.ic_nointernet, "pesan")
            }
        }
    }

    override fun onTimeOut(s: String) {
        Coroutines.main {
            cardLoading.visibility = View.VISIBLE
            btnReload.visibility = View.VISIBLE
            loadingMid.visibility = View.GONE

            dialogHandler(activity!!, "Error", ERROR_INTERNET, R.drawable.ic_nointernet, "pesan")
        }
    }

    override fun action() {

    }

    override fun onResume() {
        super.onResume()
        setPref()
    }
}