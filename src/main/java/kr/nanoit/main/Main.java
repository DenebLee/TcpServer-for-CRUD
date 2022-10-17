package kr.nanoit.main;

import java.io.File;
import java.net.ServerSocket;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import kr.nanoit.util.Verification;
import kr.nanoit.http.SandBoxHttpServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;

// 1. = i0lrqk1OkkbOxMTIkCm3EA== 14166
// 2. = FplyNqy9UcOt5AxQpNAY8g==

@Slf4j
public class Main {

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
    public static Configuration configuration;
    public static Map<String, Verification> verificationMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        final Configurations configurations = new Configurations();
        configuration = configurations.properties(new File("src/main/java/resources/test.properties"));
        int count = configuration.getInt("user.id.count");
        for (int i = 1; i < count; i++) {
            String id = configuration.getString("user.id." + i);
            String password = configuration.getString("user.password." + i);
            String encryptKey = configuration.getString("user.encryptKey." + i);

            verificationMap.put(id, Verification.builder()
                    .id(id)
                    .password(password)
                    .encryptKey(encryptKey)
                    .build());
        }
        try {
            log.info("[TCP SERVER & HTTP SERVER START] {}", SIMPLE_DATE_FORMAT.format(new Date()));
            ServerSocket serverSocket = new ServerSocket(getRandomPort());

            SandBoxHttpServer sandBoxHttpServer = new SandBoxHttpServer(configuration.getString("http.host"), configuration.getInt("http.port"), getRandomPort());
            sandBoxHttpServer.start();

//            SocketUtil socketUtil = new SocketUtil(serverSocket.accept());

            // Thread list


        } catch (Exception e) {
            log.warn("critical error in server {} ", e.getMessage());
        }
    }

    private static int getRandomPort() {
        return new SecureRandom().nextInt(64511) + 1024;
    }
}
