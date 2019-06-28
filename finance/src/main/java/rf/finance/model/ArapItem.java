package rf.finance.model;

import rf.finance.model.enums.TransactionType;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;

import java.math.BigDecimal;
import java.util.Date;


public class ArapItem extends ModelComponent {

    private TransactionType transType;
    private String refBizNumber;
    private String refExtNumber;
    private Date transDate;
    private String feeCode;
    private BigDecimal amount;
    private BigDecimal balance;
    private String currency;
    private Date dueDate;
    private PayerPayee payerPayee;

    public ArapItem(){
        this.setUuid(Guid.generateStrId());
    }

    public TransactionType getTransType() {
        return transType;
    }

    public void setTransType(TransactionType transType) {
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

    public PayerPayee getPayerPayee() {
        return payerPayee;
    }

    public void setPayerPayee(PayerPayee payerPayee) {
        this.payerPayee = payerPayee;
    }
}
