package com.genossys.pasangbalihoclient.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.genossys.pasangbalihoclient.data.db.entity.CLIENT_NUMBER
import com.genossys.pasangbalihoclient.data.db.entity.Client

@Dao
interface ClientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(client: Client)

    @Query("DELETE from client where num = $CLIENT_NUMBER")
    suspend fun delete()

    @Query("select * from client where num = $CLIENT_NUMBER")
    fun checkClient(): LiveData<Client>

    @Query("select * from client where num = $CLIENT_NUMBER")
    suspend fun checkClient2(): Client

}