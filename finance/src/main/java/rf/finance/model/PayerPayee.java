package rf.finance.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;

@Data
public class PayerPayee  extends ModelComponent {

	private String name;
	private String idType;
	private String idNumber;

}