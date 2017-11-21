package ch.zuehlke.sbb.reddit

import ch.zuehlke.sbb.reddit.models.RedditNewsData
import ch.zuehlke.sbb.reddit.models.RedditPostsData
import java.util.*

/**
 * Created by celineheldner on 21.11.17.
 */
object TestData{
    val redditPost1 = RedditPostsData("remote1",null,"author1","body1",19000,0,"bodyhtml1","/r/DotA2/comments/7ckn4x/nov_13_competitive_matches_southeast_asia/",0)
    val redditPost2 = RedditPostsData("remote2",null,"author2","body2",19000,0,"bodyhtml2","/r/DotA2/comments/7ckn4x/nov_13_competitive_matches_southeast_asia/",1)

    val initialPosts = Arrays.asList(redditPost1, redditPost2)

    val redditNews1 = RedditNewsData("author1","title1",4,19060,null,null,"remoteNews1","/r/DotA2/comments/7ckn4x/nov_13_competitive_matches_southeast_asia/")
    val redditNews2 = RedditNewsData("author2","title2",4,19060,null,null,"remoteNews2","/r/DotA2/comments/7ckn4x/nov_13_competitive_matches_southeast_asia/")

    val initialNews = Arrays.asList(redditNews1, redditNews2)
}