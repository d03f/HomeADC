class DataStorage{

    constructor(isPersistent=true){
        this.storage = isPersistent ? localStorage : sessionStorage;
    }

    setItem(key, value){ this.storage.setItem(key, value) }

    getItem(key){ return this.storage.getItem(key) }

}