package eafc.peruwelz.PlayerProject.service;

import eafc.peruwelz.PlayerProject.domain.TArtist;
import eafc.peruwelz.PlayerProject.domain.TGenre;
import eafc.peruwelz.PlayerProject.repos.TArtistRepository;
import eafc.peruwelz.PlayerProject.repos.TGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {

    private final TArtistRepository artistRepository;

    @Autowired
    public ArtistService(TArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<TArtist> findAllArtistService(){
        return artistRepository.findAll();
    }


    public void saveArtistService(TArtist artist){
        artistRepository.save(artist);
    }
}
