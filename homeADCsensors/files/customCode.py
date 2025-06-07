#This is where you can write your own code

class CustomClass:
    
    def __init__(self):
        self.reportValue = 1

    
    #The getData function gets called repetitively every numbers of seconds, in properties.json
    #Is you dont want to report any data, just return None
    def getData(self):
        return self.reportValue


