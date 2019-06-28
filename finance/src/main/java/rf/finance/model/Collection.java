package rf.finance.model;

import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;
import java.math.BigDecimal;


public class Collection extends ModelComponent {

    private String transactionType;
    private String refBizNumber;
    private String refExtNumber;
    private String feeCode;
    private BigDecimal amount;
    private String currency;
    private PayerPayee payerPayee;

    public Collection(){
        this.setUuid(Guid.generateStrId());
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public PayerPayee getPayerPayee() {
        return payerPayee;
    }

    public void setPayerPayee(PayerPayee payerPayee) {
        this.payerPayee = payerPayee;
    }
}
