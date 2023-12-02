import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Interface defining the contract for the proxy and real database connection
interface DatabaseConnectionInterface {
    Connection getConnection();
}

// Real Database Connection class implementing the DatabaseConnectionInterface
class DatabaseConnection implements DatabaseConnectionInterface {

    private Connection con = null;

    @Override
    public Connection getConnection() {
        if (con == null) {
            String url = "jdbc:mysql://localhost/bookstore?serverTimezone=UTC";
            String user = "root";
            String pass = "";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(url, user, pass);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return con;
    }
}

// Proxy class for DatabaseConnection
class DatabaseConnectionProxy implements DatabaseConnectionInterface {

    private DatabaseConnection realConnection;

    @Override
    public Connection getConnection() {
        if (realConnection == null) {
            realConnection = new DatabaseConnection();
        }
        return realConnection.getConnection();
    }
}
