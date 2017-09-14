package com.mapeiyu.client

/**
 * 连接回调
 * Created by mapeiyu on 17-9-4.
 */
interface IConnectCallback {
    fun onConnected(bridge: MBridge)
    fun onDisconnected()
    fun onError()
}
