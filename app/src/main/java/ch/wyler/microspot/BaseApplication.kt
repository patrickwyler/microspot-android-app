package ch.wyler.microspot

import android.app.Application
import android.content.Context
import ch.wyler.microspot.models.ProductSearchResult
import ch.wyler.microspot.networking.RestApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * The base application is used to setup some basic app stuff like the api or db.
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // build api instance
        RestApi.Client.build()

        // count total amount of shop products
        countProducts()
    }

    private fun countProducts() {
        //Load the products from the network
        RestApi.Client.getInstance().fetchProducts("", 1)
            .enqueue(object : Callback<ProductSearchResult> {
                override fun onFailure(call: Call<ProductSearchResult>, t: Throwable) {
                    //Display an error to the user, because there was a io exception
                }

                override fun onResponse(call: Call<ProductSearchResult>, response: Response<ProductSearchResult>) {
                    //We got a response
                    if (response.isSuccessful) {
                        //Bind the data only when we have it
                        response.body()?.pagination?.apply {
                            writeToSharedPrefTheTotalAmountOfProducts(response)
                        }

                    } else {
                        //Display an error
                    }
                }

                private fun writeToSharedPrefTheTotalAmountOfProducts(response: Response<ProductSearchResult>) {
                    val sharedPref = getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE)

                    // write to shared pref
                    if (sharedPref != null) {
                        with(sharedPref.edit()) {
                            putInt(
                                getString(R.string.amount_of_products),
                                response.body()?.pagination!!.totalNumberOfResults
                            )
                            commit()
                        }
                    }
                }
            })
    }
}