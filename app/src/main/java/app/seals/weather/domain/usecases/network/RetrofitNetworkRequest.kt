package app.seals.weather.domain.usecases.network

import app.seals.weather.network.interfaces.RetrofitApiInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIME_OUT: Long = 30

object RetrofitNetworkRequest {

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            chain.proceed(chain.request())
        }.build()

    val retrofit: RetrofitApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(RetrofitApiInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RetrofitApiInterface::class.java)
    }
}