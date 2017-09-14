package com.mapeiyu.server

import android.os.Bundle
import com.mapeiyu.messenger.server.ICallback

/**
 * SVHandler基类
 */
open class BaseSVHandler : ISVHandler {
    override fun onRequestSync(request: Bundle?, callback: ICallback?): Bundle? {
        return null
    }

    override fun onRequestAsync(request: Bundle?, callback: ICallback) {
    }
}
