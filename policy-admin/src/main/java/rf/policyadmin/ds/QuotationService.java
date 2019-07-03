package rf.policyadmin.ds;

import rf.foundation.model.ResponsePage;
import rf.policyadmin.model.QueryCondition;
import rf.policyadmin.model.Quotation;

/**
 * Created by zhengzhou on 16/8/8.
 */
public interface QuotationService {

    String generateQuotation(Quotation quotation);

    ResponsePage<Quotation> findQuotations(QueryCondition condition);

}
