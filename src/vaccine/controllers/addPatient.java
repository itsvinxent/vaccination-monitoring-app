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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

    int interval;
    String first_dose, second_dose;
    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    Calendar calendar = Calendar.getInstance();

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
        Schedule patient = new Schedule(0,
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
        scheduleDAO.save(patient);

        Stage stage = (Stage) main.getScene().getWindow();
        stage.hide();

        saveEntry.setOnAction(event1 -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_HIDDEN)));
    }

    public void cancel(ActionEvent event)  {
        Stage popup = (Stage) main.getScene().getWindow();
        popup.close();
    }

    public void showSecondDose(ActionEvent event) {
        first_dose = firstDose.getEditor().getText();
        interval = vaccineDAO.getDosageIntervalsByBrand(vaccineID.getValue());
        System.out.println(vaccineID.getValue());
        System.out.println(interval);
        System.out.println(first_dose);
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
}
