package rf.salesplatform.event;

import org.springframework.context.ApplicationEvent;
import rf.policyadmin.model.Endorsement;

public class EndorsementIssueEvent extends ApplicationEvent {

    public EndorsementIssueEvent(final Endorsement content) {
        super(content);
    }
}
