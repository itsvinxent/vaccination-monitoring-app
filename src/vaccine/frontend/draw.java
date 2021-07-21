package vaccine.frontend;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Java Class that contains methods used for drawing full sized and pop-up windows.
 */
public class draw {

    /**
     * Method used for drawing a full sized interfaces
     * USED FOR: Login Interface and Main Interface
     * @param parent JavaFX Parent Object
     * @param stage JavaFX Stage Object
     * @param scene JavaFX Scene Object
     */
    public static void screen(Parent parent, Stage stage, Scene scene) {
        try {
            Image logo = new Image("vaccine/frontend/img/GitHub.png");
            stage.setTitle("Vaccine Monitoring App");
            stage.setResizable(false);
            stage.getIcons().add(logo);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Method used for drawing pop-up windows.
     * USED FOR: Add and Update interface for individual records.
     * @param parent JavaFX Parent Object
     * @param stage JavaFX Stage Object
     * @param scene JavaFX Scene Object
     */
    public static void popUp(Parent parent, Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

}
