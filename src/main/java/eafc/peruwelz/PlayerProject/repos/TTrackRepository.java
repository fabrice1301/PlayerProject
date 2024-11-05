package eafc.peruwelz.PlayerProject.repos;

import eafc.peruwelz.PlayerProject.domain.TTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TTrackRepository extends JpaRepository<TTrack, Long> {
    //List<TTrack> findByPlaylist(TPlaylist playlist);
    /*
    @Query("SELECT p FROM TTrack p LEFT JOIN FETCH trackPlaylistList WHERE playlistId = :id")
    List<TTrack> findByIdWithPlaylist(@Param("id") Long id);
    */
}
