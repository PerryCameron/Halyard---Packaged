package com.ecsail.connection;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class AppConfig {
    private DataSource dataSource;
    public void createDataSource(String ip, int port, String user, String pass) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        dataSource = new DriverManagerDataSource(
                "jdbc:mysql://" + ip + ":" + port + "/ECSC_SQL?autoReconnect=true&useSSL=false&serverTimezone=UTC",
                user,
                pass
        );
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}