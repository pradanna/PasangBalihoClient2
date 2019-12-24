package com.genossys.pasangbalihoclient.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.genossys.pasangbalihoclient.data.db.dao.BalihoDao
import com.genossys.pasangbalihoclient.data.db.dao.ClientDao
import com.genossys.pasangbalihoclient.data.db.entity.Baliho
import com.genossys.pasangbalihoclient.data.db.entity.Client
import com.genossys.pasangbalihoclient.data.db.entity.InputBaliho

@Database(
    entities = [Client::class, InputBaliho::class, Baliho::class],
    version = 2,
    exportSchema = false
)

abstract class PasangBalihoDb : RoomDatabase() {

    abstract fun clientDao(): ClientDao
    abstract fun balihoDao(): BalihoDao

    companion object {

        @Volatile
        private var INSTANCE: PasangBalihoDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: builDataBase(context).also {
                INSTANCE = it
            }
        }

        private fun builDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PasangBalihoDb::class.java,
                "pasangBalihoClient.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }

}