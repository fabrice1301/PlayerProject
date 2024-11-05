package eafc.peruwelz.PlayerProject.ctrl;


import eafc.peruwelz.PlayerProject.domain.*;
import eafc.peruwelz.PlayerProject.Class.Catalog;
import eafc.peruwelz.PlayerProject.service.*;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class CatalogController {
    @Autowired
    private ApplicationContext context;
    private TTrack track;
    private File file;
    private Image initialPictureTrack;
    private TTrack trackSelected;
    private MediaPlayer mediaPlayer;
    private AnimationTimer timer; // Pour mettre à jour le Slider
    private Boolean muteStatus=false;
    private Integer index=-1;
    private Double volume=0.5;
    private String playlistFilter=null;
    private String genreFilter=null;
    private String artistFilter=null;
    private String albumFilter=null;
    private String textFilter=null;

    //@Autowired
    private TrackService trackService;
    //@Autowired
    private GenreService genreService;
    //@Autowired
    private ArtistService artistService;
    //@Autowired
    private PlaylistService playlistService;
    //@Autowired
    private AlbumService albumService;
    //@Autowired
    private Catalog catalog;
    private enum Actions {PLAY, STOP};
    private Actions act;
    private Boolean randomState = false;
    private Boolean reloadState = false;

    @FXML
    private TextField searchField;

    @FXML
    private VBox playZone;

    @FXML
    private VBox listenedTrackList;

    @FXML
    private Label currentTime;

    @FXML
    private Label titleLabel;

    @FXML
    private Slider audioSlider;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Button previousBtn;

    @FXML
    private Button nextBtn;

    @FXML
    private Button searchTrackBtn;

    @FXML
    private Button randomBtn;

    @FXML
    private Button reloadBtn;

    @FXML
    private ImageView pictureTrack;

    @FXML
    private ImageView volumePicture;

    @FXML
    private ImageView picturePlayBtn;

    @FXML
    private Button deleteTrackBtn;

    @FXML
    private Button addTrackBtn;

    @FXML
    private Button modifyTrackBtn;

    @FXML
    private TextField FileNameField;

    @FXML
    private ComboBox playlistComboBox;

    @FXML
    private ComboBox genreComboBox;

    @FXML
    private ComboBox artistComboBox;

    @FXML
    private ComboBox albumComboBox;

    @FXML
    private TableView<TTrack> catalogTableView;

    @FXML
    private ObservableList<TTrack> dataCatalogTable;

    @FXML
    private ObservableList<String> dataPlaylist;

    @FXML
    private ObservableList<String> dataGenre;

    @FXML
    private ObservableList<String> dataAlbum;

    @FXML
    private ObservableList<String> dataArtist;

    @FXML
    private TableColumn<TTrack, String> titleColCatalogTableView;

    @FXML
    private TableColumn<TPlaylist, String> playlistColCatalogTableView;

    @FXML
    private TableColumn<TTrack, Integer> timeColCatalogTableView;

    @FXML
    private TableColumn<TTrack, String> genreColCatalogTableView;

    @FXML
    private TableColumn<TTrack, String> artistColCatalogTableView;

    @FXML
    private TableColumn<TTrack, String> albumColCatalogTableView;


    @Autowired
    public CatalogController(TrackService trackService, GenreService genreService, ArtistService artistService, PlaylistService playlistService, AlbumService albumService, Catalog catalog){
        this.trackService=trackService;
        this.genreService=genreService;
        this.artistService=artistService;
        this.playlistService=playlistService;
        this.albumService=albumService;
        this.catalog=catalog;
    }

    @FXML
    private void initialize(){
        //On récupère l'image de la pochette vide
        initialPictureTrack =new Image(pictureTrack.getImage().getUrl());
        setupCatalogTableView();
        setupFilter();
    }

    @FXML
    private void deleteTrackEvent(){
        this.track=catalogTableView.getSelectionModel().getSelectedItem();
        if (this.index==catalog.getIndex(this.track)){
            StopEvent();
            initPlayZone();
        }
        trackService.deleteTrackService(this.track);
        dataCatalogTable.remove(track);

        catalogTableView.getSelectionModel().select(-1);
    }

    @FXML
    private void addTrackEvent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddTrackView.fxml"));
        loader.setControllerFactory(context::getBean);
        Parent root = loader.load();
        AddTrackController controller = loader.getController();
        controller.setUpdate(false);
        Stage stage = new Stage();
        stage.setTitle("Ajouter une piste");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void ModifyTrackEvent() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddTrackView.fxml"));
        loader.setControllerFactory(context::getBean);
        Parent root = loader.load();
        AddTrackController controller = loader.getController();
        controller.setTrackToModify(catalogTableView.getSelectionModel().getSelectedItem(),true);
        Stage stage = new Stage();
        stage.setTitle("Ajouter une piste");
        stage.setScene(new Scene(root));
        stage.show();
        initPlayZone();

    }

    @FXML
    private void selectTrackTableView(){
        if (catalogTableView.getSelectionModel().getSelectedItem()!=null){
            //On récupère la piste sélectionnée dans le catalogue
            int newIndex=catalogTableView.getSelectionModel().getSelectedIndex();
            if (newIndex!=-1) this.index=newIndex;
            this.index=catalogTableView.getSelectionModel().getSelectedIndex();
            if (mediaPlayer!=null) mediaPlayer.stop();
            loadTrack(this.index);
            if (act==Actions.PLAY){
                mediaPlayer.play();
            }
        }

    }

    @FXML
    private void PlayEvent(){

        if (mediaPlayer != null) {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                this.act=Actions.STOP;
            }
            else{
                mediaPlayer.play();
                this.act=Actions.PLAY;
            }
            switchPictureBtnPlay();
        }
    }

    @FXML
    private void StopEvent(){
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.act=Actions.STOP;
            timer.stop(); // Arrêter le timer
            audioSlider.setValue(0); // Réinitialiser le Slider
            String imagePath = getClass().getResource("/images/play.png").toExternalForm();
            Image image = new Image(imagePath);
            picturePlayBtn.setImage(image);
            currentTime.setText("00:00 / " + formatDuration(mediaPlayer.getTotalDuration()));
        }
    }

    @FXML
    private void handleSliderChange() {
        if (mediaPlayer != null) {
            mediaPlayer.seek(Duration.seconds(audioSlider.getValue()));
        }
    }

    private String formatDuration(Duration duration) {
        long minutes = (long) duration.toMinutes();
        long seconds = (long) duration.toSeconds() % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @FXML
    private void VolumeSliderChange(){
        mediaPlayer.setVolume(volumeSlider.getValue());
        if (volumeSlider.getValue()!=0){
            String imagePath = getClass().getResource("/images/volume.png").toExternalForm();
            Image image = new Image(imagePath);
            volumePicture.setImage(image);
            muteStatus=false;
        }else{
            String imagePath = getClass().getResource("/images/mute.png").toExternalForm();
            Image image = new Image(imagePath);
            volumePicture.setImage(image);
            muteStatus=true;
        }
        volume=volumeSlider.getValue();
    }

    @FXML
    private void MuteVolumeChange(){
        if (!muteStatus) {
            volume = volumeSlider.getValue();
            mediaPlayer.setVolume(0);
            volumeSlider.setValue(0);
            String imagePath = getClass().getResource("/images/mute.png").toExternalForm();
            Image image = new Image(imagePath);
            volumePicture.setImage(image);
            muteStatus = true;
        }
        else if (muteStatus && volume>0){
            mediaPlayer.setVolume(volume);
            volumeSlider.setValue(volume);
            String imagePath = getClass().getResource("/images/volume.png").toExternalForm();
            Image image = new Image(imagePath);
            volumePicture.setImage(image);
            muteStatus=false;
        }
    }

    private void switchPictureBtnPlay(){
        if (mediaPlayer != null) {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                String imagePath = getClass().getResource("/images/play.png").toExternalForm();
                Image image = new Image(imagePath);
                picturePlayBtn.setImage(image);
            }
            else{
                String imagePath = getClass().getResource("/images/pause.png").toExternalForm();
                Image image = new Image(imagePath);
                picturePlayBtn.setImage(image);
            }
        }
    }

    @FXML
    private void PreviousEvent(){
        if (mediaPlayer!=null) mediaPlayer.stop();
        this.index-=1;
        loadTrack(this.index);
        if (act==Actions.PLAY){
            mediaPlayer.play();
        }
    }

    @FXML
    private void NextEvent(){
        if (mediaPlayer!=null) mediaPlayer.stop();
        if (!reloadState && !randomState) {
            if (this.index<catalogTableView.getItems().size()-1) this.index+=1;
            else {
                StopEvent();
            }
        }
        if (randomState){
            Boolean flag=true;
            int totalTrack=catalogTableView.getItems().size();
            Random random = new Random();
            while (flag){
                int newIndex=random.nextInt(totalTrack);
                if (this.index != newIndex){
                    this.index=newIndex;
                    flag=false;
                }
            }
        }
        loadTrack(this.index);
        if (act==Actions.PLAY){
            mediaPlayer.play();
        }
    }

    private void verifIndex(){
        int countTrack=catalogTableView.getItems().size();
        previousBtn.setDisable(false);
        nextBtn.setDisable(false);
        if (index==0){
            previousBtn.setDisable(true);
        }
        if (index==countTrack-1){
            nextBtn.setDisable(true);
        }
    }

    private void loadTrack(int index){
        catalogTableView.getSelectionModel().select(index);
        this.trackSelected=catalogTableView.getSelectionModel().getSelectedItem();
        this.index=catalogTableView.getSelectionModel().getSelectedIndex();
        verifIndex();
        if (this.trackSelected.getTrackPath() != null) {
            playZone.setDisable(false);
            titleLabel.setText(trackSelected.getTrackTitle());

            String audioFilePath = this.trackSelected.getTrackPath();
            Media media = new Media(new File(audioFilePath).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(() -> {
                audioSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
                currentTime.setText("00:00 / " + formatDuration(mediaPlayer.getTotalDuration()));
            });
            mediaPlayer.setOnEndOfMedia(() -> {
                NextEvent(); // On passe à la piste suivante à la fin de la lecture
            });

            // Initialiser le Slider
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                        audioSlider.setValue(mediaPlayer.getCurrentTime().toSeconds());
                        currentTime.setText(formatDuration(Duration.seconds(mediaPlayer.getCurrentTime().toSeconds())) + " / " + formatDuration(mediaPlayer.getTotalDuration()));
                    }
                }
            };
            timer.start();
        }

        //On vérifie s'il y a une pochette
        if (this.trackSelected.getTrackPicture() != null) {
            Image image = new Image(this.trackSelected.getTrackPicture());
            pictureTrack.setImage(image);
        }
        else{
            //Si pas de pochette on remet la pochette vide
            pictureTrack.setImage(initialPictureTrack);
        }
    }

    @FXML
    private void RandomEvent(){
        if (randomState) randomBtn.setStyle("");
        else randomBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        randomState=!randomState;

        reloadBtn.setStyle("");
        reloadState=false;
    }

    @FXML
    private void againEvent(){
        if (reloadState) reloadBtn.setStyle("");
        else reloadBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        reloadState=!reloadState;

        randomBtn.setStyle("");
        randomState=false;
    }


    private void reloadFilter(){
        if (genreComboBox.getSelectionModel().getSelectedIndex()==0) genreFilter=null;
        if (albumComboBox.getSelectionModel().getSelectedIndex()==0) albumFilter=null;
        if (playlistComboBox.getSelectionModel().getSelectedIndex()==0) playlistFilter=null;
        if (artistComboBox.getSelectionModel().getSelectedIndex()==0) artistFilter=null;
        List<TTrack> allTracks = trackService.findAllTrackService();
        List<TTrack> filteredTracks = allTracks.stream()
                .filter(track -> {
                    boolean matchesArtist = artistFilter == null || track.getTrackArtistsAsString().contains(artistFilter);
                    boolean matchesAlbum = albumFilter == null || track.getTrackAlbumsAsString().contains(albumFilter);
                    boolean matchesGenre = genreFilter == null || track.getTrackGenresAsString().contains(genreFilter);
                    boolean matchesPlaylist = playlistFilter == null || track.getTrackPlaylistsAsString().contains(playlistFilter);
                    boolean matchesSearchField = textFilter == null || track.getTrackTitle().toLowerCase().contains(textFilter.toLowerCase())  || track.getTrackArtistsAsString().toLowerCase().contains(textFilter.toLowerCase()) || track.getTrackAlbumsAsString().toLowerCase().contains(textFilter.toLowerCase());
                    return  matchesPlaylist && matchesGenre && matchesAlbum && matchesArtist && matchesSearchField;
                })
                .collect(Collectors.toList());
        dataCatalogTable = catalog.reloadCatalogTableView(filteredTracks);
        catalogTableView.setItems(dataCatalogTable);
    }

    private void initPlayZone(){
        playZone.setDisable(true);
        pictureTrack.setImage(initialPictureTrack);
        titleLabel.setText(null);
        currentTime.setText("00:00 / 00:00");
    }

    @FXML
    private void reloadPlaylistFilter() {
        dataPlaylist = FXCollections.observableArrayList(
                dataCatalogTable.stream()
                        .flatMap(track -> track.getTrackPlaylistList().stream()
                                .map(TPlaylist::getPlaylistName))
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList())
        );

        playlistComboBox.setItems(dataPlaylist);
        playlistComboBox.getItems().add(0, "Toutes les playlists");
        if (playlistFilter==null) playlistComboBox.getSelectionModel().select(0);
    }

    @FXML
    private void reloadGenreFilter() {
        dataGenre = FXCollections.observableArrayList(
                dataCatalogTable.stream()
                        .flatMap(track -> track.getTrackGenreList().stream()
                                .map(TGenre::getGenreName))
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList())
        );
        genreComboBox.setItems(dataGenre);
        genreComboBox.getItems().add(0, "Tous les genres");
        if (genreFilter==null) genreComboBox.getSelectionModel().select(0);

    }

    @FXML
    private void reloadArtistFilter() {
        dataArtist = FXCollections.observableArrayList(
                dataCatalogTable.stream()
                        .flatMap(track -> track.getTrackArtistList().stream()
                                .map(TArtist::getArtistName))
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList())
        );
        artistComboBox.setItems(dataArtist);
        artistComboBox.getItems().add(0, "Tous les artistes");
        if (artistFilter==null) artistComboBox.getSelectionModel().select(0);
    }

    @FXML
    private void reloadAlbumFilter() {
        dataAlbum = FXCollections.observableArrayList(
                dataCatalogTable.stream()
                        .flatMap(track -> track.getTrackAlbumList().stream()
                                .map(TAlbum::getAlbumName))
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList())
        );
        albumComboBox.setItems(dataAlbum);
        albumComboBox.getItems().add(0, "Tous les albums");
        if (albumFilter==null) albumComboBox.getSelectionModel().select(0);
    }

    private void setupCatalogTableView(){
        this.titleColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackTitle"));
        this.timeColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackTimeFormat"));
        this.genreColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackGenresAsString"));
        this.artistColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackArtistsAsString"));
        this.albumColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackAlbumsAsString"));
        this.playlistColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackPlaylistsAsString"));

        List<TTrack> trackList=this.trackService.findAllTrackService();
        dataCatalogTable=this.catalog.reloadCatalogTableView(trackList);
        catalogTableView.setItems(dataCatalogTable);

        catalogTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldIndex, newIndex) -> {
            if (newIndex==null){
                modifyTrackBtn.setDisable(true);
                deleteTrackBtn.setDisable(true);
                initPlayZone();
            }else{
                modifyTrackBtn.setDisable(false);
                deleteTrackBtn.setDisable(false);
            }
        });
    }

    private void setupFilter(){
        reloadPlaylistFilter();
        reloadAlbumFilter();
        reloadArtistFilter();
        reloadGenreFilter();

        genreComboBox.getSelectionModel().select(0);
        albumComboBox.getSelectionModel().select(0);
        artistComboBox.getSelectionModel().select(0);
        playlistComboBox.getSelectionModel().select(0);

        // Ajout d'un listener pour la ComboBox des genres
        genreComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.toString().equals(genreFilter)) {
                genreFilter=newValue.toString();
                reloadFilter();
            }
        });

        // Ajout d'un listener pour la ComboBox des playlists
        playlistComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.toString().equals(playlistFilter) ) {
                playlistFilter=newValue.toString();
                reloadFilter();
            }
        });

        // Ajout d'un listener pour la ComboBox des artistes
        artistComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.toString().equals(artistFilter)) {
                artistFilter=newValue.toString();
                reloadFilter(); // Appel de la méthode de filtrage
            }
        });

        // Ajout d'un listener pour la ComboBox des albums
        albumComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.toString().equals(albumFilter)) {
                albumFilter=newValue.toString();
                reloadFilter(); // Appel de la méthode de filtrage
            }
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            textFilter = newValue;
            reloadFilter();
        });
    }

}
