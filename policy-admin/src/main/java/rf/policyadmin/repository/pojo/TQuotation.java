package rf.policyadmin.repository.pojo;

import lombok.Data;
import rf.foundation.model.BaseEntity;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "T_QUOTATION")
public class TQuotation extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -315110592954558641L;

	@Id
	private String uuid;
	private String productCode;
	private String productName;
	private String channelCode;
	private Date effectiveDate;
	private Date expiredDate;
	private String quotationStatusCode;
	private String businessOrgan;
	private String businessSource;
	private String quotationNumber;
	private Date quotationDate;
	private String rejectReason;
	private String extReferenceNumber;
	@Lob
	private String content;

}
