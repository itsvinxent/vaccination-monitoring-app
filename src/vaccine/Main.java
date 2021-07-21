package vaccine;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import vaccine.backend.dao.accountDAO;

import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("frontend/login.fxml")));
            Scene scene = new Scene(root);

            Image logo = new Image("vaccine/frontend/img/GitHub.png");
            stage.setTitle("Vaccine Monitoring App");
            stage.setResizable(false);
            stage.getIcons().add(logo);
            stage.setScene(scene);
            stage.show();
            accountDAO.createAdmin();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
