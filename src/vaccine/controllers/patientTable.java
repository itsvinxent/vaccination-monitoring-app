package vaccine.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import vaccine.classes.Account;
import vaccine.classes.Schedule;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import vaccine.classes.Vaccine;
import vaccine.dao.scheduleDAO;

public class patientTable implements Initializable {
    // Record Table
    @FXML
    private TableView<Schedule> patientT;
    @FXML
    private TableColumn<Schedule, Integer> patientNum;
    @FXML
    private TableColumn<Schedule, String> doctorNum;
    @FXML
    private TableColumn<Schedule, String> patientName;
    @FXML
    private TableColumn<Schedule, String> vaccineBrand;
    @FXML
    private TableColumn<Schedule, String> firstDose;
    @FXML
    private TableColumn<Schedule, String> secondDose;
    @FXML
    private TableColumn<Schedule, String> patientStatus;

    // Schedule Table
    @FXML
    private TableView<Schedule> schedT;
    @FXML
    private TableColumn<Schedule, String> dateCol;
    @FXML
    private TableColumn<Schedule, String> timeCol;
    @FXML
    private TableColumn<Schedule, String> patientNameCol;
    @FXML
    private TableColumn<Schedule, String> docNameCol;
    @FXML
    private TableColumn<Schedule, String> vaccineCol;
    @FXML
    private TableColumn<Schedule, String> dosageCol;

    // Account Management Table
    @FXML
    private TableView<Account> accountT;

    // Vaccine Management Table
    @FXML
    private TableView<Vaccine> vaccineT;

    // Components
    @FXML
    private AnchorPane main, menu, recordBody;
    @FXML
    private TextField searchBar;
    @FXML
    private Button addPatientButton, updatePatientButton, addAccountButton, addVaccineButton;
    @FXML
    private Button recordsButton, scheduleButton, accountMButton, vaccineInfoButton;
    @FXML
    private ComboBox<String> filterButton;
    @FXML
    private Label nameLabel, schedTitle, recordTitle, accountTitle, vaccineTitle;

    // Var
    InnerShadow innerShadow = new InnerShadow();
    ObservableList<String> filters = FXCollections.observableArrayList("Vaccine Brand", "Vaccination Status");
    ObservableList<Schedule> patients;
    String type;
    int id;

    // JAVA FX onAction Functions
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reloadRecordTable();
    }

    public void reloadRecordTable() {
        patientNum.setCellValueFactory(new PropertyValueFactory<Schedule, Integer>("patientNum"));
        doctorNum.setCellValueFactory(new PropertyValueFactory<Schedule, String>("doctorName"));
        patientName.setCellValueFactory(new PropertyValueFactory<Schedule, String>("patientName"));
        vaccineBrand.setCellValueFactory(new PropertyValueFactory<Schedule, String>("vaccineBrand"));
        firstDose.setCellValueFactory(new PropertyValueFactory<Schedule, String>("firstDose"));
        secondDose.setCellValueFactory(new PropertyValueFactory<Schedule, String>("secondDose"));
        patientStatus.setCellValueFactory(new PropertyValueFactory<Schedule, String>("status"));

//        if (type.equals("doctor"))
//            patients = scheduleDAO.getPatientByDoctor(id);
//        else
        patients = scheduleDAO.getAllPatients();
        patientT.setItems(patients);
        filterButton.setItems(filters);
        // set Row Color to complete, incomplete
        patientT.setRowFactory(tv -> new TableRow<Schedule>() {
            @Override
            protected void updateItem(Schedule item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || item.getStatus() == null)
                    setStyle("");
                else if (item.getStatus().equals("complete"))
                    setStyle("-fx-background-color: palegreen;");
                else if (item.getStatus().equals("incomplete"))
                    setStyle("-fx-background-color: palevioletred;");
                else
                    setStyle("");
            }
        });
        // by default, the table is sorted by status when it is loaded
        patientStatus.setSortType(TableColumn.SortType.DESCENDING);
        patientT.getSortOrder().add(patientStatus);
        patientT.sort();
    }

    public void reloadScheduleTable() {

    }

    public void setScheduleTable(int usertype) {
        if (usertype == 1) {
            // Set column sizes for Schedule Table when
            // logged in as a doctor
            dateCol.setPrefWidth(150);
            timeCol.setPrefWidth(150);
            patientNameCol.setPrefWidth(250);
            docNameCol.setVisible(false);
            vaccineCol.setPrefWidth(175);
            dosageCol.setPrefWidth(175);
        }
        else {
            docNameCol.setVisible(true);
            dateCol.setPrefWidth(125);
            timeCol.setPrefWidth(125);
            patientNameCol.setPrefWidth(175);
            docNameCol.setPrefWidth(175);
            vaccineCol.setPrefWidth(149);
            dosageCol.setPrefWidth(149);
        }
    }

    public void displayName(int ID, String name, String usertype) {
        String msg = null;
        type = usertype;
        id = ID;
        switch (usertype) {
            case "doctor":
                msg = "Welcome, Dr. " + name;
                addPatientButton.setDisable(true);
                addPatientButton.setOpacity(0);
                setScheduleTable(1);
                break;

            case "medstaff":
                msg = "Welcome, " + name;
                vaccineInfoButton.setDisable(false);
                vaccineInfoButton.setOpacity(1);
                setScheduleTable(0);
                break;

            case "admin":
                msg = "Welcome, Admin!";
                accountMButton.setDisable(false);
                accountMButton.setOpacity(1);
                vaccineInfoButton.setDisable(false);
                vaccineInfoButton.setOpacity(1);
                vaccineInfoButton.setLayoutY(332);
                setScheduleTable(0);
                break;
        }

        nameLabel.setText(msg);
    }

    public void addPopUp(ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../gui/addPatient.fxml")));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            draw.popUp(root, stage, scene);

            stage.setOnHidden(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    reloadRecordTable();
                }
            });
            stage.getOnHidden().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));

        } catch (IOException exception) {
            System.out.println(exception);
        }
    }

    public void registerPopUp(ActionEvent event) throws  IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../gui/register.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        Stage current = (Stage) main.getScene().getWindow();
        stage.initOwner(current);
        stage.initModality(Modality.APPLICATION_MODAL);

        draw.screen(root, stage, scene, null);

    }

    public void vaccinePopUp(ActionEvent event) throws  IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../gui/addVaccine.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        Stage current = (Stage) main.getScene().getWindow();
        stage.initOwner(current);
        stage.initModality(Modality.APPLICATION_MODAL);

        draw.screen(root, stage, scene, null);

    }

    public void switchtoSchedule(ActionEvent event) throws IOException {
        schedTitle.setOpacity(1);
        recordTitle.setOpacity(0);
        accountTitle.setOpacity(0);
        vaccineTitle.setOpacity(0);

        patientT.setOpacity(0);
        patientT.setDisable(true);
        schedT.setOpacity(1);
        schedT.setDisable(false);
        accountT.setOpacity(0);
        accountT.setDisable(true);
        vaccineT.setOpacity(0);
        vaccineT.setDisable(true);

        recordsButton.setEffect(null);
        scheduleButton.setEffect(innerShadow);
        accountMButton.setEffect(null);
        vaccineInfoButton.setEffect(null);

        updatePatientButton.setOpacity(1);
        updatePatientButton.setDisable(false);
        addPatientButton.setOpacity(0);
        addPatientButton.setDisable(true);
        filterButton.setOpacity(1);
        filterButton.setDisable(false);
        addAccountButton.setOpacity(0);
        addAccountButton.setDisable(true);
        addVaccineButton.setOpacity(0);
        addVaccineButton.setDisable(true);

        // Adjust other components
        searchBar.setPromptText("Search by Patient Name or Patient ID");
        searchBar.setLayoutX(65);
        updatePatientButton.setLayoutX(755);
        filterButton.setLayoutX(865);
    }

    public void switchtoRecords(ActionEvent event) {

        schedTitle.setOpacity(0);
        recordTitle.setOpacity(1);
        accountTitle.setOpacity(0);
        vaccineTitle.setOpacity(0);
        // Show record display components

        patientT.setOpacity(1);
        patientT.setDisable(false);
        schedT.setOpacity(0);
        schedT.setDisable(true);
        accountT.setOpacity(0);
        accountT.setDisable(true);
        vaccineT.setOpacity(0);
        vaccineT.setDisable(true);

        scheduleButton.setEffect(null);
        recordsButton.setEffect(innerShadow);
        accountMButton.setEffect(null);
        vaccineInfoButton.setEffect(null);

        if (type.equals("doctor")){
            addPatientButton.setOpacity(0);
            addPatientButton.setDisable(true);
        }else{
            addPatientButton.setOpacity(1);
            addPatientButton.setDisable(false);
        }
        updatePatientButton.setOpacity(0);
        updatePatientButton.setDisable(true);
        filterButton.setOpacity(1);
        filterButton.setDisable(false);
        addAccountButton.setOpacity(0);
        addAccountButton.setDisable(true);
        addVaccineButton.setOpacity(0);
        addVaccineButton.setDisable(true);

        // Adjust other components
        searchBar.setPromptText("Search by Patient Name or Patient ID");
        searchBar.setLayoutX(25);
        filterButton.setLayoutX(900);
    }

    public void switchtoAccountManagement(ActionEvent event) {
        schedTitle.setOpacity(0);
        recordTitle.setOpacity(0);
        accountTitle.setOpacity(1);
        vaccineTitle.setOpacity(0);

        patientT.setOpacity(0);
        patientT.setDisable(true);
        schedT.setOpacity(0);
        schedT.setDisable(true);
        accountT.setOpacity(1);
        accountT.setDisable(false);
        vaccineT.setOpacity(0);
        vaccineT.setDisable(true);

        scheduleButton.setEffect(null);
        recordsButton.setEffect(null);
        accountMButton.setEffect(innerShadow);
        vaccineInfoButton.setEffect(null);

        addPatientButton.setOpacity(0);
        addPatientButton.setDisable(true);
        updatePatientButton.setDisable(true);
        updatePatientButton.setOpacity(0);
        filterButton.setOpacity(1);
        filterButton.setDisable(false);
        addAccountButton.setOpacity(1);
        addAccountButton.setDisable(false);
        addVaccineButton.setOpacity(0);
        addVaccineButton.setDisable(true);

        searchBar.setPromptText("Search by Username or User ID");
        searchBar.setLayoutX(65);
        filterButton.setLayoutX(865);

    }

    public void switchtoVaccine(ActionEvent event) {
        schedTitle.setOpacity(0);
        recordTitle.setOpacity(0);
        accountTitle.setOpacity(0);
        vaccineTitle.setOpacity(1);

        patientT.setOpacity(0);
        patientT.setDisable(true);
        schedT.setOpacity(0);
        schedT.setDisable(true);
        accountT.setOpacity(0);
        accountT.setDisable(true);
        vaccineT.setOpacity(1);
        vaccineT.setDisable(false);

        scheduleButton.setEffect(null);
        recordsButton.setEffect(null);
        accountMButton.setEffect(null);
        vaccineInfoButton.setEffect(innerShadow);

        addPatientButton.setOpacity(0);
        addPatientButton.setDisable(true);
        updatePatientButton.setDisable(true);
        updatePatientButton.setOpacity(0);
        filterButton.setOpacity(1);
        filterButton.setDisable(false);
        addAccountButton.setOpacity(0);
        addAccountButton.setDisable(true);
        addVaccineButton.setOpacity(1);
        addVaccineButton.setDisable(false);

        searchBar.setPromptText("Search by Username or User ID");
        searchBar.setLayoutX(65);
        filterButton.setLayoutX(865);

    }

    public void logout() throws IOException {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to logout?");
            alert.setTitle("Confirm");
            Optional <ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/login.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                draw.screen(root, stage, scene, null);

                Stage current = (Stage) main.getScene().getWindow();
                current.close();
            }
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

//    public void search(ActionEvent event) {
//
//        ObservableList<Schedule> temp = FXCollections.observableArrayList();
//        patients.forEach((rec) -> {
//            String name = rec.getPatientName();
//            String input = searchBar.getText();
//            System.out.println(name);
//            System.out.println(input);
//
//            if (name.contains(input))
//                temp.add(rec);
//        });
//        patients.removeAll();
//        patients = temp;
//        patientT.getItems().clear();
//        reloadTable();
//        searchButton.setOpacity(0);
//        searchButton.setDisable(true);
//        clear.setOpacity(1);
//        clear.setDisable(false);
//    }
//
//    public void clear(ActionEvent event) {
//        patientT.getItems().clear();
//        reloadTable();
//        searchButton.setOpacity(1);
//        searchButton.setDisable(false);
//        clear.setOpacity(0);
//        clear.setDisable(true);
//    }

}
