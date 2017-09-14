package com.mason.meizu.sample_mm_client;

import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mapeiyu.client.ConnectCallback;
import com.mapeiyu.client.MBridge;
import com.mapeiyu.client.MClient;
import com.mapeiyu.client.ResultCallback;

public class MainActivity extends AppCompatActivity {

    private Button connectButton;

    private Button syncRequestButton;

    private Button asyncRequestButton;

    private TextView syncText;

    private TextView asyncText;

    private MBridge mBridge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectButton = (Button) findViewById(R.id.button3);

        syncRequestButton = (Button) findViewById(R.id.button);

        asyncRequestButton = (Button) findViewById(R.id.button2);

        syncText = (TextView) findViewById(R.id.textView);

        asyncText = (TextView) findViewById(R.id.textView2);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBridge == null) {
                    MClient.connect(MainActivity.this, "com.mason.meizu.sample_mm_server", new ConnectCallback() {
                        @Override
                        public void onConnected(@NonNull MBridge bridge) {
                            mBridge = bridge;
                            connectButton.setText("连接成功: 点击断开");
                        }
                        @Override
                        public void onDisconnected() {
                            mBridge = null;
                            connectButton.setText("连接服务");
                        }
                        @Override
                        public void onError() {
                            mBridge = null;
                            connectButton.setText("连接出错： 点击重试");
                        }
                    });
                } else {
                    mBridge.release();
                }
            }
        });

        syncRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBridge == null) {
                    syncText.setText("服务未连接");
                } else {
                    Bundle request = new Bundle();
                    request.putInt("A", 1);
                    request.putInt("B", 2);
                    try {
                        Bundle result = mBridge.requestSync("Plus", request);
                        syncText.setText("1 + 2 请求结果为: " + result.getInt("RESULT"));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

        asyncRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBridge == null) {
                    asyncText.setText("服务未连接");
                } else {
                    Bundle request = new Bundle();
                    request.putInt("A", 3);
                    request.putInt("B", 4);
                    asyncText.setText("3 + 4 请求中......");

                    try {
                        mBridge.requestAsync("Plus", request, new ResultCallback(new Handler()) {
                            @Override
                            public void onResult(Bundle result) {
                                Log.e("DDD", Thread.currentThread() + "");
                                asyncText.setText("3 + 4 请求结果为: " + result.getInt("RESULT"));
                            }
                        });
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}
