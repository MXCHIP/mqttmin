package com.fog.mqtt;

import io.fogcloud.fog_mqtt.api.MQTT;
import io.fogcloud.fog_mqtt.helper.ListenDeviceCallBack;
import io.fogcloud.fog_mqtt.helper.ListenDeviceParams;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MQTTActivity extends Activity {
	private String TAG = "---mqtt---";
	private final int _EL_S = 1;
	private final int _EL_F = 2;
	
	private Context context;

	private TextView startpub;
	private TextView logsid;

	private MQTT mqtt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = MQTTActivity.this;

		initView();
		initOnClick();

		mqtt = new MQTT(context);
		connectmqtt();
	}

	private void initView() {
		startpub = (TextView) findViewById(R.id.startpub);
		logsid = (TextView) findViewById(R.id.logsid);
	}

	private void initOnClick() {
		startpub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setTag();
			}
		});
	}

	private void connectmqtt() {
		ListenDeviceParams ldp = new ListenDeviceParams();
		ldp.host = "api.easylink.io";
		ldp.port = "1883";
		ldp.userName = "admin";
		ldp.passWord = "admin";
		ldp.topic = "d64f517c/c8934691813c/#";
		ldp.clientID = "client-000xxx";
		ldp.isencrypt = false;

		mqtt.startMqtt(ldp, new ListenDeviceCallBack() {

			@Override
			public void onSuccess(int code, String message) {
				Log.d(TAG, message);
				send2handler(_EL_S, message);
			}

			@Override
			public void onFailure(int code, String message) {
				Log.d(TAG, code + " - " + message);
				send2handler(_EL_F, message);
			}

			@Override
			public void onDeviceStatusReceived(int code, String messages) {
				Log.d(TAG + code, messages);
				send2handler(_EL_S, messages);
			}
		});
	}
	
	private void setTag(){
        String topic = "d64f517c/c8934691813c/in/write/0012";
        String command = "{\"4\":true}";

        mqtt.publish(topic, command, 0, false, new ListenDeviceCallBack() {
            @Override
            public void onSuccess(int code, String message) {
                Log.d(TAG, code + " " + message);
                send2handler(_EL_S, message);
            }

            @Override
            public void onFailure(int code, String message) {
                Log.d(TAG, code + " " + message);
                send2handler(_EL_F, message);
            }
        });
	}
	
	/**
	 * 发送消息给handler
	 * 
	 * @param tag
	 * @param message
	 */
	private void send2handler(int tag, String message) {
		Message msg = new Message();
		msg.what = tag;
		msg.obj = message;
		elhandler.sendMessage(msg);
	}
	
	/**
	 * 监听配网时候调用接口的log，并显示在activity上
	 */
	Handler elhandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case _EL_S:
				logsid.append("\n" + msg.obj.toString());
				break;
			case _EL_F:
				logsid.append("\n" + msg.obj.toString());
				break;
			}
		};
	};
}
