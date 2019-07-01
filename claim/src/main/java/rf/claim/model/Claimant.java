package rf.claim.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;

@Data
public class Claimant extends ModelComponent {
	/* 姓名 */
	private String name;
	/* 证件类型 */
	private String idType;
	/* 证件号码 */
	private String idNumber;
	/* 账户类型 */
	private String accountType;
	/* 账户号码 */
	private String accountNumber;
	/* 账户名称 */
	private String accountName;
	/* 银行代码 */
	private String bankCode;
	/* 电子邮箱 */
	private String email;
	/* 手机号码 */
	private String mobile;
	/* 通讯地址 */
	private String address;

}
