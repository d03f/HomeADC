import machine, onewire, time
from customCode import CustomClass

from machine import UART

class UARTBasic(CustomClass):
    
    def __init__(self, pin=2):
        self.device = UART(pin, 96000)


    def read(self):
        return  self.device.read()