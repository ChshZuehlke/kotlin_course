package ch.zuehlke.sbb.reddit.data

import android.support.annotation.NonNull
import com.google.common.collect.Lists

import java.util.LinkedHashMap

import ch.zuehlke.sbb.reddit.data.source.RedditDataSource
import ch.zuehlke.sbb.reddit.models.RedditNewsData
import ch.zuehlke.sbb.reddit.models.RedditPostsData

/**
 * Created by chsc on 08.11.17.
 */

class FakeRedditNewsRemoteDataSource// Prevent direct instantiation.
 constructor() : RedditDataSource {

    private val REDDIT_NEWS_SERVICE_DATA = LinkedHashMap<String, RedditNewsData>()

    override fun getMoreNews(callback: RedditDataSource.LoadNewsCallback) {
        // not needed
    }

    override fun getPosts(callback: RedditDataSource.LoadPostsCallback, title: String) {
        // not needed
    }

    override fun savePosts(data: List<RedditPostsData>) {
        // not needed
    }

    override fun deletePostsWithPermaLink(permaLink: String) {
        // not needed
    }


    override fun getNews(@NonNull callback: RedditDataSource.LoadNewsCallback) {
        callback.onNewsLoaded(Lists.newArrayList(REDDIT_NEWS_SERVICE_DATA.values))
    }

    override fun refreshNews() {
        // Not required because the {@link ch.zuehlke.sbb.reddit.data.source.RedditRepository} handles the logic of refreshing the
        // news from all the available data sources.
    }

    override fun deleteAllNews() {
        // Not supported by Reddit :)
    }


    override fun saveRedditNews(@NonNull data: List<RedditNewsData>) {
        // In this demo app we do not support posting of news, therefore not implemented.
    }

}
