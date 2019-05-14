package com.newsscraper.ui.newslist


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newsscraper.R
import com.newsscraper.transportobjects.NewsDTO
import com.newsscraper.ui.NavigationActivity
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
        (holder as ViewHolder).bind(items[holder.adapterPosition], clickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(news: NewsDTO, listener: OnItemClickListener) = with(itemView) {
            setItemTextViews(news)
            setOnClickListener {
                listener.onItemClick(news, it)
            }
            if (news.photo?.contains("http") == true) {
                (context as NavigationActivity).setImage(newsImageView, news.photo, true)
            } else {
                newsImageView.setImageDrawable(context.getDrawable(R.drawable.ic_message))
            }
        }

        private fun View.setItemTextViews(news: NewsDTO) {
            titleTextView.text = news.text?.trim()
            dateTextView.text = news.date
            val sb = StringBuilder("tagi: ")
            if(news.author != null) {
                authorTextView.text = news.author
            }
            tagTextView.text = news.tags?.joinTo(sb, ", ")
        }
    }
}