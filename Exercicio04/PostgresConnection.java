import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnection {

    private static final String URL = "YOUR_URL";
    private static final String USER = "YOUR_USER";
    private static final String PASSWORD = "YOUR_PASSWORD";

    public static Connection connect() {
        Connection conn = null;
        try {
            // Carrega o driver JDBC do PostgreSQL
            Class.forName("org.postgresql.Driver");

            // Conecta ao banco
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão estabelecida com sucesso!");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver PostgreSQL não encontrado.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados.");
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        connect();
    }
}
