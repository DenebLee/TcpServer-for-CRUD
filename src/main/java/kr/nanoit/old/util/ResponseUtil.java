package kr.nanoit.old.util;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import kr.nanoit.model.dto.HttpBadResponseDto;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;

@Slf4j
public class ResponseUtil {

    public static void responseOk(HttpExchange exchange, byte[] rawBytes) throws IOException {
        sendHeader(exchange, rawBytes, GlobalVariable.HTTP_OK);
    }

    public static void badRequest(HttpExchange exchange, String message) {
        try {
            byte[] bytesOfBody = Mapper.writePretty(new HttpBadResponseDto(OffsetDateTime.now().toString(), GlobalVariable.HTTP_BAD_REQUEST, "BadRequest", message)).getBytes(StandardCharsets.UTF_8);
            sendHeader(exchange, bytesOfBody, GlobalVariable.HTTP_BAD_REQUEST);
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

    public static void internalServerError(HttpExchange exchange, String message) {
        try {
            byte[] bytesOfBody = Mapper.writePretty(new HttpBadResponseDto(OffsetDateTime.now().toString(), GlobalVariable.HTTP_INTERNAL_SERVER_ERROR, "Internal Server Error", message)).getBytes(StandardCharsets.UTF_8);
            sendHeader(exchange, bytesOfBody, GlobalVariable.HTTP_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("internal server process in unknown error", e);
        }
    }
}
