package com.mapeiyu.server

import android.os.Bundle
import com.mapeiyu.messenger.server.ICallback

/**

 */
interface ISVHandler {
    fun onRequestSync(request: Bundle?, callback: ICallback?): Bundle?
    fun onRequestAsync(request: Bundle?, callback: ICallback)
}
