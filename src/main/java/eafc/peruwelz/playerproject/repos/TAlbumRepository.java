package eafc.peruwelz.playerproject.repos;

import eafc.peruwelz.playerproject.domain.TAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository pour l'entité {@link TAlbum}.
 * Cette interface permet d'effectuer des opérations CRUD sur les objets TAlbum.
 * Elle étend {@link JpaRepository}, offrant ainsi des méthodes prêtes à l'emploi pour accéder à la base de données.
 */
public interface TAlbumRepository extends JpaRepository<eafc.peruwelz.playerproject.domain.TAlbum, Long> {

    /**
     * Recherche une liste d'album(s) en fonction de leur nom, en ignorant la casse.
     *
     * @param albumName Le nom de l'album à rechercher.
     * @return Une liste d'objets {@link TAlbum} correspondant au nom de l'album, sans tenir compte de la casse.
     */
    List<TAlbum> findByAlbumNameIgnoreCase(String albumName);
}
