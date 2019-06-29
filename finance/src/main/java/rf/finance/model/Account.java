package rf.finance.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;

@Data
public class Account extends ModelComponent {

	private String type;
	private String accountNo;
	private String name;
	private String bank;


}