package ch.wyler.microspot.fragments


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ch.wyler.microspot.R
import ch.wyler.microspot.adapter.ProductSearchListAdapter
import ch.wyler.microspot.models.Product
import ch.wyler.microspot.models.ProductSearchResult
import ch.wyler.microspot.networking.RestApi
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment(), ProductSearchListAdapter.AdapterCallback {

    private val adapter = ProductSearchListAdapter(this)
    private val baseUrlForProduct = "http://www.microspot.ch/--P"
    private val maxAmountOfSearchResults = 10;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // search for all products
        showProducts(view, "")

        view.findViewById<EditText>(R.id.search).setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    // show the result of the searched string
                    showProducts(view, v.getText().toString())
                    true
                }
                else -> false
            }
        }
    }

    /**
     * Search for products and show the result
     */
    private fun showProducts(view: View, query: String) {
        // set the adapter
        product_list.layoutManager = LinearLayoutManager(view.context)
        product_list.adapter = adapter

        // load the products from the network
        RestApi.Client.getInstance().fetchProducts(query, maxAmountOfSearchResults)
            .enqueue(object : Callback<ProductSearchResult> {
                override fun onFailure(call: Call<ProductSearchResult>, t: Throwable) {
                    // display an error to the user, because there was a io exception
                }

                override fun onResponse(call: Call<ProductSearchResult>, response: Response<ProductSearchResult>) {
                    // we got a response
                    if (response.isSuccessful) {
                        // bind the data only when we have it
                        response.body()?.products?.apply {
                            adapter.setData(this.toMutableList())
                        }

                    }
                }
            })
    }

    /**
     * Callback for the adapter
     */
    override fun onItemClicked(product: Product, position: Int) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(baseUrlForProduct + product.code))
        startActivity(browserIntent)
    }
}
