package com.vdoichev.javafxml.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
public class MySQLConnection {
    private static Connection con;

    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost/addressbook?user=vdoichev&password=siavaismail";
            return DriverManager.getConnection(url);
        }catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex){
            System.out.println(ex);
        }

        return null;
    }
}
