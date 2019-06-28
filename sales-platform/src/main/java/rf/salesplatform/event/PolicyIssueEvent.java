package rf.salesplatform.event;

import org.springframework.context.ApplicationEvent;
import rf.policyadmin.model.Policy;


public class PolicyIssueEvent extends ApplicationEvent {

    public PolicyIssueEvent(final Policy content) {
        super(content);
    }
}
