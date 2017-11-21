package ch.zuehlke.sbb.reddit.features.news.detail

import android.databinding.ViewDataBinding
import android.util.TypedValue
import android.view.View
import ch.zuehlke.sbb.reddit.BR
import ch.zuehlke.sbb.reddit.features.GenericBindingViewHolder
import ch.zuehlke.sbb.reddit.features.news.NavigationController
import ch.zuehlke.sbb.reddit.models.RedditPostsData

/**
 * Created by chsc on 15.11.17.
 */


class PostBindingViewHolder(binding: ViewDataBinding,listener: GenericBindingClickListener) : GenericBindingViewHolder(binding,listener) {

    override fun bind(obj: Any) {
        val post = obj as RedditPostsData
        binding.setVariable(BR.obj,obj)
        binding.setVariable(BR.listener,listener)
        binding.executePendingBindings()

    }


}
