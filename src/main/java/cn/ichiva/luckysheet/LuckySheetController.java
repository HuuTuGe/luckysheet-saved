package cn.ichiva.luckysheet;

import cn.ichiva.luckysheet.pojo.Record;
import cn.ichiva.luckysheet.service.RecordService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 使用整存整取的方法保存文档
 * 文档的同步请参考 LuckySheetWebSocketServer
 *
 * 本例力求最精简
 * 可要求get/set带上文件名的方式进行多文档的协同
 * 当然多文档协同也需要websocket配合进行分组广播
 */
@Slf4j
@CrossOrigin
@RestController
public class LuckySheetController {
    //默认文档
    @Value("${def}")
    String defDoc;
    //当前文档
    String doc;


    @Autowired
    RecordService recordService;

    @PostConstruct
    public void init(){
        this.doc = defDoc;
        //每天重置文档
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(() -> this.doc = defDoc,
                        24 - LocalTime.now().getHour(),
                        24,
                        TimeUnit.HOURS);
    }

    @GetMapping("/version")
    public Object version(){
        return new Object(){ public String version = "v0.1.0"; };
    }

    //取文件
    @RequestMapping("/get")
    public Object get() throws IOException {
        return doc;
    }

    //设置文件
    @PostMapping("/set")
    public Object set(@RequestParam(value = "jsonExcel") String jsonExcel,@RequestParam(value = "name") String name,@RequestParam(value = "info") String info) throws IOException {
        doc = jsonExcel;
        return recordService.record(doc,name,info);
    }

    //查看历史记录
    @RequestMapping("/viewRecord")
    public Object viewRecord() {
        List<Record> list=new ArrayList<>();
        try {
            list=recordService.viewRecord();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    //版本回溯
    @RequestMapping("/goBack")
    public boolean goBack(@RequestParam(value = "id") int id,@RequestParam(value = "name") String name) {
        doc=recordService.goBack(id);
        String info="回退到版本"+id;
        recordService.record(doc,name,info);
        System.out.println(doc);
        System.out.println(info);
        return true;
    }
}
