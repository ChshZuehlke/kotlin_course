package ch.zuehlke.sbb.reddit.features.login

import android.databinding.BindingAdapter
import android.widget.EditText

/**
 * Created by chsc on 14.12.17.
 */
object LoginBindingAdapter {

    @JvmStatic
    @BindingAdapter("errorText")
    fun bindErrorText(editText: EditText,errorText: String?){
        editText.error = errorText
    }
}