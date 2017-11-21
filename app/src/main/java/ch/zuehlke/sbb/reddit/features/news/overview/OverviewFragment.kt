package ch.zuehlke.sbb.reddit.features.news.overview

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.zuehlke.sbb.reddit.R
import ch.zuehlke.sbb.reddit.features.BaseFragment
import ch.zuehlke.sbb.reddit.features.GenericBindingViewHolder
import ch.zuehlke.sbb.reddit.features.news.NavigationController
import ch.zuehlke.sbb.reddit.features.news.ViewTypeAwareAdapter
import ch.zuehlke.sbb.reddit.models.RedditNewsData
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with

/**
 * Created by chsc on 11.11.17.
 */

class OverviewFragment : BaseFragment(), OverviewContract.View {


    private val clickListener = object : GenericBindingViewHolder.GenericBindingClickListener {
        override fun onItemSelected(obj: Any) {
            if (obj is RedditNewsData && obj.permaLink != null) {
                showRedditNewsDetails(obj.permaLink!!)
            }
        }
    }

    override fun provideOverridingModule() = createNewsOverviewModule(this@OverviewFragment, clickListener)

    //Injections
    private val mOverviewPresenter: OverviewContract.Presenter by injector.with(this@OverviewFragment).instance()
    private val mNavigationController: NavigationController by injector.instance()
    private val mOverviewAdapter: ViewTypeAwareAdapter by injector.with(this@OverviewFragment).instance()

    private var mNoNewsView: View? = null
    private var mNewsView: RecyclerView? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater!!.inflate(R.layout.fragment_overview, container, false)
        mNoNewsView = root.findViewById<View>(R.id.noRedditNewsView)
        mNewsView = root.findViewById<RecyclerView>(R.id.redditNewsView)
        mNewsView!!.layoutManager = LinearLayoutManager(context)
        mNewsView!!.adapter = mOverviewAdapter

        // Set up progress indicator
        val swipeRefreshLayout = root.findViewById<View>(R.id.refreshLayout) as ScrollChildSwipeRefreshLayout
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(activity, R.color.colorPrimary),
                ContextCompat.getColor(activity, R.color.colorAccent),
                ContextCompat.getColor(activity, R.color.colorPrimaryDark)
        )

        val infiniteScrollListener = object : InfiniteScrollListener(mNewsView!!.layoutManager as LinearLayoutManager) {
            override fun loadingFunction() {
                mOverviewPresenter.loadMoreRedditNews()
            }
        }
        swipeRefreshLayout.setScrollUpChild(mNewsView!!)
        swipeRefreshLayout.setOnRefreshListener {
            infiniteScrollListener.reset()
            mOverviewPresenter.loadRedditNews(true)
        }


        mNewsView!!.setHasFixedSize(true)
        mNewsView!!.clearOnScrollListeners()
        mNewsView!!.addOnScrollListener(infiniteScrollListener)

        return root
    }


    override fun onResume() {
        super.onResume()
        mOverviewPresenter.start()
    }

    override fun onPause() {
        super.onPause()
        mOverviewPresenter.stop()
    }

    override val isActive: Boolean
        get() = isAdded

    override fun setLoadingIndicator(isActive: Boolean) {
        if (view == null) {
            return
        }
        val srl = view!!.findViewById<SwipeRefreshLayout>(R.id.refreshLayout)
        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post { srl.isRefreshing = isActive }
    }

    override fun showRedditNews(redditNews: List<RedditNewsData>) {
        mOverviewAdapter.clearAndAddNews(redditNews)
        mNewsView!!.visibility = View.VISIBLE
        mNoNewsView!!.visibility = View.GONE
    }


    override fun addRedditNews(redditNews: List<RedditNewsData>) {
        mOverviewAdapter.addRedditNews(redditNews)
    }

    override fun showRedditNewsLoadingError() {
        Snackbar.make(view!!, R.string.overview_screen_error_loading_reddit_news, Snackbar.LENGTH_LONG)
    }


    override fun showNoNews() {
        mNewsView!!.visibility = View.GONE
        mNoNewsView!!.visibility = View.VISIBLE
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
