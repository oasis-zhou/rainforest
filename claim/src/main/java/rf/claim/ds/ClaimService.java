package rf.claim.ds;


import rf.claim.model.Claim;
import rf.claim.model.NoticeOfLoss;
import rf.claim.model.QueryCondition;
import rf.foundation.model.ResponsePage;

import java.util.List;

public interface ClaimService {

    void saveClaim(Claim claim);

    Claim loadClaim(String claimNumber);

    List<Claim> findClaimsByPolicyNumber(String policyNumber);

    ResponsePage<Claim> queryClaim(QueryCondition condition);
}
