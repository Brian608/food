package org.feather.food.service;

import org.feather.food.common.utils.PagedGridResult;

/**
 * @projectName: food
 * @package: org.feather.food.service
 * @className: ItemsEsService
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-02-15 11:36
 * @version: 1.0
 */
public interface ItemsEsService {

    PagedGridResult searchItems(String keywords,
                               String sort,
                               Integer page,
                               Integer pageSize);


}
