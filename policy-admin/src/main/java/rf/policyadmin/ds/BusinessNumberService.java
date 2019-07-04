package rf.policyadmin.ds;

import rf.policyadmin.model.Endorsement;
import rf.policyadmin.model.Policy;
import rf.policyadmin.model.Quotation;

public interface BusinessNumberService {

    String generatePolicyNumber(Policy policy);

    String generateProposalNumber(Policy policy);

    String generateQuotationNumber(Quotation quotation);

    String generateEndorsementNumber(Endorsement endorsement);

}
