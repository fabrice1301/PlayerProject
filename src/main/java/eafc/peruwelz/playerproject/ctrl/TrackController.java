package eafc.peruwelz.playerproject.ctrl;

import eafc.peruwelz.playerproject.Class.AlertClass;
import eafc.peruwelz.playerproject.Class.Catalog;
import eafc.peruwelz.playerproject.Class.Filter;
import eafc.peruwelz.playerproject.domain.*;
import eafc.peruwelz.playerproject.modelControl.AlbumModelSelection;
import eafc.peruwelz.playerproject.modelControl.ArtistModelSelection;
import eafc.peruwelz.playerproject.modelControl.GenreModelSelection;
import eafc.peruwelz.playerproject.modelControl.PlaylistModelSelection;
import eafc.peruwelz.playerproject.service.*;
import eafc.peruwelz.playerproject.validation.StringValidation;
import eafc.peruwelz.playerproject.validation.ValidationManager;
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
import org.springframework.stereotype.Controller;
import org.tritonus.share.sampled.file.TAudioFileFormat;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Controlleur pour la gestion des pistes dans le catalogue musical.
 * Il permet d'ajouter, modifier, supprimer des pistes, ainsi que d'interagir
 * avec les genres, artistes, albums et playlists associés.
 */
@Controller
public class TrackController {
    /**
     * Piste à modifier dans l'interface.
     */
    private TTrack trackToModify;

    /**
     * Genre associé à la piste.
     */
    private TGenre genre;

    /**
     * Artiste associé à la piste.
     */
    private TArtist artist;

    /**
     * Playlist associée à la piste.
     */
    private TPlaylist playlist;

    /**
     * Album associé à la piste.
     */
    private TAlbum album;

    /**
     * Service pour gérer les pistes.
     */
    private final TrackService trackService;

    /**
     * Service pour gérer les genres.
     */
    private GenreService genreService;

    /**
     * Service pour gérer les artistes.
     */
    private ArtistService artistService;

    /**
     * Service pour gérer les playlists.
     */
    private PlaylistService playlistService;

    /**
     * Service pour gérer les albums.
     */
    private AlbumService albumService;

    /**
     * Catalogue des éléments musicaux.
     */
    private final Catalog catalog;

    /**
     * Fichier de la piste.
     */
    private File file;

    /**
     * Fichier de l'image associée à la piste.
     */
    private File picture;

    /**
     * Chemin de l'image de la piste.
     */
    private String trackPathPicture;

    /**
     * Indicateur si la piste est en mode de mise à jour.
     */
    private Boolean update = false;

    /**
     * Contrôleur du catalogue pour gérer les interactions avec le catalogue principal.
     */
    private final CatalogController catalogController;

    /**
     * Sélecteur de date pour la piste.
     */
    @FXML
    private DatePicker dateTrack;

    /**
     * Bouton pour sauvegarder la piste.
     */
    @FXML
    private Button saveTrackBtn;

    /**
     * Bouton pour annuler l'opération en cours.
     */
    @FXML
    private Button cancelBtn;

    /**
     * Bouton pour rechercher une piste.
     */
    @FXML
    private Button searchTrackBtn;

    /**
     * Bouton pour rechercher une image de piste.
     */
    @FXML
    private Button searchPictureTrackBtn;

    /**
     * Bouton pour ajouter un genre.
     */
    @FXML
    private Button addGenreBtn;

    /**
     * Bouton pour ajouter un album.
     */
    @FXML
    private Button addAlbumBtn;

    /**
     * Bouton pour ajouter un artiste.
     */
    @FXML
    private Button addArtistBtn;

    /**
     * Bouton pour ajouter une playlist.
     */
    @FXML
    private Button addPlaylistBtn;

    /**
     * Bouton pour supprimer une image associée à la piste.
     */
    @FXML
    private Button trashPictureBtn;

    /**
     * Champ de texte pour le chemin du fichier de la piste.
     */
    @FXML
    private TextField trackPathField;

    /**
     * Champ de texte pour le titre de la piste.
     */
    @FXML
    private TextField trackTitleField;

    /**
     * Champ de texte pour le chemin de l'image associée à la piste.
     */
    @FXML
    private TextField trackPathPictureField;

    /**
     * Image affichant la pochette de la piste.
     */
    @FXML
    private ImageView trackPicture;

    /**
     * Champ de texte pour le nom du genre.
     */
    @FXML
    private TextField genreNameField;

    /**
     * Champ de texte pour le nom de l'artiste.
     */
    @FXML
    private TextField artistNameField;

    /**
     * Champ de texte pour le nom de la playlist.
     */
    @FXML
    private TextField playlistNameField;

    /**
     * Champ de texte pour le nom de l'album.
     */
    @FXML
    private TextField albumNameField;

    /**
     * TableView contenant la liste des genres disponibles.
     */
    @FXML
    private TableView<GenreModelSelection> genreTableView;

    /**
     * Liste observable contenant les données des genres.
     */
    @FXML
    private ObservableList<GenreModelSelection> dataGenreTable;

    /**
     * Colonne indiquant si un genre est sélectionné.
     */
    @FXML
    private TableColumn<GenreModelSelection, Boolean> selectedGenreCol;

    /**
     * Colonne affichant le nom du genre.
     */
    @FXML
    private TableColumn<GenreModelSelection, String> genreNameCol;

    /**
     * TableView contenant la liste des artistes disponibles.
     */
    @FXML
    private TableView<ArtistModelSelection> artistTableView;

    /**
     * Liste observable contenant les données des artistes.
     */
    @FXML
    private ObservableList<ArtistModelSelection> dataArtistTable;

    /**
     * TableView contenant la liste des playlists disponibles.
     */
    @FXML
    private TableView<PlaylistModelSelection> playlistTableView;

    /**
     * Liste observable contenant les données des albums.
     */
    @FXML
    private ObservableList<AlbumModelSelection> dataAlbumTable;

    /**
     * TableView contenant la liste des albums disponibles.
     */
    @FXML
    private TableView<AlbumModelSelection> albumTableView;

    /**
     * Liste observable contenant les données des playlists.
     */
    @FXML
    private ObservableList<PlaylistModelSelection> dataPlaylistTable;

    /**
     * Colonne indiquant si un artiste est sélectionné.
     */
    @FXML
    private TableColumn<ArtistModelSelection, Boolean> selectedArtistCol;

    /**
     * Colonne affichant le nom de l'artiste.
     */
    @FXML
    private TableColumn<ArtistModelSelection, String> artistNameCol;

    /**
     * Colonne indiquant si une playlist est sélectionnée.
     */
    @FXML
    private TableColumn<PlaylistModelSelection, Boolean> selectedPlaylistCol;

    /**
     * Colonne affichant le nom de la playlist.
     */
    @FXML
    private TableColumn<PlaylistModelSelection, String> playlistNameCol;

    /**
     * Colonne indiquant si un album est sélectionné.
     */
    @FXML
    private TableColumn<AlbumModelSelection, Boolean> selectedAlbumCol;

    /**
     * Colonne affichant le nom de l'album.
     */
    @FXML
    private TableColumn<AlbumModelSelection, String> albumNameCol;


    /**
     * Constructeur de la classe TrackController.
     *
     * @param trackService Service pour la gestion des pistes.
     * @param genreService Service pour la gestion des genres.
     * @param artistService Service pour la gestion des artistes.
     * @param playlistService Service pour la gestion des playlists.
     * @param albumService Service pour la gestion des albums.
     * @param catalog Catalogue principal des éléments musicaux.
     * @param catalogController Contrôleur principal du catalogue.
     */
    @Autowired
    public TrackController(TrackService trackService, GenreService genreService, ArtistService artistService, PlaylistService playlistService, AlbumService albumService, Catalog catalog, CatalogController catalogController) {
        this.genreService=genreService;
        this.artistService=artistService;
        this.playlistService=playlistService;
        this.albumService=albumService;
        this.catalog = catalog;
        this.catalogController=catalogController;
        this.trackService=trackService;
    }

    /**
     * Initialisation des composants et des validations dans l'interface.
     */
    @FXML
    private void initialize(){
        this.update = false;
        StringValidation stringValidation =
                new StringValidation("max. 255 caractères",true);

        stringValidation
                .setMaxLength(255)
                .setRegularExp(".*");
        ValidationManager.getInstance().setValidation(trackTitleField,stringValidation);

        StringValidation stringValidation2 =
                new StringValidation("Obligatoire", true);
        stringValidation2
                .setMaxLength(255)
                .setRegularExp(".*");
        ValidationManager.getInstance().setValidation(trackPathField, stringValidation2);

        ValidationManager.getInstance().setSubmitButton(saveTrackBtn, trackTitleField, trackPathField);

        StringValidation stringValidation3 =
                new StringValidation("max. 50 caractères",false);
        stringValidation3
                .setMaxLength(50)
                .setRegularExp(".*");
        ValidationManager.getInstance().setValidation(genreNameField,stringValidation3);
        ValidationManager.getInstance().setValidation(albumNameField,stringValidation3);
        ValidationManager.getInstance().setValidation(artistNameField,stringValidation3);
        ValidationManager.getInstance().setValidation(playlistNameField,stringValidation3);
        ValidationManager.getInstance().setSubmitButton(addGenreBtn,genreNameField);
        ValidationManager.getInstance().setSubmitButton(addAlbumBtn,albumNameField);
        ValidationManager.getInstance().setSubmitButton(addArtistBtn,artistNameField);
        ValidationManager.getInstance().setSubmitButton(addPlaylistBtn,playlistNameField);
        trackToModify=new TTrack();
        //Ajoute la loupe dans les boutons de recherche
        searchTrackBtn.setText("\uD83D\uDD0D");
        searchPictureTrackBtn.setText("\uD83D\uDD0D");
        trashPictureBtn.setText("\uD83D\uDDD1");
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
                    if (AlertClass.showConfirmationDeleteDialog("cet artiste")) {
                        dataArtistTable.remove(index);
                        artistService.deleteArtistService(deletedArtist);
                        artistTableView.getSelectionModel().clearSelection();
                        artistTableView.refresh();
                    }
                }else{
                    AlertClass.showInformationDeleteDialog("cet artiste");
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

    /**
     * Définit la piste à modifier dans l'interface.
     * Cette méthode est utilisée pour pré-remplir les champs de l'interface utilisateur avec les informations
     * de la piste à modifier.
     *
     * @param track La piste à modifier.
     */
    public void setTrackToModify(TTrack track) {
        if (track != null){
            this.update = true;
            this.trackToModify=track;
            trackPathField.setText(this.trackToModify.getTrackPath());
            trackTitleField.setText(this.trackToModify.getTrackTitle());
            dateTrack.setValue(this.trackToModify.getTrackDate());
            if (trackToModify.getTrackPicture()==null){
                trackPicture.setImage(new Image(getClass().getResource("/images/vide.jpg").toExternalForm()));}
            else {

                File file = new File(this.trackToModify.getTrackPicture());
                if (file.exists() && file.isFile()) {
                    try {
                        trackPicture.setImage(new Image(file.toURI().toString()));
                        trackPathPictureField.setText(trackToModify.getTrackPicture());

                        trackPathPicture = trackToModify.getTrackPicture();
                    } catch (Exception e) {
                        trackPicture.setImage(new Image(getClass().getResource("/images/vide.jpg").toExternalForm()));
                    }
                }

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

    /**
     * Ouvre une boîte de dialogue pour rechercher et sélectionner un fichier MP3.
     * Cette méthode est utilisée pour permettre à l'utilisateur de sélectionner un fichier MP3 à ajouter à la piste.
     */
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

    /**
     * Sauvegarde les informations d'une piste, qu'elle soit nouvelle ou modifiée.
     * Cette méthode récupère toutes les informations sur la piste, y compris le titre, le chemin, les genres, artistes, albums,
     * playlists, et sauvegarde ces informations dans le service de piste ainsi que dans le catalogue.
     *
     * @throws UnsupportedAudioFileException Si le fichier audio n'est pas supporté.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @FXML
    private void saveTrackEvent() throws UnsupportedAudioFileException, IOException {
        TTrack track = new TTrack();
        if (this.update) {
            track = this.trackToModify;
        }
        //On vérifie qu'un titre a été renseigné
        if (Objects.equals(trackTitleField.getText(), "")) {
            //On récupère le nom du fichier sans l'extension
            track.setTrackTitle(extensionLess(this.file.getName()));
        }
        else{
            track.setTrackTitle(this.trackTitleField.getText());
        }
        //On récupère le chemin du fichier
        track.setTrackPath(trackPathField.getText());
        if (this.file!=null) {
            track.setTrackPath(file.getAbsolutePath());
            AudioFileFormat formatFile = AudioSystem.getAudioFileFormat(file);
            if (formatFile instanceof TAudioFileFormat) {
                // Récupérer les propriétés du fichier
                Map<?, ?> propriete = ((TAudioFileFormat) formatFile).properties();
                // Obtenir la durée en microsecondes
                Long timeMicroSecondes = (Long) propriete.get("duration");
                // Convertir la durée en secondes
                int time = (int) (timeMicroSecondes / 1000000);
                track.setTrackTime(time);
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
        if (this.trackPathPicture != null) {
            track.setTrackPicture(trackPathPictureField.getText());
        } else {
            track.setTrackPicture(null);
        }

        if (this.dateTrack!=null) track.setTrackDate(this.dateTrack.getValue());

        //On sauve la piste avec ses propriétés
        trackService.saveTrackService(track);
        if (!this.update) this.catalog.addTrack(track);
        else {
            this.catalog.modifyTrack(this.catalog.getIndex(track), track);
            this.catalogController.updateTrackWaitingList(track);
        }
        this.catalog.sortCatalog();
        CancelEvent();
    }

    /**
     * Retire l'extension du nom de fichier.
     * Cette méthode supprime l'extension d'un nom de fichier pour obtenir le nom sans l'extension.
     *
     * @param name Le nom du fichier avec l'extension.
     * @return Le nom du fichier sans extension.
     */
    private String extensionLess(String name){
        if (name != null) {
            // Trouver la dernière position du point
            int position = name.lastIndexOf(".");

            // Retirer l'extension (tout après le dernier point)
            name = name.substring(0, position);
        }

        return name;
    }

    /**
     * Ferme la fenêtre en cours lorsqu'un utilisateur clique sur le bouton d'annulation.
     * Cette méthode est utilisée pour fermer la fenêtre sans effectuer de modifications.
     */
    @FXML
    private void CancelEvent(){
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Ajoute un genre à la liste des genres disponibles.
     * Cette méthode est utilisée pour ajouter un nouveau genre à la base de données et le mettre à jour dans l'interface utilisateur.
     * Si le genre existe déjà, un message d'erreur est affiché.
     */
    @FXML
    private void AddGenreEvent(){
        if (!genreNameField.getText().isEmpty() && genreNameField.getText().trim()!="") {
            if (genreService.isExists(genreNameField.getText())) {
                AlertClass.messageError("Le genre existe déjà !");
            } else {
                genre = new TGenre();
                genre.setGenreName(genreNameField.getText());
                genreService.saveGenreService(genre);
                List<TGenre> list = genreService.findAllGenreService();
                this.dataGenreTable.clear();
                for (TGenre genre : list) {
                    dataGenreTable.add(new GenreModelSelection(genre, false));
                }
                genreTableView.refresh();
                genreNameField.setText("");
                //Si nous sommes sur la modification d'une track et non sur un ajout
                if (this.update) {
                    Set<TGenre> listGenre = this.trackToModify.getTrackGenreList();
                    if (listGenre != null) {
                        for (GenreModelSelection genre : dataGenreTable) {
                            genre.setSelected(listGenre.contains(genre.getGenre()));
                        }
                    }

                }
            }

        }
    }

    /**
     * Ajoute un artiste à la liste des artistes disponibles.
     * Cette méthode permet d'ajouter un nouvel artiste et de mettre à jour l'interface utilisateur.
     * Si l'artiste existe déjà, un message d'erreur est affiché.
     */
    @FXML
    private void AddArtistEvent(){
        if (!artistNameField.getText().isEmpty() && artistNameField.getText().trim()!="") {
            if (artistService.isExists(artistNameField.getText())) {
                AlertClass.messageError("L'artiste existe déjà !");
            }
            else {
                artist = new TArtist();
                artist.setArtistName(artistNameField.getText());
                artistService.saveArtistService(artist);
                List<TArtist> list = artistService.findAllArtistService();
                dataArtistTable.clear();
                for (TArtist artist : list) {
                    dataArtistTable.add(new ArtistModelSelection(artist, false));
                }
                artistTableView.refresh();
                artistNameField.setText("");
                //Si nous sommes sur la modification d'une track et non sur un ajout
                if (this.update) {
                    Set<TArtist> listArtist = this.trackToModify.getTrackArtistList();
                    if (listArtist != null) {
                        for (ArtistModelSelection artist : dataArtistTable) {
                            artist.setSelected(listArtist.contains(artist.getArtist()));
                        }
                    }
                }
            }
        }
    }

    /**
     * Ajoute une playlist à la liste des playlists disponibles.
     * Cette méthode permet d'ajouter une nouvelle playlist et de mettre à jour l'interface utilisateur.
     * Si la playlist existe déjà, un message d'erreur est affiché.
     */
    @FXML
    private void AddPlaylistEvent(){
        if (!playlistNameField.getText().isEmpty() && playlistNameField.getText().trim()!="") {
            if (playlistService.isExists(playlistNameField.getText())) {
                AlertClass.messageError("La playlist existe déjà !");
            }
            else {
                playlist = new TPlaylist();
                playlist.setPlaylistName(playlistNameField.getText());
                playlistService.savePlaylistService(playlist);
                List<TPlaylist> list = playlistService.findAllPlaylistService();
                dataPlaylistTable.clear();
                for (TPlaylist playlist : list) {
                    dataPlaylistTable.add(new PlaylistModelSelection(playlist, false));
                }
                playlistTableView.refresh();
                playlistNameField.setText("");

                //Si nous sommes sur la modification d'une track et non sur un ajout
                if (this.update) {
                    Set<TPlaylist> listPlaylist = this.trackToModify.getTrackPlaylistList();
                    if (listPlaylist != null) {
                        for (PlaylistModelSelection playlist : dataPlaylistTable) {
                            playlist.setSelected(listPlaylist.contains(playlist.getPlaylist()));
                        }
                    }
                }
            }
        }
    }

    /**
     * Ajoute un album à la liste des albums disponibles.
     * Cette méthode permet d'ajouter un nouvel album et de mettre à jour l'interface utilisateur.
     * Si l'album existe déjà, un message d'erreur est affiché.
     */
    @FXML
    private void AddAlbumEvent(){
        if (!albumNameField.getText().isEmpty() && albumNameField.getText().trim()!="") {
            if (albumService.isExists(albumNameField.getText())) {
                AlertClass.messageError("L'album existe déjà !");
            }
            else {
                album = new TAlbum();
                album.setAlbumName(albumNameField.getText());
                albumService.saveAlbumService(album);
                List<TAlbum> list = albumService.findAllAlbumService();
                dataAlbumTable.clear();
                for (TAlbum album : list) {
                    dataAlbumTable.add(new AlbumModelSelection(album, false));
                }
                albumTableView.refresh();
                albumNameField.setText("");

                //Si nous sommes sur la modification d'une track et non sur un ajout
                if (this.update) {
                    Set<TAlbum> listAlbum = this.trackToModify.getTrackAlbumList();
                    if (listAlbum != null) {
                        for (AlbumModelSelection album : dataAlbumTable) {
                            album.setSelected(listAlbum.contains(album.getAlbum()));
                        }
                    }
                }
            }
        }
    }

    /**
     * Ouvre une boîte de dialogue pour rechercher et sélectionner une image pour la pochette de la piste.
     * L'image sélectionnée est ensuite affichée dans l'interface utilisateur.
     */
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
            this.trackPathPictureField.setText(this.picture.getAbsolutePath());
            this.trackPathPicture=this.picture.toURI().toString();
            Image image = new Image(this.trackPathPicture);
            trackPicture.setImage(image);
        }
    }

    /**
     * Supprime la pochette de la piste et rétablit l'image par défaut.
     * Cette méthode est utilisée pour réinitialiser l'image de la pochette d'une piste.
     */
    @FXML
    private void TrashPictureEvent() {
        String path = getClass().getResource("/images/vide.jpg").toExternalForm();
        trackPathPictureField.setText("");
        trackPicture.setImage(new Image(getClass().getResource("/images/vide.jpg").toExternalForm()));
    }
}


