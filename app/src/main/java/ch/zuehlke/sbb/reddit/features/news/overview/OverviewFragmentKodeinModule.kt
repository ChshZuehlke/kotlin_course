package ch.zuehlke.sbb.reddit.features.news.overview

import ch.zuehlke.sbb.reddit.features.news.overview.adapter.impl.RedditNewsDelegateAdapter
import ch.zuehlke.sbb.reddit.features.news.overview.adapter.impl.RedditOverviewAdapter
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidSupportFragmentScope
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.scopedSingleton

/**
 * Created by celineheldner on 17.11.17.
 */

object OverviewFragmentKodeinModule{
    fun createNewsOverviewModule(view: OverviewContract.View, listener: RedditNewsDelegateAdapter.OnNewsSelectedListener) = Kodein.Module{

        bind<OverviewContract.Presenter>() with scopedSingleton(androidSupportFragmentScope){
            OverviewPresenter(view, instance())
        }

        bind<RedditOverviewAdapter>() with scopedSingleton(androidSupportFragmentScope){
            RedditOverviewAdapter(listener)
        }
    }
}
