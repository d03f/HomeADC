import network
import time

#This is used to handle the ocnecction to wifi
def connect(ssid, passwd, attemps=10) -> bool:
    wlan = network.WLAN(network.STA_IF)
    wlan.active(True)
    
    
    if not wlan.isconnected():
        print("Connecting", end="")
        wlan.connect(ssid, passwd)
        
        while not wlan.isconnected() and attemps > 0:
            time.sleep(1)
            attemps-=1
            print(".", end="")
    
    if wlan.isconnected():
        data = wlan.ifconfig()
        print("\nIp:" + data[0] + "  -  Mask:" + data[1] + "\nGateway:"+data[2] + "  -  DNS:"+data[3] )
        return True
    else:
        print("\nFailed to connect!")
        return False

#This allows to disconnect
def disconnect():
    wlan = network.WLAN(network.STA_IF)
    if wlan.isconnected():
        wlan.disconnect()
    
    print('Disconnected!')
    



        
    