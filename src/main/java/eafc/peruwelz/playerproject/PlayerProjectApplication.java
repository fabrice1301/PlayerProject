package eafc.peruwelz.playerproject;

import eafc.peruwelz.playerproject.Class.Catalog;
import eafc.peruwelz.playerproject.ctrl.CatalogController;
import eafc.peruwelz.playerproject.player.MPlayer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import javafx.application.Application;

@SpringBootApplication
public class PlayerProjectApplication extends Application {
    public ApplicationContext context;
    public static void main(final String[] args) {
        launch();
    }

    @Override
    public void init(){
        context = new SpringApplicationBuilder(PlayerProjectApplication.class).run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        CatalogController controller= context.getBean(CatalogController.class);
        Catalog catalog= context.getBean(Catalog.class);
        controller.setPlayer(new MPlayer());
        catalog.setCatalogController(controller);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CatalogView.fxml"));
        loader.setControllerFactory(context::getBean);
        Parent root=loader.load();
        stage.setTitle("Player Project");
        stage.setScene(new Scene(root));
        //stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest((Event -> Platform.exit()));
    }
    @Override
    public void stop() {
        SpringApplication.exit(context,()->0);
    }
}

