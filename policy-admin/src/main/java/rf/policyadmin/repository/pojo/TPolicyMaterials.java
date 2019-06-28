package rf.policyadmin.repository.pojo;

import rf.foundation.model.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by zhouzheng on 2017/11/23.
 */

@Entity
@Table(name = "T_POLICY_MATERIALS")
public class TPolicyMaterials extends BaseEntity {

    @Id
    private String uuid;
    @Column
    private String name;
    @Column
    private String path;
    @Column
    private String format;
    @Column
    private String size;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
