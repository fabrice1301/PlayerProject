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

public class Filter {
    private Catalog catalog;
    private ObservableList<TTrack> dataCatalogTable;
    private ObservableList<String> data;
    private ComboBox playlistComboBox;
    private ComboBox genreComboBox;
    private ComboBox artistComboBox;
    private ComboBox albumComboBox;
    private TrackService trackService;
    private TextField searchField;
    private String playlistFilter = null;
    private String genreFilter = null;
    private String artistFilter = null;
    private String albumFilter = null;
    private String textFilter = null;
    public static Filter filterInstance;

    public Filter(ComboBox playlist, ComboBox genre, ComboBox artist, ComboBox album, TrackService trackService,TextField searchField,Catalog catalog, CatalogController catalogController){
        this.catalog=catalog;
        this.playlistComboBox=playlist;
        this.albumComboBox=album;
        this.artistComboBox=artist;
        this.genreComboBox=genre;
        this.trackService=trackService;
        this.searchField=searchField;
        List<TTrack> trackList = this.trackService.findAllTrackService();
        this.catalog.setDataCatalogTable(trackList);
        Filter.filterInstance=this;
    }

    public void setup() {
        // Ajout d'un listener pour la ComboBox des genres
        genreComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.toString().equals(genreFilter)) {
                genreFilter=newValue.toString();
                reload();
            }
        });

        // Ajout d'un listener pour la ComboBox des playlists
        playlistComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.toString().equals(playlistFilter)) {
                playlistFilter=newValue.toString();
                reload();
            }
        });

        // Ajout d'un listener pour la ComboBox des artistes
        artistComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.toString().equals(artistFilter)) {
                artistFilter=newValue.toString();
                reload();
            }
        });

        // Ajout d'un listener pour la ComboBox des albums
        albumComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.toString().equals(albumFilter)) {
                albumFilter=newValue.toString();
                reload();
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            textFilter = newValue;
            reload();
        });

    }

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
        return artistComboBox;
    }

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

    public void reload() {
        if (Objects.equals(genreFilter, "Tous les genres")) genreFilter = null;
        if (Objects.equals(albumFilter, "Tous les albums")) albumFilter = null;
        if (Objects.equals(playlistFilter, "Toutes les playlists")) playlistFilter = null;
        if (Objects.equals(artistFilter, "Tous les artistes")) artistFilter = null;
        List<TTrack> allTracks = trackService.findAllTrackService();
        List<TTrack> filteredTracks = allTracks.stream()
                .filter(track -> {
                    boolean matchesArtist = artistFilter == null || track.getTrackArtistsAsString().contains(artistFilter);
                    boolean matchesAlbum = albumFilter == null || track.getTrackAlbumsAsString().contains(albumFilter);
                    boolean matchesGenre = genreFilter == null || track.getTrackGenresAsString().contains(genreFilter);
                    boolean matchesPlaylist = playlistFilter == null || track.getTrackPlaylistsAsString().contains(playlistFilter);
                    boolean matchesSearchField = textFilter == null || track.getTrackTitle().toLowerCase().contains(textFilter.toLowerCase()) || track.getTrackArtistsAsString().toLowerCase().contains(textFilter.toLowerCase()) || track.getTrackAlbumsAsString().toLowerCase().contains(textFilter.toLowerCase());
                    return matchesPlaylist && matchesGenre && matchesAlbum && matchesArtist && matchesSearchField;
                })
                .collect(Collectors.toList());
        this.catalog.setDataCatalogTable(filteredTracks);
    }



}
