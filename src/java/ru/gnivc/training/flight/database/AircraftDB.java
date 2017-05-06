
package ru.gnivc.training.flight.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.gnivc.training.flight.spr.objects.Aircraft;

/**
 *
 * @author TaylakovSA
 */
public class AircraftDB {
     private static volatile AircraftDB instance;
    
    private AircraftDB(){}
    
    public static AircraftDB getInstance(){
        AircraftDB localInstance = AircraftDB.instance;
        if (localInstance == null){
            synchronized(AircraftDB.class){
                 localInstance = AircraftDB.instance;
                if (localInstance == null){
                     AircraftDB.instance = localInstance = new AircraftDB();
                }
            }
        }
        return localInstance;
    }
    
    public Aircraft getAircraft(long id){
        try {
            return getAircraft(getAircraftStmt(id));
        } catch (SQLException ex) {
            Logger.getLogger(AircraftDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            AviaDB.getInstance().closeConnection();
        }
        return null;
    }
    
    private Aircraft getAircraft(PreparedStatement stmt){
        
            Aircraft aircraft = null;
            ResultSet rs = null;
            try {
            rs = stmt.executeQuery();
            rs.next();
            if(rs.isFirst()){
                aircraft = new Aircraft();
                aircraft.setId(rs.getLong("id"));
                aircraft.setName(rs.getString("name"));
                aircraft.setDesc(rs.getString("desc"));
                aircraft.setPlaceList(AircraftPlaceDB.getInstance().getAircraftPlaces(rs.getLong("id")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlaceDB.class.getName()).log(Level.SEVERE, null, ex);
        }
            return aircraft;
    }
    
    private PreparedStatement getAircraftStmt(long id) throws SQLException{
        Connection conn = AviaDB.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement("select * from spr_aircraft where id=?");
        stmt.setLong(1, id);
        return stmt;
    }
    
}
