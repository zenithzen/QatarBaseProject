package com.gco.gco

import com.mbt.localization.LocalizedApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.time.Duration

const val APP_USE_MOCK_RESPONSES = false

class App : LocalizedApp() {
    companion object {
        lateinit var http: OkHttpClient
            private set
    }

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate() {
        super.onCreate()
        setupHttpClient()
        coroutineScope.launch(Dispatchers.IO) {

        }
        TypefaceLoader.load(this)
    }

    override fun onTerminate() {
        try {
            coroutineScope.cancel()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onTerminate()
    }

    private fun setupHttpClient() {
        http = OkHttpClient.Builder()
            .addInterceptor { chain ->
                when (val token = Authorization.token) {
                    null -> chain.proceed(chain.request())
                    else -> chain.proceed(
                        chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer $token")
                            .build()
                    )
                }
            }
            .callTimeout(Duration.ofSeconds(300))
            .connectTimeout(Duration.ofSeconds(300))
            .readTimeout(Duration.ofSeconds(300))
            .writeTimeout(Duration.ofSeconds(300))
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            .build()
    }
}