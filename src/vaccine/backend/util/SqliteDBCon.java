package vaccine.backend.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Java Utility Class used for creating SQLite Database Connections
 */
public class SqliteDBCon {
    /**
     * Java SQL Connector Class
     * @return SQLite3 Database Connection. returns null if connection fails.
     */
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
