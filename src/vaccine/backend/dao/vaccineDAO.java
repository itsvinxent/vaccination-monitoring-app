package vaccine.backend.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vaccine.backend.classes.Vaccine;
import vaccine.backend.util.SqliteDBCon;

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

    public static int addVaccine (Vaccine v) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("insert into vaccine_info(vaccineBrand, storageAmount, doseInterval) values (?,?,?)");
            ps.setString(1, v.getVaccineBrand());
            ps.setInt(2, v.getStorageAmount());
            ps.setInt(3, v.getDoseInterval());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            return -1;
        }
        return status;
    }

    public static int updateVaccine (Vaccine v) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("update vaccine_info " +
                    "set vaccineBrand = ?, storageAmount = ?, doseInterval = ? where vaccineID = ?");
            ps.setString(1, v.getVaccineBrand());
            ps.setInt(2, v.getStorageAmount());
            ps.setInt(3, v.getDoseInterval());
            ps.setInt(4, v.getVaccineID());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            return -1;
        }
        return status;
    }

    public static int deleteVaccine (Vaccine v) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("delete from vaccine_info where vaccineID = ?");
            ps.setInt(1, v.getVaccineID());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return status;
    }
}
