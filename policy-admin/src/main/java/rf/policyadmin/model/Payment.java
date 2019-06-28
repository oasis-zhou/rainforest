package rf.policyadmin.model;

import rf.foundation.model.ModelComponent;
import rf.policyadmin.model.enums.PaymentMethod;
import rf.policyadmin.model.enums.PaymentSatus;

import java.util.Date;

public class Payment extends ModelComponent {

    private PaymentMethod method;
    private String payer;
    private String payerIdType;
    private String payerIdNumber;
    private String amount;
    private String paymentNumber;
    private Date paymentDate;
    private PaymentSatus satus;

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayerIdType() {
        return payerIdType;
    }

    public void setPayerIdType(String payerIdType) {
        this.payerIdType = payerIdType;
    }

    public String getPayerIdNumber() {
        return payerIdNumber;
    }

    public void setPayerIdNumber(String payerIdNumber) {
        this.payerIdNumber = payerIdNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentSatus getSatus() {
        return satus;
    }

    public void setSatus(PaymentSatus satus) {
        this.satus = satus;
    }
}
