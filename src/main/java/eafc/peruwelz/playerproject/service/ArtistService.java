package eafc.peruwelz.playerproject.service;

import eafc.peruwelz.playerproject.domain.TArtist;
import eafc.peruwelz.playerproject.repos.TArtistRepository;
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
