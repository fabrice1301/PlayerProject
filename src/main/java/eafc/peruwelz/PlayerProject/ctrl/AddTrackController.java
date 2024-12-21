package eafc.peruwelz.PlayerProject.ctrl;

import eafc.peruwelz.PlayerProject.domain.TTrack;

import eafc.peruwelz.PlayerProject.Class.Catalog;
import eafc.peruwelz.PlayerProject.service.TrackService;
import javafx.fxml.FXML;

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
import java.util.Map;

@Controller
public class AddTrackController {

    // Are you sure about this ?
    private TTrack track;
    private TrackService trackService;
    private Catalog catalog;
    private File file;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button searchTrackBtn;

    @FXML
    private TextField fieldNameField;

    @Autowired
    public AddTrackController(TrackService trackService, Catalog catalog){
        this.trackService=trackService;
        this.catalog = catalog;
    }

    @FXML
    private void initialize(){
        searchTrackBtn.setText("\uD83D\uDD0D " + searchTrackBtn.getText());
        System.out.println(trackService);
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
            fieldNameField.setText(this.file.getName());
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
        //dataCatalogTable.add(track);
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
}
