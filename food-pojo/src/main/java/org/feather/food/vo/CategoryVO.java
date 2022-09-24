package org.feather.food.vo;

import java.util.List;

/**
 * @projectName: food
 * @package: org.feather.food.vo
 * @className: CategoryVO
 * @author: feather(杜雪松)
 * @description: 二级分类Vo
 * @since: 2022/9/24 22:52
 * @version: 1.0
 */
public class CategoryVO {
    private  Integer id;

    private String name;

    private String type;

    private String fatherId;

    private List<SubCategoryVO> subCatList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public List<SubCategoryVO> getSubCatList() {
        return subCatList;
    }

    public void setSubCatList(List<SubCategoryVO> subCatList) {
        this.subCatList = subCatList;
    }
}
