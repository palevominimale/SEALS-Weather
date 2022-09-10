package app.seals.weather.domain.interfaces

import androidx.room.*
import app.seals.weather.data.models.ForecastItemDataModel

@Dao
interface ForecastRepositoryDAO {

    @Query("SELECT * FROM Forecast WHERE id LIKE :id LIMIT 1")
    fun getById(id: Long) : ForecastItemDataModel?

    @Query("SELECT * FROM Forecast WHERE id IN (:range)")
    fun getByIdRange(range: List<Int>) : List<ForecastItemDataModel>?

    @Insert
    fun save(data: List<ForecastItemDataModel>)

    @Insert
    fun put(item: ForecastItemDataModel)

    @Query("DELETE FROM Forecast")
    fun clear()
}