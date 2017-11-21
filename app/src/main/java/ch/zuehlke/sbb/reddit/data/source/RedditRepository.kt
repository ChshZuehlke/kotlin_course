package ch.zuehlke.sbb.reddit.data.source

import android.content.Context
import ch.zuehlke.sbb.reddit.models.RedditNewsData
import ch.zuehlke.sbb.reddit.models.RedditPostsData
import io.reactivex.Flowable
import io.reactivex.Observable
import ch.zuehlke.sbb.reddit.util.AndroidUtils

import com.google.common.base.Preconditions.checkNotNull

/**
 * Created by chsc on 08.11.17.
 */

class RedditRepository (val newsRemoteDataSource: RedditDataSource, val newsLocalDataSource: RedditDataSource) : RedditDataSource {

    private val sources = listOf(newsLocalDataSource, newsRemoteDataSource)

    override val news: Flowable<List<RedditNewsData>>
        get() = Flowable.merge(sources.map{it.news}, 1, 1).filter{ it.isNotEmpty() }

    private fun convertURLToRemote(url: String): String {
        val parsedUrl = url.substring(url.indexOf(COMMENT_SECION) + COMMENT_SECION.length)
        return parsedUrl.substring(0, parsedUrl.length - 1)
    }

    override fun posts(permalink: String): Observable<List<RedditPostsData>> {
        val convertedPermalink = convertURLToRemote(permalink)
        return Observable.merge(sources.map{it.posts(convertedPermalink)}, 1, 1)
    }
    override fun savePosts(data: List<RedditPostsData>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deletePostsWithPermaLink(permaLink: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var mCacheIsDirty: Boolean = true

    override fun refreshNews() {
        mCacheIsDirty = true
        newsRemoteDataSource.refreshNews()
    }

    override fun deleteAllNews() {
        newsRemoteDataSource.deleteAllNews() // Although we call deleteAllNews() on the remote datasource, it is not implemented.
        newsLocalDataSource.deleteAllNews()
    }

    override fun saveRedditNews(data: List<RedditNewsData>) {
        checkNotNull(data)

        newsLocalDataSource.saveRedditNews(data)
        newsRemoteDataSource.saveRedditNews(data) // Although we call saveRedditNews() on the remote datasource, it is not implemented.

    }

    private fun refreshCache(news: List<RedditNewsData>) {
        TODO("Implement caching")
        mCacheIsDirty = false
    }

    private fun updateLocalDataSource(news: List<RedditNewsData>) {
        newsLocalDataSource.saveRedditNews(news)

    }

    private fun refreshLocalDataSource(news: List<RedditNewsData>) {
        newsLocalDataSource.deleteAllNews()
        newsLocalDataSource.saveRedditNews(news)
    }

    companion object {
        private const val TAG = "RemoteDataSource"

        private const val COMMENT_SECION = "comments/"
    }
}
