package techpal.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbConnector {

    public DbConnector(){
    }

    private Connection createConnection(){
        Connection conn = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String strConnString = "jdbc:oracle:thin:@localhost:1521:xe";
            conn = DriverManager.getConnection(strConnString,"bp_1","ssvveenn");
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return conn;
    }

    public ResultSet getData(String strSQL){
        ResultSet result = null;
        try{
            Statement stmt = createConnection().createStatement();
            result = stmt.executeQuery(strSQL);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return result;
    }

    public int executeDML(String strSQL){
        int result = 0;
        try{
            Statement stmt = createConnection().createStatement();
            result = stmt.executeUpdate(strSQL);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return result;
    }
}