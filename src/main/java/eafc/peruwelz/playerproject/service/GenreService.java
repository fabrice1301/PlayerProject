package eafc.peruwelz.playerproject.service;

import eafc.peruwelz.playerproject.domain.TArtist;
import eafc.peruwelz.playerproject.domain.TGenre;
import eafc.peruwelz.playerproject.repos.TGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final TGenreRepository genreRepository;

    @Autowired
    public GenreService(TGenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<TGenre> findAllGenreService(){
        return genreRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(genre -> genre.getGenreName().toLowerCase()))
                .collect(Collectors.toList());
    }


    public void saveGenreService(TGenre genre){
        genreRepository.save(genre);
    }

    public void deleteGenreService(TGenre genre){genreRepository.delete(genre);}

}
