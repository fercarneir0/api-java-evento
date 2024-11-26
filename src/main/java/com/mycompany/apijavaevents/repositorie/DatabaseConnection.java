package com.mycompany.apijavaevents.repositorie;

import jakarta.jms.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    private static final String URL = System.getenv("DATABASE_URL");
    private static final String USER = System.getenv("DATABASE_USER");
    private static final String PASSWORD = System.getenv("DATABASE_PASSWORD");
    
    
    public static Connection getConnection() throws SQLException {
        if(URL == null || USER == null || PASSWORD == null ){
            throw new IllegalStateException("Variaveis de ambiente não inicializadas");
        }
        return (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
