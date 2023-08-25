package cn.ichiva.luckysheet.DAO;

import cn.ichiva.luckysheet.pojo.Record;
import cn.ichiva.luckysheet.utils.DBUtils;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class RecordDAO {
    public boolean record(String doc, String name, Date date,String info) throws SQLException {
        Connection connection = DBUtils.getConnection();
        Statement statement = connection.createStatement();
        java.sql.Timestamp d = new java.sql.Timestamp(date.getTime());
        System.out.println(date);
        System.out.println(d);
        String sql = "insert into record (doc,name,time,info) values (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, doc);
        preparedStatement.setString(2, name);
        preparedStatement.setTimestamp(3, d);
        preparedStatement.setString(4, info);
        preparedStatement.executeUpdate();
        connection.close();
        statement.close();
        return true;

    }

    public List<Record> getRecords() throws SQLException {
        List<Record> list = new ArrayList<>();
        ResultSet rs = null;
        Connection connection = DBUtils.getConnection();
        Statement statement = null;
        statement = connection.createStatement();
        String sql = "select * from record";
        rs = statement.executeQuery(sql);
        while (rs.next()) {
            int id = rs.getInt("id");
            String doc = rs.getString("doc");
            String name = rs.getString("name");
            Date date = rs.getTimestamp("time");
            String info = rs.getString("info");
            System.out.println(date);
            Record record = new Record(id, doc, name, date,info);
            list.add(record);
        }
        rs.close();
        statement.close();
        connection.close();
        return list;
    }

    public String goBack(int id) {
        String doc = null;
        ResultSet rs = null;
        Statement statement = null;
        try {
            Connection connection = DBUtils.getConnection();
            statement = connection.createStatement();
            String sql = "select doc from record where id =" + Integer.toString(id);
            rs = statement.executeQuery(sql);
            rs.next();
            doc = rs.getString("doc");
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return doc;
    }
}
