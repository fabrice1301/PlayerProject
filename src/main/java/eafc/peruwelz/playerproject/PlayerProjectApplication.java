package eafc.peruwelz.playerproject;

import eafc.peruwelz.playerproject.Class.Catalog;
import eafc.peruwelz.playerproject.ctrl.CatalogController;
import eafc.peruwelz.playerproject.player.MPlayer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import javafx.application.Application;

/**
 * Classe principale de l'application PlayerProject.
 * Cette classe intègre Spring Boot et JavaFX pour la gestion de l'interface utilisateur et des services backend.
 */
@SpringBootApplication
public class PlayerProjectApplication extends Application {

    public ApplicationContext context;

    /**
     * Point d'entrée principal de l'application.
     * @param args Arguments de la ligne de commande.
     */
    public static void main(final String[] args) {
        launch();
    }

    /**
     * Initialise le contexte Spring lors du démarrage de l'application.
     */
    @Override
    public void init(){
        context = new SpringApplicationBuilder(PlayerProjectApplication.class).run();
    }

    /**
     * Initialise et affiche la fenêtre principale de l'application.
     * @param stage La fenêtre principale de l'application.
     * @throws Exception En cas d'erreur de chargement de la vue.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Initialiser le contrôleur et le modèle
        CatalogController controller = context.getBean(CatalogController.class);
        Catalog catalog = context.getBean(Catalog.class);
        controller.setPlayer(new MPlayer());
        catalog.setCatalogController(controller);

        // Charger la vue depuis le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CatalogView.fxml"));
        loader.setControllerFactory(context::getBean);
        Parent root = loader.load();

        // Créer un ScrollPane et y insérer le contenu chargé
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true); // Ajuste la largeur au ScrollPane
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Barre de défilement verticale toujours visible
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Pas de barre horizontale

        // Configurer la scène avec le ScrollPane
        Scene scene = new Scene(scrollPane);
        stage.setTitle("Player Project");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setMinWidth(900);
        stage.show();

        // Gérer la fermeture de l'application
        stage.setOnCloseRequest(event -> Platform.exit());
    }

    /**
     * Arrête l'application en fermant le contexte Spring proprement.
     */
    @Override
    public void stop() {
        SpringApplication.exit(context, () -> 0);
    }
}

