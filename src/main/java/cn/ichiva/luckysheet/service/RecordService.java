package cn.ichiva.luckysheet.service;

import cn.ichiva.luckysheet.DAO.RecordDAO;
import cn.ichiva.luckysheet.pojo.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class RecordService {

    @Autowired
    RecordDAO recordDAO;

    public List<Record> viewRecord() throws SQLException {
        List<Record> list=new ArrayList<>();
        list=recordDAO.getRecords();
        return list;
    }

    public boolean record(String doc,String name) {
        Date date = new Date();
        try {
            recordDAO.record(doc, name, date);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public String goBack(int id) {
        return recordDAO.goBack(id);
    }
}
