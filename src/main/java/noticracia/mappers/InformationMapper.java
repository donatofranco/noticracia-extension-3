package noticracia.mappers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InformationMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, String> mapInformation(String jsonString) {
        return parseJson(jsonString)
                .filter(JsonNode::isArray)
                .map(this::convertJsonToMap)
                .orElseGet(HashMap::new);
    }

    private Optional<JsonNode> parseJson(String jsonString) {
        try {
            return Optional.ofNullable(objectMapper.readTree(jsonString));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Map<String, String> convertJsonToMap(JsonNode jsonArray) {
        Map<String, String> informationMap = new HashMap<>();
        jsonArray.forEach(node -> {
            String link = Optional.ofNullable(node.get("link")).map(JsonNode::asText).orElse("");
            String info = Optional.ofNullable(node.get("information")).map(JsonNode::asText).orElse("");
            informationMap.put(link, info);
        });
        return informationMap;
    }
}
