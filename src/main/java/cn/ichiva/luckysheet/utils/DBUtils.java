package cn.ichiva.luckysheet.utils;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DBUtils {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luckysheet_db?useUnicode=true&characterEncoding=utf8&useSSL=true";
        String username = "root";
        String password = "a135764321";
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
}
