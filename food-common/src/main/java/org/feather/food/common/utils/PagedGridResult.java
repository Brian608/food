package org.feather.food.common.utils;

import java.util.List;

/**
 * @projectName: food
 * @package: org.feather.food.common.utils
 * @className: PagedGridResult
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/26 21:31
 * @version: 1.0
 */
public class PagedGridResult {
    private int page;			// 当前页数
    private int total;			// 总页数
    private long records;		// 总记录数
    private List<?> rows;		// 每行显示的内容

    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public long getRecords() {
        return records;
    }
    public void setRecords(long records) {
        this.records = records;
    }
    public List<?> getRows() {
        return rows;
    }
    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
