package com.theradiary.ispwtheradiary.engineering.others;

import java.io.IOException;
import java.util.Properties;

public class FXMLPathConfig {
    private final Properties properties;

    public FXMLPathConfig(String configFile) throws IOException {
        properties = new Properties();
        // Tenta di aprire uno stream di input dal file di configurazione specificato
        try (var resource = getClass().getResourceAsStream(configFile)) {
            if (resource == null) {
                throw new IOException("Path non trovato " + configFile);
            }
            // Carica le proprietà dal file di configurazione tramite lo stream
            properties.load(resource);
        }
    }

    public String getFXMLPath(String key) {
        return properties.getProperty(key);
    }
}
