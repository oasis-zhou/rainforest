package rf.saleshorizon.ds;

import rf.policyadmin.model.Endorsement;
import rf.policyadmin.model.Policy;
import rf.product.model.ProductSpec;

public interface BlockChainService {

    ProductSpec findProduct(String productCode);

    String issuePolicy(Policy policy);
    String issueEndorsement(Endorsement endorsement);

    Policy findPolicy(String policyNumber);
    Endorsement findEndorsement(String endorsementNumber);

}
