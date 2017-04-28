package com.fog.mqtt;

import io.fogcloud.fog_mqtt.api.MQTT;
import io.fogcloud.fog_mqtt.helper.ListenDeviceCallBack;
import io.fogcloud.fog_mqtt.helper.ListenDeviceParams;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	private String TAG = "---mqtt---";
	private Context context;

	private TextView startpub;
	private TextView logsid;

	private MQTT mqtt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = MainActivity.this;

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
			}

			@Override
			public void onFailure(int code, String message) {
				Log.d(TAG, code + " - " + message);
			}

			@Override
			public void onDeviceStatusReceived(int code, String messages) {
				Log.d(TAG + code, messages);
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
            }

            @Override
            public void onFailure(int code, String message) {
                Log.d(TAG, code + " " + message);
            }
        });
	}
}
