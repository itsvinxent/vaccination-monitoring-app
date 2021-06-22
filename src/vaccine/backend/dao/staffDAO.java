package vaccine.backend.dao;

import vaccine.backend.classes.Staff;
import vaccine.backend.util.SqliteDBCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class staffDAO {
    static Connection conn = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;

    public static String getStaffNameByID(int userID) {
        String name = null;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("select staffName from staff_info where userID = ?");
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            while (rs.next()) {
                name = rs.getString("staffName");
            }
        } catch (Exception exception) {
            return null;
        }
        return name;
    }

    public static int addStaff (Staff s){
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("insert into staff_info(userID, staffName) VALUES (?,?)");
            ps.setInt(1, s.getUserNum());
            ps.setString(2, s.getStaffName());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return status;
    }

    public static int updateStaff(Staff s) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("update staff_info " +
                    "set userID = ?, staffName = ? " +
                    "where staffID = ?");
            ps.setInt(1, s.getUserNum());
            ps.setString(2, s.getStaffName());
            ps.setInt(3, s.getStaffID());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return status;
    }

    public static int deleteStaff (Staff d) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("delete from staff_info where staffID=?");
            ps.setInt(1, d.getStaffID());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return status;
    }

}
