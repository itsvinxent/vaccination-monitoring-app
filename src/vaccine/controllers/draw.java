package vaccine.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.util.List;
import java.util.Objects;

public class draw {

    public static void screen(Parent parent, Stage stage, Scene scene, String css) {
        try {
            if (css != null)
                scene.getStylesheets().add(css);
            Image logo = new Image("vaccine/img/GitHub.png");
            stage.setTitle("Vaccine Monitoring App");
            stage.setResizable(false);
            stage.getIcons().add(logo);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void popUp(Parent parent, Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

}
