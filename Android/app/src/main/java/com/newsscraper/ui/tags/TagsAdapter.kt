package com.newsscraper.ui.tags


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newsscraper.R
import kotlinx.android.synthetic.main.item_tags_list.view.*

class TagsAdapter(private var items: List<String>, private val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(tag: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tags_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(items[holder.adapterPosition], clickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setTags(tags: List<String>) {
        items = tags
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(tag: String, listener: OnItemClickListener) = with(itemView) {
            tagTextView.text = tag
            binImageView.setOnClickListener {
                listener.onItemClick(tag)
            }
        }
    }
}