package com.example.Giggles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class ToyAdapter(private val mContext: Context, private val mToyList: List<Toy>) : BaseAdapter() {
    override fun getCount(): Int {
        return mToyList.size
    }

    override fun getItem(position: Int): Any {
        return mToyList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        var convertView = convertView
        var viewHolder: ViewHolder
        if (convertView == null) {
            convertView =
                LayoutInflater.from(mContext).inflate(R.layout.list_item_toy, parent, false)
            viewHolder = ViewHolder()
            viewHolder.titleTextView = convertView.findViewById(R.id.toy_title_text_view)
            viewHolder.categoryTextView = convertView.findViewById(R.id.toy_category_text_view)
            viewHolder.brandTextView = convertView.findViewById(R.id.toy_brand_text_view)
            viewHolder.conditionTextView = convertView.findViewById(R.id.toy_condition_text_view)
            viewHolder.priceTextView = convertView.findViewById(R.id.toy_price_text_view)
            viewHolder.imageView = convertView.findViewById(R.id.toy_image_view)
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        val toy = mToyList[position]
        viewHolder.titleTextView!!.text = toy.title
        viewHolder.categoryTextView!!.text = toy.category
        viewHolder.brandTextView!!.text = toy.brand
        viewHolder.conditionTextView!!.text = toy.condition
        viewHolder.priceTextView!!.text = "$" + toy.expectedPrice
        if (toy.imageUrl != null && !toy.imageUrl!!.isEmpty()) {
            Picasso.get().load(toy.imageUrl).into(viewHolder.imageView)
        }
        return convertView
    }

    private class ViewHolder {
        var titleTextView: TextView? = null
        var categoryTextView: TextView? = null
        var brandTextView: TextView? = null
        var conditionTextView: TextView? = null
        var priceTextView: TextView? = null
        var imageView: ImageView? = null
    }
}