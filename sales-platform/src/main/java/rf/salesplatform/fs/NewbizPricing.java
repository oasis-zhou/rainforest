package rf.salesplatform.fs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.foundation.pub.FunctionSlice;
import rf.policyadmin.model.Policy;
import rf.salesplatform.fs.pub.PolicyPricing;

import java.util.Map;


@Component
public class NewbizPricing implements FunctionSlice<Policy> {

    @Autowired
    private PolicyPricing policyPricing;


    @Override
    public void execute(Policy policy, Map<String, Object> context){
        policyPricing.price(policy);
    }

}
