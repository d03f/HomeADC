# ![Logo](webServer/homeADC/favicon.svg) HomeADC 

## An open source local home automation server to centralize all your sensors
With the increasing popularity of inteligent devices, interest to connect everything and our depencendy on techonology to do basic tasks, weare goint torwards a completely online world.  
We like the idea, and specially when it comes to home automation, but we dont like big enterprises.  
So, with the aim for security and customization, we developed HomeADC.  

The server we developed is local, it doesnt make any calls outside of your local network.
All the administratiion of the users, permisions, apikeys... is at your fingertips, you own everything!

This also means that is highly customizable, you can do with the source code as you like, and if you find any weakpoints or things tou improve, 
dont hesitate to submit any change!

The idea is that you create you own diy sensors, since we think this is the best way to know for sure what they do and who they send data to.
For this we have some information later on.



## Docs
These are the docs for the main parts of the software:

[Api docs](docs/API_Documentation.md) 

[Web server](webServer/README.md)



## The backbones
The server consist of 2 main parts:

The backend is developed using springboot and jpa, and the data is focused to be stored in mysql db.

The frontend, which is totally independent, is a boundle of basic .html .css and .js files that you can deploy on any web server you like. We recomend nginx, but it will work on most of the options out there.



## Your sensors
We are tinkers, and we love programming our own microcontrollers, so when we created this proyect we focused it in diy sensors.  
With commercially avaliable sensors, you have to do a really deep down investigation to knoow how your sensors report the data and to who.  
We dont like that, so our best oslution is to create your own sensors. With some microcontrollers, solder and a coupe of sensors you will be rolling in no time.  

We choose Micropython as our firmware for the sensors since its easy for anyone to learn and use. To flash micropython on your controllers, take a look at their docs, they are usefull.  
So, we provide the basic files so we do the boring part of connecting, sending the data... So all you have to do it edit your `properties.json` file and use our libraries to send all the data you like.  

If your prefer some other firmwware, such as Arduino, CircuitPython or similars, you will have to develop the code from scratch.  
But dont worry, with some experience and our [api docs](docs/API_Documentation.md) it will be really easy.  

If you have some experience in hardware hacking or creating your own firmware for commercially avaliable home automation devices, we will be eagered to learn!
Also, consider uploading it, so the community has something!


Have fun!!





---

*This is the final proyect for the Superior Degree in Multi-Platform Application Development*
