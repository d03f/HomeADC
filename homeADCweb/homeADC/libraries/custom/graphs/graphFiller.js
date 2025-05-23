class GraphFiller{
    constructor(graphManager, sensorName, borderColor="blue",filters=[], updateInteval=2000, dateParser=new Parser_date_hour_min_sec()){
        this.graphManager = graphManager
        this.sensorName = sensorName
        this.filters = filters
        this.dateParser = dateParser
        this.borderColor = borderColor
        this.updateInteval = updateInteval
    }

    _getData() {
        
        console.log("tried to update")
        getSensorInfoRequest(localStorage.getItem("accountKey"), this.sensorName).then(result => {
            if (this.graphManager.getDataSets().length === 0) {
                this.graphManager.addDataSet(
                    new DataSetBuilder(result.dataUnit.symbol, [], true, this.borderColor).build()
                );
            }
    
            getSensorDataRequest(localStorage.getItem("apiKey"), this.sensorName, this.filters).then(data => {
                const labels = this.graphManager.getLabels();
    
                const existingTimestamps = new Set(labels);
    
                data.forEach(currRecord => {
                    const parsedTime = this.dateParser.parse(currRecord.timestamp);
    

                    if (!existingTimestamps.has(parsedTime)) {
                        this.graphManager.addLabel(parsedTime);
                        this.graphManager.addDataToDataSet(0, currRecord.value);
                    }

                    
                });
                
                

                
                this.graphManager.update();
    
            }).catch((e) => {
                this.graphManager.setError(true);
                console.log(e)
                this.graphManager.update();
            });
    
        }).catch(() => {
            this.graphManager.setError(true);
            this.graphManager.update();
        });
    }

    async fillRepeated(sleepTime=this.updateInteval){
        this.maxSize = this.filters.filter(a => a.startsWith("size="))[0] .split("=")[1].trim()

        this._getData()
        setInterval(() => this._getData(), sleepTime)
        

        


    }
}