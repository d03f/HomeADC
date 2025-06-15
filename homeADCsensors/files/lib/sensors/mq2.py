import machine, onewire, time
from customCode import CustomClass
from MQ2 import MQ2

class SensorMQ2(CustomClass):
    
    def __init__(self, pin=2):
        self.device = MQ2(pinData = pin, baseVoltage = 3.3)
        self.device.calibrate()


    def read(self):
        return  self.device.readSmoke()