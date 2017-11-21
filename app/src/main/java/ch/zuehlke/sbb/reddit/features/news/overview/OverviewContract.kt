package ch.zuehlke.sbb.reddit.features.news.overview

import ch.zuehlke.sbb.reddit.BasePresenter
import ch.zuehlke.sbb.reddit.BaseView
import ch.zuehlke.sbb.reddit.models.RedditNewsData

/**
 * Created by chsc on 11.11.17.
 */

interface OverviewContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(isActive: Boolean)

        fun showRedditNews(redditNews: List<RedditNewsData>)

        fun addRedditNews(redditNews: List<RedditNewsData>)

        fun showRedditNewsLoadingError()

        fun showNoNews()

        fun showRedditNewsDetails(redditNewsUrl: String)

    }

    interface Presenter : BasePresenter {

        fun loadRedditNews(forceUpdate: Boolean)

        fun loadMoreRedditNews()

        fun showRedditNews(redditNewsData: RedditNewsData)


    }
}
