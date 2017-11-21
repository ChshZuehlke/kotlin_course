package ch.zuehlke.sbb.reddit

import android.database.Observable

/**
 * Created by chsc on 08.11.17.
 */

interface BaseView<in T> where T: BasePresenter {


    val isActive: Boolean
}
