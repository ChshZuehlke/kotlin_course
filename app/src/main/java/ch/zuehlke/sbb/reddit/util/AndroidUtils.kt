package ch.zuehlke.sbb.reddit.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by celineheldner on 19.11.17.
 */
class AndroidUtils(private val mContext: Context){

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}