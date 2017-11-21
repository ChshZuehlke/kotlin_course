package ch.zuehlke.sbb.reddit.models

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import ch.zuehlke.sbb.reddit.TestData
import de.dabotz.shoppinglist.database.AppDatabase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by celineheldner on 21.11.17.
 */

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var redditDatabase: AppDatabase

    @Before
    fun initDb() {
        redditDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                AppDatabase::class.java
        ).build()
    }

    @Test
    fun retrieveRedditNewsData() {
        //when
        redditDatabase.redditNewsDataDao().addNewsItem(TestData.initialNews)
        val retrievedNews = redditDatabase.redditNewsDataDao().getNews()

        //then
        assert(retrievedNews.equals(TestData.initialNews.sortedWith(compareBy({ it.id }, { it.id }))))
    }

    @Test
    fun retrieveRedditPostsData() {
        //when
        redditDatabase.reditPostsDataDao().addPostItems(TestData.initialPosts)
        val retrievedPosts = redditDatabase.reditPostsDataDao().getPosts("/r/DotA2/comments/7ckn4x/nov_13_competitive_matches_southeast_asia/")

        //then
        assert(retrievedPosts.equals(TestData.initialPosts.sortedWith(compareBy({ it.id }, { it.id }))))
    }

    @Test
    fun deleteRedditPostsData() {
        //when
        redditDatabase.reditPostsDataDao().addPostItems(TestData.initialPosts)
        redditDatabase.reditPostsDataDao().deletePosts("/r/DotA2/comments/7ckn4x/nov_13_competitive_matches_southeast_asia/")
        val retrievedPosts = redditDatabase.reditPostsDataDao().getPosts("/r/DotA2/comments/7ckn4x/nov_13_competitive_matches_southeast_asia/")

        //then
        assert(retrievedPosts.isEmpty())
    }
}