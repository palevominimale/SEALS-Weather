package app.seals.weather.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.seals.weather.data.models.ForecastItemDataModel
import app.seals.weather.domain.interfaces.ForecastRepositoryDAO

@Database(entities = [ForecastItemDataModel::class], version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {

    abstract fun dao(): ForecastRepositoryDAO

    companion object {
        private var INSTANCE: RoomDB? = null

        fun getInstance(context: Context): RoomDB? {
            if (INSTANCE == null) synchronized(RoomDB::class) {
                INSTANCE = Room.databaseBuilder(

                    context.applicationContext,
                    RoomDB::class.java,
                    "forecast.db"
                ).allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }
}