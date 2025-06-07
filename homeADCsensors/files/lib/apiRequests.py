import urequests
from propertiesReader import PropertiesReader
import ujson



#This class handles the post requests
#The data, by default, is extracted from properties.json
#The values for the sensor name and report endpoint are in the properties.json
class ApiHandlerPost:
    
    def __init__(self, prop=PropertiesReader()):
        self.url = prop.getItem("serverUrl") + prop.getItem("sensorName")
        self.headers = { "Authorization": "ApiKey " + prop.getItem('apiKey'), "Content-Type": "application/json" }

        
    
    def reportData(self, value, metadata=None) -> bool:
        data = {"value" : value}
        if (metadata != None): data["metadata"] = metadata
        
        response = urequests.post(self.url, json=data, headers=self.headers)
        responseData = ujson.loads(response.text)


        response.close()
        
        return True if responseData["status"] == "success" else False

#This class handles the get requests
#The data, by default, is extracted from properties.json
#The values for the sensor name and report endpoint are in the properties.json
class ApiHandlerGet:
    
    def __init__(self, prop=PropertiesReader()):
        self.url = prop.getItem("serverUrl") + prop.getItem("sensorName")
        self.headers = { "Authorization": "ApiKey " + prop.getItem('apiKey'), "Content-Type": "application/json" }

        
    
    def reportData(self, value, metadata=None) -> bool:
        
        response = urequests.get(self.url,  headers=self.headers)
        responseData = ujson.loads(response.text)


        response.close()
        
        return responseData
        