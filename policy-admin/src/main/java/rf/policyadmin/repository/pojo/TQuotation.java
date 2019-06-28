package rf.policyadmin.repository.pojo;

import rf.foundation.model.BaseEntity;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_QUOTATION")
public class TQuotation extends BaseEntity {


	/**
	 * 
	 */
	private static final long serialVersionUID = -315110592954558641L;

	@Id
	private String uuid;
	@Column
	private String productCode;
	@Column
	private String productName;
	@Column
	private String channelCode;
	@Column
	private Date effectiveDate;
	@Column
	private Date expiredDate;
	@Column
	private Date proposalDate;
	@Column
	private String quotationStatusCode;
	@Column
	private String businessOrgan;
	@Column
	private String businessSource;
	@Column
	private String quotationNumber;
	@Column
	private Date quotationDate;
	@Column
	private String rejectReason;
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

	public String getQuotationStatusCode() {
		return quotationStatusCode;
	}

	public void setQuotationStatusCode(String quotationStatusCode) {
		this.quotationStatusCode = quotationStatusCode;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	


//	@PrePersist
//	@Override
//	protected void prePersist(){
//		this.setCreationTime(DateContext.getLocalTimeByCurrentUser());
//		this.setModificationTime(DateContext.getLocalTimeByCurrentUser());
//		
//		AuthenticationFacade authFacade = ApplicationContextUtils.getBean(AuthenticationFacade.class);
//		if(authFacade!=null) {
//			if(authFacade.getAuthentication()!=null) {
//				this.setCreatedBy(authFacade.getAuthentication().getName());
//				this.setModifiedBy(authFacade.getAuthentication().getName());
//			} else {
//				this.setCreatedBy("_UNKNOWN");
//				this.setModifiedBy("_UNKNOWN");
//			}
////			this.company = authFacade.getCompany();
////			this.country = authFacade.getCountry();
//		}
//	}

}
