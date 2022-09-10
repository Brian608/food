package org.feather.food.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @projectName: food
 * @package: org.feather.food.config
 * @className: Swagger2
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/11 07:11
 * @version: 1.0
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    //配置swagger2的核心配置
    @Bean
    public Docket createRestApi(){
        return  new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select().apis(RequestHandlerSelectors.basePackage("org.feather.food.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private  ApiInfo apiInfo(){
        return  new ApiInfoBuilder()
                .title("天天吃货电商平台接口api")
                .contact(new Contact("feather","https:xxx.com","Brian608@163.com"))
                .description("天天吃货电商平台接口api")
                .version("1.0.1")
                .termsOfServiceUrl("https:xxx.com")
                .build();
    }
}
