package ch.zuehlke.sbb.reddit.features.news.overview

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.OnScrollListener
import android.util.Log

/**
 * Created by chsc on 12.11.17.
 */

abstract class InfiniteScrollListener(private val mLayoutManager: LinearLayoutManager) : OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private var visibleThreshold = 2
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) {
            visibleItemCount = recyclerView!!.childCount
            totalItemCount = mLayoutManager.itemCount
            firstVisibleItem = mLayoutManager.findFirstCompletelyVisibleItemPosition()
            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }
            }
            if (!loading && totalItemCount - visibleItemCount >= firstVisibleItem + visibleThreshold) {
                Log.i(TAG, "End reached")
            }
            if (!loading) {
                loadingFunction()
                loading = true
            }

        }
    }

    fun reset() {
        previousTotal = 0
        loading = false
        visibleThreshold = 2
        firstVisibleItem = 0
        visibleItemCount = 0
        totalItemCount = 0

    }

    abstract fun loadingFunction()

    companion object {

        private val TAG = "InfiniteScrollListener"
    }
}
