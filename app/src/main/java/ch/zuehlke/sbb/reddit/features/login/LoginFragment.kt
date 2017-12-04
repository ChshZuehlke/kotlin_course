package ch.zuehlke.sbb.reddit.features.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ch.zuehlke.sbb.reddit.R
import ch.zuehlke.sbb.reddit.features.BaseFragment
import ch.zuehlke.sbb.reddit.features.login.LoginActivityKodeinModule.createLoginModule
import ch.zuehlke.sbb.reddit.features.news.NewsActivity
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with
import com.google.common.base.Strings
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.concurrent.TimeUnit

/**
 * Created by chsc on 08.11.17.
 */

class LoginFragment : BaseFragment(), LoginContract.View {


    override fun provideOverridingModule() = createLoginModule(this@LoginFragment)
    //injected
    private val mPresenter: LoginContract.Presenter by injector.with(this@LoginFragment).instance()

    private val loginListener = View.OnClickListener { mPresenter!!.login(username.text.toString(), password.text.toString()) }

    private val disposable = CompositeDisposable()

    private val usernameTextObservable = Observable.create(ObservableOnSubscribe<String> { emitter ->
        // Isolated callback connected to the emitter
        val textWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(editable: Editable) {
                emitter.onNext(editable.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }
        }
        // Register on subscription
        username.addTextChangedListener(textWatcher)
        // De-register on cancellation
        emitter.setCancellable {
            username.removeTextChangedListener(textWatcher)
        }
    })

    private val passwordTextObservable = Observable.create(ObservableOnSubscribe<String> { emitter ->
        val passwordListener = object : TextWatcher{
            override fun afterTextChanged(editable: Editable) {
                emitter.onNext(editable.toString())
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // DO nothing
            }


            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }
        }
        password.addTextChangedListener(passwordListener)
        emitter.setCancellable {
            password.removeTextChangedListener(passwordListener)
        }
    })

    override fun onResume() {
        super.onResume()

        mPresenter.start()
        loginButton.setOnClickListener(loginListener)

        disposable.add(usernameTextObservable
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::validateUsername))

        disposable.add(passwordTextObservable
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::validatePassword));
    }

    override fun onPause() {
        loginButton.setOnClickListener(null)
        disposable.clear()

        super.onPause()
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = LayoutInflater.from(context).inflate(R.layout.fragment_login, container, false)


    override val isActive: Boolean
        get() = isAdded

    override fun setLoadingIndicator(isActive: Boolean) {
        progressBar.visibility = if (isActive) View.VISIBLE else View.GONE
    }

    override fun showInvalidUsername() {
        username.error = getString(R.string.login_screen_invalid_username)
    }

    override fun showInvalidPassword() {
        password.error = getString(R.string.login_screen_invalid_password)
    }

    override fun showInvalidPasswordTimeout(secondsToWait: Int) {
        Toast.makeText(view!!.context,
                resources.getString(R.string.login_screen_invalid_password_timeout, secondsToWait),
                Toast.LENGTH_SHORT
        ).show();
    }

    override fun showLoginError(message: String) {
        Toast.makeText(view!!.context,
                message,
                Toast.LENGTH_LONG).show()
    }

    override fun showRedditNews() {
        val intent = Intent(context, NewsActivity::class.java)
        startActivity(intent)
        activity.finish()
    }

    private fun validatePassword(string: String) {
        if (verifyPasswordLength(string)) {
            password!!.error = null
        } else {
            password!!.error = getString(R.string.login_screen_invalid_password_length)
        }
    }

    private fun validateUsername(text: String) {
        if (text.length > 0 && verifyIsEmail(text)) {
            username!!.error = null
        } else {
            username!!.error = getString(R.string.login_screen_invalid_email)
        }
    }

    private fun verifyPasswordLength(password: String): Boolean {
        return !Strings.isNullOrEmpty(password) && password.length >= 6
    }

    private fun verifyIsEmail(email: String): Boolean {
        val matcher = android.util.Patterns.EMAIL_ADDRESS.matcher(email)
        return matcher.matches()
    }

    companion object {

        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }
}
