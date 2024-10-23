package eafc.peruwelz.PlayerProject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class TAlbum {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long albumId;

    @Column
    private String albumName;

    @Column
    private String albumPicture;

    @Column
    private LocalDate albumDate;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean albumDeleted;

    @ManyToMany(mappedBy = "trackAlbumList")
    private Set<TTrack> albumTrackList;


    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(final Long albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(final String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumPicture() {
        return albumPicture;
    }

    public void setAlbumPicture(final String albumPicture) {
        this.albumPicture = albumPicture;
    }

    public LocalDate getAlbumDate() {
        return albumDate;
    }

    public void setAlbumDate(final LocalDate albumDate) {
        this.albumDate = albumDate;
    }

    public Boolean getAlbumDeleted() {
        return albumDeleted;
    }

    public void setAlbumDeleted(final Boolean albumDeleted) {
        this.albumDeleted = albumDeleted;
    }

    public Set<TTrack> getAlbumTrackList() {
        return albumTrackList;
    }

    public void setAlbumTrackList(final Set<TTrack> albumTrackList) {
        this.albumTrackList = albumTrackList;
    }


}
