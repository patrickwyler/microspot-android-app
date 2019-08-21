package ch.wyler.microspot.networking

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RestApi {

    //How long is the time out for the ok http client
    private const val TIMEOUT_IN_SEC = 10

    object Client {

        private var instance: Api? = null
        private var url: String? = null

        fun build(endpoint: String = "https://www.microspot.ch/mspocc/occ/msp/") {
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
        //Create a ok http instance builder for configuration
        val builder = OkHttpClient.Builder()
            .readTimeout(TIMEOUT_IN_SEC.toLong(), TimeUnit.SECONDS)

        //We wan't so see the actual web requests.
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(httpLoggingInterceptor)

        //This is the ok http instance
        val client = builder.build()

        //Build the Retrofit instance
        return Retrofit.Builder()
            .baseUrl(endpoint)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }

}