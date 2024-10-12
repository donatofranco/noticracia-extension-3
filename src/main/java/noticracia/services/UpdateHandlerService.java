package noticracia.services;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import noticracia.entities.InformationSource;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class UpdateHandlerService implements HttpHandler {
    private final InformationSource informationSource;

    public UpdateHandlerService(InformationSource informationSource) {
        this.informationSource = informationSource;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            String result = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

            informationSource.notify(informationSource.mapInformation(result));

            String response = "Actualización recibida";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
    }
}