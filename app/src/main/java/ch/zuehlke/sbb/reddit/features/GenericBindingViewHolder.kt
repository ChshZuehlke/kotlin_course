package ch.zuehlke.sbb.reddit.features

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import ch.zuehlke.sbb.reddit.BR
import ch.zuehlke.sbb.reddit.features.news.NavigationController


/**
 * Created by chsc on 15.11.17.
 */


open class GenericBindingViewHolder(protected val binding: ViewDataBinding,protected val  listener: GenericBindingClickListener): RecyclerView.ViewHolder(binding.root){

    open fun bind(obj: Any){
        binding.setVariable(BR.obj,obj)
        binding.setVariable(BR.listener,listener)
        binding.executePendingBindings()
    }

    interface GenericBindingClickListener{
        fun onItemSelected(obj: Any)
    }
}