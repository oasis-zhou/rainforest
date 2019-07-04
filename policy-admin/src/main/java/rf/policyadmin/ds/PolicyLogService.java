package rf.policyadmin.ds;

import rf.policyadmin.model.Policy;

public interface PolicyLogService {

	void logPolicy(String endoId, String logType, String policyNumber);
	
	Policy loadLogPolicy(String endoId, String logType);
	
	void disablePolicyLog(String endoId);
}
