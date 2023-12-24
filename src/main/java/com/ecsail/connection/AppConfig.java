package com.ecsail.connection;
import com.ecsail.BaseApplication;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class AppConfig {
    private DataSource dataSource;

    String dbParam = BaseApplication.getPropertyValue("database.parameters");
    String dbType = BaseApplication.getPropertyValue("database.type");
    public void createDataSource(String ip, int port, String user, String pass, String database) throws ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dataSource = new DriverManagerDataSource(
                dbType + "://" + ip + ":" + port + "/"+database+ dbParam,
                user,
                pass
        );
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}