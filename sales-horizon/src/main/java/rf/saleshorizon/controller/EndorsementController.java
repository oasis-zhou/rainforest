package rf.saleshorizon.controller;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import rf.foundation.exception.GenericException;
import rf.foundation.pub.FunctionSliceBundle;
import rf.policyadmin.model.Cancellation;
import rf.policyadmin.model.Endorsement;
import rf.policyadmin.model.EndorsementPolicy;
import rf.policyadmin.model.Policy;
import rf.policyadmin.model.enums.CancellationType;
import rf.policyadmin.model.enums.EndorsementApplicationType;
import rf.policyadmin.model.enums.EndorsementType;
import rf.saleshorizon.ds.EndorsementService;
import rf.saleshorizon.ds.PolicyService;
import rf.saleshorizon.fs.EndorsementIssue;
import rf.saleshorizon.fs.EndorsementPricing;
import rf.saleshorizon.fs.EndorsementValidation;
import rf.saleshorizon.fs.UpdatePolicyByCancellation;
import rf.saleshorizon.pub.Constants;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("v0/api/endorsement")
public class EndorsementController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PolicyService policyService;
    @Autowired
    private EndorsementService endorsementService;

    @Transactional
    @PostMapping(value = "/cancellation")
    public ResponseEntity cancelFromInception(@RequestBody Cancellation cancellation){

        if(StringUtils.isEmpty(cancellation.getPolicyNumber()))
            throw new GenericException(30015L);
        Policy policy = policyService.pullFromChain(cancellation.getPolicyNumber());
        if(policy == null)
            throw new GenericException(30012L);

        cancellation.setCancellationType(CancellationType.CANCELLATION_FROM_INCEPTION);
        cancellation.setEffectiveDate(policy.getEffectiveDate());
        cancellation.setProductCode(policy.getProductCode());

        Map<String,Object> context = Maps.newHashMap();
        EndorsementPolicy endorsementPolicy = new EndorsementPolicy(policy,policy);
        context.put(Constants.ENDORSEMENT_POLICY_OBJECT,endorsementPolicy);

        new FunctionSliceBundle(cancellation,context)
                .register(EndorsementValidation.class)
                .register(UpdatePolicyByCancellation.class)
                .register(EndorsementPricing.class)
                .register(EndorsementIssue.class)
                .execute();

        Map<String,Object> response = Maps.newHashMap();
        response.put("endorsementNumber", cancellation.getEndorsementNumber());

        return new ResponseEntity(response, HttpStatus.OK);

    }

    @Transactional
    @PostMapping(value = "/cancellation/midway")
    public ResponseEntity cancelFromMidway(@RequestBody Cancellation cancellation){

        if(StringUtils.isEmpty(cancellation.getPolicyNumber()))
            throw new GenericException(30015L);
        Policy policy = policyService.pullFromChain(cancellation.getPolicyNumber());
        if(policy == null)
            throw new GenericException(30012L);

        cancellation.setCancellationType(CancellationType.CANCELLATION_MIDWAY);
        cancellation.setProductCode(policy.getProductCode());
        cancellation.setEndorsementType(EndorsementType.CANCELLATION);
        cancellation.setCode("CANCELLATION_FROM_MIDWAY");
        cancellation.setName("中途退保");
        cancellation.setApplicationDate(new Date());
        cancellation.setApplicationType(EndorsementApplicationType.APPLY_BY_INSURER);

        Map<String,Object> context = Maps.newHashMap();
        context.put(Constants.POLICY_OBJECT,policy);  EndorsementPolicy endorsementPolicy = new EndorsementPolicy(policy,policy);
        context.put(Constants.ENDORSEMENT_POLICY_OBJECT,endorsementPolicy);

        new FunctionSliceBundle(cancellation,context)
                .register(EndorsementValidation.class)
                .register(UpdatePolicyByCancellation.class)
                .register(EndorsementPricing.class)
                .register(EndorsementIssue.class)
                .execute();

        Map<String,Object> response = Maps.newHashMap();
        response.put("endorsementNumber", cancellation.getEndorsementNumber());

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "/cancellation/midway/pricing")
    public ResponseEntity priceCancellation(@RequestBody Cancellation cancellation){

        if(StringUtils.isEmpty(cancellation.getPolicyNumber()))
            throw new GenericException(30015L);
        Policy policy = policyService.pullFromChain(cancellation.getPolicyNumber());
        if(policy == null)
            throw new GenericException(30012L);

        cancellation.setCancellationType(CancellationType.CANCELLATION_MIDWAY);
        cancellation.setProductCode(policy.getProductCode());
        cancellation.setEndorsementType(EndorsementType.CANCELLATION);
        cancellation.setCode("CANCELLATION_FROM_MIDWAY");
        cancellation.setName("中途退保");
        cancellation.setApplicationDate(new Date());
        cancellation.setApplicationType(EndorsementApplicationType.APPLY_BY_INSURER);

        Map<String,Object> context = Maps.newHashMap();
        EndorsementPolicy endorsementPolicy = new EndorsementPolicy(policy,policy);
        context.put(Constants.ENDORSEMENT_POLICY_OBJECT,endorsementPolicy);

        new FunctionSliceBundle(cancellation,context)
                .register(EndorsementValidation.class)
                .register(EndorsementPricing.class)
                .execute();

        Map<String,Object> response = Maps.newHashMap();
        response.put("endorsementPremium", cancellation.getEndoFeeByCode(rf.policyadmin.pub.Constants.FEE_APP).getValue());

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping(value = "query/{endorsementNumber}")
    public ResponseEntity findEndorsements(@PathVariable("endorsementNumber")  String endorsementNumber){

        Endorsement endorsementList = endorsementService.pullFromChain(endorsementNumber);
        return new ResponseEntity(endorsementList, HttpStatus.OK);
    }
}
