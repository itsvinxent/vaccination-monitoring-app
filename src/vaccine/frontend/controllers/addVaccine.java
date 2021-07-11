package vaccine.frontend.controllers;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.w3c.dom.Text;
import vaccine.backend.classes.Schedule;
import vaccine.backend.classes.Vaccine;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import vaccine.backend.dao.*;

public class addVaccine implements Initializable {
    @FXML
    private AnchorPane main;

    @FXML
    private TextField vaccineBrand;

    @FXML
    private TextField dosageStock;

    @FXML
    private TextField dosageInterval;

    @FXML
    private Button vaccineEntry, vaccineUpdate, vaccineDelete;

    Vaccine vaccine = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void saveVaccine(ActionEvent event) throws Exception {
        //if(vaccineBrand.getText().matches(".*[^a-z].*"))
        if(vaccineBrand.getText()==""||dosageStock.getText()==""||dosageInterval.getText()==""||
                vaccineBrand.getText().matches(".*[^a-z].*")||!dosageStock.getText().matches(".*[^a-z].*")||
                !dosageInterval.getText().matches(".*[^a-z].*")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Fill in all the details and correct format.");
            alert.show();
        }
        else{
            vaccine = new Vaccine(
                    vaccineBrand.getText(),
                    Integer.parseInt(dosageStock.getText()),
                    Integer.parseInt(dosageInterval.getText())
            );
            vaccineDAO.addVaccine(vaccine);

            Stage stage = (Stage) main.getScene().getWindow();
            stage.hide();

            vaccineEntry.setOnAction(event1 -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_HIDDEN)));
        }

    }

    public void updateVaccine(ActionEvent event) throws Exception {
        if(vaccineBrand.getText()==""||dosageStock.getText()==""||dosageInterval.getText()==""||
                vaccineBrand.getText().matches(".*[^a-z].*")||!dosageStock.getText().matches(".*[^a-z].*")||
                !dosageInterval.getText().matches(".*[^a-z].*")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Fill in all the details and correct format.");
            alert.show();
        }
        else{
            Vaccine updatedvaccine = new Vaccine(
                    vaccine.getVaccineID(),
                    vaccineBrand.getText(),
                    Integer.parseInt(dosageStock.getText()),
                    Integer.parseInt(dosageInterval.getText())
            );
            vaccineDAO.updateVaccine(updatedvaccine);

            Stage stage = (Stage) main.getScene().getWindow();
            stage.hide();

            vaccineEntry.setOnAction(event1 -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_HIDDEN)));
        }
    }

    public void deleteVaccine(ActionEvent event) throws Exception {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete this record?");
            alert.setTitle("Confirm");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                if (scheduleDAO.getPatientCountBasedOnVaccineID(vaccine.getVaccineID()) == 0){
                    vaccineDAO.deleteVaccine(vaccine);
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Error! You cannot delete vaccine records while Patient Records that uses the vaccine exists!");
                    alert.setTitle("Error");
                    alert.show();
                }

                Stage stage = (Stage) main.getScene().getWindow();
                stage.hide();

                vaccineDelete.setOnAction(event1 -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_HIDDEN)));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void cancel (ActionEvent event) throws IOException {
        Stage popup = (Stage) main.getScene().getWindow();
        popup.close();
    }

    public void setFieldContent(int vaxID) {
        vaccine = vaccineDAO.getVaccineByVaccineID(vaxID);
        vaccineBrand.setText(vaccine.getVaccineBrand());
        dosageStock.setText(Integer.toString(vaccine.getStorageAmount()));
        dosageInterval.setText(Integer.toString(vaccine.getDoseInterval()));

        vaccineUpdate.setDisable(false);
        vaccineUpdate.setVisible(true);
        vaccineEntry.setDisable(true);
        vaccineEntry.setVisible(false);
        vaccineDelete.setDisable(false);
        vaccineDelete.setVisible(true);
        vaccineDelete.setOpacity(1);
    }
}
