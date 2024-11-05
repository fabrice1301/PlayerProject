package eafc.peruwelz.PlayerProject.service;

import eafc.peruwelz.PlayerProject.domain.TPlaylist;
import eafc.peruwelz.PlayerProject.domain.TTrack;
import eafc.peruwelz.PlayerProject.repos.TPlaylistRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PlaylistService {

    private final TPlaylistRepository playlistRepository;

    @Autowired
    public PlaylistService(TPlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public List<TPlaylist> findAllPlaylistService(){
        return playlistRepository.findAll();
    }

    public TPlaylist findByPlaylistIdService(String playlist){
        return playlistRepository.findByPlaylistName(playlist);
    }

    public void savePlaylistService(TPlaylist playlist){
        playlistRepository.save(playlist);
    }

    public Set<TTrack> getTrack(TPlaylist playlist){
        return playlist.getPlaylistTrackList();
    }

    /*
    @Transactional(readOnly = true)
    public Set<TTrack> getTracksForPlaylist(Long playlistId) {
        return playlistRepository.findByIdWithTracks(playlistId)
                .map(TPlaylist::getPlaylistTrackList)
                .orElse(Collections.emptySet());
    }
    */

}
