package org.feather.food.controller;

import org.feather.food.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: food
 * @package: org.feather.food.controller
 * @className: Stu
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/7/31 20:37
 * @version: 1.0
 */
@RequestMapping("/stu")
@RestController
public class StuController  {

    @Autowired
    private StuService stuService;

    @GetMapping("/getById")
    public  Object getById(int id){
        return stuService.getStuInfo(id);
    }
}
