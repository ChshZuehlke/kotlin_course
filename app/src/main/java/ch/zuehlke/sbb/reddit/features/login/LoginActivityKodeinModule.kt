package ch.zuehlke.sbb.reddit.features.login

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidSupportFragmentScope
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.scopedSingleton

/**
 * Created by celineheldner on 16.11.17.
 */

fun createLoginModule(view: LoginContract.View) = Kodein.Module {

    bind<LoginContract.Presenter>() with scopedSingleton(androidSupportFragmentScope) {
        LoginPresenter(view)
    }

}