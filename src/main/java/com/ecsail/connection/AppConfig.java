package com.ecsail.connection;
import com.ecsail.BaseApplication;
import com.ecsail.dto.LoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class AppConfig {
    private DataSource dataSource;

    public static Logger logger = LoggerFactory.getLogger(AppConfig.class);

    String dbParam = BaseApplication.getPropertyValue("database.parameters");
    String dbType = BaseApplication.getPropertyValue("database.type");

    public void createDataSource(LoginDTO loginDTO) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            dataSource = new DriverManagerDataSource(
                    dbType + "://127.0.0.1" + ":" + "3306" + "/" + loginDTO.getDatabase() + dbParam,
                    loginDTO.getSqlUser(),
                    loginDTO.getSqlPasswd()
            );
            logger.info("Connecting to schema: " + dataSource.getConnection().getCatalog());
        } catch (ClassNotFoundException e) {
            logger.error("Driver class not found", e);
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("Unable to make a database connection");
            logger.error("SQL Exception", e);
            throw new RuntimeException(e);
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}