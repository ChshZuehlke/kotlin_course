package ch.zuehlke.sbb.reddit.features


import android.os.Bundle
import android.support.v4.app.Fragment
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.SupportFragmentInjector

/**
 * Created by celineheldner on 17.11.17.
 */
open class BaseFragment : Fragment(), SupportFragmentInjector {

    override val injector: KodeinInjector = KodeinInjector()

    override fun onCreate(savedInstanceState: Bundle?) {
        initializeInjector()
        super.onCreate(savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        destroyInjector()
    }

}