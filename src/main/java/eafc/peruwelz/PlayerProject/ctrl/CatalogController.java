package eafc.peruwelz.PlayerProject.ctrl;

import eafc.peruwelz.PlayerProject.domain.TTrack;
import eafc.peruwelz.PlayerProject.Class.Catalog;
import eafc.peruwelz.PlayerProject.service.TrackService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;

@Controller
public class CatalogController {
    @Autowired
    private ApplicationContext context;
    private TTrack track;
    private File file;
    @Autowired
    private TrackService trackService;
    @Autowired
    private Catalog catalog;
    private enum Actions {ADD, UPDATE};
    private Actions act;


    @FXML
    private Button searchTrackBtn;

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
    private TableColumn<TTrack, String> pathColCatalogTableView;


    @FXML
    private void initialize(){
        this.titleColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackTitle"));
        this.timeColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackTime"));
        this.pathColCatalogTableView.setCellValueFactory(new PropertyValueFactory<>("trackPath"));
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




}
