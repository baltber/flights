/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.gnivc.training.flight.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.gnivc.training.flight.spr.objects.Place;

/**
 *
 * @author TaylakovSA
 */
public class PlaceDB {
    
    private static volatile PlaceDB instance;
    
    private PlaceDB(){}
    
    public static PlaceDB getInstance(){
         PlaceDB localInstance = instance;
        if (localInstance == null) {
            synchronized(PlaceDB.class){
                 localInstance = instance;
              if (localInstance == null){
                     instance = localInstance = new PlaceDB();
              }  
            }
        }
        return localInstance;
    }
    
    public Place getPlace(long id){
        try {
            return getPlace(getPlaceStmt(id));
        } catch (SQLException ex) {
            Logger.getLogger(PlaceDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            AviaDB.getInstance().closeConnection();
        }
        return null;
    }
    
    private Place getPlace(PreparedStatement stmt){
        
            Place place =null;
            ResultSet rs = null;
            try {
            rs = stmt.executeQuery();
            rs.next();
            if(rs.isFirst()){
                place = new Place();
                place.setId(rs.getLong("id"));
                place.setRow(rs.getString("row").charAt(0));
                place.setSeat(rs.getInt("seat"));
                place.setFlightClass(FlightClassDB.getInstance().getFlightClass(rs.getLong("flight_class_id")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlaceDB.class.getName()).log(Level.SEVERE, null, ex);
        }
            return place;
    }
    
    private PreparedStatement getPlaceStmt(long id) throws SQLException{
        Connection conn = AviaDB.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement("select * from spr_place where id=?");
        stmt.setLong(1, id);
        return stmt;
    }
}
