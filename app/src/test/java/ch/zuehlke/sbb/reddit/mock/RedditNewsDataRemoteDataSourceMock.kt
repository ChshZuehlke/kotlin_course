package ch.zuehlke.sbb.reddit.mock

import android.util.Log
import ch.zuehlke.sbb.reddit.data.source.RedditDataSource
import ch.zuehlke.sbb.reddit.data.source.remote.RedditNewsDataRemoteDataSource
import ch.zuehlke.sbb.reddit.models.RedditNewsData
import ch.zuehlke.sbb.reddit.models.RedditPostsData
import com.google.common.collect.Lists
import io.reactivex.Emitter
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers

/**
 * Created by celineheldner on 20.11.17.
 */
class RedditNewsDataRemoteDataSourceMock: RedditDataSourceMock(), RedditDataSource {

    override val news: Flowable<List<RedditNewsData>>
        get() {
            return Flowable.generate(
                {
                    "started"
                },
                fun(next: String, emitter: Emitter<List<RedditNewsData>>): String{
                    Thread.sleep(500)
                    emitter.onNext(redditNews)
                    return "";
                })
        }

    override fun posts(permaLink: String): Observable<List<RedditPostsData>> {
        return Observable.create(ObservableOnSubscribe<List<RedditPostsData>> {
            Thread.sleep(500)
            it.onNext(redditPosts)
            it.onComplete()
        })
    }

    override fun refreshNews() {

    }

    override fun savePosts(data: List<RedditPostsData>) {
        // not implemented for remote
    }

    override fun deletePostsWithPermaLink(permaLink: String) {
        // not implemented for remote
    }

    override fun deleteAllNews() {
        // not implemented for remote
    }

    override fun saveRedditNews(data: List<RedditNewsData>) {
        // not implemented for remote
    }
}