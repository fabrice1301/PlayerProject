package eafc.peruwelz.playerproject.repos;

import eafc.peruwelz.playerproject.domain.TArtist;
import eafc.peruwelz.playerproject.domain.TTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TTrackRepository extends JpaRepository<TTrack, Long> {

    List<TTrack> findByTrackWaiting(Boolean waiting);
    List<TTrack> findByTrackArtistListContains(TArtist artist);
    /*
    @Query("SELECT w FROM TTrack WHERE tackWaiting = :waiting")
    List<TTrack> findByIdWaiting(@Param("id") Boolean waiting);
     */

}
