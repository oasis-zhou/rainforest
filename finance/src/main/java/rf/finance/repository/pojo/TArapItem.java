package rf.finance.repository.pojo;

import rf.foundation.model.BaseEntity;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "T_ARAP_ITEM")
public class TArapItem extends BaseEntity {

    @Id
    private String uuid;
    @Column
    private String transType;
    @Column
    private String refBizNumber;
    @Column
    private String refExtNumber;
    @Column
    private Date transDate;
    @Column
    private String feeCode;
    @Column
    private BigDecimal amount;
    @Column
    private BigDecimal balance;
    @Column
    private String currency;
    @Column
    private Date dueDate;
    @Lob
    @Column(length = 10000)
    private String content;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getRefBizNumber() {
        return refBizNumber;
    }

    public void setRefBizNumber(String refBizNumber) {
        this.refBizNumber = refBizNumber;
    }

    public String getRefExtNumber() {
        return refExtNumber;
    }

    public void setRefExtNumber(String refExtNumber) {
        this.refExtNumber = refExtNumber;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public String getFeeCode() {
        return feeCode;
    }

    public void setFeeCode(String feeCode) {
        this.feeCode = feeCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
