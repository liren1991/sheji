package info.biyesheji.sheji.util;

import java.util.List;

public class PageResult<T> {
    List<T> beanList;
    private Integer pageNum;
    private Integer totalSize;
    private Integer totalPageCount;
    public PageResult() {
    }

    public PageResult(List<T> beanList) {
        this.beanList = beanList;
    }

    public List<T> getBeanList() {
        return this.beanList;
    }

    public void setBeanList(List<T> beanList) {
        this.beanList = beanList;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public Integer getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(Integer totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public static Integer staticTotalPageCount(int totalSize, int pageSize) {
        int totalPageCount = totalSize / pageSize;
        if (totalPageCount < 1)
            totalPageCount = 1;
        if (totalSize % pageSize > 0 && totalSize > pageSize)
            ++totalPageCount;

        return totalPageCount;
    }
}
