package com.ecsail.connection;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class AppConfig {
    private DataSource dataSource;
    public void createDataSource(String ip, int port, String user, String pass, String database) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        dataSource = new DriverManagerDataSource(
                "jdbc:mysql://" + ip + ":" + port + "/"+database+"?autoReconnect=true&useSSL=false&serverTimezone=UTC",
                user,
                pass
        );
        System.out.println("ip=" + ip + " port=" + port + " user=" +user + " pass=" + pass + " database=" + database);
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}