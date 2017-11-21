package ch.zuehlke.sbb.reddit.mock

import ch.zuehlke.sbb.reddit.data.source.RedditDataSource
import ch.zuehlke.sbb.reddit.models.RedditNewsData
import ch.zuehlke.sbb.reddit.models.RedditPostsData
import io.reactivex.Emitter
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

/**
 * Created by celineheldner on 20.11.17.
 */
class RedditNewsLocalDataSourceMock: RedditDataSourceMock(), RedditDataSource{


    override val news: Flowable<List<RedditNewsData>>  get() {
        return Flowable.generate(
                {
                    "started"
                },
                fun(next: String, emitter: Emitter<List<RedditNewsData>>): String{
                    emitter.onNext(redditNews)
                    return "";
                })
    }


    override fun posts(permaLink: String): Observable<List<RedditPostsData>> {
        return Observable.create(ObservableOnSubscribe<List<RedditPostsData>> {
            it.onNext(redditPosts)
            it.onComplete()
        })
    }


    init {
        redditPosts.clear()
        redditNews.clear()
    }

    override fun savePosts(data: List<RedditPostsData>) {
        redditPosts.addAll(data)
    }

    override fun deletePostsWithPermaLink(permaLink: String) {
        redditPosts.filter { it.parentPermaLink.equals(permaLink) }
    }

    override fun deleteAllNews() {
        redditNews.clear()
    }

    override fun saveRedditNews(data: List<RedditNewsData>) {
        redditNews.addAll(data)
    }

    override fun refreshNews() {
        //no need for local datasource
    }



}