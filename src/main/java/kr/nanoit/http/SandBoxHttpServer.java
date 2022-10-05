package kr.nanoit.http;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SandBoxHttpServer {
    private final HttpServer httpServer;

    public SandBoxHttpServer(String host, int port) throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress(host, port), 0);
        this.httpServer.createContext("/crud", new HttpServerHandler());
        this.httpServer.createContext("/health", new HealthHandler());
    }

    public void start() {
        httpServer.start();
    }

    public void stop(int delay) {
        httpServer.stop(delay);
    }
}
