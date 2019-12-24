package com.genossys.pasangbalihoclient.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.genossys.pasangbalihoclient.data.db.entity.Baliho
import com.genossys.pasangbalihoclient.data.db.entity.CLIENT_NUMBER
import com.genossys.pasangbalihoclient.data.db.entity.InputBaliho

@Dao
interface BalihoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inputLocalBaliho(baliho: InputBaliho)

    @Query("DELETE from client where num = $CLIENT_NUMBER")
    suspend fun delete()

    @Query("select * from tb_balihos")
    fun getDataBAliho(): LiveData<List<Baliho>>

}