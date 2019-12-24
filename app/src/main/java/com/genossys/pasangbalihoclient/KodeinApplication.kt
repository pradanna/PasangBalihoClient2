package com.genossys.pasangbalihoclient

import android.app.Application
import com.genossys.pasangbalihoclient.data.db.PasangBalihoDb
import com.genossys.pasangbalihoclient.data.network.api.ApiService
import com.genossys.pasangbalihoclient.data.network.NetworkConnectionInterceptor
import com.genossys.pasangbalihoclient.data.preferences.PreferenceProvider
import com.genossys.pasangbalihoclient.ui.account.AccountViewModelFactory
import com.genossys.pasangbalihoclient.ui.notifications.NotifViewModelFactory
import com.genossys.pasangbalihoclient.data.db.repository.*
import com.genossys.pasangbalihoclient.ui.auth.AuthViewModelFactory
import com.genossys.pasangbalihoclient.ui.baliho.BalihoViewModelFactory
import com.genossys.pasangbalihoclient.ui.detail.DetailBalihoViewModelFactory
import com.genossys.pasangbalihoclient.ui.news.NewsViewModelFactory
import com.genossys.pasangbalihoclient.ui.home.HomeViewModelFactory
import com.genossys.pasangbalihoclient.ui.input.baliho.InputViewModel
import com.genossys.pasangbalihoclient.ui.splashscreen.SplashViewModelFactory
import com.genossys.pasangbalihoclient.ui.transaksi.detailTransaksi.DetailTransaksiViewModelFactory
import com.genossys.pasangbalihoclient.ui.transaksi.menuTransaksi.MenuTransaksiViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class KodeinApplication : Application(), KodeinAware {


    override val kodein = Kodein.lazy {

        import(androidXModule(this@KodeinApplication))
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { ApiService(instance()) }
        bind() from singleton { PasangBalihoDb(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { AdvertiserRepository(instance(), instance()) }
        bind() from singleton { ClientRepository(instance(), instance()) }
        bind() from singleton { TransaksiRepository(instance()) }
        bind() from singleton { BalihoRepository(instance(), instance()) }
        bind() from singleton { NewsRepository(instance()) }
        bind() from singleton { NotifRepository(instance()) }
        bind() from singleton { KotaRepository(instance(), instance()) }
        bind() from singleton { KategoriRepository(instance(), instance()) }
//        bind() from provider { AuthViewModelFactory(instance()) }
//        bind() from provider { AjukanPenawaranViewModelFactory(instance(), instance()) }
        bind() from provider { MenuTransaksiViewModelFactory(instance()) }
        bind() from provider { DetailTransaksiViewModelFactory(instance(), instance()) }
        bind() from provider { AccountViewModelFactory(instance()) }
        bind() from provider { DetailBalihoViewModelFactory(instance()) }
        bind() from provider { BalihoViewModelFactory(instance(), instance(), instance()) }
        bind() from provider { HomeViewModelFactory(instance(), instance(), instance()) }
        bind() from provider { SplashViewModelFactory(instance()) }
        bind() from provider { NewsViewModelFactory(instance()) }
        bind() from provider { NotifViewModelFactory(instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { InputViewModel(instance()) }
    }


}