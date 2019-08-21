package ch.wyler.microspot.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ch.wyler.microspot.R


class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // read from shared pref
        val amountOfProducts = readFromSharedPrefTheTotalAmountOfProducts()

        // update text
        val tv = view.findViewById(R.id.homeAmountOfProducts) as TextView
        tv.text = getString(R.string.amountOfProductsText) + " " + amountOfProducts.toString()
    }

    private fun readFromSharedPrefTheTotalAmountOfProducts(): Int? {
        val sharedPref = activity?.getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE)
        val defaultValue = resources.getInteger(R.integer.amount_of_products_default_value)
        return sharedPref?.getInt(getString(R.string.amount_of_products), defaultValue)
    }
}
