package eafc.peruwelz.PlayerProject.ctrl;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class MainController {

    @Autowired
    private ApplicationContext context;

    @FXML
    private void CatalogOpenEvent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CatalogView.fxml"));
        loader.setControllerFactory(context::getBean);
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Ajouter une piste");
        stage.setScene(new Scene(root));
        stage.show();
    }

}
