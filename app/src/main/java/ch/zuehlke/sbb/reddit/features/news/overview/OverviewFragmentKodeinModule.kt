package ch.zuehlke.sbb.reddit.features.news.overview

import ch.zuehlke.sbb.reddit.features.GenericBindingViewHolder
import ch.zuehlke.sbb.reddit.features.news.ViewTypeAwareAdapter
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidSupportFragmentScope
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.scopedSingleton

/**
 * Created by celineheldner on 17.11.17.
 */

fun createNewsOverviewModule(view: OverviewContract.View, listener: GenericBindingViewHolder.GenericBindingClickListener) = Kodein.Module{

    bind<OverviewContract.Presenter>() with scopedSingleton(androidSupportFragmentScope){
        OverviewPresenter(view, instance())
    }

    bind<ViewTypeAwareAdapter>() with scopedSingleton(androidSupportFragmentScope){
        ViewTypeAwareAdapter(listener)
    }
}