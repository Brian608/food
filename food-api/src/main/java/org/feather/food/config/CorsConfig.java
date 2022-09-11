package org.feather.food.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @projectName: food
 * @package: org.feather.food.config
 * @className: CorsConfig
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/11 07:47
 * @version: 1.0
 */
@Configuration
public class CorsConfig {

    public  CorsConfig(){

    }
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration corsConfiguration=new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:8080");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");

        //为url添加映射路径
        UrlBasedCorsConfigurationSource corsSource=new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**",corsConfiguration);

        return  new CorsFilter(corsSource);

    }
}
