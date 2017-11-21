package ch.zuehlke.sbb.reddit.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

import com.google.common.base.Preconditions.checkNotNull

/**
 * Created by chsc on 08.11.17.
 */

object ActivityUtils {

    /**
     * The `fragment` is added to the container view with id `frameId`. The operation is
     * performed by the `fragmentManager`.

     */
    fun addFragmentToActivity(fragmentManager: FragmentManager,
                              fragment: Fragment, frameId: Int) {
        checkNotNull(fragmentManager)
        checkNotNull(fragment)
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(frameId,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
