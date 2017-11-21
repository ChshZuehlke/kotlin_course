package ch.zuehlke.sbb.reddit.features.news.overview

import android.util.Log
import ch.zuehlke.sbb.reddit.data.source.RedditRepository
import ch.zuehlke.sbb.reddit.models.RedditNewsData
import com.google.common.base.Preconditions.checkNotNull
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.internal.functions.Functions
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber
import org.reactivestreams.Publisher

/**
 * Created by chsc on 11.11.17.
 */


class OverviewPresenter(private val view: OverviewContract.View, private val redditRepository: RedditRepository) : OverviewContract.Presenter {

    abstract class PageSubscriber : ResourceSubscriber<Pair<Boolean, List<RedditNewsData>>>() {
        override fun onStart() {
            nextPage()
        }

        fun nextPage() {
            Log.d(TAG, "Request next page")
            request(1)
        }
    }

    fun createReddidSubscriber() = object : PageSubscriber() {
        override fun onNext(result: Pair<Boolean, List<RedditNewsData>>) {
            val (isFirst, page) = result
            Log.d(TAG, "Got page with ${page.size} entries")
            Log.d(TAG, "On thread ${Thread.currentThread().name}")
            processTasks(page, !isFirst)
        }

        override fun onComplete() {
            Log.d(TAG, "Reddit subscription completed")
        }

        override fun onError(t: Throwable?) {
            Log.e(TAG, "Reddit subscription failed with $t")
        }
    }

    val disoposable = CompositeDisposable()
    var reddidSubscriber: PageSubscriber? = null

    init {
        checkNotNull(view, "OverviewView cannot be null")
        checkNotNull(redditRepository, "RedditRepository cannot be null")
    }

    override fun start() {
        loadRedditNews(true)
    }

    override fun stop() {
        disoposable.dispose()
    }

    override fun showRedditNews(redditNewsData: RedditNewsData) {
        checkNotNull(redditNewsData, "redditNewsData cannot be null!")
        view.showRedditNewsDetails(redditNewsData.id!!)
    }

    override fun loadRedditNews(forceUpdate: Boolean) {
        loadRedditNews(forceUpdate, true)
    }

    override fun loadMoreRedditNews() {
        loadRedditNews(false)
    }

    /**
     * Allow to use zip without having a prefetch size of 128
     */
    fun <T1,T2> zip(source1: Publisher<T1>, source2: Publisher<T2>): Flowable<Pair<T1, T2>> {
        return zip(source1, source2, BiFunction<T1, T2, Pair<T1, T2>>{ a, b -> Pair(a, b) })
    }
    fun <T1, T2, R> zip(source1: Publisher<out T1>, source2: Publisher<out T2>, zipper: BiFunction<in T1, in T2, out R>): Flowable<R> {
        return Flowable.zipArray<Any, R>(Functions.toFunction(zipper), false, 1, source1, source2)
    }

    private fun loadRedditNews(forceUpdate: Boolean, showLoadingUI: Boolean) {
        if(showLoadingUI)
            view.setLoadingIndicator(true)
        if(forceUpdate || reddidSubscriber == null) {
            Log.d(TAG, "Create new subscription")
            reddidSubscriber = createReddidSubscriber()

            disoposable.add(
                    zip(
                            Flowable.concat(Flowable.just(true), Flowable.just(false).repeat()),
                            redditRepository.news
                    )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread(), false, 1)
                    .subscribeWith(reddidSubscriber!!)
            )
        }
        reddidSubscriber?.nextPage()
    }

    private fun processTasks(news: List<RedditNewsData>, added: Boolean) {
        if (news.isEmpty()) {
            // Show a message indicating there are no news for that filter type.
            processEmptyTasks()
        } else {
            // Show the list of news
            if (added) {
                view.addRedditNews(news)
            } else {
                view.showRedditNews(news)
                view.setLoadingIndicator(false)
            }

        }
    }

    private fun processEmptyTasks() {
        view.showNoNews()
    }

    companion object {
        const val TAG = "OverviewPresenter"
    }
}
