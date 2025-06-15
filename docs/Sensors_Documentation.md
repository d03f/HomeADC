# Microcontrollers and Sensors

This is the explaination of how to use and develop code for the microcontrollers.

In this ![path](homeADCsensors/files) we provided all the files necesary to connect the sensors and start reporting or getting data.

You will need a microcontroller with Micropython flashed. Once you have that, you can just drop the files provided and when the device boots up, it will connect to the network and start reporting.

To do all the configuration, you will have to edit the properties.json file. There you have to configure the network access, the api key, the sensor url, and what sensor you are using with the microcontroller.

Once its done, the board should start reporting example data.

We encourage to take a look at our code, and based on that modify it to fit your needs.
We provide some basic classes, but you can inherit from the main class and implement what ever you like.

We also recomend taking a look at main.py, where the main loop happens, and modify it to your needs.

Have fun!

