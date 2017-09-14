package com.mapeiyu.client

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.mapeiyu.messenger.server.IMMessengerService


/**
 * MClient用于创建远程连接。
 * Created by mapeiyu on 17-9-4.
 */
object MClient {
    private val SUFFIX = ".MMessenger"

    /**
     * 异步获取连接桥
     * @param context
     * *
     * @param target
     * *
     * @param connectCallback
     */
    @JvmStatic
    fun connect(context: Context, target: String, connectCallback: IConnectCallback) {
        val connection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                val bridge = MBridge(context.applicationContext)
                bridge.connection = this
                bridge.component = name
                bridge.remoteService = IMMessengerService.Stub.asInterface(service)
                connectCallback.onConnected(bridge)
            }

            override fun onServiceDisconnected(name: ComponentName) {
                connectCallback.onDisconnected()
            }
        }

        val intent = Intent(target + SUFFIX)
        intent.`package` = target
        if (!context.applicationContext.bindService(intent, connection, Context.BIND_AUTO_CREATE)) {
            connectCallback.onError()
        }
    }
}
