package rf.policyadmin.ds.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rf.foundation.pub.Guid;
import rf.foundation.utils.JsonHelper;
import rf.policyadmin.ds.PolicyLogService;
import rf.policyadmin.model.Policy;
import rf.policyadmin.model.enums.LogType;
import rf.policyadmin.repository.PolicyDao;
import rf.policyadmin.repository.PolicyLogDao;
import rf.policyadmin.repository.pojo.TPolicy;
import rf.policyadmin.repository.pojo.TPolicyLog;

@Service
public class PolicyLogServiceImpl implements PolicyLogService {

	@Autowired
	private PolicyDao policyDao;

	@Autowired
	private PolicyLogDao logDao;
	
	@Autowired
	private JsonHelper jsonHelper;

	@Override
	public void logPolicy(String endoId, String logType, String policyNumber) {
		TPolicy policy = policyDao.findByPolicyNumber(policyNumber);

		TPolicyLog policyLog = new TPolicyLog();
		policyLog.setUuid(Guid.generateStrId());
		policyLog.setEndoId(endoId);
		policyLog.setPolicyNumber(policyNumber);
		policyLog.setLogType(logType);

		policyLog.setContent(policy.getContent());

		logDao.save(policyLog);
	}

	@Override
	public Policy loadLogPolicy(String endoId, String logType) {
		TPolicyLog policyLog = logDao.findByEndoInfo(endoId,logType);

		Policy policy = jsonHelper.fromJSON(policyLog.getContent(), Policy.class);

		return policy;
	}

	@Override
	public void disablePolicyLog(String endoId) {

		TPolicyLog policyLog = logDao.findByEndoInfo(endoId, LogType.ISSUE_LOG.name());

		policyLog.setLogType(LogType.INVALID_ISSUE_LOG.name());

		logDao.save(policyLog);

	}

}
