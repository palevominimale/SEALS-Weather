package app.seals.weather.domain.usecases.network

import android.util.Log
import app.seals.weather.network.interfaces.RetrofitApiInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TAG = "RETROFIT_REQUEST"

object RetrofitNetworkRequest {
    private const val TIME_OUT: Long = 60

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val resp = chain.proceed(chain.request())
            if (resp.code == 200) {
                try {
                    val responseBody = resp.peekBody(2048).string() // peekBody() will not close the response
                    println(responseBody)
                } catch (e: Exception) {
                    Log.e(TAG, "OkHttp error: $e")
                }
            } else {
                Log.e(TAG, "OkHttp: $resp")
            }
            resp
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