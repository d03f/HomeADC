import urequests
from propertiesReader import PropertiesReader
import ujson

from apiRequests import ApiHandlerPost
from apiRequests import ApiHandlerGet


#This class is used as a hub to report the data
#Is contains a class to save all the data
#And a class to report it to the api
#
#You can create multiple instance pointing to different files to report to different end points
class DataReporter:
    
    def __init__(self, prop=PropertiesReader()):
        self.prop = prop
        self.storage = DataStorage()
        self.apiPost = ApiHandlerPost(prop)
    
    def reportData(self, data) -> bool:
        self.storage.addLast(data)
        
        for _ in range(  len( self.storage.getData() )  ):
            if ( not self._postData(self.storage.popFirst()) ): return False
        
        return True
        
    
    
    def _postData(self, value) -> bool:
        return self.apiPost.reportData(value)
        

    
#This class is used to store teh readed data
#This allows us to use it as a backup in case the reqeust goes wrong
#It can be modified to report the avg, max, min....
class DataStorage:
    def __init__(self):
        self.data = []
    
    def popFirst(self):
        return self.data.pop(0)
    
    def addLast(self, data):
        self.data.append(data)
    
    def getData(self):
        return self.data
    
    def cleanData(self):
        self.data = []
        