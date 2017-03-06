package com.example.task.service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        ApiInfo apiInfo = new ApiInfoBuilder()
            .title("TaskService REST API")
            .description("Microservice for creating and assigning task to users")
            .version("1.0")
            .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("tasks-service")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/v1/tasks.*"))
                .build();
    }
//    @Autowired
//    private SpringSwaggerConfig springSwaggerConfig;
//
//    @Bean
//    public SwaggerSpringMvcPlugin configureSwagger() {
//        SwaggerSpringMvcPlugin swaggerSpringMvcPlugin = new SwaggerSpringMvcPlugin(this.springSwaggerConfig);
//
//        ApiInfo apiInfo = new ApiInfoBuilder()
//                .title("TaskService REST API")
//                .description("Service for creating and assigning task to users")
//                .build();
//
//        swaggerSpringMvcPlugin
//                .apiInfo(apiInfo)
//                .apiVersion("1.0")
//                .includePatterns("/v1/tasks/*.*").swaggerGroup("v1");
//
//        swaggerSpringMvcPlugin.useDefaultResponseMessages(false);
//
//        return swaggerSpringMvcPlugin;
//    }
}
