package kr.nanoit.http;

import kr.nanoit.old.http.SandBoxHttpServer;
import lombok.Getter;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import static org.assertj.core.api.Assertions.*;

@DisplayName("HTTP SERVER 테스트")
class SandBoxHttpServerTest {

    private int port;

    @BeforeEach
    void setPort() {
        port = getRandomPort();
    }

    @Test
    @DisplayName("HttpServer 가 켜져있는지  ")
    void should_turn_on_http_server() throws IOException {
        // given
        SandBoxHttpServer expected = new SandBoxHttpServer("localhost", port,111);
        expected.start();

        // when
        Response actual = get("http://localhost:" + port + "/health");

        // then
        assertThat(actual.code).isEqualTo(200);
        assertThat(actual.body).isEqualTo("{\"health\": \"OK\"}");
    }

    private int getRandomPort() {
        return new SecureRandom().nextInt(64511) + 1024;
    }

    private Response get(String uri) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(uri);
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                return new Response(response.getCode(), EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Getter
    static class Response {
        private int code;
        private String body;

        public Response(int code, String body) {
            this.code = code;
            this.body = body;
        }
    }
}
