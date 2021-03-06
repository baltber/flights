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
import ru.gnivc.training.flight.spr.objects.City;



public class CityDB {

    private CityDB() {
    }
    private static volatile CityDB instance;

    public static CityDB getInstance() {
       CityDB localInstance = instance;
        if (localInstance == null) {
            synchronized(CityDB.class){
                localInstance = instance;
                if (localInstance == null){
                    instance = localInstance = new CityDB(); 
                }
            }
        }
        return localInstance;
    }

    public City getCity(long id) {
        try {
            return getCity(getCityStmt(id));
        } catch (SQLException ex) {
            Logger.getLogger(CityDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            AviaDB.getInstance().closeConnection();
        }
        return null;
    }

    public City getCity(String name) {
        try {
            return getCity(getCityStmt(name));
        } catch (SQLException ex) {
            Logger.getLogger(CityDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            AviaDB.getInstance().closeConnection();
        }
        return null;
    }

    private City getCity(PreparedStatement stmt) {

        City city = null;
        ResultSet rs = null;

        try {
            rs = stmt.executeQuery();

            rs.next();
            if (rs.isFirst()) {
                city = new City();
                city.setId(rs.getLong("id"));
                city.setCode(rs.getString("code"));
                city.setCountry(CountryDB.getInstance().getCountry(rs.getLong("country_id")));
                city.setDesc(rs.getString("desc"));
                city.setName(rs.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return city;
    }

    private PreparedStatement getCityStmt(String name) throws SQLException {
        Connection conn = AviaDB.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement("select * from spr_city where name=?");
        stmt.setString(1, name);
        return stmt;
    }

    private PreparedStatement getCityStmt(long id) throws SQLException {
        Connection conn = AviaDB.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement("select * from spr_city where id=?");
        stmt.setLong(1, id);
        return stmt;
    }
}
