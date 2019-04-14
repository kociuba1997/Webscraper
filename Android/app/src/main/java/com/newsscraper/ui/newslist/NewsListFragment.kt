package com.newsscraper.ui.newslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.newsscraper.R
import com.newsscraper.data.model.News
import com.newsscraper.services.ServiceManager
import com.newsscraper.services.ServiceProvider
import com.newsscraper.services.apireceivers.GetNewsReceiver
import com.newsscraper.transportobjects.NewsDTO
import com.newsscraper.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_news_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsListFragment : Fragment(), NewsListAdapter.OnItemClickListener, GetNewsReceiver {
    private lateinit var viewModel: NewsListViewModel
    private lateinit var newsAdapter: NewsListAdapter

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
//        viewModel.getNewsList().observe(this, Observer<List<News>> { news ->
//            news?.let {
//                populateNewsList(news)
//            }
//        })
        ServiceManager.getNews(this)
        sendToAnalyticsButton.setOnClickListener {
            if(tagEditText.text.isNotEmpty()) {
                (activity as MainActivity).sendToAnalytics(
                    tagEditText.text.toString(),
                    Bundle().apply { putInt("TAG", tagEditText.id)})
            }
        }
    }

    private fun populateNewsList(newsList: List<NewsDTO>) {
        newsAdapter = NewsListAdapter(newsList, this)
        newsRecyclerView.adapter = newsAdapter
    }

    override fun onItemClick(news: NewsDTO, itemView: View) {}

    override fun onGetNewsSuccess(news: List<NewsDTO>) {
        populateNewsList(news)
    }

    override fun onGetNewsError() {}

}
