package vaccine.backend.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import vaccine.backend.classes.Vaccine;
import vaccine.backend.util.SqliteDBCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import vaccine.frontend.controllers.addPatient;

public class graphDAO {
    static Connection conn = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;

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

    public static int getPatientCountBasedOnStatus(String s) {
        int count = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("SELECT SUM(CASE WHEN status = ? THEN 1 ELSE 0 END) AS [Total Patients] FROM schedule_info");
            ps.setString(1, s);
            rs = ps.executeQuery();
            count = rs.getInt("Total Patients");
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return count;
    }
}
