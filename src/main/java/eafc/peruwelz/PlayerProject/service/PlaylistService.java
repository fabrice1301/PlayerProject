package eafc.peruwelz.PlayerProject.service;

import eafc.peruwelz.PlayerProject.domain.TPlaylist;
import eafc.peruwelz.PlayerProject.repos.TPlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void savePlaylistService(TPlaylist playlist){
        playlistRepository.save(playlist);
    }


}
