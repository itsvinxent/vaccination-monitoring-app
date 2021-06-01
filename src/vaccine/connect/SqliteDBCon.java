package vaccine.connect;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class SqliteDBCon {
    public static Connection Connector() {
        try{
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Admin\\IdeaProjects\\vaccine-monitoring-system\\src\\vaccine\\vaccinedb.db");
        } catch (Exception e) {
            //To do
            return null;
        }
    }
}
