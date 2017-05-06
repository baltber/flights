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
import ru.gnivc.training.flight.spr.objects.Company;

/**
 *
 * @author TaylakovSA
 */
public class CompanyDB {
    
     private CompanyDB() {
    }
    private static volatile CompanyDB instance;

    public static CompanyDB getInstance() {
        CompanyDB localInstance = instance;
        if (localInstance == null) {
            synchronized(CompanyDB.class){
                 localInstance = instance;
              if (localInstance == null){
                     instance = localInstance = new CompanyDB();
              }  
            }
        }
        return localInstance;
    }

    public Company getCompany(long id) {
        try {
            return getCompany(getCompanyStmt(id));
        } catch (SQLException ex) {
            Logger.getLogger(CompanyDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            AviaDB.getInstance().closeConnection();
        }
        return null;
    }

    public Company getCompany(String name) {
        try {
            return getCompany(getCompanyStmt(name));
        } catch (SQLException ex) {
            Logger.getLogger(CompanyDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            AviaDB.getInstance().closeConnection();
        }
        return null;
    }

    private Company getCompany(PreparedStatement stmt) {

        Company company = null;
        ResultSet rs = null;

        try {
            rs = stmt.executeQuery();

            rs.next();
            if (rs.isFirst()) {
                company = new Company();
                company.setId(rs.getLong("id"));
                company.setName(rs.getString("name"));
                company.setDesc(rs.getString("desc"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return company;
    }

    private PreparedStatement getCompanyStmt(String name) throws SQLException {
        Connection conn = AviaDB.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement("select * from spr_company where name=?");
        stmt.setString(1, name);
        return stmt;
    }

    private PreparedStatement getCompanyStmt(long id) throws SQLException {
        Connection conn = AviaDB.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement("select * from spr_company where id=?");
        stmt.setLong(1, id);
        return stmt;
    }
}
