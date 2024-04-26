package org.ODBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class TransactionCommitAndRollback {
    static ConfigReader myprops = new ConfigReader();

    public static void main(String[] args) throws Exception {
        String url = myprops.getProperty("mysql.url");
        String user = myprops.getProperty("mysql.user");
        String password = myprops.getProperty("mysql.password");
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println("Before transaction");
        String selectQuery = "select * from sql5701956.DemoTable1;";
        Statement statement = conn.createStatement();
        ResultSet resultSet1 = statement.executeQuery(selectQuery);
        while (resultSet1.next()){
            System.out.println(resultSet1.getString(1) + " -- " +  resultSet1.getString(2));
        }

        System.out.println("Tx begins");
        conn.setAutoCommit(false);
        statement.executeUpdate("INSERT INTO sql5701956.DemoTable1 VALUES ('3','NEW VALUE 3');");
        statement.executeUpdate("INSERT INTO sql5701956.DemoTable1 VALUES ('4','NEW VALUE 4');");
        System.out.println(" Should we add new values? Y or N");
        Scanner sc = new Scanner(System.in);
        String option = sc.next();
        if(option.equalsIgnoreCase("Y")){
            conn.commit();
            System.out.println("Commited");
        }
        else{
            conn.rollback();
            System.out.println("Rolled back");
        }
        System.out.println("After transaction");

        resultSet1 = statement.executeQuery(selectQuery);
        while (resultSet1.next()){
            System.out.println(resultSet1.getString(1) + " -- " +  resultSet1.getString(2));
        }
        conn.close();
    }
}
