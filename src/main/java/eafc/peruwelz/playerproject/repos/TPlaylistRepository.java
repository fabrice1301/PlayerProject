package eafc.peruwelz.playerproject.repos;

import eafc.peruwelz.playerproject.domain.TPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TPlaylistRepository extends JpaRepository<TPlaylist, Long> {
    TPlaylist findByPlaylistName(String playlist);

    /*
    @Query("SELECT p FROM TPlaylist p LEFT JOIN FETCH p.playlistTrackList WHERE p.playlistId = :id")
    Optional<TPlaylist> findByIdWithTracks(@Param("id") Long id);
     */
}
