package rf.policyadmin.model.trans;

import rf.policyadmin.model.Policy;
import rf.policyadmin.model.Quotation;

public interface PolicyTransformer {

    public Policy transFromQuotation(Quotation quotation);

    public Policy transFromQuotation(Quotation quotation, Policy policy);
}
