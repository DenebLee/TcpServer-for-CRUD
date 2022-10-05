package kr.nanoit.main;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import kr.nanoit.http.SandBoxHttpServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;

@Slf4j
public class Main {

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
    public static Configuration configuration;

    public static void main(String[] args) throws Exception {
        final Configurations configurations = new Configurations();
        configuration = configurations.properties(new File("src/main/java/resources/test.properties"));

        try {
            log.info("[TCP SERVER & HTTP SERVER START] {}", SIMPLE_DATE_FORMAT.format(new Date()));

            SandBoxHttpServer sandBoxHttpServer = new SandBoxHttpServer(configuration.getString("http.host"), configuration.getInt("http.port"));
            sandBoxHttpServer.start();

//            ServerSocket serverSocket = new ServerSocket(configuration.getInt("port"));


        } catch (Exception e) {
            log.warn("critical error in server {} ", e.getMessage());
        }
    }

}
