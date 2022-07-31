package org.feather.food.service;

import org.feather.food.pojo.Stu;

/**
 * @projectName: food
 * @package: org.feather.food.service
 * @className: StuService
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/7/31 20:24
 * @version: 1.0
 */
public interface StuService {

    Stu getStuInfo(int id);

    void save(Stu stu);

    void  update(Stu stu);

    void delete(int id);
}
