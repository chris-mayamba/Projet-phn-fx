package org.example.projet_phn_fx.database;


import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionProvider {
    public Connection getConnection() throws SQLException;
}
