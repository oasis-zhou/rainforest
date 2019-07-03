package rf.policyadmin.ds;

import org.springframework.data.domain.Pageable;
import rf.foundation.model.ResponsePage;
import rf.policyadmin.model.Endorsement;
import rf.policyadmin.model.QueryCondition;

import java.util.List;

public interface EndorsementService {

	public String create(Endorsement endorsement);

	public void save(Endorsement endorsement);

	public Endorsement load(String endorsementId);
	
	public void issue(Endorsement endorsement);
	
	public String generateWording(Endorsement endorsement);
	
	public void reject(String endorsementId);

	public boolean hasPendingEndorsement(String policyNumber);

	public List<Endorsement> findEndorsements(String policyNumber);

	public Endorsement loadByNumber(String endorsementNumber);

	public Endorsement findPendingEndorsements(String policyNumber);

	public Endorsement findLastEndorsement(String policyNumber);

	public List<Endorsement> findAllEndorsements(String policyNumber);

	public Endorsement findEndorsementByUuid(String uuid);

	ResponsePage<Endorsement> findEndorsement(QueryCondition condition);
	
}
