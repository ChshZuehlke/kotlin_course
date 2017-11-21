package ch.zuehlke.sbb.reddit.data.source

import ch.zuehlke.sbb.reddit.kodein.createBaseModule
import ch.zuehlke.sbb.reddit.mock.RedditDataSourceMock
import ch.zuehlke.sbb.reddit.models.RedditNewsData
import ch.zuehlke.sbb.reddit.models.RedditPostsData
import ch.zuehlke.sbb.reddit.util.AndroidUtils
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber
import junit.framework.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by celineheldner on 20.11.17.
 */
class RedditRepositoryTest {

    val kodein = Kodein {
        import(createBaseModule())
    }
    val repositoryToTest: RedditRepository = kodein.instance()


    @Before
    fun setUp() {

    }

    @Test
    fun getMoreNews() {
        assert(true)
    }

    @Test
    fun getNews() {
        //TODO this test does not terminate
        //given
        repositoryToTest.refreshNews()
        val testSubscriber = TestSubscriber<List<ch.zuehlke.sbb.reddit.models.RedditNewsData>>()

        //when
        repositoryToTest.news.subscribe(testSubscriber)
        testSubscriber.onNext(emptyList())

        //then
        testSubscriber.assertNoErrors()
        testSubscriber.awaitTerminalEvent()
        testSubscriber.assertComplete()
        val news = testSubscriber.values()
        assert(news.size==2)
    }


    @Test
    fun refreshNews() {
        assert(true)
    }

    @Test
    fun getPosts() {
        //when
        val testObserver = TestObserver<List<ch.zuehlke.sbb.reddit.models.RedditPostsData>>()
        repositoryToTest.posts("/r/DotA2/comments/7ckn4x/nov_13_competitive_matches_southeast_asia/").subscribe(testObserver)

        //then
        val posts =  testObserver.values()
        assert(posts.size==2)
        Assert.assertEquals(0,posts.get(0).size)
        Assert.assertEquals(RedditDataSourceMock.initialPosts,posts.get(1))
    }
}