package com.spring.example.config;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration class to handle the generation of Swagger V2 API documentation
 * for all endpoints exposed to the application EXCEPT those provided by the
 * Spring framework (see how UNINCLUDED_BASE_PACKAGE is used)
 *
 * @suthor Jack Phillips
 */
@Configuration
@EnableSwagger2
public class SwaggerApiConfig {

    protected static final String UNINCLUDED_BASE_PACKAGE = "org.springframework";

    /* @Value pulled from application.properties */
    @Value("${app.name}")
    private String appName;

    @Value("${app.description}")
    private String appDescription;

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.contact.name}")
    private String appContactName;

    @Value("${app.contact.url}")
    private String appContactUrl;

    @Value("${app.contact.email}")
    private String appContactEmail;

    /**
     * Configure the Swagger documentation generation
     *
     * @return Docket primary interface into the swagger-springmvc framework
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage(UNINCLUDED_BASE_PACKAGE)))
                .build();
    }

    /**
     * Construct ApiInfo using the ApiInfoBuilder
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(appName)
                .description(appDescription)
                .contact(new Contact(appContactName, appContactUrl, appContactEmail))
                .version(appVersion)
                .build();
    }
}
