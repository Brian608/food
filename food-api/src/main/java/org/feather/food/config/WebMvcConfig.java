package org.feather.food.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
public class WebMvcConfig  implements WebMvcConfigurer {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return  builder.build();
    }

    /**
     * 实现静态资源的映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/**")
              .addResourceLocations("classpath:/META-INF/resources/")//映射swagger2
              .addResourceLocations("D:\\dev\\files\\images");//映射本地静态资源
    }
}
