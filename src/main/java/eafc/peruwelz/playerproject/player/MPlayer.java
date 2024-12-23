package eafc.peruwelz.playerproject.player;


import eafc.peruwelz.playerproject.Class.StatusPlayer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;


public class MPlayer implements Player {
    private MediaPlayer mediaPlayer;

    @Override
    public Object getInstance() {
        return this.mediaPlayer;
    }

    @Override
    public void loadTrack(String path) {
        File file=new File(path);
        Media media = new Media(file.toURI().toString());
        this.mediaPlayer=new MediaPlayer(media);
    }

    @Override
    public void play() {
        this.mediaPlayer.play();
    }

    @Override
    public void stop() {
        this.mediaPlayer.stop();
    }

    @Override
    public void pause() {
        this.mediaPlayer.pause();
    }

    @Override
    public boolean isNull() {
        return mediaPlayer==null;
    }

    @Override
    public Object getStatus() {
        return StatusPlayer.getIntance().getStatus();
    }

    @Override
    public void setStatus(String status) {
        StatusPlayer.getIntance().setStatus(status);
    }

    @Override
    public void setVolume(double volume) {
        this.mediaPlayer.setVolume(volume);
    }

    @Override
    public double getVolume() {
        return this.mediaPlayer.getVolume();
    }

    @Override
    public Duration getCurrentTime() {
        return this.mediaPlayer.getCurrentTime();
    }

    @Override
    public Duration getTotalDuration() {
        return this.mediaPlayer.getTotalDuration();
    }

    @Override
    public void setOnReady(Runnable var) {
        this.mediaPlayer.setOnReady(var);
    }

    @Override
    public void setOnEndOfMedia(Runnable var) {
        this.mediaPlayer.setOnEndOfMedia(var);
    }

    @Override
    public void seek(Duration time) {
        this.mediaPlayer.seek(time);
    }
}
