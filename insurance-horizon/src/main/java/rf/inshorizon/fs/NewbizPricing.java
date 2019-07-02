package rf.inshorizon.fs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.foundation.pub.FunctionSlice;
import rf.inshorizon.ds.PricingService;
import rf.policyadmin.model.Policy;
import java.util.Map;


@Component
public class NewbizPricing implements FunctionSlice<Policy> {

    @Autowired
    private PricingService pricingService;

    @Override
    public void execute(Policy policy, Map<String, Object> context){
        pricingService.price(policy);
    }

}
