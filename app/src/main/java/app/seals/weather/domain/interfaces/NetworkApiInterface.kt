package app.seals.weather.domain.interfaces

interface NetworkApiInterface {
    suspend fun execute()
}