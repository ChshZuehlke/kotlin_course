package ch.zuehlke.sbb.reddit.features.news.detail

import ch.zuehlke.sbb.reddit.kodein.createBaseModule
import ch.zuehlke.sbb.reddit.util.AndroidUtils
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.nhaarman.mockito_kotlin.*
import org.junit.Test

/**
 * Created by celineheldner on 20.11.17.
 */
class DetailPresenterTest {

    val kodein = Kodein {
        bind<AndroidUtils>() with singleton {
            mock<AndroidUtils> {
                on { isNetworkAvailable() } doReturn true
            }
        }
        import(createBaseModule())
    }

    var presenter: DetailPresenter
    val view: DetailContract.View

    init {
        view = mock<DetailContract.View>() { on { isActive } doReturn true }
        presenter = DetailPresenter(view, kodein.instance(), "/r/DotA2/comments/7ckn4x/nov_13_competitive_matches_southeast_asia/")
    }

    @Test
    fun loadRedditPostsOnNetworkAvailable() {
        presenter.loadRedditPosts()
        verify(view, times(2)).showRedditPosts(any())
    }

}