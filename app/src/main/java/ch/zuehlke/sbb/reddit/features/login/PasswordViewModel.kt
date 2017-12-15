package ch.zuehlke.sbb.reddit.features.login

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import ch.zuehlke.sbb.reddit.BR
import android.text.Editable
import android.text.TextWatcher
import ch.zuehlke.sbb.reddit.R
import com.google.common.base.Strings
import kotlin.properties.Delegates

/**
 * Created by chsc on 14.12.17.
 */

class PasswordViewModel(private val context: Context): BaseObservable(){

    private var password : String by Delegates.observable("Enter a password",{_, oldValue, newValue ->
        if(oldValue != newValue){
            notifyPropertyChanged(BR.passwordQuality)
        }
    })

    private var error: String? by Delegates.observable<String?>(null,{_, _, _ ->
        notifyPropertyChanged(BR.passwordError)
    })


    @Bindable
    fun getPasswordQuality(): String {
        if (password.isEmpty()) {
            return "Enter a password"
        } else if (password.equals("1234")) {
            return "Very bad"
        } else if (password.length < 6) {
            return "Short"
        } else {
            return "Okay"
        }
    }

    @Bindable
    fun getPasswordError() = error

    @Bindable
    fun getPasswordTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                password = s.toString()
            }

            override fun afterTextChanged(s: Editable) {
                error = if(verifyPasswordLength(s.toString())) null else context.getString(R.string.login_screen_invalid_password_length)
             }
        }
    }

    private fun verifyPasswordLength(password: String): Boolean {
        return !Strings.isNullOrEmpty(password) && password.length >= 6
    }

}

