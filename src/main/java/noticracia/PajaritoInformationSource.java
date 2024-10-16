package noticracia;

import noticracia.entities.InformationSource;
import noticracia.mappers.InformationMapper;
import noticracia.services.ServerService;
import noticracia.services.UpdateHandlerService;

import java.util.HashMap;
import java.util.Map;

public class PajaritoInformationSource extends InformationSource {
    private final ServerService serverService;

    public PajaritoInformationSource() {
        this.serverService = new ServerService();
    }

    @Override
    public void start(String searchCriteria) {
        serverService.startServer(new UpdateHandlerService(this), 8080);
    }

    @Override
    public void stop() {
        serverService.stopServer();
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