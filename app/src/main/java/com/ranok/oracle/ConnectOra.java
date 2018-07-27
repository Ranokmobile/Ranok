package com.ranok.oracle;

import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//https://stackoverflow.com/questions/31209978/establishing-connection-between-android-app-and-oracle-db-using-jdbc-driver
//Prerequisite are: Note there is no need to add dependency lib ojdbc14.jar just copy ojdbc14.jar to your JAVA_HOME jre -> lib -> ext
public class ConnectOra {
        private Statement stmt;
        private Connection conn = null;
    public ConnectOra() throws ClassNotFoundException {
        try {
            System.out.println("in try");
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@192.168.88.249:1521:xe";
//            OracleDriver ora = new OracleDriver();
            conn = DriverManager.getConnection(url,"test_user","test_user");
//            Properties p = new Properties();
//            p.put("user", "test_user");
//            p.put("password", "test_user");
//            conn = ora.connect(url, p);
            conn.setAutoCommit(false);
            //this.stmt = conn.createStatement();
        } catch(SQLException e) {
            Log.d("tag", e.getMessage());
        }
    }

    public Connection getConn() {
        return conn;
    }

    public ResultSet getResult() throws SQLException {
        ResultSet rset = stmt.executeQuery("select ccc, ddd from ttt");
        System.out.println(rset+"");

        return rset;
    }
}
