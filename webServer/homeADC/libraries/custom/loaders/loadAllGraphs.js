if(!new DataStorage().getItem("graphsData")){
    new DataStorage().setItem("graphsData", JSON.stringify([ 
        ["test", "line", "#CCCCCC", "#FF0000", ["sort=timestamp,desc", "size=10"], "1000"],
        ["test", "bar", "#CCCCCC", "#FF00FF", ["sort=timestamp,desc", "size=100"], "1000"],      
        ["test",  "radar", "#CCCCCC", "#FFFF00", ["sort=timestamp,desc", "size=100"], "1000"],
        ["test", "line", "#CCCCCC", "#6B26AC", ["sort=timestamp,desc", "size=100"], "1000"]
    
    ] ))
}

const graphsData = JSON.parse(new DataStorage().getItem("graphsData") )

graphs = document.getElementsByClassName("graph")
let idx = 0;
Array.from(graphs).forEach(canvasTarget => {

    canvasTarget.style.backgroundColor = graphsData[idx][2]


    currGraph = new GraphBuilder(canvasTarget, graphsData[idx][1]).build();
    
    
    currManager = new GraphManager(currGraph)
    new GraphFiller(currManager, graphsData[idx][0], graphsData[idx][3],graphsData[idx][4], graphsData[idx][5] ).fillRepeated()

    idx++

});