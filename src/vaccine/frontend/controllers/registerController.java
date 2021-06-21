package vaccine.frontend.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import vaccine.backend.classes.Account;
import vaccine.backend.classes.Doctor;
import vaccine.backend.dao.accountDAO;
import vaccine.backend.dao.doctorDAO;
import vaccine.backend.util.AES256;

public class registerController implements Initializable {

    @FXML
    private Button registerButton;
    @FXML
    private TextField username, password, cpassword;
    @FXML
    private TextField fname, lname;
    @FXML
    private RadioButton doctor, medstaff;
    @FXML
    private ComboBox<String> schedule;
    @FXML
    private AnchorPane main;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> sched = FXCollections.observableArrayList();
        sched.add("M/W/F");
        sched.add("T/TH/S");
        schedule.setItems(sched);
    }

    public void register(ActionEvent event) throws IOException {
        try {

            String user = username.getText();
            String pass = password.getText();
            String cpass = cpassword.getText();
            String fName = fname.getText();
            String lName = lname.getText();
            String sched = schedule.getValue();

            String usertype = null;
            if(doctor.isSelected())
                usertype = doctor.getText().toLowerCase(Locale.ROOT);
            else if (medstaff.isSelected())
                usertype = medstaff.getText().toLowerCase(Locale.ROOT);
            String salt = AES256.generateSalt();

            if (!pass.equals(cpass)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Confirm Password must match Password.");
                alert.show();
            } else {
                pass = AES256.securePassword(pass, salt, true);
                Account account = new Account(user, pass, usertype, salt);
                int id = accountDAO.addAccount(account);
                if (Objects.requireNonNull(usertype).equals("medstaff")) {
                    // DO MEDSTAFF ADD
                } else {
                    String fullname = lName + ", " + fName;
                    Doctor doctor = new Doctor(id, fullname, sched);
                    doctorDAO.addDoctor(doctor);
                }
            }

            Stage stage = (Stage) main.getScene().getWindow();
            stage.hide();

            registerButton.setOnAction(event1 -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_HIDDEN)));

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void cancel (ActionEvent event) throws IOException {
        Stage reg = (Stage) main.getScene().getWindow();
        reg.close();
    }


}
