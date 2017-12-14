package ru.taskmanager.config;

import ru.taskmanager.errors.ConfigurationException;
import ru.taskmanager.utils.StringUtils;

import javax.json.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class PlanJsonConfiguration extends JsonConfiguration {
    public class PlanKeyValue {
        private final int order;
        private final String key;
        private final List<String> value;

        PlanKeyValue(int order, String key, List<String> value) {
            this.order = order;
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public List<String> getValue() {
            return value;
        }

        public int getOrder() {
            return order;
        }
    }
    private List<PlanKeyValue> dictionary;

    public PlanJsonConfiguration(Path jsonPath) throws ConfigurationException {
        super(jsonPath);
        this.dictionary = new ArrayList<>();
    }

    @Override
    public Object getEntityByKey(String key) {
        return dictionary.stream()
                .filter(i->i.getKey().equals(key))
                .findFirst().orElse(null);
    }

    @Override
    public Set<String> getKeys() {
        return dictionary.stream()
                .map(i->i.getKey())
                .collect(Collectors.toSet());
    }

    public List<Integer> getOrdersList() {
        return dictionary.stream()
                .sorted((o1, o2) -> ((Integer)o1.getOrder()).compareTo(o2.getOrder()))
                .map(i->i.getOrder())
                .collect(Collectors.toList());
    }

    public PlanJsonConfiguration.PlanKeyValue getEntityByOrder(int order) {
        return dictionary.stream()
                .filter(i->i.getOrder() == order)
                .findFirst().orElse(null);
    }

    @Override
    public boolean containsKey(String key) {
        return dictionary.stream()
                .map(i->i.getKey())
                .filter(i->i.equals(key))
                .findAny()
                .isPresent();
    }

    @Override
    public void load() throws ConfigurationException {
        dictionary.clear();

        JsonReader jsonReader;
        try {
            jsonReader = Json.createReader(new FileInputStream(this.jsonPath.toFile().getAbsoluteFile()));
        } catch (FileNotFoundException e) {
            throw new ConfigurationException("File doesn't exist");
        }
        try {
            JsonArray json = jsonReader.readArray();
            List<JsonObject> commands = json.getValuesAs(JsonObject.class);
            for (int j = 0; j < commands.size(); j++) {
                JsonObject command = commands.get(j);
                String name = command.getString("name");
                if(!StringUtils.isNullOrEmpty(name)) {
                    JsonArray params = command.getJsonArray("params");
                    List<String> map = new ArrayList<>();
                    if (null != params) {
                        for (int i = 0; i < params.size(); i++) {
                            String parameter = params.getString(i);
                            if (!StringUtils.isNullOrEmpty(parameter)) {
                                map.add(parameter);
                            }
                        }
                    }
                    dictionary.add(new PlanKeyValue(j, name, map));
                }
            }
        } finally {
            jsonReader.close();
        }
    }
}
