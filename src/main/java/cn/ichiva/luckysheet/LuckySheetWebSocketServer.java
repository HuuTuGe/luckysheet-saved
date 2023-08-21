package cn.ichiva.luckysheet;

import cn.ichiva.luckysheet.utils.PakoGzipUtils;
import cn.ichiva.luckysheet.utils.ResponseDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本例只进行单文档同步
 * 多文档同步需要按文档名分组广播
 */
@Component
@ServerEndpoint("/{name}")
@Slf4j
public class LuckySheetWebSocketServer {
    //存储连接及昵称
    static final Map<Session, String> connMap = new ConcurrentHashMap<>();

    static int id=-1;

    private String name;

    private int myId;

    @OnOpen
    public void onOpen(Session conn, @PathParam("name") String name) throws IOException {
        this.name = name;
        connMap.put(conn, name);
        log.info("{} 加入,在线人数 = {}", name, connMap.size());
        myId=++id%100000;
        conn.getBasicRemote().sendText(JSON.toJSONString(new ResponseDTO(1,Integer.toString(myId),name,null)));
    }

    @OnClose
    public void onClose(Session conn) {
        String name = connMap.remove(conn);
        log.info("{} 离开", name);
    }

    @OnMessage
    public void onMessage(String message, Session conn) {
        if (null != message && message.length() != 0) {
            try {
                if ("rub".equals(message)) {
                    return;
                }
                String unMessage = PakoGzipUtils.unCompressURI(message);
                if (log.isTraceEnabled()) log.trace(unMessage);

                JSONObject jsonObject = JSON.parseObject(unMessage);
                //广播
                connMap.forEach((socket, n) -> {
                    //排除自己
                    if (conn == socket) return;

                    try {
                        if ("mv".equals(jsonObject.getString("t"))) {
                            socket.getBasicRemote().sendText(JSON.toJSONString(new ResponseDTO(3, Integer.toString(myId), name, unMessage)));
                        } else if (!"shs".equals(jsonObject.getString("t"))) {
                            socket.getBasicRemote().sendText(JSON.toJSONString(new ResponseDTO(2, Integer.toString(myId) , name, unMessage)));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session conn, Throwable ex) {
        log.warn("链接错误", ex);
        try {
            conn.close();
        } catch (IOException e) {
            log.error("错误链接关闭失败", e);
        }
    }
}
