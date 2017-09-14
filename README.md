# 跨进程通信工具MMessenger

版本更新见 [历史版本](LOG.md)

## 工具说明

MMessenger是一个跨进程通信工具， 与系统提供的Messenger不同。 MM可以拓展了功能， 支持同步， 异步， 注册操作等等。彻底封装实现逻辑， 为使用者提供了方便了拓展和便利的接口使用。

## 使用方法

```
dependencies {
    compile "com.mapeiyu.messenger:messenger:1.0.0"
}
```

工具分为两部分， Server端功能支持与Client端功能支持。下面我们分两步来讲解。

### Server端

Server端是用来提供服务为外部使用的， 我们提供了便利的接口来方便Server端的功能拓展。

在Application中添加处理器BaseSVHandler, 其中定义handlerName方便三方找到此方法.

```
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
```

其中 `onRequestSync`为同步接口， 可以使Client端直接获得返回结果。`onRequestAsync`为异步接口， 在异步线程中执行，操作结果通过回调的形式返回

这样设置后， 接入方就拥有了， 提供给外部Client使用的能力。

### Client端

Client是服务的使用方， 在使用服务时， 有下面几个步骤。

1. 连接服务

* 异步操作方式， 可用在所有场景
`MClient.connect(context: Context, target: String, connectCallback: IConnectCallback)`

其中target为目标server的包名.

2. 请求结果

请求结果也存在同步，异步两种方式， 与上面Server端的两种实现相对应.一个完整的Client端实现是这样的。注意Client端与Server端一定是对应的。

```
MClient.connect(this, "com.meizu.launcher", new ConnectCallback() {
    @Override
    public void onConnected(@NonNull MBridge bridge) {
        Bundle request = new Bundle();
        request.putInt("A", 1);
        request.putInt("B", 2);
        try {
            //同步请求的例子
            Bundle result = bridge.requestSync("Plus", request);
            Log.e("result", result.getString("RESULT") + "=");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            //异步请求的例子
            bridge.requestAsync("Plus", request, new ResultCallback() {
                @Override
                public void onResult(Bundle result) throws RemoteException {
                    Log.e("result", result.getString("RESULT") + "=");
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
});
```





