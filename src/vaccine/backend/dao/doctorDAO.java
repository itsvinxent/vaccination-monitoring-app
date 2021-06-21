package vaccine.backend.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vaccine.backend.classes.Doctor;
import vaccine.backend.util.SqliteDBCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class doctorDAO {
    static Connection conn = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;

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
}
