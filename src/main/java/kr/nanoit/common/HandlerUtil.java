package kr.nanoit.common;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import kr.nanoit.dto.HttpBadResponseDto;
import kr.nanoit.util.GlobalVariable;
import kr.nanoit.util.Mapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;

import static kr.nanoit.util.GlobalVariable.*;

@Slf4j
public class HandlerUtil {

    public static void responseOk(HttpExchange exchange, byte[] rawBytes) throws IOException {
        sendHeader(exchange, rawBytes, HTTP_OK);
    }

    public static void badRequest(HttpExchange exchange, String message) {
        try {
            byte[] bytesOfBody = Mapper.writePretty(new HttpBadResponseDto(OffsetDateTime.now().toString(), HTTP_BAD_REQUEST, "BadRequest", message)).getBytes(StandardCharsets.UTF_8);
            sendHeader(exchange, bytesOfBody, HTTP_BAD_REQUEST);
        } catch (Exception e) {
            log.error("unknown error", e); // 처리할 수 없음
        }
    }

    private static void sendHeader(HttpExchange exchange, byte[] text, int HttpCode) throws IOException {
        Headers headers = exchange.getResponseHeaders();
        headers.add(GlobalVariable.HEADER_CONTENT_TYPE, GlobalVariable.APPLICATION_JSON_CHARSET_UTF_8);
        exchange.sendResponseHeaders(HttpCode, text.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(text);
        outputStream.flush();
    }
}
