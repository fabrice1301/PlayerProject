package eafc.peruwelz.playerproject.repos;

import eafc.peruwelz.playerproject.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TTrackRepository extends JpaRepository<TTrack, Long> {

    List<TTrack> findByTrackWaiting(Boolean waiting);
    List<TTrack> findByTrackArtistListContains(TArtist artist);
    List<TTrack> findByTrackGenreListContains(TGenre genre);
    List<TTrack> findByTrackAlbumListContains(TAlbum album);
    List<TTrack> findByTrackPlaylistListContains(TPlaylist playlist);



    /*
    @Query("SELECT w FROM TTrack WHERE tackWaiting = :waiting")
    List<TTrack> findByIdWaiting(@Param("id") Boolean waiting);
     */

}
