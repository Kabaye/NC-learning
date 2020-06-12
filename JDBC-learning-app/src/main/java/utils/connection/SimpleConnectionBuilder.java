package utils.connection;

import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Setter
public class SimpleConnectionBuilder implements ConnectionBuilder {
    private final static String LOGIN = "postgres";

    private final static String URL = "jdbc:postgresql://localhost:5432/postgres";
    private final static String PASSWORD = "99ronore";

    public SimpleConnectionBuilder() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException exc) {
            System.out.println("Problems with driver!");
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, LOGIN, PASSWORD);
    }
}