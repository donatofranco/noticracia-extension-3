package noticracia.services;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerService {
    private HttpServer server;
    private ExecutorService executorService;

    public void startServer(HttpHandler handler, int port) {
        try {
            this.stopServer();
            this.executorService = Executors.newSingleThreadExecutor();
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/receive", handler);
            executorService.submit(() -> {
                server.start();
                System.out.println("Servidor escuchando en http://localhost:" + port + "/receive");
                server.setExecutor(executorService);
            });
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void stopServer() {
        if (Objects.nonNull(server)) {
            server.stop(0);
            System.out.println("Servidor detenido.");
        }
        if (Objects.nonNull(executorService) && !executorService.isShutdown()){
            executorService.shutdown();
        }
    }
}
