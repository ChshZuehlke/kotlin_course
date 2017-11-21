package ch.zuehlke.sbb.reddit.mock

import ch.zuehlke.sbb.reddit.models.RedditNewsData
import ch.zuehlke.sbb.reddit.models.RedditPostsData
import java.util.*

/**
 * Created by celineheldner on 20.11.17.
 */
open class RedditDataSourceMock{
    companion object {
        val redditPost1 = RedditPostsData("remote1",null,"author1","body1",19000,0,"bodyhtml1","/r/DotA2/comments/7ckn4x/nov_13_competitive_matches_southeast_asia/",0)
        val redditPost2 = RedditPostsData("remote2",null,"author2","body2",19000,0,"bodyhtml2","/r/DotA2/comments/7ckn4x/nov_13_competitive_matches_southeast_asia/",1)

        val initialPosts = Arrays.asList(redditPost1, redditPost2)

        val redditNews1 = RedditNewsData("author1","title1",4,19060,null,null,"remoteNews1","/r/DotA2/comments/7ckn4x/nov_13_competitive_matches_southeast_asia/")
        val redditNews2 = RedditNewsData("author2","title2",4,19060,null,null,"remoteNews2","/r/DotA2/comments/7ckn4x/nov_13_competitive_matches_southeast_asia/")

        val initialNews = Arrays.asList(redditNews1, redditNews2)

        val redditNews3 = RedditNewsData("author3","title3",4,19060,null,null,"remoteNews3","/r/DotA2/comments/7ckn4x/nov_13_competitive_matches_southeast_asia/")
        val redditNews4 = RedditNewsData("author4","title4",4,19060,null,null,"remoteNews4","/r/DotA2/comments/7ckn4x/nov_13_competitive_matches_southeast_asia/")

        val initialMoreNews = Arrays.asList(redditNews3, redditNews4)

    }
     val redditPosts = ArrayList<RedditPostsData>()
     val redditNews = ArrayList<RedditNewsData>()
     val moreNews = ArrayList<RedditNewsData>()

    init {
        redditPosts.addAll(initialPosts)
        redditNews.addAll(initialNews)
        moreNews.addAll(initialMoreNews)
    }

}