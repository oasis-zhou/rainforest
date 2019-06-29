package rf.policyadmin.ds.impl;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import rf.foundation.numbering.NumberingFactor;
import rf.foundation.numbering.NumberingService;
import rf.foundation.numbering.NumberingType;
import rf.foundation.utils.JsonHelper;
import rf.policyadmin.pub.Constants;
import rf.policyadmin.ds.EndorsementService;
import rf.policyadmin.ds.PolicyLogService;
import rf.policyadmin.ds.PolicyService;
import rf.policyadmin.model.Endorsement;
import rf.policyadmin.model.EndorsementQueryCondition;
import rf.policyadmin.model.Policy;
import rf.policyadmin.model.ResponsePage;
import rf.policyadmin.model.enums.EndorsementStatus;
import rf.policyadmin.model.enums.LogType;
import rf.policyadmin.repository.EndorsementDao;
import rf.policyadmin.repository.pojo.TEndorsement;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class EndorsementServiceImpl implements EndorsementService {

	@Autowired
	private EndorsementDao endoDao;

	@Autowired
	private PolicyLogService logService;

	@Autowired
	private PolicyService policyService;


	@Autowired
	private JsonHelper jsonHelper;

	@Autowired
	private NumberingService numberingService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public String create(Endorsement endorsement){

		Assert.notNull(endorsement.getPolicyNumber());
		Assert.notNull(endorsement.getEndorsementType());

		endorsement.setEndorsementStatus(EndorsementStatus.QUOTATION);
		save(endorsement);

		String endoId = endorsement.getUuid();

		//backup policy to log table
		logService.logPolicy(endorsement.getUuid(), LogType.ISSUE_LOG.name(), endorsement.getPolicyNumber());

		return endoId;
	}

	@Override
	public void save(Endorsement endorsement) {

		TEndorsement po = endoDao.findById(endorsement.getUuid()).get();
		if(po == null)
			po = new TEndorsement();

		BeanUtils.copyProperties(endorsement, po);
		po.setType(endorsement.getEndorsementType().name());
		if(endorsement.getEndorsementStatus() != null)
			po.setStatusCode(endorsement.getEndorsementStatus().name());

		if(endorsement.getApplicationType() != null)
			po.setApplyType(endorsement.getApplicationType().name());

		if(endorsement.getSequence() == null){

			Integer maxSequence = endoDao.findMaxSequence(endorsement.getPolicyNumber());
			Integer sequence = new Integer(1);

			if(maxSequence != null){
				sequence = maxSequence + 1;
			}
			endorsement.setSequence(sequence);
			po.setSequence(sequence);

		}

		String content = jsonHelper.toJSON(endorsement);

		po.setContent(content);

		endoDao.save(po);

	}

	@Override
	public Endorsement load(String endorsementId) {
		TEndorsement po = endoDao.findById(endorsementId).get();

		Endorsement endorsement = jsonHelper.fromJSON(po.getContent(), Endorsement.class);

		return endorsement;
	}


	@Override
	public void issue(Endorsement endorsement) {

		endorsement.setIssueDate(new Date());
		endorsement.setEndorsementStatus(EndorsementStatus.ISSUE);

		if(endorsement.getEndorsementNumber() == null){
			Map<NumberingFactor, String> factors = new HashMap<NumberingFactor, String>();
			Date date = new Date();
			factors.put(NumberingFactor.TRANS_YEAR, new SimpleDateFormat("yyyy").format(date));

			//E{863100}4{TRANS_YEAR}7{SEQUENCE}
			String endorsementNumber = numberingService.generateNumber(NumberingType.ENDORSEMENT_NUMBER,factors);
			endorsement.setEndorsementNumber(endorsementNumber);
		}

		BigDecimal app = endorsement.getEndoFeeByCode(Constants.FEE_APP).getValue();
		endorsement.setPremium(app);

		save(endorsement);
	}

	@Override
	public String generateWording(Endorsement endorsement){

		return "";
	}

	@Override
	public void reject(String endorsementId){

		TEndorsement p = endoDao.findById(endorsementId).get();

		p.setStatusCode(EndorsementStatus.REJECT.name());

		endoDao.save(p);

		//TODO recall policy info
		Policy policy = logService.loadLogPolicy(endorsementId, LogType.ISSUE_LOG.name());

		policyService.savePolicy(policy);

		logService.disablePolicyLog(endorsementId);
	}

	public boolean hasPendingEndorsement(String policyNumber){
		TEndorsement endorsement = endoDao.findPendingEndorsement(policyNumber);

		Boolean result = false;
		if(endorsement != null)
			result = true;

		return result;
	}

	@Override
	public List<Endorsement> findEndorsements(String policyNumber) {
		List<TEndorsement> pos = endoDao.findEndorsement(policyNumber);
		List<Endorsement> endorsements = Lists.newArrayList();

		for(TEndorsement po : pos){
			Endorsement endorsement = jsonHelper.fromJSON(po.getContent(), Endorsement.class);
			endorsements.add(endorsement);
		}
		return endorsements;
	}

	@Override
	public Endorsement findPendingEndorsements(String policyNumber) {
		Endorsement endorsement = null;
		TEndorsement pos = endoDao.findPendingEndorsement(policyNumber);
		if (pos!=null){
			endorsement = jsonHelper.fromJSON(pos.getContent(), Endorsement.class);
		}
		return endorsement;
	}

	@Override
	public Endorsement loadByNumber(String endorsementNumber) {
		Endorsement endorsement = null;
		TEndorsement po = endoDao.findByNumber(endorsementNumber);
		if(po != null) {
			endorsement = jsonHelper.fromJSON(po.getContent(), Endorsement.class);
		}
		return endorsement;
	}

	@Override
	public ResponsePage<Endorsement> findEndorsement(EndorsementQueryCondition condition, Pageable pageable) {

		Page tEndorsements = endoDao.findAll(new Specification<TEndorsement>(){
			@Override
			public Predicate toPredicate(Root<TEndorsement> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (condition.getStatusCode()!=null&&!"".equals(condition.getStatusCode())){
					Predicate p1 = cb.equal(root.get("statusCode").as(String.class),condition.getStatusCode());
					predicates.add(p1);
				}
				if (condition.getApplicationName()!=null&&!"".equals(condition.getApplicationName())){
					Predicate p2 = cb.like(root.get("applicationName").as(String.class),"%"+condition.getApplicationName()+"%");
					predicates.add(p2);
				}
				if (condition.getChannelCode()!=null&&!"".equals(condition.getChannelCode())){
					Predicate p3 = cb.equal(root.get("channelCode").as(String.class),condition.getChannelCode());
					predicates.add(p3);
				}
				if (condition.getProductCode()!=null&&!"".equals(condition.getProductCode())){
					Predicate p4 = cb.equal(root.get("productCode").as(String.class),condition.getProductCode());
					predicates.add(p4);
				}
				if (condition.getEndorsementNumber()!=null&&!"".equals(condition.getEndorsementNumber())){
					Predicate p5 = cb.equal(root.get("endorsementNumber").as(String.class),condition.getEndorsementNumber());
					predicates.add(p5);
				}
				if (condition.getPolicyNumber()!=null&&!"".equals(condition.getPolicyNumber())){
					Predicate p6 = cb.equal(root.get("policyNumber").as(String.class),condition.getPolicyNumber());
					predicates.add(p6);
				}
				if (condition.getApplicationDateStart()!=null&&condition.getApplicationDateEnd()!=null){
					Predicate p7 = cb.between(root.get("applicationDate").as(Date.class),
							condition.getApplicationDateStart(),condition.getApplicationDateEnd());
					predicates.add(p7);
				}
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return query.getRestriction();
			}
		},pageable);
		return buildResponsePage(tEndorsements);
	}

	@Override
	public Endorsement findLastEndorsement(String policyNumber){
		Endorsement endorsement = new Endorsement();
		TEndorsement po = endoDao.findLastEndorsement(policyNumber);
		if(po != null){
			endorsement = jsonHelper.fromJSON(po.getContent(), Endorsement.class);
		}
		return endorsement;
	}

	@Override
	public List<Endorsement> findAllEndorsements(String policyNumber){
		List<TEndorsement> pos = endoDao.findAllEndorsements(policyNumber);
		List<Endorsement> endorsements = Lists.newArrayList();
		for (TEndorsement po : pos) {
			Endorsement endorsement = jsonHelper.fromJSON(po.getContent(), Endorsement.class);
			endorsements.add(endorsement);
		}
		return endorsements;
	}

	@Override
	public Endorsement findEndorsementByUuid(String uuid){
		TEndorsement po = endoDao.findById(uuid).get();
		if(po == null){
			return null;
		}
		Endorsement endorsement = jsonHelper.fromJSON(po.getContent(), Endorsement.class);
		return endorsement;
	}

	private ResponsePage<Endorsement> buildResponsePage(Page<TEndorsement> tEndorsements){

		List<Endorsement> ts = tEndorsements.getContent().stream()
				.map(tu -> {
					Endorsement u = jsonHelper.fromJSON(tu.getContent(), Endorsement.class);
					return u;
				})
				.collect(toList());
		return new ResponsePage<>(tEndorsements, ts);
	}
}
