package eafc.peruwelz.PlayerProject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.Objects;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class TArtist {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artistId;

    @Column
    private String artistName;

    @Column
    private String artistPicture;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean artistDeleted=false;

    @ManyToMany(mappedBy = "trackArtistList")
    private Set<TTrack> artistTrackList;


    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(final Long artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(final String artistName) {
        this.artistName = artistName;
    }

    public String getArtistPicture() {
        return artistPicture;
    }

    public void setArtistPicture(final String artistPicture) {
        this.artistPicture = artistPicture;
    }

    public Boolean getArtistDeleted() {
        return artistDeleted;
    }

    public void setArtistDeleted(final Boolean artistDeleted) {
        this.artistDeleted = artistDeleted;
    }

    public Set<TTrack> getArtistTrackList() {
        return artistTrackList;
    }

    public void setArtistTrackList(final Set<TTrack> artistTrackList) {
        this.artistTrackList = artistTrackList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TArtist artist)) return false;
        return Objects.equals(getArtistId(), artist.getArtistId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArtistId());
    }


}
