package ch.zuehlke.sbb.reddit

import android.content.Context
import ch.zuehlke.sbb.reddit.data.FakeRedditNewsRemoteDataSource
import ch.zuehlke.sbb.reddit.data.source.RedditRepository
import ch.zuehlke.sbb.reddit.data.source.local.RedditNewsLocalDataSource
import ch.zuehlke.sbb.reddit.data.source.remote.RedditElementTypeAdapterFactory
import ch.zuehlke.sbb.reddit.util.AndroidUtils
import com.github.salomonbrys.kodein.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Created by celineheldner on 15.11.17.
 */

fun createBaseModule(context: Context) = Kodein.Module{


    bind<Gson>() with singleton {
        GsonBuilder()
                .registerTypeAdapterFactory(RedditElementTypeAdapterFactory.elementTypeAdapterFactory)
                .create()
    }

    bind<AndroidUtils>() with singleton { AndroidUtils(context) }

    bind<RedditRepository>() with eagerSingleton {
                RedditRepository(
                        FakeRedditNewsRemoteDataSource(),
                        RedditNewsLocalDataSource(context, instance()),
                        instance())
    }
}