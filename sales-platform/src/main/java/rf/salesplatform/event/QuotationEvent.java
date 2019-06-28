package rf.salesplatform.event;

import org.springframework.context.ApplicationEvent;
import rf.policyadmin.model.Quotation;

public class QuotationEvent extends ApplicationEvent {

    public QuotationEvent(final Quotation content) {
        super(content);
    }
}
