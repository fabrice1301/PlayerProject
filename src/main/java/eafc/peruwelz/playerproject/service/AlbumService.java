package eafc.peruwelz.playerproject.service;

import eafc.peruwelz.playerproject.domain.TAlbum;
import eafc.peruwelz.playerproject.repos.TAlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service pour la gestion des albums.
 */
@Service
public class AlbumService {

    /**
     * Repository des albums, utilisé pour interagir avec la base de données.
     * Il permet de récupérer, sauvegarder, et supprimer des objets {@link TAlbum}.
     */
    private final TAlbumRepository albumRepository;

    /**
     * Constructeur du service AlbumService.
     * @param albumRepository Repository des albums injecté par Spring.
     */
    @Autowired
    public AlbumService(TAlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    /**
     * Récupère tous les albums triés par nom en ordre alphabétique.
     * @return Liste triée de tous les albums.
     */
    public List<TAlbum> findAllAlbumService(){
        return albumRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(album -> album.getAlbumName().toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Sauvegarde un album dans le repository.
     * @param album L'album à sauvegarder.
     */
    public void saveAlbumService(TAlbum album){
        albumRepository.save(album);
    }

    /**
     * Supprime un album du repository.
     * @param album L'album à supprimer.
     */
    public void deleteAlbumService(TAlbum album){
        albumRepository.delete(album);
    }

    /**
     * Vérifie si un album existe déjà par son nom.
     * @param name Le nom de l'album à vérifier.
     * @return true si l'album existe, false sinon.
     */
    public Boolean isExists(String name){
        return !albumRepository.findByAlbumNameIgnoreCase(name).isEmpty();
    }
}

