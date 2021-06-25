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

public class registerController implements Initializable {
    // Initialize UI components
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
        initializeComboBoxes();
    }

    /**
     * Creates an ObservableList that contains the combo box input options for schedule
     */
    public void initializeComboBoxes() {
        ObservableList<String> sched = FXCollections.observableArrayList();
        sched.add("M/W/F");
        sched.add("T/TH/S");
        schedule.setItems(sched);
    }

    public void register(ActionEvent event) throws IOException {
        try {
            // Get values of input fields.
            // TODO: Input Validation for all fields. (Empty or Incorrect data type)
            String user = username.getText();
            String pass = password.getText();
            String cpass = cpassword.getText();
            String fName = fname.getText();
            String lName = lname.getText();
            String sched = schedule.getValue();

            // Check for the selected usertype in the radio buttons.
            String usertype = null;
            if(doctor.isSelected())
                usertype = doctor.getText().toLowerCase(Locale.ROOT);
            else if (medstaff.isSelected())
                usertype = medstaff.getText().toLowerCase(Locale.ROOT);

            // Checks if the Confirm Password field matches the Password Field.
            if (!pass.equals(cpass)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Confirm Password must match Password.");
                alert.show();
            } else {
                // Creates an Account object, which is sent as a parameter in the addAccount method
                Account account = new Account(user, pass, usertype);
                int id = accountDAO.addAccount(account);

                // Checks if the record is inserted
                if (id == -1) throw new IOException("Error");

                // Use the returned ID as a foreign key
                // for creating a new doctor_info or staff_info
                else {
                    if (Objects.requireNonNull(usertype).equals("medstaff")) {
                        // TODO: CODE FOR ADDING A MEDICAL STAFF
                    } else {
                        String fullname = lName + ", " + fName;
                        Doctor doctor = new Doctor(id, fullname, sched);
                        doctorDAO.addDoctor(doctor);
                    }
                }
            }
            // Closes the current window after processing.
            Stage stage = (Stage) main.getScene().getWindow();
            stage.hide();
            // Reloads the table to see the inserted record.
            registerButton.setOnAction(event1 -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_HIDDEN)));

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void cancel (ActionEvent event) throws IOException {
        // Closes the current window and restores focus to the main window.
        Stage reg = (Stage) main.getScene().getWindow();
        reg.close();
    }


}
