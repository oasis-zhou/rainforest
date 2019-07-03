package rf.foundation.model;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class ResponsePage<T> {
    private long totalCount; //总条数
    private int totalPages; //总页数
    private int pageNo;//当前页
    private int pageSize; //每页显示条数
    private List<T> datas;//数据

    public ResponsePage(Page page, List<T> datas) {
        this.pageSize = page.getSize();
        this.totalCount = page.getTotalElements();
        this.pageNo = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.datas = datas;
    }
}
