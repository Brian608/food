package org.feather.food.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @projectName: food
 * @package: org.feather.food.config
 * @className: WebMvcConfig
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/10/24 07:28
 * @version: 1.0
 */
@Configuration
public class WebMvcConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return  builder.build();
    }
}
