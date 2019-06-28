package rf.policyadmin.repository.pojo;

import rf.foundation.model.BaseEntity;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_POLICY")
public class TPolicy extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1543850843781156520L;

	@Id
	private String uuid;
	@Column
	private String productCode;
	@Column
	private String productName;
	@Column
	private String channelCode;
	@Column
	private String policyNumber;
	@Column
	private Date effectiveDate;
	@Column
	private Date expiredDate;
	@Column
	private Date proposalDate;
	@Column
	private String proposalNumber;
	@Column
	private String contractStatusCode;
	@Column
	private String businessOrgan;
	@Column
	private String businessSource;
	@Column
	private Date issueDate;
	@Column
	private String quotationNumber;
	@Column
	private Date quotationDate;
	@Column
	private String terminalReasonCode;
	@Column
	private String extReferenceNumber;
	@Lob
	private String content;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
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

	public String getProposalNumber() {
		return proposalNumber;
	}

	public void setProposalNumber(String proposalNumber) {
		this.proposalNumber = proposalNumber;
	}

	public String getContractStatusCode() {
		return contractStatusCode;
	}

	public void setContractStatusCode(String contractStatusCode) {
		this.contractStatusCode = contractStatusCode;
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

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
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

	public String getTerminalReasonCode() {
		return terminalReasonCode;
	}

	public void setTerminalReasonCode(String terminalReasonCode) {
		this.terminalReasonCode = terminalReasonCode;
	}

	public String getExtReferenceNumber() {
		return extReferenceNumber;
	}

	public void setExtReferenceNumber(String extReferenceNumber) {
		this.extReferenceNumber = extReferenceNumber;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
