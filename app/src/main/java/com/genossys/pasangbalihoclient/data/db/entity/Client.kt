package com.genossys.pasangbalihoclient.data.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val CLIENT_NUMBER = 0

@Entity(tableName = "client")
data class Client(

    @PrimaryKey(autoGenerate = false)
    var num: Int = CLIENT_NUMBER,

    @SerializedName("id_client")
    val idClient: Int?,

    val email: String,
    val nama: String,

    @SerializedName("nama_instansi")
    val namaInstansi: String?,

    val telp: String?,
    val alamat: String?,
    val nib: String?,
    val npwp: String?,
    val status: String?,

    @SerializedName("api_token")
    val apiToken: String?,

    @SerializedName("no_ktp")
    val noKtp: String?,

    @SerializedName("email_verified_at")
    val emailVerifiedAt: String?,

    @SerializedName("created_at")
    val createdAt: String?,

    @SerializedName("updated_at")
    val updatedAt: String?
)