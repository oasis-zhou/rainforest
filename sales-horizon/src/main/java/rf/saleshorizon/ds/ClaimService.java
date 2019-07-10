package rf.saleshorizon.ds;

import rf.claim.model.Claim;

public interface ClaimService {
    Claim pullFromChain(String noticeNumber);
}
