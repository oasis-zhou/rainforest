package rf.policyadmin.model;

import java.util.Date;

/**
 * EndorsementQueryCondition class
 *
 * @author zhaoyi
 * @date 17-12-27
 */
public class EndorsementQueryCondition {
    private String policyNumber;
    private String endorsementNumber;
    private String productCode;
    private String channelCode;
    private String applicationName;
    private String statusCode;
    private Date applicationDateStart;
    private Date applicationDateEnd;

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getEndorsementNumber() {
        return endorsementNumber;
    }

    public void setEndorsementNumber(String endorsementNumber) {
        this.endorsementNumber = endorsementNumber;
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

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Date getApplicationDateStart() {
        return applicationDateStart;
    }

    public void setApplicationDateStart(Date applicationDateStart) {
        this.applicationDateStart = applicationDateStart;
    }

    public Date getApplicationDateEnd() {
        return applicationDateEnd;
    }

    public void setApplicationDateEnd(Date applicationDateEnd) {
        this.applicationDateEnd = applicationDateEnd;
    }

    @Override
    public String toString() {
        return "EndorsementQueryCondition{" +
                "policyNumber='" + policyNumber + '\'' +
                ", endorsementNumber='" + endorsementNumber + '\'' +
                ", productCode='" + productCode + '\'' +
                ", channelCode='" + channelCode + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", applicationDateStart=" + applicationDateStart +
                ", applicationDateEnd=" + applicationDateEnd +
                '}';
    }
}
