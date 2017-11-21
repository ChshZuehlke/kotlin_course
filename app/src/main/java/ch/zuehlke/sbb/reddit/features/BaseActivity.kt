package ch.zuehlke.sbb.reddit.features

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.FragmentActivityInjector

/**
 * Created by celineheldner on 17.11.17.
 */
open class BaseActivity: AppCompatActivity(), FragmentActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyInjector()
    }


}