package vaccine.backend.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import vaccine.backend.classes.Schedule;
import vaccine.backend.classes.Vaccine;
import vaccine.backend.util.SqliteDBCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Java Class that contains methods used for CRUD functionalities
 * involving the vaccine_info relation in the database.
 */
public class vaccineDAO {
    static Connection conn = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;

    /**
     * Method used for fetching all vaccineBrand from vaccine_info table.
     * @return ObservableList of Vaccine Bean object/s.
     * if null, the vaccine_info table is empty or the query failed to execute.
     */
    public static ObservableList<Vaccine> getAllVaccine() {
        ObservableList<Vaccine> list = FXCollections.observableArrayList();
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("select * from vaccine_info");
            rs = ps.executeQuery();
            while(rs.next()) {
                list.add(new Vaccine(rs.getInt("vaccineID"),
                        rs.getString("vaccineBrand"),
                        rs.getInt("storageAmount"),
                        rs.getInt("doseInterval"))
                );
            }
            conn.close();
        } catch (Exception exception) {
            return null;
        }
        return list;
    }

    public static ObservableList<String> getAllVaccineBrand() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("select vaccineBrand from vaccine_info where storageAmount != 0");
            rs = ps.executeQuery();
            while(rs.next()) {
                list.add(rs.getString("vaccineBrand"));
            }
            conn.close();
        } catch (Exception exception) {
            return null;
        }
        return list;
    }

    /**
     * Method used to fetch the vaccineID of a given vaccineBrand
     * @param vaccineBrand
     * @return int - contains vaccineID associated to the given vaccineBrand
     */
    public static int getVaccineIDByBrand(String vaccineBrand) {
        int vaccineID = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("select vaccineID from vaccine_info where vaccineBrand = ?");
            ps.setString(1, vaccineBrand);
            rs = ps.executeQuery();
            while (rs.next()) {
                vaccineID = rs.getInt("vaccineID");
            }
            conn.close();
        } catch (Exception exception) {
            return -1;
        }
        return vaccineID;
    }

    /**
     * Method used to fetch the doseInterval of a given vaccineBrand
     * @param vaccineBrand
     * @return int - contains the intervals of each dosage of the vaccines in days
     */
    public static int getDosageIntervalsByBrand(String vaccineBrand) {
        int interval = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("select doseInterval from vaccine_info where vaccineBrand = ?");
            ps.setString(1, vaccineBrand);
            rs = ps.executeQuery();
            while (rs.next()) {
                interval = rs.getInt("doseInterval");
            }
            conn.close();
        } catch (Exception exception) {
            return -1;
        }
        return interval;
    }

    public static Vaccine getVaccineByVaccineID(int vaccineID) {
        Vaccine vaccine = null;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("SELECT * FROM vaccine_info WHERE vaccine_info.vaccineID = ? ");
            ps.setInt(1, vaccineID);
            rs = ps.executeQuery();
            while(rs.next()) {
                vaccine = new Vaccine(
                        rs.getInt("vaccineID"),
                        rs.getString("vaccineBrand"),
                        rs.getInt("storageAmount"),
                        rs.getInt("doseInterval")
                );
            }
            conn.close();
        } catch(Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("An error occurred.");
            alert.setContentText("Error: " + exception + "\nPlease contact the Administrator for more info.");
            alert.showAndWait();
            return null;
        }
        return vaccine;
    }

    /**
     * Inserts a new tuple in vaccine_info table.
     * @param v Vaccine Bean object
     * @return status - If status > 0, SQL query is successful.
     */
    public static int addVaccine (Vaccine v) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("insert into vaccine_info(vaccineBrand, storageAmount, doseInterval) values (?,?,?)");
            ps.setString(1, v.getVaccineBrand());
            ps.setInt(2, v.getStorageAmount());
            ps.setInt(3, v.getDoseInterval());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            return -1;
        }
        return status;
    }

    /**
     * Updates attributes of an existing tuple in staff_info table.
     * @param v Vaccine Bean Object
     * @return status - If status > 0, SQL query is successful.
     */
    public static int updateVaccine (Vaccine v) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("update vaccine_info " +
                    "set vaccineBrand = ?, storageAmount = ?, doseInterval = ? where vaccineID = ?");
            ps.setString(1, v.getVaccineBrand());
            ps.setInt(2, v.getStorageAmount());
            ps.setInt(3, v.getDoseInterval());
            ps.setInt(4, v.getVaccineID());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            return -1;
        }
        return status;
    }

    /**
     * Deletes a tuple from vaccine_info table.
     * @param v Vaccine Bean object
     * @return status - If status > 0, SQL query is successful.
     */
    public static int deleteVaccine (Vaccine v) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("delete from vaccine_info where vaccineID = ?");
            ps.setInt(1, v.getVaccineID());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return status;
    }

    public static int updateStorageAmount(int vaccineID, int amount) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("update vaccine_info set storageAmount = storageAmount - ? " +
                    "where vaccineID = ?");
            ps.setInt(1, amount);
            ps.setInt(2, vaccineID);
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return status;
    }
}
