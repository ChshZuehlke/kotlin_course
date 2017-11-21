package ch.zuehlke.sbb.reddit.features.news

import android.support.v4.util.SparseArrayCompat
import ch.zuehlke.sbb.reddit.R
import ch.zuehlke.sbb.reddit.features.AdapterConstants
import ch.zuehlke.sbb.reddit.features.GenericBindingBaseAdapter
import ch.zuehlke.sbb.reddit.features.GenericBindingViewHolder
import ch.zuehlke.sbb.reddit.features.ViewType
import ch.zuehlke.sbb.reddit.features.news.NavigationController
import ch.zuehlke.sbb.reddit.models.RedditNewsData

/**
 * Created by chsc on 15.11.17.
 */


class ViewTypeAwareAdapter(clickListener: GenericBindingViewHolder.GenericBindingClickListener): GenericBindingBaseAdapter(clickListener){


    private val items = mutableListOf<ViewType>()
    private val viewTypeLayoutDelegate = SparseArrayCompat<Int>()

    init {
        viewTypeLayoutDelegate.put(AdapterConstants.LOADING, R.layout.item_loading)
        viewTypeLayoutDelegate.put(AdapterConstants.NEWS, R.layout.item_overview)
    }

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
        override fun getViewType(): Int {
            return AdapterConstants.LOADING
        }
    }

    override fun getObjForPosition(position: Int): Any {
        return items[position]
    }

    override fun getViewTypeForPosition(position: Int): Int {
       return items[position].getViewType()
    }

    override fun getLayoutIdForViewType(position: Int): Int {
        return viewTypeLayoutDelegate.get(getViewTypeForPosition(position))
    }

    override fun getItemCount() = items.size


}