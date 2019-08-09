package rf.cohorizon.ds;

public interface IdentityService {
    void register(String orgCode, String pubKey);
    String getPubKey(String orgCode);
}
