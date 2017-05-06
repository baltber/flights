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
import ru.gnivc.training.flight.spr.objects.Country;



public class CountryDB {
     private static volatile CountryDB instance;
     
     private CountryDB(){}

    public static CountryDB getInstance() {
         CountryDB localInstance = instance;
        if (localInstance == null) {
            synchronized(CountryDB.class){
                localInstance = instance;
                if (localInstance == null){
                    instance = localInstance = new CountryDB(); 
                }
            }
        }
        return localInstance;
    }
    
    public Country getCountry(long id) {
        try {
            return getCountry(getCountryStmt(id));
        } catch (SQLException ex) {
            Logger.getLogger(CountryDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            AviaDB.getInstance().closeConnection();
        }
        return null;
    }
    
    private Country getCountry(PreparedStatement stmt){
        Country country = null;
        ResultSet rs = null;
        
        try{
            rs = stmt.executeQuery();
            rs.next();
            if(rs.isFirst()){
                country = new Country();
                country.setId(rs.getLong("id"));
                country.setName(rs.getString("name"));
                country.setDesc(rs.getString("desc"));
                country.setCode(rs.getString("code"));
                country.setFlag(rs.getBytes("flag"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return country;
    }
    
    private PreparedStatement getCountryStmt(long id) throws SQLException{
        Connection conn = AviaDB.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement("select * from spr_country where id=?");  
        stmt.setLong(1, id);
        return stmt;
    }
}
