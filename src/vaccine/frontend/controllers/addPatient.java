package vaccine.frontend.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import vaccine.backend.classes.Schedule;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

import vaccine.backend.dao.*;

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
    private Button saveEntry, updateEntry, deleteEntry;

    int interval;
    String first_dose, second_dose;
    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    Calendar calendar = Calendar.getInstance();
    Schedule patient = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate ComboBoxes with doctor names and vaccine brands
        ObservableList<String> vaccines = vaccineDAO.getAllVaccineBrand();
        ObservableList<String> doctors = doctorDAO.getAllDoctorsByName();
//        ObservableList<String> sched = FXCollections.observableArrayList("7:30AM", "10:30AM", "1:30PM", "4:30PM");
        ObservableList<String> sched = FXCollections.observableArrayList("AM", "PM");
        vaccineID.setItems(vaccines);
        drID.setItems(doctors);
        schedule.setItems(sched);

    }

    public void save(ActionEvent event) throws Exception {
        patient = new Schedule(
                drID.getValue(),
                patientLName.getText(),
                patientFName.getText(),
                vaccineID.getValue(),
                first_dose,
                second_dose,
                schedule.getValue(),
                schedule.getValue(),
                "incomplete");
        // todo: system for setting the status
        scheduleDAO.addSchedule(patient);

        Stage stage = (Stage) main.getScene().getWindow();
        stage.hide();

        saveEntry.setOnAction(event1 -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_HIDDEN)));
    }

    public void cancel(ActionEvent event)  {
        Stage popup = (Stage) main.getScene().getWindow();
        popup.close();
    }

    public void update(ActionEvent event) throws Exception {
        Schedule updatedPatient = new Schedule(
                patient.getPatientNum(),
                drID.getValue(),
                patientLName.getText(),
                patientFName.getText(),
                vaccineID.getValue(),
                firstDose.getEditor().getText(),
                secondDose.getEditor().getText(),
                schedule.getValue(),
                schedule.getValue(),
                "incomplete");
        scheduleDAO.updateSchedule(updatedPatient);
        System.out.println(updatedPatient.getFirstDose());
        Stage stage = (Stage) main.getScene().getWindow();
        stage.hide();

        updateEntry.setOnAction(event1 -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_HIDDEN)));
    }

    public void delete(ActionEvent event) throws Exception {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete this record?");
            alert.setTitle("Confirm");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                scheduleDAO.deleteSchedule(patient);
                Stage stage = (Stage) main.getScene().getWindow();
                stage.hide();

                deleteEntry.setOnAction(event1 -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_HIDDEN)));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void showSecondDose(ActionEvent event) {
        first_dose = firstDose.getEditor().getText();
        interval = vaccineDAO.getDosageIntervalsByBrand(vaccineID.getValue());
        try {
            if (event.getSource() == firstDose)
                calendar.setTime(format.parse(first_dose));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DAY_OF_MONTH, interval);
        second_dose = format.format(calendar.getTime());
        secondDose.getEditor().setText(second_dose);
    }

    public void setFieldContent(int patientID) {
        patient = scheduleDAO.getPatientByPatientID(patientID);
        drID.setValue(patient.getDoctorName());
        patientFName.setText(patient.getPatientFName());
        patientLName.setText(patient.getPatientLName());
        vaccineID.setValue(patient.getVaccineBrand());
        firstDose.getEditor().setText(patient.getFirstDose());
        secondDose.getEditor().setText(patient.getSecondDose());
        schedule.setValue(patient.getFirstTime());

        updateEntry.setDisable(false);
        updateEntry.setVisible(true);
        updateEntry.setOpacity(1);
        saveEntry.setDisable(true);
        saveEntry.setVisible(false);
        saveEntry.setOpacity(0);
        deleteEntry.setDisable(false);
        deleteEntry.setVisible(true);
        deleteEntry.setOpacity(1);
    }
}
