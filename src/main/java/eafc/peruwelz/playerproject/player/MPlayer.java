package eafc.peruwelz.playerproject.player;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;


public class MPlayer implements Player {
    private MediaPlayer mediaPlayer;
    private enum STATUS {PLAYING, STOPPED, PAUSED}

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
        int status;
        switch (mediaPlayer.getStatus()){
            case PLAYING -> {
                return STATUS.PLAYING;
            }
            case STOPPED -> {
                return STATUS.STOPPED;
            }
            case PAUSED -> {
                return STATUS.PAUSED;
            }
            default -> {
                return null;
            }
        }
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
