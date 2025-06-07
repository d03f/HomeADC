from propertiesReader import PropertiesReader
import wifi
import reportData
from customCode import CustomClass
import time

#This is the main file, it will be executed when the microcontroller turns on
#It is a template, feel free to modify it


#Connection to the network
prop = PropertiesReader()

wifiData = prop.getItem("wifi")
if not wifi.connect(wifiData["SSID"], wifiData["password"]):
    exit


#Initalization of main classes
reporter = reportData.DataReporter()
customClass = CustomClass()


#Main loop
SLEEP_TIME = prop.getItem("sleepTime")
while True:
    time.sleep(SLEEP_TIME)
    
    data = customClass.getData()
    if (data != None):
        if reporter.reportData(data): print("Data reported!")
        
