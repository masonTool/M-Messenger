package com.mapeiyu.server

import android.util.Log

import java.util.HashMap

/**
 * SVHandler管理
 * Created by mapeiyu on 17-9-4.
 */
class SVHandlerManager private constructor(){

    companion object {
        @Volatile
        private var sinstance: SVHandlerManager? = null

        fun getInstance(): SVHandlerManager {
            if (sinstance == null) {
                synchronized(SVHandlerManager::class) {
                    if (sinstance == null) {
                        sinstance = SVHandlerManager()
                    }
                }
            }
            return sinstance!!
        }
    }


    private val handlerMap = HashMap<String, ISVHandler>()

    /**
     * 添加Handler支持
     * @param handlerTag
     * *
     * @param handler
     */
    fun addHandler(handlerTag: String, handler: ISVHandler) {
        if (handlerMap.containsKey(handlerTag)) {
            Log.e("SVHandlerManager", "dumplicated hander " + handlerTag)
        }
        handlerMap.put(handlerTag, handler)
    }

    fun getHandler(handlerTag: String): ISVHandler? {
        return handlerMap[handlerTag]
    }
}
