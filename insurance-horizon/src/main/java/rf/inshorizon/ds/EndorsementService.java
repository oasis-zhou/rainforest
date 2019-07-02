package rf.inshorizon.ds;

import rf.policyadmin.model.Endorsement;

public interface EndorsementService {
   void pushToChain(Endorsement endorsement);
   Endorsement pullFromChain(String endorsementNumber);
}
