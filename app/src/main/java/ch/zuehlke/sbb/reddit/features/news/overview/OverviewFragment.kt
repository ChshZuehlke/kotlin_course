package ch.zuehlke.sbb.reddit.features.news.overview

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.zuehlke.sbb.reddit.R
import ch.zuehlke.sbb.reddit.features.BaseFragment
import ch.zuehlke.sbb.reddit.features.news.NavigationController
import ch.zuehlke.sbb.reddit.features.news.overview.OverviewFragmentKodeinModule.createNewsOverviewModule
import ch.zuehlke.sbb.reddit.features.news.overview.adapter.impl.RedditNewsDelegateAdapter.OnNewsSelectedListener
import ch.zuehlke.sbb.reddit.features.news.overview.adapter.impl.RedditOverviewAdapter
import ch.zuehlke.sbb.reddit.models.RedditNewsData
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with
import kotlinx.android.synthetic.main.fragment_overview.*

/**
 * Created by chsc on 11.11.17.
 */

class OverviewFragment : BaseFragment(), OverviewContract.View {

    override fun provideOverridingModule() = createNewsOverviewModule(this@OverviewFragment,listener)

    //Injections
    private val mOverviewPresenter: OverviewContract.Presenter by injector.with(this@OverviewFragment).instance()
    private val mNavigationController: NavigationController by injector.instance()
    private val mOverviewAdapter: RedditOverviewAdapter by injector.with(this@OverviewFragment).instance()


    private val listener = object: OnNewsSelectedListener {
        override fun onNewsSelected(url: String) {
            showRedditNewsDetails(url)
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = LayoutInflater.from(context).inflate(R.layout.fragment_overview,container,false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        redditNewsView.apply {
            adapter = mOverviewAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            clearOnScrollListeners()
        }

        val infiniteScrollListener = object : InfiniteScrollListener(redditNewsView.layoutManager as LinearLayoutManager) {
            override fun loadingFunction() {
                mOverviewPresenter!!.loadMoreRedditNews()
            }
        }

        redditNewsView.addOnScrollListener(infiniteScrollListener)

        // Set up progress indicator
        refreshLayout.apply {
            setColorSchemeColors(
                ContextCompat.getColor(activity, R.color.colorPrimary),
                ContextCompat.getColor(activity, R.color.colorAccent),
                ContextCompat.getColor(activity, R.color.colorPrimaryDark)
            )
            setScrollUpChild(redditNewsView)
            setOnRefreshListener {
                infiniteScrollListener.reset()
                mOverviewPresenter?.loadRedditNews(false)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mOverviewPresenter.start()
    }


    override val isActive: Boolean
        get() = isAdded

    override fun setLoadingIndicator(isActive: Boolean) {
        if (view == null) {
            return
        }
        // Make sure setRefreshing() is called after the layout is done with everything else.
        refreshLayout.post { refreshLayout.isRefreshing = isActive }
    }

    override fun showRedditNews(redditNews: List<RedditNewsData>) {
        mOverviewAdapter!!.clearAndAddNews(redditNews)
        redditNewsView!!.visibility = View.VISIBLE
        noRedditNewsView!!.visibility = View.GONE
    }

    override fun addRedditNews(redditNews: List<RedditNewsData>) {
        mOverviewAdapter.addRedditNews(redditNews)
    }

    override fun showRedditNewsLoadingError() {
        Snackbar.make(view!!, R.string.overview_screen_error_loading_reddit_news, Snackbar.LENGTH_LONG)
    }


    override fun showNoNews() {
        redditNewsView.visibility = View.GONE
        noRedditNewsView.visibility = View.VISIBLE
    }

    override fun showRedditNewsDetails(redditNewsUrl: String) {
        mNavigationController.showDetails(redditNewsUrl)
    }

    companion object {

        fun newInstance(): OverviewFragment {
            return OverviewFragment()
        }
    }


}