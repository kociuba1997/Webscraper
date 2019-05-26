package com.newsscraper.ui.newsdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.newsscraper.R
import com.newsscraper.transportobjects.NewsDTO
import com.newsscraper.ui.NavigationActivity
import com.newsscraper.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_news_details.*

class NewsDetailsFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startProgressDialog()
        val news: NewsDTO = arguments?.getSerializable("news") as NewsDTO
        if (news.photo?.contains("http") == true) {
            parentActivity.setImage(newsImageView, news.photo)
        } else {
            parentActivity.stopProgressDialog()
        }
        dateTextView.text = news.date
        authorTextView.text = news.author
        contentTextView.text = news.text
    }
}