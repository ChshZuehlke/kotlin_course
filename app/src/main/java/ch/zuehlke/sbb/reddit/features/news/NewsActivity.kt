package ch.zuehlke.sbb.reddit.features.news

import android.os.Bundle
import android.support.v7.widget.Toolbar
import ch.zuehlke.sbb.reddit.R
import ch.zuehlke.sbb.reddit.features.BaseActivity
import ch.zuehlke.sbb.reddit.features.news.NavigationController
import ch.zuehlke.sbb.reddit.features.news.createNewsActivityModule
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with

/**
 * Created by chsc on 11.11.17.
 */

class NewsActivity : BaseActivity() {

    override fun provideOverridingModule() = createNewsActivityModule(this@NewsActivity, R.id.contentFrame)

    private val mNavigationController: NavigationController by injector.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_overview)

        // Set up the toolbar.
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(false)

        mNavigationController.showOverview()
        // Create the presenter
    }


}
