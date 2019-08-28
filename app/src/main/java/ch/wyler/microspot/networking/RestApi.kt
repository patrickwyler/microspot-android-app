package ch.wyler.microspot.networking

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RestApi {

    private const val TIMEOUT_IN_SEC = 10
    private const val ENDPOINT = "https://www.microspot.ch/mspocc/occ/msp/"

    object Client {
        private var instance: Api? = null

        fun build(endpoint: String = ENDPOINT) {
            val retrofit = buildRetrofit(endpoint)

            instance = retrofit.create(Api::class.java)
        }

        @Synchronized
        fun getInstance(): Api {
            if (instance == null) {
                throw IllegalStateException("api is not initialized")
            }
            return instance!!
        }

    }

    private fun buildRetrofit(endpoint: String): Retrofit {
        // create a ok http instance builder for configuration
        val builder = OkHttpClient.Builder().readTimeout(TIMEOUT_IN_SEC.toLong(), TimeUnit.SECONDS)

        // log webrequests
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(httpLoggingInterceptor)

        val client = builder.build()

        // build instance
        return Retrofit.Builder()
            .baseUrl(endpoint)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }
}