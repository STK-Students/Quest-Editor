package stk.students;

import org.yaml.snakeyaml.Yaml;
import stk.students.utils.Color;
import stk.students.utils.ColorUtil;

import java.io.InputStream;
import java.util.Map;

/**
 * Handles loading and getting values from a config file.
 * Specifically, it reads data from messages.yml.
 */
public class ConfigManager {

    /**
     * Contains all message strings from the message resource file.
     */
    private final Map<String, Object> configData;

    public ConfigManager(final String resourceFileName) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resourceFileName);

        Yaml yaml = new Yaml();
        configData = yaml.load(inputStream);
    }

    /**
     * Gets a string message from the config.
     * You can only use this on method on config keys that hold a string.
     *
     * @param configKey the config key
     * @return a white config value
     */
    public String getMessage(String configKey) {
        return getMessage(configKey, Color.WHITE);
    }

    /**
     * Gets a string message from the config.
     * You can only use this on method on config keys that hold a string.
     *
     * @param configKey the config key
     * @param colors    colors to apply to the string
     * @return a colorized config value
     */
    public String getMessage(String configKey, Color... colors) {
        String message;
        try {
            message = (String) get(configKey);
        } catch (ClassCastException exception) {
            throw new UnsupportedOperationException("You can only use this on method on config keys that hold a string.");
        }
        return ColorUtil.colorize(message, colors);
    }

    /**
     * Recursively gets the value for the specified path.
     * A path contains one or more keys. Keys represent different levels in the YAML tree structure.
     * They must be seperated by a dot. Example:
     * <pre>
     * {@code
     * root:
     *   levelOne:
     *     levelTwo: "data"}
     * </pre>
     * The data can then be obtained by calling: <br> {@code get("root.levelOne.levelTwo", configRoot)}
     *
     * @param path path to a config key
     * @return the data at the specified path
     */
    public Object get(String path) {
        return get(path, configData);
    }

    /**
     * Recursively gets the value for the specified path.
     * A path contains one or more keys. Keys represent different levels in the YAML tree structure.
     * They must be seperated by a dot. Example:
     * <pre>
     * {@code
     * root:
     *   levelOne:
     *     levelTwo: "data"}
     * </pre>
     * The data can then be obtained by calling: <br> {@code get("root.levelOne.levelTwo", configRoot)}
     *
     * @param path   path to a config key
     * @param config config to search in
     * @return the data at the specified path
     */
    public Object get(final String path, final Map<String, Object> config) {
        final int splitIndex = path.indexOf('.');
        final String firstKey = splitIndex == -1 ? path : path.substring(0, splitIndex);
        final String restKey = splitIndex == -1 ? null : path.substring(splitIndex + 1);
        if (restKey == null) {
            if (config.containsKey(firstKey)) {
                return config.get(firstKey);
            }
            throw new IllegalArgumentException("Key: '" + firstKey + "' does not exist!");
        }
        return getSubConfig(config, firstKey, restKey);
    }

    private Object getSubConfig(final Map<String, Object> config, final String firstKey, final String restKey) {
        final Map<String, Object> subConfig;
        if (config.containsKey(firstKey)) {
            subConfig = (Map<String, Object>) config.get(firstKey);
            return get(restKey, subConfig);
        }
        throw new IllegalArgumentException("Key: '" + firstKey + "' does not exist!");
    }
}
