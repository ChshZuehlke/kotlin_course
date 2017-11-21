package ch.zuehlke.sbb.reddit.features

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import ch.zuehlke.sbb.reddit.features.news.NavigationController

/**
 * Created by chsc on 15.11.17.
 */


abstract class GenericBindingBaseAdapter(val clickListener: GenericBindingViewHolder.GenericBindingClickListener) : RecyclerView.Adapter<GenericBindingViewHolder>() {



    override fun onBindViewHolder(holder: GenericBindingViewHolder, position: Int) {
        val itemToBind = getObjForPosition(position)
        holder.bind(itemToBind)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): GenericBindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return GenericBindingViewHolder(binding,clickListener)
    }


    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForViewType(position)
    }

    protected abstract fun getObjForPosition(position: Int): Any

    protected abstract fun getLayoutIdForViewType(position: Int): Int

    protected abstract fun getViewTypeForPosition(position: Int): Int

}