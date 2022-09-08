package app.seals.weather.data.repos

import android.content.Context
import app.seals.weather.data.models.ForecastItemDomainModel
import app.seals.weather.data.room.RoomDB
import app.seals.weather.domain.interfaces.ForecastRepositoryDAO

class ForecastRepository(context: Context) : ForecastRepositoryDAO {

    private var db: ForecastRepositoryDAO = RoomDB.getInstance(context)?.dao()!!

    override fun getById(id: Long): ForecastItemDomainModel {
        return db.getById(id) ?: ForecastItemDomainModel()
    }

    override fun getByIdRange(range: List<Int>): List<ForecastItemDomainModel> {
        return db.getByIdRange(range) ?: listOf(ForecastItemDomainModel())
    }

    override fun save(data: List<ForecastItemDomainModel>) {
        db.save(data)
    }

    override fun put(item: ForecastItemDomainModel) {
        db.put(item)
    }

    override fun clear() {
        db.clear()
    }
}