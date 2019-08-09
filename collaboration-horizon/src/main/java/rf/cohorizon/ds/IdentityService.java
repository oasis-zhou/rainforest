package rf.cohorizon.ds;

import rf.cohorizon.model.Registration;

public interface IdentityService {
    String register(Registration registration);
    String getPubKey(String orgCode);
}
