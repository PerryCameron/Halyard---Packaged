package com.ecsail.structures;

import com.mysql.cj.x.protobuf.MysqlxCrud;

public class DataBase {

    public static void recordChange(String query) {
        String[] splitQuery = query.toUpperCase().split(" ");
        switch (splitQuery[0]) {
            case "UPDATE":
                break;
            case "DELETE":
                break;
            case "INSERT":
                break;
        }

    }

}
