package ch.wyler.microspot

import android.app.Application
import ch.sik.teko.home.networking.RestApi

/**
 * The base application is used to setup some basic app stuff like the api or db.
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //Build our api instance
        RestApi.Client.build()
    }

}