package eafc.peruwelz.playerproject.domain;

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
public class TGenre {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreId;

    @Column
    private String genreName;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean genreDeleted=false;

    @ManyToMany(mappedBy = "trackGenreList")
    private Set<TTrack> genreTrackList;


    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(final Long genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(final String genreName) {
        this.genreName = genreName;
    }

    public Boolean getGenreDeleted() {
        return genreDeleted;
    }

    public void setGenreDeleted(final Boolean genreDeleted) {
        this.genreDeleted = genreDeleted;
    }

    public Set<TTrack> getGenreTrackList() {
        return genreTrackList;
    }

    public void setGenreTrackList(final Set<TTrack> genreTrackList) {
        this.genreTrackList = genreTrackList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TGenre genre)) return false;
        return Objects.equals(getGenreId(), genre.getGenreId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGenreId());
    }
}
