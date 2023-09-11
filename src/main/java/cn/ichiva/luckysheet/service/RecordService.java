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

    List<Record> list = null;

    public List<Record> viewRecord() throws SQLException {
        if (list == null) {
            list = new ArrayList<>();
            System.out.println("查一下数据库");
            list = recordDAO.getRecords();
        }
        return list;
    }

    public boolean record(String doc, String name, String info) {
        Date date = new Date();
        list.add(new Record(list.size() + 1, doc, name, date, info));
        try {
            recordDAO.record(doc, name, date, info);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public String goBack(int id) {
        return recordDAO.goBack(id);
    }

}
