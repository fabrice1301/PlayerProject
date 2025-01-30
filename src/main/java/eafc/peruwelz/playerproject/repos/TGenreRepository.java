package eafc.peruwelz.playerproject.repos;

import eafc.peruwelz.playerproject.domain.TGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository pour l'entité {@link TGenre}.
 * Cette interface permet d'effectuer des opérations CRUD sur les objets TGenre.
 * Elle étend {@link JpaRepository}, offrant ainsi des méthodes prêtes à l'emploi pour interagir avec la base de données.
 */
public interface TGenreRepository extends JpaRepository<TGenre, Long> {

    /**
     * Recherche une liste de genres en fonction de leur nom, en ignorant la casse.
     *
     * @param genreName Le nom du genre à rechercher.
     * @return Une liste d'objets {@link TGenre} correspondant au nom du genre, sans tenir compte de la casse.
     */
    List<TGenre> findByGenreNameIgnoreCase(String genreName);
}