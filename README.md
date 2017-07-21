Paho Android MQTT(Mosquitto) with SSL/TLS

This is my small customized version of the eclipse's paho app for mqtt protocol

You can use this app after the mosquitto broker server setup first.
[site](https://mosquitto.org/)

Following my another github for setting up the mosquitto broker server and testing if you don't have any of it.

[mosqutto set up](https://github.com/tommybee-dev/tls-paho-mosquitto)

It won't compile if you don't download the openssl java library like bouncycastle from the site
[bouncycastle site](https://www.bouncycastle.org/latest_releases.html)

	- bcpkix-jdk15on-157.jar
	- bcprov-jdk15on-156.jar

Here are some screen shots.

Tab the highlighted icon on the main screen.

Connect to your mosquitto broker server fullfilling with information.

#Note: Don't forget to check the last check box


![main screen](https://github.com/tommybee-dev/tls-paho-mosquitto-android/blob/master/screenshot/screen.png?raw=true "main screen")

![start connect](https://github.com/tommybee-dev/tls-paho-mosquitto-android/blob/master/screenshot/connection.png?raw=true "start connect")

![after connection](https://github.com/tommybee-dev/tls-paho-mosquitto-android/blob/master/screenshot/connectionHist.png?raw=true "after connection")

![history](https://github.com/tommybee-dev/tls-paho-mosquitto-android/blob/master/screenshot/history.png?raw=true "history")

![subscribe](https://github.com/tommybee-dev/tls-paho-mosquitto-android/blob/master/screenshot/subscribe.png?raw=true "subscribe")

![publish](https://github.com/tommybee-dev/tls-paho-mosquitto-android/blob/master/screenshot/publish.png?raw=true "subscribe")

![inform](https://github.com/tommybee-dev/tls-paho-mosquitto-android/blob/master/screenshot/inform.png?raw=true "inform")

![messagehist](https://github.com/tommybee-dev/tls-paho-mosquitto-android/blob/master/screenshot/messagehist.png?raw=true "messagehist")