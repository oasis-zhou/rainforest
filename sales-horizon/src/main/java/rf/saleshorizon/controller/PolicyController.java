package rf.saleshorizon.controller;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rf.foundation.pub.FunctionSliceBundle;
import rf.policyadmin.model.Policy;
import rf.policyadmin.model.Quotation;
import rf.policyadmin.model.trans.PolicyTransformer;
import rf.saleshorizon.ds.BlockChainService;
import rf.saleshorizon.fs.*;
import rf.saleshorizon.pub.Constants;
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
    private BlockChainService blockChainService;

    @Transactional
    @PostMapping(value = "/proposal")
    public ResponseEntity proposal(@RequestBody Quotation quotation) {

            long s = System.currentTimeMillis();
            Policy policy = policyTransformer.transFromQuotation(quotation);

            Map<String, Object> context = Maps.newHashMap();
            context.put(Constants.AUTO_UNDERWRITING_RULE_SET, Constants.RULE_SET_UW);

            new FunctionSliceBundle(policy, context)
                    .register(SetupPolicyForFixCoverage.class)
                    .register(AutoUnderwriting.class)
                    .register(NewbizPricing.class)
                    .register(NewbizProposal.class)
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
    public ResponseEntity issue(@RequestBody Quotation quotation) {

            Policy policy = policyTransformer.transFromQuotation(quotation);

            Map<String, Object> context = Maps.newHashMap();
            context.put(Constants.AUTO_UNDERWRITING_RULE_SET, Constants.RULE_SET_UW);

            new FunctionSliceBundle(policy, context)
                    .register(SetupPolicyForFixCoverage.class)
                    .register(AutoUnderwriting.class)
                    .register(NewbizPricing.class)
                    .register(PolicyIssue.class)
                    .execute();

            Map<String, Object> response = Maps.newHashMap();
            response.put("policyNumber", policy.getPolicyNumber());
//            response.put("underwritingReason",policy.getUnderwritingReason());

        return new ResponseEntity(response, HttpStatus.OK);

    }

    @GetMapping(value = "/{policyNumber}")
    public ResponseEntity loadPolicy(@PathVariable("policyNumber") String policyNumber) {

        Policy policy = blockChainService.findPolicy(policyNumber);
        return new ResponseEntity(policy, HttpStatus.OK);
    }

    @GetMapping(value = "proposal/{proposalNumber}")
    public ResponseEntity loadPolicyByProposalNumber(@PathVariable("proposalNumber") String proposalNumber) {

        Policy policy = blockChainService.findPolicy(proposalNumber);
        return new ResponseEntity(policy, HttpStatus.OK);
    }
}
