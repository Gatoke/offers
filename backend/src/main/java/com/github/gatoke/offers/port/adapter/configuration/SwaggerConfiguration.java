package com.github.gatoke.offers.port.adapter.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "swagger.enabled", havingValue = "true")
class SwaggerConfiguration {

    private final ApiKeyConfiguration apiKeyConfiguration;

    @Bean
    Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .forCodeGeneration(true)
                .globalRequestParameters(globalRequestParameters())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
    }

    private List<RequestParameter> globalRequestParameters() {
        final RequestParameter requestParameter = new RequestParameterBuilder()
                .name("x-api-key")
                .description("Api key header for authorization")
                .in(ParameterType.HEADER)
                .required(true)
                .query(q ->
                        q.defaultValue(apiKeyConfiguration.getSwaggerApiKey())
                                .model(modelSpecificationBuilder ->
                                        modelSpecificationBuilder.scalarModel(ScalarType.STRING))
                )
                .build();
        return List.of(requestParameter);
    }
}
