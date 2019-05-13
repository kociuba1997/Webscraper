package com.newsscraper.ui.newsdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.newsscraper.R
import com.newsscraper.transportobjects.NewsDTO
import com.newsscraper.ui.NavigationActivity
import kotlinx.android.synthetic.main.fragment_news_details.*

class NewsDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val news: NewsDTO = arguments?.getSerializable("news") as NewsDTO
        news.photo?.let {
            (activity as NavigationActivity).setImage(newsImageView, it)
        }
        dateTextView.text = "12.04.2019 14:59"
        authorTextView.text = news.author
        contentTextView.text = news.text
    }
}