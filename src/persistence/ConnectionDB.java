package persistence;

import java.sql.*;

public class ConnectionDB {
    private static ConnectionDB instance;

    private ConnectionDB() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    public static synchronized ConnectionDB getInstance() throws SQLException{
        if (instance == null){
            instance = new ConnectionDB();
        }

        return instance;
    }

    public  Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=techitapart","postgres", "farouk_12");
    }


}
