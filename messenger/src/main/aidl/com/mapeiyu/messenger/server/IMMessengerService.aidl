// IMMssengerServer.aidl
package com.mapeiyu.messenger.server;

import android.os.Bundle;
import com.mapeiyu.messenger.server.ICallback;

interface IMMessengerService {

    //同步请求
    Bundle onRequestSync(String handlerName, in Bundle request, ICallback callback);

    //异步请求
    void onRequestAsync(String handlerName, in Bundle request, ICallback callback);
}
