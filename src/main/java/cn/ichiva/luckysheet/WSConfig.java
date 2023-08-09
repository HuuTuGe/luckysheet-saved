package cn.ichiva.luckysheet;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Slf4j
@Configuration
public class WSConfig {

    //启动ws服务
    @Bean
    public ServerEndpointExporter getWebSocketServer() throws Exception {
        return new ServerEndpointExporter();
    }
}
