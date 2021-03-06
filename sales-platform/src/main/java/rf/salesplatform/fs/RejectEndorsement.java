package rf.salesplatform.fs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.foundation.pub.FunctionSlice;
import rf.policyadmin.ds.EndorsementService;
import rf.policyadmin.model.Endorsement;

import java.util.Map;

/**
 * Created by zhouzheng on 2017/7/10.
 */
@Component
public class RejectEndorsement implements FunctionSlice<Endorsement> {

    @Autowired
    private EndorsementService endorsementService;

    @Override
    public void execute(Endorsement endorsement, Map<String, Object> context) {
        endorsementService.reject(endorsement.getUuid());
    }
}
