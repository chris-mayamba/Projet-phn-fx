package org.example.database;

public class MYSQLConfig implements IDatabaseConfig{

    @Override
    public String getUrl() {
        return "jdbc:mysql://localhost:3306/produitstock";
    }

    @Override
    public String getUsername() {
        return "root";
    }

    @Override
    public String getPassword() {
        return "";
    }
}
