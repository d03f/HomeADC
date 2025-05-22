class GraphBuilder {
    constructor(canvasTarget, typeOfChart = "line", dataLabels = [], dataSets = []) {
        this.canvasTarget = canvasTarget

        this.chartType = typeOfChart

        this.dataLabels = dataLabels
        this.dataSets = dataSets
    }

    setChartType(chartType) {
        this.chartType = chartType;
        return this;
    }

    setDataLabels(dataLabels) {
        this.dataLabels = dataLabels;
        return this;
    }
    addDataLabel(dataLabel) {
        this.dataLabels.push(dataLabel);
        return this;
    }

    addDataSet(dataSet) {
        this.dataSets.push(dataSet);
        return this;
    }


    setIsResponsive(isResponsive) {
        this.isResponsive = isResponsive;
        return this;
    }



    build() {
        return new Chart(this.canvasTarget, {
            type: this.chartType,
            data: {
                labels: this.dataLabels, // Corrected spelling and length
                datasets: this.dataSets
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {},

                scales: {
                    x: {
                        type: 'time', // Important!
                        time: {
                            unit: 'minute' // or 'second', 'hour', etc.
                        }
                    }
                    
                }
                
            },
            
            plugins: [{
                id: 'warningLabel',
                beforeDraw(chart, args, options) {
                    if (!options || !options.enabled) return;

                    const { ctx, chartArea } = chart;

                    ctx.save();
                    ctx.font = options.font || 'bold 28px Arial';
                    ctx.fillStyle = options.color || 'red';
                    ctx.textAlign = 'center';
                    ctx.textBaseline = 'middle'; // important for vertical centering

                    // Center horizontally and vertically within the chart area
                    const centerX = (chartArea.left + chartArea.right) / 2;
                    const centerY = (chartArea.top + chartArea.bottom) / 2;

                    ctx.fillText(
                        options.text || '⚠️ WARNING!',
                        centerX,
                        centerY
                    );
                    ctx.restore();
                }
            }]
        })
    }
}



class DataSetBuilder {

    constructor(label, data = [], isFill = true, borderColor = "blue") {
        this.label = label
        this.data = data
        this.isFill = isFill
        this.borderColor = borderColor

    }

    addDataValue(value) {
        this.data.push(value)
        return this
    }
    setBorderColor(cssColor) {
        this.borderColor = cssColor
        return this
    }
    setBorderWidth(width) {
        this.borderWidth = width
        return this
    }

    setIsFill(isFill) {
        this.isFill = isFill
    }



    build() {
        return {
            label: this.label,
            data: this.data,
            fill: this.isFill,
            borderColor: this.borderColor
        }
    }

}