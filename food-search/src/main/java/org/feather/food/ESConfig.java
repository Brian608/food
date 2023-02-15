package org.feather.food;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @projectName: food
 * @package: org.feather.food
 * @className: ESConfig
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-02-07 15:03
 * @version: 1.0
 */

@Configuration
public class ESConfig {

    /**
     * 解决netty引起的issue
     */
    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
}
