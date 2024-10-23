package eafc.peruwelz.PlayerProject.service;

import eafc.peruwelz.PlayerProject.domain.TTrack;
import eafc.peruwelz.PlayerProject.repos.TTrackRepository;
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
}
