package eafc.peruwelz.playerproject.Class;


public class StatusPlayer {
    private enum STATUS {PLAYING, STOPPED, PAUSED, UNKNOW}
    private STATUS status;

    public StatusPlayer(){
        this.status=STATUS.UNKNOW;
    }

    public String getStatus(){
        return this.status.toString();
    }

    public void setStatus(String status){
        this.status = STATUS.valueOf(status.toUpperCase());
    }

}
