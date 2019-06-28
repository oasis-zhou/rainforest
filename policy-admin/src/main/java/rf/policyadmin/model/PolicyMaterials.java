package rf.policyadmin.model;

import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;

import java.util.Date;

/**
 * Created by zhouzheng on 2017/11/23.
 */
public class PolicyMaterials extends ModelComponent {

    private String name;
    private String path;
    private Date createTime;
    private String format;
    private String size;

    public PolicyMaterials(){
        this.setUuid(Guid.generateStrId());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
