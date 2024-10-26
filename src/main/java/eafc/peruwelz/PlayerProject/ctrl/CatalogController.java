package eafc.peruwelz.PlayerProject.ctrl;

import eafc.peruwelz.PlayerProject.domain.TTrack;
import eafc.peruwelz.PlayerProject.Class.Catalog;
import eafc.peruwelz.PlayerProject.service.TrackService;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.io.File;
import java.io.IOException;

@Controller
public class CatalogController {
    @Autowired
    private ApplicationContext context;
    private TTrack track;
    private File file;
    private Image initialPicture;
    private TTrack trackSelected;
    private MediaPlayer mediaPlayer;
    private AnimationTimer timer; // Pour mettre à jour le Slider

    @Autowired
    private TrackService trackService;
    @Autowired
    private Catalog catalog;
    private enum Actions {ADD, UPDATE};
    private Actions act;

    @FXML
    private Label currentTime;

    @FXML
    private Label titleLabel;

    @FXML
    private Slider audioSlider;

    @FXML
    private Slider volumeSlider;


    @FXML
    private Button searchTrackBtn;

    @FXML
    private ImageView pictureTrack;

    @FXML
    private ImageView volumePicture;

    @FXML
    private Button deleteTrackBtn;

    @FXML
    private TextField FileNameField;

    @FXML
    private TableView<TTrack> catalogTableView;

    @FXML
    private ObservableList<TTrack> dataCatalogTable;

    @FXML
    private TableColumn<TTrack, String> titleColCatalogTableView;

    @FXML
    private TableColumn<TTrack, Integer> timeColCatalogTableView;

    @FXML
    private TableColumn<TTrack, String> genreColCatalogTableView;

    @FXML
    private TableColumn<TTrack, String> artistColCatalogTableView;

    @FXML
    private TableColumn<TTrack, String> albumColCatalogTableView;


    @FXML
    private void initialize(){
        //On récupère l'image de la pochette vide
        initialPicture=new Image(pictureTrack.getImage().getUrl());

        this.titleColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackTitle"));
        this.timeColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackTimeFormat"));
        this.genreColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackGenresAsString"));
        this.artistColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackArtistsAsString"));
        this.albumColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackAlbumsAsString"));
        dataCatalogTable=this.catalog.reloadCatalogTableView();
        catalogTableView.setItems(dataCatalogTable);
    }

    @FXML
    private void deleteTrackEvent(){
        this.track=catalogTableView.getSelectionModel().getSelectedItem();
        trackService.deleteTrackService(this.track);
        dataCatalogTable.remove(track);
    }

    @FXML
    private void addTrackEvent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddTrackView.fxml"));
        loader.setControllerFactory(context::getBean);
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Ajouter une piste");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void selectTrackTableView(){
        //On récupère la piste sélectionnée dans le catalogue
        this.trackSelected=catalogTableView.getSelectionModel().getSelectedItem();

        if (this.trackSelected.getTrackPath() != null) {
            titleLabel.setText(trackSelected.getTrackTitle());
            String audioFilePath = this.trackSelected.getTrackPath();
            Media media = new Media(new File(audioFilePath).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(() -> {
                audioSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
            });
            mediaPlayer.setOnEndOfMedia(() -> {
                StopEvent(); // Arrêter la lecture quand le média est terminé
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
            pictureTrack.setImage(initialPicture);
        }

    }

    @FXML
    private void PlayEvent(){
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }

    }

    @FXML
    private void StopEvent(){
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            timer.stop(); // Arrêter le timer
            audioSlider.setValue(0); // Réinitialiser le Slider
        }

    }

    @FXML
    private void PauseEvent(){
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
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
    }
}
