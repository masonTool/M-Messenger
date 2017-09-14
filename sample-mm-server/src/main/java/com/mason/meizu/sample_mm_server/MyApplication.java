package com.mason.meizu.sample_mm_server;

import android.app.Application;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;

import com.mapeiyu.messenger.server.ICallback;
import com.mapeiyu.server.BaseSVHandler;
import com.mapeiyu.server.MServer;

/**
 * Created by mapeiyu on 17-9-8.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MServer.addHandler("Plus", new BaseSVHandler() {
            @Override
            public Bundle onRequestSync(Bundle request, ICallback callback) {
                int a = request.getInt("A");
                int b = request.getInt("B");

                Bundle result = new Bundle();
                result.putInt("RESULT", a + b);
                return result;
            }

            @Override
            public void onRequestAsync(final Bundle request, final @NonNull ICallback callback) {
                //模拟耗时操作
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int a = request.getInt("A");
                int b = request.getInt("B");

                Bundle result = new Bundle();
                result.putInt("RESULT", a + b);
                try {
                    callback.postResult(result);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
