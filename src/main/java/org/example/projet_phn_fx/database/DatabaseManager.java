package org.example.projet_phn_fx.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager implements IConnectionProvider{
    private IDatabaseConfig config;

    public DatabaseManager(IDatabaseConfig config){
        this.config = config;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                config.getUrl(),
                config.getUsername(),
                config.getPassword()
        );
    }

}

