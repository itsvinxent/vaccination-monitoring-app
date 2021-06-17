package vaccine.dao;

import vaccine.classes.Account;
import vaccine.connect.SqliteDBCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class accountDAO {
    static Connection conn = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;

    public static int save (Account u) {
        int id = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("insert into user_info (username, password, usertype) VALUES (?,?,?)");
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getUsertype());
            ps.executeUpdate();
            id = getUserIDByUsername(u.getUsername());
            conn.close();
        } catch (Exception exception) {
            // to do
        }
        return id;
    }

    public static Account validate(Account u) {
        String user = u.getUsername();
        String pass = u.getPassword();
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("select * from user_info where username = ? and password = ?");
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                if(user.equals(username) && pass.equals(password)){
                    System.out.println("Success");
                    u.setUserID(rs.getInt("userID"));
                    u.setUsertype(rs.getString("usertype"));
                    u.setUsername(username);
                    u.setPassword(password);
                } else {
                    System.out.println("Failed");
                    return null;
                }
            }
            conn.close();
        } catch (Exception exception) {
            // to do
        }
        return u;
    }

    public static int getUserIDByUsername(String username) {
        int id = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("select userID from user_info where username = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next())
                id = rs.getInt("userID");
            conn.close();
        } catch (Exception exception) {
            // to do
        }
        return id;

    }
}
