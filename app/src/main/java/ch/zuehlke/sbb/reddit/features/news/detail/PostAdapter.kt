package ch.zuehlke.sbb.reddit.features.news.detail

import ch.zuehlke.sbb.reddit.R
import ch.zuehlke.sbb.reddit.features.AdapterConstants
import ch.zuehlke.sbb.reddit.features.GenericBindingBaseAdapter
import ch.zuehlke.sbb.reddit.features.GenericBindingViewHolder
import ch.zuehlke.sbb.reddit.features.ViewType
import ch.zuehlke.sbb.reddit.features.news.NavigationController
import ch.zuehlke.sbb.reddit.models.RedditPostsData

/**
 * Created by chsc on 15.11.17.
 */

class PostAdapter(clickListener: GenericBindingViewHolder.GenericBindingClickListener) : GenericBindingBaseAdapter(clickListener){

    private val POST_VIEW_TYPE = object : ViewType{
        override fun getViewType(): Int {
            return AdapterConstants.POST
        }
    }

    private val items = mutableListOf<RedditPostsData>()

    fun clearAndAddPosts(posts: List<RedditPostsData>) {

        val previousItemSize = items.size
        items.clear()
        notifyItemRangeRemoved(0, previousItemSize)
        items.addAll(posts)
        notifyItemRangeChanged(0, posts.size + 1 /* plus loading item */)
    }

    override fun getObjForPosition(position: Int): Any {
        return items[position]
    }

    override fun getLayoutIdForViewType(position: Int): Int {
        return R.layout.item_detail
    }

    override fun getViewTypeForPosition(position: Int): Int {
       return POST_VIEW_TYPE.getViewType()
    }

    override fun getItemCount() = items.size

}