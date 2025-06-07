import machine, onewire, ds18x20, time

class SensorDS18B20:
    
    def __init__(self, pin=2):
        self.device = ds18x20.DS18X20(  onewire.OneWire( machine.Pin(pin) )  )
        self.sensor = self.device.scan()[0]

    def read(self):
        self.device.convert_temp()
        time.sleep_ms(750)

        return  self.device.read_temp(self.sensor) 