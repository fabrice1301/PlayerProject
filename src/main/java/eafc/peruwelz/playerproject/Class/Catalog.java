package eafc.peruwelz.playerproject.Class;

import eafc.peruwelz.playerproject.ctrl.CatalogController;
import eafc.peruwelz.playerproject.domain.TTrack;
import eafc.peruwelz.playerproject.service.TrackService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Comparator;
import java.util.List;


/**
 * Classe représentant un catalogue de pistes musicales.
 * Cette classe gère une liste observable de pistes (de type TTrack) et fournit des méthodes pour ajouter,
 * modifier, supprimer, trier et rechercher des pistes dans le catalogue.
 */
@Component
public class Catalog {

    private ObservableList<TTrack> dataCatalogTable;
    private CatalogController catalogController;
    private final TrackService trackService;

    /**
     * Constructeur qui initialise le catalogue et charge les pistes depuis le service.
     *
     * @param trackService le service de gestion des pistes musicales.
     */
    @Autowired
    public Catalog(TrackService trackService){
        this.dataCatalogTable = FXCollections.observableArrayList();
        this.trackService = trackService;

        List<TTrack> trackList = trackService.findAllTrackService();
        this.dataCatalogTable.addAll(trackList);
    }

    /**
     * Définit le contrôleur du catalogue.
     *
     * @param catalogController le contrôleur du catalogue à associer.
     */
    public void setCatalogController(CatalogController catalogController){
        this.catalogController = catalogController;
    }

    /**
     * Ajoute une nouvelle piste au catalogue.
     *
     * @param track la piste à ajouter.
     */
    public void addTrack(TTrack track) {
        dataCatalogTable.add(track);
    }

    /**
     * Modifie une piste existante dans le catalogue à l'indice spécifié.
     *
     * @param index l'indice de la piste à modifier.
     * @param track la nouvelle piste à mettre à la place.
     */
    public void modifyTrack(int index, TTrack track){
        dataCatalogTable.set(index, track);
    }

    /**
     * Retourne l'indice d'une piste dans le catalogue.
     *
     * @param track la piste à rechercher dans le catalogue.
     * @return l'indice de la piste, ou -1 si la piste n'est pas trouvée.
     */
    public int getIndex(TTrack track){
        return dataCatalogTable.indexOf(track);
    }

    /**
     * Trie le catalogue en fonction du titre des pistes.
     */
    public void sortCatalog(){
        FXCollections.sort(this.dataCatalogTable, Comparator.comparing(TTrack::getTrackTitle));
    }

    /**
     * Définit les données du catalogue en remplaçant la liste actuelle par une nouvelle liste triée.
     *
     * @param list la nouvelle liste de pistes à définir dans le catalogue.
     */
    public void setDataCatalogTable(List<TTrack> list){
        this.dataCatalogTable.setAll(list.stream().sorted(Comparator.comparing(TTrack::getTrackTitle)).toList());
    }

    /**
     * Retourne la liste observable des pistes du catalogue.
     *
     * @return la liste observable des pistes.
     */
    public ObservableList<TTrack> getDataCatalogTable(){
        return this.dataCatalogTable;
    }

    /**
     * Supprime une piste du catalogue.
     *
     * @param track la piste à supprimer.
     */
    public void removeTrack(TTrack track){
        this.dataCatalogTable.remove(track);
    }

    /**
     * Retourne la piste située à l'indice spécifié dans le catalogue.
     *
     * @param index l'indice de la piste à récupérer.
     * @return la piste à cet indice.
     */
    public TTrack getTrackByIndex(int index){
        return dataCatalogTable.get(index);
    }

    /**
     * Initialise les pistes en attente (marque une piste comme non en attente si elle l'était),
     * et enregistre les modifications via le service de piste.
     */
    public void initTrackWaiting(){
        for(TTrack track : this.dataCatalogTable){
            if (track.getTrackWaiting()){
                track.setTrackWaiting(false);
                this.trackService.saveTrackService(track);
            }
        }
    }
}
