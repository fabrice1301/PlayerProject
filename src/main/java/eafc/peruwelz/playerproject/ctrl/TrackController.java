package eafc.peruwelz.playerproject.ctrl;

import eafc.peruwelz.playerproject.Class.AlertClass;
import eafc.peruwelz.playerproject.Class.Filter;
import eafc.peruwelz.playerproject.domain.*;

import eafc.peruwelz.playerproject.Class.Catalog;
import eafc.peruwelz.playerproject.modelControl.AlbumModelSelection;
import eafc.peruwelz.playerproject.modelControl.ArtistModelSelection;
import eafc.peruwelz.playerproject.modelControl.GenreModelSelection;
import eafc.peruwelz.playerproject.modelControl.PlaylistModelSelection;
import eafc.peruwelz.playerproject.service.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class TrackController {

    @Autowired
    private ApplicationContext context;
    private CatalogController catalogController;
    private TTrack track;
    private TTrack trackToModify;
    private TGenre genre;
    private TArtist artist;
    private TPlaylist playlist;
    private TAlbum album;
    private TrackService trackService;
    private GenreService genreService;
    private ArtistService artistService;
    private PlaylistService playlistService;
    private AlbumService albumService;
    private Catalog catalog;
    private File file;
    private File picture;
    private String trackPathPicture;
    private Boolean update=false;

    @FXML
    private DatePicker dateTrack;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button searchTrackBtn;

    @FXML
    private Button searchPictureTrackBtn;

    @FXML
    private TextField trackPathField;

    @FXML
    private TextField trackTitleField;

    @FXML
    private TextField trackPathPictureField;

    @FXML
    private ImageView trackPicture;

    @FXML
    private TextField genreNameField;

    @FXML
    private TextField artistNameField;

    @FXML
    private TextField playlistNameField;

    @FXML
    private TextField albumNameField;

    @FXML
    private TableView<GenreModelSelection> genreTableView;

    @FXML
    private ObservableList<GenreModelSelection> dataGenreTable;

    @FXML
    private TableColumn<GenreModelSelection, Boolean> selectedGenreCol;

    @FXML
    private TableColumn<GenreModelSelection, String> genreNameCol;

    @FXML
    private TableView<ArtistModelSelection> artistTableView;

    @FXML
    private ObservableList<ArtistModelSelection> dataArtistTable;

    @FXML
    private TableView<PlaylistModelSelection> playlistTableView;

    @FXML
    private ObservableList<AlbumModelSelection> dataAlbumTable;

    @FXML
    private TableView<AlbumModelSelection> albumTableView;

    @FXML
    private ObservableList<PlaylistModelSelection> dataPlaylistTable;

    @FXML
    private TableColumn<ArtistModelSelection, Boolean> selectedArtistCol;

    @FXML
    private TableColumn<ArtistModelSelection, String> artistNameCol;

    @FXML
    private TableColumn<PlaylistModelSelection, Boolean> selectedPlaylistCol;

    @FXML
    private TableColumn<PlaylistModelSelection, String> playlistNameCol;

    @FXML
    private TableColumn<ArtistModelSelection, Boolean> selectedAlbumCol;

    @FXML
    private TableColumn<AlbumModelSelection, String> albumNameCol;

    @Autowired
    public TrackController(TrackService trackService, Catalog catalog, GenreService genreService, ArtistService artistService, PlaylistService playlistService, AlbumService albumService,CatalogController catalogController){
        this.trackService=trackService;
        this.genreService=genreService;
        this.artistService=artistService;
        this.playlistService=playlistService;
        this.albumService=albumService;
        this.catalog = catalog;
        this.catalogController=catalogController;
    }

    @FXML
    private void initialize(){

        trackToModify=new TTrack();
        //Ajoute la loupe dans les boutons de recherche
        searchTrackBtn.setText("\uD83D\uDD0D " + searchTrackBtn.getText());
        searchPictureTrackBtn.setText("\uD83D\uDD0D " + searchPictureTrackBtn.getText());
        this.trackPathPicture=null;

        //Gestion des artistes
        this.selectedArtistCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
        this.selectedArtistCol.setCellFactory(tc -> new CheckBoxTableCell<>());
        this.artistNameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getArtist().getArtistName()));
        artistNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        artistNameCol.setOnEditCommit(event -> {
            ArtistModelSelection artist = event.getRowValue();
            String newName = event.getNewValue();
            if ((newName == null || newName.trim().isEmpty())) {
                artistTableView.refresh();
            } else {
                artist.getArtist().setArtistName(newName);
                artistService.saveArtistService(artist.getArtist());
                Filter.filterInstance.reload();
            }
        });
        List<TArtist> listArtist = artistService.findAllArtistService();
        dataArtistTable = FXCollections.observableArrayList();
        for (TArtist artist : listArtist) {
            dataArtistTable.add(new ArtistModelSelection(artist, false));
        }
        artistTableView.setItems(dataArtistTable);
        artistTableView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {
                TArtist selectedItem = artistTableView.getSelectionModel().getSelectedItem();
                int index=artistTableView.getSelectionModel().getSelectedIndex();
                TArtist deletedArtist = artistTableView.getSelectionModel().getSelectedItem().getArtist();
                if (selectedItem != null && trackService.findByArtistService(deletedArtist).isEmpty()) {
                    if ( AlertClass.showConfirmationDeleteDialog("cet article")){
                        dataArtistTable.remove(index);
                        artistService.deleteArtistService(deletedArtist);
                        artistTableView.getSelectionModel().clearSelection();
                        artistTableView.refresh();
                    }
                }else{
                    AlertClass.showInformationDeleteDialog("cet article");
                    System.out.println("Alerte");
                }
            }
        });


        // Gestion des genres
        this.selectedGenreCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
        this.selectedGenreCol.setCellFactory(tc -> new CheckBoxTableCell<>());
        this.genreNameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getGenre().getGenreName()));
        genreNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        genreNameCol.setOnEditCommit(event -> {
            GenreModelSelection genre = event.getRowValue();
            String newName = event.getNewValue();
            if (newName == null || newName.trim().isEmpty()) {
                genreTableView.refresh();
            } else {
                genre.getGenre().setGenreName(newName);
                genreService.saveGenreService(genre.getGenre());
                Filter.filterInstance.reload();
            }
        });
        List<TGenre> listGenre = genreService.findAllGenreService();
        dataGenreTable = FXCollections.observableArrayList();
        for (TGenre genre : listGenre) {
            dataGenreTable.add(new GenreModelSelection(genre, false));
        }
        genreTableView.setItems(dataGenreTable);
        genreTableView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {
                TGenre selectedItem = genreTableView.getSelectionModel().getSelectedItem();
                int index = genreTableView.getSelectionModel().getSelectedIndex();
                TGenre deletedGenre = genreTableView.getSelectionModel().getSelectedItem().getGenre();
                if (selectedItem != null && trackService.findByGenreService(deletedGenre).isEmpty()) {
                    if (AlertClass.showConfirmationDeleteDialog("ce genre")) {
                        dataGenreTable.remove(index);
                        genreService.deleteGenreService(deletedGenre);
                        genreTableView.getSelectionModel().clearSelection();
                        genreTableView.refresh();
                    }
                } else {
                    AlertClass.showInformationDeleteDialog("ce genre");
                    System.out.println("Alerte");
                }
            }
        });

// Gestion des albums
        this.selectedAlbumCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
        this.selectedAlbumCol.setCellFactory(tc -> new CheckBoxTableCell<>());
        this.albumNameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAlbum().getAlbumName()));
        albumNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        albumNameCol.setOnEditCommit(event -> {
            AlbumModelSelection album = event.getRowValue();
            String newName = event.getNewValue();
            if (newName == null || newName.trim().isEmpty()) {
                albumTableView.refresh();
            } else {
                album.getAlbum().setAlbumName(newName);
                albumService.saveAlbumService(album.getAlbum());
                Filter.filterInstance.reload();
            }
        });
        List<TAlbum> listAlbum = albumService.findAllAlbumService();
        dataAlbumTable = FXCollections.observableArrayList();
        for (TAlbum album : listAlbum) {
            dataAlbumTable.add(new AlbumModelSelection(album, false));
        }
        albumTableView.setItems(dataAlbumTable);
        albumTableView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {
                TAlbum selectedItem = albumTableView.getSelectionModel().getSelectedItem();
                int index = albumTableView.getSelectionModel().getSelectedIndex();
                TAlbum deletedAlbum = albumTableView.getSelectionModel().getSelectedItem().getAlbum();
                if (selectedItem != null && trackService.findByAlbumService(deletedAlbum).isEmpty()) {
                    if (AlertClass.showConfirmationDeleteDialog("cet album")) {
                        dataAlbumTable.remove(index);
                        albumService.deleteAlbumService(deletedAlbum);
                        albumTableView.getSelectionModel().clearSelection();
                        albumTableView.refresh();
                    }
                } else {
                    AlertClass.showInformationDeleteDialog("cet album");
                    System.out.println("Alerte");
                }
            }
        });

// Gestion des playlists
        this.selectedPlaylistCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
        this.selectedPlaylistCol.setCellFactory(tc -> new CheckBoxTableCell<>());
        this.playlistNameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPlaylist().getPlaylistName()));
        playlistNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        playlistNameCol.setOnEditCommit(event -> {
            PlaylistModelSelection playlist = event.getRowValue();
            String newName = event.getNewValue();
            if (newName == null || newName.trim().isEmpty()) {
                playlistTableView.refresh();
            } else {
                playlist.getPlaylist().setPlaylistName(newName);
                playlistService.savePlaylistService(playlist.getPlaylist());
                Filter.filterInstance.reload();
            }
        });
        List<TPlaylist> listPlaylist = playlistService.findAllPlaylistService();
        dataPlaylistTable = FXCollections.observableArrayList();
        for (TPlaylist playlist : listPlaylist) {
            dataPlaylistTable.add(new PlaylistModelSelection(playlist, false));
        }
        playlistTableView.setItems(dataPlaylistTable);
        playlistTableView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {
                TPlaylist selectedItem = playlistTableView.getSelectionModel().getSelectedItem();
                int index = playlistTableView.getSelectionModel().getSelectedIndex();
                TPlaylist deletedPlaylist = playlistTableView.getSelectionModel().getSelectedItem().getPlaylist();
                if (selectedItem != null && trackService.findByPlaylistService(deletedPlaylist).isEmpty()) {
                    if (AlertClass.showConfirmationDeleteDialog("cette playlist")) {
                        dataPlaylistTable.remove(index);
                        playlistService.deletePlaylistService(deletedPlaylist);
                        playlistTableView.getSelectionModel().clearSelection();
                        playlistTableView.refresh();
                    }
                } else {
                    AlertClass.showInformationDeleteDialog("cette playlist");
                    System.out.println("Alerte");
                }
            }
        });
    }


    public void setTrackToModify(TTrack track, Boolean update){
        if (track != null){
            this.update=update;
            this.trackToModify=track;
            trackPathField.setText(this.trackToModify.getTrackPath());
            System.out.println(track.getTrackPath());
            System.out.println(trackToModify.getTrackPath());
            trackTitleField.setText(this.trackToModify.getTrackTitle());
            dateTrack.setValue(this.trackToModify.getTrackDate());
            if (trackToModify.getTrackPicture()!=null){
                trackPathPicture=this.trackToModify.getTrackPicture();
                Image image=new Image(trackPathPicture);
                trackPicture.setImage(image);
            }

            Set<TGenre> listGenre=this.trackToModify.getTrackGenreList();
            for (GenreModelSelection genre : dataGenreTable){
                genre.setSelected(listGenre.contains(genre.getGenre()));
            }

            Set<TArtist> listArtist=this.trackToModify.getTrackArtistList();
            for (ArtistModelSelection artist : dataArtistTable){
                artist.setSelected(listArtist.contains(artist.getArtist()));
            }

            Set<TAlbum> listAlbum=this.trackToModify.getTrackAlbumList();
            for (AlbumModelSelection album : dataAlbumTable){
                album.setSelected(listAlbum.contains(album.getAlbum()));
            }

            Set<TPlaylist> listPlaylist=this.trackToModify.getTrackPlaylistList();
            for (PlaylistModelSelection playlist : dataPlaylistTable){
                playlist.setSelected(listPlaylist.contains(playlist.getPlaylist()));
            }
        }
    }

    @FXML
    private void searchTrackEvent() {

        // Créer un FileChooser
        FileChooser fileChooser = new FileChooser();

        // Configurer les filtres pour les fichiers MP3 uniquement
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers MP3 (*.mp3)", "*.mp3");
        fileChooser.getExtensionFilters().add(extFilter);

        // Ouvrir la boîte de dialogue pour sélectionner un fichier
        this.file = fileChooser.showOpenDialog(new Stage());

        // Si un fichier est sélectionné, mettre à jour l'interface
        if (this.file != null) {
            trackPathField.setText(this.file.getName());
            trackTitleField.setText(extensionLess(this.file.getName()));
        }
    }

    @FXML
    private void saveTrackEvent() throws UnsupportedAudioFileException, IOException {
        track=new TTrack();
        if (this.update) {
            track=this.trackToModify;
        }

        //On vérifie qu'un titre a été renseigné
        if (Objects.equals(trackTitleField.getText(), "")) {
            //On récupère le nom du fichier sans l'extension
            this.track.setTrackTitle(extensionLess(this.file.getName()));
        }
        else{
            this.track.setTrackTitle(this.trackTitleField.getText());
        }

        //On récupère le chemin du fichier

        this.track.setTrackPath(trackPathField.getText());
        if (this.file!=null) {
            this.track.setTrackPath(file.getAbsolutePath());
            AudioFileFormat formatFile = AudioSystem.getAudioFileFormat(file);
            if (formatFile instanceof TAudioFileFormat) {
                // Récupérer les propriétés du fichier
                Map<?, ?> propriete = ((TAudioFileFormat) formatFile).properties();
                // Obtenir la durée en microsecondes
                Long timeMicroSecondes = (Long) propriete.get("duration");
                // Convertir la durée en secondes
                int time = (int) (timeMicroSecondes / 1000000);
                this.track.setTrackTime(time);
            }
            this.file=null;
        }

        //On récupère les genres sélectionnés
        Set<TGenre> genreSelected = new HashSet<>();
        dataGenreTable.forEach(hsl -> {if (hsl.isSelected()) genreSelected.add(hsl.getGenre());});
        track.setTrackGenreList(genreSelected);

        //On récupère les artistes sélectionnés
        Set<TArtist> artistSelected = new HashSet<>();
        dataArtistTable.forEach(hsl -> {if (hsl.isSelected()) artistSelected.add(hsl.getArtist());});
        track.setTrackArtistList(artistSelected);

        //On récupère les playlists sélectionnées
        Set<TPlaylist> playlistSelected = new HashSet<>();
        dataPlaylistTable.forEach(hsl -> {if (hsl.isSelected()) playlistSelected.add(hsl.getPlaylist());});
        track.setTrackPlaylistList(playlistSelected);

        //On récupère les albums sélectionnés
        Set<TAlbum> albumSelected = new HashSet<>();
        dataAlbumTable.forEach(hsl -> {if (hsl.isSelected()) albumSelected.add(hsl.getAlbum());});
        track.setTrackAlbumList(albumSelected);

        //On récupère l'adresse de la pochette si elle existe
        if (this.trackPathPicture!=null) track.setTrackPicture(this.trackPathPicture);

        if (this.dateTrack!=null) track.setTrackDate(this.dateTrack.getValue());

        //On sauve la piste avec ses propriétés
        this.trackService.saveTrackService(this.track);

        if (!this.update) this.catalog.addTrack(this.track);
        else this.catalog.modifyTrack(catalog.getIndex(track),track);
        this.catalog.sortCatalog();
        CancelEvent();
    }


    private String extensionLess(String name){

        // Trouver la dernière position du point
        int position = name.lastIndexOf(".");

        // Retirer l'extension (tout après le dernier point)
        name = name.substring(0, position);
        return name;
    }

    @FXML
    private void CancelEvent(){
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void AddGenreEvent(){
        if (!genreNameField.getText().isEmpty() && genreNameField.getText().trim()!="") {
            genre = new TGenre();
            genre.setGenreName(genreNameField.getText());
            genreService.saveGenreService(genre);
            List<TGenre> list=genreService.findAllGenreService();
            this.dataGenreTable.clear();
            for (TGenre genre:list){
                dataGenreTable.add(new GenreModelSelection(genre,false));
            }
            genreTableView.refresh();
            genreNameField.setText("");
            //Si nous sommes la modification d'une track et non sur un ajout
            if (this.update) {
                Set<TGenre> listGenre = this.trackToModify.getTrackGenreList();
                for (GenreModelSelection genre : dataGenreTable) {
                    genre.setSelected(listGenre.contains(genre.getGenre()));
                }
            }
        }
    }

    @FXML
    private void AddArtistEvent(){
        if (!artistNameField.getText().isEmpty() && artistNameField.getText().trim()!=""){
            artist=new TArtist();
            artist.setArtistName(artistNameField.getText());
            artistService.saveArtistService(artist);
            List<TArtist> list=artistService.findAllArtistService();
            dataArtistTable.clear();
            for (TArtist artist:list){
                dataArtistTable.add(new ArtistModelSelection(artist,false));
            }
            artistTableView.refresh();
            artistNameField.setText("");
            //Si nous sommes la modification d'une track et non sur un ajout
            Set<TArtist> listArtist=this.trackToModify.getTrackArtistList();
            for (ArtistModelSelection artist : dataArtistTable){
                artist.setSelected(listArtist.contains(artist.getArtist()));
            }

        }
    }

    @FXML
    private void AddPlaylistEvent(){
        if (!playlistNameField.getText().isEmpty() && playlistNameField.getText().trim()!=""){
            playlist=new TPlaylist();
            playlist.setPlaylistName(playlistNameField.getText());
            playlistService.savePlaylistService(playlist);
            List<TPlaylist> list=playlistService.findAllPlaylistService();
            dataPlaylistTable.clear();
            for (TPlaylist playlist:list){
                dataPlaylistTable.add(new PlaylistModelSelection(playlist,false));
            }
            playlistTableView.refresh();
            playlistNameField.setText("");

            //Si nous sommes la modification d'une track et non sur un ajout
            Set<TPlaylist> listPlaylist=this.trackToModify.getTrackPlaylistList();
            for (PlaylistModelSelection playlist : dataPlaylistTable){
                playlist.setSelected(listPlaylist.contains(playlist.getPlaylist()));
            }
        }
    }

    @FXML
    private void AddAlbumEvent(){
        if (!albumNameField.getText().isEmpty() && albumNameField.getText().trim()!=""){
            album=new TAlbum();
            album.setAlbumName(albumNameField.getText());
            albumService.saveAlbumService(album);
            List<TAlbum> list=albumService.findAllAlbumService();
            dataAlbumTable.clear();
            for (TAlbum album:list){
                dataAlbumTable.add(new AlbumModelSelection(album,false));
            }
            albumTableView.refresh();
            albumNameField.setText("");

            //Si nous sommes la modification d'une track et non sur un ajout
            Set<TAlbum> listAlbum=this.trackToModify.getTrackAlbumList();
            for (AlbumModelSelection album : dataAlbumTable){
                album.setSelected(listAlbum.contains(album.getAlbum()));
            }
        }
    }

    @FXML
    private void searchPictureTrackEvent(){
        // Créer un FileChooser
        FileChooser fileChooser = new FileChooser();

        // Configurer les filtres pour les fichiers MP3 uniquement
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers JPG (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);

        // Ouvrir la boîte de dialogue pour sélectionner un fichier
        this.picture = fileChooser.showOpenDialog(new Stage());

        // Si un fichier est sélectionné, mettre à jour l'interface
        if (this.picture != null) {
            this.trackPathPictureField.setText(this.picture.getName());
            this.trackPathPicture=this.picture.toURI().toString();
            Image image = new Image(this.trackPathPicture);
            trackPicture.setImage(image);
        }
    }

    public void setUpdate(Boolean update){
        this.update=update;
    }

    public static boolean AlertDeleteDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer cet artiste ?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
