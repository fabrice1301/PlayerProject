package eafc.peruwelz.playerproject.Class;

public class StatusPlayer {
    private enum STATUS {PLAYING, STOPPED, PAUSED, UNKNOW}
    private STATUS status;
    private static StatusPlayer instance;

    private StatusPlayer(){
        status=STATUS.UNKNOW;
    }

    public static StatusPlayer getInstance(){
        if(instance==null){
            instance=new StatusPlayer();
        }
        return instance;
    }

    public String getStatus(){
        return status.toString();
    }

    public void setStatus(String status){
        this.status = STATUS.valueOf(status.toUpperCase());
    }

}
