package com.genossys.pasangbalihoclient.ui.baliho

import android.content.*
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.genossys.pasangbalihoclient.R
import com.genossys.pasangbalihoclient.data.db.entity.Baliho
import com.genossys.pasangbalihoclient.ui.adapter.AdapterBaliho
import com.genossys.pasangbalihoclient.ui.input.baliho.InputDetailBaliho
import com.genossys.pasangbalihoclient.ui.splashscreen.SplashScreen
import com.genossys.pasangbalihoclient.utils.*
import com.genossys.pasangbalihoclient.utils.customSnackBar.ChefSnackbar
import com.genossys.pasangbalihoclient.utils.firebaseServices.MyFirebaseMessagingService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_baliho.*
import kotlinx.android.synthetic.main.loading_bottom_layout.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.*
import kotlin.concurrent.timerTask

class BalihoActivity : AppCompatActivity(), SimpleListener, KodeinAware, GenosDialog {

    override val kodein by kodein()
    private val factory: BalihoViewModelFactory by instance()
    lateinit var viewModel: BalihoViewModel
    private lateinit var balihoAdapter: AdapterBaliho

    var timer = Timer()
    var kota: String? = ""
    var kategori: String? = ""
    var sortby = "nama_kota"
    var tambahan = ""
    var urutan = "ASC"
    var stateSelect = 0
    private var page: Int = 1
    private var totalPage: Int = 0
    private var readyToLoad = true
    private var btnReloadReady = false
    private var isLoadAwalOk = false

    var idPref: SharedPreferences? = null

    val listKota = mutableListOf("Semua Kota")
    val listKategori = mutableListOf("Semua Kategori")
    var listBaliho: MutableList<Baliho> = mutableListOf()
    private val listSort =
        mutableListOf("Sort By", "kota", "kategori", "termurah", "terbesar", "terkecil")

    private lateinit var recycleviewPencarian: RecyclerView
    private lateinit var root: ConstraintLayout
    private lateinit var constrainLoadingBottom: ConstraintLayout
    private lateinit var btnReload: ImageView
    private lateinit var btnTambahBaliho: FloatingActionButton

    private val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {

            val tittle: String? = intent?.getStringExtra("tittle")
            val body: String? = intent?.getStringExtra("body")

            ChefSnackbar.make(root, R.drawable.ic_notif, tittle!!, body!!).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_baliho)

        viewModel = ViewModelProviders.of(this, factory).get(BalihoViewModel::class.java)
        viewModel.listener = this

        initComponent()
        initRecycleView()
        loadData(1)
        initSpinner()
        initSpinnerSelected()
        initEditText()
        spinner_kota.setSelection(0)
        spinner_kategori.setSelection(0)

        initButton()

        recycleViewOnBottom()

    }

    private fun initComponent() {
        idPref = androidx.preference.PreferenceManager
            .getDefaultSharedPreferences(this)


        root = findViewById(R.id.root_layout)
        btnReload = findViewById(R.id.reload_bottom)
        constrainLoadingBottom = findViewById(R.id.constrain_loading_bottom)
        recycleviewPencarian = findViewById(R.id.recycle_view_pencarian)
        btnTambahBaliho = findViewById(R.id.btn_tambah_baliho)

        btnTambahBaliho.visibility = View.GONE

    }

    private fun initButton() {
        btnTambahBaliho.setOnClickListener {
            Intent(this, InputDetailBaliho::class.java).also {
                startActivity(it)
            }
        }

        button_back.setOnClickListener {
            finish()
        }

        btnReload.setOnClickListener {
            if (btnReloadReady) {
                if (isLoadAwalOk) {
                    loadData(page + 1)
                } else {
                    page = 1
                    loadData(1)
                }
            }

        }

    }


    private fun initSpinner() {

        Coroutines.main {
            listKategori.clear()
            listKota.clear()
            listKategori.add("Semua Kategori")
            listKota.add("Semua Kota")

            val kotas = viewModel.getKota()
            val kategoris = viewModel.getKategori()
            kotas.observe(this, androidx.lifecycle.Observer {
                for (element in it) {
                    listKota.add(element.namaKota.toString())
                }
            })

            kategoris.observe(this, androidx.lifecycle.Observer {
                for (element in it) {
                    listKategori.add(element.kategori.toString())
                }
            })

            val arrayAdapterKota =
                ArrayAdapter(this@BalihoActivity, R.layout.layout_spinner_item, listKota)
            spinner_kota.adapter = arrayAdapterKota

            val arrayAdapterKategori = ArrayAdapter(
                this@BalihoActivity,
                R.layout.layout_spinner_item,
                listKategori
            )
            spinner_kategori.adapter = arrayAdapterKategori

            val arrayAdapterSort = ArrayAdapter(
                this@BalihoActivity,
                R.layout.layout_spinner_item,
                listSort
            )
            spinner_sort.adapter = arrayAdapterSort

        }
    }

    private fun initSpinnerSelected() {
        if (spinner_kota != null) {

            spinner_kota.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    listBaliho.clear()
                    kota = if (listKota[position] == "Semua Kota") {
                        ""
                    } else {
                        listKota[position]
                    }

                    if (stateSelect == 1) {
                        loadData(1)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }

        if (spinner_kategori != null) {
            spinner_kategori.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    listBaliho.clear()
                    kategori = if (listKategori[position] == "Semua Kategori") {
                        ""
                    } else {
                        listKategori[position]
                    }

                    if (stateSelect == 1) {
                        loadData(1)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }

        if (spinner_sort != null) {

            spinner_sort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    listBaliho.clear()
                    when (listSort[position]) {
                        "Sort By" -> {
                            urutan = "ASC"
                            sortby = "nama_kota"
//                            root.snackbar(listSort[position])

                        }
                        "kota" -> {
                            urutan = "ASC"
                            sortby = "nama_kota"
                        }
                        "kategori" -> {
                            urutan = "ASC"
                            sortby = "kategori"
                        }
                        "termurah" -> {
                            urutan = "ASC"
                            sortby = "harga_market"
                        }
                        "terbesar" -> {
                            urutan = "DESC"
                            sortby = "luas"
                        }
                        "terkecil" -> {
                            urutan = "ASC"
                            sortby = "luas"
                        }
                        else -> {
                            urutan = "ASC"
                            sortby = "nama_kota"
                        }

                    }

                    if (stateSelect == 1) {
                        loadData(1)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }


    }

    private fun initEditText() {
        edittext_pencarian_global.requestFocus()
        edittext_pencarian_global.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                timer.cancel()
                timer = Timer()
                timer.schedule(timerTask {
                    tambahan = edittext_pencarian_global.text.toString()
                    page = 1
                    loadData(page)
                }, 700)
            }
        })
    }


    private fun initRecycleView() {
        recycleviewPencarian = findViewById(R.id.recycle_view_pencarian)

        recycleviewPencarian.apply {
            layoutManager = LinearLayoutManager(this@BalihoActivity)
            balihoAdapter = AdapterBaliho()
            adapter = balihoAdapter
            isNestedScrollingEnabled = false
        }
    }

    override fun onStarted() {
        text_kosong.visibility = View.INVISIBLE
        btnReloadReady = false
        Coroutines.main {
            constrainLoadingBottom.visibility = View.VISIBLE
            progress_loading_bottom.visibility = View.VISIBLE
            reload_bottom.visibility = View.GONE
            recycleviewPencarian.visibility = View.GONE
        }

    }

    override fun onSuccess() {
        Coroutines.main {
            //            root.snackbar("$urutan $sortby")
            recycleviewPencarian.visibility = View.VISIBLE
            constrainLoadingBottom.visibility = View.GONE
            stateSelect = 1
            readyToLoad = true
        }
    }

    override fun onTimeOut(message: String) {
        btnReloadReady = true
        Coroutines.main {
            recycleviewPencarian.visibility = View.VISIBLE
            constrainLoadingBottom.visibility = View.VISIBLE
            progress_loading_bottom.visibility = View.GONE
            reload_bottom.visibility = View.VISIBLE
            dialogHandler(this, "Internet Error", ERROR_INTERNET, R.drawable.ic_nointernet, "pesan")
        }
    }

    override fun onFailure(message: String) {
        btnReloadReady = true
        Coroutines.main {
            recycleviewPencarian.visibility = View.VISIBLE
            progress_loading_bottom.visibility = View.INVISIBLE
            reload_bottom.visibility = View.VISIBLE

            if (message == ERROR_INTERNET) {
                dialogHandler(
                    this,
                    "Internet Error",
                    ERROR_INTERNET,
                    R.drawable.ic_nointernet,
                    "pesan"
                )
            } else {
                dialogHandler(this, "Error", ERROR_API, R.drawable.ic_nointernet, "pesan")
            }
        }
    }

    override fun onEmpty() {
        Coroutines.main {
            recycleviewPencarian.visibility = View.INVISIBLE
            constrainLoadingBottom.visibility = View.GONE
            text_kosong.visibility = View.VISIBLE
        }
    }

    private fun recycleViewOnBottom() {
        recycle_view_pencarian.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    if (page != totalPage) {
                        if (readyToLoad) {
                            readyToLoad = false
                            loadData(page + 1)
                        }
                    }
                }
            }
        })
    }

    private fun loadData(thisPage: Int) {
        Coroutines.main {
            viewModel.getBalihoClient(
                idPref?.getInt(
                    SplashScreen.ID_CLIENT, 0
                )!!,
                kota!!,
                kategori!!,
                sortby,
                urutan,
                tambahan,
                thisPage
            ).observe(this@BalihoActivity, androidx.lifecycle.Observer {
                for (i in it.baliho) {
                    listBaliho.add(i!!)
                }
                balihoAdapter.sumitList(listBaliho)
                page = it.currentPage!!
                totalPage = it.lastPage!!
                isLoadAwalOk = true
            })
        }
    }

    override fun onResume() {
        super.onResume()
        appContext = this
        SplashScreen.STATE_ACTIVITY = "PencarianGlobalActivity"

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(
                broadCastReceiver,
                IntentFilter(MyFirebaseMessagingService.NOTIF_TRANSAKSI)
            )
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(broadCastReceiver)

    }

    companion object {

        var appContext: Context? = null

    }


    override fun action() {

    }
}
