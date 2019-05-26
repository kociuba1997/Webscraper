package com.newsscraper.ui.tags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.newsscraper.R
import com.newsscraper.services.ServiceManager
import com.newsscraper.services.apireceivers.GetPopularTagsReceiver
import com.newsscraper.services.apireceivers.GetTagsReceiver
import com.newsscraper.services.apireceivers.PutTagsReceiver
import com.newsscraper.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tags.*

class TagsFragment : BaseFragment(), TagsAdapter.OnItemClickListener, GetTagsReceiver, PutTagsReceiver,
    GetPopularTagsReceiver {
    private lateinit var newsAdapter: TagsAdapter
    private var tags: List<String> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_tags, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsAdapter = TagsAdapter(listOf(), this)
        tagsRecyclerView.adapter = newsAdapter
        startProgressDialog()
        serviceManager.getTags(this)
        serviceManager.getPopularTags(this)
        setAddTagButtonOnClick()
        setAddPopularTagTextViewOnClick()
    }

    private fun setAddTagButtonOnClick() {
        addTagButton.setOnClickListener {
            if (addTagEditText.text.isNotEmpty()) {
                val tag = addTagEditText.text.toString().toLowerCase()
                startProgressDialog()
                serviceManager.putTags(this, tags + tag)
                sendToAnalytics(tag)
            }
        }
    }

    private fun setAddPopularTagTextViewOnClick() {
        setAddPopularOnTextView(popularTag1TextView)
        setAddPopularOnTextView(popularTag2TextView)
        setAddPopularOnTextView(popularTag3TextView)
    }

    private fun setAddPopularOnTextView(textView: TextView) {
        textView.setOnClickListener {
            val tag = (it as TextView).text.toString()
            ServiceManager.putTags(this, tags + tag)
            it.visibility = View.INVISIBLE
            startProgressDialog()
            sendToAnalytics(tag)
        }
    }

    private fun sendToAnalytics(tag: String) {
        parentActivity.sendToAnalytics(tag, Bundle().apply { putInt("TAG", addTagEditText.id) })
    }

    private fun populateNewsList(tagsList: List<String>) {
        newsAdapter.setTags(tagsList)
        tags = tagsList
    }

    override fun onGetTagsSuccess(tags: List<String>) {
        stopProgressDialog()
        populateNewsList(tags)
    }

    override fun onGetTagsError() {
        stopProgressDialog()
    }

    override fun onPutTagsSuccess() {
        stopProgressDialog()
        serviceManager.getTags(this)
    }

    override fun onPutTagsError() {
        stopProgressDialog()
    }

    override fun onItemClick(tag: String) {
        startProgressDialog()
        serviceManager.putTags(this, tags.filter { t -> t != tag })
    }

    override fun onGetPopularTagsSuccess(popularTags: List<String>) {
        popularTag1TextView.text = popularTags[0]
        popularTag2TextView.text = popularTags[1]
        popularTag3TextView.text = popularTags[2]
    }
    override fun onGetPopularTagsError() {}
}