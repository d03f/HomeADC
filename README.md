# ![Logo](webServer/homeADC/favicon.svg) HomeADC 

## An open source local home automation server to centralize all your sensors
With the increasing popularity of inteligent devices, interest to connect everything and our depencendy on techonology to do basic tasks, weare goint torwards a completely online world
We like the idea, and specially when it comes to home automation, but we dont like big enterprises
So, with the aim for security and customization, we developed HomeADC

The server we developed is local, it doesnt make any calls outside of your local network
All the administratiion of the users, permisions, apikeys... is at your fingertips, you own everything!

This also means that is highly customizable, you can do with the source code as you like, and if you find any weakpoints or things tou improve, 
dont hesitate to submit any change!

## Docs
There are the docs for the main parts of the software:

[Api docs](docs/API_Documentation.md) 

[Web server](webServer/README.md)



## The backbones
The server consist of 2 main parts:

The backend is developed using springboot and jpa, and the data is focused to be stored in mysql db

The frontend, which is totally independent, is a boundle of basic .html .css and .js files that you can deploy on any web server you like. We recomend nginx, but it will work on most of the options out there







---

*This is the final proyect for the Superior Degree in Multi-Platform Application Development*
