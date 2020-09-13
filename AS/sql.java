package AS;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class sql {
    public static void main(String[] args) throws IOException {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
        String user = "root";
        String password = "ls991025";

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(driver);
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "INSERT INTO user(name,password) VALUES(?,?)";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,"ls");
            ptmt.setString(2, "991025");
            ptmt.execute();
            //rs.close();
            stmt.close();
            ptmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}