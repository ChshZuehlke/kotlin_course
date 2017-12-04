package ch.zuehlke.sbb.reddit.features.login

import ch.zuehlke.sbb.reddit.BasePresenter
import ch.zuehlke.sbb.reddit.BaseView

/**
 * Created by chsc on 08.11.17.
 */

interface LoginContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(isActive: Boolean)

        fun showInvalidUsername()

        fun showInvalidPassword()

        fun showRedditNews()

        fun showInvalidPasswordTimeout(secondsToWait: Int)

        fun showLoginError(message: String)
    }

    interface Presenter : BasePresenter {

        fun login(userEmail: String, password: String)
    }
}
