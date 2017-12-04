package ch.zuehlke.sbb.reddit.features.login

import com.google.common.base.Preconditions.checkNotNull
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit

/**
 * Created by chsc on 08.11.17.
 */

class LoginPresenter(view: LoginContract.View) : LoginContract.Presenter {

    private val mLoginView: LoginContract.View


    init {
        this.mLoginView = checkNotNull(view, "LoginView cannot be null")
    }

    override fun start() {
        // Do nothing here, as we don't load any redditPost
    }

    override fun stop() {
        // Do nothing here, as we don't load any redditPost
    }

    override fun login(userEmail: String, password: String) {
        mLoginView.setLoadingIndicator(true)
        // Simulate a 'long' network call to verify the credentials

        launch(UI) {
            doLogin(userEmail, password)
            if (mLoginView.isActive) {
                mLoginView.setLoadingIndicator(false)
            }
        }
    }

    private suspend fun doLogin(userName: String, password: String) {
        try {
            var hasError = false
            if (!checkUsername(userName)) {
                mLoginView.showInvalidUsername()
                hasError = true
            }

            if (!verifyPassword(password, userName)) {
                mLoginView.showInvalidPassword()
                hasError = true
            }

            if (!hasError) {
                mLoginView.showRedditNews()
            }
        } catch (e: Exception) {
            mLoginView.showLoginError(e.message ?: "Unbekannter Fehler")
        }
    }

    private suspend fun verifyPassword(password: String, userEmail: String) : Boolean {
        val passwordValid = password == "123456"
        if(!passwordValid) {
            mLoginView.showInvalidPasswordTimeout(10)
            delay(10, TimeUnit.SECONDS)
        }
        return passwordValid
    }

    private suspend fun checkUsername(userEmail: String) : Boolean {
        delay(1000, TimeUnit.MILLISECONDS)
        if(userEmail.endsWith("ch")) {
            throw IllegalArgumentException("Switzerland is NOT allowed!")
        }
        return userEmail == "test.tester@test.com"
    }


}
