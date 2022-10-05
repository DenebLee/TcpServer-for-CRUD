package kr.nanoit.http;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import kr.nanoit.config.Crypt;
import kr.nanoit.dto.ServerInfo;
import kr.nanoit.dto.UserDto;
import kr.nanoit.exception.HttpException;
import kr.nanoit.main.Main;
import kr.nanoit.util.Mapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static kr.nanoit.common.HandlerUtil.badRequest;
import static kr.nanoit.common.HandlerUtil.responseOk;
import static kr.nanoit.util.GlobalVariable.HEADER_CONTENT_TYPE;

/*
 Client 는 json 바디에 사용자 인증을 위한 id, password @GET 으로
 */
@Slf4j
public class HttpServerHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        try {
            log.info("Addresses accessed by clients [{}]", exchange.getLocalAddress());
            String body = CharStreams.toString(new InputStreamReader(exchange.getRequestBody(), Charsets.UTF_8));
            String password;
            UserDto userDto = getRead(body);

            // Json 검증
            if (!exchange.getRequestHeaders().containsKey(HEADER_CONTENT_TYPE)) {
                throw new HttpException("not found: Content-Type Header");
            }
            if (!exchange.getRequestHeaders().get(HEADER_CONTENT_TYPE).get(0).equalsIgnoreCase("application/json")) {
                throw new HttpException("You must be requested in the form of json");
            }
            Crypt crypt = new Crypt();

            // @Request Param 검증
            if (userDto.getId() != null) {
                if (userDto.getPassword() != null) {
                    if (userDto.getEncryptKey() != null) {
                        crypt.cryptInit(userDto.getEncryptKey());
                        password = new String(crypt.deCrypt(userDto.getPassword()));
                    } else {
                        throw new HttpException("not found: user encryptKey");
                    }
                } else {
                    throw new HttpException("not found: user password");
                }
            } else {
                throw new HttpException("not found: user id");
            }

            if (password.equals(Main.configuration.getString("password"))) {
                log.info("Reqeusted Password is correct! id = [{}] , password = [{}]", userDto.getId(), password);
                ServerInfo serverInfo = new ServerInfo();
                serverInfo.setHost(Main.configuration.getString("host")).setPort(Main.configuration.getInt("port"));
                responseOk(exchange, Mapper.writePretty(serverInfo).getBytes(StandardCharsets.UTF_8));
            }


        } catch (HttpException e) {
            badRequest(exchange, e.getReason());
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("HttpHandler error", e);
        }


    }

    private UserDto getRead(String body) {
        try {
            return Mapper.read(body, UserDto.class);
        } catch (Exception e) {
            log.error("Json Read error", e);
            return null;
        }
    }
}
