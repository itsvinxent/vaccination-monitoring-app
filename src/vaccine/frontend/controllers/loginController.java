package vaccine.frontend.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import vaccine.backend.classes.Account;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import vaccine.backend.classes.Doctor;
import vaccine.backend.classes.Staff;
import vaccine.backend.dao.*;

public class loginController {

    @FXML
    private BorderPane main;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;

    private Parent root;
    private Stage stage;
    private Scene scene;

    public void loginValidate(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        Account details = new Account(username, password);
        details = accountDAO.validate(details);

        if (details.getUserID() == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Incorrect Password. Try Again");
            alert.show();
        } else if (details.getUserID() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Username does not exist. Contact your administrator to create an account.");
            alert.show();
        } else if (details.getUserID() == -2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("The user has already logged in. Log out of other devices first.");
            alert.show();
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/dashboard.fxml"));
            root = loader.load();
            stage = new Stage();
            scene = new Scene(root);

            String name;
            String usertype = details.getUsertype();
            int id = details.getUserID();
            if (usertype.equals("vaccinator")){
                Doctor doctor = doctorDAO.getDoctorByUserID(id);
                name = doctor.getDoctorName();
                accountDAO.setLoginStatus(id, "in");
            }
            else if (usertype.equals("medical staff")){
                Staff staff = staffDAO.getStaffByUserID(id);
                name = staff.getStaffName();
                accountDAO.setLoginStatus(id, "in");
            }
            else
                name = "Admin";


            patientTable patientTable = loader.getController();
            patientTable.displayName(id, name, usertype);
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    accountDAO.setLoginStatus(id, "out");
                }
            });

            draw.screen(root, stage, scene);
            Stage login = (Stage) main.getScene().getWindow();
            login.close();
        }
    }

    public void exit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to exit?");
        alert.setTitle("Confirm");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            Stage login = (Stage) main.getScene().getWindow();
            login.close();
        }
    }


}
