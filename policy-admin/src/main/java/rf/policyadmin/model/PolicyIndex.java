package rf.policyadmin.model;

import rf.foundation.model.ModelComponent;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhouzheng on 2017/5/3.
 */
public class PolicyIndex extends ModelComponent {

    private String policyNumber;
    private String proposalNumber;
    private String quotationNumber;
    private String productName;
    private String productCode;
    private String channelCode;
    private BigDecimal sgp;
    private BigDecimal snp;
    private BigDecimal app;
    private BigDecimal limitAmount;
    private String policyHolderName;
    private String policyHolderIdType;
    private String policyHolderIdNumber;
    private String policyInsuredName;
    private String policyInsuredIdType;
    private String policyInsuredIdNumber;
    private String mobile;
    private Date effectiveDate;
    private Date expiredDate;
    private Date proposalDate;
    private Date issueDate;
    private String contractStatusCode;
    private Date terminalDate;
    private String terminalReasonCode;
    private String businessOrgan;
    private String businessSource;
    private String extReferenceNumber;

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getProposalNumber() {
        return proposalNumber;
    }

    public void setProposalNumber(String proposalNumber) {
        this.proposalNumber = proposalNumber;
    }

    public String getQuotationNumber() {
        return quotationNumber;
    }

    public void setQuotationNumber(String quotationNumber) {
        this.quotationNumber = quotationNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public BigDecimal getSgp() {
        return sgp;
    }

    public void setSgp(BigDecimal sgp) {
        this.sgp = sgp;
    }

    public BigDecimal getSnp() {
        return snp;
    }

    public void setSnp(BigDecimal snp) {
        this.snp = snp;
    }

    public BigDecimal getApp() {
        return app;
    }

    public void setApp(BigDecimal app) {
        this.app = app;
    }

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    public String getPolicyHolderName() {
        return policyHolderName;
    }

    public void setPolicyHolderName(String policyHolderName) {
        this.policyHolderName = policyHolderName;
    }

    public String getPolicyHolderIdType() {
        return policyHolderIdType;
    }

    public void setPolicyHolderIdType(String policyHolderIdType) {
        this.policyHolderIdType = policyHolderIdType;
    }

    public String getPolicyHolderIdNumber() {
        return policyHolderIdNumber;
    }

    public void setPolicyHolderIdNumber(String policyHolderIdNumber) {
        this.policyHolderIdNumber = policyHolderIdNumber;
    }

    public String getPolicyInsuredName() {
        return policyInsuredName;
    }

    public void setPolicyInsuredName(String policyInsuredName) {
        this.policyInsuredName = policyInsuredName;
    }

    public String getPolicyInsuredIdType() {
        return policyInsuredIdType;
    }

    public void setPolicyInsuredIdType(String policyInsuredIdType) {
        this.policyInsuredIdType = policyInsuredIdType;
    }

    public String getPolicyInsuredIdNumber() {
        return policyInsuredIdNumber;
    }

    public void setPolicyInsuredIdNumber(String policyInsuredIdNumber) {
        this.policyInsuredIdNumber = policyInsuredIdNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Date getProposalDate() {
        return proposalDate;
    }

    public void setProposalDate(Date proposalDate) {
        this.proposalDate = proposalDate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getContractStatusCode() {
        return contractStatusCode;
    }

    public void setContractStatusCode(String contractStatusCode) {
        this.contractStatusCode = contractStatusCode;
    }

    public Date getTerminalDate() {
        return terminalDate;
    }

    public void setTerminalDate(Date terminalDate) {
        this.terminalDate = terminalDate;
    }

    public String getTerminalReasonCode() {
        return terminalReasonCode;
    }

    public void setTerminalReasonCode(String terminalReasonCode) {
        this.terminalReasonCode = terminalReasonCode;
    }

    public String getBusinessOrgan() {
        return businessOrgan;
    }

    public void setBusinessOrgan(String businessOrgan) {
        this.businessOrgan = businessOrgan;
    }

    public String getBusinessSource() {
        return businessSource;
    }

    public void setBusinessSource(String businessSource) {
        this.businessSource = businessSource;
    }

    public String getExtReferenceNumber() {
        return extReferenceNumber;
    }

    public void setExtReferenceNumber(String extReferenceNumber) {
        this.extReferenceNumber = extReferenceNumber;
    }
}
