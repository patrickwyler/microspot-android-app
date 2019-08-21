package ch.wyler.microspot.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.wyler.microspot.R
import ch.wyler.microspot.models.Product
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.item_user.view.*

class MatchListAdapter(private val callback: AdapterCallback): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    // overwrite view type here!!
    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    // klossner logic!
    fun setData(data: MutableList<Product>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun addData(item: Product) {
        data.add(item)
        notifyItemInserted(data.size -1)
    }

    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    fun removeItem(index: Int) {
        data.removeAt(index)
        notifyItemRemoved(index)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (position >= 0) {
            val user = data[position]

            if (holder is UserViewHolder) {
                holder.bind(user, callback)
            }
        }

    }


    private class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val transform = CropCircleTransformation()

        fun bind(product: Product, callback: AdapterCallback) {
            //Load the image to the corresponding image view

            // Picasso Framework to get images async
           Picasso.get().load("https://www.microspot.ch/"+product.images.get(0).images.get(0).url)
               .transform(transform)
               .into(itemView.user_img)
            //Set the product name
            itemView.user_name.text = product.name
            //Bind the adapter click
            itemView.setOnClickListener {
                callback.onItemClicked(product, adapterPosition)
            }
        }
    }

    interface AdapterCallback {

        /**
         * This is the callback for the adapter click
         */
        fun onItemClicked(product:Product, position:Int)

    }
}