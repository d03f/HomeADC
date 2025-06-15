# How to set up the server

This documents contains the basic process for setting up this proyect and have it working.

First of all you will need the following:
---
- [ ] **Java OpenJDK 21**
- [ ] **Maven**
- [ ] **MySQL** (we recommed running it in a docker instance)
---
- [ ] **Web server** (we recommend nginx)
---
- [ ] **Microcontroller** (we recommend micropython as firware so you can use our files)
- [ ] **USB cable**
---
- [ ] **Eclipse IDE** (optional)
- [ ] **Thonny IDE** (optional)
- [ ] **VSCodium IDE** (optional)
----

First of all, download the proyect of thsi repo.

We will start with the backend, since its the backbone to the service.
Before doing anything, we recommend creating a Docker instance with a mysql server and configure the basic security.

Once done, we can start with teh microservice.
The java proyect is in the path homeADC. We recommend using Eclipse IDE to make the modifications needed.
To get the server up and running, you will need to modify the aplication.properties in the resources folder.

First, make sure to configure the ip and prot yor server will be running at. Remember to make it accesible to the computers in your network if that is what you want.
And remember to make sure you dont haave problems with the firewall!!!!

After that, you need to configure the connection to the db. Edit the url, username and password. If you want to use another database manager, remmenber you will need to change the conector.

Lastly, we provide the code necesarry, so when the server boots up it creates an admin account. Use this to create and edit the users needed and IMPORTANT, remember to remove it!!!
Once you remove the admin user, disable the option in the properties file and you are good to go.

Boot up the server as any other java app. There is the main class, and when you run it, spring will boot up and to prety much everything for you. If you have any errors, read carefully the logs and if not make an issue and we will take a look and try to fix it.


We recommend taking a look at the API documentation in this folter to have a reference for endpoints and their uses.  


---

Before booting up the web server, we recommend to setup a microcontroller, or a mookup, to make sure everithing is up and working. 
Take a look at our Sensors docs to know how to do that.

Important, the users have sensors, but for an api key to be able to be used with a sensors, you need to add it to the allowed api keys!!!! 

Once you have the microcontroller setup, move on to the web server.

---
This is the easiest part. Choose the web server you like, take a look at the documentation for it, the path and permissions and download our files to the route.
Once its done, you will be up and running.

Have fun!!!!
