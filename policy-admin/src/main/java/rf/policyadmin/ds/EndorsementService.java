package rf.policyadmin.ds;

import org.springframework.data.domain.Pageable;
import rf.foundation.model.ResponsePage;
import rf.policyadmin.model.Endorsement;
import rf.policyadmin.model.QueryCondition;

import java.util.List;

public interface EndorsementService {

	String create(Endorsement endorsement);

	void save(Endorsement endorsement);

	Endorsement load(String endorsementId);
	
	void issue(Endorsement endorsement);
	
	String generateWording(Endorsement endorsement);
	
	void reject(String endorsementId);

	boolean hasPendingEndorsement(String policyNumber);

	List<Endorsement> findEndorsements(String policyNumber);

	Endorsement loadByNumber(String endorsementNumber);

	Endorsement findPendingEndorsements(String policyNumber);

	Endorsement findLastEndorsement(String policyNumber);

	List<Endorsement> findAllEndorsements(String policyNumber);

	Endorsement findEndorsementByUuid(String uuid);

	ResponsePage<Endorsement> findEndorsement(QueryCondition condition);
	
}
