package com.mapeiyu.server

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import com.mapeiyu.messenger.server.ICallback
import com.mapeiyu.messenger.server.IMMessengerService
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * aidl通信服务
 */
class MMessengerService : Service() {

    companion object {
        private val cachedThreadPool: ExecutorService = Executors.newCachedThreadPool();
    }

    private val binder = object : IMMessengerService.Stub() {

        @Throws(RemoteException::class)
        override fun onRequestSync(handlerName: String, request: Bundle?, callback: ICallback?): Bundle? {
            val handler = SVHandlerManager.getInstance().getHandler(handlerName) ?: throw RemoteException("can't find handler for " + handlerName)
            return handler.onRequestSync(request, callback);
        }

        @Throws(RemoteException::class)
        override fun onRequestAsync(handlerName: String, request: Bundle?, callback: ICallback) {
            val handler = SVHandlerManager.getInstance().getHandler(handlerName) ?: throw RemoteException("can't find handler for " + handlerName)
            cachedThreadPool.execute { handler.onRequestAsync(request, callback) }
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }
}
