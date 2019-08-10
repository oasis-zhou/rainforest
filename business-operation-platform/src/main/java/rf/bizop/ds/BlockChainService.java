package rf.bizop.ds;

import rf.policyadmin.model.Endorsement;
import rf.policyadmin.model.Policy;
import rf.product.model.ProductSpec;

import java.util.List;

public interface BlockChainService {

    String releaseProduct(ProductSpec productSpec);
    ProductSpec findProduct(String productCode);

    Policy findPolicy(String policyNumber);
    Endorsement findEndorsement(String endorsementNumber);

    List<Policy> findPendingPolicies();
    List<Endorsement> findPendingEndorsements();

    String withdrawPendingPolicy(String policyNumber);
    String withdrawPendingEndorsement(String endorsementNumber);
}
