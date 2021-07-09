package vaccine.backend.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import vaccine.backend.classes.Schedule;
import vaccine.backend.util.SqliteDBCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

/**
 * Java Class that contains methods used for CRUD functionalities
 * involving the schedule_info relation in the database.
 */
public class scheduleDAO {
    static Connection conn = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;

    /**
     * Method used for fetching all tuples from schedule_info table.
     * @return ObservableList of Schedule Bean object/s.
     * if null, the schedule_info table is empty or the query failed to execute.
     */
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
                                rs.getString("status"),
                                rs.getString("city"))
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

    /**
     * Method used for fetching all tuples that matches the given value of the doctorID attribute.
     * @param userID value to be checked in the doctorID attribute of each tuple.
     * @return ObservableList of Schedule Bean object/s.
     * if null, the schedule_info table is empty, the query failed to execute, or no match is found.
     */
    public static ObservableList<Schedule> getPatientByDoctor(int userID) {
        ObservableList<Schedule> list = FXCollections.observableArrayList();
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("SELECT * FROM schedule_info, vaccine_info, doctor_info " +
                    "WHERE vacID = vaccineID " +
                    "and schedule_info.doctorID = doctor_info.drID " +
                    "and doctor_info.userID = ?");
            ps.setInt(1, userID);
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
                                rs.getString("status"),
                                rs.getString("city"))
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

    /**
     * Method used for fetching a specific tuple containing record information of a given patientID.
     * @param patientNum value to be checked in the patientID attribute of each tuple.
     * @return Schedule Bean object.
     * if null, the schedule_info table is empty, the query failed to execute, or no match is found.
     */
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
                        rs.getString("status"),
                        rs.getString("city")
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

    /**
     * Method used for fetching all tuples that matches the given date to firstDose or secondDose attribute.
     * @param date value to be checked in the firstTime/secondTime attribute of each tuple. (Current Date)
     * @return ObservableList of Schedule Bean object/s.
     */
    public static ObservableList<Schedule> getCurrentSchedule(String date) {
        ObservableList<Schedule> list = FXCollections.observableArrayList();
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("SELECT * FROM schedule_info WHERE firstDose = ? or secondDose = ?");
            ps.setString(1, date);
            ps.setString(2, date);
            rs = ps.executeQuery();
            while(rs.next()) {
                list.add(
                        new Schedule(rs.getInt("patientID"),
                                doctorDAO.getDoctorNameByDoctorID(rs.getInt("doctorID")),
                                rs.getString("patientLName"),
                                rs.getString("patientFName"),
                                vaccineDAO.getVaccineByVaccineID(rs.getInt("vacID")).getVaccineBrand(),
                                rs.getString("firstDose"),
                                rs.getString("secondDose"),
                                rs.getString("firstTime"),
                                rs.getString("secondTime"),
                                rs.getString("status"),
                                rs.getString("city"))
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

    /**
     * Method used for fetching all tuples that matches the given date and id to firstDose or secondDose attribute
     * and the doctorID.
     * @param date value to be checked in the firstTime/secondTime attribute of each tuple. (Current Date)
     * @param id value to be checked in the userID attribute of each tuple.
     * @return
     */
    public static ObservableList<Schedule> getCurrentScheduleByDoctor(String date, int id) {
        ObservableList<Schedule> list = FXCollections.observableArrayList();
        try {
            int doctorID = doctorDAO.getDoctorByUserID(id).getDoctorNum();
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("SELECT * FROM schedule_info " +
                    "WHERE doctorID = ? AND firstDose = ? OR secondDose = ?");
            ps.setInt(1, doctorID);
            ps.setString(2, date);
            ps.setString(3, date);
            rs = ps.executeQuery();
            while(rs.next()) {
                list.add(
                        new Schedule(rs.getInt("patientID"),
                                doctorDAO.getDoctorNameByDoctorID(rs.getInt("doctorID")),
                                rs.getString("patientLName"),
                                rs.getString("patientFName"),
                                vaccineDAO.getVaccineByVaccineID(rs.getInt("vacID")).getVaccineBrand(),
                                rs.getString("firstDose"),
                                rs.getString("secondDose"),
                                rs.getString("firstTime"),
                                rs.getString("secondTime"),
                                rs.getString("status"),
                                rs.getString("city"))
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

    /**
     * Method used to determine the number of patients that will be administered with the given vaccine.
     * @param vaccineID vaccineID of the Vaccine Brand used (getVaccineIDByBrand method in vaccineDAO class)
     * @return total count of patients
     */
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

    /**
     * Inserts a new tuple in schedule_info table.
     * @param u Schedule Bean object
     * @return status - If status > 0, SQL query is successful.
     */
    public static int addSchedule(Schedule u) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("insert into schedule_info " +
                    "(doctorID, vacID, patientLName, patientFName, " +
                    "firstDose, secondDose, firstTime, secondTime, status, city) " +
                    "values (?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, doctorDAO.getDoctorIDByDoctorName(u.getDoctorName()));
            ps.setInt(2, vaccineDAO.getVaccineIDByBrand(u.getVaccineBrand()));
            ps.setString(3, u.getPatientLName());
            ps.setString(4, u.getPatientFName());
            ps.setString(5, u.getFirstDose());
            ps.setString(6, u.getSecondDose());
            ps.setString(7, u.getFirstTime());
            ps.setString(8, u.getSecondTime());
            ps.setString(9, u.getStatus());
            ps.setString(10, u.getCity());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * Updates attributes of an existing tuple in schedule_info table.
     * @param u Schedule Bean object
     * @return status - If status > 0, SQL query is successful.
     */
    public static int updateSchedule (Schedule u) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("update schedule_info " +
                    "set doctorID = ?, vacID = ?, " +
                    "patientLName = ?, patientFName =?, " +
                    "firstDose = ?, secondDose = ?, " +
                    "firstTime = ?, secondTime = ?, status = ?, city = ?" +
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
            ps.setString(10, u.getCity());
            ps.setInt(11, u.getPatientNum());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return status;
    }

    /**
     * Deletes a tuple in schedule_info table.
     * @param u Schedule Bean object
     * @return status - If status > 0, SQL query is successful.
     */
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
