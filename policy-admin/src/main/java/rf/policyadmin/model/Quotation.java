package rf.policyadmin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import rf.foundation.anno.FieldSpec;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;
import rf.policyadmin.model.enums.QuotationStatus;


import java.util.Date;
import java.util.List;
import java.util.Map;


public class Quotation extends ModelComponent {

    @FieldSpec(code = "PRODUCT_CODE", name = "policy product code")
    private String productCode;
    private String productName;
    @FieldSpec(code = "CHANNEL_CODE", name = "policy channel code")
    private String channelCode;
    @FieldSpec(code = "POLICY_EFFECTIVE_DATE", name = "policy effective date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale = "zh", timezone = "GMT+8")
    private Date effectiveDate;
    @FieldSpec(code = "POLICY_EXPIRED_DATE", name = "policy expired date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale = "zh", timezone = "GMT+8")
    private Date expiredDate;
    @FieldSpec(code = "POLICY_POI", name = "period of insurance")
    private Integer poi;
    private Date proposalDate;
    private QuotationStatus quotationStatus;
    private String businessOrgan;
    private String businessSource;
    private String quotationNumber;
    private Date quotationDate;
    private Map<String,Object> underwritingReason = Maps.newHashMap();
    private String rejectReason;
    private String extReferenceNumber;
    private String sku;
    private Customer policyHolder;
    private List<Customer> insureds = Lists.newArrayList();

    public Quotation(){
        this.setUuid(Guid.generateStrId());
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
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

    public Integer getPoi() {
        return poi;
    }

    public void setPoi(Integer poi) {
        this.poi = poi;
    }

    public Date getProposalDate() {
        return proposalDate;
    }

    public void setProposalDate(Date proposalDate) {
        this.proposalDate = proposalDate;
    }

    public QuotationStatus getQuotationStatus() {
        return quotationStatus;
    }

    public void setQuotationStatus(QuotationStatus quotationStatus) {
        this.quotationStatus = quotationStatus;
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

    public String getQuotationNumber() {
        return quotationNumber;
    }

    public void setQuotationNumber(String quotationNumber) {
        this.quotationNumber = quotationNumber;
    }

    public Date getQuotationDate() {
        return quotationDate;
    }

    public void setQuotationDate(Date quotationDate) {
        this.quotationDate = quotationDate;
    }

    public Map<String, Object> getUnderwritingReason() {
        return underwritingReason;
    }

    public void setUnderwritingReason(Map<String, Object> underwritingReason) {
        this.underwritingReason = underwritingReason;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getExtReferenceNumber() {
        return extReferenceNumber;
    }

    public void setExtReferenceNumber(String extReferenceNumber) {
        this.extReferenceNumber = extReferenceNumber;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Customer getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(Customer policyHolder) {
        this.policyHolder = policyHolder;
    }

    public List<Customer> getInsureds() {
        return insureds;
    }

    public void setInsureds(List<Customer> insureds) {
        this.insureds = insureds;
    }

}
