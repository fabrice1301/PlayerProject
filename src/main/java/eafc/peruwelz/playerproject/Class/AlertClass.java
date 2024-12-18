package eafc.peruwelz.playerproject.Class;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertClass {

    public static boolean showConfirmationDeleteDialog(String item) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer "+item+" ?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }


    public static void showInformationDeleteDialog(String item) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Suppression impossible");
        alert.setHeaderText("La suppression n'est pas possible car "+item+" est en lien avec une piste audio");
        alert.showAndWait();

    }

}
