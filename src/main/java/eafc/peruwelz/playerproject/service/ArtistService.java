package eafc.peruwelz.playerproject.service;

import eafc.peruwelz.playerproject.domain.TArtist;
import eafc.peruwelz.playerproject.repos.TArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service pour la gestion des artistes.
 */
@Service
public class ArtistService {

    /**
     * Repository des artistes, utilisé pour interagir avec la base de données.
     * Il permet de récupérer, sauvegarder, et supprimer des objets {@link TArtist}.
     */
    private final TArtistRepository artistRepository;

    /**
     * Constructeur du service ArtistService.
     * @param artistRepository Repository des artistes injecté par Spring.
     */
    @Autowired
    public ArtistService(TArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    /**
     * Récupère tous les artistes triés par nom en ordre alphabétique.
     * @return Liste triée de tous les artistes.
     */
    public List<TArtist> findAllArtistService(){
        return artistRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(artist -> artist.getArtistName().toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Sauvegarde un artiste dans le repository.
     * @param artist L'artiste à sauvegarder.
     */
    public void saveArtistService(TArtist artist){
        artistRepository.save(artist);
    }

    /**
     * Supprime un artiste du repository.
     * @param artist L'artiste à supprimer.
     */
    public void deleteArtistService(TArtist artist){
        artistRepository.delete(artist);
    }

    /**
     * Vérifie si un artiste existe déjà par son nom.
     * @param name Le nom de l'artiste à vérifier.
     * @return true si l'artiste existe, false sinon.
     */
    public Boolean isExists(String name){
        return !artistRepository.findByArtistNameIgnoreCase(name).isEmpty();
    }
}

