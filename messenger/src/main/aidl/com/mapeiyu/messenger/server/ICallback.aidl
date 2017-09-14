// ICallback.aidl
package com.mapeiyu.messenger.server;

import android.os.Bundle;

interface ICallback {
    void postResult(in Bundle result);
}
