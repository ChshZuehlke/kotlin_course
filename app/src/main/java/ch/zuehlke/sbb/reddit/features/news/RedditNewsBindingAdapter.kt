package ch.zuehlke.sbb.reddit.features.news


import android.content.Intent
import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ch.zuehlke.sbb.reddit.R
import ch.zuehlke.sbb.reddit.features.news.NavigationController
import ch.zuehlke.sbb.reddit.models.RedditNewsData
import ch.zuehlke.sbb.reddit.util.DateUtils
import com.google.common.base.Strings
import com.squareup.picasso.Picasso

/**
 * Created by chsc on 15.11.17.
 */


object RedditNewsBindingAdapter{

    @JvmStatic
    @BindingAdapter("imageUrl","placeHolder",requireAll = false )
    fun setImageUrl(imageView: ImageView, url: String?, placeHolder: Drawable?){
        if(Strings.isNullOrEmpty(url) && placeHolder != null){
            imageView.setImageDrawable(placeHolder)
        }else if(!com.google.common.base.Strings.isNullOrEmpty(url)){
            Picasso.with(imageView.context).load(url).into(imageView)
        }else{
            Picasso.with(imageView.context).load(R.drawable.reddit_placeholder).into(imageView)
        }
    }


    @JvmStatic
    @BindingAdapter("friendlyTime")
    fun setFriendlyTime(textView: TextView, date: Long){
        textView.text = DateUtils.friendlyTime(date)
    }

    @JvmStatic
    fun showRedditPost(view: View, redditNews: RedditNewsData){
        TODO("delete or use!")
        //navigationController.showDetails(redditNews.permaLink)
    }
}