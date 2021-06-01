package vaccine.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vaccine.connect.SqliteDBCon;
import vaccine.classes.Schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class scheduleDAO {
    static Connection conn = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;
    public static ObservableList<Schedule> getAllPatients() {
        ObservableList<Schedule> list= FXCollections.observableArrayList();
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("select * from schedule_info");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(
                        new Schedule(rs.getInt("patientID"),
                                rs.getInt("doctorID"),
                                rs.getString("patientLName"),
                                rs.getString("patientFName"),
                                vaccineDAO.getVaccineBrandByID(rs.getInt("vaccineID")),
                                rs.getString("firstDose"),
                                rs.getString("secondDose"),
                                rs.getString("firstTime"),
                                rs.getString("secondTime"),
                                rs.getString("status"))
                );
            }

        } catch(Exception exception) {
            return null;
        }
        return list;
    }
    public static ObservableList<Schedule> getPatientByDoctor(int doctorNum) {
        ObservableList<Schedule> list = FXCollections.observableArrayList();
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("select * from schedule_info where doctorID=?");
            ps.setInt(1, doctorNum);
            rs=ps.executeQuery();
            while(rs.next()) {
                list.add(
                        new Schedule(rs.getInt("patientID"),
                                rs.getInt("doctorID"),
                                rs.getString("patientLName"),
                                rs.getString("patientFName"),
                                vaccineDAO.getVaccineBrandByID(rs.getInt("vaccineID")),
                                rs.getString("firstDose"),
                                rs.getString("secondDose"),
                                rs.getString("firstTime"),
                                rs.getString("secondTime"),
                                rs.getString("status"))
                );
            }

        } catch(Exception exception) {
            return null;
        }
        return list;
    }
    public static int save (Schedule u) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("insert into schedule_info (doctorID, vaccineID, patientLName, patientFName, firstDose, secondDose, firstTime, secondTime, status) values (?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, u.getDoctorNum());
            ps.setString(2, u.getVaccineBrand());
            ps.setString(3, u.getPatientLName());
            ps.setString(4, u.getPatientFName());
            ps.setString(5, u.getFirstDose());
            ps.setString(6, u.getSecondDose());
            ps.setString(7, u.getFirstTime());
            ps.setString(8, u.getSecondTime());
            ps.setString(9, u.getStatus());
            status = ps.executeUpdate();

        } catch (Exception e) {
            // To do
        }
        return status;
    }



}
