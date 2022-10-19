package kr.nanoit.old.http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import kr.nanoit.old.util.GlobalVariable;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HealthHandler implements HttpHandler {

    public static final String HEALTH_OK = "{\"health\": \"OK\"}";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (GlobalVariable.METHOD_GET.equals(exchange.getRequestMethod())) {
            byte[] bytesOfBody = HEALTH_OK.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().add(GlobalVariable.HEADER_CONTENT_TYPE, GlobalVariable.APPLICATION_JSON_CHARSET_UTF_8);
            exchange.sendResponseHeaders(200, bytesOfBody.length);

            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(bytesOfBody);
            outputStream.close();
        } else {
            try {
                Headers headers = exchange.getResponseHeaders();
                headers.add(GlobalVariable.HEADER_CONTENT_TYPE, GlobalVariable.APPLICATION_JSON_CHARSET_UTF_8);
                exchange.sendResponseHeaders(405, -1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
