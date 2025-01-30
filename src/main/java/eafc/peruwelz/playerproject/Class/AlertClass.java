package eafc.peruwelz.playerproject.Class;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * Classe utilitaire pour afficher des boîtes de dialogue d'alerte dans l'application.
 * Cette classe contient des méthodes statiques pour afficher des alertes de confirmation,
 * d'information, de warning, et d'erreur.
 */
public class AlertClass {

    /**
     * Affiche une boîte de dialogue de confirmation pour la suppression d'un élément.
     *
     * @param item le nom de l'élément à supprimer.
     * @return true si l'utilisateur confirme la suppression, sinon false.
     */
    public static boolean showConfirmationDeleteDialog(String item) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer " + item + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Affiche une boîte de dialogue d'information indiquant que la suppression est impossible
     * parce que l'élément est en lien avec une piste audio.
     *
     * @param item le nom de l'élément dont la suppression est impossible.
     */
    public static void showInformationDeleteDialog(String item) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Suppression impossible");
        alert.setHeaderText("La suppression n'est pas possible car " + item + " est en lien avec une piste audio");
        alert.showAndWait();
    }

    /**
     * Affiche une boîte de dialogue d'avertissement lorsque la lecture d'une piste audio échoue
     * car la piste est introuvable, et qu'elle sera supprimée de la liste d'écoute.
     */
    public static void playingError() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.WARNING);
        alert.setTitle("Lecture impossible");
        alert.setHeaderText("La piste audio est introuvable, elle va être supprimée de la liste d'écoute");
        alert.showAndWait();
    }

    /**
     * Affiche une boîte de dialogue d'erreur avec un message personnalisé.
     *
     * @param message le message d'erreur à afficher.
     */
    public static void messageError(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.ERROR);
        alert.setTitle("Problème rencontré");
        alert.setHeaderText("Problème : " + message);
        alert.showAndWait();
    }
}

