package vaccine.frontend.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.scene.control.Label;
import vaccine.backend.classes.Vaccine;
import vaccine.backend.dao.graphDAO;
import vaccine.backend.dao.vaccineDAO;

public class graphController implements Initializable {
    @FXML
    private PieChart statusG, vaccineG;

    @FXML
    private Label totalP, incP, firstP, secondP, vaccineL;

    int totalPatients, totalVaccines;
    int inc, first, second;
    int startY = 490;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeStatusGraph();
        initializeVaccineGraph();
        initializeLabel();
    }

    public void initializeStatusGraph() {
        ObservableList<PieChart.Data> statusGraphData = FXCollections.observableArrayList();
        first = graphDAO.getPatientCountBasedOnStatus("partial");
        second = graphDAO.getPatientCountBasedOnStatus("full");
        inc = graphDAO.getPatientCountBasedOnStatus("incomplete");

        if (first != 0)
            statusGraphData.add(new PieChart.Data("First Dose", graphDAO.getPatientCountBasedOnStatus("partial")));
        if (second != 0)
            statusGraphData.add(new PieChart.Data("Second Dose", graphDAO.getPatientCountBasedOnStatus("full")));
        if (inc != 0)
            statusGraphData.add(new PieChart.Data("Incomplete", graphDAO.getPatientCountBasedOnStatus("incomplete")));

        statusG.setData(statusGraphData);
        statusG.setTitle("Patient Count per Vaccination Status");

        totalPatients= statusG.getData().stream().mapToInt(data -> (int) data.getPieValue()).sum();

        statusG.getData().forEach(data -> {
            DecimalFormat format = new DecimalFormat("0.#");
            String p = format.format((data.getPieValue()/totalPatients) * 100);

            data.setName(data.getName()+ " " + p + "%");

        });
    }

    public void initializeVaccineGraph() {
        ObservableList<PieChart.Data> vaccineGraphData = FXCollections.observableArrayList();
        ObservableList<Vaccine> vaccines = vaccineDAO.getAllVaccine();
        for (Vaccine v: vaccines) {
            vaccineGraphData.add(
                    new PieChart.Data(v.getVaccineBrand(), graphDAO.getPatientCountBasedOnVaccineID(v.getVaccineID()))
            );
        }

        vaccineG.setData(vaccineGraphData);
        vaccineG.setTitle("Patient Count per Vaccine Brand");

        totalVaccines = vaccineG.getData().stream().mapToInt(data -> (int) data.getPieValue()).sum();
        vaccineG.getData().forEach(data -> {
            String vac = data.getName();
            DecimalFormat format = new DecimalFormat("0.#");
            String p = format.format((data.getPieValue()/totalVaccines) * 100);
            data.setName(vac + " " + p + "%");
            vaccineL.setText(vaccineL.getText() + vac +": "+format.format(data.getPieValue())+"\n");
        });
    }

    public void initializeLabel() {
        startY = 490;
        int diff = 20;
        totalP.setText(String.valueOf(totalPatients));
        if (inc != 0) {
            incP.setVisible(true);
            incP.setText(incP.getText()+ inc);
            incP.setLayoutY(startY);
            startY+=diff;
        }
        if (first != 0) {
            firstP.setVisible(true);
            firstP.setText(firstP.getText()+ first);
            firstP.setLayoutY(startY);
            startY+=diff;
        }
        if (second != 0) {
            secondP.setVisible(true);
            secondP.setText(secondP.getText()+ second);
            secondP.setLayoutY(startY);
            startY+=diff;
        }
    }
}
