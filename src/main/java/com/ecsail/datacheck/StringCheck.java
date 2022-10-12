package com.ecsail.datacheck;

public class StringCheck {

    public String checkForProperSQLTableName(String tableName) {
       tableName = tableName.replaceAll("[^a-zA-Z0-9]", "");
       tableName.replaceAll("\\s","_");
       return tableName.toUpperCase();
    }
}
