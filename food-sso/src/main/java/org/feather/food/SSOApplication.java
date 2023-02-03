package org.feather.food;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @projectName: food
 * @package: org.feather.food
 * @className: SsoApplication
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-02-03 9:52
 * @version: 1.0
 */
@SpringBootApplication
@MapperScan(basePackages = "org.feather.food.mapper")
@ComponentScan(basePackages = {"org.feather","org.n3r.idworker"})
public class SSOApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringApplication.class,args);
    }
}
