package com.newsscraper.ui.newslist


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newsscraper.R
import com.newsscraper.transportobjects.NewsDTO
import com.newsscraper.ui.NavigationActivity
import kotlinx.android.synthetic.main.item_news_list.view.*
import kotlinx.android.synthetic.main.item_news_list_header.view.*

private const val TYPE_HEADER = 0
private const val TYPE_NORMIE = 1

class NewsListAdapter(private val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: MutableList<NewsDTO> = mutableListOf()
    private val itemsTags = items.distinctBy { news -> news.tags }.map { news -> news.tags?.get(0) }.toMutableList()

    private var activity: NavigationActivity? = null

    interface OnItemClickListener {
        fun onItemClick(news: NewsDTO, itemView: View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v: View
        activity = parent.context as NavigationActivity
        return when (viewType) {
            TYPE_HEADER -> {
                v = LayoutInflater.from(parent.context).inflate(R.layout.item_news_list_header, parent, false)
                ViewHolderHeader(v)
            }
            else -> {
                v = LayoutInflater.from(parent.context).inflate(R.layout.item_news_list, parent, false)
                ViewHolder(v)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> TYPE_HEADER
            items.size > position && items[position].tags?.get(0) != items[position - 1].tags?.get(0) -> TYPE_HEADER
            else -> TYPE_NORMIE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_HEADER -> (holder as ViewHolderHeader).bind(items[holder.adapterPosition + 3].tags?.get(0)) { tag ->
                if (activity?.hiddenTags?.contains(tag) == true) {
                    activity?.hiddenTags?.remove(tag)
                } else {
                    activity?.hiddenTags?.add(tag)
                }
                notifyDataSetChanged()
            }
            else -> {
                if(items.size > holder.adapterPosition) {
                    val news = items[holder.adapterPosition]
                    if (activity?.hiddenTags?.contains(news.tags?.get(0)) == false) {
                        (holder as ViewHolder).bind(items[holder.adapterPosition], clickListener, activity?.hiddenTags)
                    } else {
                        holder.itemView.visibility = View.GONE
                        holder.itemView.layoutParams = ViewGroup.LayoutParams(0, 0)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size + itemsTags.size - 1
    }

    fun setNews(news: List<NewsDTO>) {
        items.clear()
        items.addAll(news)
        itemsTags.clear()
        itemsTags.addAll(items.distinctBy { n -> n.tags }.map { n -> n.tags?.get(0) }.toMutableList())
        notifyDataSetChanged()
    }

    class ViewHolderHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(tag: String?, listener: (String?) -> Unit) = with(itemView) {
            headerTextView.text = tag
            setOnClickListener {
                listener.invoke(tag)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(news: NewsDTO, listener: OnItemClickListener, hiddenTags: MutableList<String?>?) = with(itemView) {
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
            if (news.author != null) {
                authorTextView.text = news.author
            }
            tagTextView.text = news.tags?.joinTo(sb, ", ")
        }
    }
}