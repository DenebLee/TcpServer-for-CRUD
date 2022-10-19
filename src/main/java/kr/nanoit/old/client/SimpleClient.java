package kr.nanoit.old.client;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import kr.nanoit.old.http.HttpResponseDto;
import kr.nanoit.old.util.Mapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Slf4j
public class SimpleClient {

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");

    public static void main(String[] args) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            int port = 8000;
            String url = "http://localhost:" + port + "/crud";

            String json = "{\"id\": \"test02\" , \"password\": \"FplyNqy9UcOt5AxQpNAY8g==\" , \"encryptKey\" : \"q1w2e3r4t5y6u7i8\"}";
            StringEntity stringEntity = new StringEntity(json);

            log.info("[HTTPCLIENT] START {}", SIMPLE_DATE_FORMAT.format(new Date()));

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(stringEntity);
            httpPost.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = httpclient.execute(httpPost);
            Response responseReuslt = new Response(response.getCode(), Arrays.toString(response.getHeaders()));

            log.info("[HTTPCLIENT] Response Data Header : {} , code : {}", responseReuslt.getHeader(), responseReuslt.getCode());

            String body = CharStreams.toString(new InputStreamReader(response.getEntity().getContent(), Charsets.UTF_8));
            HttpResponseDto httpResponseDto = getRead(body);


            /*  Http complete
            --------------------------------------------------------------------------------------------------------------
             */


            Socket socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress(httpResponseDto.getHost(), Integer.parseInt(httpResponseDto.getPort()));
            socket.connect(socketAddress);
            


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static HttpResponseDto getRead(String body) {
        try {
            return Mapper.read(body, HttpResponseDto.class);
        } catch (Exception e) {
            log.error("Json Read error", e);
            return null;
        }
    }

    @Getter
    static class Response {
        private int code;
        private String header;

        public Response(int code, String header) {
            this.code = code;
            this.header = header;
        }
    }

}
