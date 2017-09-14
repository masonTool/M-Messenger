package com.mapeiyu.client

import android.os.Bundle
import android.os.Handler
import android.os.RemoteException
import com.mapeiyu.messenger.server.ICallback


/**
 * Created by mapeiyu on 17-9-11.
 */

abstract class ResultCallback(handler: Handler?) : ICallback.Stub() {

    private var handler: Handler? = null

    init {
        if (handler != null) {
            this.handler = Handler(handler.looper)
        }
    }

    @Throws(RemoteException::class)
    override fun postResult(result: Bundle) {
        if (handler == null) {
            onResult(result)
        } else {
            handler!!.post { onResult(result) }
        }
    }

    protected abstract fun onResult(result: Bundle)
}
