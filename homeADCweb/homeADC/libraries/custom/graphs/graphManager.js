
class GraphManager{
    constructor (chart) {
        this.chart = chart
    }


    addDataSet(newDataSet){
        this.chart.data.datasets.push(newDataSet)
    }
    getDataSet(idx){
        return this.chart.data.datasets[idx]
    }

    removeDataSet(idx){
        this.chart.data.datasets.splice(idx, 1);
    }

    addDataToDataSet(idx, newValue){
        this.chart.data.datasets[idx].data.push(newValue)
        return this;
    }

    removeFirstDataFromDataSet(idx){
        this.chart.data.datasets[idx].data.shift()
    }

    getDataSets(){
        return this.chart.data.datasets
    }


    getDataSet(idx){
        return this.chart.data.datasets[idx]
    }

    removeFirstFromDataSet(idx){
        this.chart.data.datasets[idx].data.shift()
        return this;
    }


    removeLastFromDataSet(idx){ this.chart.data.datasets[idx].data.pop(); return this; }

    addLabel(newValue){
        this.chart.data.labels.push(newValue)
        return this;
    }
    getLabels(){
        return this.chart.data.labels
    }
    removeFirstLabel(){
        this.chart.data.labels.shift()
    }

    setError(isEnabled){
        this.chart.options.plugins.warningLabel = {
            enabled: isEnabled,
            text: '⚠️ Can\'t load the data!',
            color: 'red',
            font: 'bold 180% Arial'
          };
        return this
    }

   
    update(){
        this.chart.update(
            {
                duration: 100,
                easing: 'easeInOutQuad', 
            }
        )
    }

}
