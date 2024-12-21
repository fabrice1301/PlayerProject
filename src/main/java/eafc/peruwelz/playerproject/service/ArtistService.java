package eafc.peruwelz.playerproject.service;

import eafc.peruwelz.playerproject.domain.TArtist;
import eafc.peruwelz.playerproject.repos.TArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistService {

    private final TArtistRepository artistRepository;

    @Autowired
    public ArtistService(TArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<TArtist> findAllArtistService(){
        return artistRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(artist -> artist.getArtistName().toLowerCase()))
                .collect(Collectors.toList());
    }


    public void saveArtistService(TArtist artist){
        artistRepository.save(artist);
    }

    public void deleteArtistService(TArtist artist){ artistRepository.delete(artist);}
}
