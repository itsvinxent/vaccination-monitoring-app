package vaccine.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vaccine.classes.Vaccine;
import vaccine.connect.SqliteDBCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class vaccineDAO {
    static Connection conn = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;

    public static ObservableList<String> getAllVaccineBrand() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("select vaccineBrand from vaccine_info");
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

    public static int addVaccine(Vaccine u) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("insert into vaccine_info(vaccineBrand, storageAmount, doseInterval) values (?,?,?)");
            ps.setString(1, u.getVaccineBrand());
            ps.setInt(2, u.getStorageAmount());
            ps.setInt(3, u.getDoseInterval());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            return -1;
        }
        return status;
    }
}
