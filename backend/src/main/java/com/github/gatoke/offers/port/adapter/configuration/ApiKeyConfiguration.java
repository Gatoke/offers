package com.github.gatoke.offers.port.adapter.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Data
@Component
@ConfigurationProperties(prefix = "api-key")
public class ApiKeyConfiguration {

    private Map<String, String> clients;

    public Optional<String> findClientName(final String apiKey) {
        return clients.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(apiKey))
                .findFirst()
                .map(Map.Entry::getKey);
    }

    public String getSwaggerApiKey() {
        return clients.get("swagger-user");
    }
}
