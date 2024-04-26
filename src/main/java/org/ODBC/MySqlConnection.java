package org.ODBC;

import java.sql.*;

public class MySqlConnection {

    static ConfigReader myprops = new ConfigReader();

    public static void main(String[] args) throws Exception {
        String url = myprops.getProperty("mysql.url");
        String user = myprops.getProperty("mysql.user");
        String password = myprops.getProperty("mysql.password");

        Connection conn = DriverManager.getConnection(url, user, password);
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery("select * from sql5701956.MyJavaTable");
        while (resultSet.next()){
            System.out.println("NAME : " + resultSet.getString(1));
            System.out.println("ID : " + resultSet.getInt(2));
            System.out.println("Inserted At : " + resultSet.getString(3));
            System.out.println("=========== Next Entry =================");
        }
    }

}
