package ch.zuehlke.sbb.reddit.features.news.overview

import android.support.v4.util.SparseArrayCompat
import ch.zuehlke.sbb.reddit.R
import ch.zuehlke.sbb.reddit.features.news.AdapterConstants
import ch.zuehlke.sbb.reddit.features.news.GenericBindingBaseAdapter
import ch.zuehlke.sbb.reddit.features.news.GenericBindingViewHolder
import ch.zuehlke.sbb.reddit.features.news.ViewType
import ch.zuehlke.sbb.reddit.models.RedditNewsData

/**
 * Created by chsc on 15.11.17.
 */


class OverviewAdapter(clickListener: GenericBindingViewHolder.GenericBindingClickListener): GenericBindingBaseAdapter(clickListener){

    // Excercise 01
    // Add a observable delegate and trigger the autoNotify method (You need to write the corresponding Extension)
    private val items = mutableListOf<ViewType>()

    private val viewTypeLayoutDelegate = SparseArrayCompat<Int>()

    init {
        viewTypeLayoutDelegate.put(AdapterConstants.LOADING, R.layout.item_loading)
        viewTypeLayoutDelegate.put(AdapterConstants.NEWS, R.layout.item_overview)
    }

    // Excersise 01
    // Change both methods so that the DiffUtil can be triggered (Observables are working based on setters)
    fun addRedditNews(newsData: List<RedditNewsData>) {
        val initPosition = items.size - 1
        items.removeAt(initPosition)
        notifyItemRemoved(initPosition)

        items.addAll(newsData)
        items.add(loadingItem)
        notifyItemRangeChanged(initPosition, items.size + 1 /* plus loading item */)
    }

    fun clearAndAddNews(newsData: List<RedditNewsData>) {

        val previousItemSize = items.size
        items.clear()
        notifyItemRangeRemoved(0, previousItemSize)
        items.addAll(newsData)
        items.add(loadingItem)
        notifyItemRangeChanged(0, newsData.size + 1 /* plus loading item */)

    }

    private val loadingItem = object : ViewType {
        override val viewType = AdapterConstants.LOADING
    }

    override fun getObjForPosition(position: Int): Any {
        return items[position]
    }

    override fun getViewTypeForPosition(position: Int): Int {
       return items[position].viewType
    }

    override fun getLayoutIdForViewType(position: Int): Int {
        return viewTypeLayoutDelegate.get(getViewTypeForPosition(position))
    }

    override fun getItemCount() = items.size


}