package com.ecsail.connection;
import com.ecsail.BaseApplication;
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

//    public void createDataSource(String ip, int port, String user, String pass, String database) throws ClassNotFoundException {
//        Class.forName("org.mariadb.jdbc.Driver");
//        try {
//            dataSource = new DriverManagerDataSource(
//                    dbType + "://" + ip + ":" + port + "/" + database + dbParam,
//                    user,
//                    pass
//            );
//            logger.info(String.valueOf("Connecting to schema: " + dataSource.getConnection().getCatalog()));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void createDataSource(String ip, int port, String user, String pass, String database) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            dataSource = new DriverManagerDataSource(
                    dbType + "://" + ip + ":" + port + "/" + database + dbParam,
                    user,
                    pass
            );
            logger.info("Connecting to schema: " + dataSource.getConnection().getCatalog());
        } catch (ClassNotFoundException e) {
            logger.error("Driver class not found", e);
            throw new RuntimeException(e);
        } catch (SQLException e) {
            logger.error("SQL Exception", e);
            throw new RuntimeException(e);
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}