package com.newsscraper.ui.newslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.newsscraper.R
import com.newsscraper.data.model.News
import com.newsscraper.services.ServiceManager
import com.newsscraper.services.apireceivers.GetNewsReceiver
import com.newsscraper.transportobjects.NewsDTO
import kotlinx.android.synthetic.main.fragment_news_list.*

class NewsListFragment : Fragment(), NewsListAdapter.OnItemClickListener, GetNewsReceiver {
    private lateinit var viewModel: NewsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewsListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getNewsList().observe(this, Observer<List<News>> { news ->
            news?.let {
                populateNewsList(news)
            }
        })
        ServiceManager.getNews(this)
    }

    private fun populateNewsList(newsList: List<News>) {
        newsRecyclerView.adapter = NewsListAdapter(newsList, this)
    }

    override fun onItemClick(news: News, itemView: View) {}

    override fun onGetNewsSuccess(news: List<NewsDTO>) {

    }

    override fun onGetNewsError() {}

}
