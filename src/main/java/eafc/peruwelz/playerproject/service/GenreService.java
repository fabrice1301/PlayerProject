package eafc.peruwelz.playerproject.service;

import eafc.peruwelz.playerproject.domain.TGenre;
import eafc.peruwelz.playerproject.repos.TGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service pour la gestion des genres musicaux.
 */
@Service
public class GenreService {

    /**
     * Repository des genres, utilisé pour interagir avec la base de données.
     * Il permet de récupérer, sauvegarder, et supprimer des objets {@link TGenre}.
     */
    private final TGenreRepository genreRepository;

    /**
     * Constructeur du service GenreService.
     * @param genreRepository Repository des genres injecté par Spring.
     */
    @Autowired
    public GenreService(TGenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    /**
     * Récupère tous les genres triés par nom en ordre alphabétique.
     * @return Liste triée de tous les genres musicaux.
     */
    public List<TGenre> findAllGenreService(){
        return genreRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(genre -> genre.getGenreName().toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Sauvegarde un genre musical dans le repository.
     * @param genre Le genre à sauvegarder.
     */
    public void saveGenreService(TGenre genre){
        genreRepository.save(genre);
    }

    /**
     * Supprime un genre musical du repository.
     * @param genre Le genre à supprimer.
     */
    public void deleteGenreService(TGenre genre){
        genreRepository.delete(genre);
    }

    /**
     * Vérifie si un genre musical existe déjà par son nom.
     * @param name Le nom du genre à vérifier.
     * @return true si le genre existe, false sinon.
     */
    public Boolean isExists(String name){
        return !genreRepository.findByGenreNameIgnoreCase(name).isEmpty();
    }
}

