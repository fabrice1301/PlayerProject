package eafc.peruwelz.playerproject.Class;

import eafc.peruwelz.playerproject.ctrl.CatalogController;
import eafc.peruwelz.playerproject.domain.*;
import eafc.peruwelz.playerproject.service.TrackService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Classe de filtrage pour appliquer des filtres sur les pistes du catalogue.
 * Cette classe permet de filtrer les pistes selon plusieurs critères tels que
 * les playlists, les genres, les artistes, les albums et un champ de recherche textuel.
 */
public class Filter {

    private ObservableList<TTrack> dataCatalogTable;
    private ObservableList<String> data;
    private ComboBox playlistComboBox;
    private ComboBox genreComboBox;
    private ComboBox artistComboBox;
    private ComboBox albumComboBox;
    private TextField searchField;
    private String playlistFilter = null;
    private String genreFilter = null;
    private String artistFilter = null;
    private String albumFilter = null;
    private String textFilter = null;
    public static Filter filterInstance;
    private final TrackService trackService;
    private final Catalog catalog;

    /**
     * Constructeur de la classe Filter.
     *
     * @param playlist la ComboBox pour les playlists.
     * @param genre la ComboBox pour les genres.
     * @param artist la ComboBox pour les artistes.
     * @param album la ComboBox pour les albums.
     * @param searchField le champ de texte pour la recherche.
     * @param trackService le service des pistes pour récupérer les données.
     * @param catalogController le contrôleur du catalogue.
     * @param catalog le catalogue des pistes.
     */
    public Filter(ComboBox playlist, ComboBox genre, ComboBox artist, ComboBox album, TextField searchField,
                  TrackService trackService, CatalogController catalogController, Catalog catalog) {
        this.playlistComboBox = playlist;
        this.albumComboBox = album;
        this.artistComboBox = artist;
        this.genreComboBox = genre;
        this.searchField = searchField;
        this.trackService = trackService;
        this.catalog = catalog;
        List<TTrack> trackList = trackService.findAllTrackService();
        this.catalog.setDataCatalogTable(trackList);
        Filter.filterInstance = this;
    }

    /**
     * Configure les écouteurs pour les ComboBoxes et le champ de recherche.
     * Applique les filtres en fonction des éléments sélectionnés.
     */
    public void setup() {
        // Ajout d'un listener pour la ComboBox des genres
        genreComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.toString().equals(genreFilter)) {
                genreFilter = newValue.toString();
                reload();
            }
        });

        // Ajout d'un listener pour la ComboBox des playlists
        playlistComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.toString().equals(playlistFilter)) {
                playlistFilter = newValue.toString();
                reload();
            }
        });

        // Ajout d'un listener pour la ComboBox des artistes
        artistComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.toString().equals(artistFilter)) {
                artistFilter = newValue.toString();
                reload();
            }
        });

        // Ajout d'un listener pour la ComboBox des albums
        albumComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.toString().equals(albumFilter)) {
                albumFilter = newValue.toString();
                reload();
            }
        });

        // Ajout d'un listener pour le champ de recherche
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            textFilter = newValue;
            reload();
        });
    }

    /**
     * Recharge la ComboBox des albums avec les albums uniques triés provenant des pistes.
     *
     * @return la ComboBox des albums mise à jour.
     */
    public ComboBox reloadAlbum() {
        data = FXCollections.observableArrayList(
                this.catalog.getDataCatalogTable().stream()
                        .flatMap(track -> track.getTrackAlbumList().stream()
                                .map(TAlbum::getAlbumName))
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList())
        );
        albumComboBox.setItems(data);
        albumComboBox.getItems().add(0, "Tous les albums");
        return albumComboBox;
    }

    /**
     * Recharge la ComboBox des genres avec les genres uniques triés provenant des pistes.
     *
     * @return la ComboBox des genres mise à jour.
     */
    public ComboBox reloadGenre() {
        data = FXCollections.observableArrayList(
                this.catalog.getDataCatalogTable().stream()
                        .flatMap(track -> track.getTrackGenreList().stream()
                                .map(TGenre::getGenreName))
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList())
        );
        genreComboBox.setItems(data);
        genreComboBox.getItems().add(0, "Tous les genres");
        return genreComboBox;
    }

    /**
     * Recharge la ComboBox des artistes avec les artistes uniques triés provenant des pistes.
     *
     * @return la ComboBox des artistes mise à jour.
     */
    public ComboBox reloadArtist() {
        data = FXCollections.observableArrayList(
                this.catalog.getDataCatalogTable().stream()
                        .flatMap(track -> track.getTrackArtistList().stream()
                                .map(TArtist::getArtistName))
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList())
        );
        artistComboBox.setItems(data);
        artistComboBox.getItems().add(0, "Tous les artistes");
        return artistComboBox;
    }

    /**
     * Recharge la ComboBox des playlists avec les playlists uniques triées provenant des pistes.
     *
     * @return la ComboBox des playlists mise à jour.
     */
    public ComboBox reloadPlaylist() {
        data = FXCollections.observableArrayList(
                this.catalog.getDataCatalogTable().stream()
                        .flatMap(track -> track.getTrackPlaylistList().stream()
                                .map(TPlaylist::getPlaylistName))
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList())
        );
        playlistComboBox.setItems(data);
        playlistComboBox.getItems().add(0, "Toutes les playlists");
        return playlistComboBox;
    }

    /**
     * Applique les filtres sélectionnés et recharge le catalogue avec les pistes filtrées.
     * Les pistes sont filtrées selon les critères de genre, d'album, de playlist, d'artiste et de recherche textuelle.
     */
    public void reload() {
        // Réinitialisation des filtres si nécessaire
        if (Objects.equals(genreFilter, "Tous les genres")) genreFilter = null;
        if (Objects.equals(albumFilter, "Tous les albums")) albumFilter = null;
        if (Objects.equals(playlistFilter, "Toutes les playlists")) playlistFilter = null;
        if (Objects.equals(artistFilter, "Tous les artistes")) artistFilter = null;

        // Filtrage des pistes selon les critères définis
        List<TTrack> allTracks = trackService.findAllTrackService();
        List<TTrack> filteredTracks = allTracks.stream()
                .filter(track -> {
                    boolean matchesArtist = artistFilter == null || track.getTrackArtistsAsString().contains(artistFilter);
                    boolean matchesAlbum = albumFilter == null || track.getTrackAlbumsAsString().contains(albumFilter);
                    boolean matchesGenre = genreFilter == null || track.getTrackGenresAsString().contains(genreFilter);
                    boolean matchesPlaylist = playlistFilter == null || track.getTrackPlaylistsAsString().contains(playlistFilter);
                    boolean matchesSearchField = textFilter == null || track.getTrackTitle().toLowerCase().contains(textFilter.toLowerCase()) ||
                            track.getTrackArtistsAsString().toLowerCase().contains(textFilter.toLowerCase()) ||
                            track.getTrackAlbumsAsString().toLowerCase().contains(textFilter.toLowerCase());
                    return matchesPlaylist && matchesGenre && matchesAlbum && matchesArtist && matchesSearchField;
                })
                .collect(Collectors.toList());
        this.catalog.setDataCatalogTable(filteredTracks);
    }
}