package com.ranok.oracle;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Example2 {

    private static final String DEFAULT_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DEFAULT_URL = "jdbc:oracle:thin:@192.168.0.1:1521:xe";
    private static final String DEFAULT_USERNAME = "system";
    private static final String DEFAULT_PASSWORD = "oracle";

    private Connection connection;

    public void test() {
        try {
            this.connection = createConnection();
            Log.d("ORA","Connected");
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("select * from cat");
            while (rs.next()) {
                System.out.println("hello : " + rs.getString(1));
            }
            connection.close();
        } catch (Exception e) {
            Log.d("ORA",e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection createConnection(String driver, String url, String username, String password) throws ClassNotFoundException, SQLException {

        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        return createConnection(DEFAULT_DRIVER, DEFAULT_URL, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }
}
