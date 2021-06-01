package vaccine.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vaccine.classes.Doctor;
import vaccine.connect.SqliteDBCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class doctorDAO {
    static Connection conn = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;

    public static int createDoctor(Doctor d){
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("insert into doctor_info(userID, drName, schedule) VALUES (?,?,?)");
            ps.setInt(1, d.getDoctorNum());
            ps.setString(2, d.getDoctorName());
            ps.setInt(3, d.getSchedule());
            status = ps.executeUpdate();
        } catch (Exception exception) {
            return 0; // TODO: show a messageBox for error message
        }
        return status;
    }

    public static int deleteDoctor(Doctor d) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("delete from doctor_info where drID=?");
            ps.setInt(1, d.getDoctorNum());
            status = ps.executeUpdate();
        } catch (Exception exception) {
            return 0;
        }
        return status;
    }

    public static ObservableList<String> getAllDoctorsByName() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("select drName from doctor_info");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("drName"));
            }
        } catch (Exception exception) {
            return null;
        }
        return list;
    }

    public static String getDoctorNameByID(int userID) {
        String name = null;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("select drName from doctor_info where userID = ?");
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            while (rs.next()) {
                name = rs.getString("drName");
            }
        } catch (Exception exception) {
            return null;
        }
        return name;
    }
}
