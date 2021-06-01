package vaccine.dao;

import vaccine.connect.SqliteDBCon;

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
}
