package ru.gnivc.training.flight.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.gnivc.training.flight.spr.objects.FlightClass;

/**
 *
 * @author TaylakovSA
 */
public class FlightClassDB {
    private static volatile FlightClassDB instance;
    
    private FlightClassDB(){
    }
    
    public static FlightClassDB getInstance(){
       FlightClassDB localInstance = instance;
        if (localInstance == null) {
            synchronized(FlightClassDB.class){
                 localInstance = instance;
              if (localInstance == null){
                     instance = localInstance = new FlightClassDB();
              }  
            }
        }
        return localInstance;
    }
    
    public FlightClass getFlightClass(long id){
        try {
            return getFlightClass(getFlightClassStmt(id));
        } catch (SQLException ex) {
            Logger.getLogger(FlightClassDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        AviaDB.getInstance().closeConnection();
        }
        return null;
    }
    
    private FlightClass getFlightClass(PreparedStatement stmt){
        
        FlightClass flightClass = null;
        ResultSet rs = null;
        try {
        rs = stmt.executeQuery();
        rs.next();
        if (rs.isFirst()){
            flightClass = new FlightClass();
            flightClass.setId(rs.getLong("id"));
            flightClass.setName(rs.getString("name"));
            flightClass.setDesc(rs.getString("desc"));
        }
        } catch (SQLException ex) {
            Logger.getLogger(FlightClassDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flightClass;
    }
    
    private PreparedStatement getFlightClassStmt(long id) throws SQLException{
        Connection conn = AviaDB.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement("select * from spr_flight_class where id=?");
        stmt.setLong(1, id);
        return stmt;
    }
    
}
