package rf.policyadmin.model;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 用于分页查询
 * Created by zhaoyi on 2017/12/27.
 */
public class ResponsePage<T> {

    private int pageSize; //每页显示条数
    private long totalCount; //总条数
    private int start; //开始条数
    private int pageNo;//当前页
    private int totalPages; //总页数
    private List<T> pageList;//数据

    public ResponsePage(Page page, List<T> pageList) {
        this.pageSize = page.getSize();
        this.totalCount = page.getTotalElements();
        this.start = page.getNumber() * pageSize;
        this.pageNo = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.pageList = pageList;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }
}
