package eafc.peruwelz.PlayerProject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;


@Entity
public class TListen {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listenId;

    @OneToMany(mappedBy = "trackListenList")
    private Set<TTrack> listenTrackList;

    public Long getListenId() {
        return listenId;
    }

    public void setListenId(final Long listenId) {
        this.listenId = listenId;
    }

    public Set<TTrack> getListenTrackList() {
        return listenTrackList;
    }

    public void setListenTrackList(final Set<TTrack> listenTrackList) {
        this.listenTrackList = listenTrackList;
    }

}
