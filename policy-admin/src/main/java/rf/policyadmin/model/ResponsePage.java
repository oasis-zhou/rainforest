package rf.policyadmin.model;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
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
}
