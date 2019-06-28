package rf.policyadmin.repository.pojo;

import rf.foundation.model.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhouzheng on 2017/5/3.
 */
@Entity
@Table(name = "T_POLICY_INDEX")
public class TPolicyIndex extends BaseEntity {

    @Id
    private String uuid;
    @Column
    private String policyNumber;
    @Column
    private String proposalNumber;
    @Column
    private String quotationNumber;
    @Column
    private String productName;
    @Column
    private String productCode;
    @Column
    private String channelCode;
    @Column
    private BigDecimal sgp;
    @Column
    private BigDecimal snp;
    @Column
    private BigDecimal app;
    @Column
    private BigDecimal limitAmount;
    @Column
    private String policyHolderName;
    @Column
    private String policyHolderIdType;
    @Column
    private String policyHolderIdNumber;
    @Column
    private String policyInsuredName;
    @Column
    private String policyInsuredIdType;
    @Column
    private String policyInsuredIdNumber;
    @Column
    private String mobile;
    @Column
    private Date effectiveDate;
    @Column
    private Date expiredDate;
    @Column
    private Date proposalDate;
    @Column
    private Date issueDate;
    @Column
    private String contractStatusCode;
    @Column
    private Date terminalDate;
    @Column
    private String terminalReasonCode;
    @Column
    private String businessOrgan;
    @Column
    private String businessSource;
    @Column
    private String extReferenceNumber;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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
