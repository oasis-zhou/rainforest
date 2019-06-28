package rf.salesplatform.event;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import rf.policyadmin.ds.QuotationService;
import rf.policyadmin.model.Quotation;


@Component
public class QuotationListener implements ApplicationListener<QuotationEvent> {

    @Autowired
    private QuotationService quotationService;

    @Override
    @Async
    @Transactional
    public void onApplicationEvent(final QuotationEvent event) {
        Quotation quotation = (Quotation)event.getSource();
        if(quotation != null){
            quotationService.generateQuotation(quotation);
        }
    }
}
