package rf.policyadmin.model;

import lombok.Data;
import rf.policyadmin.model.enums.CancellationType;


@Data
public class Cancellation extends Endorsement {

    private CancellationType cancellationType;
    private String reason;

}
