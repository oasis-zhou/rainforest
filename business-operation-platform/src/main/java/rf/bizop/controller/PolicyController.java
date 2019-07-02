package rf.bizop.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rf.foundation.utils.JsonHelper;
import rf.policyadmin.ds.EndorsementService;
import rf.policyadmin.ds.PolicyService;
import rf.policyadmin.model.Policy;
import rf.policyadmin.model.PolicyIndex;
import rf.policyadmin.model.PolicyQueryCondition;
import rf.policyadmin.model.enums.ContractStatus;
import rf.policyadmin.model.trans.PolicyTransformer;
import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;


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

    @PostMapping(value = "/query")
    public ResponseEntity queryPolicy(@RequestBody PolicyQueryCondition condition) {

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
