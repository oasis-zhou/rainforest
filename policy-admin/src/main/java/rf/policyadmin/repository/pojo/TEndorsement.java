package rf.policyadmin.repository.pojo;

import lombok.Data;
import rf.foundation.model.BaseEntity;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity

@Data
@Table(name = "T_ENDORSEMENT")
public class TEndorsement extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7088855087702936961L;

	@Id
	private String uuid;
	private String policyNumber;
	private String productCode;
	private String code;
	private String name;
	private String type;
	private String statusCode;
	private String channelCode;
	private Date effectiveDate;
	private Date applicationDate;
	private Date issueDate;
	private String applicationName;
	private String endorsementNumber;
	private String wording;
	private String applyType;
	private Integer sequence;
	private BigDecimal premium;
	@Lob
	private String content;

}
