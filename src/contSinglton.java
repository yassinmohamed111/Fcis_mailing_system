public class contSinglton  {

    private static contSinglton instance=null ;

    contacts cont;

    private contSinglton(){

    }

    public static contSinglton getInstance(){
        if(instance == null){
            instance = new contSinglton();
        }
        return instance ;
    }

    public contacts getcont() {
        return cont;
    }

    public void setCont(contacts cont) {
        this.cont =  cont;
    }


}

