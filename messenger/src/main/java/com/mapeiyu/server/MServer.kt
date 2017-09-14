package com.mapeiyu.server

/**
 * server添加逻辑管理
 * Created by mapeiyu on 17-9-4.
 */

object MServer {

    @JvmStatic
    fun addHandler(handlerName: String, handler: ISVHandler) {
        SVHandlerManager.getInstance().addHandler(handlerName, handler)
    }
}
