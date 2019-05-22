package info.biyesheji.sheji.entity;

import java.io.Serializable;

public class ProductParam implements Serializable {
    private static final long serialVersionUID = 80178505739562602L;
    public static Integer 下载量倒序 = 1;
    public static Integer 价格倒序 = 2;
    public static Integer 星数倒序 = 3;
    private Integer startPrice;
    private Integer endPrice;
    private String name;
    private Integer type;
    private Integer sortType;
    private Integer pageNum;
    private Integer pageSize;

    public Integer getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Integer startPrice) {
        this.startPrice = startPrice;
    }

    public Integer getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(Integer endPrice) {
        this.endPrice = endPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
