package stk.students;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

public class ConfigManager {

    private static final String RESOURCE_FILE_NAME = "messages.yml";

    /**
     * Contains all message strings from the message resource file.
     */
    private Map<String, Object> configData;

    public ConfigManager() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(RESOURCE_FILE_NAME);

        Yaml yaml = new Yaml();
        configData = yaml.load(inputStream);
    }

    /**
     * Gets the config as a parsed map.
     * @return unmodifiable view of the config
     */
    public Map<String, Object> getConfigData() {
        return Collections.unmodifiableMap(configData);
    }

    /**
     * Gets a string message from the config.
     * You can only use this on method on config keys that hold a string.
     * @param configKey the config key
     * @param colors colors to apply to the string
     * @return a colorized config value
     */
    public String getMessage(String configKey, Color... colors) {
        String message;
        try {
             message = (String) configData.get(configKey);
        } catch (ClassCastException exception) {
            throw new UnsupportedOperationException("You can only use this on method on config keys that hold a string.");

        }
        return ColorUtil.colorize(message, colors);
    }

    /**
     * Gets a string message from the config.
     * You can only use this on method on config keys that hold a string.
     * @param configKey the config key
     * @return a white config value
     */
    public String getMessage(String configKey) {
        return getMessage(configKey, Color.WHITE);
    }
}
