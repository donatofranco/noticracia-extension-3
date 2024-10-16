package noticracia;

import noticracia.entities.InformationSource;
import noticracia.mappers.InformationMapper;
import noticracia.services.ServerService;
import noticracia.services.UpdateHandlerService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PajaritoInformationSource extends InformationSource {
    private final ServerService serverService;

    public PajaritoInformationSource() {
        this.serverService = new ServerService();
    }

    @Override
    public void start(String searchCriteria) {
        this.searchCriteria = searchCriteria;
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
    public Map<String, String> mapInformation(Object response) {
        String[] words = this.searchCriteria.toLowerCase().split(" ");

        return new InformationMapper().mapInformation((String) response).entrySet().stream()
                //Le quito las informaciones que no traten de mi searchCriteria
                .filter(e -> e.getValue().toLowerCase().contains(this.searchCriteria.toLowerCase()))
                .collect(Collectors.toMap(
                        //Le quito las apariciones del searchCriteria
                        Map.Entry::getKey,
                        e -> {
                            String modifiedValue = e.getValue();
                            for (String word : words) {
                                modifiedValue = modifiedValue.replace(word, "");
                            }
                            return modifiedValue;
                        }
                ));
    }


}