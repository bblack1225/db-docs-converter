package com.blake;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Scanner;

@Slf4j
public class Main {
    public static void main(String[] args) {
//        Connection connection = DriverManager
//                .getConnection("jdbc:mysql://10.2.122.8:3306/ubrecord", "root", "!QAZ2wsx3edc")
//        Connection connection = DriverManager
//                .getConnection("jdbc:postgresql://localhost:5430/commutingtime?currentSchema=myschema", "root", "secret")
        log.info("Test");
        System.out.println("Hello world!");
        try(
                Connection connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:5430/commutingtime?currentSchema=myschema", "root", "secret")
        ) {
            DatabaseMetaData metaData = connection.getMetaData();
            Scanner scanner = new Scanner(System.in);
            System.out.print("Database name:");
            String catalog = scanner.nextLine();
            System.out.println("!!!" + catalog);
            // catalog database名稱
            // schema 有些DB不需要，像是mysql
            ResultSet resultSet = metaData.getTables(catalog, "myschema", "%", new String[]{"TABLE"});
            int count = 0;
            while (resultSet.next()) {
                count++;
                String tableName = resultSet.getString("TABLE_NAME");
                System.out.println("table name: " + tableName);
                if(tableName.equals("passwordhistory")){
                    ResultSet columns = metaData.getColumns(catalog, "myschema", tableName, null);
                    while (columns.next()) {
                        String columnName = columns.getString("COLUMN_NAME");
                        System.out.println("Column Name : " + columnName);
                        String columnType = columns.getString("TYPE_NAME");
                        System.out.println("Column Type : " + columnType);
                    }
                }
            }
            System.out.println("Total number of tables : " + count);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}