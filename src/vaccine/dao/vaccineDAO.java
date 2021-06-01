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

    public static String getVaccineBrandByID(int vaccineID) {
        String brand = null;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("select vaccineBrand from vaccine_info where vaccineID=?");
            ps.setInt(1, vaccineID);
            rs=ps.executeQuery();
            while(rs.next()) {
                brand = rs.getString("vaccineBrand");
            }
        } catch (Exception exception) {
            return null;
        }
        return brand;
    }

    public static ObservableList<String> getAllVaccineBrand() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("select vaccineBrand from vaccine_info");
            rs = ps.executeQuery();
            while(rs.next()) {
                list.add(rs.getString("vaccineBrand"));
            }
        } catch (Exception exception) {
            return null;
        }
        return list;
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
        } catch (Exception exception) {
            return status;
        }
        return status;
    }
}
