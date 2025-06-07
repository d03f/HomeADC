import ujson


# Class used to read the properties.json file
# It can be used to read other .json files if wanted
class PropertiesReader:
    
    
    def __init__(self, filePath="properties.json"):        
        with open(filePath, "r") as file:
            self.data = ujson.load(file)
    
    def getItem(self, key):
        return self.data[key]