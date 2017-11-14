package ch.zuehlke.sbb.reddit.features.detail

import ch.zuehlke.sbb.reddit.data.source.RedditDataSource
import ch.zuehlke.sbb.reddit.data.source.RedditRepository
import ch.zuehlke.sbb.reddit.data.source.remote.RedditAPI
import ch.zuehlke.sbb.reddit.data.source.remote.RedditElementTypeAdapterFactory.Companion.elementTypeAdapterFactory
import ch.zuehlke.sbb.reddit.data.source.remote.model.posts.RedditPostElement
import ch.zuehlke.sbb.reddit.models.RedditPostsData
import com.google.common.base.Preconditions.checkNotNull
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * Created by chsc on 13.11.17.
 */

class DetailPresenter(detailView: DetailContract.View, repository: RedditRepository, redditUrl: String, redditAPI: RedditAPI) : DetailContract.Presenter {

    private val mRedditUrl: String
    private val mDetailView: DetailContract.View
    private val mRepository: RedditRepository
    private val mRedditAPI: RedditAPI

    init {
        mRepository = checkNotNull(repository, "The repository cannot be null")
        mDetailView = checkNotNull(detailView, "The DetailView cannot be null")
        checkNotNull(redditUrl, "The reddit url cannot be null")
        mRedditAPI = checkNotNull(redditAPI, "RedditAPI cannot be null")
        mRedditUrl = checkNotNull(redditUrl, "The reddit url cannot be null")
        detailView.setPresenter(this)
    }

    override fun start() {
        loadRedditPosts()
    }


    override fun loadRedditPosts() {
        loadRedditPosts(mRedditUrl)
    }


    fun loadRedditPosts(url: String) {
        if (mDetailView.isActive) {
            mDetailView.setLoadingIndicator(true)
        }

        mRepository.getPosts(object : RedditDataSource.LoadPostsCallback {
            override fun onPostsLoaded(posts: List<RedditPostsData>) {
                if (mDetailView.isActive) {
                    mDetailView.setLoadingIndicator(false)
                    mDetailView.showRedditPosts(posts)
                }

            }

            override fun onDataNotAvailable() {
                if (mDetailView.isActive) {
                    mDetailView.showRedditNewsLoadingError()
                }

            }
        }, mRedditUrl)

    }

    companion object {

        private val type = object : TypeToken<List<RedditPostElement>>() {

        }.type

        private val gson = GsonBuilder()
                .registerTypeAdapterFactory(elementTypeAdapterFactory)
                .create()


        private val TAG = "DetailPresenter"
    }

}
