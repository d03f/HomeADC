class BgItemsModifier{

    constructor(){
        this.items = document.getElementsByClassName("custom-bg-item")
        this.positions = this._generatePos(this.items.length)
        this.colors = this._generateColors(this.items.length)
        
    }


    _generatePos(size){
        let result = []
        for (let i=0; i<size; i++){
            let top  = (Math.floor(Math.random()*6) * 10) + 20
            let left = (Math.floor(Math.random()*6) * 10) + 20
            
            result.push([top, left])
        }

        return result
    }

    _generateColors(size){
        let result = []
        for (let i=0; i<size; i++){
            let r = Math.floor(Math.random() * 3) * 125
            let g = Math.floor(Math.random() * 3) * 125
            let b = Math.floor(Math.random() * 3) * 125

            result.push([r, g, b])
        }

        return result
    }

    asignProperties(){
        for (let item of this.items){
            let currPos = this.positions.pop()
            let currColor = this.colors.pop()
            
            item.style.top = currPos[0] + "%"
            item.style.left = currPos[1] + "%"

            item.style.background = `rgb( ${currColor[0]}, ${currColor[1]}, ${currColor[2]}, 0.1)`

            item.style.animation = `float${Math.floor(Math.random() *3) } 10s infinite ease-in-out`
            item.style.animationDelay = "-"+Math.floor(Math.random() *20) + "s"


        }
    }
}

new BgItemsModifier().asignProperties()