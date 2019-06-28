package rf.policyadmin.repository.pojo;

import rf.foundation.model.BaseEntity;
import javax.persistence.*;

@Entity
@Table(name = "T_POLICY_LOG")
public class TPolicyLog extends BaseEntity {

    @Id
    private String uuid;
    @Column
    private String endoId;
    @Column
    private String policyNumber;
    @Column
    private String logType;
    @Lob
    private String content;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEndoId() {
        return endoId;
    }

    public void setEndoId(String endoId) {
        this.endoId = endoId;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
