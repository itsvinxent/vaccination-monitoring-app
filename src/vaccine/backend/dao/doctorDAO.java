package vaccine.backend.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import vaccine.backend.classes.Doctor;
import vaccine.backend.classes.Staff;
import vaccine.backend.util.SqliteDBCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Java Class that contains methods used for CRUD functionalities
 * involving the doctor_info relation in the database.
 */
public class doctorDAO {
    static Connection conn = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;

    /**
     * Inserts a new tuple in doctor_info table.
     * @param d Doctor Bean object
     * @return status - If status > 0, SQL query is successful.
     */
    public static int addDoctor (Doctor d){
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("insert into doctor_info(userID, drName, schedule) VALUES (?,?,?)");
            ps.setInt(1, d.getUserNum());
            ps.setString(2, d.getDoctorName());
            ps.setString(3, d.getSchedule());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return status;
    }

    /**
     * Updates attributes of an existing tuple in doctor_info table.
     * @param d Doctor Bean Object
     * @return status - If status > 0, SQL query is successful.
     */
    public static int updateDoctor (Doctor d) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("update doctor_info " +
                    "set userID = ?, drName = ?, schedule = ? " +
                    "where drID = ?");
            ps.setInt(1, d.getUserNum());
            ps.setString(2, d.getDoctorName());
            ps.setString(3, d.getSchedule());
            ps.setInt(4, d.getDoctorNum());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return status;
    }

    /**
     * Deletes a tuple from doctor_info table.
     * @param d Doctor Bean object
     * @return status - If status > 0, SQL query is successful.
     */
    public static int deleteDoctor (Doctor d) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("delete from doctor_info where drID=?");
            ps.setInt(1, d.getDoctorNum());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return status;
    }

    /**
     * Method used to fetch all doctor's names in doctor_info table.
     * @return ObservableList of Doctor Bean object/s.
     */
    public static ObservableList<String> getAllDoctorsByName() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("select drName from doctor_info");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("drName"));
            }
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        return list;
    }

    /**
     * Method used to get a Doctor's Name based on the given userID
     * @param userID
     * @return String - contains the doctor's name
     */
    public static String getDoctorNameByUserID(int userID) {
        String name = null;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("select drName from doctor_info where userID = ?");
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            while (rs.next()) {
                name = rs.getString("drName");
            }
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        return name;
    }

    /**
     * Method used to get a drID value based on a given name
     * @param drName
     * @return int - contains the drID
     */
    public static int getDoctorIDByDoctorName(String drName) {
        int id = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("select drID from doctor_info where drName = ?");
            ps.setString(1, drName);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("drID");
            }
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return id;
    }

    public static Doctor getDoctorByUserID(int userID) {
        Doctor doctor = null;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("SELECT * FROM doctor_info WHERE doctor_info.userID = ? ");
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            while(rs.next()) {
                doctor = new Doctor(
                        rs.getInt("userID"),
                        rs.getInt("drID"),
                        rs.getString("drName"),
                        rs.getString("schedule")
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
        return doctor;
    }
}
