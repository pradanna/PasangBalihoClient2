package com.genossys.pasangbalihoclient.data.network.api

import com.genossys.pasangbalihoclient.data.db.response.*
import com.genossys.pasangbalihoclient.data.network.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

//const val URL_API = "http://genossys.site/"
const val URL_API = "https://www.pasangbaliho.com/"
const val BASE_URL: String = "$URL_API/api/v1/"
const val URL_FOTO: String = "$URL_API/assets/original/"
const val URL_THUMBNAIL: String = "$URL_API/assets/thumbnails/"
const val URL_SLIDER: String = "$URL_API/assets/slider/"

interface ApiService {

    //DATA USER
    @FormUrlEncoded
    @POST("loginClient")
    suspend fun loginClient(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<ClientResponse>


    @FormUrlEncoded
    @POST("registerClient")
    suspend fun registerClient(
        @Field("email") email: String,
        @Field("nama") nama: String,
        @Field("telp") telp: String,
        @Field("password") password: String,
        @Field("alamat") alamat: String
    ): Response<AdvertiserResponse>

    //DATA BALIHO
    @GET("showDetailBaliho/{id}")
    suspend fun showDetailBaliho(
        @Path("id") id: Int
    ): Response<DetailBalihoResponse>

    @GET("getBalihoClient")
    suspend fun getBalihoClient(
        @Query("idClient") idClient: Int,
        @Query("kota") kota: String,
        @Query("kategori") kategori: String,
        @Query("sortby") sortby: String,
        @Query("urutan") urutan: String,
        @Query("tambahan") tambahan: String,
        @Query("page") page: Int
    ): Response<BalihoResponse>

    //DATA NEWS
    @GET("getDataNews")
    suspend fun getDataNews(
        @Query("page") page: Int
    ): Response<NewsResponse>

    //DATA NOTIFIKASI
    @GET("getDataNotifikasiClient")
    suspend fun getDataNotifikasi(
        @Query("page") page: Int,
        @Query("idClient") idClient: Int
    ): Response<NotifikasiResponse>

    //FCM
    @FormUrlEncoded
    @POST("insertFcmClient")
    suspend fun insertFcmClient(
        @Field("idClient") client: Int,
        @Field("fcmToken") apiToken: String
    ): Response<PostResponse>

    @FormUrlEncoded
    @POST("deleteFcmClient")
    suspend fun deleteFcmClient(
        @Field("fcmToken") apiToken: String
    ): Response<PostResponse>


    //TRANSAKSI
    @GET("allDataTransaksiClient")
    suspend fun allDataTransaksiClient(
        @Query("idClient") idClient: Int,
        @Query("page") sortby: Int
    ): Response<TransaksiResponse>

    @GET("dataTransaksiClient")
    suspend fun dataTransaksiClient(
        @Query("idClient") kota: Int,
        @Query("status") kategori: String,
        @Query("page") sortby: Int
    ): Response<TransaksiResponse>

    @GET("detailTransaksiClient/{id}")
    suspend fun showDetailTransaksi(
        @Path("id") id: Int
    ): Response<DetailTransaksiResponse>


    @GET("countNewTransaksiClient")
    suspend fun countNewTransaksiClient(
        @Query("idClient") idClient: Int
    ): Response<CountResponse>

    @FormUrlEncoded
    @POST("setReadAdvertiser")
    suspend fun setReadAdvertiser(
        @Field("idAdv") idAdvertiser: Int
    ): Response<PostResponse>

    //    etc
    @GET("dataAllKota")
    suspend fun getDataAllKota(): Response<KotaResponse>

    @GET("dataAllKategori")
    suspend fun getDataAllKategori(): Response<KategoriResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): ApiService {

            val okHttpClient = OkHttpClient.Builder().addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }


}

