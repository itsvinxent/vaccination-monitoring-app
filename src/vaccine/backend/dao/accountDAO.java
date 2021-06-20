package vaccine.backend.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vaccine.backend.util.SqliteDBCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import vaccine.backend.classes.Account;

public class accountDAO {
    static Connection conn = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;

    public static int addAccount(Account u) {
        int id = -1;
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
            exception.printStackTrace();
        }
        return id;
    }

    public static int updateAccount (Account u) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("update user_info set username = ?, password = ?, usertype = ? where userID = ?");
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getUsertype());
            ps.setInt(4, u.getUserID());
            status = ps.executeUpdate();
            if (status > 0){
                System.out.println("success");
            }
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return status;
    }

    public static int deleteAccount (Account u) {
        int status = 0;
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("delete from user_info where userID = ?");
            ps.setInt(1, u.getUserID());
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return status;
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
            exception.printStackTrace();
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
            exception.printStackTrace();
        }
        return id;

    }

    public static ObservableList<Account> getAllAccounts() {
        ObservableList<Account> list = FXCollections.observableArrayList();
        try {
            conn = SqliteDBCon.Connector();
            ps = conn.prepareStatement("select * from user_info");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Account(rs.getInt("userID"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("usertype")
                ));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return list;
    }
}
