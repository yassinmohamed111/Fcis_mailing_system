public class userLoginHolder {

    private static userLoginHolder instance=null ;

    User user;

    private userLoginHolder(){

    }

    public static userLoginHolder getInstance(){
        if(instance == null){
            instance = new userLoginHolder();
        }
        return instance ;
    }

    public User getuser() {
        return user;
    }

    public void setuser(User user) {
        this.user = user;
    }


}
