package eafc.peruwelz.playerproject.repos;

import eafc.peruwelz.playerproject.domain.TArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository pour l'entité {@link TArtist}.
 * Cette interface permet d'effectuer des opérations CRUD sur les objets TArtist.
 * Elle étend {@link JpaRepository}, offrant ainsi des méthodes prêtes à l'emploi pour accéder à la base de données.
 */
public interface TArtistRepository extends JpaRepository<TArtist, Long> {

    /**
     * Recherche une liste d'artistes en fonction de leur nom, en ignorant la casse.
     *
     * @param artistName Le nom de l'artiste à rechercher.
     * @return Une liste d'objets {@link TArtist} correspondant au nom de l'artiste, sans tenir compte de la casse.
     */
    List<TArtist> findByArtistNameIgnoreCase(String artistName);
}
