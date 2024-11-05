package eafc.peruwelz.PlayerProject.repos;

import eafc.peruwelz.PlayerProject.domain.TPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface TPlaylistRepository extends JpaRepository<TPlaylist, Long> {
    TPlaylist findByPlaylistName(String playlist);

    /*
    @Query("SELECT p FROM TPlaylist p LEFT JOIN FETCH p.playlistTrackList WHERE p.playlistId = :id")
    Optional<TPlaylist> findByIdWithTracks(@Param("id") Long id);
     */
}
