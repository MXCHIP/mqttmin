# mqttmin
Android - 简单的MQTTdemo by eclipse

##### 能看到的log示例

```js
04-28 14:16:40.909 22694-22694/com.fog.mqtt D/---mqtt---4214: {"status":"re-subscribe success"}
04-28 14:16:40.909 22694-22694/com.fog.mqtt D/---mqtt---4210: {"status":"connected"}
04-28 14:16:42.398 22694-22694/com.fog.mqtt D/---mqtt---4200: {"topic":"d64f517c/c8934691813c/out/read","payload":{ "9": 3214 }}
04-28 14:16:45.270 22694-22694/com.fog.mqtt D/---mqtt---4219: {"status":"publish success"}
04-28 14:16:45.288 22694-22694/com.fog.mqtt D/---mqtt---4200: {"topic":"d64f517c/c8934691813c/in/write/0012","payload":{"4":true}}
04-28 14:16:45.434 22694-22694/com.fog.mqtt D/---mqtt---4200: {"topic":"d64f517c/c8934691813c/out/err","payload":{ "status": 0 }}
04-28 14:16:45.451 22694-22694/com.fog.mqtt D/---mqtt---4200: {"topic":"d64f517c/c8934691813c/out/write/0012","payload":{ "4": true }}
```

##### 测试流程

MQTT数据收发需要三个部分：
1、app
2、云
3、设备

云的部分我们不用管，我们只需要管设备和APP部分，找到一个测试专用的服务器，APP我们自己开发，然后接下来只需要找到一个设备了，我们可以通过这个网页模拟一个设备：[传送门](http://api.easylink.io/tools/mqtt/)，

完成几步如下：
1、Connection中填入几个参数，其他默认，点击Connect按钮，

> host:api.easylink.io
> port:1983
> ClientID:clientId-osjRjMOd74
> username:xuyx@mxchip.com
> username:123456
> Keep Alive:60

2、Subscriptions中点击“Add New Topic Subscription”,在弹出框的Topic中填入以下参数，其他默认，点击Subscribe按钮

> d64f517c/c8934691813c/#

3、Publish部分，Topic中填入“d64f517c/c8934691813c/in/write/0013”，message中填入“{"123":45}”，当点击Publish按钮，在APP的startMqtt接口的onDeviceStatusReceived回调中就会收到消息

4、Message部分：当APP端调用publish接口，在这一栏就能看到APP下发的指令


















真实环境会不一样，对于