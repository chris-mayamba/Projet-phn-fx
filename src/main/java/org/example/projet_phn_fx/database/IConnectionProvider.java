package org.example.database;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionProvider {
    public Connection getConnection() throws SQLException;
}
