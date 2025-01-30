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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Contrôleur principal pour la gestion du lecteur multimédia et du catalogue de musique.
 * Ce contrôleur permet de gérer les fonctionnalités du lecteur telles que la lecture, la gestion des pistes audio,
 * l'ajout de playlists, la gestion du volume, et l'affichage des informations du catalogue.
 */
@Controller
public class CatalogController {
    // Services injectés pour la gestion des différentes entités
    /**
     * Contexte de l'application pour obtenir des beans Spring.
     */
    @Autowired
    private ApplicationContext context;

    /**
     * Service pour la gestion des pistes musicales.
     */
    private final TrackService trackService;

    /**
     * Service pour la gestion des genres musicaux.
     */
    private final GenreService genreService;

    /**
     * Service pour la gestion des artistes.
     */
    private final ArtistService artistService;

    /**
     * Service pour la gestion des playlists.
     */
    private final PlaylistService playlistService;

    /**
     * Service pour la gestion des albums.
     */
    private final AlbumService albumService;

    /**
     * Le catalogue de musique qui contient les pistes et les informations associées.
     */
    private final Catalog catalog;

    /**
     * Le lecteur de musique utilisé pour lire les pistes.
     */
    private Player player;

    /**
     * Piste actuellement chargée dans le lecteur.
     */
    private TTrack TrackLoaded;

    /**
     * Image représentant la pochette de la piste au moment du chargement initial.
     */
    private Image initialPictureTrack;

    /**
     * Timer utilisé pour mettre à jour le Slider de progression pendant la lecture.
     */
    private AnimationTimer timer;

    /**
     * Statut de mise en sourdine du lecteur.
     * Si `true`, le son est coupé.
     */
    private Boolean muteStatus = false;

    /**
     * Index de la piste actuellement lue.
     * Utilisé pour naviguer entre les pistes.
     */
    private Integer index = -1;

    /**
     * Volume actuel du lecteur, compris entre 0.0 et 1.0.
     */
    private Double volume = 0.5;

    /**
     * Enumération pour suivre l'état actuel du lecteur.
     */
    private enum STATUS {PLAYING, STOPPED, PAUSED, UNKNOW}

    /**
     * Indicateur de l'état du mode lecture aléatoire.
     * Si `true`, la lecture des pistes est aléatoire.
     */
    private Boolean randomState = false;

    /**
     * Indicateur de l'état du mode rechargement.
     * Si `true`, le lecteur doit recharger la piste actuelle.
     */
    private Boolean reloadState = false;

    /**
     * Style de fond pour les éléments d'interface, utilisé pour personnaliser l'apparence.
     */
    private String style1 = "-fx-background-color: #c5fca9; -fx-text-fill: black;";

    /**
     * Filtre appliqué aux pistes lors de la recherche.
     */
    private Filter filter;
    
    /**
     * Menu contextuel pour le catalogue de musique, utilisé pour interagir avec les pistes.
     */
    @FXML
    private ContextMenu CatalogContextMenu;

    /**
     * Menu contextuel pour la liste d'attente des pistes à jouer.
     */
    @FXML
    private ContextMenu WaitingContextMenu;

    /**
     * Champ de texte permettant à l'utilisateur de rechercher des pistes.
     */
    @FXML
    private TextField searchField;

    /**
     * ComboBox permettant de sélectionner une playlist pour ajouter des pistes.
     */
    @FXML
    private ComboBox addPlaylistComboBox;

    /**
     * Zone de lecture où les commandes de lecture, pause, etc., sont affichées.
     */
    @FXML
    private FlowPane playZone;

    /**
     * Label affichant le temps actuel de lecture de la piste.
     */
    @FXML
    private Label currentTime;

    /**
     * Label affichant le titre de la piste en cours de lecture.
     */
    @FXML
    private Label titleLabel;

    /**
     * Slider permettant de contrôler la position de lecture de la piste.
     */
    @FXML
    private Slider seekSlider;

    /**
     * Slider permettant de régler le volume du lecteur.
     */
    @FXML
    private Slider volumeSlider;

    /**
     * Bouton permettant de revenir à la piste précédente.
     */
    @FXML
    private Button previousBtn;

    /**
     * Bouton permettant de passer à la piste suivante.
     */
    @FXML
    private Button nextBtn;

    /**
     * Bouton permettant d'activer ou désactiver le mode lecture aléatoire.
     */
    @FXML
    private Button randomBtn;

    /**
     * Bouton permettant de recharger la piste actuelle.
     */
    @FXML
    private Button reloadBtn;

    /**
     * Bouton permettant d'ajouter une piste à une playlist.
     */
    @FXML
    private Button addPlaylistBtn;

    /**
     * Bouton permettant d'ajouter une piste à la liste d'attente.
     */
    @FXML
    private Button addWaitingListBtn;

    /**
     * Image affichant la pochette de la piste en cours de lecture.
     */
    @FXML
    private ImageView pictureTrack;

    /**
     * Image représentant l'état du volume (son activé ou coupé).
     */
    @FXML
    private ImageView volumePicture;

    /**
     * Image représentant le bouton de lecture/pause.
     */
    @FXML
    private ImageView picturePlayBtn;

    /**
     * ComboBox permettant de filtrer par playlist.
     */
    @FXML
    private ComboBox playlistComboBox;

    /**
     * ComboBox permettant de filtrer par genre musical.
     */
    @FXML
    private ComboBox genreComboBox;

    /**
     * ComboBox permettant de filtrer par artiste.
     */
    @FXML
    private ComboBox artistComboBox;

    /**
     * ComboBox permettant de filtrer par album.
     */
    @FXML
    private ComboBox albumComboBox;

    /**
     * Liste affichant les pistes en attente dans la file d'attente de lecture.
     */
    @FXML
    private ListView<TTrack> waitingTrackListView;

    /**
     * Table affichant les pistes du catalogue.
     */
    @FXML
    private TableView<TTrack> catalogTableView;

    /**
     * Liste observable des pistes en attente.
     */
    @FXML
    private ObservableList<TTrack> dataWaitingTrackList;

    // Colonnes de la table des pistes du catalogue
    /**
     * Colonne affichant le titre de chaque piste dans la table.
     */
    @FXML
    private TableColumn<TTrack, String> titleColCatalogTableView;

    /**
     * Colonne affichant la playlist associée à chaque piste dans la table.
     */
    @FXML
    private TableColumn<TPlaylist, String> playlistColCatalogTableView;

    /**
     * Colonne affichant la durée de chaque piste dans la table.
     */
    @FXML
    private TableColumn<TTrack, Integer> timeColCatalogTableView;

    /**
     * Colonne affichant le genre de chaque piste dans la table.
     */
    @FXML
    private TableColumn<TTrack, String> genreColCatalogTableView;

    /**
     * Colonne affichant l'artiste de chaque piste dans la table.
     */
    @FXML
    private TableColumn<TTrack, String> artistColCatalogTableView;

    /**
     * Colonne affichant l'album de chaque piste dans la table.
     */
    @FXML
    private TableColumn<TTrack, String> albumColCatalogTableView;

    // MenuItems pour les actions contextuelles sur les pistes

    /**
     * Option pour supprimer une piste de la liste d'attente.
     */
    @FXML
    private MenuItem deleteWaitingTrackMenu;

    /**
     * Option pour ajouter une piste à la liste des écoutées.
     */
    @FXML
    private MenuItem AddListenedTrackListMenu;

    /**
     * Option pour déplacer une piste vers le haut dans la liste d'attente.
     */
    @FXML
    private MenuItem upWaitingMenu;

    /**
     * Option pour déplacer une piste vers le bas dans la liste d'attente.
     */
    @FXML
    private MenuItem downWaitingMenu;

    /**
     * Option pour supprimer une piste du catalogue.
     */
    @FXML
    private MenuItem deleteTrackMenu;

    /**
     * Option pour modifier les informations d'une piste du catalogue.
     */
    @FXML
    private MenuItem modifyTrackMenu;

    // Commandes pour le contrôle à distance du lecteur

    /**
     * Objet représentant le contrôle à distance du lecteur.
     */
    private RemoteControl remote;

    /**
     * Commande pour démarrer la lecture de la piste.
     */
    private PlayCommand playCommand;

    /**
     * Commande pour mettre la lecture en pause.
     */
    private PauseCommand pauseCommand;

    /**
     * Commande pour arrêter la lecture de la piste.
     */
    private StopCommand stopCommand;

    /**
     * Commande pour obtenir le statut actuel du lecteur (lecture, pause, arrêt).
     */
    private GetStatusCommand getStatusCommand;

    /**
     * Commande pour obtenir le temps actuel de lecture de la piste.
     */
    private GetCurrentTimeCommand getCurrentTimeCommand;

    /**
     * Commande pour obtenir la durée totale de la piste.
     */
    private GetTotalDurationCommand getTotalDurationCommand;

    /**
     * Commande pour obtenir le volume actuel du lecteur.
     */
    private GetVolumeCommand getVolumeCommand;

    /**
     * Commande pour obtenir le lecteur actuel.
     */
    private GetPlayerCommand getPlayerCommand;

    /**
     * Fenêtre de la cataolgView
     */
    @FXML
    private BorderPane catalogWindow;


    /**
     * Constructeur du contrôleur qui initialise les services nécessaires pour gérer le catalogue,
     * les pistes, les genres, les artistes, les playlists, et les albums.
     *
     * @param trackService Le service pour gérer les pistes audio.
     * @param genreService Le service pour gérer les genres musicaux.
     * @param artistService Le service pour gérer les artistes.
     * @param playlistService Le service pour gérer les playlists.
     * @param albumService Le service pour gérer les albums.
     * @param catalog Le catalogue de pistes à gérer.
     */
    @Autowired
    public CatalogController(TrackService trackService,GenreService genreService, ArtistService artistService, PlaylistService playlistService, AlbumService albumService,Catalog catalog) {
        this.trackService = trackService;
        this.genreService = genreService;
        this.artistService = artistService;
        this.playlistService = playlistService;
        this.albumService = albumService;
        this.catalog = catalog;
    }

    /**
     * Initialise le contrôleur en créant les commandes de contrôle du lecteur,
     * en configurant la table du catalogue et en appliquant les filtres pour le catalogue.
     * Cette méthode est appelée automatiquement après l'injection des dépendances.
     */

    @FXML
    private void initialize() {
        this.remote=new RemoteControl();
        this.playCommand=new PlayCommand(this.player);
        this.pauseCommand=new PauseCommand(this.player);
        this.stopCommand=new StopCommand(this.player);
        this.getStatusCommand=new GetStatusCommand(this.player);
        this.getCurrentTimeCommand =new GetCurrentTimeCommand(this.player);
        this.getTotalDurationCommand =new GetTotalDurationCommand(this.player);
        this.getVolumeCommand=new GetVolumeCommand(this.player);
        this.getPlayerCommand=new GetPlayerCommand(this.player);

        //On récupère l'image de la pochette vide
        initialPictureTrack = new Image(pictureTrack.getImage().getUrl());
        //Création de la tableview du catalogue
        setupCatalogTableView();
        dataWaitingTrackList = FXCollections.observableArrayList(trackService.findByWaitingService(true));
        waitingTrackListView.setItems(dataWaitingTrackList);
        updateListView();
        playZone.setStyle("-fx-background-color: #e2e2e2;");
        filter=new Filter(playlistComboBox, genreComboBox, artistComboBox, albumComboBox,searchField,trackService,this,this.catalog);
        filter.setup();
        addPlaylistComboBox.setPromptText("Max. 50 caractères");
        addPlaylistComboBox.getEditor().textProperty().addListener((observable, oldText, newText) -> {
            int size=50;
            if (newText.toString().trim()!="" && !newText.toString().isEmpty() && newText.length()<=size){
                addPlaylistBtn.setDisable(false);
            }
            else{
                addPlaylistBtn.setDisable(true);
            }
            if (newText.length()>size){
                addPlaylistComboBox.setStyle("-fx-background-color: red; -fx-text-fill: red;");
            }
            else{
                addPlaylistComboBox.setStyle("");
            }
        });
    }

    /**
     * Définit le lecteur multimédia à contrôler par ce contrôleur.
     *
     * @param player Le lecteur à configurer.
     */
    public void setPlayer(Player player){
        this.player=player;
    }

    /**
     * Configure la TableView du catalogue des pistes en définissant les colonnes et en remplissant les données.
     * Active la multisélection et désactive le menu contextuel par défaut.
     */
    private void setupCatalogTableView() {
        this.titleColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackTitle"));
        this.timeColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackTimeFormat"));
        this.genreColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackGenresAsString"));
        this.artistColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackArtistsAsString"));
        this.albumColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackAlbumsAsString"));
        this.playlistColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackPlaylistsAsString"));

        //On réinitialise la liste d'écoute
        this.catalog.initTrackWaiting();
        //On remplit la tableview avec le catalog des tracks
        catalogTableView.setItems(this.catalog.getDataCatalogTable());
        //On active la multisélection dans la table view
        catalogTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //On initialize le menu contextuel de la tablewiew à null pour le désactiver
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
                        if (event.getClickCount() == 1) { // Vérifie si c'est un clic simple
                            selectTrackList();
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


    /**
     * Supprime les pistes sélectionnées dans le catalogue.
     * Affiche une boîte de confirmation avant de supprimer définitivement les pistes sélectionnées.
     */
    @FXML
    private void deleteTrackEvent() {

        String item="cet enregistrement";
        int countTrack = catalogTableView.getSelectionModel().getSelectedItems().size();
        if (countTrack>1) item="ces enregistrements";
        
        if (AlertClass.showConfirmationDeleteDialog(item)) {
            for (TTrack track : catalogTableView.getSelectionModel().getSelectedItems()) {
                if (track.getTrackWaiting()) {
                    deleteTrackWaiting(track);
                }
                trackService.deleteTrackService(track);
                this.catalog.removeTrack(track);
                catalogTableView.getSelectionModel().select(-1);
            }
        }
    }

    /**
     * Ouvre la fenêtre pour ajouter une nouvelle piste au catalogue.
     * Cette fenêtre permet à l'utilisateur de saisir les informations relatives à la nouvelle piste.
     *
     * @throws IOException Si la fenêtre ne peut pas être ouverte.
     */
    @FXML
    private void addTrackEvent() throws IOException {
        try {
            openTrackWindow(false);

        } catch (Exception e) {
            AlertClass.messageError(e.getMessage());
        }
    }

    /**
     * Ouvre la fenêtre pour modifier une piste du catalogue.
     * Cette fenêtre permet à l'utilisateur de modifier les informations relatives à la piste sélectionnée.
     *
     * @throws IOException Si la fenêtre ne peut pas être ouverte.
     */
    @FXML
    private void ModifyTrackEvent() throws IOException {
        try {
            openTrackWindow(true);

        } catch (Exception e) {
            AlertClass.messageError(e.getMessage());
        }
    }

    /**
     * Ouvre une fenêtre de modification ou d'ajout de piste.
     * Cette fenêtre permet d'ajouter une nouvelle piste ou de modifier une piste existante.
     *
     * @param update Si vrai, la fenêtre permettra de modifier une piste existante.
     * @throws IOException Si le chargement de la fenêtre échoue.
     */
    private void openTrackWindow(Boolean update) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TrackView.fxml"));
        loader.setControllerFactory(context::getBean);
        Parent root = loader.load();
        // Ajouter un ScrollPane pour un défilement vertical uniquement
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root); // Ajouter le contenu à faire défiler
        scrollPane.setFitToWidth(true); // Ajuster la largeur pour éviter le défilement horizontal
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Activer la barre verticale si nécessaire
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Désactiver la barre horizontale
        Stage stage = new Stage();
        if (update){
            TTrack track=catalogTableView.getSelectionModel().getSelectedItem();
            TrackController controller = loader.getController();
            controller.setTrackToModify(track);
            stage.setTitle("Modifier une piste");
        }
        else{
            stage.setTitle("Ajouter une piste");
        }
        stage.setScene(new Scene(scrollPane)); // Utiliser ScrollPane comme racine de la scène
        stage.setMinWidth(600);
        catalogWindow.setDisable(true);
        stage.showAndWait();
        catalogWindow.setDisable(false);
    }

    /**
     * Gère la sélectionne d'une piste dans la tableview du catalogue
     */
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

    /**
     * Gère la mise en lecture ou en pause d'une piste
     */
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

    /**
     * Gère l'arrêt de la lecture d'une piste
     */
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

    /**
     * Gère l'événement de modification de la position de la lecture à travers le slider de progression.
     */
    @FXML
    private void handleSliderChange() {
        if (this.remote.setCommand(getPlayerCommand).execute() != null) {
            remote.setCommand(new SeekCommand(this.player,Duration.seconds(seekSlider.getValue()))).execute();
        }
    }
    /**
     * Formate la durée d'une piste en "mm:ss".
     *
     * @param duration Durée à formater
     */
    private String formatDuration(double duration) {
        int minutes = (int) duration / 60;
        int seconds = (int) duration % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Gère l'événement de changement de volume via le slider de volume.
     */
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

    /**
     * Gère l'événement de changement de volume par la sourdine.
     */
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

    /**
     * Change l'icône du bouton de lecture/pause en fonction de l'état actuel du player (en pause ou en lecture).
     */
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

    /**
     * Gère l'événement de lecture de la piste précédente dans la liste.
     */
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

    /**
     * Fait appel à la méthode NextEvent en passant la valeur "true" afin de préciser que l'appel de la méthode se fait via le bouton "Suivant"
     */
    @FXML
    private void NextEvent() {
        NextEvent(true);
    }

    /**
     * Gère l'événement de lecture de la piste suivante en tenant compte de plusieurs scénarios
     * comme la lecture en mode aléatoire, le rechargement de la liste, etc.
     *
     * @param btn Booléen qui permet de savoir si la méthode a été appelée automatiquement ou via le bouton "Suivant"
     */
    @FXML
    private void NextEvent(Boolean btn) {
        initPlayZone();
        TTrack newTrack;
        int index=this.index;
        int countTrack = waitingTrackListView.getItems().size();
        String previousStatus= this.remote.setCommand(getStatusCommand).execute().toString();
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
        }else{
            index=0;
        }

        newTrack = dataWaitingTrackList.get(index);
        loadTrack(newTrack);
        if (Objects.equals(previousStatus, STATUS.PLAYING.toString()) || !btn) {
            this.remote.setCommand(playCommand).execute();
        }
        updateListView();
    }

    /**
     * Vérifie et met à jour les boutons de navigation (précédent et suivant) en fonction de l'index de la piste actuelle
     * et de l'état de la liste des pistes.
     */
    private void checkIndex() {
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
        if (countTrack == 1 || this.index == countTrack - 1) nextBtn.setDisable(true);
    }

    /**
     * Gère l'événement du bouton de mode aléatoire.
     * Modifie l'état du mode aléatoire et met à jour l'apparence du bouton.
     */
    @FXML
    private void randomEvent() {
        if (randomState) randomBtn.setStyle("");
        else randomBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        randomState = !randomState;
        reloadBtn.setStyle("");
        reloadState = false;
        checkIndex();
    }

    /**
     * Gère l'événement de rechargement de la playlist ou des options liées au rechargement.
     * Change l'état visuel du bouton de rechargement en fonction de son état actuel.
     * Désactive également l'état de randomisation (mode aléatoire) lorsqu'un rechargement est effectué.
     */
    @FXML
    private void reloadEvent() {
        if (reloadState) reloadBtn.setStyle("");
        else reloadBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        reloadState = !reloadState;
        randomBtn.setStyle("");
        randomState = false;
    }

    /**
     * Initialise la zone de lecture en réinitialisant l'affichage de la piste actuelle,
     * les informations associées (titre, temps), et désactive la zone de lecture.
     */
    private void initPlayZone() {
        playZone.setDisable(true);
        pictureTrack.setImage(initialPictureTrack);
        titleLabel.setText(null);
        currentTime.setText("00:00 / 00:00");
        this.TrackLoaded=null;
        if (reloadState && dataWaitingTrackList.isEmpty()) reloadEvent();
        if (randomState && dataWaitingTrackList.isEmpty()) randomEvent();
    }


    /**
     * Recharge le filtre de playlists dans la TableView en utilisant un objet `filter`.
     */
    @FXML
    private void reloadPlaylistFilter() {
        playlistComboBox=filter.reloadPlaylist();
    }

    /**
     * Recharge le filtre de genres dans la TableView en utilisant un objet `filter`.
     */
    @FXML
    private void reloadGenreFilter() {
        genreComboBox=filter.reloadGenre();
    }

    /**
     * Recharge le filtre d'artistes dans la TableView en utilisant un objet `filter`.
     */
    @FXML
    private void reloadArtistFilter() {
        artistComboBox=filter.reloadArtist();
    }

    /**
     * Recharge le filtre d'albums dans la TableView en utilisant un objet `filter`.
     */
    @FXML
    private void reloadAlbumFilter() {
        albumComboBox=filter.reloadAlbum();
    }

    /**
     * Gère la sélection d'une piste dans la liste d'écoute. Active ou désactive certains menus contextuels
     * en fonction de l'élément sélectionné et de son statut.
     */
    @FXML
    private void selectWaitingTrackList() {
        int index = waitingTrackListView.getSelectionModel().getSelectedIndex();
        int indexEnd=waitingTrackListView.getItems().size()-1;
        TTrack track = waitingTrackListView.getSelectionModel().getSelectedItem();
        upWaitingMenu.setDisable(false);
        deleteWaitingTrackMenu.setDisable(false);
        downWaitingMenu.setDisable(false);

        if (track != null && index != -1) {
            updateListView();
            if (this.remote.setCommand(getPlayerCommand).execute() != null && this.remote.setCommand(getStatusCommand).execute() == STATUS.PLAYING && track.equals(this.TrackLoaded)) {
                deleteWaitingTrackMenu.setDisable(true);
            }
        }
        if (index==0) upWaitingMenu.setDisable(true);
        if (index==indexEnd) downWaitingMenu.setDisable(true);
    }

    /**
     * Charge la piste sélectionnée dans la liste d'écoute et lance la lecture si nécessaire.
     * Si la piste est déjà en cours de lecture, elle la charge sans la relancer.
     */
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

    /**
     * Charge une piste dans le lecteur et met à jour l'interface en fonction de son état.
     *
     * @param newTrack La nouvelle piste à charger.
     */

    private void loadTrack(TTrack newTrack) {
        waitingTrackListView.getSelectionModel().select(newTrack);
        this.TrackLoaded = waitingTrackListView.getSelectionModel().getSelectedItem();
        this.index = waitingTrackListView.getSelectionModel().getSelectedIndex();
        checkIndex();
        updateListView();
        if (this.TrackLoaded.getTrackPath() != null) {
            playZone.setDisable(false);
            titleLabel.setText(TrackLoaded.getTrackTitle());

            String audioFilePath = this.TrackLoaded.getTrackPath();
            File audioFile = new File(audioFilePath);
            if (audioFile.exists() && audioFile.isFile()) {
                this.remote.setCommand(new LoadTrackCommand(this.player,audioFilePath)).execute();
            } else {
                AlertClass.playingError();
                dataWaitingTrackList.remove(TrackLoaded);
                TrackLoaded.setTrackWaiting(false);
                this.index-=1;
                NextEvent();
            }

            if (this.remote.setCommand(getPlayerCommand).execute()!=null){
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
                if (this.TrackLoaded.getTrackPicture() != null && !this.TrackLoaded.getTrackPicture().isEmpty()) {
                    File file = new File(this.TrackLoaded.getTrackPicture());
                    if (file.exists() && file.isFile()) {
                        try {
                            Image image = new Image(file.toURI().toString());
                            pictureTrack.setImage(image);
                        } catch (IllegalArgumentException e) {
                            pictureTrack.setImage(initialPictureTrack);
                            AlertClass.messageError(e.getMessage());
                        }
                    } else {
                        pictureTrack.setImage(initialPictureTrack);
                    }
                } else {
                    pictureTrack.setImage(initialPictureTrack);
                }
            }
        }
    }

    /**
     * Fait appel à la méthode deleteTrackWaiting en passant la piste sélectionnée en paramètre
     */
    @FXML
    private void deleteWaitingTrackEvent() {
        TTrack track = waitingTrackListView.getSelectionModel().getSelectedItem();
        deleteTrackWaiting(track);
    }

    /**
     * Supprime une piste de la liste d'attente et met à jour l'interface en conséquence.
     *
     * @param track La piste à supprimer.
     */
    private void deleteTrackWaiting(TTrack track) {
        int countTrack;
        dataWaitingTrackList.remove(track);
        track.setTrackWaiting(false);
        trackService.saveTrackService(track);
        countTrack = waitingTrackListView.getItems().size();
        checkIndex();

        if (track.equals(TrackLoaded)) {
            this.remote.setCommand(stopCommand).execute();
            if (!reloadState) this.index-=1;
            if (countTrack>0) NextEvent(false);
            else{
                initPlayZone();
            }
        }
        if (countTrack>0) updateListView();
        else{
            addPlaylistComboBox.setDisable(true);
            addPlaylistBtn.setDisable(true);
        }
    }

    /**
     * Déplace la piste sélectionnée vers le haut dans la liste d'attente.
     */
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
        updateListView();
    }

    /**
     * Déplace la piste sélectionnée vers le bas dans la liste d'attente.
     */
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
        updateListView();
    }

    /**
     * Échange deux éléments dans la liste des pistes d'attente.
     * @param list
     * @param index1
     * @param index2
     */
    private void swap(ObservableList<TTrack> list, int index1, int index2) {
        TTrack temp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, temp);
    }

    /**
     * Mise à jour de la liste d'écoute.
     */
    private void updateListView() {
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
                            updateListView();
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

    /**
     * Vide la liste d'écoute
     */

    @FXML
    private void emptyWaitingEvent() {
        for (TTrack track : dataWaitingTrackList) {
            if (this.remote.setCommand(getPlayerCommand).execute() != null && Objects.equals(this.remote.setCommand(getStatusCommand).execute(), STATUS.PLAYING.toString())) {
                StopEvent();
            }
            track.setTrackWaiting(false);
            trackService.saveTrackService(track);
        }
        dataWaitingTrackList.clear();
        initPlayZone();
        TrackLoaded = null;
        addPlaylistComboBox.setDisable(true);
        addPlaylistBtn.setDisable(true);
    }

    /**
     * Ajoute une track à la liste d'écoute
     */

    @FXML
    private void AddTrackWaitingListEvent() {
        List<TTrack> list=ListOfTracks();

        for (TTrack track : list) {
            if (!track.getTrackWaiting()) {
                dataWaitingTrackList.add(track);
                track.setTrackWaiting(true);
                trackService.saveTrackService(track);
                addPlaylistComboBox.setDisable(false);
            }
        }
        nextBtn.setDisable(false);
    }

    /**
     * Ajoute une playlist depuis la liste d'écoute
     */
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
        addPlaylistComboBox.getSelectionModel().select(-1);
    }

    /**
     * Remplit la addPlayListComboBox des playlists disponibles
     */
    @FXML
    private void SetupPlaylistComboBoxEvent(){
        addPlaylistComboBox.getItems().clear();
        List<TPlaylist> listPlaylists=playlistService.findAllPlaylistService();
        for(TPlaylist p:listPlaylists){
            addPlaylistComboBox.getItems().add(p.getPlaylistName());
        }
    }

    /**
     * Récupère la liste des tracks sélectionnées dans la catalogTableView (si vide, renvoie les tracks de la catalogTableView).
     */
    private List<TTrack> ListOfTracks(){
        List<TTrack> list=catalogTableView.getSelectionModel().getSelectedItems();
        if (!list.isEmpty()){
            list=catalogTableView.getSelectionModel().getSelectedItems();
        }else {
            list = this.catalog.getDataCatalogTable();
        }
        return list;
    }

    /**
     * Modifie le titre d'une piste dans la liste d'écoute
     *
     *  @param track La piste qui a été modifiée dans le calaog
     */
    public void updateTrackWaitingList(TTrack track){
        if (track!=null){
            for (TTrack t:dataWaitingTrackList){
                if (t.equals(track)) {
                    t.setTrackTitle(track.getTrackTitle());
                    waitingTrackListView.refresh();
                }
            }
        }
    }
}
