package kr.nanoit.http;

import kr.nanoit.dto.UserDto;
import lombok.Getter;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("HTTP SERVER HANDLER 테스트 ")
class HttpServerHandlerTest {
    private SandBoxHttpServer httpServer;
    private int port;


    @BeforeEach
    void setUp() throws IOException {
        port = getRandomPort();
        httpServer = new SandBoxHttpServer("localhost", port, 123);
        httpServer.start();
    }

    @Test
    @DisplayName(" header = content type 을 요청했을때 올바르지 않으면 badRequest 가 떨어져야됨")
    void should_return_bad_request_when_uncorrected_content_type_header() throws IOException {
        // given
        String contentType = "xontent-type";
        String headerValue = "application/json";
        String url = "http://localhost:" + port + "/user";

        // when
        Response actual = post(url, contentType, headerValue);

        // then
        assertThat(actual.code).isEqualTo(400);
        assertThat(actual.header).isNotEqualTo(contentType);
        assertThat(actual.body).contains("not found: Content-Type Header");
    }

    @Test
    @DisplayName(" header = content type 을 요청했을때  application/json 이 아닌경우 badRequest 가 떨어져야됨")
    void should_return_bad_request_when_not_application_json() throws IOException {
        // given
        String contentType = "content-type";
        String headerValue = "application/xml";
        String url = "http://localhost:" + port + "/user";

        // when
        Response actual = post(url, contentType, headerValue);

        // then
        assertThat(actual.code).isEqualTo(400);
        assertThat(actual.body).contains("accept Content-Type: application/json");
    }

    @Test
    @DisplayName("UserDto에 id값이 null이면 badRequest가 떨어져야 함")
    void should_return_bad_request_when_id_is_null() throws IOException {
        // given
        String json = "{\"password\": \"test01\" , \"encryptKey\" : \"q1w2e3r4t5\"}";
        String url = "http://localhost:" + port + "/crud";
        StringEntity stringEntity = new StringEntity(json);

        // when
        Response actual = postJson(url, stringEntity);

        // then
        assertThat(actual.code).isEqualTo(400);
        assertThat(actual.body).contains("not found: user id");
    }

    @Test
    @DisplayName("UserDto에 password값이 null이면 badRequest가 떨어져야 함")
    void should_return_bad_reqeust_when_password_is_null() throws IOException {
        // given
        String json = "{\"id\": \"test01\" , \"encryptKey\" : \"q1w2e3r4t5\"}";
        String url = "http://localhost:" + port + "/crud";
        StringEntity stringEntity = new StringEntity(json);

        // when
        Response actual = postJson(url, stringEntity);

        // then
        assertThat(actual.code).isEqualTo(400);
        assertThat(actual.body).contains("not found: user password");
    }

    @Test
    @DisplayName("요청한 id값이 일치 하지 않을때 badRequest가 떨어져야 함")
    void should_return_bad_request_when_id_is_not_match() throws IOException {
        // given
        String json = "{\"id\": \"test03\" , \"password\": \"test01\" , \"encryptKey\" : \"q1w2e3r4t5y6u7i8\"}";
        String url = "http://localhost:" + port + "/crud";
        StringEntity stringEntity = new StringEntity(json);

        // when
        Response actual = postJson(url, stringEntity);

        // then
        assertThat(actual.code).isEqualTo(400);
        assertThat(actual.body).contains("Requested Id is not Match");
    }

    @Test
    @DisplayName("요청한 password값이 일치 하지 않을때 badRequest가 떨어져야 함")
    void should_return_bad_request_when_password_is_not_match() throws IOException {
        // given
        String json = "{\"id\": \"test02\" , \"password\": \"FplyNqy9UcOt5AxQpNAY8g==\" , \"encryptKey\" : \"q1w2e3r4t5y6u7i8\"}";
        String url = "http://localhost:" + port + "/crud";
        StringEntity stringEntity = new StringEntity(json);

        // when
        Response actual = postJson(url, stringEntity);

        // then
        assertThat(actual.code).isEqualTo(400);
        assertThat(actual.body).contains("Requested Password is not Match");
    }


    private Response post(String uri, String contentType, String value) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(uri);
            if (contentType != null) {
                httpPost.setHeader(contentType, value);
            }
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                return new Response(response.getCode(), Arrays.toString(response.getHeaders()), EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Response postJson(String uri, StringEntity stringEntity) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(stringEntity);
            httpPost.setHeader("Content-type", "application/json");

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                return new Response(response.getCode(), Arrays.toString(response.getHeaders()), EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int getRandomPort() {
        return new SecureRandom().nextInt(64511) + 1024;
    }

    @Getter
    static class Response {
        private int code;
        private String header;
        private String body;

        public Response(int code, String header, String body) {
            this.code = code;
            this.header = header;
            this.body = body;
        }
    }

}
