package eafc.peruwelz.PlayerProject.service;

import eafc.peruwelz.PlayerProject.domain.TPlaylist;
import eafc.peruwelz.PlayerProject.domain.TTrack;
import eafc.peruwelz.PlayerProject.repos.TTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public void copy(TTrack track,TTrack track2){
        track2.setTrackId(track.getTrackId());
        track2.setTrackPicture(track.getTrackPicture());
        track2.setTrackTitle(track.getTrackTitle());
        track2.setTrackPath(track.getTrackPath());
    }
    
    /*
    @Transactional(readOnly = true) // On s'assure que la session est ouverte
    public List<TTrack> getTracksForPlaylist(Long playlistId) {
        return trackRepository.findByIdWithPlaylist(playlistId);
    }
    */




}
