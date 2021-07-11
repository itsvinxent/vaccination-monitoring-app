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
import java.util.*;

import vaccine.backend.dao.*;

public class addPatient implements Initializable {
    @FXML
    private TextField patientLName, patientFName, cityAddress, age, sex;

    @FXML
    private DatePicker firstDose, secondDose;

    @FXML
    private ComboBox<String> drID, drID2, vaccineID, schedule;

    @FXML
    private AnchorPane main, mainupd;

    @FXML
    private Button saveEntry, updateEntry, deleteEntry;

    int interval;
    String first_dose, second_dose;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    Calendar calendar = GregorianCalendar.getInstance();
    Schedule patient = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate ComboBoxes with doctor names and vaccine brands
        ObservableList<String> vaccines = vaccineDAO.getAllVaccineBrand();

        ObservableList<String> sched = FXCollections.observableArrayList("AM", "PM");
        vaccineID.setItems(vaccines);
        schedule.setItems(sched);

    }

    public boolean errorChecker(){
        if(patientFName.getText()==""||patientFName.getText().matches(".*[^a-z].*")||patientLName.getText()==""||
        patientLName.getText().matches(".*[^a-z].*")||cityAddress.getText()==""||cityAddress.getText().matches(".*[^a-z].*")||
        vaccineID.getValue()==null||drID.getValue()==null||schedule.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Fill in all the details and correct format");
            alert.show();
        }else{
            return true;
        }
        return false;
    }

    public ObservableList<String> getAvailableVaccinators(String date) throws ParseException {
        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] strDays = new String[]{
                "Sunday",
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thusday",
                "Friday",
                "Saturday"
        };

        return doctorDAO.getAllDoctorsByName(strDays[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
    }

    public void save(ActionEvent event) throws Exception {
        if (errorChecker()){
            patient = new Schedule(
                    drID.getValue(),
                    drID2.getValue(),
                    patientLName.getText(),
                    patientFName.getText(),
                    age.getText(),
                    sex.getText(),
                    capitalize(cityAddress.getText()),
                    vaccineID.getValue(),
                    first_dose,
                    second_dose,
                    schedule.getValue(),
                    schedule.getValue(),
                    "incomplete"
            );
            // todo: system for setting the status
            scheduleDAO.addSchedule(patient);
            vaccineDAO.updateStorageAmount(vaccineDAO.getVaccineIDByBrand(patient.getVaccineBrand()), 1);


            Stage stage = (Stage) main.getScene().getWindow();
            stage.hide();

            saveEntry.setOnAction(event1 -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_HIDDEN)));
        }
    }

    public void cancel(ActionEvent event)  {
        Stage popup = (Stage) main.getScene().getWindow();
        popup.close();
    }

    public void update(ActionEvent event) throws Exception {
        if (errorChecker()){
            Schedule updatedPatient = new Schedule(
                    patient.getPatientNum(),
                    drID.getValue(),
                    drID2.getValue(),
                    patientLName.getText(),
                    patientFName.getText(),
                    age.getText(),
                    sex.getText(),
                    capitalize(cityAddress.getText()),
                    vaccineID.getValue(),
                    firstDose.getEditor().getText(),
                    secondDose.getEditor().getText(),
                    schedule.getValue(),
                    schedule.getValue(),
                    patient.getStatus()
            );
            scheduleDAO.updateSchedule(updatedPatient);
            if (!patient.getVaccineBrand().equals(updatedPatient.getVaccineBrand())){
                vaccineDAO.updateStorageAmount(vaccineDAO.getVaccineIDByBrand(patient.getVaccineBrand()), -1);
                vaccineDAO.updateStorageAmount(vaccineDAO.getVaccineIDByBrand(updatedPatient.getVaccineBrand()), 1);
            }
            System.out.println(updatedPatient.getFirstDose());
            Stage stage = (Stage) main.getScene().getWindow();
            stage.hide();

            updateEntry.setOnAction(event1 -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_HIDDEN)));
        }
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

    public void showSecondDose(ActionEvent event) throws ParseException {
        first_dose = firstDose.getEditor().getText();
        interval = vaccineDAO.getDosageIntervalsByBrand(vaccineID.getValue());
        try {
            if (event.getSource() == firstDose)
                calendar.setTime(sdf.parse(first_dose));

        calendar.add(Calendar.DAY_OF_MONTH, interval);
        second_dose = sdf.format(calendar.getTime());
        drID2.setItems(getAvailableVaccinators(second_dose));
        secondDose.getEditor().setText(second_dose);

        calendar.add(Calendar.DAY_OF_MONTH, -interval);
        first_dose = sdf.format(calendar.getTime());
        drID.setItems(getAvailableVaccinators(first_dose));
        firstDose.getEditor().setText(first_dose);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setFieldContent(int patientID) {
        patient = scheduleDAO.getPatientByPatientID(patientID);
        drID.setValue(patient.getDoctorName_1());
        drID2.setValue(patient.getDoctorName_2());
        patientFName.setText(patient.getPatientFName());
        patientLName.setText(patient.getPatientLName());
        age.setText(patient.getAge());
        sex.setText(patient.getSex());
        vaccineID.setValue(patient.getVaccineBrand());
        firstDose.getEditor().setText(patient.getFirstDose());
        secondDose.getEditor().setText(patient.getSecondDose());
        schedule.setValue(patient.getFirstTime());
        cityAddress.setText(patient.getCity());

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

    public static String capitalize(String str) {
        if(str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public void setAnchorVisible(boolean setVisible){
        if (setVisible){
            mainupd.setDisable(false);
            mainupd.setVisible(true);
            main.setDisable(true);
            main.setVisible(false);
        } else {
            mainupd.setDisable(true);
            mainupd.setVisible(false);
            main.setDisable(false);
            main.setVisible(true);
        }

    }
}
