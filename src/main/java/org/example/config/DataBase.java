package org.example.config;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariConfig;
import javax.sql.DataSource;
import io.github.cdimascio.dotenv.Dotenv;

public class DataBase {
    private static HikariDataSource dataSource;
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