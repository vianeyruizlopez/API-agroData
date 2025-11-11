package org.example.config;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariConfig;
import javax.sql.DataSource;
import io.github.cdimascio.dotenv.Dotenv;

public class DataBase {
    private static HikariDataSource dataSource;
    public static DataSource getDataSource(){
        if (dataSource == null) {
        //   Dotenv dotenv = Dotenv.load();
            //String host="13.219.151.36";
            String host ="localhost";
           //dotenv.get("DB_HOST");
            String dbName = "agrodata2";
           //dotenv.get("DB_NAME");
            //String user="ailyn";
            String user = "root";
            //dotenv.get("DB_USER");
            //String pass="250319";
            String pass = "root";
            //dotenv.get("DB_PASS");
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