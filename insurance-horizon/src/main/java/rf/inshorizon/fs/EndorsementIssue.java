package rf.inshorizon.fs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.foundation.pub.FunctionSlice;

import rf.inshorizon.ds.EndorsementService;
import rf.policyadmin.model.Endorsement;
import rf.policyadmin.model.enums.EndorsementStatus;

import java.util.Date;
import java.util.Map;

@Component
public class EndorsementIssue implements FunctionSlice<Endorsement> {

    @Autowired
    private EndorsementService endorsementService;

    @Override
    public void execute(Endorsement endorsement, Map<String, Object> context){
        endorsement.setIssueDate(new Date());
        endorsement.setEndorsementStatus(EndorsementStatus.ISSUE);
       endorsementService.pushToChain(endorsement);
    }
}
