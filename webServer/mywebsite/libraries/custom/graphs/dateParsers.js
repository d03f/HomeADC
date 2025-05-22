class DataParser{

    parse(){

    }

}

class Parser_date_hour_min_sec{
    parse(string){
        return string.split("T")[0].split("-").slice(1, 3).reverse().join("-") 
        + " " 
        + string.split("T")[1].split(":").slice(0, 2).join(":") 

        +":"+ string.split("T")[1].split(":")[2].split(".")[0]
    }
}

