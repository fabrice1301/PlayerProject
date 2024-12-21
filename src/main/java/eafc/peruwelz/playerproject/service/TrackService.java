package eafc.peruwelz.playerproject.service;

import eafc.peruwelz.playerproject.domain.*;
import eafc.peruwelz.playerproject.repos.TTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TrackService {

    private final TTrackRepository trackRepository;


    @Autowired
    public TrackService(TTrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public void deleteTrackService(TTrack track){
        this.trackRepository.delete(track);
    }
    @Transactional
    public void saveTrackService(TTrack track){
        this.trackRepository.save(track);
    }

    public List<TTrack> findAllTrackService(){

        return trackRepository.findAll();
    }

    public List<TTrack> findByWaitingService(Boolean waiting){
        return trackRepository.findByTrackWaiting(waiting);
    }

    public List<TTrack> findByArtistService(TArtist artist){
        List<TTrack> byArtist = trackRepository.findByTrackArtistListContains(artist);
        return byArtist;
    }

    public List<TTrack> findByGenreService(TGenre genre){
        List<TTrack> byGenre = trackRepository.findByTrackGenreListContains(genre);
        return byGenre;
    }

    public List<TTrack> findByAlbumService(TAlbum album){
        List<TTrack> byAlbum = trackRepository.findByTrackAlbumListContains(album);
        return byAlbum;
    }

    public List<TTrack> findByPlaylistService(TPlaylist playlist){
        List<TTrack> byPlaylist = trackRepository.findByTrackPlaylistListContains(playlist);
        return byPlaylist;
    }

/*
    public void copy(TTrack track,TTrack track2){
        track2.setTrackId(track.getTrackId());
        track2.setTrackPicture(track.getTrackPicture());
        track2.setTrackTitle(track.getTrackTitle());
        track2.setTrackPath(track.getTrackPath());
    }
 */
    /*
    @Transactional(readOnly = true) // On s'assure que la session est ouverte
    public List<TTrack> getTracksForPlaylist(Long playlistId) {
        return trackRepository.findByIdWithPlaylist(playlistId);
    }
    */


}
