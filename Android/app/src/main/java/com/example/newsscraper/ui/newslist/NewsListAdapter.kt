package com.example.newsscraper.ui.newslist

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsscraper.R
import com.example.newsscraper.data.model.News
import kotlinx.android.synthetic.main.item_news_list.view.*

class NewsListAdapter(private val items: List<News>, private val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(news: News, itemView: View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(items[position], clickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(news: News, listener: OnItemClickListener) = with(itemView) {
            titleTextView.text = news.text
            dateTextView.text = news.date
            authorTextView.text = news.author
            tagTextView.text = "tagi: ${news.tag}"
            authorTextView.setOnClickListener {
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:${news.link}")
                itemView.context.startActivity(dialIntent)
            }
            setOnClickListener {
                listener.onItemClick(news, it)
            }
        }

    }

}