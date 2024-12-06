package eafc.peruwelz.playerproject.service;

import eafc.peruwelz.playerproject.domain.TGenre;
import eafc.peruwelz.playerproject.repos.TGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class GenreService {

    private final TGenreRepository genreRepository;

    @Autowired
    public GenreService(TGenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<TGenre> findAllGenreService(){
        return genreRepository.findAll();
    }


    public void saveGenreService(TGenre genre){
        genreRepository.save(genre);
    }


}
