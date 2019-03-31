package com.example.newsscraper.ui.newslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.newsscraper.R
import com.example.newsscraper.data.model.News
import kotlinx.android.synthetic.main.fragment_news_list.*

class NewsListFragment : Fragment(), NewsListAdapter.OnItemClickListener {

    private lateinit var viewModel: NewsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewsListViewModel::class.java)
        setHasOptionsMenu(true)
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
    }

    private fun populateNewsList(newsList: List<News>) {
        newsRecyclerView.adapter = NewsListAdapter(newsList, this)
    }

    override fun onItemClick(news: News, itemView: View) {
//        val peopleBundle = Bundle().apply {
//            putInt(getString(R.string.people_id), people.id)
//        }
//        view?.findNavController()?.navigate(R.id.action_peoplesListFragment_to_peopleDetailsFragment, peopleBundle)
    }

}