
package ru.gnivc.training.flight.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AviaDB {

    private static Connection conn;
    
    private AviaDB() {
    }
    private static volatile AviaDB instance;

    public static AviaDB getInstance() {
        AviaDB localInstance = instance;
        if (localInstance == null) {
            synchronized(AviaDB.class){
                localInstance = instance;
                if (localInstance == null){
                    instance = localInstance = new AviaDB(); 
                }
            }
        }
        return localInstance;
    }

    public Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/avia?zeroDateTimeBehavior=convertToNull", "root", "1234");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AviaDB.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return conn;
    }

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();


            } catch (SQLException ex) {
                Logger.getLogger(AviaDB.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

