package cn.ichiva.luckysheet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
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
    public Object set(String jsonExcel) throws IOException {
        doc = jsonExcel;
        return true;
    }
}
