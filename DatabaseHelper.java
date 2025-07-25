import java.sql.*;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:registration.db";
    private static Connection conn = null;

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (conn == null || conn.isClosed()) {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(DB_URL);
        }
        return conn;
    }

    public static void createUsersTable() throws SQLException, ClassNotFoundException {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id TEXT PRIMARY KEY," +
                "name TEXT," +
                "gender TEXT," +
                "address TEXT," +
                "contact TEXT)";
        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute(sql);
        }
    }

    public static void createUsersWithDOBTable() throws SQLException, ClassNotFoundException {
        String sql = "CREATE TABLE IF NOT EXISTS users_dob (" +
                "name TEXT," +
                "mobile TEXT," +
                "gender TEXT," +
                "dob TEXT," +
                "address TEXT)";
        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute(sql);
        }
    }
}
