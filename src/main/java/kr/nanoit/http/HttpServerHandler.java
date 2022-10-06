package kr.nanoit.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import kr.nanoit.controller.SocketConfig;
import kr.nanoit.util.Crypt;
import kr.nanoit.dto.HttpBadResponseDto;
import kr.nanoit.dto.ServerInfo;
import kr.nanoit.dto.UserDto;
import kr.nanoit.exception.HttpException;
import kr.nanoit.main.Main;
import kr.nanoit.util.Mapper;
import kr.nanoit.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.OffsetDateTime;

import static kr.nanoit.util.GlobalVariable.*;
import static kr.nanoit.util.ResponseUtil.*;

@Slf4j
public class HttpServerHandler implements HttpHandler {
    private Crypt crypt;
    private int tcpPort;

    public HttpServerHandler(int tcpPort) {
        crypt = new Crypt();
        this.tcpPort = tcpPort;
    }

    private SocketConfig socketConfig;

    private String password;

    @Override
    public void handle(HttpExchange exchange) {
        if (METHOD_POST.equals(exchange.getRequestMethod())) {
            try {
                log.info("Addresses accessed by clients [{}]", exchange.getLocalAddress());
                UserDto userDto = getRead(exchange);

                // Json 검증
                if (!exchange.getRequestHeaders().containsKey(HEADER_CONTENT_TYPE)) {
                    throw new HttpException("not found: Content-Type Header");
                }
                if (!exchange.getRequestHeaders().get(HEADER_CONTENT_TYPE).get(0).equalsIgnoreCase("application/json")) {
                    throw new HttpException("You must be requested in the form of json");
                }
                if (userDto.getId() == null) throw new HttpException("not found: user id");
                if (userDto.getPassword() == null) throw new HttpException("not found: user password");
                if (userDto.getEncryptKey() == null) throw new HttpException("not found: user encryptKey");

                if (Main.verificationMap.containsKey(userDto.getId())) {
                    crypt.cryptInit(Main.verificationMap.get(userDto.getId()).getEncryptKey());
                    password = new String(crypt.deCrypt(userDto.getPassword()));
                    if (Main.verificationMap.get(userDto.getId()).getPassword().contains(password)) {
                        log.info("Reqeusted Password is correct! id = [{}] , password = [{}]", userDto.getId(), password);
                        ServerInfo serverInfo = new ServerInfo();
                        serverInfo.setHost(Main.configuration.getString("tcp.host")).setPort(tcpPort);

                        responseOk(exchange, Mapper.writePretty(serverInfo).getBytes(StandardCharsets.UTF_8));

                    } else {
                        throw new HttpException("Requested Password is not Match");
                    }
                } else {
                    throw new HttpException("Requested Id is not Match");
                }
            } catch (HttpException e) {
                badRequest(exchange, e.getReason());
            } catch (BadPaddingException e) {
                badRequest(exchange, "It provided the wrong encryption key and the wrong password encrypted with that key");
            } catch (Exception e) {
                e.printStackTrace();
                log.warn("HttpHandler error", e);
                internalServerError(exchange, "internalServer Error");
            }
        }else{
            badRequest(exchange,"you must request POST");
        }
    }

    private UserDto getRead(HttpExchange exchange) throws IOException {
        String body = CharStreams.toString(new InputStreamReader(exchange.getRequestBody(), Charsets.UTF_8));
        try {
            return Mapper.read(body, UserDto.class);
        } catch (Exception e) {
            log.error("Json Read error", e);
            return null;
        }
    }

}
