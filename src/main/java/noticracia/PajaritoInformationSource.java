package noticracia;

import noticracia.entities.InformationSource;
import noticracia.mappers.InformationMapper;
import noticracia.services.HttpListenerService;
import noticracia.services.UpdateHandlerService;

import java.util.Map;

public class PajaritoInformationSource extends InformationSource {
    private final HttpListenerService httpListenerService;

    public PajaritoInformationSource() {
        this.httpListenerService = new HttpListenerService();
    }

    @Override
    public void start(String searchCriteria) {
        httpListenerService.startServer(new UpdateHandlerService(this), 8080);
    }

    @Override
    public void stop() {
        httpListenerService.stopServer();
    }

    @Override
    public String getName() {
        return "Pajarito";
    }

    @Override
    public Map<String, String> mapInformation(Object result) {
        return new InformationMapper().mapInformation((String) result);
    }

}