package vaccine.backend.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqliteDBCon {
    public static Connection Connector() {
        try{
            Class.forName("org.sqlite.JDBC");
            String currentPath = System.getProperty("user.dir");
            return DriverManager.getConnection("jdbc:sqlite:"+currentPath+"\\src\\vaccine\\vaccinedb.db");
        } catch (Exception e) {
            //To do
            return null;
        }
    }
}
