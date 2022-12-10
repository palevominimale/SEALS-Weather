package app.seals.weather.data.repos

import android.content.Context
import app.seals.weather.data.models.ForecastItemDataModel
import app.seals.weather.data.room.RoomDB
import app.seals.weather.domain.interfaces.ForecastRepositoryDAO

class ForecastRepository(context: Context) : ForecastRepositoryDAO {

    private var db: ForecastRepositoryDAO = RoomDB.getInstance(context)?.dao()!!

    override fun getById(id: Long): ForecastItemDataModel {
        return db.getById(id) ?: ForecastItemDataModel()
    }

    override fun getByIdRange(range: List<Int>): List<ForecastItemDataModel> {
        return db.getByIdRange(range) ?: listOf(ForecastItemDataModel())
    }

    override fun save(data: List<ForecastItemDataModel>) {
        db.save(data)
    }

    override fun put(item: ForecastItemDataModel) {
        db.put(item)
    }

    override fun clear() {
        db.clear()
    }
}