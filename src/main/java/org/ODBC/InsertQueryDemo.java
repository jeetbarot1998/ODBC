package org.ODBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class InsertQueryDemo {
    static ConfigReader myprops = new ConfigReader();

    public static void main(String[] args) throws Exception {
        String url = myprops.getProperty("mysql.url");
        String user = myprops.getProperty("mysql.user");
        String password = myprops.getProperty("mysql.password");

        ResultSet resultSet = null;
        Connection conn = DriverManager.getConnection(url, user, password);
        String insertQuery = "INSERT INTO sql5701956.DemoTable1 VALUES ('2','NEW VALUE');";
        String selectQuery = "select * from sql5701956.DemoTable1;";

        try {
            Statement statement = conn.createStatement();
            int count = statement.executeUpdate(insertQuery);
            boolean execute = statement.execute(selectQuery);
            System.out.println("execute response = " + execute);
            if (execute){
                resultSet = statement.getResultSet();
                while (resultSet.next()){
                    System.out.println(resultSet.getString(2));
                }
            }
            else {
                System.out.println(statement.getUpdateCount());
            }
//            System.out.println(count + " rows affected");
        }
        catch (Exception e){
            System.out.println(e);
        }
        finally {
            if (resultSet != null)
                resultSet.close();
            if (conn != null)
                conn.close();
        }
    }
}
