package rf.cohorizon.ds;

public interface IdentityService {

    String getToken();
    void register(String org,String pubKey);
    String getPubKey(String org);
}
