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

# Note: Don't forget to check the last check box

You can see the mqtt paho icon on the main screen after installation.

![main screen](https://github.com/tommybee-dev/tls-paho-mosquitto-android/blob/master/screenshot/screen.png?raw=true "main screen")

Next, let's connect mosquitto broker system in which you built as below. Don't forget to check the TLS thing check box checked.

![start connect](https://github.com/tommybee-dev/tls-paho-mosquitto-android/blob/master/screenshot/connection.png?raw=true "start connect")

If you have like following image, It means you are success to connect the mosquitto broker server.

![after connection](https://github.com/tommybee-dev/tls-paho-mosquitto-android/blob/master/screenshot/connectionHist.png?raw=true "after connection")

First of all, you will see the connection history how many times by tapping arrow image on previous image.
You will get message prompt asking remove this connection by Long tapping on the message field.

![history](https://github.com/tommybee-dev/tls-paho-mosquitto-android/blob/master/screenshot/history.png?raw=true "history")

Subscribe a topic as a 'my topic1' at this example.

![subscribe](https://github.com/tommybee-dev/tls-paho-mosquitto-android/blob/master/screenshot/subscribe.png?raw=true "subscribe")

Next step is sending a message whatever sentence i pick up.
![publish](https://github.com/tommybee-dev/tls-paho-mosquitto-android/blob/master/screenshot/publish.png?raw=true "subscribe")

Now we've got a message poping up on the upside title bar.
![inform](https://github.com/tommybee-dev/tls-paho-mosquitto-android/blob/master/screenshot/inform.png?raw=true "inform")

Finally, You can check how many message i recieved from someone by mqtt protocol with the mosquitto environment.

![messagehist](https://github.com/tommybee-dev/tls-paho-mosquitto-android/blob/master/screenshot/messagehist.png?raw=true "messagehist")

