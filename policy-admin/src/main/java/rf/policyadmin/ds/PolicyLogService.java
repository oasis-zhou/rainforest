package rf.policyadmin.ds;

import rf.policyadmin.model.Policy;

public interface PolicyLogService {

	public void logPolicy(String endoId, String logType, String policyNumber);
	
	public Policy loadLogPolicy(String endoId, String logType);
	
	public void disablePolicyLog(String endoId);
}
