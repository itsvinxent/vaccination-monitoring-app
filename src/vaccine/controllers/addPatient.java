package vaccine.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import vaccine.classes.Schedule;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import vaccine.classes.Vaccine;
import vaccine.dao.*;

public class addPatient implements Initializable {
    @FXML
    private TextField patientLName;

    @FXML
    private TextField patientFName;

    @FXML
    private DatePicker firstDose;

    @FXML
    private DatePicker secondDose;

    @FXML
    private ComboBox<String> drID;

    @FXML
    private ComboBox<String> vaccineID;

    @FXML
    private ComboBox<String> schedule;

    @FXML
    private AnchorPane main;

    @FXML
    private Button saveEntry;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate ComboBoxes with doctor names and vaccine brands
        ObservableList<String> vaccines = vaccineDAO.getAllVaccineBrand();
        ObservableList<String> doctors = doctorDAO.getAllDoctorsByName();
        ObservableList<String> sched = FXCollections.observableArrayList("7:30AM", "10:30AM", "1:30PM", "4:30PM");
        vaccineID.setItems(vaccines);
        drID.setItems(doctors);
        schedule.setItems(sched);

    }

    public void save(ActionEvent event) throws Exception {
        String pattern = "MM-dd-yyyy";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

//        Schedule patient = new Schedule(0,
//                drID.getValue(),
//                patientLName.getText(),
//                patientFName.getText(),
//                vaccineID.getValue(),
//                firstDose.getValue().format(dateFormatter),
//                secondDose.getValue().format(dateFormatter),
//                "incomplete");
        // todo: system for setting the status
        Schedule patient = new Schedule(0,
                2,
                "Silonga",
                "Blaster",
                "1",
                "03-03-2021",
                "03-24-2021",
                "10:30AM",
                "10:30AM",
                "incomplete");

        int stat = scheduleDAO.save(patient);
//        save(patient);

        Stage stage = (Stage) main.getScene().getWindow();
        stage.hide();

        saveEntry.setOnAction(event1 -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_HIDDEN)));



//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/dashboard.fxml"));
//        Parent root = loader.load();
//
//        Stage stage2 = (Stage) main.getScene().getWindow();
//        stage2.close();
//
//        Stage stage = new Stage();
//        Scene scene = new Scene(root, 854, 480);
//        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("../gui/application.css")).toExternalForm());
//        String css = Objects.requireNonNull(this.getClass().getResource("../gui/application.css")).toExternalForm();
//        scene.getStylesheets().add(css);
//
//        Image logo = new Image("vaccine/img/GitHub.png");
//        stage.setTitle("Vaccine Monitoring App");
//        stage.setResizable(false);
//        stage.getIcons().add(logo);
//        stage.setScene(scene);
//        stage.show();

    }

    public void cancel(ActionEvent event)  {
        Stage popup = (Stage) main.getScene().getWindow();
        popup.close();
    }


}
