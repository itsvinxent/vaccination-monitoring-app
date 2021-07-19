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
            ps = conn.prepareStatement("SELECT * FROM schedule_info");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(
                        new Schedule(
                                rs.getInt("patientID"),
                                doctorDAO.getDoctorNameByDoctorID(rs.getInt("doctor1ID")),
                                doctorDAO.getDoctorNameByDoctorID(rs.getInt("doctor2ID")),
                                rs.getString("patientLName"),
                                rs.getString("patientFName"),
                                rs.getString("age"),
                                rs.getString("sex"),
                                rs.getString("city"),
                                vaccineDAO.getVaccineByVaccineID(rs.getInt("vacID")).getVaccineBrand(),
                                rs.getString("firstDose"),
                                rs.getString("secondDose"),
                                rs.getString("firstTime"),
                                rs.getString("secondTime"),
                                rs.getString("status")
                        )
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
            ps = conn.prepareStatement("SELECT * FROM schedule_info WHERE doctor1ID = ? or doctor2ID = ?");
            ps.setInt(1, doctorDAO.getDoctorByUserID(userID).getDoctorNum());
            ps.setInt(2, doctorDAO.getDoctorByUserID(userID).getDoctorNum());
            rs = ps.executeQuery();
            while(rs.next()) {
                list.add(
                        new Schedule(
                                rs.getInt("patientID"),
                                doctorDAO.getDoctorNameByDoctorID(rs.getInt("doctor1ID")),
                                doctorDAO.getDoctorNameByDoctorID(rs.getInt("doctor2ID")),
                                rs.getString("patientLName"),
                                rs.getString("patientFName"),
                                rs.getString("age"),
                                rs.getString("sex"),
                                rs.getString("city"),
                                vaccineDAO.getVaccineByVaccineID(rs.getInt("vacID")).getVaccineBrand(),
                                rs.getString("firstDose"),
                                rs.getString("secondDose"),
                                rs.getString("firstTime"),
                                rs.getString("secondTime"),
                                rs.getString("status")
                        )
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
            ps = conn.prepareStatement("SELECT * FROM schedule_info WHERE patientID = ?");
            ps.setInt(1, patientNum);
            rs = ps.executeQuery();
            while(rs.next()) {
                schedule = new Schedule(
                        rs.getInt("patientID"),
                        doctorDAO.getDoctorNameByDoctorID(rs.getInt("doctor1ID")),
                        doctorDAO.getDoctorNameByDoctorID(rs.getInt("doctor2ID")),
                        rs.getString("patientLName"),
                        rs.getString("patientFName"),
                        rs.getString("age"),
                        rs.getString("sex"),
                        rs.getString("city"),
                        vaccineDAO.getVaccineByVaccineID(rs.getInt("vacID")).getVaccineBrand(),
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

    /**
     * Method used for fetching all tuples that matches the given date to firstDose or secondDose attribute.
     * @param date value to be checked in the firstTime/secondTime attribute of each tuple. (Current Date)
     * @return ObservableList of Schedule Bean object/s.
     */
    public static ObservableList<Schedule> getCurrentScheduleFirst(String date) {
        ObservableList<Schedule> list = FXCollections.observableArrayList();
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("SELECT * FROM schedule_info " +
                    "WHERE firstDose = ? and status = 'incomplete'");
            ps.setString(1, date);
            rs = ps.executeQuery();
            while(rs.next()) {
                list.add(
                        new Schedule(
                                rs.getInt("patientID"),
                                doctorDAO.getDoctorNameByDoctorID(rs.getInt("doctor1ID")),
                                doctorDAO.getDoctorNameByDoctorID(rs.getInt("doctor1ID")),
                                rs.getString("patientLName"),
                                rs.getString("patientFName"),
                                rs.getString("age"),
                                rs.getString("sex"),
                                rs.getString("city"),
                                vaccineDAO.getVaccineByVaccineID(rs.getInt("vacID")).getVaccineBrand(),
                                rs.getString("firstDose"),
                                rs.getString("secondDose"),
                                rs.getString("firstTime"),
                                rs.getString("secondTime"),
                                rs.getString("status")
                        )
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

    public static ObservableList<Schedule> getCurrentScheduleSecond(String date, ObservableList<Schedule> list) {

        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("SELECT * FROM schedule_info " +
                    "WHERE secondDose = ? and status = 'partial'");
            ps.setString(1, date);
            rs = ps.executeQuery();
            while(rs.next()) {
                list.add(
                        new Schedule(
                                rs.getInt("patientID"),
                                doctorDAO.getDoctorNameByDoctorID(rs.getInt("doctor2ID")),
                                doctorDAO.getDoctorNameByDoctorID(rs.getInt("doctor2ID")),
                                rs.getString("patientLName"),
                                rs.getString("patientFName"),
                                rs.getString("age"),
                                rs.getString("sex"),
                                rs.getString("city"),
                                vaccineDAO.getVaccineByVaccineID(rs.getInt("vacID")).getVaccineBrand(),
                                rs.getString("firstDose"),
                                rs.getString("secondDose"),
                                rs.getString("firstTime"),
                                rs.getString("secondTime"),
                                rs.getString("status")
                        )
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
            System.out.println(date);
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("SELECT * FROM " +
                    "(SELECT * FROM schedule_info WHERE firstDose = ? and status = 'incomplete' or secondDose = ? and status = 'partial') " +
                    "WHERE doctor1ID = ? or doctor2ID = ?");
            ps.setString(1, date);
            ps.setString(2, date);
            ps.setInt(3, doctorID);
            ps.setInt(4, doctorID);

            rs = ps.executeQuery();
            while(rs.next()) {
                list.add(
                        new Schedule(
                                rs.getInt("patientID"),
                                doctorDAO.getDoctorNameByDoctorID(rs.getInt("doctor1ID")),
                                doctorDAO.getDoctorNameByDoctorID(rs.getInt("doctor2ID")),
                                rs.getString("patientLName"),
                                rs.getString("patientFName"),
                                rs.getString("age"),
                                rs.getString("sex"),
                                rs.getString("city"),
                                vaccineDAO.getVaccineByVaccineID(rs.getInt("vacID")).getVaccineBrand(),
                                rs.getString("firstDose"),
                                rs.getString("secondDose"),
                                rs.getString("firstTime"),
                                rs.getString("secondTime"),
                                rs.getString("status")
                        )
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
     * Inserts a new tuple in schedule_info table.
     * @param u Schedule Bean object
     * @return status - If status > 0, SQL query is successful.
     */
    public static int addSchedule(Schedule u) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("insert into schedule_info " +
                    "(doctor1ID, doctor2ID, vacID, patientLName, patientFName, age, sex," +
                    "firstDose, secondDose, firstTime, secondTime, status, city) " +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, doctorDAO.getDoctorIDByDoctorName(u.getDoctorName_1()));
            ps.setInt(2, doctorDAO.getDoctorIDByDoctorName(u.getDoctorName_2()));
            ps.setInt(3, vaccineDAO.getVaccineIDByBrand(u.getVaccineBrand()));
            ps.setString(4, u.getPatientLName());
            ps.setString(5, u.getPatientFName());
            ps.setString(6, u.getAge());
            ps.setString(7, u.getSex());
            ps.setString(8, u.getFirstDose());
            ps.setString(9, u.getSecondDose());
            ps.setString(10, u.getFirstTime());
            ps.setString(11, u.getSecondTime());
            ps.setString(12, u.getStatus());
            ps.setString(13, u.getCity());
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
                    "set doctor1ID = ?, doctor2ID = ?, vacID = ?, " +
                    "patientLName = ?, patientFName =?, " +
                    "age = ?, sex = ?," +
                    "firstDose = ?, secondDose = ?, " +
                    "firstTime = ?, secondTime = ?, status = ?, city = ?" +
                    "where patientID = ?");
            ps.setInt(1, doctorDAO.getDoctorIDByDoctorName(u.getDoctorName_1()));
            ps.setInt(2, doctorDAO.getDoctorIDByDoctorName(u.getDoctorName_2()));
            ps.setInt(3, vaccineDAO.getVaccineIDByBrand(u.getVaccineBrand()));
            ps.setString(4, u.getPatientLName());
            ps.setString(5, u.getPatientFName());
            ps.setString(6, u.getAge());
            ps.setString(7, u.getSex());
            ps.setString(8, u.getFirstDose());
            ps.setString(9, u.getSecondDose());
            ps.setString(10, u.getFirstTime());
            ps.setString(11, u.getSecondTime());
            ps.setString(12, u.getStatus());
            ps.setString(13, u.getCity());
            ps.setInt(14, u.getPatientNum());
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
