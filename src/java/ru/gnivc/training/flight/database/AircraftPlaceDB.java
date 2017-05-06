
package ru.gnivc.training.flight.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.gnivc.training.flight.spr.objects.AircraftPlace;
import ru.gnivc.training.flight.spr.objects.Place;

/**
 *
 * @author TaylakovSA
 */
public class AircraftPlaceDB {
    private static AircraftPlaceDB instance;
    
    
    private AircraftPlaceDB(){}
    
    public static AircraftPlaceDB getInstance(){
        if (instance == null){
            synchronized(AircraftPlaceDB.class){
                if (instance == null){
                    instance = new AircraftPlaceDB();
                }
            }
        }
        return instance;
    }
    
    public AircraftPlace getAircraftPlace(long id){
        try {
            return getAircraftPlace(getAircraftPlaceStmt(id));
        } catch (SQLException ex) {
            Logger.getLogger(AircraftPlaceDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            AviaDB.getInstance().closeConnection();
        }
        return null;
    }
    //Получить список мест по id самолёта
    public ArrayList<Place> getAircraftPlaces(long aircraftId){
        try {
            return getAircraftPlaces(getAircraftPlacesStmt(aircraftId));
        } catch (SQLException ex) {
            Logger.getLogger(AircraftPlaceDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            AviaDB.getInstance().closeConnection();
        }
        return null;
    }
    
    private AircraftPlace getAircraftPlace(PreparedStatement stmt){
        
            AircraftPlace aircraftPlace = null;
            ResultSet rs = null;
            try {
            rs = stmt.executeQuery();
            rs.next();
            if(rs.isFirst()){
                aircraftPlace = new AircraftPlace();
                aircraftPlace.setId(rs.getLong("id"));
                aircraftPlace.setAircraftId(rs.getLong("aircraft_id"));
                aircraftPlace.setPlaceId(rs.getLong("place_id"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlaceDB.class.getName()).log(Level.SEVERE, null, ex);
        }
            return aircraftPlace;
    }
    
    private ArrayList<Place> getAircraftPlaces(PreparedStatement stmt){
        Place place = null;
        ArrayList<Place> listPlace = new ArrayList<>();
        for(Long id : getAircraftPlacesId(stmt)){
            place = PlaceDB.getInstance().getPlace(id);
            listPlace.add(place);
        }
        return listPlace;
    }
    //Получить список id мест
     private ArrayList<Long> getAircraftPlacesId(PreparedStatement stmt){
         ResultSet rs = null;
        ArrayList<Long> listId = new ArrayList<>();
        try {
        rs = stmt.executeQuery();
        if(rs.next()){
            do{
            long id = rs.getLong("place_id");
            listId.add(id);
            } while (rs.next());
        }
        } catch (SQLException ex) {
            Logger.getLogger(AircraftPlaceDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listId;
     }
    
    private PreparedStatement getAircraftPlaceStmt(long id) throws SQLException{
        Connection conn = AviaDB.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement("select * from spr_aircraft_place where id=?");
        stmt.setLong(1, id);
        return stmt;
    }
    //Выборка мест по aircraft_id
    private PreparedStatement getAircraftPlacesStmt(long aircraftId) throws SQLException{
        Connection conn = AviaDB.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement("select * from spr_aircraft_place where aircraft_id=?");
        stmt.setLong(1, aircraftId);
        return stmt;
    }
}
