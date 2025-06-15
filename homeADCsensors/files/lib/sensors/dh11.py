import machine, onewire, dht, time
from customCode import CustomClass

class SensorDH11(CustomClass):
    
    def __init__(self, pin=2):
        self.device = dht.DHT11(  onewire.OneWire( machine.Pin(pin) )  )
        self.device.measure()

    def read(self):
        return  self.device.temperature()