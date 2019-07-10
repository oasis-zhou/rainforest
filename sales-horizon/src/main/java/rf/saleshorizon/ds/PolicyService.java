package rf.saleshorizon.ds;

import rf.policyadmin.model.Policy;

public interface PolicyService {

    void pushToChain(Policy policy);
    Policy pullFromChain(String number);

}
