package rf.claim.model;


import lombok.Data;
import rf.claim.model.enums.ClaimStatus;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Claim extends ModelComponent
{

	/**
	 * 理赔号
	 */
	private String claimNumber;
	/**
	 * 产品代码
	 */
	private String productCode;
	/**
	 * 保单号
	 */
	private String policyNumber;
	/**
	 * 出险时间
	 */
	private Date accidentTime;
	/**
	 * 理赔申请时间
	 */
	private Date claimTime;
	/**
	 * 出险原因
	 */
	private String accidentCause;
	/**
	 * 事故描述
	 */
	private String accidentDescription;
	/**
	 * 索赔人信息
	 */
	private Claimant claimant;

	/**
	 * 申请理赔金额
	 */
	private BigDecimal claimAmount;
	/**
	 * 支付理赔金额
	 */
	private BigDecimal paymentAmount;
	/**
	 * 备注信息
	 */
	private String memo;
	/**
	 * 结案日期
	 */
	private Date closeDate;
	/**
	 * 结案意见
	 */
	private String closingOpinion;
	/**
	 * 理赔状态
	 */
	private ClaimStatus status;
	/**
	 * 拒赔原因
	 */
	private String rejectReason;

	/**
	 * 累计赔付次数
	 */
	private int numberOfClaim;

	public Claim(){
		this.setUuid(Guid.generateStrId());
	}

}