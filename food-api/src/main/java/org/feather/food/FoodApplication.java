package org.feather.food;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import tk.mybatis.spring.annotation.MapperScan;
/**
 * @projectName: food
 * @package: org.feather.food
 * @className: FoodApplication
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/7/21 08:08
 * @version: 1.0
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
// 扫描 mybatis 通用 mapper 所在的包
@MapperScan(basePackages = "org.feather.food.mapper")
//@EnableTransactionManagement
//扫描所有包以及相关组件
@ComponentScan(basePackages = {"org.feather","org.n3r.idworker"})
@EnableScheduling       // 开启定时任务
@EnableRedisHttpSession //开启使用redis 作为spring  session
public class FoodApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoodApplication.class,args);
    }

}
