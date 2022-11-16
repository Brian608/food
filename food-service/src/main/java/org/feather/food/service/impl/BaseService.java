package org.feather.food.service.impl;

import com.github.pagehelper.PageInfo;
import org.feather.food.common.utils.PagedGridResult;

import java.util.List;

/**
 * @projectName: food
 * @package: org.feather.food.service.impl
 * @className: BaseService
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022-11-16 22:47
 * @version: 1.0
 */

public class BaseService {

    public PagedGridResult setterPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }
}
