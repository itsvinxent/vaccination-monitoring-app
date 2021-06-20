package vaccine.frontend.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class addVaccine implements Initializable {
    @FXML
    private AnchorPane main;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void cancel (ActionEvent event) throws IOException {
        Stage popup = (Stage) main.getScene().getWindow();
        popup.close();
    }
}
