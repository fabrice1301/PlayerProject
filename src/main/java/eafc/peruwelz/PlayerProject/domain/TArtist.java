package eafc.peruwelz.PlayerProject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.time.OffsetDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class TArtist {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String artistName;

    @Column
    private String artistPicture;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean artistDeleted;

    @ManyToMany(mappedBy = "trackArtistList")
    private Set<TTrack> artistTrackList;


    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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



}
