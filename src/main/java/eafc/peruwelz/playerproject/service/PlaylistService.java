package eafc.peruwelz.playerproject.service;

import eafc.peruwelz.playerproject.domain.TPlaylist;
import eafc.peruwelz.playerproject.domain.TTrack;
import eafc.peruwelz.playerproject.repos.TPlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlaylistService {

    private final TPlaylistRepository playlistRepository;

    @Autowired
    public PlaylistService(TPlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public List<TPlaylist> findAllPlaylistService(){
        return playlistRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(playlist -> playlist.getPlaylistName().toLowerCase()))
                .collect(Collectors.toList());
    }

    public TPlaylist findByPlaylistIdService(String playlist){
        return playlistRepository.findByPlaylistName(playlist);
    }

    public void savePlaylistService(TPlaylist playlist){
        playlistRepository.save(playlist);
    }

    public void deletePlaylistService(TPlaylist playlist){playlistRepository.delete(playlist);}

    public Set<TTrack> getTrack(TPlaylist playlist){
        return playlist.getPlaylistTrackList();
    }


}
