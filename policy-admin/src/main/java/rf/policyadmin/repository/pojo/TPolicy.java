package rf.policyadmin.repository.pojo;

import lombok.Data;
import rf.foundation.model.BaseEntity;
import javax.persistence.*;
import java.util.Date;

@Entity

@Data
@Table(name = "T_POLICY")
public class TPolicy extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1543850843781156520L;

	@Id
	private String uuid;
	private String productCode;
	private String productName;
	private String channelCode;
	private String policyNumber;
	private Date effectiveDate;
	private Date expiredDate;
	private Date proposalDate;
	private String proposalNumber;
	private String contractStatusCode;
	private String businessOrgan;
	private String businessSource;
	private Date issueDate;
	private String quotationNumber;
	private Date quotationDate;
	private String terminalReasonCode;
	private String extReferenceNumber;
	@Lob
	private String content;

}
