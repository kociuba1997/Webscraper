package com.newsscraper.ui.newslist

import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.newsscraper.R
import com.newsscraper.services.ServiceManager
import com.newsscraper.services.apireceivers.GetNewsReceiver
import com.newsscraper.transportobjects.NewsDTO
import com.newsscraper.ui.NavigationActivity
import com.newsscraper.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_news_list.*

class NewsListFragment : BaseFragment(), NewsListAdapter.OnItemClickListener, GetNewsReceiver {
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
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.activity_navigation_drawer, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                (activity as NavigationActivity).hiddenTags.clear()
                view?.findNavController()?.navigate(R.id.action_newsListFragment_to_loginFragment)
                true
            }
            R.id.tags -> {
                view?.findNavController()?.navigate(R.id.action_newsListFragment_to_tagsFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        newsAdapter = NewsListAdapter(this)
        newsRecyclerView.adapter = newsAdapter
        startProgressDialog()
        serviceManager.getNews(this)
        setupSwipeRefreshLayout()
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout?.setOnRefreshListener { ServiceManager.getNews(this) }
    }

    private fun cancelRefreshingList() {
        Handler().postDelayed({
            swipeRefreshLayout?.isRefreshing = false
        }, 1000)
    }

    private fun populateNewsList(newsList: List<NewsDTO>) {
        newsAdapter.setNews(newsList)
    }

    override fun onItemClick(news: NewsDTO, itemView: View) {
        view?.findNavController()?.navigate(
            R.id.action_newsListFragment_to_newsDetailsFragment,
            Bundle().apply { putSerializable("news", news) })
    }

    override fun onGetNewsSuccess(news: List<NewsDTO>) {
        stopProgressDialog()
        populateNewsList(news)
        cancelRefreshingList()
    }

    override fun onGetNewsError() {
        stopProgressDialog()
    }
}
