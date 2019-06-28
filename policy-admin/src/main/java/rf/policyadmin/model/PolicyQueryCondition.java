package rf.policyadmin.model;

import java.util.Date;

/**
 * Created by zhouzheng on 2017/5/24.
 */
public class PolicyQueryCondition {

    private String policyNumber;
    private String proposalNumber;
    private String productCode;
    private String channelCode;
    private String policyHolderName;
    private String policyHolderIdNumber;
    private String policyInsuredName;
    private String policyInsuredIdNumber;
    private String mobile;
    private String contractStatus;
    private Date proposalDateStart;
    private Date proposalDateEnd;

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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getPolicyHolderName() {
        return policyHolderName;
    }

    public void setPolicyHolderName(String policyHolderName) {
        this.policyHolderName = policyHolderName;
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

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Date getProposalDateStart() {
        return proposalDateStart;
    }

    public void setProposalDateStart(Date proposalDateStart) {
        this.proposalDateStart = proposalDateStart;
    }

    public Date getProposalDateEnd() {
        return proposalDateEnd;
    }

    public void setProposalDateEnd(Date proposalDateEnd) {
        this.proposalDateEnd = proposalDateEnd;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    @Override
    public String toString() {
        return "PolicyQueryCondition{" +
                "policyNumber='" + policyNumber + '\'' +
                ", proposalNumber='" + proposalNumber + '\'' +
                ", productCode='" + productCode + '\'' +
                ", channelCode='" + channelCode + '\'' +
                ", policyHolderName='" + policyHolderName + '\'' +
                ", policyHolderIdNumber='" + policyHolderIdNumber + '\'' +
                ", policyInsuredName='" + policyInsuredName + '\'' +
                ", policyInsuredIdNumber='" + policyInsuredIdNumber + '\'' +
                ", mobile='" + mobile + '\'' +
                ", contractStatus='" + contractStatus + '\'' +
                ", proposalDateStart=" + proposalDateStart +
                ", proposalDateEnd=" + proposalDateEnd +
                '}';
    }
}
