package org.feather.food;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;
/**
 * @projectName: food
 * @package: org.feather.food
 * @className: FsApplication
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-02-15 16:02
 * @version: 1.0
 */
@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class})
@MapperScan(basePackages = "org.feather.mapper")
@ComponentScan(basePackages = {"org.feather", "org.n3r.idworker"})
public class FsApplication {
    public static void main(String[] args) {
        SpringApplication.run(FsApplication.class,args);
    }
}
