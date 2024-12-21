package eafc.peruwelz.playerproject.player;

import javafx.util.Duration;
import org.springframework.stereotype.Component;

@Component
public interface Player {
    Object getInstance();
    void loadTrack(String path);
    void play();
    void stop();
    void pause();
    boolean isNull();
    Object getStatus();
    void setStatus(String status);
    void setVolume(double volume);
    double getVolume();
    Duration getCurrentTime();
    Duration getTotalDuration();
    void setOnReady(Runnable var);
    void setOnEndOfMedia(Runnable var);
    void seek(Duration time);
}
