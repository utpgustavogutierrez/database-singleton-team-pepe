import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sakila {
    private static Sakila instance; 
    private Connection connection; 

    private Sakila() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:./sqlite-sakila.db");
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static Sakila getInstance() {
        if (instance == null) {
            instance = new Sakila();
        }
        return instance;
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public static void main(String[] args) {
        Sakila sakila = Sakila.getInstance();
        try {
            ResultSet rs = sakila.executeQuery("SELECT * FROM film");
            while (rs.next()) {
                String title = rs.getString("title");
                System.out.println("title = " + title);
            }
            rs.close();
            sakila.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }
}
