package ch.sik.teko.home.networking


import ch.wyler.microspot.models.ProductSearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * This interface is used by Retrofit to generate the internal client.
 */
interface Api {

    @GET("products/search/")
    fun fetchProducts(@Query("query") query: String, @Query("pageSize") count: Int): Call<ProductSearchResult>

}