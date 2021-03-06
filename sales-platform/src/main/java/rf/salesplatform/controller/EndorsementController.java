package rf.salesplatform.controller;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import rf.foundation.context.AppContext;
import rf.foundation.exception.GenericException;
import rf.foundation.pub.FunctionSliceBundle;
import rf.policyadmin.ds.EndorsementService;
import rf.policyadmin.ds.PolicyService;
import rf.policyadmin.model.Cancellation;
import rf.policyadmin.model.Endorsement;
import rf.policyadmin.model.EndorsementPolicy;
import rf.policyadmin.model.Policy;
import rf.policyadmin.model.enums.CancellationType;
import rf.policyadmin.model.enums.EndorsementApplicationType;
import rf.policyadmin.model.enums.EndorsementType;
import rf.salesplatform.event.EndorsementIssueEvent;
import rf.salesplatform.fs.*;
import rf.salesplatform.pub.Constants;
import java.util.Date;
import java.util.List;
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
        Policy policy = policyService.loadPolicyByPolicyNumber(cancellation.getPolicyNumber());
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
                .register(CreateEndorsement.class)
                .register(UpdatePolicyByCancellation.class)
                .register(EndorsementPricing.class)
                .register(EndorsementIssue.class)
                .execute();

        EndorsementIssueEvent event = new EndorsementIssueEvent(cancellation);
        AppContext.getApplicationContext().publishEvent(event);

        Map<String,Object> response = Maps.newHashMap();
        response.put("endorsementNumber", cancellation.getEndorsementNumber());

        return new ResponseEntity(response, HttpStatus.OK);

    }

    @Transactional
    @PostMapping(value = "/cancellation/midway")
    public ResponseEntity cancelFromMidway(@RequestBody Cancellation cancellation){

        if(StringUtils.isEmpty(cancellation.getPolicyNumber()))
            throw new GenericException(30015L);
        Policy policy = policyService.loadPolicyByPolicyNumberOnLock(cancellation.getPolicyNumber());
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
                .register(CreateEndorsement.class)
                .register(UpdatePolicyByCancellation.class)
                .register(EndorsementPricing.class)
                .register(EndorsementIssue.class)
                .execute();

        EndorsementIssueEvent event = new EndorsementIssueEvent(cancellation);
        AppContext.getApplicationContext().publishEvent(event);

        Map<String,Object> response = Maps.newHashMap();
        response.put("endorsementNumber", cancellation.getEndorsementNumber());

        return new ResponseEntity(response, HttpStatus.OK);

    }

    @Transactional
    @PostMapping(value = "/cancellation/midway/pricing")
    public ResponseEntity priceCancellation(@RequestBody Cancellation cancellation){

        if(StringUtils.isEmpty(cancellation.getPolicyNumber()))
            throw new GenericException(30015L);
        Policy policy = policyService.loadPolicyByPolicyNumber(cancellation.getPolicyNumber());
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
                .register(UpdatePolicyByCancellation.class)
                .register(EndorsementPricing.class)
                .execute();

        Map<String,Object> response = Maps.newHashMap();
        response.put("endorsementPremium", cancellation.getEndoFeeByCode(Constants.FEE_APP).getValue());

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping(value = "query/{policyNumber}")
    public ResponseEntity findEndorsements(@PathVariable("policyNumber")  String policyNumber){

        List<Endorsement> endorsementList = endorsementService.findEndorsements(policyNumber);
        return new ResponseEntity(endorsementList, HttpStatus.OK);
    }

}
