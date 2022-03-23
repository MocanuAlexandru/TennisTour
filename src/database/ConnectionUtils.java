package database;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtils {
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASS;
    private static ConnectionUtils instance = new ConnectionUtils();

    {
        Properties properties = new Properties();

        try(FileReader f = new FileReader(new File("src/database/connection.properties"))) {
            properties.load(f);
            DB_URL = properties.getProperty("URL");
            DB_USER = properties.getProperty("User");
            DB_PASS = properties.getProperty("Password");
        }
        catch (IOException e) {
            e.printStackTrace();
            DB_USER = "";
            DB_PASS = "";
        }
    }
    public static ConnectionUtils getInstance() {return instance;}
    private ConnectionUtils(){}
    public Connection getDBConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public void closeDBConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
