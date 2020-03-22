package com.edwinacubillos.sesionroom

import android.app.Application
import androidx.room.Room
import com.edwinacubillos.sesionroom.model.DeudorDataBase

class SesionRoom : Application() {

    companion object {
        lateinit var database: DeudorDataBase
    }

    override fun onCreate() {
        super.onCreate()

        SesionRoom.database = Room.databaseBuilder(
            this,
            DeudorDataBase::class.java,
            "deudor_DB"
        ).allowMainThreadQueries()
            .build()
    }
}



