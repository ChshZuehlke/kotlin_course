package ch.zuehlke.sbb.reddit.features.login

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.AppCompatButton
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import ch.zuehlke.sbb.reddit.R
import ch.zuehlke.sbb.reddit.features.BaseFragment
import ch.zuehlke.sbb.reddit.features.news.NewsActivity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.SupportFragmentInjector
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with
import com.google.common.base.Strings

/**
 * Created by chsc on 08.11.17.
 */

class LoginFragment : BaseFragment(), LoginContract.View {


    override fun provideOverridingModule() = createLoginModule(this@LoginFragment)
    //injected
    private val mPresenter: LoginContract.Presenter by injector.with(this@LoginFragment).instance()


    private var mProgessBar: ProgressBar? = null
    private var mLoginButton: AppCompatButton? = null
    private var mUsername: TextInputEditText? = null
    private var mPassword: TextInputEditText? = null

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val root = inflater!!.inflate(R.layout.fragment_login, container, false)
        mProgessBar = root.findViewById<ProgressBar>(R.id.progressBar)
        mLoginButton = root.findViewById<AppCompatButton>(R.id.loginButton)
        mUsername = root.findViewById<TextInputEditText>(R.id.username)
        mPassword = root.findViewById<TextInputEditText>(R.id.password)

        mLoginButton!!.setOnClickListener { mPresenter.login(mUsername!!.text.toString(), mPassword!!.text.toString()) }


        mUsername!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Do nothing
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Do nothing
            }

            override fun afterTextChanged(editable: Editable) {
                if (editable.length > 0 && verifyIsEmail(editable.toString())) {
                    mUsername!!.error = null
                } else {
                    mUsername!!.error = getString(R.string.login_screen_invalid_email)
                }
            }
        })

        mPassword!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                if (verifyPasswordLength(editable.toString())) {
                    mPassword!!.error = null
                } else {
                    mPassword!!.error = getString(R.string.login_screen_invalid_password_length)
                }
            }
        })

        return root

    }

    override val isActive: Boolean
        get() = isAdded

    override fun setLoadingIndicator(isActive: Boolean) {
        mProgessBar!!.visibility = if (isActive) View.VISIBLE else View.GONE
    }

    override fun showInvalidUsername() {
        mUsername!!.error = getString(R.string.login_screen_invalid_username)
    }

    override fun showInvalidPassword() {
        mPassword!!.error = getString(R.string.login_screen_invalid_password)
    }


    override fun showRedditNews() {
        val intent = Intent(context, NewsActivity::class.java)
        startActivity(intent)
        activity.finish()
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
