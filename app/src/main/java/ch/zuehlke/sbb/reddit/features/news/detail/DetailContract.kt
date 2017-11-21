package ch.zuehlke.sbb.reddit.features.news.detail

import ch.zuehlke.sbb.reddit.BasePresenter
import ch.zuehlke.sbb.reddit.BaseView
import ch.zuehlke.sbb.reddit.models.RedditPostsData

/**
 * Created by chsc on 13.11.17.
 */

interface DetailContract {

    interface View : BaseView<Presenter> {
        fun showRedditPosts(posts: List<RedditPostsData>)
        fun showRedditNewsLoadingError()
        fun setLoadingIndicator(isActive: Boolean)
    }

    interface Presenter : BasePresenter {
        fun loadRedditPosts()
    }
}
