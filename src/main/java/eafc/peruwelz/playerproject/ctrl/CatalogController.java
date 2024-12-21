package eafc.peruwelz.playerproject.ctrl;

import eafc.peruwelz.playerproject.Class.*;
import eafc.peruwelz.playerproject.command.*;
import eafc.peruwelz.playerproject.domain.*;
import eafc.peruwelz.playerproject.player.Player;
import eafc.peruwelz.playerproject.service.*;
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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.*;


@Controller
public class CatalogController {

    @Autowired
    private ApplicationContext context;

    private final TrackService trackService;
    private final GenreService genreService;
    private final ArtistService artistService;
    private final PlaylistService playlistService;
    private final AlbumService albumService;
    private final Catalog catalog;
    private Player player;
    private TTrack TrackLoaded;
    private Image initialPictureTrack;
    private AnimationTimer timer; // Pour mettre à jour le Slider
    private Boolean muteStatus = false;
    private Integer index = -1;
    private Double volume = 0.5;
    private enum STATUS {PLAYING, STOPPED, PAUSED, UNKNOW}
    private StatusPlayer status;
    private Boolean randomState = false;
    private Boolean reloadState = false;
    private String style1 = "-fx-background-color: #c5fca9; -fx-text-fill: black;";
    private Filter filter;
    private LoadedTrack loadedTrack;
    @FXML
    private ContextMenu CatalogContextMenu;
    @FXML
    private ContextMenu WaitingContextMenu;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox addPlaylistComboBox;
    @FXML
    private HBox playZone;
    @FXML
    private VBox listenZone;
    @FXML
    private Label currentTime;
    @FXML
    private Label titleLabel;
    @FXML
    private Slider seekSlider;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Button previousBtn;
    @FXML
    private Button nextBtn;
    @FXML
    private Button randomBtn;
    @FXML
    private Button reloadBtn;
    @FXML
    private Button addPlaylistBtn;
    @FXML
    private ImageView pictureTrack;
    @FXML
    private ImageView volumePicture;
    @FXML
    private ImageView picturePlayBtn;
    @FXML
    private ComboBox playlistComboBox;
    @FXML
    private ComboBox genreComboBox;
    @FXML
    private ComboBox artistComboBox;
    @FXML
    private ComboBox albumComboBox;

    @FXML
    private ListView<TTrack> waitingTrackListView;
    @FXML
    private TableView<TTrack> catalogTableView;
    @FXML
    private ObservableList<String> dataPlaylist;
    @FXML
    private ObservableList<String> dataGenre;
    @FXML
    private ObservableList<String> dataAlbum;
    @FXML
    private ObservableList<String> dataArtist;
    @FXML
    private ObservableList<TTrack> dataWaitingTrackList;
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
    @FXML
    private MenuItem deleteWaitingTrackMenu;
    @FXML
    private MenuItem AddListenedTrackListMenu;

    @FXML
    private MenuItem upWaitingMenu;
    @FXML
    private MenuItem downWaitingMenu;
    @FXML
    private MenuItem deleteTrackMenu;
    @FXML
    private MenuItem modifyTrackMenu;
    @FXML
    private MenuItem emptyWaitingMenu;

    private RemoteControl remote;
    private PlayCommand playCommand;
    private PauseCommand pauseCommand;
    private StopCommand stopCommand;
    private GetStatusCommand getStatusCommand;
    private SetStatusCommand setStatusCommand;
    private SetVolumeCommand setVolumeCommand;
    private GetCurrentTimeCommand getCurrentTimeCommand;
    private GetTotalDurationCommand getTotalDurationCommand;
    private SeekCommand seekCommand;
    private GetVolumeCommand getVolumeCommand;
    //private SetOnReadyCommand setOnReadyCommand;
    private GetPlayerCommand getPlayerCommand;

    @Autowired
    public CatalogController(TrackService trackService,GenreService genreService, ArtistService artistService, PlaylistService playlistService, AlbumService albumService,Catalog catalog) {
        this.trackService = trackService;
        this.genreService = genreService;
        this.artistService = artistService;
        this.playlistService = playlistService;
        this.albumService = albumService;
        this.catalog = catalog;


    }


    @FXML
    private void initialize() {
        List<TTrack> trackList=trackService.findAllTrackService();
        this.remote=new RemoteControl();
        this.playCommand=new PlayCommand(this.player);
        this.pauseCommand=new PauseCommand(this.player);
        this.stopCommand=new StopCommand(this.player);
        this.getStatusCommand=new GetStatusCommand(this.player);
        this.getCurrentTimeCommand =new GetCurrentTimeCommand(this.player);
        this.getTotalDurationCommand =new GetTotalDurationCommand(this.player);
        this.getVolumeCommand=new GetVolumeCommand(this.player);
        this.getPlayerCommand=new GetPlayerCommand(this.player);
        //this.status=new StatusPlayer();

        //On récupère l'image de la pochette vide
        initialPictureTrack = new Image(pictureTrack.getImage().getUrl());
        setupCatalogTableView();
        this.catalog.initTrackWaiting();
        dataWaitingTrackList = FXCollections.observableArrayList(trackService.findByWaitingService(true));
        waitingTrackListView.setItems(dataWaitingTrackList);
        updateListViewStyle();
        playZone.setStyle("-fx-background-color: #e2e2e2;");
        filter=new Filter(playlistComboBox, genreComboBox, artistComboBox, albumComboBox,searchField,trackService,this,this.catalog);
        filter.setup();
    }

    public void setPlayer(Player player){
        this.player=player;
        //this.status=new StatusPlayer();
    }



    @FXML
    private void deleteTrackEvent() {
        String item="cet enregistrement";
        int countTrack = catalogTableView.getSelectionModel().getSelectedItems().size();
        if (countTrack>1) item="ces enregistrements";
        ObservableList<TTrack> listTrackToDelete = catalogTableView.getSelectionModel().getSelectedItems();
        if (AlertClass.showConfirmationDeleteDialog(item)) {
            for (TTrack track : listTrackToDelete) {
                if (track.equals(TrackLoaded)) {
                    StopEvent();
                    initPlayZone();
                }
                if (track.getTrackWaiting()) {
                    deleteTrackWaiting(track);
                }
                trackService.deleteTrackService(track);
                this.catalog.removeTrack(track);
                catalogTableView.getSelectionModel().select(-1);
            }
        }
    }


    @FXML
    private void addTrackEvent() throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddTrackView.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();
            TrackController controller = loader.getController();
            controller.setUpdate(false);
            Stage stage = new Stage();
            stage.setTitle("Ajouter une piste");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void ModifyTrackEvent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddTrackView.fxml"));
        loader.setControllerFactory(context::getBean);
        Parent root = loader.load();
        TrackController controller = loader.getController();
        controller.setTrackToModify(catalogTableView.getSelectionModel().getSelectedItem(), true);
        Stage stage = new Stage();
        stage.setTitle("Ajouter une piste");
        stage.setScene(new Scene(root));
        stage.show();
        initPlayZone();
    }

    private void selectTrackList() {
        int index = catalogTableView.getSelectionModel().getSelectedIndex();
        int countSelected = catalogTableView.getSelectionModel().getSelectedItems().size();
        TTrack track;
        AddListenedTrackListMenu.setDisable(false);
        modifyTrackMenu.setDisable(false);
        if (index == -1) {
            AddListenedTrackListMenu.setDisable(true);
            deleteTrackMenu.setDisable(true);
            modifyTrackMenu.setDisable(true);
        } else {
            track = this.catalog.getTrackByIndex(index);
            AddListenedTrackListMenu.setDisable(track.getTrackWaiting());
            deleteTrackMenu.setDisable(false);
            modifyTrackMenu.setDisable(false);
        }

        if (countSelected > 1) {
            boolean flag = false;
            for (TTrack t : catalogTableView.getSelectionModel().getSelectedItems()) {
                if (!t.getTrackWaiting()) {
                    flag = true;
                    break;
                }
            }
            AddListenedTrackListMenu.setDisable(!flag);
            modifyTrackMenu.setDisable(true);
        }
    }

    @FXML
    private void PlayEvent() {
        if (this.remote.setCommand(getPlayerCommand).execute() != null) {
            if (Objects.equals(this.remote.setCommand(getStatusCommand).execute(), STATUS.PLAYING.toString())) {
                this.remote.setCommand(pauseCommand).execute();
                this.remote.setCommand(new SetStatusCommand(this.player,STATUS.PAUSED.toString())).execute();
                timer.stop();
            } else {
                this.remote.setCommand(playCommand).execute();
                this.remote.setCommand(new SetStatusCommand(this.player,STATUS.PLAYING.toString())).execute();
                timer.start();
            }
            switchPictureBtnPlay();
        }
    }

    @FXML
    private void StopEvent() {
        if (this.remote.setCommand(getPlayerCommand).execute() != null) {
            timer.stop();
            this.remote.setCommand(stopCommand).execute();
            this.remote.setCommand(new SetStatusCommand(this.player,STATUS.STOPPED.toString())).execute();
            timer.stop(); // Arrêter le timer
            seekSlider.setValue(0); // Réinitialiser le Slider
            String imagePath = getClass().getResource("/images/play.png").toExternalForm();
            Image image = new Image(imagePath);
            picturePlayBtn.setImage(image);
            currentTime.setText("00:00 / " + formatDuration((Double) this.remote.setCommand(getTotalDurationCommand).execute()));
        }
    }

    @FXML
    private void handleSliderChange() {
        if (this.remote.setCommand(getPlayerCommand).execute() != null) {
            remote.setCommand(new SeekCommand(this.player,Duration.seconds(seekSlider.getValue()))).execute();
        }
    }

    private String formatDuration(double duration) {
        int minutes = (int) duration / 60;
        int seconds = (int) duration % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @FXML
    private void VolumeSliderChange() {
        this.remote.setCommand(new SetVolumeCommand(this.player,volumeSlider.getValue())).execute();
        if (volumeSlider.getValue() != 0) {
            String imagePath = getClass().getResource("/images/volume.png").toExternalForm();
            Image image = new Image(imagePath);
            volumePicture.setImage(image);
            muteStatus = false;
        } else {
            String imagePath = getClass().getResource("/images/mute.png").toExternalForm();
            Image image = new Image(imagePath);
            volumePicture.setImage(image);
            muteStatus = true;
        }
        volume = volumeSlider.getValue();
    }

    @FXML
    private void MuteVolumeChange() {
        if (this.remote.setCommand(getPlayerCommand).execute() != null) {
            if (!muteStatus) {
                volume = volumeSlider.getValue();
                this.remote.setCommand(new SetVolumeCommand(this.player,0)).execute();
                volumeSlider.setValue(0);
                String imagePath = getClass().getResource("/images/mute.png").toExternalForm();
                Image image = new Image(imagePath);
                volumePicture.setImage(image);
                muteStatus = true;
            } else if (volume > 0) {
                this.remote.setCommand(new SetVolumeCommand(this.player,volume)).execute();
                volumeSlider.setValue(volume);
                String imagePath = getClass().getResource("/images/volume.png").toExternalForm();
                Image image = new Image(imagePath);
                volumePicture.setImage(image);
                muteStatus = false;
            }
        }
    }

    private void switchPictureBtnPlay() {
        if (this.remote.setCommand(getPlayerCommand).execute() != null) {
            if (this.remote.setCommand(getStatusCommand).execute().equals(STATUS.PAUSED.toString())) {
                String imagePath = getClass().getResource("/images/play.png").toExternalForm();
                Image image = new Image(imagePath);
                picturePlayBtn.setImage(image);
            } else {
                String imagePath = getClass().getResource("/images/pause.png").toExternalForm();
                Image image = new Image(imagePath);
                picturePlayBtn.setImage(image);
            }
        }
    }

    @FXML
    private void PreviousEvent() {
        TTrack newTrack;
        String previousStatus;
        if (this.remote.setCommand(getPlayerCommand).execute() != null) {
            previousStatus= this.remote.setCommand(getStatusCommand).execute().toString();
            if ((Double) this.remote.setCommand(getCurrentTimeCommand).execute() < 3 && this.index>0) this.index -= 1;
            this.remote.setCommand(stopCommand).execute();
            newTrack = dataWaitingTrackList.get(this.index);
            loadTrack(newTrack);
            if (Objects.equals(previousStatus, STATUS.PLAYING.toString())) {
                this.remote.setCommand(playCommand).execute();
            }
        }
    }

    @FXML
    private void NextEvent() {
        NextEvent(true);
    }

    @FXML
    private void NextEvent(Boolean btn) {

        TTrack newTrack;
        int index=this.index;
        int countTrack = waitingTrackListView.getItems().size();
        String previousStatus= this.remote.setCommand(getStatusCommand).execute().toString();

        if (!btn){
            TrackLoaded.setTrackCount(TrackLoaded.getTrackCount()+1);
            trackService.saveTrackService(TrackLoaded);
        }
        if (countTrack > 1) {
            int oldindex=index;
            if (this.remote.setCommand(getPlayerCommand).execute() != null) {
                this.remote.setCommand(stopCommand).execute();
                initPlayZone();
            }
            if (btn && !reloadState && !randomState) index += 1;
            if (!reloadState && !btn) {
                if (index<countTrack-1) index++;
                else index=0;
            }
            if (randomState) {
                Boolean flag = true;
                int totalTrack = waitingTrackListView.getItems().size();
                Random random = new Random();
                while (flag && totalTrack > 1) {
                    int newIndex = random.nextInt(totalTrack);
                    if (oldindex != newIndex) {
                        index = newIndex;
                        flag = false;
                    }
                }
            }
        }
        newTrack = dataWaitingTrackList.get(index);
        loadTrack(newTrack);

        if (Objects.equals(previousStatus, STATUS.PLAYING.toString()) || !btn) {
            this.remote.setCommand(playCommand).execute();
        }
        updateListViewStyle();
    }

    private void verifIndex() {
        int countTrack = waitingTrackListView.getItems().size();
        previousBtn.setDisable(false);
        nextBtn.setDisable(false);

        if (this.index == countTrack - 1 && !randomState) {
            nextBtn.setDisable(true);
        }
        if (countTrack - 1 == 0) {
            previousBtn.setDisable(true);
            nextBtn.setDisable(true);
        }
    }

    @FXML
    private void randomEvent() {
        if (randomState) randomBtn.setStyle("");
        else randomBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        randomState = !randomState;
        reloadBtn.setStyle("");
        reloadState = false;
        verifIndex();
    }

    @FXML
    private void reloadEvent() {
        if (reloadState) reloadBtn.setStyle("");
        else reloadBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        reloadState = !reloadState;
        randomBtn.setStyle("");
        randomState = false;
    }


    private void initPlayZone() {
        playZone.setDisable(true);
        pictureTrack.setImage(initialPictureTrack);
        titleLabel.setText(null);
        currentTime.setText("00:00 / 00:00");
        this.index=-1;
    }


    private void setupCatalogTableView() {
        this.titleColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackTitle"));
        this.timeColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackTimeFormat"));
        this.genreColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackGenresAsString"));
        this.artistColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackArtistsAsString"));
        this.albumColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackAlbumsAsString"));
        this.playlistColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackPlaylistsAsString"));

        catalogTableView.setItems(this.catalog.getDataCatalogTable());
        catalogTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        catalogTableView.setContextMenu(null);
        // Configuration du RowFactory pour la TableView
        catalogTableView.setRowFactory(tv -> {
            TableRow<TTrack> row = new TableRow<>();
            // Gérer l'affichage du menu contextuel manuellement
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    // Afficher le menu contextuel si la ligne n'est pas vide
                    if (event.getButton() == MouseButton.SECONDARY) {
                        selectTrackList();
                        CatalogContextMenu.show(row, event.getScreenX(), event.getScreenY());
                    }
                    else {
                        if (event.getClickCount() == 1) { // Vérifie si c'est un clic simple (un seul clic)
                            selectTrackList(); // Appelle la méthode selectWaitingTrackList
                        } else if (event.getClickCount() == 2) {
                            AddTrackWaitingListEvent();
                        }
                    }
                } else {
                    // Masquer le menu contextuel si la ligne est vide
                    CatalogContextMenu.hide();
                    catalogTableView.getSelectionModel().clearSelection();
                }
            });
            return row;
        });
    }

    @FXML
    private void reloadPlaylistFilter() {
        playlistComboBox=filter.reloadPlaylist();
    }

    @FXML
    private void reloadGenreFilter() {
        genreComboBox=filter.reloadGenre();
    }

    @FXML
    private void reloadArtistFilter() {
        artistComboBox=filter.reloadArtist();
    }

    @FXML
    private void reloadAlbumFilter() {
        albumComboBox=filter.reloadAlbum();
    }


    @FXML
    private void AddTrackWaitingListEvent() {

        ObservableList<TTrack> list = catalogTableView.getSelectionModel().getSelectedItems();
        for (TTrack track : list) {
            if (!track.getTrackWaiting()) {
                dataWaitingTrackList.add(track);
                track.setTrackWaiting(true);
                trackService.saveTrackService(track);
                addPlaylistComboBox.setDisable(false);
                addPlaylistBtn.setDisable(false);
            }
        }
        nextBtn.setDisable(false);

    }

    @FXML
    private void selectWaitingTrackList() {
        int index = waitingTrackListView.getSelectionModel().getSelectedIndex();
        int indexEnd=waitingTrackListView.getItems().size()-1;
        TTrack track = waitingTrackListView.getSelectionModel().getSelectedItem();
        upWaitingMenu.setDisable(false);
        deleteWaitingTrackMenu.setDisable(false);
        downWaitingMenu.setDisable(false);

        if (track != null && index != -1) {
            updateListViewStyle();
            if (this.remote.setCommand(getPlayerCommand).execute() != null && this.remote.setCommand(getStatusCommand).execute() == STATUS.PLAYING && track.equals(this.TrackLoaded)) {
                deleteWaitingTrackMenu.setDisable(true);
            }
        }
        if (index==0) upWaitingMenu.setDisable(true);
        if (index==indexEnd) downWaitingMenu.setDisable(true);
    }

    @FXML
    private void loadWaitingTrackList() {
        TTrack newTrack;
        int newIndex = waitingTrackListView.getSelectionModel().getSelectedIndex();
        if (waitingTrackListView.getSelectionModel().getSelectedItem() != null && (newIndex != this.index || this.remote.setCommand(getStatusCommand).execute() != STATUS.PLAYING)) {
            //On récupère la piste sélectionnée dans le catalogue
            if (newIndex != -1) this.index = newIndex;
            newTrack = dataWaitingTrackList.get(this.index);
            if(this.remote.setCommand(getStatusCommand).execute()!=STATUS.STOPPED.toString() && this.remote.setCommand(getStatusCommand).execute()!=STATUS.UNKNOW.toString())
            {
                this.remote.setCommand(stopCommand).execute();
                this.remote.setCommand(new SetStatusCommand(this.player,STATUS.STOPPED.toString())).execute();
            }
            loadTrack(newTrack);
            PlayEvent();
        }
    }

    private void loadTrack(TTrack newTrack) {
        waitingTrackListView.getSelectionModel().select(newTrack);
        this.TrackLoaded = waitingTrackListView.getSelectionModel().getSelectedItem();
        this.index = waitingTrackListView.getSelectionModel().getSelectedIndex();
        verifIndex();
        updateListViewStyle();
        if (this.TrackLoaded.getTrackPath() != null) {
            playZone.setDisable(false);
            titleLabel.setText(TrackLoaded.getTrackTitle());

            String audioFilePath = this.TrackLoaded.getTrackPath();
            this.remote.setCommand(new LoadTrackCommand(this.player,audioFilePath)).execute();
            remote.setCommand(new SetVolumeCommand(player,0.5)).execute();
            remote.setCommand(new SetOnReadyCommand(this.player,() -> {
                seekSlider.setMax(player.getTotalDuration().toSeconds());
                currentTime.setText("00:00 / " + formatDuration((Double) remote.setCommand(getTotalDurationCommand).execute()));
            })).execute();

            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    seekSlider.setValue((Double) remote.setCommand(getCurrentTimeCommand).execute());
                    currentTime.setText(formatDuration((Double) remote.setCommand(getCurrentTimeCommand).execute()) + " / " + formatDuration((Double) remote.setCommand(getTotalDurationCommand).execute()));
                }
            };

            remote.setCommand(new SetOnEndOfMediaCommand(this.player,() -> {
                NextEvent(false);
            })).execute();

            //On vérifie s'il y a une pochette
            if (this.TrackLoaded.getTrackPicture() != null) {
                Image image = new Image(this.TrackLoaded.getTrackPicture());
                pictureTrack.setImage(image);
            } else {
                //Si pas de pochette on remet la pochette vide
                pictureTrack.setImage(initialPictureTrack);
            }
        }
    }

    @FXML
    private void deleteWaitingTrackEvent() {
        TTrack track = waitingTrackListView.getSelectionModel().getSelectedItem();
        deleteTrackWaiting(track);
    }

    private void deleteTrackWaiting(TTrack track) {
        int countTrack;
        this.catalog.activeTrack(track);
        dataWaitingTrackList.remove(track);
        track.setTrackWaiting(false);
        trackService.saveTrackService(track);
        countTrack = waitingTrackListView.getItems().size();

        if (countTrack == 1 || this.index == countTrack - 1) nextBtn.setDisable(true);
        if (track.equals(TrackLoaded)) {
            this.remote.setCommand(stopCommand).execute();
            updateListViewStyle();
            this.index-=1;
            NextEvent(false);
        }
        else updateListViewStyle();
        if (countTrack==0){
            addPlaylistComboBox.setDisable(true);
            addPlaylistBtn.setDisable(true);
        }
    }

    @FXML
    private void upWaitingEvent() {
        int indexDown;
        int indexUp = waitingTrackListView.getSelectionModel().getSelectedIndex();
        int countTrack = waitingTrackListView.getItems().size();
        int indexTrackLoaded = dataWaitingTrackList.indexOf(TrackLoaded);
        nextBtn.setDisable(false);
        previousBtn.setDisable(false);
        if (indexUp > 0) {
            indexDown = indexUp - 1;
            swap(dataWaitingTrackList, indexDown, indexUp);
            nextBtn.setDisable(false);
        }
        if (indexTrackLoaded == countTrack - 1) nextBtn.setDisable(true);
        if (indexTrackLoaded == 0) previousBtn.setDisable(true);
        this.index = dataWaitingTrackList.indexOf(TrackLoaded);
        updateListViewStyle();
    }

    @FXML
    private void downWaitingEvent() {
        int indexUp = waitingTrackListView.getSelectionModel().getSelectedIndex();
        int indexDown = indexUp + 1;
        int countTrack = waitingTrackListView.getItems().size();
        int indexTrackLoaded = dataWaitingTrackList.indexOf(TrackLoaded);
        nextBtn.setDisable(false);
        previousBtn.setDisable(false);

        if (indexUp < countTrack - 1) {
            swap(dataWaitingTrackList, indexDown, indexUp);
            previousBtn.setDisable(false);
        }
        if (indexTrackLoaded == countTrack - 1) nextBtn.setDisable(true);
        if (indexTrackLoaded == 0) previousBtn.setDisable(true);
        this.index = dataWaitingTrackList.indexOf(TrackLoaded);
        updateListViewStyle();
    }

    public void swap(ObservableList<TTrack> list, int index1, int index2) {
        TTrack temp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, temp);
    }

    private void updateListViewStyle() {
        waitingTrackListView.setContextMenu(null);
        waitingTrackListView.setCellFactory(param -> {
            ListCell<TTrack> cell = new ListCell<TTrack>() {
                @Override
                protected void updateItem(TTrack track, boolean empty) {
                    super.updateItem(track, empty);
                    if (empty || track == null) {
                        setText(null);
                        setStyle(null);
                    } else {
                        setText(track.getTrackTitle());
                        if (track.equals(TrackLoaded)) {
                            setStyle(style1);
                            System.out.println(TrackLoaded);
                        }
                    }
                }
            };
            // Gérer l'affichage du menu contextuel manuellement
            cell.setOnMouseClicked(event -> {

                if (!cell.isEmpty()) {
                    // Afficher le menu contextuel si la cellule n'est pas vide
                    if (event.getButton() == MouseButton.SECONDARY) {
                        WaitingContextMenu.show(cell, event.getScreenX(), event.getScreenY());
                    }
                    else {
                        if (event.getClickCount() == 1 && event.getButton() == MouseButton.PRIMARY) { // Vérifie si c'est un clic simple (un seul clic)
                            selectWaitingTrackList(); // Appelle la méthode selectWaitingTrackList
                        } else if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) { // Vérifie si c'est un double-clic
                            updateListViewStyle();
                            loadWaitingTrackList(); // Appelle la méthode loadWaitingTrackList pour un double-clic
                        }
                    }
                } else {
                    // Masquer le menu contextuel si la cellule est vide
                    WaitingContextMenu.hide();
                    waitingTrackListView.getSelectionModel().clearSelection();
                }

            });
            return cell;
        });

    }

    @FXML
    private void emptyWaitingEvent() {

        for (TTrack track : dataWaitingTrackList) {
            if (this.remote.setCommand(getPlayerCommand).execute() != null && Objects.equals(this.remote.setCommand(getStatusCommand).execute(), STATUS.PLAYING.toString())) {
                StopEvent();
            }
            this.catalog.activeTrack(track);
            track.setTrackWaiting(false);
            //trackService.saveTrackService(track);
        }
        dataWaitingTrackList.clear();
        initPlayZone();
        TrackLoaded = null;
        addPlaylistComboBox.setDisable(true);
        addPlaylistBtn.setDisable(true);
    }

    public void addCatalogWaitingListEvent(){
        for (TTrack track : catalogTableView.getItems()) {
            if (!track.getTrackWaiting()) {
                dataWaitingTrackList.add(track);
                track.setTrackWaiting(true);
                addPlaylistComboBox.setDisable(false);
                addPlaylistBtn.setDisable(false);
            }
        }
        nextBtn.setDisable(false);
    }


    @FXML
    private void addPlaylistEvent(){
        TPlaylist newPlaylist=this.playlistService.findByPlaylistIdService(addPlaylistComboBox.getValue().toString());
        if(newPlaylist==null) {
            newPlaylist=new TPlaylist();
            newPlaylist.setPlaylistName(addPlaylistComboBox.getValue().toString());
            playlistService.savePlaylistService(newPlaylist);
        }

        for(TTrack track:dataWaitingTrackList){
            Set<TPlaylist> trackPlaylist = new HashSet<>();
            trackPlaylist=track.getTrackPlaylistList();
            if (!trackPlaylist.contains(newPlaylist)){
                trackPlaylist.add(newPlaylist);
                track.setTrackPlaylistList(trackPlaylist);
                trackService.saveTrackService(track);
                this.catalog.modifyTrack(this.catalog.getIndex(track),track);
            }
        }
        refreshTableView();
    }

    public void refreshTableView(){
        catalogTableView.refresh();
        catalogTableView.setItems(this.catalog.getDataCatalogTable());

    }

    public void TableRefresh(){
        catalogTableView.refresh();
    }

    public void SetupPlaylistComboBoxEvent(){
        addPlaylistComboBox.getItems().clear();
        List<TPlaylist> listPlaylists=playlistService.findAllPlaylistService();
        for(TPlaylist p:listPlaylists){
            addPlaylistComboBox.getItems().add(p.getPlaylistName());
        }

    }

}
