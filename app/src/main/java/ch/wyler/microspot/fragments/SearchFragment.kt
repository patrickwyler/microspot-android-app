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
import ch.sik.teko.home.networking.RestApi
import ch.wyler.microspot.R
import ch.wyler.microspot.adapter.ProductSearchListAdapter
import ch.wyler.microspot.models.Product
import ch.wyler.microspot.models.ProductSearchResult
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment(), ProductSearchListAdapter.AdapterCallback {

    private val adapter = ProductSearchListAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showProducts(view, "")

        view.findViewById<EditText>(R.id.search).setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    showProducts(view, v.getText().toString())
                    true
                }
                else -> false
            }
        }
    }

    private fun showProducts(view: View, query: String) {
        //Set the adapter
        product_list.layoutManager = LinearLayoutManager(view.context)
        product_list.adapter = adapter


        //Load the products from the network
        RestApi.Client.getInstance().fetchProducts(query, 10)
            .enqueue(object : Callback<ProductSearchResult> {
                override fun onFailure(call: Call<ProductSearchResult>, t: Throwable) {
                    //Display an error to the user, because there was a io exception
                }

                override fun onResponse(call: Call<ProductSearchResult>, response: Response<ProductSearchResult>) {
                    //We got a response
                    if (response.isSuccessful) {
                        //Bind the data only when we have it
                        response.body()?.products?.apply {
                            adapter.setData(this.toMutableList())
                        }

                    } else {
                        //Display an error
                    }
                }
            })
    }

    /**
     * Callback for the adapter
     */
    override fun onItemClicked(product: Product, position: Int) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.microspot.ch/--P" + product.code))
        startActivity(browserIntent)
    }
}
