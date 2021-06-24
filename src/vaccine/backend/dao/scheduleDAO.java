package vaccine.backend.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import vaccine.backend.classes.Schedule;
import vaccine.backend.util.SqliteDBCon;

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
            ps = conn.prepareStatement("SELECT * FROM schedule_info, vaccine_info, doctor_info " +
                    "WHERE vacID = vaccineID " +
                    "and schedule_info.doctorID = doctor_info.drID");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(
                        new Schedule(rs.getInt("patientID"),
                                rs.getString("drName"),
                                rs.getString("patientLName"),
                                rs.getString("patientFName"),
                                rs.getString("vaccineBrand"),
                                rs.getString("firstDose"),
                                rs.getString("secondDose"),
                                rs.getString("firstTime"),
                                rs.getString("secondTime"),
                                rs.getString("status"))
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
        return list;
    }
    public static ObservableList<Schedule> getPatientByDoctor(int doctorNum) {
        ObservableList<Schedule> list = FXCollections.observableArrayList();
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("SELECT * FROM schedule_info, vaccine_info, doctor_info " +
                    "WHERE vacID = vaccineID " +
                    "and schedule_info.doctorID = doctor_info.drID " +
                    "and schedule_info.doctorID = ?");
            ps.setInt(1, doctorNum);
            rs = ps.executeQuery();
            while(rs.next()) {
                list.add(
                        new Schedule(rs.getInt("patientID"),
                                rs.getString("drName"),
                                rs.getString("patientLName"),
                                rs.getString("patientFName"),
                                rs.getString("vaccineBrand"),
                                rs.getString("firstDose"),
                                rs.getString("secondDose"),
                                rs.getString("firstTime"),
                                rs.getString("secondTime"),
                                rs.getString("status"))
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
        return list;
    }

    public static Schedule getPatientByPatientID(int patientNum) {
        Schedule schedule = null;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("SELECT * FROM schedule_info, vaccine_info, doctor_info " +
                    "WHERE vacID = vaccineID " +
                    "and schedule_info.doctorID = doctor_info.drID " +
                    "and schedule_info.patientID = ?");
            ps.setInt(1, patientNum);
            rs = ps.executeQuery();
            while(rs.next()) {
                schedule = new Schedule(
                        rs.getInt("patientID"),
                        rs.getString("drName"),
                        rs.getString("patientLName"),
                        rs.getString("patientFName"),
                        rs.getString("vaccineBrand"),
                        rs.getString("firstDose"),
                        rs.getString("secondDose"),
                        rs.getString("firstTime"),
                        rs.getString("secondTime"),
                        rs.getString("status")
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
        return schedule;
    }

    public static ObservableList<Schedule> getCurrentSchedule(String date) {
        ObservableList<Schedule> list = FXCollections.observableArrayList();
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("SELECT * FROM schedule_info, vaccine_info, doctor_info  " +
                    "WHERE vacID = vaccineID " +
                    "and schedule_info.doctorID = doctor_info.drID " +
                    "and firstTime = ? or secondTime = ?");
            ps.setString(1,date);
            ps.setString(2,date);
            rs = ps.executeQuery();
            while(rs.next()) {
                list.add(
                        new Schedule(rs.getInt("patientID"),
                                rs.getString("drName"),
                                rs.getString("patientLName"),
                                rs.getString("patientFName"),
                                rs.getString("vaccineBrand"),
                                rs.getString("firstDose"),
                                rs.getString("secondDose"),
                                rs.getString("firstTime"),
                                rs.getString("secondTime"),
                                rs.getString("status"))
                );
            }
            conn.close();
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("An error occurred.");
            alert.setContentText("Error: " + exception + "\nPlease contact the Administrator for more info.");
            alert.showAndWait();
            return null;
        }
        return list;
    }

    public static int getPatientCountBasedOnVaccineID(int vaccineID) {
        int count = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("SELECT SUM(CASE WHEN vacID = ? THEN 1 ELSE 0 END) AS [Total Patients] FROM schedule_info");
            ps.setInt(1, vaccineID);
            rs = ps.executeQuery();
            count = rs.getInt("Total Patients");
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return count;
    }

    public static int addSchedule(Schedule u) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("insert into schedule_info " +
                    "(doctorID, vacID, patientLName, patientFName, " +
                    "firstDose, secondDose, firstTime, secondTime, status) " +
                    "values (?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, doctorDAO.getDoctorIDByDoctorName(u.getDoctorName()));
            ps.setInt(2, vaccineDAO.getVaccineIDByBrand(u.getVaccineBrand()));
            ps.setString(3, u.getPatientLName());
            ps.setString(4, u.getPatientFName());
            ps.setString(5, u.getFirstDose());
            ps.setString(6, u.getSecondDose());
            ps.setString(7, u.getFirstTime());
            ps.setString(8, u.getSecondTime());
            ps.setString(9, u.getStatus());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public static int updateSchedule (Schedule u) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("update schedule_info " +
                    "set doctorID = ?, vacID = ?, " +
                    "patientLName = ?, patientFName =?, " +
                    "firstDose = ?, secondDose = ?, " +
                    "firstTime = ?, secondTime = ?, status = ? " +
                    "where patientID = ?");
            ps.setInt(1, doctorDAO.getDoctorIDByDoctorName(u.getDoctorName()));
            ps.setInt(2, vaccineDAO.getVaccineIDByBrand(u.getVaccineBrand()));
            ps.setString(3, u.getPatientLName());
            ps.setString(4, u.getPatientFName());
            ps.setString(5, u.getFirstDose());
            ps.setString(6, u.getSecondDose());
            ps.setString(7, u.getFirstTime());
            ps.setString(8, u.getSecondTime());
            ps.setString(9, u.getStatus());
            ps.setInt(10, u.getPatientNum());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return status;
    }

    public static int deleteSchedule (Schedule u) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("delete from schedule_info where patientID = ?");
            ps.setInt(1,u.getPatientNum());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return status;
    }

}
