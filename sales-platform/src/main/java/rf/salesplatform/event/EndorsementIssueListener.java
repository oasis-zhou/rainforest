package rf.salesplatform.event;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import rf.foundation.pub.FunctionSliceBundle;
import rf.policyadmin.ds.PolicyService;
import rf.policyadmin.model.Endorsement;
import rf.salesplatform.fs.EndorsementKeepAccounts;

import java.util.Map;

@Component
public class EndorsementIssueListener implements ApplicationListener<EndorsementIssueEvent> {
    @Autowired
    private PolicyService policyService;

    @Override
    @Async
    @Transactional
    public void onApplicationEvent(final EndorsementIssueEvent event) {
        Endorsement endorsement = (Endorsement)event.getSource();
        if(endorsement != null){
            Map<String,Object> context = Maps.newHashMap();

            new FunctionSliceBundle(endorsement,context)
                    .register(EndorsementKeepAccounts.class)
                    .execute();
        }
    }
}
