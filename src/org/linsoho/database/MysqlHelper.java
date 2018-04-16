package org.linsoho.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlHelper {
    
    private static final String classNm = "com.mysql.cj.jdbc.Driver";
    private static String url;
    private static String user;
    private static String password;
    
    private static Connection conn;
    private static PreparedStatement pst;
    
    private static MysqlHelper instance;
    
    public static MysqlHelper getInstance() {
        if (instance == null) {
            instance = new MysqlHelper();
        }
        return instance;
    }
    
    private MysqlHelper() {
        url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
        user = "root";
        password = "ABCabc123";
    }
    
    public ResultSet query(String sql) {
        ResultSet set = null;
        System.out.println("---------------------------------------------");
        System.out.println(sql);
        System.out.println("---------------------------------------------");
        try {
            Class.forName(classNm);
            conn = DriverManager.getConnection(url, user, password);
            pst = conn.prepareStatement(sql);
            set = pst.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }
    
    public void update(String sql) {
        System.out.println("---------------------------------------------");
        System.out.println(sql);
        System.out.println("---------------------------------------------");
        try {
            Class.forName(classNm);
            conn = DriverManager.getConnection(url, user, password);
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Boolean insert(String sql) {
        System.out.println("---------------------------------------------");
        System.out.println(sql);
        System.out.println("---------------------------------------------");
        try {
            Class.forName(classNm);
            conn = DriverManager.getConnection(url, user, password);
            pst = conn.prepareStatement(sql);
            return pst.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
            if (pst != null) {
                pst.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
