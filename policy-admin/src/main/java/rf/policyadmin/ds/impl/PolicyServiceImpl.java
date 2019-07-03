package rf.policyadmin.ds.impl;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import rf.foundation.exception.GenericException;
import rf.foundation.model.ResponsePage;
import rf.foundation.utils.JsonHelper;
import rf.policyadmin.ds.BusinessNumberService;
import rf.policyadmin.pub.Constants;
import rf.policyadmin.ds.PolicyService;
import rf.policyadmin.model.*;
import rf.policyadmin.model.enums.ContractStatus;
import rf.policyadmin.model.enums.TerminalReason;
import rf.policyadmin.repository.PolicyDao;
import rf.policyadmin.repository.PolicyIndexDao;
import rf.policyadmin.repository.pojo.TPolicy;
import rf.policyadmin.repository.pojo.TPolicyIndex;
import rf.policyadmin.repository.pojo.TQuotation;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhengzhou on 16/8/8.
 */
@Service
public class PolicyServiceImpl implements PolicyService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PolicyDao dao;
    @Autowired
    private PolicyIndexDao indexDao;
    @Autowired
    private JsonHelper jsonHelper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BusinessNumberService businessNumberService;

    @Override
    public String generateProposal(Policy policy) {
        TPolicy po = dao.findByProposalNumber(policy.getProposalNumber());

        if (po == null) {
            po = new TPolicy();
        } else {
            throw new GenericException(30002L);
        }

        if (po.getProposalNumber() == null) {
                String proposalNumber = businessNumberService.generateProposalNumber(policy);
                policy.setProposalNumber(proposalNumber);
        }

        BeanUtils.copyProperties(policy, po);

        po.setContractStatusCode(policy.getContractStatus().name());
        po.setProposalDate(new Date());

        String content = jsonHelper.toJSON(policy);
        po.setContent(content);

        dao.save(po);

        return po.getProposalNumber();
    }

    @Override
    public String issuePolicy(Policy policy) {
        TPolicy po = null;

        if (policy.getPolicyNumber() != null) {
            po = dao.findByPolicyNumber(policy.getPolicyNumber());
            if (po != null){
                throw new GenericException(30001L);
            }
        }
        if (policy.getProposalNumber() != null) {
            po = dao.findByProposalNumber(policy.getProposalNumber());
            if (po.getPolicyNumber() == null) {
                if (policy.getPolicyNumber() == null) {
                    po.setPolicyNumber(policy.getProposalNumber());
                    policy.setPolicyNumber(policy.getProposalNumber());
                } else {
                    po.setPolicyNumber(policy.getPolicyNumber());
                }
            } else {
                throw new GenericException(30001L);
            }

        }

        if (po == null) {
            po = new TPolicy();
        }

        if (policy.getPolicyNumber() == null) {
            String policyNumber = businessNumberService.generatePolicyNumber(policy);
            policy.setPolicyNumber(policyNumber);
        }

        BeanUtils.copyProperties(policy, po);

        po.setContractStatusCode(ContractStatus.EFFECTIVE.name());
        po.setIssueDate(new Date());

        String content = jsonHelper.toJSON(policy);
        po.setContent(content);

        dao.save(po);

        return po.getPolicyNumber();
    }

    @Override
    public void rejectPolicy(String proposalNumber){

        Policy policy = this.loadPolicyByProposalNumber(proposalNumber);

        policy.setContractStatus(ContractStatus.TERMINAL);
        if(policy.getContractStatus().equals(ContractStatus.WAITING_FOR_PAYMENT)) {
            policy.setTerminalReason(TerminalReason.TERMINAL_BY_PAYMENT_FAILED);
        }else{
            policy.setTerminalReason(TerminalReason.TERMINAL_BY_UNDERWRITING);
        }
        policy.setTerminalDate(new Date());

        this.savePolicy(policy);
        
        this.generatePolicyIndex(policy);

    }

    @Override
    public Policy loadPolicyByPolicyNumber(String policyNumber) {
        TPolicy po = dao.findByPolicyNumber(policyNumber);

        Policy policy = null;
        if (po != null) {
            policy = jsonHelper.fromJSON(po.getContent(), Policy.class);
        }

        return policy;
    }

    @Override
    public Policy loadPolicyByPolicyNumberOnLock(String policyNumber) {
        TPolicy po = dao.findByPolicyNumberOnLock(policyNumber);

        Policy policy = null;
        if (po != null) {
            policy = jsonHelper.fromJSON(po.getContent(), Policy.class);
        }

        return policy;
    }

    @Override
    public Policy loadPolicyByProposalNumber(String proposalNumber) {
        TPolicy po = dao.findByProposalNumber(proposalNumber);

        Policy policy = null;
        if (po != null) {
            policy = jsonHelper.fromJSON(po.getContent(), Policy.class);
        }

        return policy;
    }

    @Override
    public Policy loadPolicyByProposalNumberOnLock(String proposalNumber) {
        TPolicy po = dao.findByProposalNumberOnLock(proposalNumber);

        Policy policy = null;
        if (po != null) {
            policy = jsonHelper.fromJSON(po.getContent(), Policy.class);
        }

        return policy;
    }

    @Override
    public void savePolicy(Policy policy) {
        TPolicy po = dao.findById(policy.getUuid()).get();
        if (po == null){
            po = new TPolicy();
        }

        BeanUtils.copyProperties(policy, po);

        po.setContractStatusCode(policy.getContractStatus().name());

        String content = jsonHelper.toJSON(policy);
        po.setContent(content);

        dao.save(po);
    }

    @Override
    public void generatePolicyIndex(Policy policy) {

        TPolicyIndex po = indexDao.findById(policy.getUuid()).get();
        if (po == null) {
            po = new TPolicyIndex();
        }
        BeanUtils.copyProperties(policy, po);
        if (policy.getContractStatus() != null){
            po.setContractStatusCode(policy.getContractStatus().name());
        }
        Customer policyHolder = policy.getPolicyHolder();
        po.setPolicyHolderName(policyHolder.getName());
        po.setPolicyHolderIdType(policyHolder.getIdType());
        po.setPolicyHolderIdNumber(policyHolder.getIdNumber());
        if (policyHolder instanceof PersonCustomer){
            po.setMobile(((PersonCustomer) policyHolder).getMobile());
        }
        List<Customer> insureds = policy.getInsureds();
        PersonCustomer insured = (PersonCustomer) insureds.get(0);
        po.setPolicyInsuredName(insured.getName());
        po.setPolicyInsuredIdType(insured.getIdType());
        po.setPolicyInsuredIdNumber(insured.getIdNumber());
        po.setLimitAmount(policy.getAOPAmount());
        po.setTerminalReasonCode(policy.getTerminalReason() == null ? "" : policy.getTerminalReason().name());

        BigDecimal SGP = policy.getPolicyFeeByCode(Constants.FEE_SGP).getValue();
        BigDecimal SNP = policy.getPolicyFeeByCode(Constants.FEE_SNP).getValue();
        BigDecimal APP = policy.getPolicyFeeByCode(Constants.FEE_APP).getValue();

        po.setSgp(SGP);
        po.setSnp(SNP);
        po.setApp(APP);

        indexDao.save(po);

    }

    @Override
    public ResponsePage<PolicyIndex> findPolicy(QueryCondition condition) {

        // 构造自定义查询条件
        Specification<TPolicyIndex> queryCondition = new Specification<TPolicyIndex>() {
            @Override
            public Predicate toPredicate(Root<TPolicyIndex> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (condition.getPolicyNumber() != null && !condition.getPolicyNumber().equals("")) {
                    predicateList.add(criteriaBuilder.equal(root.get("policyNumber"),condition.getPolicyNumber()));
                }
                if (condition.getProposalNumber() != null && !condition.getProposalNumber().equals("")) {
                    predicateList.add(criteriaBuilder.equal(root.get("proposalNumber"),condition.getProposalNumber()));
                }
                if (condition.getPolicyHolderName() != null && !condition.getPolicyHolderName().equals("")) {
                    predicateList.add(criteriaBuilder.like(root.get("policyHolderName"),"%" + condition.getPolicyHolderName() + "%"));
                }
                if (condition.getPolicyHolderIdNumber() != null && !condition.getPolicyHolderIdNumber().equals("")) {
                    predicateList.add(criteriaBuilder.equal(root.get("policyHolderIdNumber"),condition.getPolicyHolderIdNumber()));
                }
                if (condition.getPolicyInsuredName() != null && !condition.getPolicyInsuredName().equals("")) {
                    predicateList.add(criteriaBuilder.like(root.get("policyInsuredName"),"%" + condition.getPolicyInsuredName() + "%"));
                }
                if (condition.getPolicyInsuredIdNumber() != null && !condition.getPolicyInsuredIdNumber().equals("")) {
                    predicateList.add(criteriaBuilder.equal(root.get("policyInsuredNumber"),condition.getPolicyInsuredIdNumber()));
                }
                if (condition.getMobile() != null && !condition.getMobile().equals("")) {
                    predicateList.add(criteriaBuilder.equal(root.get("mobile"),condition.getMobile()));
                }
                if (condition.getChannelCode()!=null&&!"".equals(condition.getChannelCode())){
                    predicateList.add(criteriaBuilder.equal(root.get("channelCode"),condition.getChannelCode()));
                }
                if (condition.getProductCode()!=null&&!"".equals(condition.getProductCode())){
                    predicateList.add(criteriaBuilder.equal(root.get("productCode"),condition.getProductCode()));
                }
                if (condition.getDateStart()!=null&&condition.getDateEnd()!=null){
                    predicateList.add(criteriaBuilder.between(root.get("proposalDate"),condition.getDateStart(),condition.getDateEnd()));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

        Page<TPolicyIndex> page = indexDao.findAll(queryCondition, PageRequest.of(condition.getPageNo() - 1, condition.getPageSize(), Sort.by(Sort.Direction.DESC, "proposalDate")));

        return buildResponsePage(page);

    }

    @Override
    public Long countPolicy(QueryCondition condition) {

        Long count = 0L;
        // 构造自定义查询条件
        Specification<TPolicyIndex> queryCondition = new Specification<TPolicyIndex>() {
            @Override
            public Predicate toPredicate(Root<TPolicyIndex> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (condition.getPolicyNumber() != null && !condition.getPolicyNumber().equals("")) {
                    predicateList.add(criteriaBuilder.equal(root.get("policyNumber"),condition.getPolicyNumber()));
                }
                if (condition.getProposalNumber() != null && !condition.getProposalNumber().equals("")) {
                    predicateList.add(criteriaBuilder.equal(root.get("proposalNumber"),condition.getProposalNumber()));
                }
                if (condition.getPolicyHolderName() != null && !condition.getPolicyHolderName().equals("")) {
                    predicateList.add(criteriaBuilder.like(root.get("policyHolderName"),"%" + condition.getPolicyHolderName() + "%"));
                }
                if (condition.getPolicyHolderIdNumber() != null && !condition.getPolicyHolderIdNumber().equals("")) {
                    predicateList.add(criteriaBuilder.equal(root.get("policyHolderIdNumber"),condition.getPolicyHolderIdNumber()));
                }
                if (condition.getPolicyInsuredName() != null && !condition.getPolicyInsuredName().equals("")) {
                    predicateList.add(criteriaBuilder.like(root.get("policyInsuredName"),"%" + condition.getPolicyInsuredName() + "%"));
                }
                if (condition.getPolicyInsuredIdNumber() != null && !condition.getPolicyInsuredIdNumber().equals("")) {
                    predicateList.add(criteriaBuilder.equal(root.get("policyInsuredNumber"),condition.getPolicyInsuredIdNumber()));
                }
                if (condition.getMobile() != null && !condition.getMobile().equals("")) {
                    predicateList.add(criteriaBuilder.equal(root.get("mobile"),condition.getMobile()));
                }
                if (condition.getChannelCode()!=null&&!"".equals(condition.getChannelCode())){
                    predicateList.add(criteriaBuilder.equal(root.get("channelCode"),condition.getChannelCode()));
                }
                if (condition.getProductCode()!=null&&!"".equals(condition.getProductCode())){
                    predicateList.add(criteriaBuilder.equal(root.get("productCode"),condition.getProductCode()));
                }
                if (condition.getDateStart()!=null&&condition.getDateEnd()!=null){
                    predicateList.add(criteriaBuilder.between(root.get("proposalDate"),condition.getDateStart(),condition.getDateEnd()));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

        count = indexDao.count(queryCondition);

        return count;
    }

    private ResponsePage<PolicyIndex> buildResponsePage(Page<TPolicyIndex> page){
        List<PolicyIndex> datas = page.getContent().stream()
                .map(po -> {
                    PolicyIndex index = new PolicyIndex();
                    BeanUtils.copyProperties(po,index);
                    return index;
                })
                .collect(toList());
        return new ResponsePage<>(page, datas);
    }

}
