package eafc.peruwelz.PlayerProject.ctrl;

import eafc.peruwelz.PlayerProject.domain.TArtist;
import eafc.peruwelz.PlayerProject.domain.TGenre;
import eafc.peruwelz.PlayerProject.domain.TTrack;

import eafc.peruwelz.PlayerProject.Class.Catalog;
import eafc.peruwelz.PlayerProject.modelControl.ArtistModelSelection;
import eafc.peruwelz.PlayerProject.modelControl.GenreModelSelection;
import eafc.peruwelz.PlayerProject.service.ArtistService;
import eafc.peruwelz.PlayerProject.service.GenreService;
import eafc.peruwelz.PlayerProject.service.TrackService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class AddTrackController {

    private TTrack track;
    private TGenre genre;
    private TArtist artist;
    private TrackService trackService;
    private GenreService genreService;
    private ArtistService artistService;
    private Catalog catalog;
    private File file;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button searchTrackBtn;

    @FXML
    private Button searchPictureTrackBtn;

    @FXML
    private TextField trackNameField;

    @FXML
    private TextField genreNameField;

    @FXML
    private TextField artistNameField;

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
    private TableColumn<ArtistModelSelection, Boolean> selectedArtistCol;

    @FXML
    private TableColumn<ArtistModelSelection, String> artistNameCol;

    @Autowired
    public AddTrackController(TrackService trackService, Catalog catalog, GenreService genreService, ArtistService artistService){
        this.trackService=trackService;
        this.genreService=genreService;
        this.artistService=artistService;
        this.catalog = catalog;
    }

    @FXML
    private void initialize(){
        //Ajoute de la loupe dans les boutons de recherche
        searchTrackBtn.setText("\uD83D\uDD0D " + searchTrackBtn.getText());
        searchPictureTrackBtn.setText("\uD83D\uDD0D " + searchPictureTrackBtn.getText());

        //Gestion des genres
        this.selectedGenreCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
        this.selectedGenreCol.setCellFactory(tc -> new CheckBoxTableCell<>());
        this.genreNameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getGenre().getGenreName()));
        List<TGenre> listGenre = genreService.findAllGenreService();
        dataGenreTable = FXCollections.observableArrayList();
        for (TGenre genre : listGenre) {
            dataGenreTable.add(new GenreModelSelection(genre, false));
        }
        genreTableView.setItems(dataGenreTable);

        //Gestion des artistes
        this.selectedArtistCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
        this.selectedArtistCol.setCellFactory(tc -> new CheckBoxTableCell<>());
        this.artistNameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getArtist().getArtistName()));
        List<TArtist> listArtist = artistService.findAllArtistService();
        dataArtistTable = FXCollections.observableArrayList();
        for (TArtist artist : listArtist) {
            dataArtistTable.add(new ArtistModelSelection(artist, false));
        }
        artistTableView.setItems(dataArtistTable);

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
            trackNameField.setText(this.file.getName());
        }
    }

    @FXML
    private void saveTrackEvent() throws UnsupportedAudioFileException, IOException {
        track=new TTrack();

        //On récupère le nom du fichier sans l'extension
        this.track.setTrackTitle(extensionLess(this.file.getName()));

        //On récupère le chemin du fichier
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
        //On récupère les genres sélectionnés
        Set<TGenre> genreSelected = new HashSet<>();
        dataGenreTable.forEach(hsl -> {if (hsl.isSelected()) genreSelected.add(hsl.getGenre());});
        track.setTrackGenreList(genreSelected);

        //On récupère les artistes sélectionnés
        Set<TArtist> artistSelected = new HashSet<>();
        dataArtistTable.forEach(hsl -> {if (hsl.isSelected()) artistSelected.add(hsl.getArtist());});
        track.setTrackArtistList(artistSelected);

        //On sauve la piste avec ses propriétés
        this.trackService.saveTrackService(this.track);
        this.catalog.addTrack(this.track);
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
        genre=new TGenre();
        genre.setGenreName(genreNameField.getText());
        genre.setGenreDeleted(false);
        genreService.saveGenreService(genre);
        dataGenreTable.add(new GenreModelSelection(genre, false));
    }

    @FXML
    private void AddArtistEvent(){
        artist=new TArtist();
        artist.setArtistName(artistNameField.getText());
        artist.setArtistDeleted(false);
        artistService.saveArtistService(artist);
        dataArtistTable.add(new ArtistModelSelection(artist, false));
    }

    @FXML
    private void AddPlaylistEvent(){

    }

    @FXML
    private void AddAlbumEvent(){

    }
}
