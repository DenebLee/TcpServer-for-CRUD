package kr.nanoit.tcp;

import kr.nanoit.controller.SocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;

import java.io.*;
import java.net.ServerSocket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@Slf4j
public class GateWayMain {

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");

    public static void main(String[] args) {
        log.info("[TCPSERVER START ] {}", SIMPLE_DATE_FORMAT.format(new Date()));

        try {

            Properties properties = new Properties();
            InputStream propertiesStream = Resources.getResourceAsStream("resource.properties");

            if (propertiesStream != null) {
                properties.load(propertiesStream);
                log.info("Read property file successfully");
            } else {
                log.warn("Failed to read property file");
            }

            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(properties.getProperty("tcp.server.port")));

            SocketUtil socketUtil = new SocketUtil(serverSocket.accept());
            // Thread list
            Thread receive = new Thread(new ReceiveServer(socketUtil, properties));
            receive.setName("Receive-Server");

            Thread send = new Thread(new SendServer(socketUtil, properties));
            send.setName("Send-Server");

            receive.start();
            send.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
