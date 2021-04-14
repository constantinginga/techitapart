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
        String user = "xorpblocnixrey";
        String pass = "7f8b2ffb33886cbe57fdb0c360df3186e636ea082374d6b7bebdc1d33d075930";
        String host = "jdbc:postgresql://ec2-99-80-200-225.eu-west-1.compute.amazonaws.com:5432/df75rrd5dikon6?currentSchema=techitapart";
        return DriverManager.getConnection(host,user, pass);
    }


}
