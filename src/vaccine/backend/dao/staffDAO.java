package vaccine.backend.dao;

import javafx.scene.control.Alert;
import vaccine.backend.classes.Account;
import vaccine.backend.classes.Staff;
import vaccine.backend.classes.Vaccine;
import vaccine.backend.util.SqliteDBCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Java Class that contains methods used for CRUD functionalities
 * involving the staff_info relation in the database.
 */
public class staffDAO {
    static Connection conn = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;

    /**
     * Method that fetches the staffName attribute of a tuple that matches the given userID
     * @param userID
     * @return String - contains the name associated with the given userID
     */
    public static String getStaffNameByID(int userID) {
        String name = null;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("select staffName from staff_info where userID = ?");
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            while (rs.next()) {
                name = rs.getString("staffName");
            }
        } catch (Exception exception) {
            return null;
        }
        return name;
    }

    public static Staff getStaffByUserID(int userID) {
        Staff staff = null;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("SELECT * FROM staff_info WHERE staff_info.userID = ? ");
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            while(rs.next()) {
                staff = new Staff(
                        rs.getInt("userID"),
                        rs.getInt("staffID"),
                        rs.getString("staffName")
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
        return staff;
    }

    /**
     * Inserts a new tuple in staff_info table.
     * @param s Staff Bean object
     * @return status - If status > 0, SQL query is successful.
     */
    public static int addStaff (Staff s){
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("insert into staff_info(userID, staffName) VALUES (?,?)");
            ps.setInt(1, s.getUserNum());
            ps.setString(2, s.getStaffName());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return status;
    }

    /**
     * Updates attributes of an existing tuple in staff_info table.
     * @param s Staff Bean Object
     * @return status - If status > 0, SQL query is successful.
     */
    public static int updateStaff(Staff s) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("update staff_info " +
                    "set userID = ?, staffName = ? " +
                    "where staffID = ?");
            ps.setInt(1, s.getUserNum());
            ps.setString(2, s.getStaffName());
            ps.setInt(3, s.getStaffID());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return status;
    }

    /**
     * Deletes a tuple from staff_info table.
     * @param s Staff Bean object
     * @return status - If status > 0, SQL query is successful.
     */
    public static int deleteStaff (Staff s) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("delete from staff_info where staffID=?");
            ps.setInt(1, s.getStaffID());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return status;
    }

}
