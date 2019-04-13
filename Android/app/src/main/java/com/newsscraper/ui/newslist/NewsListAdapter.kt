package com.newsscraper.ui.newslist

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newsscraper.R
import com.newsscraper.data.model.News
import com.newsscraper.transportobjects.NewsDTO
import kotlinx.android.synthetic.main.item_news_list.view.*

class NewsListAdapter(private val items: List<NewsDTO>, private val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(news: NewsDTO, itemView: View)
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

        fun bind(news: NewsDTO, listener: OnItemClickListener) = with(itemView) {
            titleTextView.text = news.text.trim()
            dateTextView.text = "11.04.2019 15:24"
            val sb = StringBuilder("tagi: ")
            authorTextView.text = news.author
            tagTextView.text = news.tags.joinTo(sb, ", ")
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