package ch.zuehlke.sbb.reddit.data.source.local

import android.os.AsyncTask
import io.reactivex.internal.operators.single.SingleDoOnSuccess

/**
 * Created by celineheldner on 17.11.17.
 */
class AsyncDBAccessor<OUT>(val callDB: () -> OUT, val onSuccess: (OUT) -> Any = {} ): AsyncTask<Void,Void,OUT>(){

    override fun doInBackground(vararg params: Void?): OUT? {
        return run(callDB)
    }

    override fun onPostExecute(result: OUT) {
        super.onPostExecute(result)
        run{ onSuccess(result) }
    }

    fun go(){
        execute()
    }

}