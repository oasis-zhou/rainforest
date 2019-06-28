package rf.salesplatform.controller;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rf.foundation.context.AppContext;
import rf.foundation.pub.FunctionSliceBundle;
import rf.foundation.utils.JsonHelper;
import rf.policyadmin.ds.EndorsementService;
import rf.policyadmin.ds.PolicyService;
import rf.policyadmin.model.Policy;
import rf.policyadmin.model.PolicyIndex;
import rf.policyadmin.model.PolicyQueryCondition;
import rf.policyadmin.model.Quotation;
import rf.policyadmin.model.enums.ContractStatus;
import rf.policyadmin.model.trans.PolicyTransformer;
import rf.salesplatform.event.PolicyIssueEvent;
import rf.salesplatform.fs.*;
import rf.salesplatform.pub.PAFConsts;
import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author Zhouzheng
 */

@RestController
@RequestMapping("v0/api/policy")
public class PolicyController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PolicyTransformer policyTransformer;
    @Autowired
    private JsonHelper jsonHelper;
    @Autowired
    private PolicyService policyService;
    @Autowired
    private EndorsementService endorsementService;

    @Transactional
    @PostMapping(value = "/proposal")
    public ResponseEntity proposal(Quotation quotation) {

            long s = System.currentTimeMillis();
            Policy policy = policyTransformer.transFromQuotation(quotation);

            Map<String, Object> context = Maps.newHashMap();
            context.put(PAFConsts.AUTO_UNDERWRITING_RULE_SET, PAFConsts.RULE_SET_UW);

            new FunctionSliceBundle(policy, context)
                    .register(SetupPolicyForFixCoverage.class)
                    .register(DataValidation.class)
                    .register(DuplicatePolicyCheck.class)
                    .register(AutoUnderwriting.class)
                    .register(NewbizPricing.class)
                    .register(NewbizProposal.class)
                    .register(CreatePolicyIndex.class)
                    .execute();

            Map<String, Object> response = Maps.newHashMap();
            response.put("proposalNumber", policy.getProposalNumber());
//            response.put("underwritingReason",policy.getUnderwritingReason());
            long e = System.currentTimeMillis();
            logger.info("生成投保单耗时" + (e - s) + "ms");
            return new ResponseEntity(response, HttpStatus.OK);
    }


    @Transactional
    @PostMapping(value = "/issue")
    public ResponseEntity issue(Quotation quotation) {

            Policy policy = policyTransformer.transFromQuotation(quotation);

            Map<String, Object> context = Maps.newHashMap();
            context.put(PAFConsts.AUTO_UNDERWRITING_RULE_SET, PAFConsts.RULE_SET_UW);

            new FunctionSliceBundle(policy, context)
                    .register(SetupPolicyForFixCoverage.class)
                    .register(DataValidation.class)
                    .register(DuplicatePolicyCheck.class)
                    .register(AutoUnderwriting.class)
                    .register(NewbizPricing.class)
                    .register(PolicyIssue.class)
                    .register(CreatePolicyIndex.class)
                    .execute();

            PolicyIssueEvent event = new PolicyIssueEvent(policy);
            AppContext.getApplicationContext().publishEvent(event);

            Map<String, Object> response = Maps.newHashMap();
            response.put("policyNumber", policy.getPolicyNumber());
//            response.put("underwritingReason",policy.getUnderwritingReason());

        return new ResponseEntity(response, HttpStatus.OK);

    }


    @Transactional
    @PostMapping(value = "/issue/{proposalNumber}")
    public ResponseEntity issueProposal(@PathParam("proposalNumber") String proposalNumber) {


            Policy policy = policyService.loadPolicyByProposalNumber(proposalNumber);

            Map<String, Object> context = Maps.newHashMap();


            new FunctionSliceBundle(policy, context)
                    .register(PolicyIssue.class)
                    .register(CreatePolicyIndex.class)
                    .execute();

            PolicyIssueEvent event = new PolicyIssueEvent(policy);
            AppContext.getApplicationContext().publishEvent(event);

            Map<String, Object> response = Maps.newHashMap();
            response.put("policyNumber", policy.getPolicyNumber());
//            response.put("underwritingReason",policy.getUnderwritingReason());

        return new ResponseEntity(response, HttpStatus.OK);

    }


    @PostMapping(value = "/query")
    public ResponseEntity queryPolicy(PolicyQueryCondition condition) {

            logger.debug("policy query condition:" + jsonHelper.toJSON(condition));
            List<PolicyIndex> policyIndices = policyService.findPolicy(condition);

            for (PolicyIndex index : policyIndices) {
                if (index.getContractStatusCode().equals(ContractStatus.EFFECTIVE.name())) {
                    Date expDate = index.getExpiredDate();
                    if (expDate.before(new Date())) {
                        index.setContractStatusCode(ContractStatus.TERMINAL.name());
                    }
                }
            }
            return new ResponseEntity(policyIndices, HttpStatus.OK);

    }

    @GetMapping(value = "/{policyNumber}")
    public ResponseEntity loadPolicy(@PathParam("policyNumber") String policyNumber) {

        Policy policy = policyService.loadPolicyByPolicyNumber(policyNumber);
        return new ResponseEntity(policy, HttpStatus.OK);
    }


    @GetMapping(value = "proposal/{proposalNumber}")
    public ResponseEntity loadPolicyByProposalNumber(@PathParam("proposalNumber") String proposalNumber) {

        Policy policy = policyService.loadPolicyByProposalNumber(proposalNumber);
        return new ResponseEntity(policy, HttpStatus.OK);
    }
}
