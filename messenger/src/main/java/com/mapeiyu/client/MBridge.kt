package com.mapeiyu.client

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.Bundle
import android.os.RemoteException
import com.mapeiyu.messenger.server.ICallback
import com.mapeiyu.messenger.server.IMMessengerService

/**
 * 用于aidl的客户端的请求处理.
 * Created by mapeiyu on 17-9-4.
 */
class MBridge internal constructor(private val context: Context) {
    @Volatile internal var remoteService: IMMessengerService? = null
    var connection: ServiceConnection? = null
    var component: ComponentName? = null


    /**
     * 发送同步请求, 直接返回处理结果
     * @param handler
     * *
     * @param request
     * *
     * @return
     * *
     * @throws RemoteException
     */
    @Throws(RemoteException::class)
    fun requestSync(handler: String, request: Bundle?, callback: ICallback?): Bundle? {
        return remoteService!!.onRequestSync(handler, request, callback)
    }

    /**
     * 发送同步请求, 直接返回处理结果
     * @param handler
     * *
     * @param request
     * *
     * @return
     * *
     * @throws RemoteException
     */
    @Throws(RemoteException::class)
    fun requestSync(handler: String, request: Bundle?): Bundle? {
        return requestSync(handler, request, null)
    }

    /**
     * 发送异步请求， 通过callback返回结果
     * @param handler
     * *
     * @param request
     * *
     * @param callback
     * *
     * @throws RemoteException
     */
    @Throws(RemoteException::class)
    fun requestAsync(handler: String, request: Bundle?, callback: ICallback) {
        remoteService!!.onRequestAsync(handler, request, callback)
    }

    /**
     * 释放连接
     */
    fun release() {
        if (component != null) {
            context.unbindService(connection!!)
            connection!!.onServiceDisconnected(component)
            connection = null
            component = null
        }
    }

}
