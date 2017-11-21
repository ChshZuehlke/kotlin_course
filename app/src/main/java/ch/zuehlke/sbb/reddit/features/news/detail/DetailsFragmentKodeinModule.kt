package ch.zuehlke.sbb.reddit.features.news.detail


import ch.zuehlke.sbb.reddit.features.GenericBindingViewHolder
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidSupportFragmentScope
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.scopedSingleton

/**
 * Created by celineheldner on 17.11.17.
 */
fun createNewsDetailsModule(view: DetailContract.View, redditUrl: String,listener: GenericBindingViewHolder.GenericBindingClickListener) = Kodein.Module{

    bind<DetailContract.Presenter>() with scopedSingleton(androidSupportFragmentScope){
        DetailPresenter(view,instance(), it.arguments.getString(redditUrl))
    }

    bind<PostAdapter>() with scopedSingleton(androidSupportFragmentScope){
        PostAdapter(listener)
    }
}