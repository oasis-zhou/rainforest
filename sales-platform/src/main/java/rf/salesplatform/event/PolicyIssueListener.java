package rf.salesplatform.event;

import com.google.common.collect.Maps;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import rf.foundation.pub.FunctionSliceBundle;
import rf.policyadmin.model.Policy;
import rf.salesplatform.fs.NewbizKeepAccounts;
import rf.salesplatform.fs.SaveCustomer;

import java.util.Map;

@Component
public class PolicyIssueListener implements ApplicationListener<PolicyIssueEvent> {

    @Override
    @Async
    @Transactional
    public void onApplicationEvent(final PolicyIssueEvent event) {
        Policy policy = (Policy)event.getSource();
        if(policy != null){
            Map<String,Object> context = Maps.newHashMap();
            new FunctionSliceBundle(policy,context)
                    .register(NewbizKeepAccounts.class)
                    .register(SaveCustomer.class)
                    .execute();
        }
    }
}
