import machine, onewire, time
from customCode import CustomClass

from machine import I2C

class I2CBasic(CustomClass):
    
    def __init__(self, pin=2):
        self.device = I2C(freq=400000)
        self.device.scan()


    def read(self):
        return  self.device.readfrom(42, 4)