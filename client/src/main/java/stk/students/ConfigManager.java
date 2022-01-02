package stk.students;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class ConfigManager {

    private static final String RESOURCE_FILE_NAME = "messages.yml";

    /**
     * Contains all message strings from the message resource file.
     */
    public static Map<String, Object> messages;

    public ConfigManager() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(RESOURCE_FILE_NAME);

        Yaml yaml = new Yaml();
        messages = yaml.load(inputStream);
    }
}
