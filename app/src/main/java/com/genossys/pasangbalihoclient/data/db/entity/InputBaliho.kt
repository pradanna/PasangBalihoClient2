package com.genossys.pasangbalihoclient.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tb_balihos")
data class InputBaliho(

    @PrimaryKey(autoGenerate = false)
    @SerializedName("id_baliho")
    val idBaliho: Int?,

    val kategori: String?,
    @SerializedName("nama_baliho")
    val namaBaliho: String?,
    val lebar: Int?,
    val tinggi: Int?,
    val luas: Int?,
    val orientasi: String?,
    val venue: String?,
    val deskripsi: String?,

    @SerializedName("harga_client")
    val hargaClient: Int?,

    @SerializedName("nama_provinsi")
    val provinsi: String?,

    @SerializedName("nama_kota")
    val kota: String?,

    val alamat: String?,
    val latitude: String?,
    val longitude: String?



)