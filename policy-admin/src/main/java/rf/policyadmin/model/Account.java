package rf.policyadmin.model;

import rf.foundation.model.ModelComponent;

/**
 * Created by zhouzheng on 2017/6/13.
 */
public class Account extends ModelComponent {

    private String accountName;
    private String idNumber;
    private String accountNumber;
    private String bankCode;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
