package persistence;

import java.sql.*;

public class ConnectionDB {
    private static ConnectionDB instance;
    private String url;
    private String schemaName;
    private String username;
    private String password;

    private final static String URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=";
    private final static String SCHEMA_NAME = "techitapart";
    private final static String USERNAME = "postgres";
    private final static String PASSWORD = "kubino";

//    private final static String URL = "jdbc:postgresql://ec2-99-80-200-225.eu-west-1.compute.amazonaws.com:5432/df75rrd5dikon6?currentSchema=";
//    private final static String SCHEMA_NAME = "techitapart";
//    private final static String USERNAME = "xorpblocnixrey";
//    private final static String PASSWORD = "7f8b2ffb33886cbe57fdb0c360df3186e636ea082374d6b7bebdc1d33d075930";


    private ConnectionDB() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    public static synchronized ConnectionDB getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnectionDB();
        }

        return instance;
    }


    public Connection getConnection() throws SQLException {
        this.url = URL;
        this.schemaName = SCHEMA_NAME;
        this.username = USERNAME;
        this.password = PASSWORD;

        return DriverManager.getConnection(url + schemaName, username, password);

    }


}