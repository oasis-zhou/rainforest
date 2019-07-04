package rf.inshorizon.ds;

import rf.claim.model.Claim;

public interface ClaimService {
    Claim pullFromChain(String noticeNumber);
}
