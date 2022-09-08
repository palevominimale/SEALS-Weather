package app.seals.weather.domain.interfaces

import androidx.room.*
import app.seals.weather.data.models.ForecastItemDomainModel

@Dao
interface ForecastRepositoryDAO {

    @Query("SELECT * FROM Forecast WHERE id LIKE :id LIMIT 1")
    fun getById(id: Long) : ForecastItemDomainModel?

    @Query("SELECT * FROM Forecast WHERE id IN (:range)")
    fun getByIdRange(range: List<Int>) : List<ForecastItemDomainModel>?

    @Insert
    fun save(data: List<ForecastItemDomainModel>)

    @Insert
    fun put(item: ForecastItemDomainModel)

    @Query("DELETE FROM Forecast")
    fun clear()
}