package rf.bizop.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rf.foundation.model.ResponsePage;
import rf.policyadmin.ds.EndorsementService;
import rf.policyadmin.model.Endorsement;
import rf.policyadmin.model.QueryCondition;
import java.util.List;

@RestController
@RequestMapping("v0/api/endorsement")
public class EndorsementController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private EndorsementService endorsementService;

    @GetMapping(value = "query")
    public ResponseEntity findEndorsements(@RequestBody QueryCondition condition){

        ResponsePage<Endorsement> endorsementList = endorsementService.findEndorsement(condition);
        return new ResponseEntity(endorsementList, HttpStatus.OK);
    }


    @GetMapping(value = "query/{policyNumber}")
    public ResponseEntity findEndorsements(@PathVariable("policyNumber")  String policyNumber){

        List<Endorsement> endorsementList = endorsementService.findEndorsements(policyNumber);
        return new ResponseEntity(endorsementList, HttpStatus.OK);
    }

}
