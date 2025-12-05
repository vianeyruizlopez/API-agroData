package org.example.config;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariConfig;
import javax.sql.DataSource;
import io.github.cdimascio.dotenv.Dotenv;

/**
 * Clase que maneja la configuración de la base de datos.
 * Usa HikariCP para el pool de conexiones y carga la configuración desde variables de entorno.
 */
public class DataBase {
    /**
     * Fuente de datos compartida para toda la aplicación.
     * <p>
     * Se inicializa una sola vez y se reutiliza en cada llamada.
     */
    private static HikariDataSource dataSource;
    /**
     * Constructor privado vacío.
     * <p>
     * Se declara para evitar que la clase sea instanciada,
     * ya que todos sus métodos son estáticos.
     */
    private DataBase() {
    }

    /**
     * Obtiene el DataSource configurado con HikariCP.
     * Crea la conexión solo una vez usando el patrón Singleton.
     * Lee la configuración de la base de datos desde el archivo .env
     * @return el DataSource configurado para conectar a MySQL
     */
    public static DataSource getDataSource(){
        if (dataSource == null) {
           Dotenv dotenv = Dotenv.load();
            String host = dotenv.get("DB_HOST");

            String dbName = dotenv.get("DB_SCHEMA");

            String user = dotenv.get("DB_USER") ;

            String pass = dotenv.get("DB_PASS");

            String url = "jdbc:mysql://" + host + ":3306/" + dbName;

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(user);
            config.setPassword(pass);
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }
}