package rf.policyadmin.ds.impl;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import rf.foundation.exception.GenericException;
import rf.foundation.numbering.NumberingFactor;
import rf.foundation.numbering.NumberingService;
import rf.foundation.numbering.NumberingType;
import rf.foundation.utils.JsonHelper;
import rf.policyadmin.constants.PolicyConstants;
import rf.policyadmin.ds.PolicyService;
import rf.policyadmin.model.*;
import rf.policyadmin.model.enums.ContractStatus;
import rf.policyadmin.model.enums.TerminalReason;
import rf.policyadmin.repository.PolicyDao;
import rf.policyadmin.repository.PolicyIndexDao;
import rf.policyadmin.repository.pojo.TPolicy;
import rf.policyadmin.repository.pojo.TPolicyIndex;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private NumberingService numberingService;

    @Override
    public String generateProposal(Policy policy) {
        TPolicy po = dao.findByProposalNumber(policy.getProposalNumber());

        if (po == null) {
            po = new TPolicy();
        } else {
            throw new GenericException(30002L);
        }

        if (po.getProposalNumber() == null) {

                Map<NumberingFactor, String> factors = new HashMap<NumberingFactor, String>();
                Date date = new Date();
                factors.put(NumberingFactor.TRANS_YEAR, new SimpleDateFormat("yyyy").format(date));

                //民生投保单号和保单号一致
                //P{863100}4{TRANS_YEAR}{023}7{SEQUENCE}
                String proposalNumber = numberingService.generateNumber(NumberingType.POLICY_NUMBER, factors);
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

        //民生投保单号和保单号一致
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
            Map<NumberingFactor, String> factors = new HashMap<NumberingFactor, String>();
            Date date = new Date();
            factors.put(NumberingFactor.TRANS_YEAR, new SimpleDateFormat("yyyy").format(date));

            //{863100}4{TRANS_YEAR}{023}7{SEQUENCE}
            String policyNumber = numberingService.generateNumber(NumberingType.POLICY_NUMBER, factors);
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

        BigDecimal SGP = policy.getPolicyFeeByCode(PolicyConstants.FEE_SGP).getValue();
        BigDecimal SNP = policy.getPolicyFeeByCode(PolicyConstants.FEE_SNP).getValue();
        BigDecimal APP = policy.getPolicyFeeByCode(PolicyConstants.FEE_APP).getValue();

        po.setSgp(SGP);
        po.setSnp(SNP);
        po.setApp(APP);

        indexDao.save(po);

    }

    @Override
    public List<PolicyIndex> findPolicy(PolicyQueryCondition condition) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        String sql = "select * from t_policy_index as p  where 1 = 1 ";

        List<PolicyIndex> indexs = Lists.newArrayList();

        List<Object> queryConditions = new ArrayList<Object>();
        if (condition.getPolicyNumber() != null && !condition.getPolicyNumber().equals("")) {
            sql += " and p.policyNumber = ? ";
            queryConditions.add(condition.getPolicyNumber());
        }
        if (condition.getProposalNumber() != null && !condition.getProposalNumber().equals("")) {
            sql += " and p.proposalNumber = ? ";
            queryConditions.add(condition.getProposalNumber());
        }
        if (condition.getPolicyHolderName() != null && !condition.getPolicyHolderName().equals("")) {
            sql += " and p.policyHolderName = ? ";
            queryConditions.add(condition.getPolicyHolderName());
        }
        if (condition.getPolicyHolderIdNumber() != null && !condition.getPolicyHolderIdNumber().equals("")) {
            sql += " and p.policyHolderIdNumber = ? ";
            queryConditions.add(condition.getPolicyHolderIdNumber());
        }
        if (condition.getPolicyInsuredName() != null && !condition.getPolicyInsuredName().equals("")) {
            sql += " and p.policyInsuredName = ? ";
            queryConditions.add(condition.getPolicyInsuredName());
        }
        if (condition.getPolicyInsuredIdNumber() != null && !condition.getPolicyInsuredIdNumber().equals("")) {
            sql += " and p.policyInsuredIdNumber = ? ";
            queryConditions.add(condition.getPolicyInsuredIdNumber());
        }
        if (condition.getProductCode() != null && !condition.getProductCode().equals("")) {
            sql += " and p.productCode = ? ";
            queryConditions.add(condition.getProductCode());
        }
        if (condition.getMobile() != null && !condition.getMobile().equals("")) {
            sql += " and p.mobile = ? ";
            queryConditions.add(condition.getMobile());
        }
        if (condition.getContractStatus() != null && !condition.getContractStatus().equals("")) {
            sql += " and p.contractStatusCode = ? ";
            queryConditions.add(condition.getContractStatus());
        }
        if (condition.getProposalDateStart() != null) {
            sql += " and p.proposalDate >= ? ";
            queryConditions.add(fmt.print(new DateTime(condition.getProposalDateStart())));
        }
        if (condition.getProposalDateEnd() != null) {
            sql += " and p.proposalDate <= ? ";
            queryConditions.add(fmt.print(new DateTime(condition.getProposalDateEnd())));
        }
        if (condition.getChannelCode() != null && !condition.getChannelCode().equals("")) {
            sql += " and p.channelCode = ? ";
            queryConditions.add(condition.getChannelCode());
        }

        if (queryConditions.size()==0){
            return indexs;
        }

        indexs = jdbcTemplate.query(sql, queryConditions.toArray(), new BeanPropertyRowMapper(PolicyIndex.class));


        return indexs;
    }

}
