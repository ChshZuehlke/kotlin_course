package ch.zuehlke.sbb.reddit.features.news.detail

import android.databinding.BindingAdapter
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import ch.zuehlke.sbb.reddit.util.DateUtils

/**
 * Created by chsc on 16.11.17.
 */

object PostDataBindingAdapter{


    @JvmStatic
    @BindingAdapter("friendlyTime")
    fun setFriendlyTime(textView: TextView, date: Long){
        textView.text = DateUtils.friendlyTime(date)
    }

    @JvmStatic
    @BindingAdapter("android:paddingLeft")
    fun setPostPadding(view: View, depth: Int){
        (setDepthPadding(view, depth))
    }

    private fun setDepthPadding(view: View, depth: Int) {
        val normalPadding = 5f
        val leftPadding = normalPadding + depth * 10f

        val normalDp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, normalPadding, view.context.resources.displayMetrics).toInt()
        val leftDp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftPadding, view.context.resources.displayMetrics).toInt()
        view.setPadding(leftDp, normalDp, normalDp, normalDp)
    }
}