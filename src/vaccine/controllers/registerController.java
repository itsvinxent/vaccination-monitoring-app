package vaccine.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import vaccine.classes.Account;
import vaccine.classes.Doctor;
import vaccine.dao.accountDAO;

public class registerController implements Initializable {

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


            String usertype;
            if(doctor.isSelected())
                usertype = doctor.getText();
            else if (medstaff.isSelected())
                usertype = medstaff.getText();

            if (!pass.equals(cpass)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Confirm Password must match Password.");
                alert.show();
            } else {
                Account account = new Account(0, user, pass, "");
                int id = accountDAO.save(account);

                System.out.println(id);
            }
            System.out.println("register");

        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public void cancel (ActionEvent event) throws IOException {
        Stage reg = (Stage) main.getScene().getWindow();
        reg.close();
    }


}
