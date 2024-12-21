package eafc.peruwelz.playerproject.Class;

import eafc.peruwelz.playerproject.domain.TTrack;

public class LoadedTrack extends TTrack {
    private static LoadedTrack instance;
    private int index;
    private TTrack track;

    private LoadedTrack(){

    }

    public static LoadedTrack getInstance(){
        if (instance==null){
            instance=new LoadedTrack();
        }
        return instance;
    }

    public void setTrack(TTrack track){
        this.track=track;
    }

    public TTrack getTrack(){
        return this.track;
    }

    public int getIndex(){
        return this.index;
    }

    public void setIndex(int index){
        this.index=index;
    }
}
