package noticracia.services;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpListenerService {
    private HttpServer server;
    private final ExecutorService executorService;

    public HttpListenerService() {
        this.executorService = Executors.newSingleThreadExecutor();
}

    public void startServer(HttpHandler handler, int port) {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/receive", handler);
            executorService.submit(() -> {
                System.out.println("Servidor escuchando en http://localhost:" + port + "/receive");
                server.start();
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopServer() {
        if (server != null) {
            server.stop(0);
        }
        executorService.shutdown();
        System.out.println("Servidor detenido.");
    }
}
