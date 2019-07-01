package rf.claim.ds;


import rf.claim.model.Claim;
import java.util.List;

public interface ClaimService {

    public void saveClaim(Claim claim);

    public Claim loadClaim(String claimNumber);

    public List<Claim> findClaimsByPolicyNumber(String policyNumber);

}
