package vaccine.frontend.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import vaccine.backend.classes.Account;
import vaccine.backend.classes.Schedule;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import vaccine.backend.classes.Vaccine;
import vaccine.backend.dao.scheduleDAO;
import vaccine.backend.dao.vaccineDAO;
import vaccine.backend.dao.accountDAO;


/**
 * JavaFx Class Controller for dashboard.fxml
 */
public class patientTable implements Initializable {
    // Record Table
    @FXML
    private TableView<Schedule> patientT;
    @FXML
    private TableColumn<Schedule, Integer> patientNum;
    @FXML
    private TableColumn<Schedule, String> doctorName;
    @FXML
    private TableColumn<Schedule, String> doctorName1;
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
    @FXML
    private TableColumn<Account, String> userIDCol;
    @FXML
    private TableColumn<Account, String> usernameCol;
    @FXML
    private TableColumn<Account, String> passwordCol;
    @FXML
    private TableColumn<Account, String> usertypeCol;

    // Vaccine Management Table
    @FXML
    private TableView<Vaccine> vaccineT;
    @FXML
    private TableColumn<Vaccine, String> vaccineIDCol;
    @FXML
    private TableColumn<Vaccine, String> vaccineBrandCol;
    @FXML
    private TableColumn<Vaccine, String> storageAmountCol;
    @FXML
    private TableColumn<Vaccine, String> doseIntervalCol;

    // Components
    @FXML
    private AnchorPane main;
    @FXML
    private TextField searchBar;
    @FXML
    private Button addPatientButton, updatePatientButton, addAccountButton, addVaccineButton, reloadButton;
    @FXML
    private Button recordsButton, scheduleButton, accountMButton, vaccineInfoButton;
    @FXML
    private Label nameLabel, schedTitle, recordTitle, accountTitle, vaccineTitle;

    // Var
    static InnerShadow innerShadow = new InnerShadow();
    static ObservableList<Schedule> patients;
    static ObservableList<Schedule> schedules;
    static ObservableList<Vaccine> vaccines;
    static ObservableList<Account> accounts;
    static String type, _name;
    static int id;
    static Schedule selectedSchedule;

    static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    static Calendar calendar = GregorianCalendar.getInstance();
    static String currentDate = sdf.format(calendar.getTime());

    // JAVA FX onAction Functions
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reloadRecordTable();
        reloadScheduleTable();
        reloadVaccineTable();
        reloadStaffTable();
    }

    /**
     * Reloads the Patient Records table.
     * Sorts the values and the row colors based on the status.
     */
    public void reloadRecordTable() {
        // Initializes the data source for each column
        patientNum.setCellValueFactory(new PropertyValueFactory<Schedule, Integer>("patientNum"));
        doctorName.setCellValueFactory(new PropertyValueFactory<Schedule, String>("doctorName_1"));
        doctorName1.setCellValueFactory(new PropertyValueFactory<Schedule, String>("doctorName_2"));
        patientName.setCellValueFactory(new PropertyValueFactory<Schedule, String>("patientName"));
        vaccineBrand.setCellValueFactory(new PropertyValueFactory<Schedule, String>("vaccineBrand"));
        firstDose.setCellValueFactory(new PropertyValueFactory<Schedule, String>("firstDose"));
        secondDose.setCellValueFactory(new PropertyValueFactory<Schedule, String>("secondDose"));
        patientStatus.setCellValueFactory(new PropertyValueFactory<Schedule, String>("status"));
        // Sets the ObservableList that contains the records.
        patientT.setItems(patients);
        // set Row Color to complete, incomplete
        patientT.setRowFactory(tv -> new TableRow<Schedule>() {
            @Override
            protected void updateItem(Schedule item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || item.getStatus() == null)
                    setStyle("");
                else if (item.getStatus().equals("full"))
                    setStyle("-fx-background-color: palegreen;");
                else if (item.getStatus().equals("incomplete"))
                    setStyle("-fx-background-color: palevioletred;");
                else if (item.getStatus().equals("partial"))
                    setStyle("-fx-background-color: #79A8D8");
                else
                    setStyle("");
            }
        });
        // by default, the table is sorted by status when it is loaded
        sortTable(patientT, patientStatus, TableColumn.SortType.DESCENDING);
    }

    public void reloadVaccineTable() {
        // Initializes the data source for each column
        vaccineIDCol.setCellValueFactory(new PropertyValueFactory<Vaccine, String>("vaccineID"));
        vaccineBrandCol.setCellValueFactory(new PropertyValueFactory<Vaccine, String>("vaccineBrand"));
        storageAmountCol.setCellValueFactory(new PropertyValueFactory<Vaccine, String>("storageAmount"));
        doseIntervalCol.setCellValueFactory(new PropertyValueFactory<Vaccine, String>("doseInterval"));
        // Sets the ObservableList that contains the records.
        vaccines = vaccineDAO.getAllVaccine();
        vaccineT.setItems(vaccines);

        sortTable(vaccineT, vaccineIDCol, TableColumn.SortType.ASCENDING);
    }

    public void reloadStaffTable() {
        // Initializes the data source for each column
        userIDCol.setCellValueFactory(new PropertyValueFactory<Account, String>("userID"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<Account, String>("username"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<Account, String>("password"));
        usertypeCol.setCellValueFactory(new PropertyValueFactory<Account, String>("usertype"));
        // Sets the ObservableList that contains the records.
        accounts = accountDAO.getAllAccounts();
        accountT.setItems(accounts);

        sortTable(accountT, userIDCol, TableColumn.SortType.ASCENDING);
    }

    public void reloadScheduleTable() {
        timeCol.setCellValueFactory(new PropertyValueFactory<Schedule, String>("firstTime"));
        patientNameCol.setCellValueFactory(new PropertyValueFactory<Schedule, String>("patientName"));
        docNameCol.setCellValueFactory(new PropertyValueFactory<Schedule, String>("doctorName_1"));
        vaccineCol.setCellValueFactory(new PropertyValueFactory<Schedule, String>("vaccineBrand"));
        dosageCol.setCellValueFactory(new PropertyValueFactory<Schedule, String>("status"));

        schedT.setItems(schedules);
        sortTable(schedT, dosageCol, TableColumn.SortType.DESCENDING);
    }

    public void sortTable(TableView t, TableColumn c, TableColumn.SortType s) {
        c.setSortType(s);
        t.getSortOrder().add(c);
        t.sort();
    }

    /**
     * Sets the visibility of the Assigned Doctor column.
     * @param displayAll If set to True, displays all columns of the table.
     *                   If False, hides the Assigned Doctor column.
     *                   Set to True if the usertype of the person logged-in is a Doctor.
     */
    public void setTableColumnSizes(boolean displayAll) {
        if (!displayAll) {

            timeCol.setPrefWidth(200);
            patientNameCol.setPrefWidth(250);
            docNameCol.setVisible(false);
            vaccineCol.setPrefWidth(200);
            dosageCol.setPrefWidth(200);

            patientT.setPrefWidth(900);
            patientT.setLayoutX(65);
            patientNum.setPrefWidth(125);
            doctorName.setVisible(false);
            doctorName1.setVisible(false);
            patientName.setPrefWidth(175);
            vaccineBrand.setPrefWidth(150);
            firstDose.setPrefWidth(149);
            secondDose.setPrefWidth(149);
            patientStatus.setPrefWidth(149);
        }
        else {
            docNameCol.setVisible(true);

            timeCol.setPrefWidth(150);
            patientNameCol.setPrefWidth(200);
            docNameCol.setPrefWidth(200);
            vaccineCol.setPrefWidth(174);
            dosageCol.setPrefWidth(174);

            patientT.setPrefWidth(980);
            patientT.setLayoutX(25);
            doctorName.setVisible(true);
            doctorName1.setVisible(true);
            patientNum.setPrefWidth(40);
            doctorName.setPrefWidth(150);
            doctorName1.setPrefWidth(150);
            patientName.setPrefWidth(150);

            vaccineBrand.setPrefWidth(125);
            firstDose.setPrefWidth(125);
            secondDose.setPrefWidth(125);
            patientStatus.setPrefWidth(112);
        }
    }

    /**
     * Initializes the contents of all the UI Components based on the usertype.
     * @param ID userID of the user (int)
     * @param name Full name of the user (String)
     * @param usertype Usertype of the user (String)
     */
    public void displayName(int ID, String name, String usertype) {
        String msg = null;
        _name = name;
        type = usertype;
        id = ID;
        switch (type) {
            case "vaccinator":
                msg = "Welcome, " + name;
                addPatientButton.setDisable(true);
                addPatientButton.setOpacity(0);
                setTableColumnSizes(false);
                searchBar.setLayoutX(65);
                reloadButton.setLayoutX(865);
                patients = scheduleDAO.getPatientByDoctor(id);
                schedules = scheduleDAO.getCurrentScheduleByDoctor(currentDate, id);
                break;

            case "medical staff":
                msg = "Welcome, " + name;
                vaccineInfoButton.setDisable(false);
                vaccineInfoButton.setOpacity(1);
                setTableColumnSizes(true);
                patients = scheduleDAO.getAllPatients();
                schedules = scheduleDAO.getCurrentSchedule(currentDate);
                break;

            default:
                msg = "Welcome, Admin!";
                accountMButton.setDisable(false);
                accountMButton.setOpacity(1);
                vaccineInfoButton.setDisable(false);
                vaccineInfoButton.setOpacity(1);
                vaccineInfoButton.setLayoutY(332);
                setTableColumnSizes(true);
                patients = scheduleDAO.getAllPatients();
                schedules = scheduleDAO.getCurrentSchedule(currentDate);
                break;
        }
        nameLabel.setText(msg);
        reloadRecordTable();
        reloadScheduleTable();
        reloadStaffTable();
        reloadVaccineTable();
    }

    /**
     * Draws the pop-up window used for creating new Patient records
     * USAGE: Click the +Add Patient button in the Patient Records tab.
     * @param event
     * @throws IOException
     */
    public void addPopUp(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../gui/addPatient.fxml")));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            Stage current = (Stage) main.getScene().getWindow();
            stage.initOwner(current);

            draw.popUp(root, stage, scene);
            addPatient addPatient = loader.getController();
            addPatient.setAnchorVisible(false);

            stage.setOnHidden(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    displayName(id,_name,type);
                }
            });
            stage.getOnHidden().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Draws the pop-up window used for creating new User Accounts
     * USAGE: Click the +Add Account button in the Account Management tab.
     * @param event
     * @throws IOException
     */
    public void registerPopUp(ActionEvent event) throws  IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../gui/register.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        Stage current = (Stage) main.getScene().getWindow();
        stage.initOwner(current);

        draw.popUp(root, stage, scene);

        stage.setOnHidden(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                reloadStaffTable();
            }
        });

    }

    /**
     * Draws the pop-up window used for creating new Vaccine records
     * USAGE: Click the +Add Vaccine button in the Vaccine Information tab.
     * @param event
     * @throws IOException
     */
    public void vaccinePopUp(ActionEvent event) throws  IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../gui/addVaccine.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        Stage current = (Stage) main.getScene().getWindow();
        stage.initOwner(current);

        draw.popUp(root, stage, scene);

        stage.setOnHidden(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                reloadVaccineTable();
            }
        });

    }

    /**
     * Draws the pop-up window used for updating existing Patient Records in the table.
     * USAGE: Double-Click a row inside the Patient Records Table.
     * @param event
     * @throws IOException
     */
    public void updatePopUp(MouseEvent event) throws IOException {
        try {
            if (patientT.getSelectionModel().getSelectedItem() != null) {
                if (event.getClickCount() == 2 && (!type.equals("vaccinator"))) {
                    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../gui/addPatient.fxml")));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    Stage current = (Stage) main.getScene().getWindow();
                    stage.initOwner(current);

                    draw.popUp(root, stage, scene);
                    addPatient addPatient = loader.getController();
                    addPatient.setFieldContent(patientT.getSelectionModel().getSelectedItem().getPatientNum());
                    stage.setOnHidden(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {
                            displayName(id, _name, type);
                        }
                    });
                    stage.getOnHidden().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                }
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void vaccineUpdatePopUp(MouseEvent event) throws IOException {
        try {
            if (event.getClickCount() == 2 && vaccineT.getSelectionModel().getSelectedItem() != null){
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../gui/addVaccine.fxml")));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                Stage current = (Stage) main.getScene().getWindow();
                stage.initOwner(current);

                draw.popUp(root, stage, scene);
                addVaccine addVaccine = loader.getController();
                addVaccine.setFieldContent(vaccineT.getSelectionModel().getSelectedItem().getVaccineID());
                stage.setOnHidden(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent we) {
                        displayName(id,_name,type);
                    }
                });
                stage.getOnHidden().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            }


        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void staffUpdatePopUp(MouseEvent event) throws IOException {
        try {
            if (event.getClickCount() == 2 && accountT.getSelectionModel().getSelectedItem() != null){
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../gui/register.fxml")));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                Stage current = (Stage) main.getScene().getWindow();
                stage.initOwner(current);

                draw.popUp(root, stage, scene);
                registerController registerController = loader.getController();
                registerController.setFieldContent(accountT.getSelectionModel().getSelectedItem().getUserID());
                stage.setOnHidden(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent we) {
                        displayName(id,_name,type);
                    }
                });
                stage.getOnHidden().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            }


        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void getSchedule(MouseEvent event) throws IOException {
        if (schedT.getSelectionModel().getSelectedItem() != null) {
            if (event.getClickCount() == 2 && (!type.equals("vaccinator"))) {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../gui/addPatient.fxml")));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                Stage current = (Stage) main.getScene().getWindow();
                stage.initOwner(current);

                draw.popUp(root, stage, scene);
                addPatient addPatient = loader.getController();
                addPatient.setAnchorVisible(true);
                addPatient.setUpdateStatusFields(schedT.getSelectionModel().getSelectedItem().getPatientNum());
                stage.setOnHidden(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent we) {
                        displayName(id,_name,type);
                    }
                });
                stage.getOnHidden().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            } else {
                if (type.equals("vaccinator")){
                    selectedSchedule = scheduleDAO.getPatientByPatientID(
                            schedT.getSelectionModel().getSelectedItem().getPatientNum()
                    );
                    updatePatientButton.setOpacity(1);
                    updatePatientButton.setDisable(false);
                }
            }
        }


    }

    public void updateStatus(ActionEvent event) {
        String status = selectedSchedule.getStatus();
        String new_status = null;
        if (status.equals("incomplete"))
            new_status = "partial";
        else if (status.equals("partial"))
            new_status = "full";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to update the status of this record from "
                + addPatient.capitalize(status) +" to "+ addPatient.capitalize(new_status) +"?");
        alert.setTitle("Confirm");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            selectedSchedule.setStatus(new_status);
            scheduleDAO.updateSchedule(selectedSchedule);
            displayName(id,_name,type);
        }
    }

    /**
     * Clears all selected rows in all TableViews when a
     * MouseEvent.MOUSE_CLICKED event occurred outside the bounds of the Table or when clicking an empty row.
     * @param event
     */
    public void clearRowSelection(MouseEvent event) {
        // Get access to the current scene
        Scene scene = main.getScene();
        // Add an EventFilter
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
            // Get the hierarchy
            Node source = evt.getPickResult().getIntersectedNode();
            // Climb up the hierarchy until the scene/ a TableRow is found
            while (source != null && !(source instanceof TableRow)) {
                source = source.getParent();
            }
            // Now that the source Node is the scene / TableRow, clear the SelectionModel of the tables/
            if (source == null || (source instanceof TableRow && ((TableRow) source).isEmpty())) {
                patientT.getSelectionModel().clearSelection();
                schedT.getSelectionModel().clearSelection();
                vaccineT.getSelectionModel().clearSelection();
                accountT.getSelectionModel().clearSelection();
                if (type.equals("vaccinator")){
                    updatePatientButton.setOpacity(0);
                    updatePatientButton.setDisable(true);
                }
            }
        });
    }

    public void graphs(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../gui/graphs.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        Stage current = (Stage) main.getScene().getWindow();
        stage.initOwner(current);

        draw.popUp(root, stage, scene);


    }

    /**
     * Logs out the user by closing the Main Interface and drawing the Login Interface
     * @throws IOException
     */
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

                draw.screen(root, stage, scene);

                Stage current = (Stage) main.getScene().getWindow();
                current.close();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
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

        addPatientButton.setOpacity(0);
        addPatientButton.setDisable(true);
        addAccountButton.setOpacity(0);
        addAccountButton.setDisable(true);
        addVaccineButton.setOpacity(0);
        addVaccineButton.setDisable(true);

        // Adjust other components
        searchBar.setPromptText("Search by Patient Name or Patient ID");
        searchBar.setLayoutX(65);
        searchBar.clear();
        updatePatientButton.setLayoutX(755);
        reloadButton.setLayoutX(865);
        displayName(id, _name, type);
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

        if (type.equals("vaccinator")){
            addPatientButton.setOpacity(0);
            addPatientButton.setDisable(true);
        }else{
            addPatientButton.setOpacity(1);
            addPatientButton.setDisable(false);
        }
        updatePatientButton.setOpacity(0);
        updatePatientButton.setDisable(true);
        addAccountButton.setOpacity(0);
        addAccountButton.setDisable(true);
        addVaccineButton.setOpacity(0);
        addVaccineButton.setDisable(true);

        // Adjust other components
        searchBar.setPromptText("Search by Patient Name or Patient ID");
        searchBar.clear();
        if (!type.equals("vaccinator")) {
            searchBar.setLayoutX(25);
            reloadButton.setLayoutX(900);
        }
        displayName(id, _name, type);
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
        addAccountButton.setOpacity(1);
        addAccountButton.setDisable(false);
        addVaccineButton.setOpacity(0);
        addVaccineButton.setDisable(true);

        searchBar.setPromptText("Search by Username or User ID");
        searchBar.setLayoutX(65);
        searchBar.clear();
        reloadButton.setLayoutX(865);

        displayName(id, _name, type);
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
        addAccountButton.setOpacity(0);
        addAccountButton.setDisable(true);
        addVaccineButton.setOpacity(1);
        addVaccineButton.setDisable(false);

        searchBar.setPromptText("Search by Vaccine Brand or Vaccine ID");
        searchBar.setLayoutX(65);
        searchBar.clear();
        reloadButton.setLayoutX(865);

        displayName(id, _name, type);
    }

    public void search(KeyEvent event) {
        if (recordsButton.getEffect() != null) {
            FilteredList<Schedule> filteredList = new FilteredList<>(patients, b->true);
            searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(row -> {
                    if(newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowercase = newValue.toLowerCase(Locale.ROOT);

                    if(row.getPatientLName().toLowerCase(Locale.ROOT).contains(lowercase) ||
                            row.getPatientFName().toLowerCase(Locale.ROOT).contains(lowercase))
                        return true;
                    else return String.valueOf(row.getPatientNum()).contains(lowercase);
                });
            });

            SortedList<Schedule> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(patientT.comparatorProperty());
            patientT.setItems(sortedList);
        }
        else if (scheduleButton.getEffect() != null) {
            FilteredList<Schedule> filteredList = new FilteredList<>(schedules, b->true);
            searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(row -> {
                    if(newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowercase = newValue.toLowerCase(Locale.ROOT);

                    if(row.getPatientLName().toLowerCase(Locale.ROOT).contains(lowercase) ||
                            row.getPatientFName().toLowerCase(Locale.ROOT).contains(lowercase))
                        return true;
                    else return String.valueOf(row.getPatientNum()).contains(lowercase);
                });
            });

            SortedList<Schedule> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(schedT.comparatorProperty());
            schedT.setItems(sortedList);
        }
        else if (vaccineInfoButton.getEffect() != null) {
            FilteredList<Vaccine> filteredList = new FilteredList<>(vaccines, b->true);
            searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(row -> {
                    if(newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowercase = newValue.toLowerCase(Locale.ROOT);

                    if(row.getVaccineBrand().toLowerCase(Locale.ROOT).contains(lowercase))
                        return true;
                    else return String.valueOf(row.getVaccineID()).contains(lowercase);
                });
            });

            SortedList<Vaccine> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(vaccineT.comparatorProperty());
            vaccineT.setItems(sortedList);
        }
        else {
            FilteredList<Account> filteredList = new FilteredList<>(accounts, b->true);
            searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(row -> {
                    if(newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowercase = newValue.toLowerCase(Locale.ROOT);

                    if(row.getUsername().toLowerCase(Locale.ROOT).contains(lowercase))
                        return true;
                    else return String.valueOf(row.getUserID()).contains(lowercase);
                });
            });

            SortedList<Account> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(accountT.comparatorProperty());
            accountT.setItems(sortedList);
        }
    }

    public void reloadTables() {
        displayName(id, _name, type);
    }

}
